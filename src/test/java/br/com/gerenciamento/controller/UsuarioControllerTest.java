package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
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
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void testCadastrarUsuario() throws Exception {
        mockMvc.perform(post("/salvarUsuario")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "teste@exemplo.com")
                        .param("user", "testeuser")
                        .param("senha", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLoginComCredenciaisValidas() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("login@exemplo.com");
        usuario.setUser("loginuser");
        usuario.setSenha("senha123");
        serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("user", "loginuser")
                        .param("senha", "senha123"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));
    }

    @Test
    public void testLoginComCredenciaisInvalidas() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("user", "invalido")
                        .param("senha", "senhaerrada"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"));
    }
}