import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class DisplayWindow extends JFrame {

    private CustomPanel paintPan;
    private ScriptEngine scriptEngine;
    private ScriptObjectMirror a;
    private Thread t;
    private boolean end = false;

    public DisplayWindow(int sizeX, int sizeY, int speed, int sqSize, boolean ants) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        setTitle("Cell Automat");
        int y1 =  sqSize*sizeY+75;
        int x1 = sqSize*sizeX+35;

        setSize(x1, y1);
        setLayout(new BorderLayout());

        paintPan = new CustomPanel(sqSize);
        paintPan.setBorder(new EmptyBorder(0, 0, 0, 0));

        scriptEngine = new ScriptEngineManager().getEngineByName("Nashorn");
        if(ants) scriptEngine.eval(new FileReader("sc1.js"));
        else scriptEngine.eval(new FileReader("sc.js"));

        Invocable invocable = (Invocable) scriptEngine;

        Object[][] o = new Object[sizeY][sizeX];
        Random r = new Random();
        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j < sizeX; j++){
                if(ants) o[i][j] = 0;
                else o[i][j] = r.nextInt(2);
            }
        }

        if(ants) {
            Object[][] obj1 = new Object[15][3];
            for (int i = 0; i < 5; i++) {
                obj1[i][2] = (r.nextInt(4) + 2); //kolor
                obj1[i][1] = r.nextInt(sizeX); //x
                obj1[i][0] = r.nextInt(sizeY); //y
            }
            a = (ScriptObjectMirror) invocable.invokeFunction("nextGeneration", new Object[]{o, obj1});
        }else{
            a = (ScriptObjectMirror) invocable.invokeFunction("nextGeneration", new Object[]{o});
        }

        paintPan.setA(a);

        add(paintPan, BorderLayout.CENTER);

        t = new Thread(() -> {
            while (!end){
                try {
                    repaint();
                    Thread.sleep(speed);
                    if(ants) {
                        a = (ScriptObjectMirror) invocable.invokeFunction("nextGeneration", new Object[]{
                                a.values().toArray()[0],
                                a.values().toArray()[1]
                        });
                    }else{
                        a = (ScriptObjectMirror) invocable.invokeFunction("nextGeneration", new Object[]{
                                a.values().toArray()[0]
                        });
                    }
                    paintPan.setA(a);
                } catch (InterruptedException | NoSuchMethodException | ScriptException ex) {
                    ex.printStackTrace();
                }
            }
        });
        t.start();

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                end = true;
                try {
                    t.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                paintPan = null;
                scriptEngine = null;
                a = null;
                t = null;
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
