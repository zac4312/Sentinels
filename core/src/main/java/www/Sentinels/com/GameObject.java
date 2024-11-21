import java.awt.Color;
import java.awt.Graphics;

// This is the GameObject class that represents a square
public class GameObject {
    protected int x, y, width, height;
    private boolean isMoving = true; // Track if the object is moving
    protected boolean isVisible = true;
    private int speedY;

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
        // Constructor to initialize the square's position and size
        public GameObject(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    
       
        public boolean isVisible() {
            return isVisible;
        }

        public void setVisible(boolean visible) {
            this.isVisible = visible; // Remove static context
        }

            // Method to render (draw) the square on the screen
            public void render(Graphics g) {
                if (isVisible) {
                // Set the color for the square
                    g.setColor(Color.green);
                // Draw the square as a filled rectangle
                    g.drawRect(x, y, width, height);
                }
            }
            
            public void moveLeft(Bullet bullet) {
                this.x -= 100; // Move square left by 10 pixels
                if (!bullet.isVisible()) {
                    bullet.setX(this.x + this.width / 2 - bullet.getWidth() / 2); // Keep bullet aligned with the ship
                }
            }
        
            public void moveRight(Bullet bullet) {
                this.x += 100; // Move square right by 10 pixels
                if (!bullet.isVisible()) {
                    bullet.setX(this.x + this.width / 2 - bullet.getWidth() / 2); // Keep bullet aligned with the ship
                }
            }

            public void moveUP() {
                this.y -= speedY; // Move square up by 10 pixels
                if (this.y + this.height < 0) { // Check if bullet moves off-screen
                    this.resetPosition(); // Hide bullet
                }
            }
            public void moveDown() {
                if (isMoving) {
                this.y += 20;
                }
            }   
            public void resetPosition() {
                this.isVisible = false; // Hide bullet again
            }
        public int getX() {
            return x;
            }
        
        public int getY() {
            return y;
            }
        
        public int getWidth() {
            return width;
            }
        
        public int getHeight() {
            return height;
            }
        
// GameObject class
public void shoot(Bullet bullet) {
    if (!bullet.isVisible()) { // Only shoot if the bullet is not visible
        bullet.setX(this.x + this.width / 2 - bullet.getWidth() / 2); // Center above the ship
        bullet.setY(this.y - 20); // Set initial position above the ship
        bullet.setVisible(true); // Make it visible
    }
}

 // Method to stop the movement of th e square
 public void stopMoving() {
    isMoving = false;
    }

  // Method to check if the square collides with another square
 public boolean checkCollision(GameObject other) {
    // Check for overlap between the two rectangles (squares)
    return this.x < other.x + other.width &&
           this.x + this.width > other.x &&
           this.y < other.y + other.height &&
           this.y + this.height > other.y;
    }
}


