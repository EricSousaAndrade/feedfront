package com.ciandt.feedfront.contracts;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;

import java.io.IOException;
import java.util.List;

public interface DAO<E> {
    boolean tipoImplementaSerializable();

    List<E> listar() throws ArquivoException, EntidadeNaoSerializavelException;

    E buscar(String id) throws ArquivoException, EntidadeNaoSerializavelException, BusinessException;

    E salvar(E e) throws ArquivoException, EntidadeNaoSerializavelException, BusinessException;

    boolean apagar(String id) throws ArquivoException, EntidadeNaoSerializavelException, BusinessException;

    E atualizar(E e) throws ArquivoException, BusinessException, IllegalArgumentException;

}
