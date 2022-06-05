package gui;

import backend.BundleManager;
import backend.Question;
import backend.Templates;
import backend.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestFrameParametrized extends JDialog{
    private JTextField textFieldQuestion;
    private JPanel panel1;
    private JTextField textFieldAnswer;
    private JButton buttonNext;
    private JButton buttonCheck;
    private JProgressBar progressBar1;
    private JLabel labelStatus;
    private JLabel labelQuestion;
    private JComboBox<String> comboBox1;
    private JLabel labelQmark;
    private JLabel labelAnswer;
    private Test test;
    private int actualQuestionIdx;
    private int questionCount;
    private int points;


    public TestFrameParametrized(Frame frame, int questionCount) {
        super(frame, true);
        reloadLanguage();
        this.questionCount = questionCount;
        test = new Test(questionCount);
        actualQuestionIdx = 1;
        points = 0;
        labelStatus.setText(actualQuestionIdx + " / " + questionCount);
        comboBox1.addItem(Templates.questionTemplate("q1", "_"));
        comboBox1.addItem(Templates.questionTemplate("q2", "_"));
        comboBox1.addItem(Templates.questionTemplate("q3", "_"));
        comboBox1.addItem(Templates.questionTemplate("q4", "_"));
        comboBox1.addItem(Templates.questionTemplate("q5", "_"));
        buttonNext.setEnabled(false);
        setQuestion();

        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonNext.setEnabled(false);
                buttonCheck.setEnabled(true);
                if(actualQuestionIdx < questionCount) {
                    actualQuestionIdx++;
                    labelStatus.setText(actualQuestionIdx + " / " + questionCount);
                    labelAnswer.setText("");
                }else{
                    JOptionPane.showMessageDialog(null, BundleManager.numbersSpeling(points) + " " + questionCount);
                    dispose();
                }
            }
        });

        buttonCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonNext.setEnabled(true);
                buttonCheck.setEnabled(false);
                    Question question = test.generateQuestion(comboBox1.getSelectedIndex(), textFieldQuestion.getText());
                    labelAnswer.setText(question.getAnswer());
                    if (test.checkAnswer(textFieldAnswer.getText(), question.getShortAnswer())) {
                        progressBar1.setValue(progressBar1.getValue() + 1);
                        points++;
                    }
                    textFieldQuestion.setText("");
                    textFieldAnswer.setText("");

            }
        });

        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setQuestion();
            }
        });
        progressBar1.setMaximum(questionCount);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void setQuestion(){
        String[] tokenize = comboBox1.getSelectedItem().toString().split("_");
        labelQuestion.setText(tokenize[0]);
        labelQmark.setText(tokenize[1]);
    }

    private void reloadLanguage(){
        buttonNext.setText(BundleManager.resourceBundle.getString("next"));
        buttonCheck.setText(BundleManager.resourceBundle.getString("check"));
    }

}
