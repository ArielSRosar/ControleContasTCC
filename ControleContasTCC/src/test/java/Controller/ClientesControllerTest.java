/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Controller;

import DAO.ClientesDAO;
import Model.Clientes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Ariel
 */
public class ClientesControllerTest {

    private ClientesController clientesController;
    private ClientesDAO mockClientesDAO;

    @BeforeEach
    public void setUp() {
        // Mock é utilizado para "clonar" o ClientesDAO e não ter uma interação direta ao BD
        mockClientesDAO = mock(ClientesDAO.class);
        clientesController = new ClientesController(mockClientesDAO);
    }

    @Test
    public void testSalvarCliente() throws Exception {
        int id = 1;
        String nome = "Ariel";
        String cpf = "111.111.111-11";
        String status = "CDL";
        String observacao = "Cliente regular";

        doNothing().when(mockClientesDAO).save(any(Clientes.class));

        clientesController.salvarCliente(id, nome, cpf, status, observacao);

        verify(mockClientesDAO, times(1)).save(any(Clientes.class));
    }

    @Test
    public void testBuscarClientePorId() {
        int id = 1;
        Clientes cliente = new Clientes("Ariel", id, 1, "111.111.111-11", "Teste");

        when(mockClientesDAO.findById(id)).thenReturn(cliente);

        Clientes resultado = clientesController.buscarClientePorId(id);

        assertNotNull(resultado);
        assertEquals("Ariel", resultado.getNome());
        verify(mockClientesDAO, times(1)).findById(id);
    }

    @Test
    public void testPesquisarClientePorNome() {
        String nome = "Ariel";
        List<Clientes> clientesMock = new ArrayList<>();
        clientesMock.add(new Clientes("Ariel", 1, 1, "111.111.111-11", "Teste"));

        when(mockClientesDAO.findByName(nome)).thenReturn(clientesMock);

        List<Clientes> resultado = clientesController.pesquisarClientePorNome(nome);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ariel", resultado.get(0).getNome());
        verify(mockClientesDAO, times(1)).findByName(nome);
    }

    @Test
    public void testListarClientes() {
        List<Clientes> clientesMock = new ArrayList<>();
        clientesMock.add(new Clientes("Ariel", 1, 1, "111.111.111-11", "Teste"));

        when(mockClientesDAO.getAllClients()).thenReturn(clientesMock);

        List<Clientes> resultado = clientesController.listarClientes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(mockClientesDAO, times(1)).getAllClients();
    }

    @Test
    public void testAtualizarCliente() throws Exception {
        int id = 1;
        String novoStatus = "Acordo";
        String observacao = "Atualizado";

        doNothing().when(mockClientesDAO).updateCliente(any(Clientes.class));

        clientesController.atualizarCliente(id, novoStatus, observacao);

        verify(mockClientesDAO, times(1)).updateCliente(any(Clientes.class));
    }
}
