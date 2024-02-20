package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Entity {
    public float x;
    public float y;
    protected int width;
    protected int height;
    protected Rectangle2D.Float hitbox;
    public Entity(float x,float y,int width,int height){
        this.x=x;
        this.y=y;
        this.height = height;
        this.width = width;
        initHitbox(x,y,width,height);
     }

    protected void initHitbox(float x,float y,int width,int height) {
        hitbox=new Rectangle2D.Float(x,y,width,height);
    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }
    protected void drawHitbox(Graphics g,int xLevelOffset){
        g.setColor(Color.red);
        g.drawRect((int)hitbox.x-xLevelOffset,(int) hitbox.y,(int) hitbox.width, (int)hitbox.height);
    }
    public void updateHitboxPosition(float newX) {
        hitbox.x = newX;
    }

}
