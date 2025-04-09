package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;
import static entities.Entity.*;

public class EnemyManager {

    private BufferedImage[][] ghoulArr,minotaurArr;
    private Playing playing;
    private ArrayList<Ghoul> ghouls = new ArrayList<>();
    private ArrayList<Minotaur> minotaurs = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing=playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        ghouls=level.getGhouls();
        minotaurs=level.getMinotaurs();
    }

    public void update(int [][] lvlData,Player player) {
        boolean isAnyActive=false;
        for(Ghoul c : ghouls)
            if(c.isActive()) {
                c.update(lvlData,player);
                isAnyActive=true;
            }
        for(Minotaur m:minotaurs)
            if(m.isActive()) {
                m.update(lvlData, player);
                isAnyActive=true;
            }
        if(!isAnyActive)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g,int xLvlOffset) {
        drawGhouls(g, xLvlOffset);
        drawMinotaurs(g,xLvlOffset);

    }

    private void drawMinotaurs(Graphics g, int xLvlOffset) {
        for(Minotaur m:minotaurs)
            g.drawImage(  minotaurArr[m.getEnemyState()][m.getAniIndex()]  ,  (int)m.getHitbox().x - xLvlOffset + m.flipX(),  (int)m.getHitbox().y   ,
                    MINOTAUR_WIDTH * m.flipW()   ,   MINOTAUR_HEIGHT   ,   null);
    }

    private void drawGhouls(Graphics g,int xLvlOffset) {
        for(Ghoul c : ghouls)
            g.drawImage(  ghoulArr[c.getEnemyState()][c.getAniIndex()]  ,  (int)c.getHitbox().x - xLvlOffset + c.flipX(),  (int)c.getHitbox().y   ,
                    GHOUL_WIDTH * c.flipW()   ,   GHOUL_HEIGHT   ,   null);
    }



    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for(Ghoul c:ghouls)
            if(c.getCurrentHealth()>=0)
                if(c.isActive())
                    if(attackBox.intersects(c.getHitbox())) {
                        c.hurt(10);
                        return;
                    }
        for(Minotaur m:minotaurs)
            if(m.getCurrentHealth()>=0)
                if(m.isActive())
                    if(attackBox.intersects(m.getHitbox())) {
                        m.hurt(20);
                        return;
                    }
    }

    private void loadEnemyImgs() {
        ghoulArr=new BufferedImage[5][8];
        BufferedImage temp=LoadSave.GetSpriteAtlas(LoadSave.GHOUL_ATLAS);
        for(int j=0;j<ghoulArr.length;j++)
            for(int i=0;i<ghoulArr[j].length;i++)
                ghoulArr[j][i]=temp.getSubimage(i* GHOUL_WIDTH_DEFAULT, j*GHOUL_HEIGHT_DEFAULT, GHOUL_WIDTH_DEFAULT, GHOUL_HEIGHT_DEFAULT);

        minotaurArr=new BufferedImage[5][9];
        BufferedImage temp2=LoadSave.GetSpriteAtlas(LoadSave.MINOTAUR_ATLAS);
        for(int j=0;j<minotaurArr.length;j++)
            for(int i=0;i<minotaurArr[j].length;i++)
                minotaurArr[j][i]=temp2.getSubimage(i* MINOTAUR_WIDTH_DEFAULT, j*MINOTAUR_HEIGHT_DEFAULT, MINOTAUR_WIDTH_DEFAULT, MINOTAUR_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies() {
        for(Ghoul c:ghouls)
            c.resetEnemy();
        for(Minotaur m:minotaurs)
            m.resetEnemy();
    }
}
