/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Clientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ariel
 */
public class ClientesDAO {

    private final Connection connection;

    public ClientesDAO() {
        this.connection = DataBase.getConnection();
    }

    public void save(Clientes clientes) {
        String sql = "INSERT INTO clientes (nome, id_status, cpf, observacoes) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, clientes.getNome());
            stmt.setInt(2, clientes.getIdStatus());
            System.out.println("RESULTADO DAO : " + clientes.getIdStatus());
            stmt.setString(3, clientes.getCpf());
            stmt.setString(4, clientes.getObservacao());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Clientes findById(int id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        Clientes clientes = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clientes = new Clientes();
                clientes.setId(rs.getInt("id_cliente"));
                clientes.setNome(rs.getString("nome"));
                clientes.setCpf(rs.getString("cpf"));
                clientes.setIdStatus(rs.getInt("id_status"));
                clientes.setObservacao(rs.getString("observacoes"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public List<Clientes> findByName(String name) {
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?";
        List<Clientes> clientesList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Clientes cliente = new Clientes();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setIdStatus(rs.getInt("id_status"));
                cliente.setObservacao(rs.getString("observacoes"));
                clientesList.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientesList;
    }

    public List<Clientes> getAllClients() {
        String sql = "SELECT * FROM clientes";
        List<Clientes> clientesList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Clientes cliente = new Clientes();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setIdStatus(rs.getInt("id_status"));
                cliente.setObservacao(rs.getString("observacoes"));
                clientesList.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientesList;
    }

    public void updateCliente(Clientes cliente) throws Exception {
        String sql = "UPDATE clientes SET observacoes = ?, id_status = ? WHERE id_cliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getObservacao());
            stmt.setInt(2, cliente.getIdStatus());
            stmt.setInt(3, cliente.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao atualizar cliente no banco de dados.");
        }
    }
}
