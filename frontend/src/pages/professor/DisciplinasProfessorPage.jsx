import { useState } from 'react'
import Mensagem from '../../components/Mensagem.jsx'
import PageHeader from '../../components/PageHeader.jsx'
import StatusBadge from '../../components/StatusBadge.jsx'
import { useCarregar } from '../../hooks/useCarregar.js'
import { api } from '../../services/api.js'

export default function DisciplinasProfessorPage() {
  const { dados: disciplinas, erro, carregando } = useCarregar(() => api.get('/professor/ofertas'))
  const [selecionadaId, setSelecionadaId] = useState(null)

  const selecionada = disciplinas?.find((d) => d.oferta.id === selecionadaId)

  return (
    <>
      <PageHeader titulo="Minhas disciplinas" descricao="Veja os alunos matriculados em cada disciplina que você leciona." />
      <Mensagem texto={erro} />

      <section className="card table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Semestre</th>
              <th>Disciplina</th>
              <th>Matriculados</th>
              <th>Situação</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {carregando && !disciplinas ? (
              <tr><td colSpan="5" className="vazio">Carregando…</td></tr>
            ) : disciplinas?.length ? (
              disciplinas.map(({ semestre, oferta }) => (
                <tr key={oferta.id} className={oferta.id === selecionadaId ? 'linha-selecionada' : undefined}>
                  <td>{semestre}</td>
                  <td><strong>{oferta.codigo}</strong> — {oferta.nome}</td>
                  <td>{oferta.inscritos} / {oferta.maximoAlunos}</td>
                  <td><StatusBadge status={oferta.status} /></td>
                  <td className="acoes">
                    <button className="btn btn-secondary btn-sm" onClick={() => setSelecionadaId(oferta.id)}>
                      Ver alunos
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr><td colSpan="5" className="vazio">Você não leciona nenhuma disciplina ofertada.</td></tr>
            )}
          </tbody>
        </table>
      </section>

      {selecionada && <AlunosDaDisciplina key={selecionada.oferta.id} disciplina={selecionada} />}
    </>
  )
}

function AlunosDaDisciplina({ disciplina }) {
  const { oferta, semestre } = disciplina
  const { dados: alunos, erro, carregando } = useCarregar(() => api.get(`/professor/ofertas/${oferta.id}/alunos`))

  return (
    <section className="card table-wrapper">
      <h2>
        Alunos matriculados em {oferta.codigo} — {oferta.nome} ({semestre})
      </h2>
      <Mensagem texto={erro} />
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Matrícula</th>
            <th>Nome</th>
            <th>Curso</th>
          </tr>
        </thead>
        <tbody>
          {carregando && !alunos ? (
            <tr><td colSpan="4" className="vazio">Carregando…</td></tr>
          ) : alunos?.length ? (
            alunos.map((a, i) => (
              <tr key={a.id}>
                <td>{i + 1}</td>
                <td>{a.matricula}</td>
                <td><strong>{a.nome}</strong></td>
                <td>{a.curso}</td>
              </tr>
            ))
          ) : (
            <tr><td colSpan="4" className="vazio">Nenhum aluno matriculado nesta disciplina.</td></tr>
          )}
        </tbody>
      </table>
    </section>
  )
}
