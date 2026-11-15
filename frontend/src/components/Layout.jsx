import { NavLink, Outlet } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth.js'
import { MENU } from '../navegacao.js'
import { NOMES_PERFIL } from '../utils/formatacao.js'

export default function Layout() {
  const { usuario, logout } = useAuth()
  const itens = MENU[usuario.perfil] ?? []

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div className="brand">
          <img src="/favicon.svg" alt="" width="32" height="32" />
          <span>Sistema de Matrículas</span>
        </div>
        <nav aria-label="Menu principal">
          {itens.map((item) => (
            <NavLink key={item.para} to={item.para} end={item.exato} className="nav-link">
              {item.rotulo}
            </NavLink>
          ))}
        </nav>
        <div className="user-box">
          <div>
            <strong>{usuario.nome}</strong>
            <span className="muted">{NOMES_PERFIL[usuario.perfil]} · {usuario.login}</span>
          </div>
          <button className="btn btn-secondary btn-sm" onClick={logout}>
            Sair
          </button>
        </div>
      </aside>
      <main className="content">
        <Outlet />
      </main>
    </div>
  )
}
