package entities;

import gamestate.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] scoutarr;
    private ArrayList<Scout> scouts= new ArrayList<>();
    public EnemyManager(Playing playing){
        this.playing=playing;
        loadEnemyImages();
    }

    public void addEnemies(Level level) {
        scouts=level.getScouts();
    }
    public void resetAllEnemies() {
        for (Scout s : scouts)
            s.resetEnemy();
    }



    public void update(int[][] lvldata,YogiBear bear){
        for (Scout s : scouts){
            s.update(lvldata,bear);
        }
    }
    public void draw(Graphics g,int xLevelOffset){
        drawScouts(g,xLevelOffset);

    }

    private void drawScouts(Graphics g, int xLevelOffset) {

        for (Scout s : scouts) {
            if (s.getEnemyState()==RUNNINGLEFT){
                g.drawImage(scoutarr[s.getEnemyState()][s.getAniIndex()], (int) (s.getHitbox().x-70) - xLevelOffset-SCOUT_DRAWOFFSETX, (int) s.getHitbox().y- SCOUT_DRAWOFFSETY, SCOUT_WIDTH, SCOUT_HEIGHT, null);

                //s.drawHitbox(g,xLevelOffset);
            }
            else {
                g.drawImage(scoutarr[s.getEnemyState()][s.getAniIndex()], (int) s.getHitbox().x - xLevelOffset-SCOUT_DRAWOFFSETX, (int) s.getHitbox().y- SCOUT_DRAWOFFSETY, SCOUT_WIDTH, SCOUT_HEIGHT, null);
                //s.drawHitbox(g,xLevelOffset);
            }
        }
/*
        for (Scout s : scouts) {
            g.drawImage(
                    scoutarr[s.getEnemyState()][s.getAniIndex()],
                    (int) s.getHitbox().x - xLevelOffset - SCOUT_DRAWOFFSETX,
                    (int) s.getHitbox().y - SCOUT_DRAWOFFSETY,
                    SCOUT_WIDTH,
                    SCOUT_HEIGHT,
                    null
            );

            // Check if the scout is facing left or right
            if (s.getEnemyState()==RUNNINGLEFT) {
                // Adjust hitbox for scout facing left
                s.getHitbox().x -= SCOUT_DRAWOFFSETX;
            } else {
                // Adjust hitbox for scout facing right
                s.getHitbox().x += SCOUT_DRAWOFFSETX;
            }

            s.drawHitbox(g, xLevelOffset);

            // Reset hitbox position to its original value after drawing
            if (s.getEnemyState()==RUNNINGLEFT) {
                s.getHitbox().x += SCOUT_DRAWOFFSETX;
            } else {
                s.getHitbox().x -= SCOUT_DRAWOFFSETX;
            }
        }
*/

    }

    private void loadEnemyImages() {
        scoutarr=new BufferedImage[4][2];
        BufferedImage temp = LoadSave.get_sprite(LoadSave.SCOUT_SPRITE);
        for (int i =0;i<scoutarr.length;i++){
            for (int j =0;j<scoutarr[i].length;j++){
                scoutarr[i][j]=temp.getSubimage(j*SCOUT_WIDTH,i*SCOUT_HEIGHT,SCOUT_WIDTH,SCOUT_HEIGHT);
            }
        }
    }
}
