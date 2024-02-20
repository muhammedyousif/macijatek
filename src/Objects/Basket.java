package Objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Basket {
    protected int x,y;
    protected Rectangle2D.Float hitbox;
    protected int xDrawOffset,yDrawOffset;
    protected boolean active=true;
    public Basket(int x,int y){
        this.x=x;
        this.y=y;
        xDrawOffset=6;
        yDrawOffset=20;
        initHitbox(60,70);
    }
    protected void initHitbox(int width,int height) {
        hitbox=new Rectangle2D.Float(x,y,width,height);
    }
    public void drawHitbox(Graphics g,int xLevelOffset){
        g.setColor(Color.red);
        g.drawRect((int)hitbox.x-xLevelOffset,(int) hitbox.y,(int) hitbox.width, (int)hitbox.height);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public boolean isActive() {
        return active;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public void reset() {

    }
}
