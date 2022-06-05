package client;

import interfaces.IClient;
import interfaces.IManager;
import interfaces.Order;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;

public class Main {

    Registry registry;
    IManager manager;
    String managerName = IManager.class.getName();

    Client clientImpl;
    IClient klient;
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton makeOrderButton;
    private JList<Integer> list1;
    private JTextField textField1;
    private DefaultListModel<Integer> listModel;

    private void setClient(){
        clientImpl = new Client();
        try{
        klient = (IClient) UnicastRemoteObject.exportObject(clientImpl, 0);
        registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostName(), 50000, new SslRMIClientSocketFactory());
        manager = (IManager) registry.lookup(managerName);
        } catch (RemoteException | NotBoundException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void makeOrder(String text){
        try {
            Order order = new Order();
            order.client = klient;
            order.advertText = text;
            order.displayPeriod = Duration.ofSeconds(Integer.parseInt(textField1.getText()));

            if(manager.placeOrder(order)) {
                listModel.addElement(clientImpl.id);
                list1.setModel(listModel);
            }else JOptionPane.showMessageDialog(null, "Brak miejsca!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Main() {
        listModel = new DefaultListModel<>();
        setClient();
        makeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeOrder(textArea1.getText());
            }
        });
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if(!manager.withdrawOrder(list1.getSelectedValue())) JOptionPane.showMessageDialog(null, "Zamowienie nieaktywne!");
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore", "keystoreC");
        System.setProperty("javax.net.ssl.keyStorePassword","client");
        System.setProperty("javax.net.ssl.trustStore","keystoreC");
        System.setProperty("javax.net.ssl.trustStorePassword","client");
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
