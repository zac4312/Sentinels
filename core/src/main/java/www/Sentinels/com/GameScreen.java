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
    private Texture[] backgrounds;

    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;

    // World parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;
    public GameScreen(Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        // Initialize the backgrounds array
        backgrounds = new Texture[4];
        backgrounds[0] = new Texture("backg1.png");
        backgrounds[1] = new Texture("backg2.png");
        backgrounds[2] = new Texture("backg3.png");
        backgrounds[3] = new Texture("backg4.png");

        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {// ikaw na bahala sabot ani yAWAAAAAAA KAAAAAA IBALIK NI SA DATI, cge:(

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        // Render scrolling background
        renderBackground(deltaTime);

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
        for (Texture texture : backgrounds) {
            texture.dispose();
        }
    }
}
