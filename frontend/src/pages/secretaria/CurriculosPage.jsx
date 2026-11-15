import { useState } from 'react'
import Mensagem from '../../components/Mensagem.jsx'
import PageHeader from '../../components/PageHeader.jsx'
import StatusBadge from '../../components/StatusBadge.jsx'
import { useCarregar } from '../../hooks/useCarregar.js'
import { api } from '../../services/api.js'
import { formatarData } from '../../utils/formatacao.js'

const NOVO = { semestre: '', inicioMatriculas: '', fimMatriculas: '' }

export default function CurriculosPage() {
  const { dados: curriculos, erro: erroLista, recarregar } = useCarregar(() => api.get('/curriculos'))
  const [selecionadoId, setSelecionadoId] = useState(null)
  const [novo, setNovo] = useState(NOVO)
  const [erro, setErro] = useState('')
  const [sucesso, setSucesso] = useState('')

  const selecionado = curriculos?.find((c) => c.id === selecionadoId) ?? curriculos?.[0]

  async function executar(acao, mensagem) {
    setErro('')
    setSucesso('')
    try {
      await acao()
      setSucesso(mensagem)
      await recarregar()
      return true
    } catch (ex) {
      setErro(ex.message)
      return false
    }
  }

  async function criar(e) {
    e.preventDefault()
    let criado
    const ok = await executar(async () => {
      criado = await api.post('/curriculos', novo)
    }, `Currículo ${novo.semestre} criado. Agora oferte as disciplinas.`)
    if (ok) {
      setNovo(NOVO)
      setSelecionadoId(criado.id)
    }
  }

  return (
    <>
      <PageHeader
        titulo="Currículos semestrais"
        descricao="Gere o currículo de cada semestre, oferte as disciplinas e controle o período de matrículas."
      />
      <Mensagem texto={erro || erroLista} onFechar={() => setErro('')} />
      <Mensagem tipo="success" texto={sucesso} onFechar={() => setSucesso('')} />

      <section className="card">
        <h2>Gerar novo currículo</h2>
        <form className="form-grid" onSubmit={criar}>
          <label>
            Semestre
            <input
              value={novo.semestre}
              onChange={(e) => setNovo({ ...novo, semestre: e.target.value })}
              placeholder="2027/2"
              pattern="\d{4}/[12]"
              title="Use o formato AAAA/1 ou AAAA/2"
              required
            />
          </label>
          <label>
            Início das matrículas
            <input
              type="date"
              value={novo.inicioMatriculas}
              onChange={(e) => setNovo({ ...novo, inicioMatriculas: e.target.value })}
              required
            />
          </label>
          <label>
            Fim das matrículas
            <input
              type="date"
              value={novo.fimMatriculas}
              onChange={(e) => setNovo({ ...novo, fimMatriculas: e.target.value })}
              required
            />
          </label>
          <div>
            <button className="btn" type="submit">Gerar currículo</button>
          </div>
        </form>
      </section>

      {curriculos?.length > 0 && (
        <div className="tabs" role="tablist" aria-label="Semestres">
          {curriculos.map((c) => (
            <button
              key={c.id}
              role="tab"
              aria-selected={selecionado?.id === c.id}
              className={`tab ${selecionado?.id === c.id ? 'tab-ativa' : ''}`}
              onClick={() => setSelecionadoId(c.id)}
            >
              {c.semestre}
            </button>
          ))}
        </div>
      )}

      {selecionado && (
        <DetalheCurriculo key={selecionado.id} curriculo={selecionado} executar={executar} />
      )}
    </>
  )
}

function DetalheCurriculo({ curriculo, executar }) {
  const { dados, recarregar } = useCarregar(async () => {
    const [ofertas, disciplinas, professores] = await Promise.all([
      api.get(`/curriculos/${curriculo.id}/ofertas`),
      api.get('/disciplinas'),
      api.get('/professores'),
    ])
    return { ofertas, disciplinas, professores }
  }, curriculo.id + curriculo.status)
  const [oferta, setOferta] = useState({ disciplinaId: '', professorId: '' })
  const [datas, setDatas] = useState({ inicio: curriculo.inicioMatriculas, fim: curriculo.fimMatriculas })

  const encerrado = curriculo.status === 'MATRICULAS_ENCERRADAS'
  const ofertadas = new Set(dados?.ofertas.map((o) => o.disciplinaId))
  const disponiveis = dados?.disciplinas.filter((d) => !ofertadas.has(d.id)) ?? []

  async function ofertar(e) {
    e.preventDefault()
    const ok = await executar(() => api.post(`/curriculos/${curriculo.id}/ofertas`, oferta), 'Disciplina ofertada.')
    if (ok) {
      setOferta({ disciplinaId: '', professorId: '' })
      recarregar()
    }
  }

  async function removerOferta(o) {
    if (!window.confirm(`Retirar ${o.codigo} - ${o.nome} do currículo?`)) return
    if (await executar(() => api.del(`/curriculos/${curriculo.id}/ofertas/${o.id}`), 'Oferta removida.')) recarregar()
  }

  async function salvarDatas(e) {
    e.preventDefault()
    await executar(
      () => api.put(`/curriculos/${curriculo.id}`, {
        semestre: curriculo.semestre,
        inicioMatriculas: datas.inicio,
        fimMatriculas: datas.fim,
      }),
      'Período de matrículas atualizado.',
    )
  }

  function encerrar() {
    const mensagem =
      'Encerrar o período de matrículas? Disciplinas com menos de 3 alunos serão canceladas e não será possível reabrir.'
    if (!window.confirm(mensagem)) return
    executar(() => api.post(`/curriculos/${curriculo.id}/encerrar`), 'Período encerrado. Disciplinas avaliadas.')
  }

  const ativas = dados?.ofertas.filter((o) => o.status === 'ATIVA').length ?? 0
  const canceladas = dados?.ofertas.filter((o) => o.status === 'CANCELADA').length ?? 0

  return (
    <section className="card">
      <div className="section-title">
        <div>
          <h2>Currículo {curriculo.semestre}</h2>
          <p className="muted">
            Matrículas de {formatarData(curriculo.inicioMatriculas)} a {formatarData(curriculo.fimMatriculas)}
          </p>
        </div>
        <div className="page-actions">
          <StatusBadge status={curriculo.status} />
          {curriculo.status === 'PLANEJADO' && (
            <button
              className="btn"
              onClick={() => executar(() => api.post(`/curriculos/${curriculo.id}/abrir`), 'Período de matrículas aberto.')}
            >
              Abrir matrículas
            </button>
          )}
          {curriculo.status === 'MATRICULAS_ABERTAS' && (
            <button className="btn btn-danger" onClick={encerrar}>Encerrar matrículas</button>
          )}
        </div>
      </div>

      {encerrado ? (
        <div className="alert alert-info">
          Período encerrado: {ativas} disciplina(s) ativa(s) para o semestre e {canceladas} cancelada(s) por terem
          menos de 3 alunos.
        </div>
      ) : (
        <div className="split">
          <form className="form-grid" onSubmit={salvarDatas}>
            <label>
              Início
              <input type="date" value={datas.inicio} onChange={(e) => setDatas({ ...datas, inicio: e.target.value })} required />
            </label>
            <label>
              Fim
              <input type="date" value={datas.fim} onChange={(e) => setDatas({ ...datas, fim: e.target.value })} required />
            </label>
            <div>
              <button className="btn btn-secondary" type="submit">Salvar período</button>
            </div>
          </form>
          <form className="form-grid" onSubmit={ofertar}>
            <label>
              Disciplina
              <select value={oferta.disciplinaId} onChange={(e) => setOferta({ ...oferta, disciplinaId: e.target.value })} required>
                <option value="">Selecione…</option>
                {disponiveis.map((d) => (
                  <option key={d.id} value={d.id}>{d.codigo} — {d.nome}</option>
                ))}
              </select>
            </label>
            <label>
              Professor
              <select value={oferta.professorId} onChange={(e) => setOferta({ ...oferta, professorId: e.target.value })} required>
                <option value="">Selecione…</option>
                {dados?.professores.map((p) => (
                  <option key={p.id} value={p.id}>{p.nome}</option>
                ))}
              </select>
            </label>
            <div>
              <button className="btn" type="submit">Ofertar disciplina</button>
            </div>
          </form>
        </div>
      )}

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Disciplina</th>
              <th>Professor</th>
              <th>Créditos</th>
              <th>Inscritos</th>
              <th>Situação</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {dados?.ofertas.length ? (
              dados.ofertas.map((o) => (
                <tr key={o.id}>
                  <td><strong>{o.codigo}</strong> — {o.nome}</td>
                  <td>{o.professorNome}</td>
                  <td>{o.creditos}</td>
                  <td>{o.inscritos} / {o.maximoAlunos}</td>
                  <td><StatusBadge status={o.status} /></td>
                  <td className="acoes">
                    {!encerrado && o.inscritos === 0 && (
                      <button className="btn btn-danger btn-sm" onClick={() => removerOferta(o)}>Retirar</button>
                    )}
                  </td>
                </tr>
              ))
            ) : (
              <tr><td colSpan="6" className="vazio">Nenhuma disciplina ofertada neste currículo.</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </section>
  )
}
