package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Employee;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeDAO implements DAO<Employee> {
    private String repositorioPath = "src/main/resources/data/employee/";

    private static ObjectOutputStream getOutputStream(String arquivo) throws IOException {
        return new ObjectOutputStream(new FileOutputStream(arquivo));
    }

    private static ObjectInputStream getInputStream(String arquivo) throws IOException {
        return new ObjectInputStream(new FileInputStream(arquivo));
    }

    @Override
    public boolean tipoImplementaSerializable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Employee> listar() throws ArquivoException, EntidadeNaoSerializavelException {
        List<Employee> employees = new ArrayList<>();

        try {
            Stream<Path> paths = Files.walk(Paths.get(repositorioPath));

            List<String> files = paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".byte"))
                    .map(p -> p.replace(".byte", ""))
                    .collect(Collectors.toList());

            for (String file: files) {
                try {
                    employees.add(buscar(file));
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

        return employees;
    }

    @Override
    public Employee buscar(String id) throws ArquivoException, EntidadeNaoSerializavelException, BusinessException {
        Employee employee;
        ObjectInputStream inputStream;

        try {
            inputStream = getInputStream(repositorioPath + id + ".byte");
            employee = (Employee) inputStream.readObject();

            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            if (e.getClass().getSimpleName().equals("FileNotFoundException")) {
                throw new EntidadeNaoEncontradaException("Employee não encontrado");
            }
            throw new ArquivoException("");
        }

        return employee;
    }

    @Override
    public Employee salvar(Employee employee) throws ArquivoException, EntidadeNaoSerializavelException, BusinessException {
        ObjectOutputStream outputStream = null;

        try {
            List<Employee> employees = listar();
            boolean emailExistente = false;
            for (Employee employeeSalvo: employees) {
                if (!employeeSalvo.getId().equals(employee.getId()) && employeeSalvo.getEmail().equals(employee.getEmail())) {
                    emailExistente = true;
                    break;
                }
            }

            if (emailExistente) {
                throw new EmailInvalidoException("E-mail ja cadastrado no repositorio");
            }

            outputStream = getOutputStream(employee.getArquivo());
            outputStream.writeObject(employee);

            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ArquivoException("");
        }

        return employee;
    }

    @Override
    public boolean apagar(String id) throws ArquivoException, EntidadeNaoSerializavelException, BusinessException {
        buscar(id);

        return new File(String.format("%s%s.byte", repositorioPath, id)).delete();
    }

    @Override
    public Employee atualizar(Employee employee) throws ArquivoException, BusinessException, IllegalArgumentException {
        buscar(employee.getId());

        return salvar(employee);
    }
}
