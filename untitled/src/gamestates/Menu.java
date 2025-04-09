package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import ui.ContinueButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods{

    private MenuButton[] buttons=new MenuButton[3];
    //private ContinueButton[] cButton=new ContinueButton[1];
    private BufferedImage backgroundImg, backgroundImgFull;
    private int menuX,menuY,menuWidth,menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        //loadContinueButton();
        loadBackground();
        backgroundImgFull=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
    }

    private void loadBackground() {
        backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth=(int)(backgroundImg.getWidth()*Game.SCALE);
        menuHeight=(int)(backgroundImg.getHeight()*Game.SCALE);
        menuX=Game.GAME_WIDTH/2 - menuWidth/2;
        menuY=(int)(45*Game.SCALE);
    }

    private void loadButtons() {
        buttons[0]=new MenuButton(Game.GAME_WIDTH/2,(int)(150*Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1]=new MenuButton(Game.GAME_WIDTH/2,(int)(220*Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2]=new MenuButton(Game.GAME_WIDTH/2,(int)(290*Game.SCALE), 2, Gamestate.QUIT);
    }

    /*private void loadContinueButton(){
        cButton[0]=new ContinueButton(Game.GAME_WIDTH/2+200,(int)(150*Game.SCALE),0, Gamestate.PLAYING);
    }*/

    @Override
    public void update() {
        for(MenuButton mb : buttons)
            mb.update();
        /*for(ContinueButton c : cButton)
            c.update();*/
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgFull,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for(MenuButton mb : buttons)
            mb.draw(g);
       /* for(ContinueButton c : cButton)
            c.draw(g);*/
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb : buttons) {
            if(isIn(e,mb)) {
                mb.setMousePressed(true);
                break;
            }
        }

       /* for(ContinueButton c : cButton) {
            if(isInC(e,c)) {
                c.setMousePressed(true);
                break;
            }
        }*/
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb : buttons) {
            if(isIn(e,mb)) {
                if(mb.isMousePressed())
                    mb.applyGamestate();
                if(mb.getState()==Gamestate.PLAYING)
                    game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLvlIndex());
                break;
            }
        }

        /*for(ContinueButton c : cButton) {
            if(isInC(e,c)) {
                if(c.isMousePressed()) {
                    c.applyGamestate();
                }
                if(c.getState()==Gamestate.PLAYING)
                    game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLvlIndex());
                break;
            }
        }*/

        resetButtons();

    }

    private void resetButtons() {
        for(MenuButton mb : buttons)
            mb.resetBools();
        /*for(ContinueButton c : cButton)
            c.resetBools();*/
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb : buttons)
            mb.setMouseOver(false);

        for(MenuButton mb : buttons)
            if(isIn(e,mb)) {
                mb.setMouseOver(true);
                break;
            }

       /* for(ContinueButton c : cButton)
            c.setMouseOver(false);

        for(ContinueButton c : cButton)
            if(isInC(e,c)) {
                c.setMouseOver(true);
                break;
            }*/
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER) {
            Gamestate.state = Gamestate.PLAYING;
            game.saveNow.loadAll();
            game.getPlaying().getPlayer().changeHealth(game.getPlaying().getPlayer().getPlayerHealth());
            System.out.println("level:" + game.getPlaying().getLevelManager().getLvlIndex());
            game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLvlIndex());
            //game.getPlaying().getLevelManager().loadNextLevel();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
