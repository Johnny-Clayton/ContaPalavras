package arquivo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ContaPalavras {

    private JFrame frame;
    private JTable table;
    private JButton loadButton;
    private JFileChooser fileChooser;
    private DefaultTableModel tableModel;

    public ContaPalavras() {
        frame = new JFrame("Conta Palavras");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Palavra");
        tableModel.addColumn("Quantidade");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        loadButton = new JButton("Carregar Arquivo");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        fileChooser = new JFileChooser();

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(loadButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void openFile() {
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
            countWords(fileName);
        }
    }

    private void countWords(String fileName) {
        HashMap<String, Integer> contadorDePalavras = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String linha = reader.readLine();
            while (linha != null) {
                String[] palavras = linha.split(" ");
                for (String palavra : palavras) {
                    if (contadorDePalavras.containsKey(palavra)) {
                        int contador = contadorDePalavras.get(palavra);
                        contadorDePalavras.put(palavra, contador + 1);
                    } else {
                        contadorDePalavras.put(palavra, 1);
                    }
                }
                linha = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map.Entry<String, Integer>> entradas = new ArrayList<>(contadorDePalavras.entrySet());
        entradas.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        tableModel.setRowCount(0); 
        for (Map.Entry<String, Integer> entrada : entradas) {
            tableModel.addRow(new Object[]{entrada.getKey(), entrada.getValue()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ContaPalavras();
            }
        });
    }
}
