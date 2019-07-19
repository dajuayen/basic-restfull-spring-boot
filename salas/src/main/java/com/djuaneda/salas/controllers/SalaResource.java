package com.djuaneda.salas.controllers;

import com.djuaneda.salas.Sala;
import com.djuaneda.salas.SalaDAOImpl;
import com.djuaneda.salas.services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import javax.validation.constraints.Null;
import java.util.List;

public class SalaResource implements SalaService {

    @Autowired
    private SalaDAOImpl salaManager;

    @Override
    public ResponseEntity<Sala> create(String nombre, Integer numero, Integer planta, String edificio, String direccion, String poblacion, String telefono) {

        Sala nueva = new Sala(nombre, numero, planta, edificio, direccion, poblacion, telefono);
        salaManager.save(nueva);
        return new ResponseEntity<Sala>(nueva, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Sala> read(Integer id) {
        Sala buscada = salaManager.findById(id);
        return new ResponseEntity<Sala>(buscada, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<Sala> update(Integer id, String nombre, Integer numero, Integer planta, String edificio, String direccion, String poblacion, String telefono) {
        Sala modificada = salaManager.findById(id);
        if (!nombre.isEmpty()) modificada.setNombre(nombre);
        if (!edificio.isEmpty()) modificada.setNombre(edificio);
        if (!direccion.isEmpty()) modificada.setNombre(direccion);
        if (!poblacion.isEmpty()) modificada.setNombre(poblacion);
        if (!telefono.isEmpty()) modificada.setNombre(telefono);
        if (numero != null) modificada.setNumero(numero);
        if (planta != null) modificada.setNumero(planta);
        salaManager.save(modificada);
        return new ResponseEntity<Sala>(modificada, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> remove(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Sala>> listar(Model model) {
//
//        List<Resource<Employee>> employees = repository.findAll().stream()
//                .map(employee -> new Resource<>(employee,
//                        linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
//                        linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
//                .collect(Collectors.toList());
//
//        return new Resources<>(employees,
//                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
        return null;
    }
}
