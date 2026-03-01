
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;

// JPanel is something that is added to the frame, that handel drawing
public class FishInvaders extends JPanel implements ActionListener, KeyListener {

    // sound
    Clip backgroundMusic;
    Clip laserSound;
    Clip killSound;
    Clip gameOverSound;

    public Clip loadSound(String path) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    getClass().getResource(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            if (gameOverSound != null) {
                backgroundMusic.stop();
                gameOverSound.setFramePosition(0); // rewind to start
                gameOverSound.start();
            }
            gameLoop.stop();
        }
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");

        if (gameOver) {
            ship.x = shipX;
            alienArray.clear();
            bulletArray.clear();
            score = 0;
            alienVelocityX = 1;
            alienRows = 2;
            alienColumns = 3;
            gameOver = false;
            createAliens();

            if (backgroundMusic != null) {
                backgroundMusic.setFramePosition(0); // rewind to start
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundMusic.start();
            }

            gameLoop.start();
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT && ship.x > 0) {
            ship.x -= shipVelocityX;
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT && ship.x < boardWidth - ship.width) {
            ship.x += shipVelocityX;
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE) {
            // create a bullet
            Block bullet = new Block(
                    // ship.x + ship.width/2 - bulletWidth/2
                    ship.x + ship.width * 15 / 32 - bulletWidth / 2,
                    ship.y,
                    bulletWidth,
                    bulletHeight,
                    null);
            bulletArray.add(bullet);

            // add the sound effect for shooting
            if (laserSound != null) {
                laserSound.setFramePosition(0); // rewind to start
                laserSound.start();
            }
        }
    }

    class Block {

        int x;
        int y;
        int width;
        int height;
        Image img;
        boolean alive = true; // used for aliens
        boolean used = false; // used for bullets

        Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    // board
    // same size as the window
    int tileSize = 32;
    int rows = 16;
    int columns = 16;
    int boardWidth = tileSize * columns;
    int boardHeight = tileSize * rows;

    // ship
    int shipWidth = tileSize * 2;
    int shipHeight = tileSize;
    int shipX = boardWidth / 2 - shipWidth / 2;
    int shipY = boardHeight - shipHeight - tileSize;
    int shipVelocityX = tileSize;
    // int shipX = tileSize * columns/2 - shipWidth/2;
    // int shipY = boardHeight - tileSize * 2;
    Block ship;

    // aliens
    ArrayList<Block> alienArray;
    int alienWidth = tileSize * 2;
    int alienHeight = tileSize;
    int alienX = tileSize;
    int alienY = tileSize;

    // the number of aliens
    int alienRows = 2;
    int alienColumns = 3;
    int alienCount = 0;
    int alienVelocityX = 1;

    // bullets
    ArrayList<Block> bulletArray;
    int bulletWidth = tileSize / 8;
    int bulletHeight = tileSize / 2;
    int bulletVelocityY = -10;

    ArrayList<Image> fishImgArray;
    int score = 0;
    Timer gameLoop;
    boolean gameOver = false;

    FishInvaders() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        // sound
        backgroundMusic = loadSound("./assets/Sound/Juhani Junkala [Retro Game Music Pack] Title Screen.wav");
        laserSound = loadSound("./assets/Sound/laser-one-shot-2.wav");
        killSound = loadSound("./assets/Sound/explosion-sound-4.wav");
        gameOverSound = loadSound("./assets/Sound/game-die.wav");

        // Image shipImg = new
        // ImageIcon(getClass().getResource("./assets/Pink.png")).getImage();
        Image shipImg = new ImageIcon(getClass().getResource("./assets/Pink.png")).getImage();
        Image blueFishImg = new ImageIcon(getClass().getResource("./assets/Blue fish.png")).getImage();
        Image whiteFishImg = new ImageIcon(getClass().getResource("./assets/White fish.png")).getImage();
        Image brownFishImg = new ImageIcon(getClass().getResource("./assets/Brown fish.png")).getImage();
        Image PinkFishImg = new ImageIcon(getClass().getResource("./assets/Pink fish.png")).getImage();

        fishImgArray = new ArrayList<Image>();
        fishImgArray.add(blueFishImg);
        fishImgArray.add(whiteFishImg);
        fishImgArray.add(brownFishImg);
        fishImgArray.add(PinkFishImg);

        ship = new Block(shipX, shipY, shipWidth, shipHeight, shipImg);
        alienArray = new ArrayList<Block>();
        bulletArray = new ArrayList<Block>();

        // the game starts
        gameLoop = new Timer(1000 / 60, this); // means 60 frames per second
        // we need to creat the aliens before the timer
        // we will make a function that creates the aliens and call it here
        createAliens();

        if (backgroundMusic != null) {
            backgroundMusic.setFramePosition(0); // rewind to start
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        }

        gameLoop.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);

        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
        }

        // bullets
        g.setColor(Color.white);
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            if (!bullet.used) {
                g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);

            }
        }

        // score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 32));

        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf(score), 10, 35);
        } else {
            g.drawString("Score: " + score, 10, 35);
        }
    }

    public void createAliens() {
        Random random = new Random();
        for (int i = 0; i < alienRows; i++) {
            for (int j = 0; j < alienColumns; j++) {
                int randomImgIndex = random.nextInt(fishImgArray.size());
                Block alien = new Block(
                        alienX + j * alienWidth,
                        alienY + i * alienHeight,
                        alienWidth,
                        alienHeight,
                        fishImgArray.get(randomImgIndex));
                alienArray.add(alien);
            }
        }
        alienCount = alienRows * alienColumns;
    }

    public void move() {
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive) {
                alien.x += alienVelocityX;

                if (alien.x <= 0 || alien.x >= boardWidth - alien.width) {
                    alienVelocityX *= -1;
                    alien.x += alienVelocityX * 2; // to prevent the alien from getting stuck on the wall

                    // move one row
                    for (int j = 0; j < alienArray.size(); j++) {
                        alienArray.get(j).y += alienHeight;
                    }
                }

                if (alien.y >= ship.y) {
                    gameOver = true;
                    // gameLoop.stop();
                }
            }
            alien.x += alienVelocityX;
        }

        // move bullets
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            bullet.y += bulletVelocityY;

            for (int j = 0; j < alienArray.size(); j++) {
                Block alien = alienArray.get(j);
                if (!bullet.used && alien.alive && detectCollision(bullet, alien)) {
                    alien.alive = false;
                    bullet.used = true;
                    alienCount--;
                    score += 100;
                    if (killSound != null) {
                        killSound.setFramePosition(0); // rewind to start
                        killSound.start();
                    }
                    break;
                }
            }
        }

        // remove bullets that are off the screen or used
        while (bulletArray.size() > 0 && (bulletArray.get(0).y < 0 || bulletArray.get(0).used)) {
            bulletArray.remove(0);
        }

        // if all aliens are dead, create new aliens with more rows and columns
        if (alienCount == 0) {
            score += alienRows * alienColumns * 100;
            alienColumns = Math.min(alienColumns + 1, columns / 2 - 2);
            alienRows = Math.min(alienRows + 1, rows - 6);
            alienArray.clear();
            bulletArray.clear();
            alienVelocityX = 1; // increase the speed of the aliens
            createAliens();
        }
    }

    public boolean detectCollision(Block a, Block b) {
        return a.x < b.x + b.width
                && a.x + a.width > b.x
                && a.y < b.y + b.height
                && a.y + a.height > b.y;
    }
}
