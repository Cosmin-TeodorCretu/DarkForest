package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.IsFloor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Ghoul extends Enemy{

    private int attackBoxOffsetX;

    public Ghoul(float x, float y) {
        super(x, y, GHOUL_WIDTH, GHOUL_HEIGHT, GHOUL);
        initHitbox(22, 19);  //32  43-42
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox=new Rectangle2D.Float(x,y,(int)(40*Game.SCALE),(int)(20*Game.SCALE));
        attackBoxOffsetX=(int)(Game.SCALE*5);
    }


    public void update(int [][] lvlData,Player player) {
        updateMove(lvlData,player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x=hitbox.x - attackBoxOffsetX;
        attackBox.y=hitbox.y;
    }

    private void updateMove(int [][] lvlData,Player player) {
        if(firstUpdate)
            firstUpdateCheck(lvlData);
        if(inAir)
            updateInAir(lvlData);
        else {
            switch(state) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:

                    if(canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if(isPlayerCloseForAttack(player))
                            newState(ATTACK);
                    }

                    move(lvlData);
                    break;
                case ATTACK:
                    if(aniIndex==0)
                        attackChecked=false;

                    if(aniIndex==3 && !attackChecked)
                        checkEnemyHit(attackBox,player);
                    break;
                case HIT:
                    break;
            }
        }

    }

    public int flipX() {
        if(walkDir==RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if(walkDir==RIGHT)
            return -1;
        else
            return 1;
    }
}
