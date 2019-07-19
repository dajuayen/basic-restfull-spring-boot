package com.djuaneda.salas;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class SalaDAOImpl implements SalaDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Sala> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Sala> theQuery = currentSession.createQuery("from Sala", Sala.class);

        List<Sala> salas = theQuery.getResultList();

        return salas;
    }

    @Override
    public Sala findById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);

        Sala sala = currentSession.get(Sala.class, id);

        return sala;
    }

    @Override
    public void save(Sala sala) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(sala);
    }

    @Override
    public void deleteById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Sala> theQuery = currentSession.createQuery("delete from Sala where id=:idSala");

		theQuery.setParameter("idSala", id);
		theQuery.executeUpdate();
    }
}
