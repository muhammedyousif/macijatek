package levels;
import Gamepack.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private static ArrayList<Level> levels;
    private int levelIndex;
    public LevelManager(Game game){
        this.game=game;
        //levelSprite = LoadSave.get_sprite(LoadSave.LEVEL_ATLAS);
        importOutsideSprite();
        levels= new ArrayList<>();
        buildAllLevels();
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels= LoadSave.getAllLevels();
        for (BufferedImage img:allLevels){
            levels.add(new Level(img));
        }
    }

    private void importOutsideSprite() {
        BufferedImage img = LoadSave.get_sprite(LoadSave.LEVEL_ATLAS);
        levelSprite=new BufferedImage[80];
        for(int i = 0;i<5;i++){
            for(int j=0;j<16;j++){
                int index = i*16+j;
                levelSprite[index] = img.getSubimage(j*16,i*16,16,16);
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset){
        for (int j=0;j< Game.TILES_IN_HEIGHT;j++){
            for(int i=0;i< levels.get(levelIndex).getLvldata()[0].length;i++){
                int index = levels.get(levelIndex).getSpriteIndex(i,j);
                g.drawImage(levelSprite[index],i*Game.TILES_SIZE-xLvlOffset,j*Game.TILES_SIZE,Game.TILES_SIZE,Game.TILES_SIZE,null);

            }
        }
    }
    public void update(){

    }
    public Level getLevel(){
        return levels.get(levelIndex);
    }
    public int getLevelAmount(){
        return levels.size();
    }

    public void loadNextlevel() {

            levelIndex++;
            System.out.println(levelIndex);
            if (levelIndex >= levels.size()){
                levelIndex = 0;
                System.out.println("FINISH");

            }
            System.out.println("COMPLETED GAME");
            Level newLevel = levels.get(levelIndex);
            game.getPlaying().getEnemyManager().addEnemies(newLevel);
            game.getPlaying().getPlayer().loadLvlData(newLevel.getLvldata());
            game.getPlaying().setMaxLevelOffset(newLevel.getMaxLvlOffset());
            game.getPlaying().getObjManager().addBaskets(newLevel);
            game.getPlaying().stopStopwatch();


    }

    public void setLevelIndex(int levelIndex) {
        this.levelIndex = levelIndex;
    }

    public void reset() {
        levelIndex=0;
        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().addEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLvldata());
        game.getPlaying().getObjManager().addBaskets(newLevel);

    }

}
