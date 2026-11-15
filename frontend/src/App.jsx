import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import Layout from './components/Layout.jsx'
import ProtectedRoute from './components/ProtectedRoute.jsx'
import { AuthProvider } from './contexts/AuthContext.jsx'
import { useAuth } from './hooks/useAuth.js'
import { PAGINA_INICIAL } from './navegacao.js'
import BoasVindasPage from './pages/BoasVindasPage.jsx'
import MatriculaPage from './pages/aluno/MatriculaPage.jsx'
import LoginPage from './pages/LoginPage.jsx'
import AlunosPage from './pages/secretaria/AlunosPage.jsx'
import CobrancasPage from './pages/secretaria/CobrancasPage.jsx'
import CurriculosPage from './pages/secretaria/CurriculosPage.jsx'
import CursosPage from './pages/secretaria/CursosPage.jsx'
import DisciplinasPage from './pages/secretaria/DisciplinasPage.jsx'
import ProfessoresPage from './pages/secretaria/ProfessoresPage.jsx'

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
            <Route path="secretaria" element={<Navigate to="/secretaria/curriculos" replace />} />
            <Route path="secretaria/curriculos" element={somente('SECRETARIA', <CurriculosPage />)} />
            <Route path="secretaria/cursos" element={somente('SECRETARIA', <CursosPage />)} />
            <Route path="secretaria/disciplinas" element={somente('SECRETARIA', <DisciplinasPage />)} />
            <Route path="secretaria/professores" element={somente('SECRETARIA', <ProfessoresPage />)} />
            <Route path="secretaria/alunos" element={somente('SECRETARIA', <AlunosPage />)} />
            <Route path="secretaria/cobrancas" element={somente('SECRETARIA', <CobrancasPage />)} />
            <Route path="aluno" element={somente('ALUNO', <MatriculaPage />)} />
            <Route path="professor" element={somente('PROFESSOR', <BoasVindasPage />)} />
          </Route>
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}
