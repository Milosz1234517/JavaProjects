import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CustomPanel extends JPanel implements MouseListener, MouseMotionListener {

    ScriptObjectMirror a;
    private boolean mouseLeftButton = false;
    int sqSize;

    public CustomPanel(int sqSize) {
        super();
        this.sqSize = sqSize;
    }

    public void setA(ScriptObjectMirror a) {
        this.a = a;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        setBackground(Color.WHITE);
        Graphics2D drawImage = (Graphics2D) g;
        drawImage.setColor(Color.BLACK);
        int x = 10;
        int y = 10;
        for (Object i : (Object[]) a.values().toArray()[0]) {
            for (Object u : ((Object[]) i)) {
//        for (Object i : a.values()) {
//            for (Object u : ((ScriptObjectMirror) i).values()) {
                if((Integer) u == 1) {
                    drawImage.setColor(Color.GREEN);
                    drawImage.fillRect(x, y, sqSize, sqSize);
                }
                drawImage.setColor(Color.BLACK);
                drawImage.drawRect(x, y, sqSize, sqSize);
                x += sqSize;
            }
            x = 10;
            y += sqSize;
        }

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
//        if (mouseLeftButton) {
//            double r = Math.sqrt(Math.pow(GameOptions.mouseX - e.getX(), 2) + Math.pow(GameOptions.mouseY - e.getY(), 2));
//
//            if (item != null) {
//                double treasure = Math.pow(item.x + 10 - GameOptions.mouseX, 2) + Math.pow(item.y + 10 - GameOptions.mouseY, 2);
//                if (treasure <= 625) {
//                    item.collectTreasure();
//                    pointLabel.setText(String.valueOf(item.points));
//                }
//            }
//
//            if (r < 25) {
//                GameOptions.mouseX = e.getX();
//                GameOptions.mouseY = e.getY();
//
//                if (GameOptions.mouseX < 75) {
//                    GameOptions.mouseX = 75;
//                }
//
//                if (GameOptions.mouseX > GameOptions.resolutionX - 125) {
//                    GameOptions.mouseX = GameOptions.resolutionX - 125;
//                }
//
//                if (GameOptions.mouseY < 75) {
//                    GameOptions.mouseY = 75;
//                }
//
//                if (GameOptions.mouseY > GameOptions.resolutionY - 125) {
//                    GameOptions.mouseY = GameOptions.resolutionY - 125;
//                }
//
//                repaint();
//            }
//        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        if(!GameOptions.control) {
//            move(e);
//        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        if(GameOptions.control) {
//            move(e);
//        }
    }
}
