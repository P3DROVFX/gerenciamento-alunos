package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;


    @Test
    public void findAllRetornaListaDeAlunos() {
        Aluno aluno = new Aluno();
        aluno.setNome("Carlos");
        aluno.setMatricula("789101");
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        serviceAluno.save(aluno);

        List<Aluno> alunos = serviceAluno.findAll();
        Assert.assertFalse(alunos.isEmpty());
    }

    @Test
    public void buscarAlunoPorNome() {
        Aluno aluno = new Aluno();
        aluno.setNome("Mariana");
        aluno.setMatricula("321654");
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);
        serviceAluno.save(aluno);

        List<Aluno> resultado = serviceAluno.findByNomeContainingIgnoreCase("Mariana");
        Assert.assertEquals(1, resultado.size());
        Assert.assertEquals("Mariana", resultado.get(0).getNome());
    }

    @Test
    public void salvarAlunoComMatriculaInvalida() {
        Aluno aluno = new Aluno();
        aluno.setNome("Luciana");
        aluno.setMatricula("12");
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);

        Assert.assertThrows(ConstraintViolationException.class, () -> serviceAluno.save(aluno));
    }

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);});
    }

    @Test
    public void salvarAlunoComStatusInativo() {
        Aluno aluno = new Aluno();
        aluno.setNome("Felipe");
        aluno.setMatricula("789123");
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.INATIVO);
        aluno.setTurno(Turno.MATUTINO);

        serviceAluno.save(aluno);
        List<Aluno> alunosInativos = serviceAluno.findByStatusInativo();
        Assert.assertFalse(alunosInativos.isEmpty());
    }
}