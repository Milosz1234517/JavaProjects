package project;

import processing.Status;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;

public class StatusListener implements processing.StatusListener {

    HashMap<Integer, Integer> statusTable;

    public StatusListener() {
        statusTable = new HashMap<>();
    }

    @Override
    public void statusChanged(Status s) {
        if(statusTable.containsKey(s.getTaskId())){
            statusTable.replace(s.getTaskId(), s.getProgress());
        }else{
            statusTable.put(s.getTaskId(), s.getProgress());
        }
    }
}
