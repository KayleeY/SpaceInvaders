import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship {
    public static Image image = new Image("images/player.png", 50, 40, true, true);
    private float ship_x;
    private float ship_y;
    private float velocity;

    public Ship() {
        ImageView imageView = new ImageView(image);
        ship_x = 380;//305; // canvas width/2 - player width/2
        ship_y = 570;//520; // canvas height - player height.
    }

    public float getShip_x() {
        return ship_x;
    }

    public float getShip_y() {
        return ship_y;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float input) {
        velocity = input;
    }

    public void setShip_x(float input) {
        ship_x = input;
    }

    public void setShip_y(float input) {
        ship_y = input;
    }

}