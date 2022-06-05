package bilboard;

import interfaces.IBillboard;
import interfaces.IManager;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.*;

public class Main extends JFrame{

    Registry registry;
    IManager manager;
    String managerName = IManager.class.getName();
    int id;
    boolean onOff = true;

    IBillboard bilboard;
    Bilboard bilboardImpl;
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton ONButton;
    private JButton OFFButton;
    private JLabel leftTimeAd;
    private JLabel leftTimeOnBoard;

    private void runBilboard(){
        System.setProperty("javax.net.ssl.keyStorePassword","bilboard");
        System.setProperty("javax.net.ssl.trustStore","keystoreB");
        System.setProperty("javax.net.ssl.trustStorePassword","bilboard");

        try {
            bilboardImpl = new Bilboard(2);
            bilboard = (IBillboard) UnicastRemoteObject.exportObject(bilboardImpl, 0);
            registry = LocateRegistry.getRegistry("localhost", 50000, new SslRMIClientSocketFactory());
            manager = (IManager) registry.lookup(managerName);

            id = manager.bindBillboard(bilboard);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public Main() {
        runBilboard();

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                try {
                    manager.unbindBillboard(id);
                } catch (RemoteException ex) {
                    System.exit(0);
                }
            }
        });

        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<Advert> copy = new ArrayList<>(bilboardImpl.adverts);
                Collections.reverse(copy);
                if(copy.size() == 0) textArea1.setText("");
                leftTimeAd.setText("");
                leftTimeOnBoard.setText("");
                for(Advert ad : copy){
                    Duration seconds = bilboardImpl.seconds;
                    while(seconds.getSeconds() > 0) {
                        if (ad.leftSec.getSeconds() <= 0) {
                            try {
                                bilboardImpl.removeAdvertisement(ad.id);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        if (!bilboardImpl.adverts.contains(ad)) break;
                        while (!onOff){
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        textArea1.setText(ad.advertText);
                        leftTimeAd.setText(String.valueOf(ad.leftSec.getSeconds()));
                        leftTimeOnBoard.setText(String.valueOf(seconds.getSeconds()));
                        ad.leftSec = ad.leftSec.minusMillis(10);
                        seconds = seconds.minusMillis(10);
                    }
                }
            }
        }).start();

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        ONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    onOff = bilboardImpl.start();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        OFFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    onOff = bilboardImpl.stop();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}
