package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void testInserirAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nome", "Carlos Silva")
                        .param("matricula", "123456")
                        .param("curso", "DIREITO")
                        .param("status", "ATIVO")
                        .param("turno", "NOTURNO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testListagemAlunos() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria Oliveira");
        aluno.setMatricula("654321");
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        serviceAluno.save(aluno);

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alunosList"));
    }

    @Test
    public void testEditarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Ana Lima");
        aluno.setMatricula("789456");
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setTurno(Turno.NOTURNO);
        serviceAluno.save(aluno);

        mockMvc.perform(post("/editar")
                        .param("id", aluno.getId().toString())
                        .param("nome", "Ana Lima Editada")
                        .param("matricula", "789456")
                        .param("curso", "INFORMATICA")
                        .param("status", "ATIVO")
                        .param("turno", "NOTURNO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testRemoverAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Lucas Costa");
        aluno.setMatricula("999111");
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        serviceAluno.save(aluno);

        mockMvc.perform(get("/remover/" + aluno.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

}