package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.daos.FeedbackDAO;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;

import java.util.List;

public class FeedbackService implements Service<Feedback> {
    private DAO<Feedback> dao;
    private Service<Employee> employeeService;

    public FeedbackService() {
        this.dao = new FeedbackDAO();
    }

    @Override
    public List<Feedback> listar() {
        return dao.listar();
    }

    @Override
    public Feedback buscar(long id) throws BusinessException {
        return dao.buscar(id).get();
    }

    @Override
    public Feedback salvar(Feedback feedback) throws BusinessException, IllegalArgumentException {
        return dao.salvar(feedback);
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws BusinessException, IllegalArgumentException {
        throw new UnsupportedOperationException(); // não implementar o método
    }

    @Override
    public void apagar(long id) throws BusinessException {
        throw new UnsupportedOperationException(); // não implementar o método
    }

    @Override
    public void setDAO(DAO<Feedback> dao) {
        this.dao = dao;
    }

    public void setEmployeeService(Service<Employee> employeeService) {
        this.employeeService = employeeService;
    }
}
