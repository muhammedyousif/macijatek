package Gamepack;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow{
    private JFrame jframe;
    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.pack();
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().WindowFocusLost();
            }
        });
    }


}
