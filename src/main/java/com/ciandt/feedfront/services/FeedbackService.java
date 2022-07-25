package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.FeedbackDAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Feedback;

import java.util.List;

public class FeedbackService implements Service<Feedback> {
    private DAO<Feedback> dao;

    public FeedbackService() {
        this.dao = new FeedbackDAO();
    }

    @Override
    public List<Feedback> listar() throws ArquivoException {
        return dao.listar();
    }

    @Override
    public Feedback buscar(String id) throws ArquivoException, BusinessException {
        return dao.buscar(id);
    }

    @Override
    public Feedback salvar(Feedback feedback) throws ArquivoException, BusinessException, IllegalArgumentException {
        if(feedback==null){
            throw new IllegalArgumentException("feedback inválido");
        }
        return dao.salvar(feedback);
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws ArquivoException, BusinessException, IllegalArgumentException {
        return dao.atualizar(feedback);
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {
        dao.apagar(id);
    }

    @Override
    public void setDAO(DAO<Feedback> dao) {
        this.dao = dao;
    }
}
