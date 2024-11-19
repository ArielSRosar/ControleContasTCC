/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ClientesDAO;
import Model.Clientes;
import java.util.List;

/**
 *
 * @author Ariel
 */
public class ClientesController {

    private final ClientesDAO dao;

    public ClientesController() {
        dao = new ClientesDAO();
    }

    public void salvarCliente(String nome, String cpf, String status, String observacao) throws Exception {
        if (nome.isEmpty() || cpf.isEmpty()) {
            throw new Exception("Preencha os campos obrigatórios!");
        }
        int idStatus = obterStatusId(status);
        Clientes cliente = new Clientes();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setIdStatus(idStatus);
        cliente.setObservacao(observacao);

        dao.save(cliente);
    }

    private int obterStatusId(String status) {
        switch (status) {
            case "Negativado":
                return 0;
            case "CDL":
                return 1;
            case "Pequenas Causas":
                return 2;
            case "Acordo":
                return 3;
            default:
                return -1;
        }
    }

    public Clientes buscarClientePorId(int id) {
        return dao.findById(id);
    }

    public List<Clientes> pesquisarClientePorNome(String nome) {
        return dao.findByName(nome);
    }

    public List<Clientes> listarClientes() {
        return dao.getAllClients();
    }
}
