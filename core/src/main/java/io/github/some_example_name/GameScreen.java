package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {

    OrthographicCamera camera;

    SpriteBatch batch;

    Ship playerShip;

    ShapeRenderer shapeRenderer;

    ArrayList<EnemyShip> enemyShips;

    int timer = 0;
    int timerForFlock = 0;

    int enemiesKilled;

    GameOverScreen gameOverScreen;

    Game game;

    Label enemiesKilledLabel;
    Table enemiesKilledTable;
    Label.LabelStyle font;
    Stage stage;
    Viewport viewport;

    Random rand;

    Flock flock;

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

        enemiesKilled = 0;

        font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport);

        enemiesKilledTable = new Table();
        enemiesKilledTable.bottom();
        enemiesKilledTable.setFillParent(true);

        enemiesKilledLabel = new Label("Kill Count: " + enemiesKilled, font);

        enemiesKilledTable.add(enemiesKilledLabel);

        stage.addActor(enemiesKilledTable);

        rand = new Random();

        flock = new Flock(playerShip);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();

        stage.draw();

        // Randomly spawn enemies off screen
        float x = rand.nextFloat() * (1400 - 1) + 1;
        float y = rand.nextFloat() * (800 - 1) + 1;

        if(x > 700 && y > 400){
            x += 700f;
            y += 400f;
        }
        else{
            x -= 700f;
            y -= 400f;
        }

        if(timer > 200){
            enemyShips.add(new EnemyShip(x,y, new Texture("enemy.png")));
            timer = 0;
        }

        if(timerForFlock > 400){
            flock = new Flock(playerShip);
            timerForFlock = 0;
        }

        batch.begin();
        playerShip.draw(batch);
        for(int i = 0; i < enemyShips.size(); i++)
            enemyShips.get(i).draw(batch, playerShip);
        for(EnemyShip ship : flock.ships){
            ship.enemySpaceShip.draw(batch);
        }
        batch.end();

        playerShip.movePlayer();
        playerShip.rotatePlayer();
        playerShip.shootLaser();

        flock.flock();

        for(int i = 0; i < enemyShips.size(); i++) {
            enemyShips.get(i).moveEnemyShip(playerShip, enemyShips);
        }


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        for(Bullet bullet : playerShip.lasers)
            shapeRenderer.circle(bullet.position.x, bullet.position.y, 5f, 32);
        shapeRenderer.end();

        detectCollision();

        updateKillCount();

        timer++;
        timerForFlock++;
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
        stage.dispose();
        shapeRenderer.dispose();
        gameOverScreen.dispose();
    }

    public void detectCollision(){

        ArrayList<EnemyShip> tempList = new ArrayList<>();

        for(int i = 0; i < enemyShips.size(); i++) {
            for (Bullet bullet : playerShip.lasers) {
                if(enemyShips.get(i).boundingRec.overlaps(bullet.boundingRec)){
                    tempList.add(enemyShips.get(i));
                    enemiesKilled++;
                }
            }
        }
        enemyShips.removeAll(tempList);
        tempList.clear();

        for(int i = 0; i < flock.ships.size(); i++) {
            for (Bullet bullet : playerShip.lasers) {
                if(flock.ships.get(i).boundingRec.overlaps(bullet.boundingRec)){
                    tempList.add(flock.ships.get(i));
                    enemiesKilled++;
                }
            }
        }
        flock.ships.removeAll(tempList);


        for(EnemyShip enemyShip : enemyShips){

            if(enemyShip.boundingRec.overlaps(playerShip.boundingRec)){
                gameOver();
            }
        }

        for(EnemyShip enemyShip : flock.ships){
            if(enemyShip.boundingRec.overlaps(playerShip.boundingRec)){
                gameOver();
            }
        }

    }

    public void updateKillCount(){
        enemiesKilledLabel.setText("Kill Count: " + enemiesKilled);
    }

    public void gameOver(){
        gameOverScreen = new GameOverScreen(game);
        game.setScreen(gameOverScreen);

    }
}
