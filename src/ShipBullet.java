import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShipBullet {
    public static Image image = new Image("images/player_bullet.png", 5, 25, true, true);
    private float shipBullet_x;
    private float shipBullet_y;
    private float velocity;
    private boolean isDestroyed = false;

    public ShipBullet(float x, float y) {
        ImageView imageView = new ImageView(image);
        shipBullet_x = x;
        shipBullet_y = y;
    }

    public float getShipBullet_x() {
        return shipBullet_x;
    }

    public float getShipBullet_y() {
        return shipBullet_y;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float input) {
        velocity = input;
    }

    public void setShipBullet_x(float input) {
        shipBullet_x = input;
    }

    public void setShipBullet_y(float input) {
        shipBullet_y = input;
    }

    public boolean getIsDestroyed() {
        return isDestroyed;
    }

    public void setIsDestroyed(boolean input) {
        isDestroyed = input;
    }
}