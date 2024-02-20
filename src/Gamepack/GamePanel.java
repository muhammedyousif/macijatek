package Gamepack;

import inputs.KeyboardInputs;
import inputs.Mouseinputs;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Gamepack.Game.GAME_HEIGHT;
import static Gamepack.Game.GAME_WIDTH;
import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game){
        this.game=game;
        setPanelSize();
        Color customBlue = new Color(3, 140, 255);

        setBackground(Color.BLUE);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(new Mouseinputs(this));
        addMouseMotionListener(new Mouseinputs(this));
    }
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
       setMinimumSize(size);
       setPreferredSize(size);
       setMaximumSize(size);
       System.out.println("Size: "+ GAME_WIDTH +" x "+ Game.GAME_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
