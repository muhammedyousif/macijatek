package gamestate;

import Gamepack.Game;
import Objects.ObjectManager;
import entities.EnemyManager;
import entities.YogiBear;
import levels.Level;
import levels.LevelManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Playing extends State implements Statemethods {
    private YogiBear bear;
    private LevelManager manager;
    private EnemyManager enemyManager;
    private ObjectManager objManager;
    private int xLvlOffset;
    private int leftBorder=(int) (0.45 * Game.GAME_WIDTH);
    private int rightBorder=(int) (0.55 * Game.GAME_WIDTH);
    private int maxLvlOffset;
    private boolean levelCompleted=false;
    private boolean gameOver=false;
    private long startTime;
    private boolean stopwatchRunning = false;


    public Playing(Game game) {

        super(game);
        initClasses();
        calculateLvlOffset();
        loadStartLevel();
        startTime = System.currentTimeMillis();
        stopwatchRunning = true;

    }

    public void calculateLvlOffset() {
        maxLvlOffset=manager.getLevel().getMaxLvlOffset();
    }

    public void loadNxtLevel(){
        resetall();
        manager.loadNextlevel();
        //bear.setSpawn(manager.getLevel().getPlayerSpawn());

    }

    private void resetall() {

        gameOver = false;
        levelCompleted = false;
        bear.resetAll();
        enemyManager.resetAllEnemies();
    }

    private void loadStartLevel() {
        enemyManager.addEnemies(manager.getLevel());
    }

    private void initClasses() {
        manager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objManager=new ObjectManager(this);
        bear=new YogiBear(200f,200f,160,200,this);
        bear.loadLvlData(manager.getLevel().getLvldata());
    }
    public void WindowFocusLost() {
        bear.resetDirBool();
    }
    public YogiBear getPlayer(){
        return this.bear;
    }

    @Override
    public void update() {
        manager.update();
        bear.update();
        enemyManager.update(manager.getLevel().getLvldata(),bear);
        checkCloseToBorder();
        if (levelCompleted){
            loadNxtLevel();
        }
        if (gameOver) {
            resetall();
            manager.reset();
            game.getPlaying().stopStopwatch();
        }
    }

    public YogiBear getBear() {
        return bear;
    }

    public LevelManager getManager() {
        return manager;
    }
    public void setMaxLevelOffset(int levelOffset){
        maxLvlOffset=levelOffset;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    private void checkCloseToBorder() {
        int playerx=(int) bear.getHitbox().x;
        int diff=playerx-xLvlOffset;
        if (diff>rightBorder){
            xLvlOffset+=diff-rightBorder;
        } else if (diff<leftBorder) {
            xLvlOffset+=diff-leftBorder;
        }

        if (xLvlOffset > maxLvlOffset)
            xLvlOffset = maxLvlOffset;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    @Override
    public void test() {

    }

    @Override
    public void draw(Graphics g) {

        manager.draw(g,xLvlOffset);
        objManager.draw(g,xLvlOffset);
        bear.render(g,xLvlOffset);
        enemyManager.draw(g,xLvlOffset);
        if (stopwatchRunning) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            String timeString = formatTime(elapsedTime);
            drawStopwatch(g, timeString);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                bear.setLeft(true);
                break;
            case KeyEvent.VK_D:
                bear.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                bear.setJump(true);
                break;

        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                bear.setLeft(false);
                break;
            case KeyEvent.VK_D:
                bear.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                bear.setJump(false);
                break;
        }

    }

    public ObjectManager getObjManager() {
        return objManager;
    }

    public void checkBasketContact(Rectangle2D.Float hitbox) {
        objManager.checkObjectTouched(hitbox);
    }

    public boolean isLevelCompleted() {
        return levelCompleted;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.levelCompleted = levelCompleted;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    public void stopStopwatch() {
        startTime=System.currentTimeMillis();
    }
    private String formatTime(long millis) {
        long secondsTotal = millis / 1000;
        long minutes = secondsTotal / 60;
        long seconds = secondsTotal % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void drawStopwatch(Graphics g, String timeString) {
        // Set font and color
        g.setColor(Color.WHITE); // Change color as needed
        g.setFont(new Font("Monospaced", Font.BOLD, 20)); // Change font as needed

        // Get the top right corner
        int x = 40; // Adjust as needed
        int y = 110; // Adjust as needed

        g.drawString(timeString, x, y);
    }



}
