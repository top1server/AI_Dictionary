import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel() {
        String filePath = "res/BackGround.png"; // Path to your image file
        File file = new File(filePath);
        if (file.exists()) {
            try {
                // Load the background image
                backgroundImage = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("File not found: " + filePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        BackgroundPanel backgroundPanel = new BackgroundPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set frame size
        frame.add(backgroundPanel); // Add the panel to the frame
        frame.setVisible(true); // Make the frame visible
    }
}
