import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import ProtectedRoute from './components/ProtectedRoute.jsx'
import { AuthProvider } from './contexts/AuthContext.jsx'
import { useAuth } from './hooks/useAuth.js'
import LoginPage from './pages/LoginPage.jsx'

const NOMES_PERFIL = { ALUNO: 'Aluno', PROFESSOR: 'Professor', SECRETARIA: 'Secretaria' }

function Inicio() {
  const { usuario, logout } = useAuth()
  return (
    <main className="centered">
      <div className="card">
        <h1>Olá, {usuario.nome}!</h1>
        <p className="muted">Perfil: {NOMES_PERFIL[usuario.perfil]}</p>
        <button className="btn btn-secondary" onClick={logout}>Sair</button>
      </div>
    </main>
  )
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <Inicio />
              </ProtectedRoute>
            }
          />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}
