package levels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex=0;

    public LevelManager(Game game)
    {
        this.game=game;
        importOutsideSprites();
        levels=new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel() {
        lvlIndex++;
        if(lvlIndex>=levels.size()) {
            lvlIndex=0;
            System.out.println("Game finished");
            //for(int i=0;i<levels.size();i++) {
            //    DbConnection.deleteRow(i);
            //}
            Gamestate.state=Gamestate.MENU;
        }

        Level newLevel=levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        //DbConnection.insert(lvlIndex,game.getPlaying().getPlayer().getX(),game.getPlaying().getPlayer().getY());
        //DbConnection.deleteRow(lvlIndex-1);
        game.getPlaying().setMaxLvlOffsetX(newLevel.getLvlOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels=LoadSave.GetAllLevels();
        for(BufferedImage img:allLevels)
            levels.add(new Level(img));
    }

    private void importOutsideSprites() {
        BufferedImage img=LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite=new BufferedImage[48];
        for(int j=0;j<4;j++)
            for(int i=0;i<12;i++)
            {
                int index =j*12+i;
                levelSprite[index]=img.getSubimage(i*32, j*32, 32, 32);
            }
    }

    public void draw(Graphics g,int lvlOffset)
    {
        for(int j=0;j<Game.TILES_IN_HEIGHT;j++)
            for(int i=0;i< levels.get(lvlIndex).getLevelData()[0].length;i++)
            {
                int index=levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE*i - lvlOffset, Game.TILES_SIZE*j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        if(lvlIndex==0)
            drawInstructions(g);
    }
    public void update()
    {

    }

    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public Level getSpecificLevel(int index){
        if(index<=levels.size())
            return levels.get(index);
        else
            return null;
    }

    public int getAmountLevels() {
        return levels.size();
    }

    public int getLvlIndex() {
        return lvlIndex;
    }

    public void drawInstructions(Graphics g) {
        g.setColor(Color.YELLOW);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2F);
        g.setFont(newFont);
        g.drawString("For moving left and right use A and D!", Game.GAME_WIDTH/2 - 500, 150);
        g.drawString("For jumping use SPACE !", Game.GAME_WIDTH/2 - 500, 190);
        g.drawString("For attack use LEFT CLICK !", Game.GAME_WIDTH/2 - 500, 240);
        g.drawString("For special attack use RIGHT CLICK !", Game.GAME_WIDTH/2 - 500, 290);
    }

    public void setLevelIndex(int level) {
        lvlIndex=level;
    }
}
