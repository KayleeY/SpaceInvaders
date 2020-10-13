import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.scene.media.AudioClip;
import java.awt.*;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Vector;


public class SpaceInvaders extends Application{

    // set global variables
    AnimationTimer timer;
    public Stage primaryStage;
    public Scene introduction;
    public Scene game;
    public Scene gameoverScene;
    public Scene gamewin;
    public Scene gamepause;
    public GraphicsContext gc;
    int STAGE_WIDTH = 800;
    int STAGE_HEIGHT = 600;
    public Ship ship;
    int score = 0;
    int lives = 3;
    int level = 1;
    int aliensCount = 50;
    float ALIEN_VELOCITY = 0.5f;
    float SHIP_VELOCITY = 3.0f;
    float ALIEN_BULLET_VELOCITY = 3.0f;
    float SHIP_BULLET_VELOCITY = -5.0f;
    Vector<Alien> aliens = new Vector<Alien>();
    Vector<ShipBullet> shipBullets = new Vector<ShipBullet>();;
    Vector<AlienBullet> alienBullets = new Vector<AlienBullet>();
    Label labelScore;
    Label labelLives;
    Label labelLevel;
    AudioClip soundShoot = new AudioClip(Paths.get("src/sounds/shoot.wav").toUri().toString());
    AudioClip soundInvaderKilled = new AudioClip(Paths.get("src/sounds/invaderkilled.wav").toUri().toString());
    AudioClip soundExplosion = new AudioClip(Paths.get("src/sounds/explosion.wav").toUri().toString());
    AudioClip soundFastInvader1 = new AudioClip(Paths.get("src/sounds/fastinvader1.wav").toUri().toString());
    AudioClip soundFastInvader2 = new AudioClip(Paths.get("src/sounds/fastinvader2.wav").toUri().toString());
    AudioClip soundFastInvader3 = new AudioClip(Paths.get("src/sounds/fastinvader3.wav").toUri().toString());
    AudioClip soundFastInvader4 = new AudioClip(Paths.get("src/sounds/fastinvader4.wav").toUri().toString());


    // set Introduction scene
    public void initIntroScene() {

        GridPane pane = new GridPane();

        Image logo = new Image("images/logo.png", 400, 300, true, true);
        ImageView logoView = new ImageView(logo);
        Label linstructions = new Label("Instructions");
        linstructions.setFont(new Font ("Arial Bold", 30));
        Label lstart = new Label("ENTER - Start Game");
        lstart.setFont(new Font ("Arial", 20));
        Label lmovement = new Label("A or <-, D or -> - Move ship left or right");
        lmovement.setFont(new Font("Arial", 20));
        Label lfire = new Label ("SPACE - Fire!");
        lfire.setFont(new Font("Arial", 20));
        Label lquit = new Label ("Q - Quit Game");
        lquit.setFont(new Font("Arial", 20));
        Label llevel = new Label ("1 or 2 or 3 - Start Game at a specific level");
        llevel.setFont(new Font("Arial", 20));

        pane.add(logoView, 0, 0);
        pane.setMargin(logoView, new Insets(10, 0, 0, 175));
        pane.add(linstructions, 0, 1);
        pane.setMargin(linstructions, new Insets(40, 0, 0, 300));
        pane.add(lstart, 0, 2);
        pane.setMargin(lstart, new Insets(40, 0, 0, 300));
        pane.add(lmovement, 0, 3);
        pane.setMargin(lmovement, new Insets(20, 0, 0, 220));
        pane.add(lfire, 0, 4);
        pane.setMargin(lfire, new Insets(20, 0, 0, 325));
        pane.add(lquit, 0, 5);
        pane.setMargin(lquit, new Insets(20, 0, 0, 325));
        pane.add(llevel, 0, 6);
        pane.setMargin(llevel, new Insets(20, 0, 0, 220));

        introduction = new Scene(pane, STAGE_WIDTH, STAGE_HEIGHT);
    }

    // create ship
    public void initShip() {
        ship = new Ship();
        gc.drawImage(ship.image, ship.getShip_x(), ship.getShip_y());
    }

    // create Aliens
    public void initAlien() {
        int initialx = 50;
        int initialy = 50;
        for (int i = 10; i > 0; i--) {
            for (int j = 5; j > 0; j--) {
                int image;
                if (j % 2 == 0) {
                    image = 1;
                } else {
                    image = 2;
                }
                Alien alien = new Alien(image, initialx + (i * 45), initialy + (j * 30));
                aliens.add(alien);
                gc.drawImage(alien.getAlienImage(), alien.getAlien_x(), alien.getAlien_y());
            }
        }
    }

    // create game scene
    public void initGameScene(){
        Group gameBoard = new Group();
        Canvas gameCanvas = new Canvas(STAGE_WIDTH, STAGE_HEIGHT);
        gc = gameCanvas.getGraphicsContext2D();

        GridPane pane = new GridPane();

        Label labelScoretxt = new Label("Score: ");
        labelScoretxt.setFont(new Font ("Arial", 20));
        labelScoretxt.setTextFill(Color.WHITE);

        labelScore = new Label(Integer.toString(score));
        labelScore.setFont(new Font ("Arial", 20));
        labelScore.setTextFill(Color.WHITE);

        Label labelLivestxt = new Label("Lives: ");
        labelLivestxt.setFont(new Font ("Arial", 20));
        labelLivestxt.setTextFill(Color.WHITE);

        labelLives = new Label(Integer.toString(lives));
        labelLives.setFont(new Font ("Arial", 20));
        labelLives.setTextFill(Color.WHITE);

        Label labelLevelstxt = new Label("Levels: ");
        labelLevelstxt.setFont(new Font ("Arial", 20));
        labelLevelstxt.setTextFill(Color.WHITE);

        labelLevel = new Label(Integer.toString(level));
        labelLevel.setFont(new Font ("Arial", 20));
        labelLevel.setTextFill(Color.WHITE);

        pane.add(labelScoretxt, 0, 0);
        pane.setMargin(labelScoretxt, new Insets(10, 0, 0, 20));

        pane.add(labelScore, 1, 0);
        pane.setMargin(labelScore, new Insets(10, 0, 0, 0));

        pane.add(labelLivestxt, 2, 0);
        pane.setMargin(labelLivestxt, new Insets(10, 0, 0, 450));

        pane.add(labelLives, 3, 0);
        pane.setMargin(labelLives, new Insets(10, 0, 0, 0));

        pane.add(labelLevelstxt, 4, 0);
        pane.setMargin(labelLevelstxt, new Insets(10, 0, 0, 40));

        pane.add(labelLevel, 5, 0);
        pane.setMargin(labelLevel, new Insets(10, 0, 0, 0));

        initShip();
        initAlien();

        gameBoard.getChildren().addAll(gameCanvas, pane);
        game = new Scene(gameBoard, STAGE_WIDTH, STAGE_HEIGHT);
        game.setFill(Color.BLACK);
    }

    void ship_animation_handler() {
        if (ship.getShip_x() < 50) {
            gc.clearRect(ship.getShip_x(), ship.getShip_y(),50, 40);
            ship.setShip_x(50);
            ship.setVelocity(0.0f);
        }
        if (ship.getShip_x() > 700) {
            gc.clearRect(ship.getShip_x(), ship.getShip_y(),50, 40);
            ship.setShip_x(700);
            ship.setVelocity((0.0f));
        }
        gc.clearRect(ship.getShip_x(), ship.getShip_y(),50, 40);
        ship.setShip_x(ship.getShip_x() + ship.getVelocity());
        gc.drawImage(ship.image, ship.getShip_x(), ship.getShip_y());
    }

    void ship_bullet_animation() {
        Iterator<ShipBullet> shipBulletIterator = shipBullets.iterator();
        while(shipBulletIterator.hasNext()) {
            ShipBullet bullet = shipBulletIterator.next();
            if (bullet.getShipBullet_y() < 50) {
                gc.clearRect(bullet.getShipBullet_x(), bullet.getShipBullet_y(), 5, 25);
                shipBulletIterator.remove();
            } else {
                gc.clearRect(bullet.getShipBullet_x(), bullet.getShipBullet_y(), 5, 25);
                bullet.setShipBullet_y(bullet.getShipBullet_y() + SHIP_BULLET_VELOCITY);
                gc.drawImage(bullet.image, bullet.getShipBullet_x(), bullet.getShipBullet_y());
            }
        }
    }

    void alien_bullet_animation() {
        Iterator<AlienBullet> alienBulletIterator = alienBullets.iterator();
        while(alienBulletIterator.hasNext()) {
            AlienBullet bullet = alienBulletIterator.next();
            if (bullet.getAlienBullet_y() > 570) {
                gc.clearRect(bullet.getAlienBullet_x(), bullet.getAlienBullet_y(), 10, 30);
                alienBulletIterator.remove();
            } else {
                gc.clearRect(bullet.getAlienBullet_x(), bullet.getAlienBullet_y(), 10, 30);
                bullet.setAlienBullet_y(bullet.getAlienBullet_y() + ALIEN_BULLET_VELOCITY);
                gc.drawImage(bullet.getAlienBulletImage(), bullet.getAlienBullet_x(), bullet.getAlienBullet_y());
            }
        }
    }

    void alien_animation_handler() {
        boolean changeDirection = false;
        // check if reached boundary
        for (Alien alien : aliens) {
            if ((alien.getAlien_x() + ALIEN_VELOCITY) > 710 || (alien.getAlien_x() + ALIEN_VELOCITY) < 50) {
                changeDirection = true;
                ALIEN_VELOCITY *= -1.0f;
                break;
            }
        }

        if (changeDirection) {
            shoot_alien_bullet();
        } else {
            // shoot based on random percentage
            int randomShoot = (int) (1000 * Math.random());
            if (level == 1 && randomShoot == 500) {
                shoot_alien_bullet();
            } else if (level == 2 && (randomShoot == 500 || randomShoot == 501)) {
                shoot_alien_bullet();
            } else if (level == 3 && (randomShoot == 500 || randomShoot == 501 || randomShoot == 502)) {
                shoot_alien_bullet();
            }
        }

        for (Alien alien : aliens) {
            gc.clearRect(alien.getAlien_x(), alien.getAlien_y(), 40, 30);
            if (changeDirection) {
                if (alien.getAlien_y() + 30 > 540) {
                    lives--;
                    if (lives <= 0){
                        setGameOver();
                        setKeyEventGameOver();
                        timer.stop();
                        primaryStage.setScene(gameoverScene);
                        // System.out.println("GAME OVER");
                        // System.exit(0);
                    } else {
                        clear_previous_level();
                        aliensCount = 50;
                        setLevel(level);
                        initShip();
                        initAlien();
                    }
                    break;
                }
                alien.setAlien_y(alien.getAlien_y() + 30);
            }
            alien.setAlien_x(alien.getAlien_x() + ALIEN_VELOCITY);
            gc.drawImage(alien.getAlienImage(), alien.getAlien_x(), alien.getAlien_y());
        }
    }

    void shoot_alien_bullet() {
        int randomAlien = (int) ((aliensCount - 1) * Math.random());
        Alien currentAlien = aliens.elementAt(randomAlien);
        if (randomAlien % 4 == 0) {
            soundFastInvader1.play();
        } else if (randomAlien % 4 == 1) {
            soundFastInvader2.play();
        } else if (randomAlien % 4 == 2) {
            soundFastInvader3.play();
        } else {
            soundFastInvader4.play();
        }
        AlienBullet bullet = new AlienBullet(currentAlien.getAlienType(), currentAlien.getAlien_x() + 15, currentAlien.getAlien_y() + 35);
        alienBullets.add(bullet);
        gc.drawImage(bullet.getAlienBulletImage(), bullet.getAlienBullet_x(), bullet.getAlienBullet_y());
    }

    // check if alien is hit
    void check_hit_alien() {
        Iterator<ShipBullet> shipBulletIterator = shipBullets.iterator();
        while(shipBulletIterator.hasNext()) {
            ShipBullet bullet = shipBulletIterator.next();
            Iterator<Alien> alienIterator = aliens.iterator();
            while(alienIterator.hasNext()) {
                Alien alien = alienIterator.next();
                // if bullet hits alien
                if (((bullet.getShipBullet_x() >= alien.getAlien_x() && bullet.getShipBullet_x() <= (alien.getAlien_x() + 40))
                        || (bullet.getShipBullet_x() + 5 >= alien.getAlien_x() && bullet.getShipBullet_x() + 5<= (alien.getAlien_x() + 40)))
                        && bullet.getShipBullet_y() <= (alien.getAlien_y() + 30)
                        && bullet.getShipBullet_y() >= alien.getAlien_y()) {
                    score += level * 10;
                    aliensCount--;
                    if (ALIEN_VELOCITY > 0) {
                        ALIEN_VELOCITY += 0.1f;
                    } else {
                        ALIEN_VELOCITY -= 0.1f;
                    }
                    soundInvaderKilled.play();
                    //System.out.println("count alien: " + aliensCount);
                    gc.clearRect(alien.getAlien_x(), alien.getAlien_y(), 40, 30);
                    gc.clearRect(bullet.getShipBullet_x(), bullet.getShipBullet_y(), 5, 25);
                    shipBulletIterator.remove();
                    alienIterator.remove();
                    break;
                }
            }
        }
    }

    void check_hit_ship() {
        Iterator<AlienBullet> alienBulletIterator = alienBullets.iterator();
        while(alienBulletIterator.hasNext()) {
            AlienBullet bullet = alienBulletIterator.next();
            if (((bullet.getAlienBullet_x() >= ship.getShip_x()
                    && bullet.getAlienBullet_x() <= (ship.getShip_x() + 50)) ||
                    (bullet.getAlienBullet_x() + 10 >= ship.getShip_x()
                            && bullet.getAlienBullet_x() + 10 <= (ship.getShip_x() + 50)))
                    && (bullet.getAlienBullet_y() + 20) >= ship.getShip_y()) {
                gc.clearRect(bullet.getAlienBullet_x(), bullet.getAlienBullet_y(), 10, 30);
                lives--;
                soundExplosion.play();
                alienBulletIterator.remove();
                ResetShip();
            }
        }
    }

    void ResetShip() {
        if (lives == 0) {
            setGameOver();
            setKeyEventGameOver();
            timer.stop();
            primaryStage.setScene(gameoverScene);
            return;
            //System.out.println("GAME OVER");
            //System.exit(0);
        }
        gc.clearRect(ship.getShip_x(), ship.getShip_y(),50, 40);
        ship.setShip_x(380);
        ship.setShip_y(570);
        gc.drawImage(ship.image, ship.getShip_x(), ship.getShip_y());
    }


    void update_labels() {
        labelLevel.setText(Integer.toString(level));
        labelScore.setText(Integer.toString(score));
        labelLives.setText(Integer.toString(lives));
    }

    void setLevel(int lv) {
        if (lv == 1) {
            ALIEN_VELOCITY = 0.5f;
            SHIP_VELOCITY = 3.0f;
            ALIEN_BULLET_VELOCITY = 3.0f;
            SHIP_BULLET_VELOCITY = -5.0f;
        } else if (lv == 2) {
            ALIEN_VELOCITY = 1.25f;
            SHIP_VELOCITY = 5.0f;
            ALIEN_BULLET_VELOCITY = 4.0f;
            SHIP_BULLET_VELOCITY = -7.0f;
        } else {
            ALIEN_VELOCITY = 2.0f;
            SHIP_VELOCITY = 7.0f;
            ALIEN_BULLET_VELOCITY = 5.0f;
            SHIP_BULLET_VELOCITY = -9.0f;
        }
    }

    void clear_previous_level() {
        // clear ship
        gc.clearRect(ship.getShip_x(), ship.getShip_y(),50, 40);
        // clear aliens
        Iterator<Alien> alienIterator = aliens.iterator();
        while(alienIterator.hasNext()) {
            Alien alien = alienIterator.next();
            gc.clearRect(alien.getAlien_x(), alien.getAlien_y(), 40, 30);
            alienIterator.remove();
        }
        // clear alien bullets
        Iterator<AlienBullet> alienBulletIterator = alienBullets.iterator();
        while(alienBulletIterator.hasNext()) {
            AlienBullet bullet = alienBulletIterator.next();
            gc.clearRect(bullet.getAlienBullet_x(), bullet.getAlienBullet_y(), 10, 30);
            alienBulletIterator.remove();
        }
        // clear ship bullets
        Iterator<ShipBullet> shipBulletIterator = shipBullets.iterator();
        while(shipBulletIterator.hasNext()) {
            ShipBullet bullet = shipBulletIterator.next();
            gc.clearRect(bullet.getShipBullet_x(), bullet.getShipBullet_y(), 5, 25);
            shipBulletIterator.remove();
        }
    }

    void handle_animation(long now) {
        ship_animation_handler();
        ship_bullet_animation();
        alien_animation_handler();
        alien_bullet_animation();
        check_hit_alien();
        check_hit_ship();
        update_labels();
        if (aliensCount == 0) {
            if (level == 3) {
                setGameWin();
                setKeyEventGameWin();
                timer.stop();
                primaryStage.setScene(gamewin);
                //System.out.println("CONGRATS! YOU WIN!");
                //System.exit(0);
            } else {
                level++;
                setLevel(level);
                aliensCount = 50;
                clear_previous_level();
                initShip();
                initAlien();
            }
        }
    }



    void setGameOver() {

        GridPane pane = new GridPane();

        Label labelGameOver = new Label("GAME OVER");
        labelGameOver.setFont(new Font ("Arial Bold", 50));
        labelGameOver.setTextFill(Color.BLACK);

        Label Scoretxt = new Label("Score:   " + Integer.toString(score));
        Scoretxt.setFont(new Font ("Arial", 20));
        Scoretxt.setTextFill(Color.BLACK);

        Label starttxt = new Label("Enter - Start New Game");
        starttxt.setFont(new Font ("Arial", 20));
        starttxt.setTextFill(Color.BLACK);

        Label quittxt = new Label("Q - Quit Game");
        quittxt.setFont(new Font ("Arial", 20));
        quittxt.setTextFill(Color.BLACK);

        Label chooselevel = new Label("1 OR 2 OR 3 - Start New Game at a Specific Level");
        chooselevel.setFont(new Font ("Arial", 20));
        chooselevel.setTextFill(Color.BLACK);

        pane.add(labelGameOver, 0, 0);
        pane.setMargin(labelGameOver, new Insets(70, 0, 0, 240));
        pane.add(Scoretxt, 0, 1);
        pane.setMargin(Scoretxt, new Insets(70, 0, 0, 340));
        pane.add(starttxt, 0, 2);
        pane.setMargin(starttxt, new Insets(30, 0, 0, 280));
        pane.add(quittxt, 0, 3);
        pane.setMargin(quittxt, new Insets(30, 0, 0, 330));
        pane.add(chooselevel, 0, 4);
        pane.setMargin(chooselevel, new Insets(30, 0, 0, 160));

        gameoverScene = new Scene(pane, STAGE_WIDTH, STAGE_HEIGHT);

    }

    void setGamepause() {
        GridPane pane = new GridPane();

        Label labelGamePause = new Label("PAUSE");
        labelGamePause.setFont(new Font ("Arial Bold", 50));
        labelGamePause.setTextFill(Color.BLACK);

        Label continuetxt = new Label("Enter - Continue");
        continuetxt.setFont(new Font ("Arial", 20));
        continuetxt.setTextFill(Color.BLACK);

        Label quittxt = new Label("Q - Quit Game");
        quittxt.setFont(new Font ("Arial", 20));
        quittxt.setTextFill(Color.BLACK);

        pane.add(labelGamePause, 0, 0);
        pane.setMargin(labelGamePause, new Insets(120, 0, 0, 300));
        pane.add(continuetxt, 0, 1);
        pane.setMargin(continuetxt, new Insets(70, 0, 0, 320));
        pane.add(quittxt, 0, 2);
        pane.setMargin(quittxt, new Insets(30, 0, 0, 330));

        gamepause = new Scene(pane, STAGE_WIDTH, STAGE_HEIGHT);
    }

    void setGameWin() {

        GridPane pane = new GridPane();

        Label labelGamewin = new Label("CONGRATULATIONS! YOU WIN!");
        labelGamewin.setFont(new Font ("Arial Bold", 40));
        labelGamewin.setTextFill(Color.BLACK);

        // already set in game over scene
        Label Scoretxt = new Label("Score:   " + Integer.toString(score));
        Scoretxt.setFont(new Font ("Arial", 20));
        Scoretxt.setTextFill(Color.BLACK);

        Label starttxt = new Label("Enter - Start New Game");
        starttxt.setFont(new Font ("Arial", 20));
        starttxt.setTextFill(Color.BLACK);

        Label quittxt = new Label("Q - Quit Game");
        quittxt.setFont(new Font ("Arial", 20));
        quittxt.setTextFill(Color.BLACK);

        Label chooselevel = new Label("1 OR 2 OR 3 - Start New Game at a Specific Level");
        chooselevel.setFont(new Font ("Arial", 20));
        chooselevel.setTextFill(Color.BLACK);

        pane.add(labelGamewin, 0, 0);
        pane.setMargin(labelGamewin, new Insets(70, 0, 0, 110));
        pane.add(Scoretxt, 0, 1);
        pane.setMargin(Scoretxt, new Insets(70, 0, 0, 340));
        pane.add(starttxt, 0, 2);
        pane.setMargin(starttxt, new Insets(30, 0, 0, 280));
        pane.add(quittxt, 0, 3);
        pane.setMargin(quittxt, new Insets(30, 0, 0, 330));
        pane.add(chooselevel, 0, 4);
        pane.setMargin(chooselevel, new Insets(30, 0, 0, 160));

        gamewin = new Scene(pane, STAGE_WIDTH, STAGE_HEIGHT);

    }

    void setKeyEventGameWin() {
        gamewin.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q) {
                System.exit(0);
            } else if (e.getCode() == KeyCode.ENTER) {
                level = 1;
                setLevel(1);
                score = 0;
                aliensCount = 50;
                lives = 3;
                clear_previous_level();
                initShip();
                initAlien();
                timer.start();
                primaryStage.setScene(game);
            } else if (e.getCode() == KeyCode.DIGIT1) {
                level = 1;
                setLevel(1);
                score = 0;
                aliensCount = 50;
                lives = 3;
                clear_previous_level();
                initShip();
                initAlien();
                timer.start();
                primaryStage.setScene(game);
            } else if (e.getCode() == KeyCode.DIGIT2) {
                level = 2;
                setLevel(2);
                score = 0;
                aliensCount = 50;
                lives = 3;
                clear_previous_level();
                initShip();
                initAlien();
                timer.start();
                primaryStage.setScene(game);
            } else if (e.getCode() == KeyCode.DIGIT3) {
                level = 3;
                setLevel(3);
                score = 0;
                aliensCount = 50;
                lives = 3;
                clear_previous_level();
                initShip();
                initAlien();
                timer.start();
                primaryStage.setScene(game);
            }
        });
    }

    void setKeyEventGameOver() {
        gameoverScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q) {
                System.exit(0);
            } else if (e.getCode() == KeyCode.ENTER) {
                level = 1;
                setLevel(1);
                score = 0;
                aliensCount = 50;
                lives = 3;
                clear_previous_level();
                initShip();
                initAlien();
                timer.start();
                primaryStage.setScene(game);
            } else if (e.getCode() == KeyCode.DIGIT1) {
                level = 1;
                setLevel(1);
                score = 0;
                aliensCount = 50;
                lives = 3;
                clear_previous_level();
                initShip();
                initAlien();
                timer.start();
                primaryStage.setScene(game);
            } else if (e.getCode() == KeyCode.DIGIT2) {
                level = 2;
                setLevel(2);
                score = 0;
                aliensCount = 50;
                lives = 3;
                clear_previous_level();
                initShip();
                initAlien();
                timer.start();
                primaryStage.setScene(game);
            } else if (e.getCode() == KeyCode.DIGIT3) {
                level = 3;
                setLevel(3);
                score = 0;
                aliensCount = 50;
                lives = 3;
                clear_previous_level();
                initShip();
                initAlien();
                timer.start();
                primaryStage.setScene(game);
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        initIntroScene();
        initGameScene();
        setGameOver();
        setGameWin();
        setGamepause();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handle_animation(now);
            }
        };

        stage.setTitle("Space Invaders");
        stage.setResizable(false);
        stage.setScene(introduction);

        introduction.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                timer.start();
                stage.setScene(game);
            } else if (e.getCode() == KeyCode.DIGIT1) {
                level = 1;
                setLevel(1);
                timer.start();
                stage.setScene(game);
            } else if (e.getCode() == KeyCode.DIGIT2) {
                level = 2;
                setLevel(2);
                timer.start();
                stage.setScene(game);
            } else if (e.getCode() == KeyCode.DIGIT3) {
                level = 3;
                setLevel(3);
                timer.start();
                stage.setScene(game);
            } else if (e.getCode() == KeyCode.Q) {
                System.exit(0);
            }
        });

        game.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                stage.setScene(gamepause);
                timer.stop();
            } else if ((e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) && ship.getShip_x() > 50) {
                ship.setVelocity(-1.0f * SHIP_VELOCITY);
                //System.out.println("ship velocity = -1.5");
            } else if ((e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) && ship.getShip_x() < 700) {
                ship.setVelocity(SHIP_VELOCITY);
                //System.out.println("ship velocity = 1.5");
            }
        });

        game.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                ship.setVelocity(0.0f);
                //System.out.println("ship velocity = 0");
            } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                ship.setVelocity(0.0f);
            }  else if (e.getCode() == KeyCode.SPACE) {
                soundShoot.play();
                ShipBullet bullet = new ShipBullet(ship.getShip_x() + 20, ship.getShip_y() - 35);
                //System.out.println("shipx: " + ship.getShip_x() + " shipy: " + ship.getShip_y ());
                shipBullets.add(bullet);
                gc.drawImage(bullet.image, bullet.getShipBullet_x(), bullet.getShipBullet_y());
            }
        });

        gamepause.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q) {
                System.exit(0);
            } else if (e.getCode() == KeyCode.ENTER) {
                timer.start();
                stage.setScene(game);
            }
        });
        // show the starting scene.
        stage.show();
    }

}
