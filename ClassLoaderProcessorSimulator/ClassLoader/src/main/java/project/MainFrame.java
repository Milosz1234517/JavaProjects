package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MainFrame extends JFrame{
    private JPanel panel;
    private JTable statusTable;
    private JTextField askArea;
    private JButton wykonajZadanieButton;
    private JList<String> list;
    private JButton zaladujKlaseButton;
    private JTextPane textPane1;
    private JTextArea resultArea;
    private JButton odswiezButton;
    private JButton wyladujButton;
    private JLabel descLabel;
    private DefaultTableModel tableModel1;
    private Path globalPath = Path.of(System.getProperty("user.dir"));
    private ArrayList<Class<?>> classes = new ArrayList<>();
    private Class<?> processorClass;
    private Object processor;
    private Method submitTaskMethod;
    private Method getResultMethod;
    private Method getInfoMethod;
    private StatusListener st = new StatusListener();
    public static Consumer<String> printResultS;

    public void tableIni(){
        tableModel1 = new DefaultTableModel();
        tableModel1.addColumn("Task ID");
        tableModel1.addColumn("Progres");
        statusTable.setModel(tableModel1);
    }


    public void tableUpdater(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(
                ()->{
                    SwingUtilities.invokeLater(() -> {
                        for (HashMap.Entry<Integer, Integer> entry : st.statusTable.entrySet()) {
                            Integer key = entry.getKey();
                            Integer value = entry.getValue();
                            try {
                                tableModel1.setValueAt(value, key, 1);
                            }catch (ArrayIndexOutOfBoundsException e){
                                tableModel1.insertRow(tableModel1.getRowCount(), new Object[]{key, value});
                            }
                        }
                        statusTable.setModel(tableModel1);
                    });
                    if(getResultMethod != null) {
//                        try {
//                            //resultArea.setText((String) getResultMethod.invoke(processor));
//                        } catch (IllegalAccessException | InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
                    }
                },
                1, 1, TimeUnit.MILLISECONDS);
    }

    public MainFrame() {
        printResultS  = s -> resultArea.setText(resultArea.getText() + s);
        tableIni();
        classes.clear();
        loadClassesF(globalPath);
        tableUpdater();

        wykonajZadanieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    submitTaskMethod.invoke(processor, askArea.getText(), st);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        });
        zaladujKlaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();

                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.showOpenDialog(panel);
                globalPath =  fc.getSelectedFile().toPath();

                classes.clear();
                loadClassesF(globalPath);
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    //tableIni();
                    //st.statusTable.clear();
                    processorClass = classes.get(list.getSelectedIndex());
                    Constructor<?> cp = processorClass.getConstructor();
                    processor = cp.newInstance();
                    submitTaskMethod = processorClass.getDeclaredMethod("submitTask", String.class, processing.StatusListener.class);
                    getResultMethod = processorClass.getDeclaredMethod("getResult");
                    getInfoMethod = processorClass.getDeclaredMethod("getInfo");
                    descLabel.setText((String) getInfoMethod.invoke(processor));
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        });
        list.addContainerListener(new ContainerAdapter() {
        });
        odswiezButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classes.clear();
                loadClassesF(globalPath);
            }
        });
        wyladujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classes = new ArrayList<>();
                processorClass = null;
                processor = null;
                submitTaskMethod = null;
                getResultMethod = null;
                getInfoMethod = null;
                list.setModel(new DefaultListModel<>());
                System.gc();
            }
        });
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    void loadClassesF(Path path){
        LoaderClass l = new LoaderClass();
        Stream<Path> list1;
        try {
            list1 = Files.list(path);
            Path[] listOfFiles = list1.toArray(Path[]::new);
            DefaultListModel<String> listModel = new DefaultListModel<>();

            for (Path file : listOfFiles) {
                String s = file.getFileName().toString();
                String[] tab = s.split("\\.");
                if(tab[tab.length-1].equals("class")) {
                    listModel.addElement(file.getFileName().toString());
                    classes.add(l.findClass(file.toString()));
                }
            }
            list.setModel(listModel);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
