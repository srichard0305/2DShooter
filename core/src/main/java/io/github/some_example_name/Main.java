package io.github.some_example_name;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    GameScreen gameScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }

    @Override
    public void render() {
        super.render();
    }


    @Override
    public void resize(int width, int height){
        gameScreen.resize(width, height);
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
    }
}
