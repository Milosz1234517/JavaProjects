import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Item implements Runnable {

    long points = 0;
    Color color = Color.YELLOW;
    final JFrame mainFrame;


    int x = new Random().nextInt(GameOptions.resolutionX - 450) + 200;
    int y = new Random().nextInt(GameOptions.resolutionY - 450) + 200;

    public Item(JFrame generateEnemies) {
        this.mainFrame = generateEnemies;
    }

    @Override
    public void run() {

        points++;
        try {
            synchronized (this) {
                wait();
            }
            Random r = new Random();
            int power = r.nextInt(4);
            switch (power) {
                case 0:
                    GameOptions.end = true;
                    x = 0;
                    y = 0;
                    color = Color.MAGENTA;
                    synchronized (this) {
                        wait(3000);
                        color = Color.YELLOW;
                        wait(500);
                    }
                    GameOptions.end = false;
                    break;
                case 1:
                    GameOptions.shield = true;
                    x = 0;
                    y = 0;
                    color = Color.black;
                    synchronized (this) {
                        wait(3000);
                        color = Color.YELLOW;
                        wait(500);
                    }
                    synchronized (mainFrame) {
                        mainFrame.notifyAll();
                    }
                    GameOptions.shield = false;
                    break;
                case 2:
                    points ++;
                    break;
            }

            x = r.nextInt(GameOptions.resolutionX - 450) + 200;
            y = r.nextInt(GameOptions.resolutionY - 450) + 200;

            if(!GameOptions.endgame) new Thread(this).start();

        } catch (InterruptedException ignored) {

        }
    }

    public synchronized void collectTreasure() {
        notifyAll();
    }
}
