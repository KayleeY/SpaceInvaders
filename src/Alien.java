import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Alien {
    public static Image image1 = new Image("images/enemy1.png", 40, 30, true, true);
    public static Image image2 = new Image("images/enemy2.png", 40, 30, true, true);
    private int selectedImage;
    private float alien_x;
    private float alien_y;
    private boolean isDestroyed = false;
    //private float velocity = 1.0f;

    public Alien(int chooseImage, float x, float y) {
        if (chooseImage == 1) {
            ImageView imageView = new ImageView(image1);
            selectedImage = 1;
        } else if (chooseImage == 2) {
            ImageView imageView = new ImageView(image2);
            selectedImage = 2;
        } else {
            System.out.println("Invalid Alien Image selected.");
            System.exit(0);
        }
        alien_x = x;
        alien_y = y;
    }

    public boolean getIsDestroyed() {
        return isDestroyed;
    }

    public void setIsDestroyed(boolean input) {
        isDestroyed = input;
    }

    public int getAlienType() {
        return selectedImage;
    }

    public Image getAlienImage() {
        if (selectedImage == 1) {
            return image1;
        } else {
            return image2;
        }
    }

    public float getAlien_x() {
        return alien_x;
    }

    public float getAlien_y() {
        return alien_y;
    }

    public void setAlien_x(float input) {
        alien_x = input;
    }

    public void setAlien_y(float input) {
        alien_y = input;
    }

    //public float getVelocity() { return velocity;}

    //public void setVelocity(float input) { velocity = input; }
}
