package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Employee;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements DAO<Employee> {

    EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("feedfront");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public EmployeeDAO() {
    }

    @Override
    public List<Employee> listar() {
        List<Employee> listEmployee = entityManager.createQuery("select e from EMPLOYEE e").getResultList();
        return listEmployee;
    }

    @Override
    public Optional<Employee> buscar(long id) {
        Optional<Employee> employee =
                entityManager.createQuery("select e from EMPLOYEE e where e.id = :id")
                .setParameter("id", id)
                .getResultList().stream()
                .findFirst();
        return employee;
    }

    @Override
    public Employee salvar(Employee employee) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
            return employee;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean apagar(long id) {
        try {
            entityManager.getTransaction().begin();
            Employee employee = entityManager.find(Employee.class, id);
            entityManager.remove(employee);
            entityManager.getTransaction().commit();
        return true;
        }catch (Exception e){
            System.out.println("Erro ao apagar");
            return false;
        }
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager  = entityManager;
    }
}
