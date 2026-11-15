import Mensagem from '../../components/Mensagem.jsx'
import PageHeader from '../../components/PageHeader.jsx'
import { useCarregar } from '../../hooks/useCarregar.js'
import { api } from '../../services/api.js'
import { formatarDataHora, formatarMoeda, ROTULOS_STATUS } from '../../utils/formatacao.js'

export default function CobrancasPage() {
  const { dados: notificacoes, erro, carregando, recarregar } = useCarregar(() => api.get('/cobrancas'))

  return (
    <>
      <PageHeader
        titulo="Sistema de cobranças"
        descricao="Notificações enviadas após cada inscrição para que o aluno seja cobrado pelas disciplinas do semestre."
      >
        <button className="btn btn-secondary" onClick={recarregar}>Atualizar</button>
      </PageHeader>
      <Mensagem texto={erro} />

      <section className="card table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Data</th>
              <th>Aluno</th>
              <th>Semestre</th>
              <th>Motivo</th>
              <th>Disciplinas cobradas</th>
              <th>Total</th>
            </tr>
          </thead>
          <tbody>
            {carregando && !notificacoes ? (
              <tr><td colSpan="6" className="vazio">Carregando…</td></tr>
            ) : notificacoes?.length ? (
              notificacoes.map((n) => (
                <tr key={n.id}>
                  <td>{formatarDataHora(n.dataNotificacao)}</td>
                  <td>
                    <strong>{n.alunoNome}</strong>
                    <div className="muted">{n.alunoMatricula}</div>
                  </td>
                  <td>{n.semestre}</td>
                  <td>{ROTULOS_STATUS[n.motivo] ?? n.motivo}</td>
                  <td>{n.itens.length ? n.itens.map((i) => i.disciplinaCodigo).join(', ') : 'Nenhuma'}</td>
                  <td><strong>{formatarMoeda(n.valorTotal)}</strong></td>
                </tr>
              ))
            ) : (
              <tr><td colSpan="6" className="vazio">Nenhuma notificação enviada ainda.</td></tr>
            )}
          </tbody>
        </table>
      </section>
    </>
  )
}
