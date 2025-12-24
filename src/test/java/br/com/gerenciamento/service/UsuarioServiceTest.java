package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.exception.CriptoExistsException;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    private Usuario usuario;

    @Before
    public void setUp() {
        usuario = new Usuario();
        usuario.setEmail("usuario@dominio.com");
        usuario.setUser("usuario1");
        usuario.setSenha("senha123");
    }

    @Test
    public void salvarUsuarioComEmailValido() throws Exception {
        serviceUsuario.salvarUsuario(usuario);
        Usuario usuarioSalvo = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
        Assert.assertNotNull(usuarioSalvo);
        Assert.assertEquals(usuario.getEmail(), usuarioSalvo.getEmail());
    }

    @Test
    public void salvarUsuarioComEmailJaCadastrado() throws Exception {
        serviceUsuario.salvarUsuario(usuario);
        Usuario usuario2 = new Usuario();
        usuario2.setEmail("usuario@dominio.com");
        usuario2.setUser("usuario2");
        usuario2.setSenha("senha456");

        Assert.assertThrows(EmailExistsException.class, () -> serviceUsuario.salvarUsuario(usuario2));
    }

    @Test
    public void loginComCredenciaisValidas() throws Exception {
        serviceUsuario.salvarUsuario(usuario);
        Usuario usuarioLogado = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
        Assert.assertNotNull(usuarioLogado);
        Assert.assertEquals(usuario.getUser(), usuarioLogado.getUser());
    }

    @Test
    public void loginComCredenciaisInvalidas() throws Exception {
        serviceUsuario.salvarUsuario(usuario);
        Usuario usuarioLogado = serviceUsuario.loginUser(usuario.getUser(), "senhaErrada");
        Assert.assertNull(usuarioLogado);
    }
}