import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import Layout from './components/Layout.jsx'
import ProtectedRoute from './components/ProtectedRoute.jsx'
import { AuthProvider } from './contexts/AuthContext.jsx'
import { useAuth } from './hooks/useAuth.js'
import { PAGINA_INICIAL } from './navegacao.js'
import BoasVindasPage from './pages/BoasVindasPage.jsx'
import LoginPage from './pages/LoginPage.jsx'

/** Redireciona o usuário para a área do seu perfil. */
function Inicio() {
  const { usuario } = useAuth()
  return <Navigate to={PAGINA_INICIAL[usuario.perfil]} replace />
}

const somente = (perfil, elemento) => <ProtectedRoute perfis={[perfil]}>{elemento}</ProtectedRoute>

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route
            element={
              <ProtectedRoute>
                <Layout />
              </ProtectedRoute>
            }
          >
            <Route index element={<Inicio />} />
            <Route path="secretaria" element={somente('SECRETARIA', <BoasVindasPage />)} />
            <Route path="aluno" element={somente('ALUNO', <BoasVindasPage />)} />
            <Route path="professor" element={somente('PROFESSOR', <BoasVindasPage />)} />
          </Route>
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}
