/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.ClientesController;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.io.File;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Ariel
 */
public class TelaRelatorio extends JFrame {

    private final JTextField tfDataInicio;
    private final JTextField tfDataFim;
    private final JButton btGerarRelatorio;
    private final ClientesController clientesController;

    public TelaRelatorio() {
        setTitle("Relatório por Status");
        setSize(600, 110);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        clientesController = new ClientesController();

        JPanel panelDatas = new JPanel(new FlowLayout());
        tfDataInicio = new JTextField(10);
        tfDataFim = new JTextField(10);
        panelDatas.add(new JLabel("Data Início (YYYY-MM-DD): "));
        panelDatas.add(tfDataInicio);
        panelDatas.add(new JLabel("Data Fim (YYYY-MM-DD): "));
        panelDatas.add(tfDataFim);

        btGerarRelatorio = new JButton("Gerar Relatório");
        btGerarRelatorio.addActionListener(e -> gerarRelatorio());

        add(panelDatas, BorderLayout.NORTH);
        add(btGerarRelatorio, BorderLayout.SOUTH);
    }

    private void gerarRelatorio() {
        try {
            String dataInicio = tfDataInicio.getText();
            String dataFim = tfDataFim.getText();

            Map<String, Integer> relatorio = clientesController.gerarRelatorioPorStatus(dataInicio, dataFim);

            StringBuilder resultado = new StringBuilder("Relatório de Alterações por Status:\n");
            for (Map.Entry<String, Integer> entry : relatorio.entrySet()) {
                resultado.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            String pdfPath = clientesController.gerarRelatorioPDF(dataInicio, dataFim, relatorio);

            exibirPDF(pdfPath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exibirPDF(String pdfPath) {
        try {
            File pdfFile = new File(pdfPath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                JOptionPane.showMessageDialog(this, "Visualização de PDF não suportada.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir o PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
