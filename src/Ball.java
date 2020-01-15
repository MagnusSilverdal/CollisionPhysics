/**
 * This is a class
 * Created 2020-01-15
 *
 * @author Magnus Silverdal
 */
public class Ball {
    private int diameter;
    private int color;
    private double x;
    private double y;
    private double vx;
    private double vy;
    private int[] pixels;

    public Ball(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 10;
        this.vy = 10;
        this.diameter = 20;
        this.color = 0xFFFFFFFF;
        createGraphics();
    }

    private void createGraphics() {
        pixels = new int[diameter*diameter];
        int center = diameter/2;
        for (int y = 0 ; y < diameter ; y++) {
            for (int x = 0 ; x < diameter ; x++) {
                if (Math.sqrt((x-center)*(x-center)+(y-center)*(y-center)) < center) {
                    pixels[y*diameter+x] = color;
                } else {
                    pixels[y*diameter+x] = 0;
                }
            }
        }
    }

    public void update(double deltaT) {
        x += vx*deltaT;
        y += vy*deltaT;
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getX() {
        return (int)Math.round(x);
    }

    public int getY() {
        return (int)Math.round(y);
    }

    public int getDiameter() {
        return diameter;
    }
}
