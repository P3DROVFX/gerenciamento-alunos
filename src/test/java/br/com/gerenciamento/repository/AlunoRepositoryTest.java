package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    private Aluno alunoAtivo;
    private Aluno alunoInativo;

    @Before
    public void setUp() {
        alunoAtivo = new Aluno();
        alunoAtivo.setNome("Carlos Silva");
        alunoAtivo.setMatricula("123456");
        alunoAtivo.setCurso(Curso.INFORMATICA);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setTurno(Turno.MATUTINO);

        alunoInativo = new Aluno();
        alunoInativo.setNome("Maria Oliveira");
        alunoInativo.setMatricula("654321");
        alunoInativo.setCurso(Curso.BIOMEDICINA);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setTurno(Turno.NOTURNO);

        alunoRepository.save(alunoAtivo);
        alunoRepository.save(alunoInativo);
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase("Carlos");
        Assert.assertFalse(alunos.isEmpty());
        Assert.assertEquals("Carlos Silva", alunos.get(0).getNome());
    }

    @Test
    public void testFindByStatusAtivo() {
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        Assert.assertFalse(alunosAtivos.isEmpty());
        Assert.assertEquals(Status.ATIVO, alunosAtivos.get(0).getStatus());
    }

    @Test
    public void testFindByStatusInativo() {
        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        Assert.assertFalse(alunosInativos.isEmpty());
        Assert.assertEquals(Status.INATIVO, alunosInativos.get(0).getStatus());
    }

    @Test
    public void testFindById() {
        Optional<Aluno> alunoOptional = alunoRepository.findById(alunoAtivo.getId());
        Assert.assertTrue(alunoOptional.isPresent());
        Assert.assertEquals("Carlos Silva", alunoOptional.get().getNome());
    }
}