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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String sql = "INSERT INTO clientes (id_cliente, nome, id_status, cpf, observacoes) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clientes.getId());
            stmt.setString(2, clientes.getNome());
            stmt.setInt(3, clientes.getIdStatus());
            stmt.setString(4, clientes.getCpf());
            stmt.setString(5, clientes.getObservacao());
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
        String sql = "UPDATE clientes SET id_status = ?, observacoes = ? WHERE id_cliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getIdStatus());
            stmt.setString(2, cliente.getObservacao());
            stmt.setInt(3, cliente.getId());
            stmt.executeUpdate();

            salvarStatusHistorico(cliente.getId(), obterNomeStatus(cliente.getIdStatus()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao atualizar cliente.");
        }
    }

    public void salvarStatusHistorico(int idCliente, String novoStatus) throws Exception {
        String sql = "INSERT INTO status_historico (id_cliente, novo_status) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.setString(2, novoStatus);
            stmt.executeUpdate();
            int rowsInserted = stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao salvar histórico de status.");
        }
    }

    private String obterNomeStatus(int idStatus) {
        switch (idStatus) {
            case 0:
                return "Negativado";
            case 1:
                return "CDL";
            case 2:
                return "Pequenas Causas";
            case 3:
                return "Acordo";
            default:
                return "Desconhecido";
        }
    }

    public Map<String, Integer> gerarRelatorioPorStatus(String dataInicio, String dataFim) throws Exception {
        String sql = "SELECT novo_status AS status, COUNT(DISTINCT id_cliente) AS total "
                + "FROM ( "
                + "    SELECT id_cliente, novo_status, MAX(data_alteracao) AS ultima_alteracao "
                + "    FROM status_historico "
                + "    WHERE data_alteracao BETWEEN ? AND ? "
                + "    GROUP BY id_cliente, novo_status "
                + ") AS subquery "
                + "GROUP BY status";

        Map<String, Integer> relatorio = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dataInicio + " 00:00:00");
            stmt.setString(2, dataFim + " 23:59:59");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String status = rs.getString("status");
                int total = rs.getInt("total");

                relatorio.put(status, total);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao gerar relatório.");
        }
        return relatorio;
    }
}
