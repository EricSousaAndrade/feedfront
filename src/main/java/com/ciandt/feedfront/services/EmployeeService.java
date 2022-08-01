package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.BusinessException;

import java.util.List;
import java.util.Objects;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {
        this.dao = new EmployeeDAO();
    }

    @Override
    public List<Employee> listar() {
        return dao.listar();
    }

    @Override
    public Employee buscar(long id) throws BusinessException, EmployeeNaoEncontradoException {
        return dao.buscar(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi encontrado o Empregado"));
    }

    @Override
    public Employee salvar(Employee employee) throws BusinessException {
    if(listar().stream().filter(a -> a.getEmail().equals(employee.getEmail())).findFirst().orElse(null) != null){
        throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
    }
        return dao.salvar(employee);
    }

    @Override
    public Employee atualizar(Employee employee) throws EmployeeNaoEncontradoException, BusinessException {

        if(employee == null){
            throw new IllegalArgumentException("Employee ");
        }if(employee.getId() == null){
            throw new IllegalArgumentException("employee inválido: não possui ID");
        }if(listar().stream().filter(a -> a.getEmail().equals(employee.getEmail())).findFirst().orElse(null) != null){
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        }
        return dao.salvar(employee);
    }

    @Override
    public void apagar(long id) throws BusinessException, EmployeeNaoEncontradoException {
        buscar(id);
        dao.apagar(id);
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }
}
