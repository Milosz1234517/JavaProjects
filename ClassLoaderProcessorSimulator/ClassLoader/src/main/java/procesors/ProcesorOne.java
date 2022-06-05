package procesors;

import processing.Processor;
import processing.Status;
import processing.StatusListener;
import project.ID;
import project.MainFrame;

import javax.swing.*;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcesorOne implements Processor {

    private String result = "";
    //int taskId;

    private synchronized int giveID() {
        //int id = taskId;
        //taskId++;
        //return id;
        return ID.ID++;
    }

    private synchronized void setResult(int id, String str) {
        result = "Task: " + id + " result: " + str + "\n";
        MainFrame.printResultS.accept(result);
    }

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {

            int id = giveID();
            StringBuilder str = new StringBuilder();
            char[] letters = task.toCharArray();
            int progres = 0;
            for (char letter : letters) {
                if (letter >= 'A' && letter <= 'Z') {
                    str.append((char) ((int) letter + 32));
                } else {
                    str.append(letter);
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }
                progres++;
                sl.statusChanged(new Status(id, progres * 100 / letters.length));
            }
            setResult(id, str.toString());
            executor.shutdown();

        });
        return true;
    }

    @Override
    public String getInfo() {
        return "Zamiana rozmiarow liter na male";
    }

    @Override
    public String getResult() {
        return result;
    }
}
