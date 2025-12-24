package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
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
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario1;
    private Usuario usuario2;

    @Before
    public void setUp() {
        usuario1 = new Usuario();
        usuario1.setEmail("carlos.silva@example.com");
        usuario1.setUser("Carlos Silva");
        usuario1.setSenha("senha123");

        usuario2 = new Usuario();
        usuario2.setEmail("maria.oliveira@example.com");
        usuario2.setUser("Maria Oliveira");
        usuario2.setSenha("senha456");

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
    }

    @Test
    public void testBuscarLogin() {
        String nomeUsuario = "Carlos Silva";
        String senhaUsuario = "senha123";

        Usuario usuario = new Usuario();
        usuario.setUser(nomeUsuario);
        usuario.setSenha(senhaUsuario);
        usuario.setEmail("carlos.silva@example.com");
        usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.buscarLogin(nomeUsuario, senhaUsuario);

        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals(nomeUsuario, usuarioEncontrado.getUser());
        Assert.assertEquals(senhaUsuario, usuarioEncontrado.getSenha());
    }

    @Test
    public void testCountUsuarios() {
        long totalUsuarios = usuarioRepository.count();
        Assert.assertEquals(2, totalUsuarios);
    }

    @Test
    public void testFindById() {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario1.getId());
        Assert.assertTrue(usuarioOptional.isPresent());
        Assert.assertEquals("Carlos Silva", usuarioOptional.get().getUser());
    }

    @Test
    public void testFindAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        Assert.assertFalse(usuarios.isEmpty());
        Assert.assertEquals(2, usuarios.size());
    }
}