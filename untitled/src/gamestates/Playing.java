package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.Constants.Environment.*;

public class Playing extends State implements Statemethods{

    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused=false;

    //Border
    private int xLvlOffset;
    private int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;

    //Background
    private BufferedImage backgroundImg,bgrd_tree1,bgrd_tree2;

    //GameOver
    private boolean gameOver;
    private boolean lvlCompleted;
    private boolean playerDying;

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG);
        bgrd_tree1=LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_TREES1);
        bgrd_tree2=LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_TREES2);

        calcLvlOffset();
        loadStartLevel();
    }

    public void loadNextLevel() {
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        resetAll();
    }

    private void loadStartLevel() {
        /*if(DbConnection.getLastLevel()==0) {
            enemyManager.loadEnemies(levelManager.getCurrentLevel());
            objectManager.loadObjects(levelManager.getCurrentLevel());
        } else{
            enemyManager.loadEnemies(levelManager.getSpecificLevel(DbConnection.getLastLevel()));
            objectManager.loadObjects(levelManager.getSpecificLevel(DbConnection.getLastLevel()));
        }*/
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX=levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager=new LevelManager(game);
        enemyManager=new EnemyManager(this);
        objectManager=new ObjectManager(this);

        player=new Player(200,300,(int) (64*Game.SCALE),(int)(64*Game.SCALE),this); //(200,200,64,40)

        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay=new PauseOverlay(this);
        gameOverOverlay=new GameOverOverlay(this);
        levelCompletedOverlay=new LevelCompletedOverlay(this);
    }

    @Override
    public void update() {
        if(paused) {
            pauseOverlay.update();
        }else if(lvlCompleted) {
            levelCompletedOverlay.update();
        }else if(gameOver){
            gameOverOverlay.update();
        }else if(playerDying){
            player.update();
        }else {
            levelManager.update();
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(),player);
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX=(int)player.getHitbox().x;
        int diff = playerX- xLvlOffset;

        if(diff>rightBorder)
            xLvlOffset += diff - rightBorder;
        else if(diff<leftBorder)
            xLvlOffset += diff - leftBorder;

        if(xLvlOffset>maxLvlOffsetX)
            xLvlOffset=maxLvlOffsetX;
        else if(xLvlOffset<0)
            xLvlOffset=0;

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH , Game.GAME_HEIGHT , null);

        drawTrees(g);

        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);

        if(paused) {
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }else if(gameOver)
            gameOverOverlay.draw(g);
        else if(lvlCompleted)
            levelCompletedOverlay.draw(g);
    }

    private void drawTrees(Graphics g) {
        for(int i=0;i<3;i++)
            g.drawImage(bgrd_tree2,0+i*BGRD_TREES2_WIDTH - (int)(xLvlOffset*0.3),(int)(106 * Game.SCALE),BGRD_TREES2_WIDTH,BGRD_TREES2_HEIGHT ,null);

        for(int i=0;i<3;i++)
            g.drawImage(bgrd_tree1, 0 + i*BGRD_TREES1_WIDTH - (int)(xLvlOffset*0.7), (int)(94 * Game.SCALE),BGRD_TREES1_WIDTH,BGRD_TREES1_HEIGHT ,null);
    }

    public void resetAll() {
        gameOver=false;
        paused=false;
        lvlCompleted=false;
        playerDying=false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver=gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjTouched(hitbox);
    }


    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjHit(attackBox);
    }

    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver) {
            if(e.getButton()==MouseEvent.BUTTON1)
                player.setAttack(true);
            else if(e.getButton()==MouseEvent.BUTTON3)
                player.powerAttack();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver) {
            if(paused)
                pauseOverlay.mousePressed(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mousePressed(e);
        }else
            gameOverOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver) {
            if(paused)
                pauseOverlay.mouseReleased(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);
        }else
            gameOverOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver) {
            if(paused)
                pauseOverlay.mouseMoved(e);
            else if(lvlCompleted)
                levelCompletedOverlay.mouseMoved(e);
        }else
            gameOverOverlay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch(e.getKeyCode()){
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused=!paused;
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
            switch(e.getKeyCode()){
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
    }

    public void unpauseGame() {
        paused=false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer(){
        return player;
    }

    public void mouseDragged(MouseEvent e) {
        if(!gameOver)
            if(paused)
                pauseOverlay.mouseDragged(e);
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void setMaxLvlOffsetX(int lvlOffset) {
        this.maxLvlOffsetX=lvlOffset;
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted=levelCompleted;
        if(levelCompleted)
            game.getAudioPlayer().lvlCompleted();
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying=playerDying;
    }
}
