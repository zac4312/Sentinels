import javax.swing.JFrame;
    import javax.swing.JPanel;
    import java.awt.Graphics;
    import javax.swing.Timer;
    import java.awt.event.KeyAdapter;
    import java.awt.event.KeyEvent;
    import java.util.ArrayList;
    import java.awt.Color;
    import java.awt.Dimension;
    
    public class GameMechanic {
 // In the main game class
    private static void SpawnNewEnemy  (ArrayList<GameObject> enemy_spawn) {
        int numberOfEnemies = 3; // Number of enemies to spawn
        int spacing = 120; // Spacing between enemies
        int startX = 350; // Starting X position

    for (int i = 0; i < numberOfEnemies; i++) {
        GameObject newEnemy = new GameObject(
            startX + i * spacing,  // Position enemies next to each other
            0,                   // Y position (top of the screen)
            105,             // Width
            100             // Height
        );
        enemy_spawn.add(newEnemy); // Add to enemy list
    }
}
        
        public static void main(String[] args)  {
            // Create a GameObject representing a square at position (100, 100) with size 50x50
            GameObject ship = new GameObject(700, 650, 105, 100);
            Bullet bullet = new Bullet(ship.getX(), ship.getY(), 10, 20, 20); // Adjust parameters as necessary

            bullet.setSpeedY(50);

            ArrayList<Bullet> bullet_spawn = new ArrayList<>();
            ArrayList<GameObject> enemy_spawn = new ArrayList<>(); // List to store enemies
            
            // Create a JFrame to display the game
            JFrame frame = new JFrame("galaga");
                int screenWidth = 800;
                int screenHeight = 600;

            
            // Create a custom JPanel to override the paintComponent method
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g); // Call the superclass's paintComponent to ensure proper rendering
                    ship.render(g); // Call the render method of the square to draw it
                    
                    for (GameObject enemy : enemy_spawn) {
                        enemy.render(g); // Render each enemy
                    }
                 
                    for (Bullet bullet : bullet_spawn) {// Render all visible bullets
                    bullet.render(g);
                    }
                }
            };

             // Set preferred size and background color of the panel
            panel.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Fixed screen size
            panel.setBackground(Color.blue); // Set background color

            frame.add(panel); // Add the custom panel to the JFrame
            frame.setSize(400, 400); // Set the size of the window
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
            frame.setVisible(true); // Make the window visible
            panel.setFocusable(true); 
            panel.requestFocusInWindow(); // Request focus after frame is visible

            // Add key listener for controlling the player
            panel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            ship.moveLeft(bullet);
                            break;
                        
                        case KeyEvent.VK_RIGHT:
                            ship.moveRight(bullet);
                            break;
                        
                        case KeyEvent.VK_SPACE:
                            bullet.shoot(bullet);
                            Bullet new_Bullet = new Bullet(
                                ship.getX() + ship.getWidth() / 2 - 5, // Centered on the ship
                                ship.getY() - 20,                         // Above the ship
                                    10, 20, 5         // Size and speed
                            );
                            bullet_spawn.add(new_Bullet);                 // Add the new bullet to the list
                            break;                
                    }
                    panel.repaint();
                }
            });

        

            Timer spawnTimer = new Timer(500, e -> {
                // Check if the ship is alive and all enemies are destroyed
                while (ship.isVisible() && enemy_spawn.isEmpty()) {
                    // Spawn new enemies
                    SpawnNewEnemy(enemy_spawn);

                    if (!ship.isVisible) {
                        break;
                       }
                }
                
                // Update enemies
                for (GameObject enemy : enemy_spawn) {
                    enemy.moveDown(); // Move enemies down

                if (!ship.isVisible) {
                        break;
                    }
                }

                // Repaint the panel
                panel.repaint();

        });

            // Start the game loop
            spawnTimer.start();
            
            ArrayList<GameObject> ToRemove = new ArrayList<>();

            Timer Enemy_Timer = new Timer(500, e -> {
                for (GameObject enemy : enemy_spawn) {
                enemy.moveDown(); // Move the alien down

                if (ship.checkCollision(enemy)) {
                    ToRemove.add(ship);
                    ToRemove.add(enemy);
                }   
                // Iterate through all bullets using forEach
            bullet_spawn.forEach(b -> {
                if (b.isVisible()) {
                        b.move(); // Move the bullet upward

                // Check collision with the alien
                if (b.checkCollision(enemy)) {
                        b.setVisible(false); // Hide the bullet upon collision
                    }
                }
        });
    }
        panel.repaint(); // Refresh the screen to update visuals
        });
            
        
            Enemy_Timer.start();

            Timer bullet_Timer = new Timer(50, e -> {

        for (GameObject enemy : enemy_spawn) {
            // Iterate through all bullets using forEach
        bullet_spawn.forEach(b -> {
            if (b.isVisible()) {
                b.moveUP(); // Move the bullet upward

            // Check collision with the alien
            if (b.checkCollision(enemy)) {
                b.setVisible(false); // Hide the bullet upon collision
                ToRemove.add(enemy); // Mark the enemy for removal
                }
            }
        });
        }
    enemy_spawn.removeAll(ToRemove);

    for (GameObject enemy : ToRemove) {
        enemy_spawn.remove(enemy);

        // Spawn a new enemy at a random position
        int enemyX = (int) (Math.random() * (screenWidth - 50)); // Random X within screen bounds
        int enemyY = (int) (Math.random() * 200);                // Random Y within a specific range
        enemy_spawn.add(new GameObject(enemyX, enemyY, 105, 100));
    }

    panel.repaint(); // Refresh the screen to update visuals
});

            bullet_Timer.start();

        }
    }


