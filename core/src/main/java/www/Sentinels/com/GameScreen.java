package www.Sentinels.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    private Main game;
    private TextureAtlas CharAtlas;
    private Animation<TextureRegion> CharAnimation;
    private Camera camera;
    private Viewport viewport;

    private SpriteBatch batch;
    private TextureAtlas textureAtlas;

    private TextureRegion[] backgrounds;

    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion, enemyShipTextureRegion,
        enemyShieldTextureRegion, playerLaserTextureRegion, enemyLaserTextureregion;

    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;

    // World parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    //game Objects
    private Ship playerShip;
    private Ship enemyShip;

    public GameScreen(Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up the texture atlas
        TextureAtlas mainAtlas = new TextureAtlas("images.atlas");
        TextureAtlas enemyAtlas = new TextureAtlas("Enemy.atlas");

        // Initialize the backgrounds array
        backgrounds = new TextureRegion[4];

        backgrounds[0] = mainAtlas.findRegion("backg1");
        backgrounds[1] = mainAtlas.findRegion("backg2");
        backgrounds[2] = mainAtlas.findRegion("backg3");
        backgrounds[3] = mainAtlas.findRegion("backg4");

        int backgroundHeight = WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;


        //initialize texture regions
        playerShipTextureRegion = mainAtlas.findRegion("1"); // From mainAtlas
        enemyShipTextureRegion = enemyAtlas.findRegion("Enemyship");
        playerShieldTextureRegion = mainAtlas.findRegion("shield1");
        playerShieldTextureRegion.flip(false, true);
        enemyShieldTextureRegion = mainAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(false, true);
        playerLaserTextureRegion = mainAtlas.findRegion("laserBlue06"); // From mainAtlas
        enemyLaserTextureregion = mainAtlas.findRegion("laserRed07"); // From enemyAtlas

        if (playerShipTextureRegion == null || enemyShipTextureRegion== null) {
            Gdx.app.log("TextureRegion Error", "playerShipTextureRegion not found in mainAtlas");
        }

        //set up game objects
        playerShip = new Ship(2,3,20,20,
        WORLD_WIDTH/2, WORLD_HEIGHT/4, playerShipTextureRegion,playerShieldTextureRegion);
        enemyShip = new Ship(2,1,10,10,
            WORLD_WIDTH/2, WORLD_HEIGHT*3/4, enemyShipTextureRegion,enemyShieldTextureRegion);
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        // Render scrolling background
        renderBackground(deltaTime);

        //enemy ships
        enemyShip.draw(batch);

        //player ships
        playerShip.draw(batch);
        //lasers

        //explosions

        batch.end();
    }

    private void renderBackground(float deltaTime) {
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer],
                0,
                -backgroundOffsets[layer], WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer],
                0,
                -backgroundOffsets[layer] + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (TextureRegion region : backgrounds) {
            if (region != null && region.getTexture() != null) {
                region.getTexture().dispose();
            }
        }

    }

    }


