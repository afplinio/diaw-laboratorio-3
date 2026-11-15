import { useState } from 'react'
import Mensagem from '../../components/Mensagem.jsx'
import PageHeader from '../../components/PageHeader.jsx'
import { useCarregar } from '../../hooks/useCarregar.js'
import { api } from '../../services/api.js'

const VAZIO = { nome: '', login: '', senha: '', departamento: '' }

export default function ProfessoresPage() {
  const { dados: professores, erro: erroLista, carregando, recarregar } = useCarregar(() => api.get('/professores'))
  const [form, setForm] = useState(VAZIO)
  const [editandoId, setEditandoId] = useState(null)
  const [erro, setErro] = useState('')
  const [sucesso, setSucesso] = useState('')

  const atualizar = (campo) => (e) => setForm({ ...form, [campo]: e.target.value })

  function editar(p) {
    setEditandoId(p.id)
    setForm({ nome: p.nome, login: p.login, senha: '', departamento: p.departamento ?? '' })
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
      if (editandoId) await api.put(`/professores/${editandoId}`, corpo)
      else await api.post('/professores', corpo)
      setSucesso(editandoId ? 'Professor atualizado.' : 'Professor cadastrado.')
      limpar()
      recarregar()
    } catch (ex) {
      setErro(ex.message)
    }
  }

  async function remover(p) {
    if (!window.confirm(`Remover o professor ${p.nome}?`)) return
    setErro('')
    setSucesso('')
    try {
      await api.del(`/professores/${p.id}`)
      setSucesso('Professor removido.')
      recarregar()
    } catch (ex) {
      setErro(ex.message)
    }
  }

  return (
    <>
      <PageHeader titulo="Professores" descricao="Professores acessam o sistema para ver os alunos de cada disciplina." />
      <Mensagem texto={erro || erroLista} onFechar={() => setErro('')} />
      <Mensagem tipo="success" texto={sucesso} onFechar={() => setSucesso('')} />

      <section className="card">
        <h2>{editandoId ? 'Editar professor' : 'Novo professor'}</h2>
        <form className="form-grid" onSubmit={salvar}>
          <label>
            Nome
            <input value={form.nome} onChange={atualizar('nome')} required />
          </label>
          <label>
            Departamento
            <input value={form.departamento} onChange={atualizar('departamento')} />
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
              <th>Nome</th>
              <th>Departamento</th>
              <th>Login</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {carregando && !professores ? (
              <tr><td colSpan="4" className="vazio">Carregando…</td></tr>
            ) : professores?.length ? (
              professores.map((p) => (
                <tr key={p.id}>
                  <td><strong>{p.nome}</strong></td>
                  <td>{p.departamento || '—'}</td>
                  <td>{p.login}</td>
                  <td className="acoes">
                    <button className="btn btn-secondary btn-sm" onClick={() => editar(p)}>Editar</button>
                    <button className="btn btn-danger btn-sm" onClick={() => remover(p)}>Remover</button>
                  </td>
                </tr>
              ))
            ) : (
              <tr><td colSpan="4" className="vazio">Nenhum professor cadastrado.</td></tr>
            )}
          </tbody>
        </table>
      </section>
    </>
  )
}
