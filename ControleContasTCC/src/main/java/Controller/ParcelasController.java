/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ParcelasDAO;
import Model.Parcelas;
import java.util.List;

/**
 *
 * @author Ariel
 */
public class ParcelasController {

    private final ParcelasDAO parcelasDAO;

    public ParcelasController() {
        this.parcelasDAO = new ParcelasDAO();
    }

    public void salvarParcela(Parcelas parcela) {
        parcelasDAO.save(parcela);
    }

    public List<Parcelas> buscarParcelasPorClienteId(int idCliente) {
        return parcelasDAO.buscarParcelasPorClienteId(idCliente);
    }

    public Parcelas buscarParcelaPorId(int idParcela) {
        return parcelasDAO.findById(idParcela);
    }

    public void deletarParcela(int idParcela) {
        parcelasDAO.delete(idParcela);
    }
}
