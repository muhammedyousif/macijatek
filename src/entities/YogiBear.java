package entities;

import Gamepack.Game;
import gamestate.Playing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class YogiBear extends Entity {

    private int HEALTH;
    private int aniTick, aniIndex, aniSpeed=99 ;

    private BufferedImage[][] animations;
    private int playeraction = IDLE;
    private Boolean moving=false;
    private Boolean left=false, up, right=false, down,jump=false;
    private float playerSpeed=2.0f;
    //gravity jumping
    private float airspeed=0f;
    private float gravity=0.04f;
    private float jumpspeed=-6;
    private float fallSpeedAfterCollison=0.5f;
    private Boolean inAir=false;
    private int[][] lvldata;
    //hitbox
    private float xdrawOffset= 56;
    private float ydrawOffset=30;
    private BufferedImage heartImage;

    private Playing playing;
    public YogiBear(float x,float y,int width,int height,Playing playing){
        super(x,y,70,130);
        this.playing=playing;
        loadAnimation();
        initHitbox(x,y,70,130);
        HEALTH=3;
        loadHeartImage();
    }
    public void update(){
        if (HEALTH==0){
            playing.setGameOver(true);
        }
        udpatePOS();
        if (moving){
            checkBasketContact();
        }
        updateAnimation();
        setAnimation();

    }

    private void checkBasketContact() {
        playing.checkBasketContact(hitbox);
    }

    public void render(Graphics g, int xLvlOffset){
        g.drawImage(animations[playeraction][aniIndex],(int)(hitbox.x-xdrawOffset)-xLvlOffset,(int)(hitbox.y-ydrawOffset),null);
        //drawHitbox(g,xLvlOffset);
        drawLives(g, 20, 20);
    }
    private void setAnimation() {
        if(moving){
            playeraction = RUNNING;
        }
        else playeraction =  IDLE;
    }
    private void udpatePOS() {
        moving=false;
        if (jump)
            jump();
        if (!left&&!right&&!inAir)
            return;
        float xspeed=0;
        if (!inAir){
            if (!isEntityOnFloor(hitbox,lvldata)){
                inAir=true;
            }
        }
        if(left){
            xspeed-=playerSpeed;
        }
        if(right ) {
            xspeed+=playerSpeed;
        }
        if (inAir){
            if(canMoveHere(hitbox.x, hitbox.y+airspeed, hitbox.width, hitbox.height,lvldata)){
                hitbox.y+=airspeed;
                airspeed+=gravity;
                if (hitbox.y>=Game.GAME_HEIGHT){
                    respawn();
                }
                updateXPos(xspeed);
            }
            else {
                hitbox.y=GetEntityYPosUnderRoofOrAboveFloor(hitbox,airspeed);
                if (airspeed>0){
                    resetInAir();
                }
                else {
                    airspeed=fallSpeedAfterCollison;
                }
                updateXPos(xspeed);
            }
        }else {
            updateXPos(xspeed);
        }
        moving=true;
    }

    private void jump() {
        if (inAir){
            return;
        }
        inAir=true;
        airspeed=jumpspeed;
    }

    private void resetInAir() {
        inAir=false;
        airspeed=0;
    }

    private void updateXPos(float xspeed) {
        if (canMoveHere(hitbox.x+ xspeed,hitbox.y, hitbox.width, hitbox.height, lvldata)){
            hitbox.x+=xspeed;
        }else {
            hitbox.x= GetEntityXPosNextToWall(hitbox,xspeed);
        }
    }

    private void updateAnimation() {
        aniTick++;
        if (aniTick>=aniSpeed){
            aniTick=0;
            aniIndex++;
            if (aniIndex>=getSprite(playeraction)){
                aniIndex=0;
            }
        }
    }
    private void loadAnimation() {
        InputStream is = getClass().getResourceAsStream("/playersprite.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[2][2];
            for(int i =0;i<animations.length;i++){
                for(int j =0;j<animations[i].length;j++) {
                    animations[i][j] = img.getSubimage(j * 160, i*200, 160, 200);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    public void loadLvlData(int[][] lvldata){
        this.lvldata=lvldata;
        if (!isEntityOnFloor(hitbox,lvldata)){
            inAir=true;
        }
    }
    public void setLeft(Boolean left) {
        this.left = left;
    }

    public void setUp(Boolean up) {
        this.up = up;
    }

    public void setRight(Boolean right) {
        this.right = right;
    }

    public void setDown(Boolean down) {
        this.down = down;
    }

    public void resetDirBool() {
         up=false;
         left=false;
         right=false;
         down=false;
    }

    public void setJump(boolean jump) {
        this.jump=jump;
    }

    public void respawn() {
        x=200f;
        y=253f;
        hitbox.x=x;
        hitbox.y=y;
        resetInAir();
        resetDirBool();
        HEALTH--;
        System.out.println(HEALTH);
    }

    public void loadHeartImage() {
        try {
            heartImage = ImageIO.read(getClass().getResourceAsStream("/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void drawLives(Graphics g, int x, int y) {
        for (int i = 0; i < HEALTH; i++) {
            g.drawImage(heartImage, x + (heartImage.getWidth() * i), y, null);
        }
    }


    public void resetAll() {
        resetDirBool();
        inAir = false;
        moving = false;
        playeraction = IDLE;
        HEALTH = 3;

        hitbox.x = x;
        hitbox.y = y;

        if (!isEntityOnFloor(hitbox, lvldata))
            inAir = true;
    }


}
