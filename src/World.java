/**
 * This is a class
 * Created 2020-01-21
 *
 * @author Magnus Silverdal
 */
public class World {
    private int width;
    private int height;
    private Ball[] balls;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        balls = new Ball[2];
        balls[0] = new Ball(width/2,height/2);
        balls[1] = new Ball(width/4,height/4);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void render(int[] pixels) {
        for (int b = 0 ; b < balls.length ; b++) {
            int x = balls[b].getX();
            int y = balls[b].getY();

            for (int j = 0; j < balls[b].getDiameter(); j++) {
                for (int i = 0; i < balls[b].getDiameter(); i++) {
                    if (balls[b].getPixels()[j * balls[b].getDiameter() + i] != 0)
                        pixels[(y + j) * getWidth() + (x + i)] = balls[b].getPixels()[j * balls[b].getDiameter() + i];
                }
            }
        }
    }

    public void update(double t) {
        for (int i = 0 ; i < balls.length ; i++) {
            balls[i].update(t);
        }
    }
}
