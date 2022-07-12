package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.util.*;

public class Employee {
    private String id;
    private String nome;
    private String sobrenome;
    private String email;

    private static String arquivoCriado = "src/main/resources/arquivo.extensao";

    public Employee(String nome, String sobrenome, String email) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public Employee() {
    }

    public static Employee salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        File output;
        FileWriter out;
        try {
            output = new File(getArquivoCriado());
            if (output.exists()) {
                out = new FileWriter(output, true);
                out.write(employee.toString());
                System.out.println("salvou");
                out.close();
            }
            return employee;
        } catch (IOException fx){
            fx.printStackTrace();
        }
        return null;
    }

    public static void adicionarNoArquivoAlteracoes(List<Employee> employeeList){
        File output;
        FileWriter out;
        try {
            output = new File(getArquivoCriado());
            if (output.exists()) {
                out = new FileWriter(output);
                employeeList.forEach(p -> {
                    try {
                        out.write(p.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                out.close();
            }
        } catch (IOException fx){
            fx.printStackTrace();
        }
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException, EmployeeNaoEncontradoException {
        Employee employeeParaApagar = buscarEmployee(employee.getId());
        List<Employee> novaLista = listarEmployees();
        assert novaLista != null;
        novaLista.removeIf(p -> p.getId().equals(employeeParaApagar.getId()));
        novaLista.add(employee);
        adicionarNoArquivoAlteracoes(novaLista);
        return employee;
    }

    public static List<Employee> listarEmployees() {
        List<Employee> listaEmployee = new ArrayList<>();
        List<List<String>> listaDados = new ArrayList<>();
        try {
            File output = new File(getArquivoCriado());
            FileReader fr = new FileReader(output);
            BufferedReader bfr = new BufferedReader(fr);

            if (output.exists()) {
                String[] values;

                String line = bfr.readLine();

                while(line != null){
                    values = line.split(";");
                    listaDados.add(Arrays.asList(values));
                    line = bfr.readLine();
                }
                listaDados.forEach(p -> {
                    Employee employee = new Employee();
                    employee.setId(p.get(0));
                    employee.setNome(p.get(1));
                    employee.setSobrenome(p.get(2));
                    employee.setEmail(p.get(3));
                    listaEmployee.add(employee);
                });
                System.out.println("salvou");
                fr.close();
                return listaEmployee;
            }
        } catch (IOException fx){
            fx.printStackTrace();
        }
        return null;
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        return Objects.requireNonNull(listarEmployees()).stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        Employee employeeParaApagar = buscarEmployee(id);
        List<Employee> novaLista = listarEmployees();
        assert novaLista != null;
        novaLista.removeIf(p -> p.getId().equals(employeeParaApagar.getId()));
        adicionarNoArquivoAlteracoes(novaLista);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getArquivoCriado() {
        return arquivoCriado;
    }

    public static void setArquivoCriado(String arquivoCriado) {
        Employee.arquivoCriado = arquivoCriado;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return  id +
                ";" + nome +
                ";" + sobrenome +
                ";" + email + "\n";
    }

}
