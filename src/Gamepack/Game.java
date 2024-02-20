package Gamepack;


import entities.YogiBear;
import gamestate.Gamestate;
import gamestate.Menu;
import gamestate.Playing;
import levels.LevelManager;
import utilz.LoadSave;

import java.awt.*;

public class Game implements Runnable {
    private GameWindow window;
    private GamePanel gamePanel;
    private Playing playing;
    private Menu menu;
    private Thread gameThread;
    private final int FPS_SET=120;
    private final int UPS_SET=200;

    public final static int TILES_DEFAULT_SIZE=16;
    public final static float SCALE = 3f;
    public final static int TILES_IN_WIDTH=30;
    public final static int TILES_IN_HEIGHT=14;
    public final static int TILES_SIZE= (int) (TILES_DEFAULT_SIZE*SCALE);
    public final static int GAME_WIDTH=TILES_SIZE*TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;



    public Game(){
        initClasses();
        gamePanel = new GamePanel(this);
        window = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();

    }

    private void initClasses() {
        playing=new Playing(this);
        menu=new Menu(this);
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
         gameThread.start();
    }
    public void update(){
        switch (Gamestate.state){
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            default:
                break;
        }
    }
    public void render(Graphics g){

        switch (Gamestate.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }

    }

    @Override
    public void run() {
        double timeframe = 1000000000.0/FPS_SET;
        double timeupdtae = 1000000000.0/UPS_SET;
        long previousTime= System.nanoTime();

        int updates = 0;
        int frames=0;
        long lastCheck = System.currentTimeMillis();
        double deltaU=0;
        double deltaF=0;
        while (true){
            long currentTime = System.nanoTime();
            deltaU+=(currentTime-previousTime)/timeupdtae;
            deltaF+=(currentTime-previousTime)/timeframe;

            previousTime=currentTime;
            if (deltaU>=1){
                update();
                updates++;
                deltaU--;
            }
            if (deltaF>=1){
                gamePanel.repaint();
                deltaF--;
                frames++;
            }
/*
            if(now-lastframe>=timeframe){
                gamePanel.repaint();
                lastframe=now;
                frames++;
            }
*/
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " +frames+"| UPS: "+updates+"|x: "+playing.getPlayer().getHitbox().x+" y: "+playing.getPlayer().getHitbox().y);
                frames = 0;
                updates=0;
            }
        }




    }

    public void WindowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING){
            playing.getPlayer().resetDirBool();
        }
    }

    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }
}
