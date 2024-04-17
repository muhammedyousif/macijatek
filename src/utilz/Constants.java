package utilz;

public class Constants {
    public static class EnemyConstants{
        public static final int IDLE=0;
        public static final int RUNNINGRIGHT =1;
        public static final int RUNNINGLEFT =3;


        public static final int SCOUT_WIDTH=172;
        public static final int SCOUT_HEIGHT=170;
        public static final int SCOUT_DRAWOFFSETX= 5;
        public static final int SCOUT_DRAWOFFSETY= 30;

        public  static int GetSpriteAmount(int enemy_state){
            switch (enemy_state){
                case IDLE:
                    return 1;
                case RUNNINGRIGHT:
                    return 1;
                case RUNNINGLEFT:
                    return 1;
                default:
                    return 1;
            }
        }


    }
    public static class Directions{
        public static final int LEFT=0;
        public static final int RIGHT=1;
    }
    public static class PlayerConstants{
        public static final int RUNNING=0;
        public static final int IDLE=1;
        public static final int JUMP=0;
        public static final int FALLING=0;
        public static int getSprite(int player_action){
            switch (player_action){
                case RUNNING:
                    return 2;
                case IDLE:
                    return 1;
                default:
                    return 1;
            }
        }

    }
}
