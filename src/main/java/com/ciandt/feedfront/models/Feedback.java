package com.ciandt.feedfront.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Feedback implements Serializable {

    private String id;
    private LocalDate data;
    private Employee autor;
    private Employee proprietario;
    private String descricao;
    private String oqueMelhora;
    private String comoMelhora;
    private String arquivo;
    private static final String repositorioPath = "src/main/resources/data/feedback/";

    private static final long serialVersionID = 1;

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao) {
        this.id = UUID.randomUUID().toString();
        this.data = data;
        this.autor = autor;
        this.proprietario = proprietario;
        this.descricao = descricao;
        this.arquivo = repositorioPath + getId() + ".byte";
    }

    public Feedback( LocalDate data, Employee autor, Employee proprietario, String id,  String descricao, String oqueMelhora, String comoMelhora) {
        this.id = id;
        this.data = data;
        this.autor = autor;
        this.proprietario = proprietario;
        this.descricao = descricao;
        this.oqueMelhora = oqueMelhora;
        this.comoMelhora = comoMelhora;
        this.arquivo = repositorioPath + getId() + ".byte";
    }

    public Feedback() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Employee getAutor() {
        return autor;
    }

    public void setAutor(Employee autor) {
        this.autor = autor;
    }

    public Employee getProprietario() {
        return proprietario;
    }

    public void setProprietario(Employee proprietario) {
        this.proprietario = proprietario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getOqueMelhora() {
        return oqueMelhora;
    }

    public void setOqueMelhora(String oqueMelhora) {
        this.oqueMelhora = oqueMelhora;
    }

    public String getComoMelhora() {
        return comoMelhora;
    }

    public void setComoMelhora(String comoMelhora) {
        this.comoMelhora = comoMelhora;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) && Objects.equals(data, feedback.data) && Objects.equals(autor, feedback.autor) && Objects.equals(proprietario, feedback.proprietario) && Objects.equals(descricao, feedback.descricao) && Objects.equals(oqueMelhora, feedback.oqueMelhora) && Objects.equals(comoMelhora, feedback.comoMelhora) && Objects.equals(arquivo, feedback.arquivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, autor, proprietario, descricao, oqueMelhora, comoMelhora, arquivo);
    }
}
