import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Grid {

    public Tile[][] grid;
    public int rows;
    public int cols;
    public int cellWidth;
    public int cellHeight;
    public int year = 0;
    public static BufferedImage charcoal;
    public static BufferedImage dirt;
    public static BufferedImage grass;
    public static BufferedImage ice;
    public static BufferedImage lava;
    public static BufferedImage mars;
    public static BufferedImage sand;
    public static BufferedImage snow;
    public static BufferedImage stone;
    public static BufferedImage water;
    public static BufferedImage wood;

    public Grid(int r, int c) throws IOException {
        cellWidth = 19;
        cellHeight = 19;
        rows = r;
        cols = c;
        grid = new Tile[r][c];

        charcoal = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/charcoal.png"));
        dirt = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/dirt.png"));
        grass = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/grass.png"));
        ice = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/ice.png"));
        lava = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/lava.png"));
        mars = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/mars.png"));
        sand = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/sand.png"));
        snow = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/snow.png"));
        stone = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/stone.png"));
        water = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/water.png"));
        wood = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/wood.png"));

        for (int ro = 0; ro < rows; ro++) {
            for (int co = 0; co < cols; co++) {
                grid[ro][co] = new Tile();
            }
        }
    }

    public void display() {
       // NOOPDraw.drawString("Year is   " + year, 1360, 30);

        int y = 0;
        for (int ro = 0; ro < rows; ro++) {
            int x = 0;
            for (int co = 0; co < cols; co++) {
                //we need to draw the tile at grid[ro][co]
                int red = grid[ro][co].r;
                int green = grid[ro][co].g;
                int blue = grid[ro][co].b;
            //    NOOPDraw.setColor(red, green, blue);
                NOOPDraw.drawImage(grid[ro][co].image, x, y, cellWidth, cellHeight);
                x += cellWidth;
            }
            y += cellHeight;
        }
    }


    public void update() {
        year++;
        for (Tile[] tiles : grid) {
            for (Tile tile : tiles) {
                tile.age++;
            }
        }
        System.out.println(year);
        //r1 develop grass
        if (year > 3 && year < 8) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (surround(grid, i, j, (int) (Math.random() * 10), "dirt") > 1) {
                        grid[i][j].setToDirt();
                    }
                }
            }
        }
        //r2 make ponds
        if (year > 8 && year < 35) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (j > 2 && i > 2) {
                        if (surround(grid, i, j, (int) (Math.random() * 10), "dirt") > 1) {
                            grid[i][j].setToWater();
                        }
                    }
                }
            }
        }
        //r3 lake gen
        if (year > 35 && year < 90) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (surround(grid, i, j, (int) (Math.random() * 10), "water") > 1) {
                        grid[i][j].setToWater();
                        if ((i < grid.length - 1 && j < grid[i].length - 3) && (i > 1 && j > 1)) {
                            grid[i--][j].setToWater();
                            grid[i++][j].setToWater();
                            grid[i--][j++].setToWater();
                            grid[i++][j--].setToWater();
                            grid[i][j++].setToWater();
                            grid[i][j--].setToWater();
                            grid[i][(int) (j + (Math.random() * 3))].setToWater();
                        }
                    }
                }
            }
        }
        //r4 make houses
        if (year > 90 && year < 100) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j].name.equals("water")) {
                        if (surround(grid, i, j, (int) (Math.random() * 2), "water") > 0) {
                            if (i < 32) {
                                if (Math.random() > 0.9) {
                                    if (!grid[i + 2][j].name.equals("water")) {
                                        grid[i + 2][j].setToHouse();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //r4 more house
        if (year > 100 && year < 130) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j].name.equals("house")) {
                        if (surround(grid, i, j, (int) (Math.random() * 2), "house") > 0) {
                            if (i < 32) {
                                if (Math.random() > 0.9) {
                                    grid[i + 1][j].setToHouse();
                                }
                            }
                        } else {
                            grid[i][j].setToDirt();
                        }
                    }
                }
            }
        }
        //r 5,6,7,8,9  fire + charcoal + dirt + grass + stone
        if (year > 130 && year < 300) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (year % 100 == 0) {
                        if (Math.random() > 0.75) {
                            if (surround(grid, i, j, 1, "water") < 1) {
                                if (!grid[i][j].name.equals("stone")) {
                                    grid[i][j].setToFire();
                                }
                            }
                        }
                    }
                    if (Math.random() > 0.9999) {
                        if (surround(grid, i, j, 1, "water") < 1) {
                            if (!grid[i][j].name.equals("stone")) {
                                grid[i][j].setToFire();
                            }
                        }
                    }
                    if (grid[i][j].name.equals("fire")) {
                        if (grid[i][j].age > 5) {
                            grid[i][j].setToChar();
                        }
                    }
                    if (grid[i][j].name.equals("dirt")) {
                        if (Math.random() > 0.99) {
                            grid[i][j].setToGrass();
                        }
                    }
                    if (grid[i][j].name.equals("char")) {
                        if (grid[i][j].age > 10) {
                            if (Math.random() > 0.75) {
                                grid[i][j].setToDirt();
                            }
                        }
                    }

                    if (grid[i][j].name.equals("house") || grid[i][j].name.equals("stone")) {
                        if (Math.random() > 0.95) {
                            if (surround(grid, i, j, 1, "water") < 1) {
                                double choice = Math.random() * 7;
                                if (i < 34 && j < 69 && i >= 1 && j >= 1) {
                                    if (choice < 1) {
                                        grid[i][j].setToStone();
                                    } else if (choice > 1 && choice < 2) {
                                        grid[i][j + 1].setToStone();
                                    } else if (choice > 2 && choice < 3) {
                                        grid[i][j - 1].setToStone();
                                    } else if (choice > 3 && choice < 4) {
                                        grid[i + 1][j].setToStone();
                                    } else if (choice > 4 && choice < 5) {
                                        grid[i - 1][j].setToStone();
                                    } else if (choice > 5 && choice < 6) {
                                        grid[i + 1][j + 1].setToStone();
                                    } else if (choice > 6 && choice < 7) {
                                        grid[i - 1][j - 1].setToStone();
                                    }
                                }
                            }
                        }
                    }

                    if (grid[i][j].name.equals("house")) {
                        if (grid[i][j].age > 130) {
                            if (surround(grid, i, j, 1, "char") > 0) {
                                if (Math.random() > 0.5) {
                                    grid[i][j].setToStone();
                                }
                            }
                        }
                    }
                }
            }
        }
        //r10 snow
        if (year > 300 && year < 450) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (year % 50 == 0) {
                        if (Math.random() > 0.75) {
                            if (surround(grid, i, j, 1, "ice") < 1 && surround(grid, i, j, 1, "water") < 1) {
                                if (!grid[i][j].name.equals("stone")) {
                                    grid[i][j].setToSnow();
                                }
                            }
                        }
                    }
                    if (Math.random() > 0.99) {
                        if (surround(grid, i, j, 0, "ice") < 1 && surround(grid, i, j, 0, "water") < 1) {
                            if (!grid[i][j].name.equals("stone") && !grid[i][j].name.equals("ice")) {
                                grid[i][j].setToSnow();
                            }
                        }
                    }
                    //dirt
                    if (grid[i][j].name.equals("grass")) {
                        if (Math.random() > 0.99) {
                            grid[i][j].setToDirt();
                        }
                    }
                    //r11
                    //ice
                    if (grid[i][j].name.equals("ice")) {
                        if (Math.random() > 0.9) {
                            double choice = Math.random() * 7;
                            if (surround(grid, i, j, 2, "stone") < 1) {
                                if (i < 34 && j < 69 && i >= 1 && j >= 1) {
                                    if (choice < 1) {
                                        grid[i][j].setToIce();
                                    } else if (choice > 1 && choice < 2) {
                                        grid[i][j + 1].setToIce();
                                    } else if (choice > 2 && choice < 3) {
                                        grid[i][j - 1].setToIce();
                                    } else if (choice > 3 && choice < 4) {
                                        grid[i + 1][j].setToIce();
                                    } else if (choice > 4 && choice < 5) {
                                        grid[i - 1][j].setToIce();
                                    } else if (choice > 5 && choice < 6) {
                                        grid[i + 1][j + 1].setToIce();
                                    } else if (choice > 6 && choice < 7) {
                                        grid[i - 1][j - 1].setToIce();
                                    }
                                }
                            }
                        }
                    }
                    //stone
                    if (grid[i][j].name.equals("house")) {
                        if (grid[i][j].age > 130) {
                            if (surround(grid, i, j, 1, "char") > 0) {
                                if (Math.random() > 0.5) {
                                    grid[i][j].setToStone();
                                }
                            }
                        }
                    }
                    //ice
                    if (grid[i][j].name.equals("water")) {
                        if (Math.random() > 0.99) {
                            grid[i][j].setToIce();
                        }
                    }
                }
            }
        }
        //grass
        if (year > 450 && year < 500) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j].name.equals("dirt")) {
                        if (Math.random() > 0.99) {
                            grid[i][j].setToGrass();
                        }
                    }
                    //water
                    if (grid[i][j].name.equals("ice")) {
                        if (Math.random() > 0.85) {
                            grid[i][j].setToWater();
                        }
                    }
                    //dirt
                    if (grid[i][j].name.equals("snow")) {
                        if (Math.random() > 0.85) {
                            grid[i][j].setToDirt();
                        }
                    }
                    if (grid[i][j].name.equals("fire")) {
                        grid[i][j].setToDirt();
                    }
                    if (grid[i][j].name.equals("char")) {
                        grid[i][j].setToDirt();
                    }
                    //stone
                    if (grid[i][j].name.equals("house") || grid[i][j].name.equals("stone")) {
                        if (Math.random() > 0.95) {
                            if (surround(grid, i, j, 1, "water") < 1) {
                                double choice = Math.random() * 7;
                                if (i < 34 && j < 69 && i >= 1 && j >= 1) {
                                    if (choice < 1) {
                                        grid[i][j].setToStone();
                                    } else if (choice > 1 && choice < 2) {
                                        grid[i][j + 1].setToStone();
                                    } else if (choice > 2 && choice < 3) {
                                        grid[i][j - 1].setToStone();
                                    } else if (choice > 3 && choice < 4) {
                                        grid[i + 1][j].setToStone();
                                    } else if (choice > 4 && choice < 5) {
                                        grid[i - 1][j].setToStone();
                                    } else if (choice > 5 && choice < 6) {
                                        grid[i + 1][j + 1].setToStone();
                                    } else if (choice > 6 && choice < 7) {
                                        grid[i - 1][j - 1].setToStone();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //r12 sand
        if (year > 500 && year < 750) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[0][0].setToSand();
                    grid[1][1].setToSand();
                    grid[1][2].setToSand();
                    grid[2][3].setToSand();
                    double choice = Math.random() * 7;
                    if (grid[i][j].name.equals("sand")) {
                        if (Math.random() > 0.5) {
                            if (i < 34 && j < 69 && i >= 1 && j >= 1) {
                                if (choice < 1) {
                                    grid[i][j].setToSand();
                                } else if (choice > 1 && choice < 2) {
                                    grid[i][j + 1].setToSand();
                                } else if (choice > 2 && choice < 3) {
                                    grid[i][j - 1].setToSand();
                                } else if (choice > 3 && choice < 4) {
                                    grid[i + 1][j].setToSand();
                                } else if (choice > 4 && choice < 5) {
                                    grid[i - 1][j].setToSand();
                                } else if (choice > 5 && choice < 6) {
                                    grid[i + 1][j + 1].setToSand();
                                } else if (choice > 6 && choice < 7) {
                                    grid[i - 1][j - 1].setToSand();
                                }
                            }
                        }
                    }
                }
            }
        }
        if (year == 750) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j].setToSand();
                }
            }
        }
        //r13 lava sea
        if (year == 775) {
            grid[17][35].setToFire();
        }
        if (year > 775 && year < 900) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    double choice = Math.random() * 7;
                    if (grid[i][j].name.equals("fire")) {
                        if (Math.random() > 0.1) {
                            if (i < 34 && j < 69 && i >= 1 && j >= 1) {
                                if (choice < 1) {
                                    grid[i][j].setToFire();
                                } else if (choice > 1 && choice < 2) {
                                    grid[i][j + 1].setToFire();
                                } else if (choice > 2 && choice < 3) {
                                    grid[i][j - 1].setToFire();
                                } else if (choice > 3 && choice < 4) {
                                    grid[i + 1][j].setToFire();
                                } else if (choice > 4 && choice < 5) {
                                    grid[i - 1][j].setToFire();
                                } else if (choice > 5 && choice < 6) {
                                    grid[i + 1][j + 1].setToFire();
                                } else if (choice > 6 && choice < 7) {
                                    grid[i - 1][j - 1].setToFire();
                                }
                            }
                        }
                    }
                    if (year == 899) {
                        grid[i][j].setToFire();
                    }
                }
            }
        }
        //lava recedes and becomes mars
        if (year == 900) {
            grid[17][35].setToMars();
        }
        if (year > 900) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    double choice = Math.random() * 7;
                    if (grid[i][j].name.equals("mars")) {
                        if (Math.random() > 0.1) {
                            if (i < 34 && j < 69 && i >= 1 && j >= 1) {
                                if (choice < 1) {
                                    grid[i][j].setToMars();
                                } else if (choice > 1 && choice < 2) {
                                    grid[i][j + 1].setToMars();
                                } else if (choice > 2 && choice < 3) {
                                    grid[i][j - 1].setToMars();
                                } else if (choice > 3 && choice < 4) {
                                    grid[i + 1][j].setToMars();
                                } else if (choice > 4 && choice < 5) {
                                    grid[i - 1][j].setToMars();
                                } else if (choice > 5 && choice < 6) {
                                    grid[i + 1][j + 1].setToMars();
                                } else if (choice > 6 && choice < 7) {
                                    grid[i - 1][j - 1].setToMars();
                                }
                            }
                        }
                    }

                    if (year == 1000) {
                        grid[i][j].setToMars();
                    }
                    if (year == 1050) {
                      //  year = 0;
                    }
                }
            }
        }
    }


    public int surround(Tile[][] array, int row, int col, int distance, String name) {
        int amount = 0;
        if (row < 35 - distance && row > distance - 1 && col > distance - 1 && col < 70 - distance) {
            if (array[row + distance][col].name.equals(name)) {
                amount += 1;
            } else if (array[row][col + distance].name.equalsIgnoreCase(name)) {
                amount += 1;
            } else if (array[row + distance][col + distance].name.equalsIgnoreCase(name)) {
                amount += 1;
            } else if (array[row - distance][col].name.equalsIgnoreCase(name)) {
                amount += 1;
            } else if (array[row][col - distance].name.equalsIgnoreCase(name)) {
                amount += 1;
            } else if (array[row - distance][col - distance].name.equalsIgnoreCase(name)) {
                amount += 1;
            } else if (array[row + distance][col - distance].name.equalsIgnoreCase(name)) {
                amount += 1;
            } else if (array[row - distance][col + distance].name.equalsIgnoreCase(name)) {
                amount += 1;
            }
        }
        if ((row == 0 || row == 35 - distance) && col < 70 - distance) {
            if (col > distance) {
                if (array[row][col - distance].name.equalsIgnoreCase(name)) {
                    amount += 1;
                }
            }
            if (array[row][col + distance].name.equalsIgnoreCase(name)) {
                amount += 1;
            }
        }
        if ((col == 0 || col == 70 - distance) && row < 35 - distance) {
            if (row != 0) {
                if (array[row - 1][col].name.equalsIgnoreCase(name)) {
                    amount += 1;
                }
            }
            if (row < 34) {
                if (array[row + 1][col].name.equalsIgnoreCase(name)) {
                    amount += 1;
                }
            }
        }

        return amount;
    }

}
