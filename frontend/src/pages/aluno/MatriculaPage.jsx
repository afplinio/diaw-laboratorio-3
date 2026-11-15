import { useState } from 'react'
import Mensagem from '../../components/Mensagem.jsx'
import PageHeader from '../../components/PageHeader.jsx'
import StatusBadge from '../../components/StatusBadge.jsx'
import { useCarregar } from '../../hooks/useCarregar.js'
import { api } from '../../services/api.js'
import { formatarData, formatarDataHora, formatarMoeda } from '../../utils/formatacao.js'

export default function MatriculaPage() {
  const { dados, erro: erroCarga, carregando, recarregar } = useCarregar(async () => {
    const [painel, matriculas] = await Promise.all([api.get('/aluno/painel'), api.get('/aluno/matriculas')])
    return { painel, matriculas }
  })
  const [erro, setErro] = useState('')
  const [sucesso, setSucesso] = useState('')
  const [processando, setProcessando] = useState(null)

  if (carregando && !dados) return <p className="muted">Carregando…</p>
  if (!dados) return <Mensagem texto={erroCarga} />

  const { painel, matriculas } = dados
  const curriculo = painel.curriculo
  const doSemestre = curriculo ? matriculas.filter((m) => m.oferta?.curriculoId === curriculo.id) : []
  const ativas = doSemestre.filter((m) => m.status === 'ATIVA')
  const obrigatorias = ativas.filter((m) => m.tipo === 'OBRIGATORIA').length
  const optativas = ativas.filter((m) => m.tipo === 'OPTATIVA').length
  const total = ativas.reduce((soma, m) => soma + Number(m.oferta?.valor ?? 0), 0)
  const matriculadoEm = new Set(ativas.map((m) => m.oferta?.id))
  const historico = matriculas.filter((m) => !doSemestre.includes(m))

  async function executar(chave, acao, mensagem) {
    setErro('')
    setSucesso('')
    setProcessando(chave)
    try {
      await acao()
      setSucesso(mensagem)
      await recarregar()
    } catch (ex) {
      setErro(ex.message)
      await recarregar()
    } finally {
      setProcessando(null)
    }
  }

  const matricular = (oferta, tipo) =>
    executar(
      oferta.id + tipo,
      () => api.post('/aluno/matriculas', { ofertaId: oferta.id, tipo }),
      `Matrícula em ${oferta.nome} realizada. O sistema de cobranças foi notificado.`,
    )

  const cancelar = (m) => {
    if (!window.confirm(`Cancelar a matrícula em ${m.oferta?.nome}?`)) return
    executar(m.id, () => api.del(`/aluno/matriculas/${m.id}`), 'Matrícula cancelada. O sistema de cobranças foi notificado.')
  }

  return (
    <>
      <PageHeader
        titulo="Minhas matrículas"
        descricao="Escolha até 4 disciplinas obrigatórias (1ª opção) e 2 optativas (alternativas)."
      />
      <Mensagem texto={erro} onFechar={() => setErro('')} />
      <Mensagem tipo="success" texto={sucesso} onFechar={() => setSucesso('')} />

      {!curriculo ? (
        <div className="alert alert-info">Não há período de matrículas aberto no momento.</div>
      ) : (
        <>
          <div className={`alert ${painel.periodoAberto ? 'alert-success' : 'alert-info'}`}>
            Semestre <strong>{curriculo.semestre}</strong> · matrículas de {formatarData(curriculo.inicioMatriculas)} a{' '}
            {formatarData(curriculo.fimMatriculas)}
            {!painel.periodoAberto && ' — fora do período: matrículas e cancelamentos indisponíveis.'}
          </div>

          <div className="stats">
            <div className="stat">
              <span>Obrigatórias</span>
              <strong>{obrigatorias} / {painel.maximoObrigatorias}</strong>
            </div>
            <div className="stat">
              <span>Optativas</span>
              <strong>{optativas} / {painel.maximoOptativas}</strong>
            </div>
            <div className="stat">
              <span>Valor do semestre</span>
              <strong>{formatarMoeda(total)}</strong>
            </div>
          </div>

          <section className="card table-wrapper">
            <h2>Disciplinas em que estou matriculado</h2>
            <table>
              <thead>
                <tr>
                  <th>Disciplina</th>
                  <th>Professor</th>
                  <th>Tipo</th>
                  <th>Situação</th>
                  <th>Data</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {doSemestre.length ? (
                  doSemestre.map((m) => (
                    <tr key={m.id}>
                      <td><strong>{m.oferta?.codigo}</strong> — {m.oferta?.nome}</td>
                      <td>{m.oferta?.professorNome}</td>
                      <td><StatusBadge status={m.tipo} /></td>
                      <td><StatusBadge status={m.status} /></td>
                      <td>{formatarDataHora(m.dataMatricula)}</td>
                      <td className="acoes">
                        {m.status === 'ATIVA' && painel.periodoAberto && (
                          <button className="btn btn-danger btn-sm" disabled={processando === m.id} onClick={() => cancelar(m)}>
                            Cancelar
                          </button>
                        )}
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr><td colSpan="6" className="vazio">Você ainda não se matriculou neste semestre.</td></tr>
                )}
              </tbody>
            </table>
          </section>

          <section className="card table-wrapper">
            <h2>Disciplinas ofertadas</h2>
            <table>
              <thead>
                <tr>
                  <th>Disciplina</th>
                  <th>Professor</th>
                  <th>Créditos</th>
                  <th>Valor</th>
                  <th>Vagas</th>
                  <th>Situação</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {painel.ofertas.map((o) => {
                  const jaMatriculado = matriculadoEm.has(o.id)
                  const podeMatricular = painel.periodoAberto && o.status === 'ABERTA' && !jaMatriculado
                  return (
                    <tr key={o.id}>
                      <td><strong>{o.codigo}</strong> — {o.nome}</td>
                      <td>{o.professorNome}</td>
                      <td>{o.creditos}</td>
                      <td>{formatarMoeda(o.valor)}</td>
                      <td>{o.vagasDisponiveis} de {o.maximoAlunos}</td>
                      <td><StatusBadge status={o.status} /></td>
                      <td className="acoes">
                        {jaMatriculado ? (
                          <span className="muted">Matriculado</span>
                        ) : (
                          <>
                            <button
                              className="btn btn-sm"
                              disabled={!podeMatricular || obrigatorias >= painel.maximoObrigatorias || processando !== null}
                              onClick={() => matricular(o, 'OBRIGATORIA')}
                            >
                              Obrigatória
                            </button>
                            <button
                              className="btn btn-secondary btn-sm"
                              disabled={!podeMatricular || optativas >= painel.maximoOptativas || processando !== null}
                              onClick={() => matricular(o, 'OPTATIVA')}
                            >
                              Optativa
                            </button>
                          </>
                        )}
                      </td>
                    </tr>
                  )
                })}
              </tbody>
            </table>
          </section>
        </>
      )}

      {historico.length > 0 && (
        <section className="card table-wrapper">
          <h2>Histórico de outros semestres</h2>
          <table>
            <thead>
              <tr>
                <th>Semestre</th>
                <th>Disciplina</th>
                <th>Tipo</th>
                <th>Situação</th>
              </tr>
            </thead>
            <tbody>
              {historico.map((m) => (
                <tr key={m.id}>
                  <td>{m.semestre}</td>
                  <td><strong>{m.oferta?.codigo}</strong> — {m.oferta?.nome}</td>
                  <td><StatusBadge status={m.tipo} /></td>
                  <td><StatusBadge status={m.status} /></td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      )}
    </>
  )
}
