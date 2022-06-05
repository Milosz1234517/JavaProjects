import java.awt.*;
import javax.swing.*;

public class DisplayGraphics extends JFrame{

    private final GraphicsPanel paintPan;
    Thread itemT;

    public DisplayGraphics(){
        setTitle("Ball Game");
        setSize(GameOptions.resolutionX, GameOptions.resolutionY);
        setLayout(new BorderLayout());

        paintPan = new GraphicsPanel();
        JButton testButon = new JButton("New Game");

        add(paintPan, BorderLayout.CENTER);
        add(testButon, BorderLayout.PAGE_START);

        JButton btnNewButton = new JButton("Change Control");
        btnNewButton.addActionListener(e -> GameOptions.control = !GameOptions.control);
        add(btnNewButton, BorderLayout.PAGE_END);

        testButon.addActionListener(arg0 -> newGame());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void newGame(){
        if(itemT != null){
            itemT.interrupt();
        }
        GameOptions.endgame = false;
        paintPan.resetEnemies();

        GameOptions.mouseX = 100;
        GameOptions.mouseY = 100;

        Thread geneT = new Thread(() -> {
            while (!GameOptions.endgame) {
                try {
                    if(GameOptions.shield){
                        synchronized (this){
                            wait();
                        }
                    }

                    Thread.sleep(500);

                    EnemyBall enemyBall = new EnemyBall(this);
                    paintPan.addEnemy(enemyBall);
                    new Thread(enemyBall).start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        geneT.start();

        Item item = new Item(this);
        itemT = new Thread(item);
        itemT.start();
        paintPan.setItem(item);
    }

}  