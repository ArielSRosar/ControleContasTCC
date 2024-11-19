/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.ClientesController;
import Controller.ParcelasController;
import Model.Parcelas;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ariel
 */
public class TelaObservacao extends JFrame {

    private final int idCliente;
    private final ClientesController clientesController;
    private final ParcelasController parcelasController;
    private final JTextArea taObservacao;
    private final JTable tableParcelas;
    private final JComboBox<String> cbStatus;
    private final JButton btSalvar;

    public TelaObservacao(int idCliente, String observacao, String statusAtual) {
        setTitle("Observação do Cliente");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        this.idCliente = idCliente;
        this.clientesController = new ClientesController();
        this.parcelasController = new ParcelasController();

        taObservacao = new JTextArea(observacao);
        JScrollPane scrollPaneObservacao = new JScrollPane(taObservacao);
        scrollPaneObservacao.setBorder(BorderFactory.createTitledBorder("Observação"));

        tableParcelas = new JTable();
        configurarTabelaParcelas();
        JScrollPane scrollPaneParcelas = new JScrollPane(tableParcelas);
        scrollPaneParcelas.setBorder(BorderFactory.createTitledBorder("Parcelas"));

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbStatus = new JComboBox<>(new String[]{"Negativado", "CDL", "Pequenas Causas", "Acordo"});
        cbStatus.setSelectedItem(statusAtual);
        btSalvar = new JButton("Salvar Alterações");
        btSalvar.addActionListener(e -> salvarAlteracoes());

        panelInferior.add(new JLabel("Status: "));
        panelInferior.add(cbStatus);
        panelInferior.add(btSalvar);

        add(scrollPaneObservacao, BorderLayout.NORTH);
        add(scrollPaneParcelas, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        carregarParcelas(idCliente);
    }

    private void configurarTabelaParcelas() {
        tableParcelas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Parcela", "Qtd. Parcelas", "Valor Total", "Data Emissão"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        });
    }

    private void carregarParcelas(int idCliente) {
        List<Parcelas> parcelasList = parcelasController.buscarParcelasPorClienteId(idCliente);
        DefaultTableModel model = (DefaultTableModel) tableParcelas.getModel();
        model.setRowCount(0);

        for (Parcelas parcela : parcelasList) {
            model.addRow(new Object[]{
                parcela.getIdParcela(),
                parcela.getQuantidadeParcelas(),
                parcela.getValorTotal(),
                parcela.getDataEmissao()
            });
        }
    }

    private void salvarAlteracoes() {
        try {
            String novaObservacao = taObservacao.getText();
            String novoStatus = (String) cbStatus.getSelectedItem();

            clientesController.atualizarCliente(idCliente, novaObservacao, novoStatus);

            JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Fecha a tela
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar alterações: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
