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

import java.util.ArrayList;
import java.util.Collections;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    OrthographicCamera camera;

    SpriteBatch batch;

    Ship playerShip;

    ShapeRenderer shapeRenderer;

    ArrayList<EnemyShip> enemyShips;

    int timer = 0;

    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        playerShip = new Ship(w/2, h/2, new Texture("spaceship-removebg-preview.png"));

        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();

        enemyShips = new ArrayList<>();

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();

        if(timer > 300){
            enemyShips.add(new EnemyShip(0,0, new Texture("enemy.png")));
            timer = 0;
        }

        batch.begin();
        playerShip.draw(batch);
        for(int i = 0; i < enemyShips.size(); i++){
            if(enemyShips.get(i).isDestroyed)
                continue;
            enemyShips.get(i).draw(batch, playerShip);
        }
        batch.end();

        playerShip.movePlayer();
        playerShip.rotatePlayer();
        playerShip.shootLaser();
        for(int i = 0; i < enemyShips.size(); i++){
            if(enemyShips.get(i).isDestroyed)
                continue;
            enemyShips.get(i).moveEnemyShip(playerShip);
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        for(Bullet bullet : playerShip.lasers)
            shapeRenderer.circle(bullet.position.x, bullet.position.y, 5f, 32);
        shapeRenderer.end();

        detectCollision();

        timer++;

    }

    public void detectCollision(){

        ArrayList<EnemyShip> tempList = new ArrayList<>();

        for(int i = 0; i < enemyShips.size(); i++) {
            for (Bullet bullet : playerShip.lasers) {
                if(enemyShips.get(i).boundingRec.overlaps(bullet.boundingRec)){
                    tempList.add(enemyShips.get(i));

                }
            }
        }
        enemyShips.removeAll(tempList);
       

        for(EnemyShip enemyShip : enemyShips){

            if(enemyShip.boundingRec.overlaps(playerShip.boundingRec)){
            }
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
