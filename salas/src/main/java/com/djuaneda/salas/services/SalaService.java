package com.djuaneda.salas.services;


import com.djuaneda.salas.Sala;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sala")
public interface SalaService {

    @PostMapping("") // Create
    @ApiOperation(value = "Create sala", notes = "${Sala.create.notes}")
    public ResponseEntity<Sala> create(@RequestParam(value = "nombre") String nombre,
                                       @RequestParam(value = "numero") Integer numero,
                                       @RequestParam(value = "planta") Integer planta,
                                       @RequestParam(value = "edificio") String edificio,
                                       @RequestParam(value = "direccion") String direccion,
                                       @RequestParam(value = "poblacion") String poblacion,
                                       @RequestParam(value = "telefono") String telefono);

    @GetMapping("/{id}") // Read
    @ApiOperation(value = "Read Sala", notes = "${Sala.read.notes}")
    public ResponseEntity<Sala> read(@PathVariable Integer id);


    @PutMapping("/{id}") // Update
    @ApiOperation(value = "Update User", notes = "${Sala.update.notes}")
    public ResponseEntity<Sala> update(@PathVariable Integer id,
                                       @RequestParam(value = "nombre") String nombre,
                                       @RequestParam(value = "numero") Integer numero,
                                       @RequestParam(value = "planta") Integer planta,
                                       @RequestParam(value = "edificio") String edificio,
                                       @RequestParam(value = "direccion") String direccion,
                                       @RequestParam(value = "poblacion") String poblacion,
                                       @RequestParam(value = "telefono") String telefono);


    @DeleteMapping("/{id}") // Delete
    @ApiOperation(value = "Remove User", notes = "${Sala.remove.notes}")
    public ResponseEntity<String> remove(@PathVariable Integer id);


    @GetMapping("") // Listado de usuarios activos
    @ApiOperation(
            value = "Lista de salas ",
            notes = "${Sala.listar.notes}",
            response = Sala.class,
            responseContainer = "List")
    public ResponseEntity<List<Sala>> listar(Model model);


}
