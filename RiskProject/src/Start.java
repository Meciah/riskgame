import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Start extends JPanel {
    BufferedImage risk;


    public Start() {
        try {
            risk = ImageIO.read(new File("images/risk.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void paintComponent(Graphics g) {
        g.drawImage(risk, 0, 0, null);
    }


    public Dimension getPreferredSize() {
        return new Dimension(risk.getWidth(), risk.getHeight());
    }


}
