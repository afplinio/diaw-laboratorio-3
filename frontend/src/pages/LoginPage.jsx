import { useState } from 'react'
import { Navigate } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth.js'

export default function LoginPage() {
  const { usuario, login } = useAuth()
  const [form, setForm] = useState({ login: '', senha: '' })
  const [erro, setErro] = useState('')
  const [enviando, setEnviando] = useState(false)

  if (usuario) return <Navigate to="/" replace />

  async function handleSubmit(event) {
    event.preventDefault()
    setErro('')
    setEnviando(true)
    try {
      await login(form.login, form.senha)
    } catch (e) {
      setErro(e.status === 401 ? 'Usuário ou senha inválidos.' : e.message)
    } finally {
      setEnviando(false)
    }
  }

  const atualizar = (campo) => (event) => setForm({ ...form, [campo]: event.target.value })

  return (
    <main className="login-page">
      <section className="card login-card">
        <div className="login-brand">
          <img src="/favicon.svg" alt="" width="44" height="44" />
          <div>
            <h1>Sistema de Matrículas</h1>
            <p className="muted">Entre com seu usuário e senha</p>
          </div>
        </div>

        {erro && <div className="alert alert-error" role="alert">{erro}</div>}

        <form className="form" onSubmit={handleSubmit}>
          <label>
            Usuário
            <input value={form.login} onChange={atualizar('login')} autoComplete="username" autoFocus required />
          </label>
          <label>
            Senha
            <input
              type="password"
              value={form.senha}
              onChange={atualizar('senha')}
              autoComplete="current-password"
              required
            />
          </label>
          <button className="btn" type="submit" disabled={enviando}>
            {enviando ? 'Entrando…' : 'Entrar'}
          </button>
        </form>
      </section>
    </main>
  )
}
