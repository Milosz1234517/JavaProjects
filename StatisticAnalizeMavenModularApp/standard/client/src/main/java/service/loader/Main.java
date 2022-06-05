package service.loader;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Choice;
import java.util.ArrayList;
import java.util.ServiceLoader;

public class Main extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTable table_1;
    private JComboBox<String> choice;
    private JScrollPane scrollPane;
    private JButton btnNewButton;
    private JScrollPane scrollPane_1;
    private JButton btnNewButton_1;
    private DefaultTableModel tableModel1;
    private DefaultTableModel tableModel2;

    private DataSet dataSet;
    private AnalysisService chosenService;
    private ArrayList<AnalysisService> services = new ArrayList<>();

    public void tableIni() {
        try {
            tableModel1 = new DefaultTableModel();
            tableModel2 = new DefaultTableModel();
            int cloumns = Integer.parseInt(JOptionPane.showInputDialog("Insert column number"));
            int rows = Integer.parseInt(JOptionPane.showInputDialog("Insert row number"));
            tableModel1.addColumn("RowID");
            tableModel2.addColumn("Alghoritm");
            for (int i = 0; i < cloumns; i++) {
                String name = JOptionPane.showInputDialog("Insert name of column");
                tableModel1.addColumn(name);
                tableModel2.addColumn(name);
            }
            for (int i = 0; i < rows; i++) {
                tableModel1.addRow(new Object[]{i});
            }
            table_1.setModel(tableModel2);
            table.setModel(tableModel1);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Table init error!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setResult() {
        new Thread(() -> {
            DataSet r = null;
            while (true) {
                try {
                    Thread.sleep(300);
                    r = chosenService.retrieve(true);
                } catch (AnalysisException | InterruptedException e) {
                    e.printStackTrace();
                }
                if (r != null) {
                    for (int i = 0; i < tableModel1.getColumnCount(); i++) {
                        for(int j = 0; j < r.getData()[i].length; j++) {
                            try {
                                tableModel2.setValueAt(r.getData()[i][j], j, i);
                            }catch (ArrayIndexOutOfBoundsException e){
                                tableModel2.insertRow(i, new Object[]{});
                                tableModel2.setValueAt(r.getData()[i][j], j, i);
                            }
                        }
                    }
                    table_1.setModel(tableModel2);
                    break;
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new Main();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void loadServices(){
        ServiceLoader<AnalysisService> serviceLoader = ServiceLoader.load(AnalysisService.class);
        for (AnalysisService s : serviceLoader) {
            choice.addItem(s.getName());
            services.add(s);
        }
    }

    public Main() {
        choice = new JComboBox<>();
        scrollPane = new JScrollPane();
        table = new JTable();
        table_1 = new JTable();
        btnNewButton = new JButton("Create Table");
        scrollPane_1 = new JScrollPane();
        btnNewButton_1 = new JButton("Calculate");
        contentPane = new JPanel();

        btnNewButton.addActionListener(e -> tableIni());

        btnNewButton_1.addActionListener(e -> {
            try {
                chosenService = services.get(choice.getSelectedIndex());
                dataSet = new DataSet();
                tableModel1 = (DefaultTableModel) table.getModel();
                String[][] data = new String[tableModel1.getColumnCount()][];
                for (int i = 0; i < tableModel1.getColumnCount(); i++) {
                    data[i] = new String[tableModel1.getRowCount()];
                    for (int j = 0; j < tableModel1.getRowCount(); j++) {
                        data[i][j] = tableModel1.getValueAt(j, i).toString();
                    }
                }
                dataSet.setData(data);
                try {
                    chosenService.submit(dataSet);
                    setResult();
                }catch (AnalysisException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Calculation fatal error", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        choice.setBounds(650, 400, 200, 20);
        contentPane.add(choice);

        btnNewButton_1.setBounds(300, 400, 180, 25);
        contentPane.add(btnNewButton_1);

        scrollPane.setBounds(25, 20, 825, 150);
        contentPane.add(scrollPane);
        scrollPane.setViewportView(table);

        scrollPane_1.setBounds(25, 200, 825, 50);
        contentPane.add(scrollPane_1);
        scrollPane_1.setViewportView(table_1);

        btnNewButton.setBounds(25, 400, 180, 25);
        contentPane.add(btnNewButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 520);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        loadServices();

        setVisible(true);

    }
}
