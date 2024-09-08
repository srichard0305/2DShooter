package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StartScreen implements Screen {

    Game game;
    GameScreen gameScreen;
    Viewport viewport;
    Stage stage;

    StartScreen(Game game){

        this.game = game;
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table startScreenTable = new Table();
        startScreenTable.center();
        startScreenTable.setFillParent(true);

        Label welcomeLabel = new Label("Welcome to 2D Top Down Shooter", font);
        Label howToPlayLabel = new Label("Use WASD to move ship, Fire with Left mouse button, and aim with mouse", font);
        Label clickToPlayLabel = new Label("CLICK ANYWHERE TO PLAY", font);

        startScreenTable.add(welcomeLabel).expandX();
        startScreenTable.row();
        startScreenTable.add(howToPlayLabel).expandX().padTop(10f);
        startScreenTable.row();
        startScreenTable.add(clickToPlayLabel).expandX().padTop(10f);

        stage.addActor(startScreenTable);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            game.setScreen(new GameScreen((Main) game));
            dispose();
        }
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.draw();
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
        stage.dispose();
    }
}
