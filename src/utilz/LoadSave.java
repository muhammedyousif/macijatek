package utilz;

import Gamepack.Game;
import Objects.Basket;
import entities.Scout;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class LoadSave {
    public static final String LEVEL_ATLAS="tileset.png";
    public static final String SCOUT_SPRITE="scoutsprite.png";
    public static final String BASKET_SPRITE="basket.png";


    public static BufferedImage get_sprite(String filename) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/"+filename);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return img;
    }
    public static BufferedImage[] getAllLevels(){
        URL url = LoadSave.class.getResource("/lvls");
        File file=null;
        try {
            file=new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];
        for (int i =0;i< filesSorted.length;i++){
            for (int j=0;j<files.length;j++){
                if(files[j].getName().equals((i+1)+".png"))
                    filesSorted[i]=files[j];

            }
        }
        for (File f : files){
            System.out.println("file: "+f.getName());
        }
        BufferedImage[] imgs = new BufferedImage[filesSorted.length];
        for (int i=0;i<filesSorted.length;i++){
            try {
                imgs[i]=ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imgs;


    }
    public static ArrayList<Basket> getBaskets(BufferedImage map){
        ArrayList<Basket> list = new ArrayList<>();

        for (int j =0;j<map.getHeight();j++){
            for(int i =0;i<map.getWidth();i++){
                Color color = new Color(map.getRGB(i,j));
                int value = color.getBlue();
                if (value==1){
                    list.add(new Basket(i*Game.TILES_SIZE,j* Game.TILES_SIZE));
                }
            }
        }

        return list;

    }

}
