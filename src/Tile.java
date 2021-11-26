import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {


    public String name;
    public String colour;
    public String imageName;
    public BufferedImage image;
    public int age;
    public int r;
    public int g;
    public int b;
    public double rand = Math.random();

    public Tile() {
        if (rand > 0.8) {
            setToDirt();
        }
        else {
            setToGrass();
        }
    }

    public void setToGrass() {
        name = "grass";
        colour = "green";
     //   imageName = "grass.png";
        image = Grid.grass;
        age = 0;
        r = 0;
        g = 155 + (int) (Math.random() * 100);
        b = 0;
    }

    public void setToDirt() {
        name = "dirt";
        colour = "brown";
        imageName = "dirt.png";
        image = Grid.dirt;
        age = 0;
        r = 102;
        g = 51;
        b = 0;
    }

    public void setToWater() {
        name = "water";
        colour = "blue";
        imageName = "water.png";
        image = Grid.water;
        age = 0;
        r = 35;
        g = 137;
        b = 218;
    }

    public void setToHouse() {
        name = "house";
        colour = "wood";
        imageName = "wood.png";
        image = Grid.wood;
        age = 0;
        r = 202;
        g = 164;
        b = 104;
    }

    public void setToFire() {
        name = "fire";
        colour = "red";
        imageName = "lava.png";
        image = Grid.lava;
        age = 0;
        r = 226;
        g = 88 + (int) (Math.random() * 100);
        b = 34;
    }
    public void setToChar() {
        name = "char";
        colour = "black";
        imageName = "charcoal.png";
        image = Grid.charcoal;
        age = 0;
        r = 104;
        g = 103;
        b = 99;
    }

    public void setToStone() {
        name = "stone";
        colour = "grey";
        imageName = "stone.png";
        image = Grid.stone;
        age = 0;
        r = 136;
        g = 140;
        b = 141;
    }

    public void setToMars() {
        name = "mars";
        colour = "red";
        imageName = "mars.png";
        image = Grid.mars;
        age = 0;
        r = 136;
        g = 140;
        b = 141;
    }

    public void setToSnow() {
        name = "snow";
        colour = "white";
        imageName = "snow.png";
        image = Grid.snow;
        age = 0;
        //deprecated
        r = 136;
        g = 140;
        b = 141;
    }

    public void setToIce() {
        name = "ice";
        colour = "ice";
        imageName = "ice.png";
        image = Grid.ice;
        age = 0;
        //deprecated
        r = 136;
        g = 140;
        b = 141;
    }

    public void setToSand() {
        name = "sand";
        colour = "yellow";
        imageName = "sand.png";
        image = Grid.sand;

    }
}