import java.awt.Color;
import java.awt.Graphics;

class Bullet extends GameObject {
    private int speedY;


    public Bullet(int x, int y, int width, int height, int speedY) {
        super(x, y, width, height);
        this.speedY = speedY;
        this.setVisible(true); 
    }
    
    public void move() {
        if (isVisible) {
            this.y -= speedY; // Move up
            // Check if it goes off-screen (adjust value based on your game window size)
            if (this.y < 0) {
                resetPosition(); // Reset bullet to ship position
            }
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void resetPosition() {
        if (this.isVisible()) {
            this.setX(-100); // Move it off-screen or to an inactive position
            this.setY(-100); // Same as above
            this.setVisible(false);
        }
    }

    @Override
    public void render(Graphics g) {
        if (isVisible) {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height); // Render bullet if visible
        }
    }
}

