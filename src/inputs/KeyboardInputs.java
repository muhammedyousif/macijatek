package inputs;

import Gamepack.GamePanel;
import gamestate.Gamestate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilz.Constants.Directions.*;

public class KeyboardInputs implements KeyListener
{
    private GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            default:
                break;

        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state){
            case MENU:

                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;
        }

    }
}
