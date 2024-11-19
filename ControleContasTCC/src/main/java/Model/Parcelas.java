/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author Ariel
 */
public class Parcelas {

    private int quantidadeParcelas;
    private int idCliente;
    private int idParcela;
    private double valorTotal;
    private LocalDate dataEmissao;

    public Parcelas(int quantidadeParcelas, int idCliente, double valorTotal, LocalDate dataEmissao) {
        this.quantidadeParcelas = quantidadeParcelas;
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
        this.dataEmissao = dataEmissao;
    }

    public Parcelas() {
    }

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(int quantidadeParcelas) {
        if (quantidadeParcelas > 0) {
            this.quantidadeParcelas = quantidadeParcelas;
        } else {
            throw new IllegalArgumentException("A quantidade de parcelas deve ser maior que zero.");
        }
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getValorIndividual() {
        if (valorTotal != 0 && quantidadeParcelas != 0 && quantidadeParcelas > 0) {
            return valorTotal / quantidadeParcelas;
        }
        return 0.0;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        if (valorTotal >= 0) {
            this.valorTotal = valorTotal;
        } else {
            throw new IllegalArgumentException("O valor total deve ser positivo.");
        }
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public int getIdParcela() {
        return idParcela;
    }

    public void setIdParcela(int idParcela) {
        this.idParcela = idParcela;
    }

}
