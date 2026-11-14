import { Navigate, useLocation } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth.js'

/** Exige login. Se perfis for informado, restringe a rota a esses perfis. */
export default function ProtectedRoute({ perfis, children }) {
  const { usuario, carregando } = useAuth()
  const location = useLocation()

  if (carregando) return <p className="centered muted">Carregando…</p>
  if (!usuario) return <Navigate to="/login" replace state={{ from: location }} />
  if (perfis && !perfis.includes(usuario.perfil)) return <Navigate to="/" replace />
  return children
}
