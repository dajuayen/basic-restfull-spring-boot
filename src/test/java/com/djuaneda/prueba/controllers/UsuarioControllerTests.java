package com.djuaneda.prueba.controllers;

import com.djuaneda.prueba.models.Usuario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UsuarioController usuarioController;

    @MockBean
    private UsuarioService service;

    /**
     * Lista de usuarios
     */
    private List<Usuario> usuarioList;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));



    @Before
    public void setup() throws Exception {
        this.mvc = standaloneSetup(this.usuarioController).build();// Standalone context
        // mockMvc = MockMvcBuilders.webAppContextSetup(wac)
        // .build();

        Usuario usuario1 = new Usuario("Jose", "Gonzalez Perez", "Porto Pi 55","Palma de Mallorca","971343536",30);
        Usuario usuario2 = new Usuario("Ana", "Zapatero Díez", "Portales 33","Logroño","666555444",32);
        Usuario usuario3 = new Usuario("María", "Saenz Jimenez", "Av. Gabriel Roca 20","Palma de Mallorca","971242526",27);
        Usuario usuario4 = new Usuario("Daniel", "Torres López", "Gran Vía 80","Madrid","665544778",35);

        usuarioList = new ArrayList<>();
        usuarioList.add(usuario1);
        usuarioList.add(usuario2);
        usuarioList.add(usuario3);
        usuarioList.add(usuario4);
    }

    @Test
    public void list() throws Exception {

        when(service.getUsuarios()).thenReturn(usuarioList);

        mvc.perform(get("/usuario").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].nombre", is("Jose")))
                .andExpect(jsonPath("$[1].nombre", is("Ana")))
                .andExpect(jsonPath("$[2].nombre", is("María")))
                .andExpect(jsonPath("$[3].nombre", is("Daniel")));
    }

    @Test
    public void listEmpty() throws Exception {

        when(service.getUsuarios()).thenReturn(new ArrayList<Usuario>());

        mvc.perform(get("/usuario").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createTest() throws Exception {
        String nom = "Lara";
        String apes = "Recio Hernández";
        String dir = "Av. La Paz 88";
        String pob = "Zaragoza";
        String tel = "667744778";
        Usuario usuario5 = new Usuario(nom, apes, dir, pob, tel , 37);

        when(service.addUsuario(nom,apes,dir,pob,tel,37)).thenReturn(usuario5);

        mvc.perform(post("/usuario")
                .param("nombre",nom)
                .param("apellidos",apes)
                .param("direccion",dir)
                .param("poblacion",pob)
                .param("telefono",tel)
                .param("edad","37"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(nom)))
                .andExpect(jsonPath("$.apellidos", is(apes)))
                .andExpect(jsonPath("$.direccion", is(dir)))
                .andExpect(jsonPath("$.poblacion", is(pob)))
                .andExpect(jsonPath("$.telefono", is(tel)))
                .andExpect(jsonPath("$.edad", is(37)));
    }

    @Test
    public void createTestFail() throws Exception {
        String nom = "Lara";
        String apes = "Recio Hernández";
        String dir = "Av. La Paz 88";
        String pob = "Zaragoza";
        String tel = "667744778";

        when(service.addUsuario(nom,apes,dir,pob,tel,37)).thenThrow(new UsuarioExcepcion("lolo"));

        mvc.perform(post("/usuario")
                .param("nombre",nom)
                .param("apellidos",apes)
                .param("direccion",dir)
                .param("poblacion",pob)
                .param("telefono",tel)
                .param("edad","37"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void readTest() throws Exception {
        when(service.getUsuario(1)).thenReturn(usuarioList.get(0));

        mvc.perform(get("/usuario/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.nombre", is("Jose")));
    }

    @Test
    public void readTestNotFound() throws Exception {
        String msg = null;
        when(service.getUsuario(1)).thenThrow(new UsuarioExcepcion(msg));

        mvc.perform(get("/usuario/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.nombre", is(msg)));
    }

    @Test
    public void updateTest() throws Exception {
        String nom = "Lara";
        String apes = "Recio Hernández";
        String dir = "Av. La Paz 88";
        String pob = "Zaragoza";
        String tel = "667744778";
        Usuario usuario5 = new Usuario(nom, apes, dir, pob, tel , 19);

        when(service.updateUsuario(1, nom, apes, dir, pob, tel, 19)).thenReturn(usuario5);

        mvc.perform(put("/usuario/1")
                .param("nombre", nom)
                .param("apellidos",apes)
                .param("direccion", dir)
                .param("poblacion", pob)
                .param("telefono",tel)
                .param("edad","19"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTestNotFound() throws Exception {
        String nom = "Lara";
        String apes = "Recio Hernández";
        String dir = "Av. La Paz 88";
        String pob = "Zaragoza";
        String tel = "667744778";
        Usuario usuario5 = new Usuario(nom, apes, dir, pob, tel , 19);
        String msg = "Usuario no encontrado";

        when(service.updateUsuario(1, nom, apes, dir, pob, tel, 19)).thenThrow(new UsuarioExcepcion(msg));

        mvc.perform(put("/usuario/1")
                .param("nombre", nom)
                .param("apellidos",apes)
                .param("direccion", dir)
                .param("poblacion", pob)
                .param("telefono",tel)
                .param("edad","19"))
                .andExpect(content().string(""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTest() throws Exception {
        when(service.removeUsuario(1)).thenReturn(true);

        mvc.perform(delete("/usuario/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("El usuario [1] fue eliminado."));
    }

    @Test
    public void deleteTestNotFound() throws Exception {
        String msg = "Usuario no encontrado";
        when(service.removeUsuario(1)).thenThrow(new UsuarioExcepcion(msg));

        mvc.perform(delete("/usuario/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(msg));
    }

}