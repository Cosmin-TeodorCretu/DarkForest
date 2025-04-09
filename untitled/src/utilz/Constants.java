package utilz;

import main.Game;

public class Constants {

    //gravity
    public static final float GRAVITY=0.04f*Game.SCALE;
    public static final int ANI_SPEED=25;

    public static class Projectiles{
        public static final int CANNON_BALL_DEFAULT_WIDTH=15;
        public static final int CANNON_BALL_DEFAULT_HEIGHT=15;
        public static final int CANNON_BALL_WIDTH=(int)(Game.SCALE*CANNON_BALL_DEFAULT_WIDTH);
        public static final int CANNON_BALL_HEIGHT=(int)(Game.SCALE*CANNON_BALL_DEFAULT_HEIGHT);
        public static final float SPEED=0.7f*Game.SCALE;
    }

    public static class ObjectConstants {

        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;
        public static final int SPIKE = 4;
        public static final int CANNON_LEFT = 5;
        public static final int CANNON_RIGHT = 6;

        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (Game.SCALE * SPIKE_WIDTH_DEFAULT);
        public static final int SPIKE_HEIGHT = (int) (Game.SCALE * SPIKE_HEIGHT_DEFAULT);

        public static final int CANNON_WIDTH_DEFAULT = 40;
        public static final int CANNON_HEIGHT_DEFAULT = 26;
        public static final int CANNON_WIDTH = (int) (Game.SCALE * CANNON_WIDTH_DEFAULT);
        public static final int CANNON_HEIGHT = (int) (Game.SCALE * CANNON_HEIGHT_DEFAULT);

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case RED_POTION, BLUE_POTION:
                    return 7;
                case BARREL, BOX:
                    return 8;
                case CANNON_LEFT,CANNON_RIGHT:
                    return 7;
            }
            return 1;
        }
    }


    public static class EnemyConstants{
        public static final int GHOUL=0;
        public static final int MINOTAUR=1;

        public static final int IDLE=0;
        public static final int RUNNING=1;
        public static final int ATTACK=2;
        public static final int HIT=3;
        public static final int DEATH=4;

        public static final int GHOUL_WIDTH_DEFAULT=32;
        public static final int GHOUL_HEIGHT_DEFAULT=32;

        public static final int GHOUL_WIDTH=(int)(GHOUL_WIDTH_DEFAULT * Game.SCALE);
        public static final int GHOUL_HEIGHT=(int)(GHOUL_HEIGHT_DEFAULT * Game.SCALE);

        public static final int GHOUL_DRAWOFFSET_X=(int)(26*Game.SCALE);
        public static final int GHOUL_DRAWOFFSET_Y=(int)(9*Game.SCALE);

        public static final int MINOTAUR_WIDTH_DEFAULT=80;
        public static final int MINOTAUR_HEIGHT_DEFAULT=80;

        public static final int MINOTAUR_WIDTH=(int)(2*GHOUL_WIDTH_DEFAULT * Game.SCALE);
        public static final int MINOTAUR_HEIGHT=(int)(2*GHOUL_HEIGHT_DEFAULT * Game.SCALE);

        public static final int MINOTAUR_DRAWOFFSET_X=(int)(1*Game.SCALE);   //26
        public static final int MINOTAUR_DRAWOFFSET_Y=(int)(1*Game.SCALE);   //9

        public static int GetSpriteAmount(int enemyType,int enemyState) {
            switch(enemyType) {
                case MINOTAUR:
                    switch(enemyState) {
                        case HIT:
                            return 3;
                        case IDLE:
                            return 5;
                        case DEATH:
                            return 6;
                        case RUNNING:
                            return 8;
                        case ATTACK:
                            return 9;
                    }
                case GHOUL:
                    switch(enemyState) {
                        case IDLE:
                        case HIT:
                            return 4;
                        case RUNNING:
                            return 8;
                        case ATTACK:
                        case DEATH:
                            return 6;
                    }
            }
            return 0;
        }

        public static int GetMaxHealth(int enemy_type) {
            switch(enemy_type) {
                case MINOTAUR:
                    return 100;
                case GHOUL:
                    return 10;
                default:
                    return 1;
            }
        }

        public static int GetEnemyDmg(int enemy_type) {
            switch(enemy_type) {
                case MINOTAUR:
                    return 35;
                case GHOUL:
                    return 15;
                default:
                    return 0;
            }

        }

    }

    public static class Environment{
        public static final int BGRD_TREES1_WIDTH_DEFAULT=816;    //448
        public static final int BGRD_TREES1_HEIGHT_DEFAULT=288;   //101

        public static final int BGRD_TREES1_WIDTH=(int)(BGRD_TREES1_WIDTH_DEFAULT * Game.SCALE);
        public static final int BGRD_TREES1_HEIGHT=(int)(BGRD_TREES1_HEIGHT_DEFAULT * Game.SCALE);

        public static final int BGRD_TREES2_WIDTH_DEFAULT=816;
        public static final int BGRD_TREES2_HEIGHT_DEFAULT=288;

        public static final int BGRD_TREES2_WIDTH=(int)(BGRD_TREES2_WIDTH_DEFAULT * Game.SCALE);
        public static final int BGRD_TREES2_HEIGHT=(int)(BGRD_TREES2_HEIGHT_DEFAULT * Game.SCALE);
    }

    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT =140;
            public static final int B_HEIGHT_DEFAULT =56;
            public static final int B_WIDTH=(int)(B_WIDTH_DEFAULT*Game.SCALE);
            public static final int B_HEIGHT=(int)(B_HEIGHT_DEFAULT*Game.SCALE);
        }

        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT=42;
            public static final int SOUND_SIZE=(int)(SOUND_SIZE_DEFAULT*Game.SCALE);
        }

        public static class UrmButtons{
            public static final int URM_DEFAULT_SIZE=56;
            public static final int URM_SIZE=(int)(URM_DEFAULT_SIZE*Game.SCALE);
        }

        public static class VolumeButtons{
            public static final int VOLUME_DEFAULT_WIDTH=28;
            public static final int VOLUME_DEFAULT_HEIGHT=44;
            public static final int SLIDER_DEFAULT_WIDTH=215;

            public static final int VOLUME_WIDTH=(int)(VOLUME_DEFAULT_WIDTH*Game.SCALE);
            public static final int VOLUME_HEIGHT=(int)(VOLUME_DEFAULT_HEIGHT*Game.SCALE);
            public static final int SLIDER_WIDTH=(int)(SLIDER_DEFAULT_WIDTH*Game.SCALE);
        }
    }

    public static class Directions{
        public static final int LEFT=0;
        public static final int UP=1;
        public static final int RIGHT=2;
        public static final int DOWN=3;
    }

    public static class PlayerConstants{
        public static final int IDLE=0;
        public static final int WALK=1;
        public static final int CROUCH=2;
        public static final int DMGH=3;
        public static final int DMGL=4;
        public static final int DEATH=5;
        public static final int ATTACK1=6;
        public static final int ATTACK2=7;
        public static final int JUMP=8;


        public static int GetSpriteAmount(int player_action){

            switch(player_action)
            {
                case WALK:
                    return 6;
                case ATTACK1:
                case ATTACK2:
                case DMGH:
                    return 4;
                case CROUCH:
                case JUMP:
                case DEATH:
                    return 3;
                case DMGL:
                    return 2;
                case IDLE:
                default:
                    return 1;
            }
        }

    }

}