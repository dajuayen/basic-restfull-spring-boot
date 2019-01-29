package com.djuaneda.prueba.integration;

import com.djuaneda.prueba.App;
import com.djuaneda.prueba.configuration.UsuarioRepository;
import com.djuaneda.prueba.controllers.UsuarioExcepcion;
import com.djuaneda.prueba.models.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {App.class})
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        classes = {App.class, IntegrationH2Config.class})
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioIntegracionTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private Usuario insertarUsuario(Usuario usuario){
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("nombre", usuario.getNombre());
        parts.add("apellidos", usuario.getApellidos());
        parts.add("direccion", usuario.getDireccion());
        parts.add("poblacion", usuario.getPoblacion());
        parts.add("telefono", usuario.getPoblacion());
        parts.add("edad", usuario.getEdad());
        ResponseEntity<Usuario> r = restTemplate.postForEntity("/usuario",parts,Usuario.class);
        return  r.getBody();
    }

    @Test
    public void listaVaciaTest() {
        ResponseEntity<List> response = restTemplate.getForEntity("/usuario", List.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals(0, response.getBody().size());

    }

    @Test
    public void listarInitialTest() {
        Usuario usuario = insertarUsuario(new Usuario("Iván", "Casquero Luque","Av. Colón 11",
                "Logroño", "684235178", 22));

        ResponseEntity<List> response = restTemplate.getForEntity("/usuario", List.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void createAndGetUsuarioTest() {
        Usuario u = insertarUsuario(new Usuario("Iván", "Casquero Luque","Av. Colón 11",
                "Logroño", "684235178", 22));

        ResponseEntity<Usuario> response = restTemplate.getForEntity("/usuario/" + u.getId(), Usuario.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.FOUND));
        assertEquals(u.getId(),response.getBody().getId());
    }

    @Test
    public void getUsuarioTestFail() {
        String msg = null;
        Usuario u = insertarUsuario(new Usuario("Iván", "Casquero Luque","Av. Colón 11",
                "Logroño", "684235178", 22));

        ResponseEntity<Usuario> response = restTemplate.getForEntity("/usuario/" + u.getId()+1, Usuario.class);
        assertEquals(msg, response.getBody().getNombre());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void updateTest() {
        Usuario u = insertarUsuario(new Usuario("Iván", "Casquero Luque","Av. Colón 11",
                "Logroño", "684235178", 22));

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("id",1);
        parts.add("nombre","Virginia");
        parts.add("edad", 99);

//        ResponseEntity<Usuario> responseEntity;
//        responseEntity = new ResponseEntity<Usuario>(HttpStatus.OK);
//        restTemplate.exchange("/usuario/1", HttpMethod.DELETE, responseEntity, Usuario.class);
//        restTemplate.put("/usuario/" + u.getId(), Usuario.class, parts);
        restTemplate.put("/usuario/1", Usuario.class, parts);

        ResponseEntity<Usuario> response = restTemplate.getForEntity("/usuario/" + u.getId(), Usuario.class);

        assertEquals(u.getId(),response.getBody().getId());
        assertEquals(u.getApellidos(),response.getBody().getApellidos());
        assertNotEquals(u.getEdad(),response.getBody().getEdad());
        assertNotEquals(u.getNombre(),response.getBody().getNombre());
    }

    @Test
    public void updateTestFail() {
        String msg = null;
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("nombre","Virginia");
        params.add("nombre","Virginia");
        params.add("edad", 99);

        ResponseEntity<Usuario> responseEntity;
        responseEntity = new ResponseEntity<Usuario>(HttpStatus.OK);

        restTemplate.exchange("/usuario/1", HttpMethod.PUT, responseEntity, Usuario.class, params);
        restTemplate.put("/usuario/", Usuario.class, params);

        ResponseEntity<Usuario> response = restTemplate.getForEntity("/usuario/1", Usuario.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }


    @Test
    public void removeTest() throws Exception {
        Usuario u = insertarUsuario(new Usuario("Iván", "Casquero Luque","Av. Colón 11",
                "Logroño", "684235178", 22));
        ResponseEntity<Usuario> response = restTemplate.getForEntity("/usuario/" + u.getId(), Usuario.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.FOUND));
        assertEquals(u.getId(),response.getBody().getId());

        restTemplate.delete("/usuario/" + u.getId());

        ResponseEntity<Usuario> response2 = restTemplate.getForEntity("/usuario/" + u.getId(), Usuario.class);

        assertThat(response2.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

    }

    @Test
    public void deleteTestFail() throws Exception {
        ResponseEntity<String> responseEntity;
        responseEntity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);

        ResponseEntity<Usuario> response = restTemplate.exchange("/usuario/1000", HttpMethod.DELETE, responseEntity, Usuario.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }




}
