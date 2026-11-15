import PageHeader from '../components/PageHeader.jsx'
import { useAuth } from '../hooks/useAuth.js'
import { NOMES_PERFIL } from '../utils/formatacao.js'

export default function BoasVindasPage() {
  const { usuario } = useAuth()
  return (
    <>
      <PageHeader titulo={`Olá, ${usuario.nome}!`} descricao={`Você entrou como ${NOMES_PERFIL[usuario.perfil]}.`} />
      <div className="card">
        <p className="muted">Use o menu ao lado para acessar as funcionalidades do seu perfil.</p>
      </div>
    </>
  )
}
