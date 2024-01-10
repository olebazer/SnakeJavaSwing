package snake;

import javax.swing.*;

public class Window extends JFrame {
    Window() {
        this.setTitle("My JFrame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(new Game());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
