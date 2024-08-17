package gamestates;

import entities.Enemy;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManager levelManager;
    //private EnemyManager enemyManager;
    private boolean paused = false;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private BufferedImage backgroundImg;

    private boolean gameOver = false;

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.TEMPLE_STAGE_BACKGROUND);
    }


    private void initClasses() {
        levelManager = new LevelManager(game);
        //enemyManager = new EnemyManager(this);
        player = new Player(200,200, (int) (64*Game.SCALE), (int)(40*Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
    }

    @Override
    public void update() {
        if(!paused && !gameOver){
            levelManager.update();
            player.update();
            //enemyManager.update(levelManager.getCurrentLevel().getLvlData(), player);
        }else{
            pauseOverlay.update();
        }

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT,null);

        levelManager.draw(g);
        player.render(g);
        //enemyManager.draw(g);

        if(paused){
            pauseOverlay.draw(g);
        }else if(gameOver){
            gameOverOverlay.draw(g);
        }

    }

    public void resetAll(){
        gameOver = false;
        paused = false;
        player.resetAll();
        //enemyManager.resetAll();
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        //enemyManager.checkEnemyHit(attackBox);
    }

    //region Inputs
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver){
            if(e.getButton() == MouseEvent.BUTTON1){
                player.setAttacking(true);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver){
            if(paused){
                pauseOverlay.mousePressed(e);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver){
            if(paused){
                pauseOverlay.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(paused){
            pauseOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver){
            gameOverOverlay.keyPressed(e);
        }else{
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_W:
                    player.setUp(true);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
                case KeyEvent.VK_SHIFT:
                    player.dashMove();
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_W:
                    player.setUp(false);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
                case KeyEvent.VK_SHIFT:
                    player.dashMove();
                    break;
            }
        }

    }

    public void mouseDragged(MouseEvent e){
        if(!gameOver){
            if(paused){
                pauseOverlay.mouseDragged(e);
            }
        }
    }
    //endregion

    public Player getPlayer(){
        return player;
    }

    public void WindowFocusLost(){
        player.resetDirBooleans();
    }

    public void unpauseGame(){
        paused = false;
    }
}
