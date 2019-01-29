package com.djuaneda.prueba.services;

import com.djuaneda.prueba.App;
import com.djuaneda.prueba.configuration.UsuarioRepository;
import com.djuaneda.prueba.controllers.UsuarioExcepcion;
import com.djuaneda.prueba.controllers.UsuarioService;
import com.djuaneda.prueba.models.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class UsuarioServiceTests {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private List<Usuario> usuarioList;

    @Before
    public void setup() throws Exception {
        Usuario usuario1 = new Usuario("Jose", "Gonzalez Perez", "Porto Pi 55","Palma de Mallorca","971343536",30);
        Usuario usuario2 = new Usuario("Ana", "Zapatero Díez", "Portales 33","Logroño","666555444",32);
        Usuario usuario3 = new Usuario("María", "Saenz Jimenez", "Av. Gabriel Roca 20","Palma de Mallorca","971242526",27);

        usuarioList = new ArrayList<>();
        usuarioList.add(usuario1);
        usuarioList.add(usuario2);
        usuarioList.add(usuario3);
    }

    @Test
    public void listarUsuarios() {
        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarioList);
        List<Usuario> listado = usuarioService.getUsuarios();
        Assert.assertEquals(listado.size(), usuarioList.size());
    }

    @Test
    public void listarUsuariosVacia() {
        Mockito.when(usuarioRepository.findAll()).thenReturn(new ArrayList<Usuario>());
        List<Usuario> listado = usuarioService.getUsuarios();
        Assert.assertEquals(0, listado.size());
    }

    @Test
    public void devolverUsuario() {
        Usuario usuario = usuarioList.get(0);
        Optional<Usuario> oUsuario = Optional.of(usuario);
        Mockito.when(usuarioRepository.existsById(1)).thenReturn(true);
        Mockito.when(usuarioRepository.findById(1)).thenReturn(oUsuario);
        Usuario usuarioTest = null;
        try {
            usuarioTest = usuarioService.getUsuario(1);
        } catch (UsuarioExcepcion usuarioExcepcion) {
            usuarioExcepcion.printStackTrace();
        }
        Assert.assertEquals(usuario.getNombre(), usuarioTest.getNombre());
    }

    @Test
    public void devolverUsuarioNoEncontrado() {
        Mockito.when(usuarioRepository.existsById(1)).thenReturn(false);
        Usuario usuarioTest = usuarioList.get(1);
        String msg = "";
        try {
            usuarioTest = usuarioService.getUsuario(1);
            msg = "Usuario encontrado";
        } catch (UsuarioExcepcion usuarioExcepcion) {
            usuarioTest = null;
            msg = usuarioExcepcion.getMessage();
        }
        Assert.assertEquals(null, usuarioTest);
        Assert.assertEquals("Usuario no encontrado", msg);
    }

    @Test
    public void insertarUsuario() {
        Usuario usuario4 = new Usuario("Daniel", "Torres López", "Gran Vía 80","Madrid","665544778",35);
        Mockito.when(usuarioRepository.saveAndFlush(usuario4)).thenReturn(usuario4);
        Usuario usuarioTest = null;
        try {
            usuarioTest = usuarioService.addUsuario("Daniel", "Torres López", "Gran Vía 80","Madrid","665544778",35);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(usuario4.getNombre(), usuarioTest.getNombre());
    }

    @Test
    public void actualizarUsuario() {
        String nuevoNombre = "Lara";
        Integer nuevaEdad = 10;
        Usuario usuario = usuarioList.get(0);
        String apellidos = usuario.getApellidos();
        Optional<Usuario> oUsuario1 = Optional.of(usuario);
        Mockito.when(usuarioRepository.existsById(1)).thenReturn(true);
        Mockito.when(usuarioRepository.findById(1)).thenReturn(oUsuario1);
        Mockito.when(usuarioRepository.saveAndFlush(usuario)).thenReturn(usuario);
        Usuario usuarioTest;
        try {
            usuarioTest = usuarioService.updateUsuario(1, nuevoNombre, "","","","",nuevaEdad);
        } catch (UsuarioExcepcion usuarioExcepcion) {
            usuarioTest = null;
        }
        Assert.assertEquals(nuevoNombre, usuarioTest.getNombre());
        Assert.assertEquals(apellidos, usuarioTest.getApellidos());
        Assert.assertEquals(nuevaEdad, usuarioTest.getEdad());
    }

    @Test
    public void actualizarUsuarioNoEncontrado() {
        String nuevoNombre = "Lara";
        Integer nuevaEdad = 10;
        Mockito.when(usuarioRepository.existsById(1)).thenReturn(false);
        Usuario usuarioTest = usuarioList.get(1);
        String msg = "";
        try {
            usuarioTest = usuarioService.updateUsuario(1, nuevoNombre, "","","","",nuevaEdad);
            msg = "Usuario encontrado";
        } catch (UsuarioExcepcion usuarioExcepcion) {
            usuarioTest = null;
            msg = usuarioExcepcion.getMessage();
        }
        Assert.assertEquals(null, usuarioTest);
        Assert.assertEquals("Usuario no encontrado", msg);
    }

    @Test
    public void eliminarUsuario() {
        Usuario usuario = usuarioList.get(0);
        Optional<Usuario> oUsuario1 = Optional.of(usuario);
        Mockito.when(usuarioRepository.existsById(1)).thenReturn(true);
        Mockito.when(usuarioRepository.findById(1)).thenReturn(oUsuario1);
        boolean result;
        try {
            result = usuarioService.removeUsuario(1);
        } catch (UsuarioExcepcion usuarioExcepcion) {
            result = false;
        }
        Assert.assertEquals(true, result);
    }

    @Test
    public void eliminarUsuarioNoEncontrado() {
        Mockito.when(usuarioRepository.existsById(1)).thenReturn(false);
        boolean result;
        String msg = "";
        try {
            usuarioService.removeUsuario(1);
        } catch (UsuarioExcepcion usuarioExcepcion) {
            msg = usuarioExcepcion.getMessage();
        }
        Assert.assertEquals("Usuario no encontrado", msg);
    }


}

