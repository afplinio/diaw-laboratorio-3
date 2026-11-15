/** Itens do menu lateral por perfil de usuário. */
export const MENU = {
  SECRETARIA: [
    { para: '/secretaria/curriculos', rotulo: 'Currículos e matrículas' },
    { para: '/secretaria/cursos', rotulo: 'Cursos' },
    { para: '/secretaria/disciplinas', rotulo: 'Disciplinas' },
    { para: '/secretaria/professores', rotulo: 'Professores' },
    { para: '/secretaria/alunos', rotulo: 'Alunos' },
    { para: '/secretaria/cobrancas', rotulo: 'Cobranças' },
  ],
  ALUNO: [],
  PROFESSOR: [],
}

export const PAGINA_INICIAL = {
  SECRETARIA: '/secretaria/curriculos',
  ALUNO: '/aluno',
  PROFESSOR: '/professor',
}
