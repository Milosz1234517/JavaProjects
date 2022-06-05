package server;

//import fabryka.RMISSLClientSocketFactory;
//import fabryka.RMISSLServerSocketFactory;

import interfaces.IManager;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.ArrayList;

public class Main extends JFrame{


    Registry registry;
    Manager managerImpl;
    IManager manager;
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JLabel duration;
    private JButton setButton;
    DefaultTableModel tableModel;

    private void runServer(){
        System.setProperty("javax.net.ssl.keyStore", "keystoreM");
        System.setProperty("javax.net.ssl.keyStorePassword","manager");
        System.setProperty("javax.net.ssl.trustStore","keystoreM");
        System.setProperty("javax.net.ssl.trustStorePassword","manager");
        System.setProperty("java.security.policy","p.policy");
        System.setSecurityManager(new SecurityManager());

        managerImpl = new Manager(Duration.ofSeconds(Long.parseLong(textField1.getText())));
        try{
            manager = (IManager) UnicastRemoteObject.exportObject(managerImpl, 0);
            registry = LocateRegistry.createRegistry(50000, new SslRMIClientSocketFactory(), new SslRMIServerSocketFactory());
            registry.rebind(IManager.class.getName(), manager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(()->{
            while (true) {
                tableModel = new DefaultTableModel();
                tableModel.addColumn("Connected Billboards");
                for (Integer i : new ArrayList<>(managerImpl.bilboards.keySet())) {
                    tableModel.addRow(new Object[]{"Billboard:"+i});
                }
                table1.setModel(tableModel);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public Main() {
        textField1.setText("10");

        runServer();

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerImpl.period = Duration.ofSeconds(Long.parseLong(textField1.getText()));
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}
