package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Feedback;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedbackDAO implements DAO<Feedback> {

    private String repositorioPath = "src/main/resources/data/feedback/";

    @Override
    public boolean tipoImplementaSerializable() {
        throw new UnsupportedOperationException();
    }

    private static ObjectOutputStream getOutputStream(String arquivo) throws IOException {
        return new ObjectOutputStream(new FileOutputStream(arquivo));
    }

    private static ObjectInputStream getInputStream(String arquivo) throws IOException {
        return new ObjectInputStream(new FileInputStream(arquivo));
    }

    @Override
    public List<Feedback> listar() throws ArquivoException, EntidadeNaoSerializavelException {
        List<Feedback> feedbacks = new ArrayList<>();

        try {
            Stream<Path> paths = Files.walk(Paths.get(repositorioPath));

            List<String> files = paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".byte"))
                    .map(p -> p.replace(".byte", ""))
                    .collect(Collectors.toList());

            for (String file: files) {
                try {
                    feedbacks.add(buscar(file));
                } catch (EntidadeNaoEncontradaException e) {
                    // Exception silenciada porque sei que não chegará aqui
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            }

            paths.close();
        } catch (IOException e) {
            throw new ArquivoException("");
        }

        return feedbacks;
    }

    @Override
    public Feedback buscar(String id) throws ArquivoException, EntidadeNaoSerializavelException, BusinessException {
        Feedback feedback;
        ObjectInputStream inputStream;

        try {
            inputStream = getInputStream(repositorioPath + id + ".byte");
            feedback = (Feedback) inputStream.readObject();

            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            if (e.getClass().getSimpleName().equals("FileNotFoundException")) {
                throw new EntidadeNaoEncontradaException("não foi possível encontrar o feedback");
            }

            throw new ArquivoException("");
        }

        return feedback;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws ArquivoException, EntidadeNaoSerializavelException, BusinessException {
        ObjectOutputStream outputStream = null;
        if(feedback.getAutor() == null && feedback.getProprietario() == null){
            throw new IllegalArgumentException("employee inválido");
        }if(feedback.getAutor() == null){
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
        }
        try {

            outputStream = getOutputStream(feedback.getArquivo());
            outputStream.writeObject(feedback);

            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ArquivoException("");
        }

        return feedback;
    }

    @Override
    public boolean apagar(String id) throws ArquivoException, EntidadeNaoSerializavelException, BusinessException {
        buscar(id);

        return new File(String.format("%s%s.byte", repositorioPath, id)).delete();
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws ArquivoException, BusinessException, IllegalArgumentException {
        buscar(feedback.getId());

        return salvar(feedback);
    }
}
