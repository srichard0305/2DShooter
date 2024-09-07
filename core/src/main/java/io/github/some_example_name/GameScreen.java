package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class GameScreen implements Screen {

    OrthographicCamera camera;

    SpriteBatch batch;

    Ship playerShip;

    ShapeRenderer shapeRenderer;

    ArrayList<EnemyShip> enemyShips;

    int timer = 0;

    int enemiesKilled;

    GameOverScreen gameOverScreen;

    Game game;


    GameScreen(Game game){

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        playerShip = new Ship(w/2, h/2, new Texture("spaceship-removebg-preview.png"));

        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();

        enemyShips = new ArrayList<>();

        this.game = game;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();

        if(timer > 300){
            enemyShips.add(new EnemyShip(0,0, new Texture("enemy.png")));
            timer = 0;
        }

        batch.begin();
        playerShip.draw(batch);
        for(int i = 0; i < enemyShips.size(); i++)
            enemyShips.get(i).draw(batch, playerShip);
        batch.end();

        playerShip.movePlayer();
        playerShip.rotatePlayer();
        playerShip.shootLaser();

        for(int i = 0; i < enemyShips.size(); i++)
            enemyShips.get(i).moveEnemyShip(playerShip);


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        for(Bullet bullet : playerShip.lasers)
            shapeRenderer.circle(bullet.position.x, bullet.position.y, 5f, 32);
        shapeRenderer.end();

        detectCollision();

        timer++;
    }

    @Override
    public void resize(int width, int height) {

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
        shapeRenderer.dispose();
        gameOverScreen.dispose();
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
                gameOver();
            }
        }

    }

    public void gameOver(){
        gameOverScreen = new GameOverScreen(game);
        game.setScreen(gameOverScreen);

    }
}
