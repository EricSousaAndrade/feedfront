package com.ciandt.feedfront.employee;


import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    public Employee employee1;
    public Employee employee2;
    public EmployeeService employeeService;



    @BeforeEach
    public void initEach() throws IOException, BusinessException {
        employeeService = new EmployeeService();
        employee1 = new Employee("Jose", "Silveira", "j.silveira@email.com");
        employee2 = null;

        Files.walk(Paths.get("src/main/resources/data/employee/"))
                .filter(p -> p.toString().endsWith(".byte"))
                .forEach(p -> {
                    new File(p.toString()).delete();
                });

        employeeService.salvar(employee1);
    }


    @Test
    public void nomeDeveTerComprimentoMaiorQueDois() {
        Exception comprimentoInvalidoException = assertThrows(ComprimentoInvalidoException.class, () ->
                employee1 = new Employee("Ze", "Juvenil", "z.juvenil@ciandt.com")
        );

        String mensagemEsperada = "Comprimento do nome deve ser maior que 2 caracteres.";
        String mensagemRecebida = comprimentoInvalidoException.getMessage();
        assertEquals(mensagemEsperada, mensagemRecebida);
    }

    @Test
    public void sobrenomeDeveTerComprimentoMaiorQueDois() {
        Exception comprimentoInvalidoException = assertThrows(ComprimentoInvalidoException.class, () ->
                employee1 = new Employee("Joao", "ao", "z.juvenil@ciandt.com")
        );

        String mensagemEsperada = "Comprimento do sobrenome deve ser maior que 2 caracteres.";
        String mensagemRecebida = comprimentoInvalidoException.getMessage();
        assertEquals(mensagemEsperada, mensagemRecebida);

    }
}
