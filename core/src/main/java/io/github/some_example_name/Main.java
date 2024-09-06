package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    OrthographicCamera camera;

    SpriteBatch batch;

    Ship playerShip;

    ShapeRenderer shapeRenderer;

    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        playerShip = new Ship(w/2, h/2, new Texture("spaceship-removebg-preview.png"));

        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();

        batch.begin();
        playerShip.draw(batch);
        batch.end();

        playerShip.movePlayer();
        playerShip.rotatePlayer();
        playerShip.shootLaser();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        for(Bullet bullet : playerShip.lasers)
            shapeRenderer.circle(bullet.position.x, bullet.position.y, 5f, 32);
        shapeRenderer.end();

    }


    @Override
    public void dispose() {
        batch.dispose();
    }
}
