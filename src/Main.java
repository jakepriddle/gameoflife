import javax.swing.*;
import java.io.IOException;

public class Main {

    //this is the story of how Mars came to be

    public static void main(String[] args) throws InterruptedException, IOException {
        NOOPDraw.createWindow(1330,690,"Mars");
        Grid game = new Grid(35,70);

        while(true) {
            game.display();
            Thread.sleep(100);
            game.update();
        }
    }


}
