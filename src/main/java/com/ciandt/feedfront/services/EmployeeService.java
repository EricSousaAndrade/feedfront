package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;

import java.util.List;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {
        this.dao = new EmployeeDAO();
    }

    @Override
    public List<Employee> listar() throws ArquivoException {
        return dao.listar();
    }

    @Override
    public Employee buscar(String id) throws ArquivoException, BusinessException {
        return dao.buscar(id);
    }

    @Override
    public Employee salvar(Employee employee) throws ArquivoException, BusinessException {
        return dao.salvar(employee);
    }

    @Override
    public Employee atualizar(Employee employee) throws ArquivoException, BusinessException {
        return dao.atualizar(employee);
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {
        dao.apagar(id);
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }
}
