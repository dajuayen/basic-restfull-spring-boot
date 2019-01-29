package com.djuaneda.prueba.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import com.djuaneda.prueba.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
}
