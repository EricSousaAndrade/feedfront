package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Feedback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class FeedbackDAO implements DAO<Feedback>{
    EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("feedfront");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public FeedbackDAO() {
    }

    @Override
    public List<Feedback> listar() {
        List<Feedback> listFeedback = entityManager.createQuery("select f from FEEDBACK f").getResultList();

        return listFeedback;
    }

    @Override
    public Optional<Feedback> buscar(long id) {
        List feedback = entityManager.createQuery("select f from FEEDBACK f where f.id = :id").setParameter("id", id).getResultList();
        return feedback.stream().findAny();
    }

    @Override
    public Feedback salvar(Feedback feedback) {
        entityManager.getTransaction().begin();
        entityManager.persist(feedback);
        entityManager.getTransaction().commit();
        return feedback;
    }

    @Override
    public boolean apagar(long id) {
        try {
            entityManager.remove(buscar(id));
            entityManager.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager  = entityManager;
    }
}
