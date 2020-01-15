import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * This is a class
 * Created 2020-01-15
 *
 * @author Magnus Silverdal
 */
public class GUIRunner extends Canvas implements Runnable{
    private static String title = "Collisions";
    private JFrame frame;
    private BufferedImage image;
    private int[] pixels;
    private int width;
    private int height;
    private int scale = 1;
    private Thread thread;
    private boolean running = false;
    private int fps = 60;
    private int ups = 100;

    private Ball ball;

    public GUIRunner(int w, int h) {
        width = w;
        height = h;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        Dimension size = new Dimension(scale*width, scale*height);
        setPreferredSize(size);
        frame = new JFrame();
        ball = new Ball(width/2,height/2);
    }

    private synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double frameUpdateinteval = 1000000000.0 / fps;
        double stateUpdateinteval = 1000000000.0 / ups;
        double deltaFrame = 0;
        double deltaUpdate = 0;
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            deltaFrame += (now - lastTime) / frameUpdateinteval;
            deltaUpdate += (now - lastTime) / stateUpdateinteval;
            lastTime = now;

            while (deltaUpdate >= 1) {
                update();
                deltaUpdate--;
            }

            while (deltaFrame >= 1) {
                draw();
                deltaFrame--;
            }
        }
        stop();
    }

    private void render() {
        int x = ball.getX();
        int y = ball.getY();

        for (int j = 0 ; j < ball.getDiameter() ; j++) {
            for (int i = 0 ; i < ball.getDiameter() ; i++) {
                if (ball.getPixels()[j*ball.getDiameter()+i] != 0)
                    pixels[(y+j)*width + (x+i)] = ball.getPixels()[j*ball.getDiameter()+i];
            }
        }
    }

    private void clear() {
        for (int i = 0 ; i < pixels.length ; i++) {
            pixels[i] = 0;
        }
    }

    private void draw() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        clear();
        render();

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        bs.show();
    }

    private void update(){
        ball.update(1.0/ups);
    }

    public static void main(String[] args) {
        GUIRunner gui = new GUIRunner(800,600);
        gui.frame.setTitle(title);
        gui.frame.add(gui);
        gui.frame.pack();
        gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.frame.setLocationRelativeTo(null);
        gui.frame.setVisible(true);
        gui.start();
    }
}
