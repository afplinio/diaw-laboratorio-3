package br.pucminas.matriculas.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.pucminas.matriculas.model.Aluno;
import br.pucminas.matriculas.model.Curriculo;
import br.pucminas.matriculas.model.Curso;
import br.pucminas.matriculas.model.Disciplina;
import br.pucminas.matriculas.model.Oferta;
import br.pucminas.matriculas.model.Professor;
import br.pucminas.matriculas.model.Secretaria;
import br.pucminas.matriculas.model.StatusCurriculo;
import br.pucminas.matriculas.repository.AlunoRepository;
import br.pucminas.matriculas.repository.CurriculoRepository;
import br.pucminas.matriculas.repository.CursoRepository;
import br.pucminas.matriculas.repository.DisciplinaRepository;
import br.pucminas.matriculas.repository.OfertaRepository;
import br.pucminas.matriculas.repository.ProfessorRepository;
import br.pucminas.matriculas.repository.SecretariaRepository;

/**
 * Popula o banco com dados de demonstração na primeira execução (quando ainda não existe
 * nenhum usuário da secretaria). Pode ser desligado com APP_SEED_ENABLED=false.
 */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final SecretariaRepository secretariaRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final CurriculoRepository curriculoRepository;
    private final OfertaRepository ofertaRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(SecretariaRepository secretariaRepository, ProfessorRepository professorRepository,
            AlunoRepository alunoRepository, CursoRepository cursoRepository,
            DisciplinaRepository disciplinaRepository, CurriculoRepository curriculoRepository,
            OfertaRepository ofertaRepository, PasswordEncoder passwordEncoder) {
        this.secretariaRepository = secretariaRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.curriculoRepository = curriculoRepository;
        this.ofertaRepository = ofertaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (secretariaRepository.count() > 0) {
            return;
        }
        log.info("Banco vazio: criando dados de demonstração do Sistema de Matrículas");

        secretariaRepository.save(new Secretaria("Secretaria Acadêmica", "secretaria", senha("secretaria123"),
                "Registro Acadêmico"));

        Professor ana = professorRepository.save(
                new Professor("Ana Souza", "ana.souza", senha("professor123"), "Ciência da Computação"));
        Professor carlos = professorRepository.save(
                new Professor("Carlos Lima", "carlos.lima", senha("professor123"), "Engenharia de Software"));

        List<Disciplina> disciplinas = disciplinaRepository.saveAll(List.of(
                new Disciplina("ES101", "Algoritmos e Estruturas de Dados", 4, new BigDecimal("850.00")),
                new Disciplina("ES102", "Programação Orientada a Objetos", 4, new BigDecimal("850.00")),
                new Disciplina("ES103", "Banco de Dados", 4, new BigDecimal("780.00")),
                new Disciplina("ES104", "Engenharia de Requisitos", 2, new BigDecimal("520.00")),
                new Disciplina("ES105", "Desenvolvimento Web", 4, new BigDecimal("850.00")),
                new Disciplina("ES106", "Arquitetura de Software", 4, new BigDecimal("780.00")),
                new Disciplina("ES201", "Libras", 2, new BigDecimal("400.00")),
                new Disciplina("ES202", "Empreendedorismo", 2, new BigDecimal("400.00"))));

        Curso engenharia = new Curso("Engenharia de Software", 3200);
        engenharia.setDisciplinaIds(disciplinas.stream().map(Disciplina::getId).toList());
        engenharia = cursoRepository.save(engenharia);
        Curso computacao = new Curso("Ciência da Computação", 3000);
        computacao.setDisciplinaIds(disciplinas.subList(0, 3).stream().map(Disciplina::getId).toList());
        cursoRepository.save(computacao);

        List<Aluno> alunos = new ArrayList<>();
        String[] nomes = { "Bruna Alves", "Diego Martins", "Elisa Rocha", "Felipe Costa", "Gabriela Dias" };
        for (int i = 0; i < nomes.length; i++) {
            String login = "aluno" + (i + 1);
            alunos.add(new Aluno(nomes[i], login, senha("aluno123"), "2026" + String.format("%04d", i + 1),
                    engenharia.getId()));
        }
        alunoRepository.saveAll(alunos);

        LocalDate hoje = LocalDate.now();
        Curriculo curriculo = new Curriculo(proximoSemestre(hoje), hoje.minusDays(1), hoje.plusDays(30));
        curriculo.setStatus(StatusCurriculo.MATRICULAS_ABERTAS);
        curriculo = curriculoRepository.save(curriculo);
        for (int i = 0; i < disciplinas.size(); i++) {
            Professor professor = i % 2 == 0 ? ana : carlos;
            ofertaRepository.save(new Oferta(curriculo.getId(), disciplinas.get(i).getId(), professor.getId()));
        }
        log.info("Dados de demonstração criados. Currículo {} com matrículas abertas.", curriculo.getSemestre());
    }

    private String senha(String senhaPura) {
        return passwordEncoder.encode(senhaPura);
    }

    private static String proximoSemestre(LocalDate hoje) {
        return hoje.getMonthValue() <= 6 ? hoje.getYear() + "/2" : (hoje.getYear() + 1) + "/1";
    }
}
