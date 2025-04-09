package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Ghoul;
import entities.Minotaur;
import main.Game;
import objects.Cannon;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;
import utilz.HelpMethods;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetGhouls;
import static utilz.HelpMethods.GetMinotaurs;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Ghoul> ghouls;
    private ArrayList<Minotaur> minotaurs;
    private ArrayList<Potion> potions;
    private ArrayList<Spike> spikes;
    private ArrayList<GameContainer> containers;
    private ArrayList<Cannon> cannons;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img=img;
        createLevelData();
        createEnemies();
        createPotions();
        createContainers();
        createSpikes();
        createCannons();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void createCannons() {
        cannons=HelpMethods.GetCannons(img);
    }

    private void createSpikes() {
        spikes=HelpMethods.GetSpikes(img);
    }

    private void createContainers() {
        containers=HelpMethods.GetContainers(img);
    }

    private void createPotions() {
        potions=HelpMethods.GetPotions(img);
    }

    private void calcPlayerSpawn() {
        playerSpawn=GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide=img.getWidth();
        maxTilesOffset=lvlTilesWide-Game.TILES_IN_WIDTH;
        maxLvlOffsetX=Game.TILES_SIZE*maxTilesOffset;
    }

    private void createEnemies() {
        ghouls=GetGhouls(img);
        minotaurs=GetMinotaurs(img);
    }

    private void createLevelData() {
        lvlData=GetLevelData(img);
    }

    public int getSpriteIndex(int x,int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData(){
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public ArrayList<Ghoul> getGhouls(){
        return ghouls;
    }

    public ArrayList<Minotaur> getMinotaurs(){
        return minotaurs;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Potion> getPotions(){
        return potions;
    }

    public ArrayList<GameContainer> getContainers(){
        return containers;
    }

    public ArrayList<Spike> getSpikes(){
        return spikes;
    }

    public ArrayList<Cannon> getCannons(){
        return cannons;
    }
}
