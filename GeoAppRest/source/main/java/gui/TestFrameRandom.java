package gui;

import backend.BundleManager;
import backend.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TestFrameRandom extends JDialog{
    private JTextField textAnswer;
    private JPanel panel1;
    private JButton buttonCheckAnswer;
    private JButton buttonNext;
    private JLabel labelQuestion;
    private JProgressBar progressBar1;
    private JLabel labelAnswer;
    private JLabel labelStatus;
    private Test test;
    private int actualQuestionIdx;
    private int questionCount;
    private int points;

    public TestFrameRandom(Frame frame, int questionCount) {
        super(frame, true);
        this.questionCount = questionCount;
        test = new Test(questionCount);
        actualQuestionIdx = 0;
        points = 0;
        reloadLanguage();
        labelStatus.setText(actualQuestionIdx + " / " + questionCount);
        doNext();

        buttonCheckAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerCompare();
            }
        });

        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                if(actualQuestionIdx < questionCount) {
                    doNext();
                }else{
                    JOptionPane.showMessageDialog(null, BundleManager.numbersSpeling(points) + " " + questionCount);
                    dispose();
                }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        buttonNext.setEnabled(false);
        progressBar1.setMaximum(questionCount);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void reloadLanguage(){
        buttonNext.setText(BundleManager.resourceBundle.getString("next"));
        buttonCheckAnswer.setText(BundleManager.resourceBundle.getString("check"));
    }

    private void doNext() {
        try {
            test.generateRandomQuestion(actualQuestionIdx);
            labelQuestion.setText(test.getQuestions()[actualQuestionIdx].getContents());
            labelAnswer.setText("");
            actualQuestionIdx++;
            buttonNext.setEnabled(false);
            buttonCheckAnswer.setEnabled(true);
            labelStatus.setText(actualQuestionIdx + " / " + questionCount);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void answerCompare(){
        buttonNext.setEnabled(true);
        buttonCheckAnswer.setEnabled(false);
        if(test.checkAnswer(textAnswer.getText(), test.getQuestions()[actualQuestionIdx-1].getShortAnswer())){
            progressBar1.setValue(progressBar1.getValue()+1);
            points++;
        }
        labelAnswer.setText(test.getQuestions()[actualQuestionIdx-1].getAnswer());
        textAnswer.setText("");
    }


}
