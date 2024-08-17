package utils;

import entities.Crabby;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.CRABBY;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_ATLAS = "level_one_data.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND_IMG = "background_menu.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String CRABBY_SPRITE = "crabby_sprite.png";
    public static final String STATUS_BAR = "health_power_bar.png";
    public static final String MATCH_END_IMG = "completed_sprite.png";
    public static final String OPTIONS_MENU = "options_background.png";

    public static final String TEMPLE_STAGE_BACKGROUND = "playing_bg_img.png";


    //public static final String LEVEL_TEST = "TVZ_ArenaOne.png";

    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                is.close();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static ArrayList<Crabby> GetCrabs(){
        BufferedImage img = GetSpriteAtlas(LoadSave.LEVEL_ONE_ATLAS);
        ArrayList<Crabby> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight();j++){
            for (int i = 0; i < img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int value = color.getGreen();
                if(value == CRABBY){
                    list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static int[][] GetLevelData(){
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_ATLAS);
        //BufferedImage img = GetSpriteAtlas(LEVEL_TEST);

        for (int j = 0; j < img.getHeight();j++){
            for (int i = 0; i < img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int value = color.getRed();
                if(value >= 48){ value = 0;}
                lvlData[j][i] = value; //index za sprite
            }
        }
        return lvlData;
    }
}
