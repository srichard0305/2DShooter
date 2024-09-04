package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private int shipX, shipY;

    Vector2 mousePosition;

    OrthographicCamera camera;
    Viewport viewport;

    SpriteBatch batch;
    Sprite spaceShip;

    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(30, 30 * (h / w));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        
        shipX = shipY = 100;
        spaceShip = new Sprite(new Texture("spaceship-removebg-preview.png"));

        batch = new SpriteBatch();

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();

        movePlayer();
        rotatePlayer();

        batch.begin();
        spaceShip.setPosition(shipX, shipY);
        spaceShip.draw(batch);
        batch.end();


    }

    //moves player around world
    public void movePlayer() {

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            shipY += 8;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            shipX -= 8;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            shipY -= 8;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            shipX += 8;
        }

    }

    public void rotatePlayer(){

        float xInput = Gdx.input.getX();
        float yInput = (Gdx.graphics.getHeight() - Gdx.input.getY());

        float angle = (float) Math.atan2(yInput - shipY, xInput - shipX);
        angle -= Math.PI/2;
        angle = angle * MathUtils.radDeg;

        spaceShip.setRotation(angle);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
