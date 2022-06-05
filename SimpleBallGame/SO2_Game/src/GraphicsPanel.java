import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements MouseListener, MouseMotionListener {

    private ArrayList<EnemyBall> enemies = new ArrayList<>();
    private boolean mouseLeftButton = false;
    private final Label pointLabel;
    private Item item;

    public GraphicsPanel() {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        pointLabel =new Label("");
        pointLabel.setAlignment(Label.CENTER);
        pointLabel.setBounds(20,50,10,10);
        add(pointLabel);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        setBackground(Color.WHITE);
        Graphics2D drawImage = (Graphics2D) g;
        drawImage.setColor(Color.BLACK);
        drawImage.drawRect(50, 50, GameOptions.resolutionX - 150, GameOptions.resolutionY - 150);

        drawImage.setColor(Color.GREEN);
        drawImage.fillOval(GameOptions.mouseX - 25, GameOptions.mouseY - 25, 50, 50);

        if (item != null) {
            drawImage.setColor(item.color);
            drawImage.fillOval(item.x, item.y, 20, 20);
        }

        for(int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).end){
                enemies.remove(i);
                i--;
                continue;
            }
            drawImage.setColor(Color.RED);
            drawImage.fillOval(enemies.get(i).x, enemies.get(i).y, 10, 10);
        }

        if(GameOptions.endgame){
            drawImage.setColor(Color.BLACK);
            drawImage.setFont(new Font("TimesRoman", Font.PLAIN, 100));
            drawImage.drawString("Game Over!",(GameOptions.resolutionX - 250)/4,GameOptions.resolutionY/2 -10);
        }

    }

    public void addEnemy(EnemyBall enemyBall) {
        enemies.add(enemyBall);
    }

    public void setItem(Item item){
        this.item = item;
    }

    public void resetEnemies() {
        enemies = new ArrayList<>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftButton = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftButton = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftButton = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void move(MouseEvent e){
        if (mouseLeftButton) {
            double r = Math.sqrt(Math.pow(GameOptions.mouseX - e.getX(), 2) + Math.pow(GameOptions.mouseY - e.getY(), 2));

            if (item != null) {
                double treasure = Math.pow(item.x + 10 - GameOptions.mouseX, 2) + Math.pow(item.y + 10 - GameOptions.mouseY, 2);
                if (treasure <= 625) {
                    item.collectTreasure();
                    pointLabel.setText(String.valueOf(item.points));
                }
            }

            if (r < 25) {
                GameOptions.mouseX = e.getX();
                GameOptions.mouseY = e.getY();

                if (GameOptions.mouseX < 75) {
                    GameOptions.mouseX = 75;
                }

                if (GameOptions.mouseX > GameOptions.resolutionX - 125) {
                    GameOptions.mouseX = GameOptions.resolutionX - 125;
                }

                if (GameOptions.mouseY < 75) {
                    GameOptions.mouseY = 75;
                }

                if (GameOptions.mouseY > GameOptions.resolutionY - 125) {
                    GameOptions.mouseY = GameOptions.resolutionY - 125;
                }

                repaint();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(!GameOptions.control) {
                move(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(GameOptions.control) {
            move(e);
        }
    }
}
