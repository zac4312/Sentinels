package www.Sentinels.com;

import com.badlogic.gdx.graphics.g2d.SpriteBatch; // Import SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ship {

    // Ship characteristics
    private float movementSpeed;
    private int shield;

    // Position and dimensions
    private float xPosition, yPosition;
    private float width, height;

    // Graphics
    private TextureRegion shipTexture;
    private TextureRegion shieldTexture;

    public Ship(float movementSpeed, int shield,
                float width, float height,
                float xCentre, float yCentre,
                TextureRegion shipTexture,
                TextureRegion shieldTexture) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
    }

    // Draw method to render the ship and shield
    public void draw(SpriteBatch batch) {
        batch.draw(shipTexture, xPosition, yPosition, width, height);
        if (shield > 0) {
            batch.draw(shieldTexture, xPosition, yPosition, width, height);
        }
    }
}
