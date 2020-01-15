import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * This is a class
 * Created 2020-01-15
 *
 * @author Magnus Silverdal
 */
public class GUIRunner extends Canvas implements Runnable{
    public static String title = "Collisions";
    private JFrame frame;
    private BufferedImage image;
    private int[] pixels;
    private int scale = 1;

    public GUIRunner(int w, int h) {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        Dimension size = new Dimension(scale*w, scale*h);
        setPreferredSize(size);
        frame = new JFrame();
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        GUIRunner gui = new GUIRunner(800,600);
        gui.frame.setTitle(title);
        gui.frame.add(gui);
        gui.frame.pack();
        gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.frame.setLocationRelativeTo(null);
        gui.frame.setVisible(true);
        gui.run();
    }
}
