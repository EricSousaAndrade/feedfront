package com.ciandt.feedfront.contracts;

import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.util.List;

public interface Service<T> {
    List<T> listar();

    T buscar(long id) throws BusinessException, EmployeeNaoEncontradoException;

    T salvar(T e) throws BusinessException, IllegalArgumentException;

    T atualizar(T e) throws BusinessException, IllegalArgumentException, EmployeeNaoEncontradoException;

    void apagar(long id) throws BusinessException, EmployeeNaoEncontradoException;

    void setDAO(DAO<T> dao);
}
