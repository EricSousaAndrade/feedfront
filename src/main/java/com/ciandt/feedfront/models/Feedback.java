package com.ciandt.feedfront.models;

import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "FEEDBACK")
public class Feedback {

    @Id
    @SequenceGenerator(name="feedback_seq", sequenceName="feedback_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_seq")
    @Column(name = "id_feedback", nullable = false)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "o_que_melhora")
    private String oQueMelhora;

    @Column(name = "como_melhora")
    private String comoMelhora;

    @Column(name = "data")
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Employee autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Employee proprietario;

    public Feedback() {
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao) throws ComprimentoInvalidoException {
        this.descricao = descricao;
        this.data = data;
        this.autor = autor;
        this.proprietario = proprietario;
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao, String oQueMelhora, String comoMelhora) {
        this.descricao = descricao;
        this.oQueMelhora = oQueMelhora;
        this.comoMelhora = comoMelhora;
        this.data = data;
        this.autor = autor;
        this.proprietario = proprietario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) && Objects.equals(descricao, feedback.descricao) && Objects.equals(oQueMelhora, feedback.oQueMelhora) && Objects.equals(comoMelhora, feedback.comoMelhora) && Objects.equals(data, feedback.data) && Objects.equals(autor, feedback.autor) && Objects.equals(proprietario, feedback.proprietario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, oQueMelhora, comoMelhora, data, autor, proprietario);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getoQueMelhora() {
        return oQueMelhora;
    }

    public void setoQueMelhora(String oQueMelhora) {
        this.oQueMelhora = oQueMelhora;
    }

    public String getComoMelhora() {
        return comoMelhora;
    }

    public void setComoMelhora(String comoMelhora) {
        this.comoMelhora = comoMelhora;
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

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", oQueMelhora='" + oQueMelhora + '\'' +
                ", comoMelhora='" + comoMelhora + '\'' +
                ", data=" + data +
                ", autor=" + autor +
                ", proprietario=" + proprietario +
                '}';
    }
}
