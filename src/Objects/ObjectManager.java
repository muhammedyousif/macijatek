package Objects;

import entities.Scout;
import gamestate.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ObjectManager {
    private Playing playing;
    private BufferedImage basketpic;
    private ArrayList<Basket> baskets;
    public ObjectManager(Playing playing){
        this.playing=playing;
        addBaskets(playing.getManager().getLevel());
        loadimgs();
    }

    public boolean isFinished(){
        for (Basket b : baskets){
            if (b.isActive()){
                return false;
            }
        }
        return true;
    }
    public void addBaskets(Level level) {
        baskets=LoadSave.getBaskets(level.getImg());
    }
    public void checkObjectTouched(Rectangle2D.Float hitbox){
        for (Basket basket : baskets){
            if (basket.isActive()){
                if (hitbox.intersects(basket.getHitbox()))
                    basket.setActive(false);
            }
        }
        if (isFinished()){
            playing.setLevelCompleted(true);
        }
    }


    private void loadimgs() {
        BufferedImage basketimg= LoadSave.get_sprite(LoadSave.BASKET_SPRITE);
        basketpic=basketimg;
    }
    public void draw(Graphics g,int xLvlOffset){
        for (Basket basket : baskets) {
            if (basket.isActive()) {
                g.drawImage(basketpic,
                        (int) (basket.getHitbox().x - basket.getxDrawOffset() - xLvlOffset),
                        (int) (basket.getHitbox().y - basket.yDrawOffset),
                        null);
            }
        }
    }

}
