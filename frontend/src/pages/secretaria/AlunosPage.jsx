import { useState } from 'react'
import Mensagem from '../../components/Mensagem.jsx'
import PageHeader from '../../components/PageHeader.jsx'
import { useCarregar } from '../../hooks/useCarregar.js'
import { api } from '../../services/api.js'

const VAZIO = { nome: '', matricula: '', cursoId: '', login: '', senha: '' }

export default function AlunosPage() {
  const { dados, erro: erroLista, carregando, recarregar } = useCarregar(async () => {
    const [alunos, cursos] = await Promise.all([api.get('/alunos'), api.get('/cursos')])
    return { alunos, cursos }
  })
  const [form, setForm] = useState(VAZIO)
  const [editandoId, setEditandoId] = useState(null)
  const [erro, setErro] = useState('')
  const [sucesso, setSucesso] = useState('')

  const cursos = dados?.cursos ?? []
  const nomeCurso = (id) => cursos.find((c) => c.id === id)?.nome ?? '—'
  const atualizar = (campo) => (e) => setForm({ ...form, [campo]: e.target.value })

  function editar(a) {
    setEditandoId(a.id)
    setForm({ nome: a.nome, matricula: a.matricula, cursoId: a.cursoId, login: a.login, senha: '' })
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
    const corpo = { ...form, senha: form.senha || null }
    try {
      if (editandoId) await api.put(`/alunos/${editandoId}`, corpo)
      else await api.post('/alunos', corpo)
      setSucesso(editandoId ? 'Aluno atualizado.' : 'Aluno cadastrado.')
      limpar()
      recarregar()
    } catch (ex) {
      setErro(ex.message)
    }
  }

  async function remover(a) {
    if (!window.confirm(`Remover o aluno ${a.nome}?`)) return
    setErro('')
    setSucesso('')
    try {
      await api.del(`/alunos/${a.id}`)
      setSucesso('Aluno removido.')
      recarregar()
    } catch (ex) {
      setErro(ex.message)
    }
  }

  return (
    <>
      <PageHeader titulo="Alunos" descricao="Alunos cadastrados podem se matricular durante o período de matrículas." />
      <Mensagem texto={erro || erroLista} onFechar={() => setErro('')} />
      <Mensagem tipo="success" texto={sucesso} onFechar={() => setSucesso('')} />

      <section className="card">
        <h2>{editandoId ? 'Editar aluno' : 'Novo aluno'}</h2>
        <form className="form-grid" onSubmit={salvar}>
          <label>
            Nome
            <input value={form.nome} onChange={atualizar('nome')} required />
          </label>
          <label>
            Nº de matrícula
            <input value={form.matricula} onChange={atualizar('matricula')} required />
          </label>
          <label>
            Curso
            <select value={form.cursoId} onChange={atualizar('cursoId')} required>
              <option value="">Selecione…</option>
              {cursos.map((c) => (
                <option key={c.id} value={c.id}>{c.nome}</option>
              ))}
            </select>
          </label>
          <label>
            Login
            <input value={form.login} onChange={atualizar('login')} autoComplete="off" required />
          </label>
          <label>
            {editandoId ? 'Nova senha (opcional)' : 'Senha'}
            <input
              type="password"
              value={form.senha}
              onChange={atualizar('senha')}
              minLength={6}
              autoComplete="new-password"
              required={!editandoId}
            />
          </label>
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
              <th>Matrícula</th>
              <th>Nome</th>
              <th>Curso</th>
              <th>Login</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {carregando && !dados ? (
              <tr><td colSpan="5" className="vazio">Carregando…</td></tr>
            ) : dados?.alunos.length ? (
              dados.alunos.map((a) => (
                <tr key={a.id}>
                  <td>{a.matricula}</td>
                  <td><strong>{a.nome}</strong></td>
                  <td>{nomeCurso(a.cursoId)}</td>
                  <td>{a.login}</td>
                  <td className="acoes">
                    <button className="btn btn-secondary btn-sm" onClick={() => editar(a)}>Editar</button>
                    <button className="btn btn-danger btn-sm" onClick={() => remover(a)}>Remover</button>
                  </td>
                </tr>
              ))
            ) : (
              <tr><td colSpan="5" className="vazio">Nenhum aluno cadastrado.</td></tr>
            )}
          </tbody>
        </table>
      </section>
    </>
  )
}
