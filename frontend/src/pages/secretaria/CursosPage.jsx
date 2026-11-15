import { useState } from 'react'
import Mensagem from '../../components/Mensagem.jsx'
import PageHeader from '../../components/PageHeader.jsx'
import { useCarregar } from '../../hooks/useCarregar.js'
import { api } from '../../services/api.js'

const VAZIO = { nome: '', creditos: '', disciplinaIds: [] }

export default function CursosPage() {
  const { dados, erro: erroLista, carregando, recarregar } = useCarregar(async () => {
    const [cursos, disciplinas] = await Promise.all([api.get('/cursos'), api.get('/disciplinas')])
    return { cursos, disciplinas }
  })
  const [form, setForm] = useState(VAZIO)
  const [editandoId, setEditandoId] = useState(null)
  const [erro, setErro] = useState('')
  const [sucesso, setSucesso] = useState('')

  const disciplinas = dados?.disciplinas ?? []
  const nomeDisciplina = (id) => disciplinas.find((d) => d.id === id)?.codigo ?? '?'

  function alternarDisciplina(id) {
    const selecionadas = form.disciplinaIds.includes(id)
      ? form.disciplinaIds.filter((d) => d !== id)
      : [...form.disciplinaIds, id]
    setForm({ ...form, disciplinaIds: selecionadas })
  }

  function editar(curso) {
    setEditandoId(curso.id)
    setForm({ nome: curso.nome, creditos: curso.creditos, disciplinaIds: curso.disciplinaIds })
    setErro('')
    setSucesso('')
  }

  function limpar() {
    setEditandoId(null)
    setForm(VAZIO)
  }

  async function salvar(e) {
    e.preventDefault()
    setErro('')
    setSucesso('')
    const corpo = { ...form, creditos: Number(form.creditos) }
    try {
      if (editandoId) await api.put(`/cursos/${editandoId}`, corpo)
      else await api.post('/cursos', corpo)
      setSucesso(editandoId ? 'Curso atualizado.' : 'Curso cadastrado.')
      limpar()
      recarregar()
    } catch (ex) {
      setErro(ex.message)
    }
  }

  async function remover(curso) {
    if (!window.confirm(`Remover o curso ${curso.nome}?`)) return
    setErro('')
    setSucesso('')
    try {
      await api.del(`/cursos/${curso.id}`)
      setSucesso('Curso removido.')
      recarregar()
    } catch (ex) {
      setErro(ex.message)
    }
  }

  return (
    <>
      <PageHeader titulo="Cursos" descricao="Cada curso tem um nome, um número de créditos e é constituído por diversas disciplinas." />
      <Mensagem texto={erro || erroLista} onFechar={() => setErro('')} />
      <Mensagem tipo="success" texto={sucesso} onFechar={() => setSucesso('')} />

      <section className="card">
        <h2>{editandoId ? 'Editar curso' : 'Novo curso'}</h2>
        <form className="form" onSubmit={salvar}>
          <div className="form-grid">
            <label>
              Nome
              <input value={form.nome} onChange={(e) => setForm({ ...form, nome: e.target.value })} required />
            </label>
            <label>
              Créditos
              <input
                type="number"
                min="1"
                value={form.creditos}
                onChange={(e) => setForm({ ...form, creditos: e.target.value })}
                required
              />
            </label>
          </div>
          <fieldset className="checklist">
            <legend>Disciplinas do curso</legend>
            {disciplinas.length === 0 && <span className="muted">Cadastre disciplinas primeiro.</span>}
            {disciplinas.map((d) => (
              <label key={d.id} className="check">
                <input
                  type="checkbox"
                  checked={form.disciplinaIds.includes(d.id)}
                  onChange={() => alternarDisciplina(d.id)}
                />
                {d.codigo} — {d.nome}
              </label>
            ))}
          </fieldset>
          <div className="page-actions">
            <button className="btn" type="submit">{editandoId ? 'Salvar' : 'Cadastrar'}</button>
            {editandoId && (
              <button className="btn btn-secondary" type="button" onClick={limpar}>
                Cancelar
              </button>
            )}
          </div>
        </form>
      </section>

      <section className="card table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Nome</th>
              <th>Créditos</th>
              <th>Disciplinas</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {carregando && !dados ? (
              <tr><td colSpan="4" className="vazio">Carregando…</td></tr>
            ) : dados?.cursos.length ? (
              dados.cursos.map((c) => (
                <tr key={c.id}>
                  <td><strong>{c.nome}</strong></td>
                  <td>{c.creditos}</td>
                  <td>{c.disciplinaIds.length ? c.disciplinaIds.map(nomeDisciplina).join(', ') : '—'}</td>
                  <td className="acoes">
                    <button className="btn btn-secondary btn-sm" onClick={() => editar(c)}>Editar</button>
                    <button className="btn btn-danger btn-sm" onClick={() => remover(c)}>Remover</button>
                  </td>
                </tr>
              ))
            ) : (
              <tr><td colSpan="4" className="vazio">Nenhum curso cadastrado.</td></tr>
            )}
          </tbody>
        </table>
      </section>
    </>
  )
}
