package levels;

import Gamepack.Game;
import entities.Scout;
import utilz.HelpMethods;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.getLevelData;
import static utilz.HelpMethods.*;

public class Level
{
    private int[][] lvldata;
    private BufferedImage img;
    private ArrayList<Scout> scouts;
    private int lvlTilesWide ;
    private int maxTilesOffset;
    private int maxLvlOffset= maxTilesOffset*Game.TILES_SIZE;


    public Level(BufferedImage img){
      this.img = img;
      CreateLevelData();
      createEnemies();
      calculateLevelOffset();
    }

    private void calculateLevelOffset() {
        lvlTilesWide=img.getWidth();
        maxTilesOffset=lvlTilesWide-Game.TILES_IN_WIDTH;
        maxLvlOffset=Game.TILES_SIZE*maxTilesOffset;
    }

    private void createEnemies() {
        scouts= HelpMethods.getScouts(img);
    }

    private void CreateLevelData() {
        lvldata=getLevelData(img );
    }

    public int getSpriteIndex(int x,int y){
        return lvldata[y][x];
    }

    public int[][] getLvldata() {
        return lvldata;
    }

    public ArrayList<Scout> getScouts() {
        return scouts;
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getLvlTilesWide() {
        return lvlTilesWide;
    }

    public int getMaxTilesOffset() {
        return maxTilesOffset;
    }

    public int getMaxLvlOffset() {
        return maxLvlOffset;
    }
}
