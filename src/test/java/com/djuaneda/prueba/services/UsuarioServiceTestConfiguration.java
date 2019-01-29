package com.djuaneda.prueba.services;

import com.djuaneda.prueba.configuration.UsuarioRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class UsuarioServiceTestConfiguration {

    @Bean
    @Primary
    public UsuarioRepository usuarioRepositoryTest() {
        return Mockito.mock(UsuarioRepository.class);
    }
}
