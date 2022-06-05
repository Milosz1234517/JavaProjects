package gui;

import backend.BundleManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuFrame extends JFrame{


    private JPanel panel;
    private JButton buttonLanguage;
    private JButton buttonRandom;
    private JButton buttonParametrized;
    private JSlider sliderCount;
    private JLabel labelCount;

    public MenuFrame() {
        reloadLanguage();
        buttonParametrized.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTestParametrized();
            }
        });
        buttonRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTestRandom();
            }
        });
        buttonLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(BundleManager.lang.equals("en")) {
                    BundleManager.lang = "pl";
                    BundleManager.country = "PL";
                }else{
                    BundleManager.lang = "en";
                    BundleManager.country = "UK";
                }
                BundleManager.pref.put("lang", BundleManager.lang);
                BundleManager.pref.put("country", BundleManager.country);
                BundleManager.resourceBundle = ResourceBundle.getBundle("Language", new Locale(BundleManager.lang, BundleManager.country));
                reloadLanguage();
            }
        });
        sliderCount.setMinimum(1);
        sliderCount.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelCount.setText(Integer.toString(sliderCount.getValue()));
            }
        });
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void runTestRandom(){
        new TestFrameRandom(this, sliderCount.getValue());
    }

    private void runTestParametrized(){
        new TestFrameParametrized(this, sliderCount.getValue());
    }

    private void reloadLanguage(){
        buttonLanguage.setText(BundleManager.resourceBundle.getString("lan"));
        buttonRandom.setText(BundleManager.resourceBundle.getString("start"));
        buttonParametrized.setText(BundleManager.resourceBundle.getString("start1"));
    }
}
