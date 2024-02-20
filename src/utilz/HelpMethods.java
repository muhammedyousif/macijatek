package utilz;

import Gamepack.Game;
import entities.Scout;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HelpMethods {
    public static Boolean canMoveHere(float x,float y,float width,float height,int[][] lvldata){
        if (IsSolid(x, y, lvldata) || IsSolid(x +width, y, lvldata) ||
                IsSolid(x, y + height, lvldata) || IsSolid(x +width, y + height, lvldata)) {
            return false;
        }
        for (float checkY = y; checkY <= y + height; checkY += Game.TILES_SIZE / 2) {
            if (IsSolid(x + width, checkY, lvldata)) {
                return false;
            }
        }


        // Additional checks along the edges
        for (float checkX = x; checkX <= x + width; checkX += (float) Game.TILES_SIZE / 2) {
            for (float checkY = y; checkY <= y + height; checkY += (float) Game.TILES_SIZE / 2) {
                if (IsSolid(checkX, checkY, lvldata)) {
                    return false;
                }
            }
        }

        return true;

    }
    private static boolean IsSolid(float x,float y,int[][] lvldata){
        int maxwidth = lvldata[0].length*Game.TILES_SIZE;
        if (x < 0 || x >= maxwidth)
            return true;
        if (y >= Game.GAME_HEIGHT) {
            return false;
        }
        if (y<0){
            return false;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        return isTileSolid((int)xIndex,(int)yIndex,lvldata);
    }
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox,float xspeed){
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xspeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width)+47;
            return tileXPos + xOffset-1;
        } else
            // Left
            return currentTile * Game.TILES_SIZE;
    }
    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox,float airspeed){
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airspeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (16 * Game.SCALE) -34;
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * Game.TILES_SIZE;


    }
    public static Boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][]lvldata){
        //check pixel below bottomleft bottom right corners
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvldata))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvldata))
                return false;

        return true;

    }
    public static Boolean IsFloor(Rectangle2D.Float hitbox,float xspeed,int[][] lvldata){
        if (xspeed>0){
            return IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvldata);

        }
        return IsSolid(hitbox.x + xspeed, hitbox.y + hitbox.height + 1, lvldata);
    }
    public static boolean isTileSolid(int xTile,int yTile,int[][]lvldata){

        int value = lvldata[yTile][xTile];

        if (value >= 80 || value < 0 || value != 21)
            return true;
        return false;

    }
    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (isTileSolid(xStart + i, y, lvlData)){
                System.out.println("false");
                return false;}
            if (!isTileSolid(xStart + i, y + 1, lvlData)){
                System.out.println("false");
                return false;}
        }
        System.out.println("True");

        return true;
    }

    public static boolean isSightClear(int[][] lvldata, Rectangle2D.Float scoutHitbox, Rectangle2D.Float bearHitbox, int enemyTileY) {
        int firstXTile=(int)(scoutHitbox.x/Game.TILES_SIZE);
        int SecondXTile=(int)(bearHitbox.x/Game.TILES_SIZE);
        if (firstXTile>SecondXTile){
            return IsAllTilesWalkable(SecondXTile, firstXTile, enemyTileY, lvldata);
        }
        else {
            return IsAllTilesWalkable(firstXTile, SecondXTile, enemyTileY, lvldata);
        }
    }
    public static int[][] getLevelData(BufferedImage img){
        int[][] lvldata=new int[img.getHeight()][img.getWidth()];

        for (int j =0;j<img.getHeight();j++){
            for(int i =0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int value = color.getRed();
                if (value<84){
                    value=0;
                    lvldata[j][i] = color.getRed();
                }
                lvldata[j][i] = color.getRed();
            }
        }
        return lvldata;
    }
    public static ArrayList<Scout> getScouts(BufferedImage map){
        ArrayList<Scout> list = new ArrayList<>();

        for (int j =0;j<map.getHeight();j++){
            for(int i =0;i<map.getWidth();i++){
                Color color = new Color(map.getRGB(i,j));
                int value = color.getGreen();
                if (value==0){
                    list.add(new Scout(i*Game.TILES_SIZE,j*Game.TILES_SIZE));
                }
            }
        }

        return list;
    }


}
