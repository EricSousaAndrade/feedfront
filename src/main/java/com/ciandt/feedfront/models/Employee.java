package com.ciandt.feedfront.models;

import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "EMPLOYEE")
public class Employee {

    @Id
    @SequenceGenerator(name="article_gen", sequenceName="article_gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_gen")
    @Column(name = "id_employee", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<Feedback> feedbackFeitos;

    @OneToMany(mappedBy = "proprietario", fetch = FetchType.LAZY)
    private List<Feedback> feedbackRecebidos;

    public Employee() {
    }

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        setNome(nome);
        setSobrenome(sobrenome);
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(nome, employee.nome) && Objects.equals(sobrenome, employee.sobrenome) && Objects.equals(email, employee.email) && Objects.equals(feedbackFeitos, employee.feedbackFeitos) && Objects.equals(feedbackRecebidos, employee.feedbackRecebidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobrenome, email, feedbackFeitos, feedbackRecebidos);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
        if(nome.length() <=2){
            throw new ComprimentoInvalidoException("Nome precisa ser maior que dois");
        }
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
        if(sobrenome.length() <=2){
            throw new ComprimentoInvalidoException("Nome precisa ser maior que dois");
        }
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Feedback> getFeedbackFeitos() {
        return feedbackFeitos;
    }

    public List<Feedback> getFeedbackRecebidos() {
        return feedbackRecebidos;
    }

    public void setFeedbackFeitos(List<Feedback> feedbackFeitos) {
        this.feedbackFeitos = feedbackFeitos;
    }

    public void setFeedbackRecebidos(List<Feedback> feedbackRecebidos) {
        this.feedbackRecebidos = feedbackRecebidos;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
