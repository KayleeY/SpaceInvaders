import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AlienBullet {
    public static Image image1 = new Image("images/bullet1.png", 10, 30, true, true);
    public static Image image2 = new Image("images/bullet2.png", 10, 30, true, true);
    private float alienBullet_x;
    private float alienBullet_y;
    private int selectedImage;
    private float velocity;
    private boolean isDestroyed = false;

    public AlienBullet(int chooseImage, float x, float y) {
        if (chooseImage == 1) {
            ImageView imageView = new ImageView(image1);
            selectedImage = 1;
        } else if (chooseImage == 2) {
            ImageView imageView = new ImageView(image2);
            selectedImage = 2;
        } else {
            System.out.println("Invalid Alien Bullet Image selected.");
            System.exit(0);
        }
        alienBullet_x = x;
        alienBullet_y = y;
    }

    public Image getAlienBulletImage() {
        if (selectedImage == 1) {
            return image1;
        } else {
            return image2;
        }
    }

    public float getAlienBullet_x() {
        return alienBullet_x;
    }

    public float getAlienBullet_y() {
        return alienBullet_y;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float input) {
        velocity = input;
    }

    public void setAlienBullet_x(float input) {
        alienBullet_x = input;
    }

    public void setAlienBullet_y(float input) {
        alienBullet_y = input;
    }

    public boolean getIsDestroyed() {
        return isDestroyed;
    }

    public void setIsDestroyed(boolean input) {
        isDestroyed = input;
    }
}

