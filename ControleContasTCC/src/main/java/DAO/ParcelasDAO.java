package DAO;

import Model.Parcelas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ParcelasDAO {

    private final Connection connection;

    public ParcelasDAO() {
        this.connection = DataBase.getConnection();
    }

    public void save(Parcelas parcelas) {
        String sql = "INSERT INTO parcelas (id_cliente, quantidade_parcelas, valor_total, data_emissao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, parcelas.getQuantidadeParcelas());
            stmt.setInt(2, parcelas.getIdCliente());
            stmt.setDouble(3, parcelas.getValorTotal());
            stmt.setDate(4, java.sql.Date.valueOf(parcelas.getDataEmissao()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Parcelas findById(int idParcela) {
        String sql = "SELECT * FROM parcelas WHERE id_parcelas = ?";
        Parcelas parcelas = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idParcela);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                parcelas = mapResultSetToParcelas(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parcelas;
    }

    public void delete(int idParcela) {
        String sql = "DELETE FROM parcelas WHERE id_parcelas = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idParcela);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Parcelas mapResultSetToParcelas(ResultSet rs) throws Exception {
        Parcelas parcelas = new Parcelas();
        parcelas.setIdParcela(rs.getInt("id_parcelas"));
        parcelas.setIdCliente(rs.getInt("id_cliente"));
        parcelas.setQuantidadeParcelas(rs.getInt("quantidade_parcelas"));
        parcelas.setValorTotal(rs.getDouble("valor_total"));
        parcelas.setDataEmissao(rs.getDate("data_emissao").toLocalDate());
        return parcelas;
    }
    
    public List<Parcelas> buscarParcelasPorClienteId(int idCliente) {
    List<Parcelas> parcelasList = new ArrayList<>();
    String sql = "SELECT * FROM parcelas WHERE id_cliente = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, idCliente);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Parcelas parcela = new Parcelas();
            parcela.setIdParcela(rs.getInt("id_parcela"));
            parcela.setIdCliente(rs.getInt("id_cliente"));
            parcela.setQuantidadeParcelas(rs.getInt("quantidade_parcelas"));
            parcela.setValorTotal(rs.getDouble("valor_total"));
            parcela.setDataEmissao(rs.getDate("data_emissao").toLocalDate());
            parcelasList.add(parcela);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return parcelasList;
}

}
