import { ROTULOS_STATUS } from '../utils/formatacao.js'

const TONS = {
  ABERTA: 'info',
  MATRICULAS_ABERTAS: 'info',
  ATIVA: 'success',
  LOTADA: 'warning',
  PLANEJADO: 'neutral',
  MATRICULAS_ENCERRADAS: 'neutral',
  CANCELADA: 'danger',
  DISCIPLINA_CANCELADA: 'danger',
  OBRIGATORIA: 'primary',
  OPTATIVA: 'neutral',
}

export default function StatusBadge({ status }) {
  return <span className={`badge badge-${TONS[status] ?? 'neutral'}`}>{ROTULOS_STATUS[status] ?? status}</span>
}
