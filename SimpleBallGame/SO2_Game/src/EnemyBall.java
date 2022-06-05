import javax.swing.*;
import java.util.Random;

public class EnemyBall implements Runnable {
    int x;
    int y;
    JFrame frame;
    boolean end;

    public EnemyBall(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        Random r = new Random();
        x = r.nextInt(GameOptions.resolutionX - 450) + 200;
        y = r.nextInt(GameOptions.resolutionY - 450) + 200;

        int x1 = (r.nextBoolean() ? 1 : -1);
        int y1 = (r.nextBoolean() ? 1 : -1);


        while (true) {
            try {
                if (GameOptions.endgame) {
                    break;
                }
                Thread.sleep(10);

                x += x1;
                y += y1;

                if (x == 50 || x == GameOptions.resolutionX - 110) x1 = -x1;
                if (y == 50 || y == GameOptions.resolutionY - 110) y1 = -y1;

                if (!GameOptions.shield) {
                    double o = Math.pow(x + 5 - GameOptions.mouseX, 2) + Math.pow(y + 5 - GameOptions.mouseY, 2);

                    if (o <= 625) {
                        if (GameOptions.end) end = true;
                        else GameOptions.endgame = true;
                        break;
                    }
                }

                frame.repaint();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
