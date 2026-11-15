const moeda = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' })

export const formatarMoeda = (valor) => moeda.format(Number(valor ?? 0))

export function formatarData(iso) {
  if (!iso) return '—'
  const [ano, mes, dia] = iso.slice(0, 10).split('-')
  return `${dia}/${mes}/${ano}`
}

export function formatarDataHora(iso) {
  if (!iso) return '—'
  return new Date(iso).toLocaleString('pt-BR', { dateStyle: 'short', timeStyle: 'short' })
}

export const NOMES_PERFIL = { ALUNO: 'Aluno', PROFESSOR: 'Professor', SECRETARIA: 'Secretaria' }

export const ROTULOS_STATUS = {
  ABERTA: 'Inscrições abertas',
  LOTADA: 'Lotada (60 alunos)',
  ATIVA: 'Ativa',
  CANCELADA: 'Cancelada',
  DISCIPLINA_CANCELADA: 'Disciplina cancelada',
  PLANEJADO: 'Planejado',
  MATRICULAS_ABERTAS: 'Matrículas abertas',
  MATRICULAS_ENCERRADAS: 'Matrículas encerradas',
  OBRIGATORIA: 'Obrigatória',
  OPTATIVA: 'Optativa',
  INSCRICAO: 'Inscrição',
  CANCELAMENTO: 'Cancelamento',
  ENCERRAMENTO_PERIODO: 'Encerramento do período',
}
