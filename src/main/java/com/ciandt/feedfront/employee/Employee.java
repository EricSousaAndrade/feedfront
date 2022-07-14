package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.util.*;

public class Employee implements Serializable {
    private String id;
    private String nome;
    private String sobrenome;
    private String email;

    private static List<Employee> employeeList = new ArrayList<>();

    private static String arquivoCriado = "src/main/resources/arquivo.extensao";

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
        if (nome.length() <= 2) {
            throw new ComprimentoInvalidoException("Comprimento do nome precisa ser maior que 2");
        }
        this.nome = nome;
        if (sobrenome.length() <= 2) {
            throw new ComprimentoInvalidoException("Comprimento do sobrenome precisa ser maior que 2");
        }
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public Employee() {
    }

    public static Employee salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        File output;
        FileWriter out;
        employeeList.add(employee);
        if (Objects.requireNonNull(listarEmployees()).stream().anyMatch(e -> e.getEmail().equals(employee.getEmail()))) {
            throw new EmailInvalidoException("E-mail ja cadastrado no repositorio");
        }
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
            return employee;
        } catch (IOException fx) {
            fx.printStackTrace();
        }
        return null;
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException, EmployeeNaoEncontradoException {
        File output;
        FileWriter out;
        Employee employeeParaApagar = buscarEmployee(employee.getId());
        if (employeeParaApagar == null) {
            throw new EmployeeNaoEncontradoException("Empregado não encontrado");
        } else {
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
                return employee;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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

                while (line != null) {
                    values = line.split(";");
                    listaDados.add(Arrays.asList(values));
                    line = bfr.readLine();
                }
                listaDados.forEach(p -> {
                    Employee employee = new Employee();
                    employee.setId(p.get(0));
                    try {
                        employee.setNome(p.get(1));
                    } catch (ComprimentoInvalidoException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        employee.setSobrenome(p.get(2));
                    } catch (ComprimentoInvalidoException e) {
                        throw new RuntimeException(e);
                    }
                    employee.setEmail(p.get(3));
                    listaEmployee.add(employee);
                });
                fr.close();
                return listaEmployee;
            }
        } catch (IOException fx) {
            fx.printStackTrace();
        }
        return null;
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        return Objects.requireNonNull(listarEmployees()).stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        Employee employeeParaApagar = buscarEmployee(id);
        File output;
        FileWriter out;
        if (employeeParaApagar == null) {
            throw new EmployeeNaoEncontradoException("Employee não encontrado");
        } else {
            List<Employee> listaNova = listarEmployees();
            assert listaNova != null;
            listaNova.removeIf(e -> e.getId().equals(employeeParaApagar.getId()));
            try {
                output = new File(getArquivoCriado());
                if (output.exists()) {
                    out = new FileWriter(output);
                    Objects.requireNonNull(listaNova).forEach(p -> {
                        try {
                            out.write(p.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    out.close();
                }
            } catch (IOException fx) {
                fx.printStackTrace();
            }
        }

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
        if (nome.length() <= 2) {
            throw new ComprimentoInvalidoException("Comprimento do nome precisa ser maior que 2");
        }
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
        if (sobrenome.length() <= 2) {
            throw new ComprimentoInvalidoException("Comprimento do sobrenome precisa ser maior que 2");
        }
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
        return id +
                ";" + nome +
                ";" + sobrenome +
                ";" + email + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) && nome.equals(employee.nome) && sobrenome.equals(employee.sobrenome) && email.equals(employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobrenome, email);
    }
}
