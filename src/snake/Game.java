package snake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener {
    private final int WIDTH = 550;
    private final int HEIGHT = 550;
    private final int UNIT_SIZE = 25;
    private final int VELOCITY = UNIT_SIZE;
    private final int GAME_SPEED = 100;
    private Rectangle food;
    private Random random;
    private Direction direction;
    private ArrayList<Rectangle> body;
    private Timer timer;
    private Image backgroundImage;
    private Image foodImage;
    private Image headImage;
    private Image bodyImage;
    private int score = 0;

    private enum Direction { LEFT, UP, DOWN, RIGHT }

    Game() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setLayout(null);
        backgroundImage = new ImageIcon(
            this.getClass().getClassLoader()
            .getResource("./backgroundImage.jpg")).getImage();
        foodImage = new ImageIcon(
            this.getClass().getClassLoader()
            .getResource("./food.png")).getImage();
        headImage = new ImageIcon(
            this.getClass().getClassLoader()
            .getResource("./head.png")).getImage();
        bodyImage = new ImageIcon(
            this.getClass().getClassLoader().
            getResource("./body.png")).getImage();
        body = new ArrayList<Rectangle>();
        body.add(new Rectangle(5 * UNIT_SIZE, 3 * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE));
        food = new Rectangle();
        random = new Random();
        this.spawnfood();
        timer = new Timer(GAME_SPEED, this);
        timer.start();
        direction = Direction.RIGHT;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;

        g2D.drawImage(backgroundImage, 0, 0, null);

        //g2D.setPaint(new Color(203, 207, 0));

        for (int i = 1; i < body.size(); i++) {
            // g2D.fill(body.get(i));
            g2D.drawImage(bodyImage, body.get(i).x, body.get(i).y, null);
        }

        g2D.setPaint(new Color(251, 255, 0));
        // g2D.fill(body.get(0));
        g2D.drawImage(headImage, body.get(0).x, body.get(0).y, null);

        // g2D.setPaint(new Color(245, 84, 51));
        // g2D.fill(food);
        g2D.drawImage(foodImage, food.x, food.y, null);
        Toolkit.getDefaultToolkit().sync();
    }

    public void spawnfood() {
        food.width = UNIT_SIZE;
        food.height = UNIT_SIZE;
        food.x = random.nextInt(0, WIDTH / UNIT_SIZE - 1) * UNIT_SIZE;
        food.y = random.nextInt(0, HEIGHT / UNIT_SIZE - 1) * UNIT_SIZE;

        body.forEach((segment) -> {
            if (segment.x == food.x && segment.y == food.y) {
                food.x = random.nextInt(0, WIDTH / UNIT_SIZE - 1) * UNIT_SIZE;
                food.y = random.nextInt(0, HEIGHT / UNIT_SIZE - 1) * UNIT_SIZE;
            }
        });
    }

    public void eat() {
        if (body.get(0).intersects(food)) {
            spawnfood();
            body.add(new Rectangle(body.get(0).x, body.get(0).y, body.get(0).width,
                body.get(0).height));
            score++;
            System.out.println(score);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                if (direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J:
                if (direction != Direction.UP) {
                    direction = Direction.DOWN;
                }
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K:
                if (direction != Direction.DOWN) {
                    direction = Direction.UP;
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                if (direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                }
                break;
        }
    }

    public void move() {
        for (int i = body.size() - 1; i > 0; i--) {
            body.get(i).x = body.get(i - 1).x;
            body.get(i).y = body.get(i - 1).y;
        }

        switch (direction) {
            case LEFT:
                body.get(0).x -= VELOCITY;
                break;
            case DOWN:
                body.get(0).y += VELOCITY;
                break;
            case UP:
                body.get(0).y -= VELOCITY;
                break;
            case RIGHT:
                body.get(0).x += VELOCITY;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean gameOver() {
        if (body.get(0).x > WIDTH - UNIT_SIZE || body.get(0).y > HEIGHT - UNIT_SIZE ||
        body.get(0).x < 0 || body.get(0).y < 0) {
            return true;
        }

        for (int i = 1; i < body.size(); i++) {
            if (body.get(0).x == body.get(i).x && body.get(0).y == body.get(i).y) {
                return true;
            }
        }

        return false;
    }

    public void game() {
        if (!gameOver()) {
            eat();
            move();
            repaint();
        } else {
            System.out.println("Game Over!");
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game();
    }
}
