/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ClientesDAO;
import Model.Clientes;
import java.util.List;
import java.util.Map;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

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

    public void atualizarCliente(int idCliente, String novoStatus, String observacao) throws Exception {
        Clientes cliente = new Clientes();
        cliente.setId(idCliente);
        cliente.setIdStatus(obterStatusId(novoStatus));
        cliente.setObservacao(observacao);

        ClientesDAO dao = new ClientesDAO();
        dao.updateCliente(cliente);

    }

    public Map<String, Integer> gerarRelatorioPorStatus(String dataInicio, String dataFim) throws Exception {
        ClientesDAO dao = new ClientesDAO();
        return dao.gerarRelatorioPorStatus(dataInicio, dataFim);
    }

    public String gerarRelatorioPDF(String dataInicio, String dataFim, Map<String, Integer> relatorio) throws Exception {
        String filePath = "Relatorio_Status.pdf";

        PdfWriter writer = new PdfWriter(filePath);
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph("Relatório de Alterações por Status").setBold().setFontSize(16));
        document.add(new Paragraph("Intervalo: " + dataInicio + " a " + dataFim).setFontSize(12));
        document.add(new Paragraph("\n"));

        Table table = new Table(2);
        table.addCell(new Cell().add(new Paragraph("Status").setBold()));
        table.addCell(new Cell().add(new Paragraph("Total de Alterações").setBold()));

        if (relatorio.isEmpty()) {
            table.addCell(new Cell(1, 2).add(new Paragraph("Nenhum dado encontrado.").setItalic()));
        } else {
            for (Map.Entry<String, Integer> entry : relatorio.entrySet()) {
                table.addCell(new Cell().add(new Paragraph(entry.getKey())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(entry.getValue()))));
            }
        }

        document.add(table);

        document.close();

        return filePath;
    }
}
