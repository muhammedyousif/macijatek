package entities;

import Gamepack.Game;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public class Scout extends Entity {
    private int aniTick, aniIndex, aniSpeed=70 ;
    private int enemyState;
    private Boolean moving=false;
    private Boolean left=false, up, right=false, down,jump=false;
    //gravity jumping
    private float airspeed=0f;
    private float gravity=0.04f;
    private float jumpspeed=-6;
    private float fallSpeedAfterCollison=0.5f;
    private Boolean inAir=false;
    private float fallSpeed;
    private float walkSpeed=0.2f;
    private int walkDir=LEFT;
    //hitbox
    private Boolean firstUpdate=true;
    private int enemyTileY;

    public Scout(float x, float y) {
        super(x, y, SCOUT_WIDTH, SCOUT_HEIGHT);
        initHitbox(x,y,90,SCOUT_HEIGHT-40);

    }
    private void updateAnimationTick(){
        aniTick++;
        if (aniTick>=aniSpeed){
            aniTick=0;
            aniIndex++;
            if (aniIndex>=GetSpriteAmount(enemyState)){
                aniIndex=0;
            }
        }
    }
    public void update(int[][] lvldata,YogiBear bear){
        updateMove(lvldata,bear);
        updateAnimationTick();
    }

    public int getWalkDir() {
        return walkDir;
    }

    public void updateMove(int[][] lvldata,YogiBear bear){
        if (firstUpdate){
            if (!isEntityOnFloor(hitbox,lvldata)){
                inAir=true;
            }
            firstUpdate=false;
        }
        if (inAir){
            if (canMoveHere(hitbox.x, hitbox.y+fallSpeed, hitbox.width, hitbox.height, lvldata)){
                hitbox.y+=fallSpeed;
                fallSpeed+=gravity;
            }else {
                inAir=false;
                hitbox.y=GetEntityYPosUnderRoofOrAboveFloor(hitbox,fallSpeed);
                enemyTileY=(int) (hitbox.y / Game.TILES_SIZE);
            }
        }
        else {
            switch (enemyState){
                case IDLE:
                    enemyState = RUNNINGRIGHT;
                    // Update sprite to running right animation
                    break;

                case RUNNINGRIGHT:
                case RUNNINGLEFT:
                    float xspeed = 0;
                    if (canSeePlayer(lvldata,bear)){
                        bear.respawn();
                        System.out.println("seen");
                    }

                    if (walkDir == LEFT) {
                        xspeed = -walkSpeed;
                        if (enemyState != RUNNINGLEFT) {
                            enemyState = RUNNINGLEFT;
                            // Update sprite to running left animation
                        }
                    } else {
                        xspeed = walkSpeed;
                        if (enemyState != RUNNINGRIGHT) {
                            enemyState = RUNNINGRIGHT;
                            // Update sprite to running right animation

                        }
                    }

                    if (canMoveHere(hitbox.x + xspeed, hitbox.y, hitbox.width, hitbox.height, lvldata)) {
                        if (IsFloor(hitbox, xspeed, lvldata)){
                            hitbox.x += xspeed;
                            return;
                        }
                    }
                    changeWalkDir();
                    break;
            }
        }
    }
    protected boolean canSeePlayer(int[][] lvldata ,YogiBear bear){
        int playerTileY=(int) (bear.getHitbox().y/ Game.TILES_SIZE);
        if (playerTileY==enemyTileY)
            if (isPlayerInRange(bear))
                //if (isSightClear(lvldata,hitbox,bear.hitbox,enemyTileY))
                    return true;

        return false;


    }

    protected boolean isPlayerInRange(YogiBear bear) {
        int absValue =(int) (Math.abs(bear.hitbox.x-hitbox.x));
        if (absValue<=Game.TILES_SIZE*2) {
            System.out.println("man");
            return true;
        }
        return false;
    }




    protected void newState(int enemyState){
        this.enemyState=enemyState;
        aniTick=0;
        aniIndex=0;
    }
    public void resetEnemy(){
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        newState(IDLE);
        fallSpeed = 0;
    }

    private void changeWalkDir() {
        if (walkDir==LEFT)
            walkDir=RIGHT;
        else
            walkDir=LEFT;

    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
