package utils;

import entities.Player;
import main.Game;

import java.awt.geom.Rectangle2D;
import static utils.Constants.PlayerConstants.DEAD;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData, Player player){
        if(!IsSolid(x,y,lvlData, player))
            if(!IsSolid(x+width,y+height, lvlData, player))
                if(!IsSolid(x+width,y,lvlData, player))
                    if(!IsSolid(x,y+height,lvlData, player)){
                        return true;
                    }
        return false;
    }

    public static boolean IsSolid(float x, float y, int[][] lvlData, Player player){
        //Ako Player hitbox izađe iz ekrane (GAME_WIDTH, GAME_WIDTH) promjeni dead variablu.
        if(x + player.getHitBox().width < 0 || x >= Game.GAME_WIDTH){
            player.setKO(true);
            return false;
        }
        if(y + player.getHitBox().height < 0 || y - player.getHitBox().height >= Game.GAME_HEIGHT){
            player.setKO(true);
            return false;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        //korekcija indexa da ostaju unutar lvlData arraya pri izlazu playera iz ekrana
        if(yIndex > lvlData.length){
            yIndex =  lvlData.length-1;
        }
        if(xIndex > lvlData[0].length){
            xIndex =  lvlData[0].length-1;
        }

        int value =lvlData[(int) yIndex][(int) xIndex];
        if(value >= 48 || value <= 0 || value != 11){
            return true;
        }
        else {
            return false;
        }
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox,float xSpeed){
        int currentTile = (int)(hitBox.x / Game.TILES_SIZE);

        if(xSpeed > 0){
            //Desno
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xoffset = (int)(Game.TILES_SIZE - hitBox.width);
            return tileXPos + xoffset - 1;
        }else{
            //Lijevo
            return currentTile * Game.TILES_SIZE;
        }

    }

    public static float GetEntityYPosRoofOrFloor(Rectangle2D.Float hitBox,float airSpeed){
        int currentTile = (int)(hitBox.y / Game.TILES_SIZE);
        if(airSpeed > 0){
            //Down - touching Floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yoffset = (int)(Game.TILES_SIZE - hitBox.height);
            return tileYPos + yoffset - 1;
        }else{
            //Up
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox,int[][] lvlData, Player player){
        //Provjera da li su točke ispod donje lijeve i donje desne točke objekti
        if(!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData, player)){
            if(!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData, player)){
                return false;
            }
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData, Player player){
        return IsSolid(hitbox.x + xSpeed, hitbox.y, lvlData, player);
    }
}
