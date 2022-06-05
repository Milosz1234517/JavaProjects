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

public class ProcesorThree implements Processor {

    private String result = "";
    //int taskId = 0;

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
            String[] additions = task.split("\\+");
            int wynik = 0;
            int progres = 0;
            for (String addition : additions) {
                wynik += Integer.parseInt(addition);
                try {
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }

                progres++;
                sl.statusChanged(new Status(id, progres * 100 / additions.length));
            }
            setResult(id, String.valueOf(wynik));
            executor.shutdown();
        });
        return true;
    }

    @Override
    public String getInfo() {
        return "Dodawanie";
    }

    @Override
    public String getResult() {
        return result;
    }
}
