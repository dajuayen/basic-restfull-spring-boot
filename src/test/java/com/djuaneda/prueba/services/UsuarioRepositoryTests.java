package com.djuaneda.prueba.services;

import com.djuaneda.prueba.App;
import com.djuaneda.prueba.configuration.UsuarioRepository;
import com.djuaneda.prueba.models.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void insertar_recuperar_usuario_repositorio() {
        Usuario usuarioTest = new Usuario("Jose", "Gonzalez Perez", "Porto Pi 55","Palma de Mallorca","971343536",30);
        Usuario usuario = usuarioRepository.save(usuarioTest);
        Usuario usuarioFound = usuarioRepository.findById(usuario.getId()).get();

        assertNotNull(usuarioFound);
        assertEquals(usuario.getNombre(), usuarioFound.getNombre());
    }
}
