package com.djuaneda.salas;

import java.util.List;

public interface SalaDAO {

    public List<Sala> findAll();

    public Sala findById(int id);

    public void save(Sala sala);

    public void deleteById(int id);
}
