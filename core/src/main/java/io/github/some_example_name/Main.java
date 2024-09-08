package io.github.some_example_name;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    StartScreen startScreen;

    @Override
    public void create() {
        startScreen = new StartScreen(this);
        setScreen(startScreen);
    }

    @Override
    public void render() {
        super.render();
    }


    @Override
    public void resize(int width, int height){
        startScreen.resize(width, height);
    }

    @Override
    public void dispose() {
        startScreen.dispose();
    }
}
