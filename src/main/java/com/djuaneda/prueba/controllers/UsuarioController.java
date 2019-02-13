package com.djuaneda.prueba.controllers;


import com.djuaneda.prueba.models.Usuario;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("") // Create
    @ApiOperation(value = "Create User", notes = "${Usuario.create.notes}")
    public ResponseEntity<Usuario> create(@RequestParam(value = "nombre") String nombre,
                                          @RequestParam(value = "apellidos") String apellidos,
                                          @RequestParam(value = "direccion") String direccion,
                                          @RequestParam(value = "poblacion") String poblacion,
                                          @RequestParam(value = "telefono") String telefono,
                                          @RequestParam(value = "edad") Integer edad) {
        Usuario usr = null;
        try {
            usr = usuarioService.addUsuario(nombre, apellidos, direccion, poblacion, telefono, edad);
        }catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(usr);
    }

    @GetMapping("/{id}") // Read
    @ApiOperation(value = "Read User", notes = "${Usuario.read.notes}")
    public ResponseEntity<Usuario> read(@PathVariable Integer id) {
        Usuario usr = null;
        try{
            usr = usuarioService.getUsuario(id);
        }catch (UsuarioExcepcion e) {
            usr = new Usuario();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usr);
        }catch (Exception e){
            usr = new Usuario();
            return ResponseEntity.status(404).body(usr);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(usr);
    }

    @PutMapping("/{id}") // Update
    @ApiOperation(value = "Update User", notes = "${Usuario.update.notes}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id,
                          @RequestParam(value = "nombre") String nombre,
                          @RequestParam(value = "apellidos") String apellidos,
                          @RequestParam(value = "direccion") String direccion,
                          @RequestParam(value = "poblacion") String poblacion,
                          @RequestParam(value = "telefono") String telefono,
                          @RequestParam(value = "edad") Integer edad) {
        Usuario usr = null;
        try {
            usr = usuarioService.updateUsuario(id, nombre, apellidos, direccion, poblacion, telefono, edad);
        }catch (UsuarioExcepcion | IndexOutOfBoundsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usr);
        }catch (Exception e){
            return ResponseEntity.status(500).body(usr);
        }
        return ResponseEntity.status(200).body(usr);
    }

    @DeleteMapping("/{id}") // Delete
    @ApiOperation(value = "Remove User", notes = "${Usuario.remove.notes}")
    public ResponseEntity<String> remove(@PathVariable Integer id) {
        try{
            usuarioService.removeUsuario(id);
        }catch (UsuarioExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(500).body("No se puedo eliminar el usuario : " + id);
        }
        return ResponseEntity.status(200).body("El usuario [" + id + "] fue eliminado.");
    }

    @GetMapping("") // Listado de usuarios activos
    @ApiOperation(
            value = "List User",
            notes = "${Usuario.listar.notes}",
            response = Usuario.class,
            responseContainer = "List")
    public ResponseEntity<List<Usuario>> listar(Model model) {
        List<Usuario> usuarios = null;
        try{
            usuarios = usuarioService.getUsuarios();
        }catch (Exception e){
            usuarios = new ArrayList<>();
            return ResponseEntity.status(500).body(usuarios);
        }
        return ResponseEntity.status(200).body(usuarios);
    }

}