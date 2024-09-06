package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class EnemyShip {

    private final float maxSpeed = 4.0f;

    private float shipX, shipY;

    private Texture enemySpaceShipTexture;

    private Sprite enemySpaceShip;

    Rectangle boundingRec;

    boolean isDestroyed = false;

    public EnemyShip(float shipX, float shipY, Texture enemySpaceShipTexture) {
        this.shipX = shipX;
        this.shipY = shipY;
        this.enemySpaceShipTexture = enemySpaceShipTexture;
        enemySpaceShip = new Sprite(enemySpaceShipTexture);
        boundingRec = new Rectangle(shipX, shipY, enemySpaceShip.getWidth(), enemySpaceShip.getHeight());
    }

    public void draw(Batch batch, Ship ship){
        enemySpaceShip.setPosition(shipX, shipY);
        boundingRec.setPosition(shipX, shipY);
        float angle = (float) Math.atan2(ship.getCenterPosition().y - shipY, ship.getCenterPosition().x - shipX);
        angle -= Math.PI/2;
        angle = angle * MathUtils.radDeg;
        enemySpaceShip.setRotation(angle);

        enemySpaceShip.draw(batch);
    }

    public void moveEnemyShip(Ship ship){

        Vector2 seekResult = this.seek(ship);
        shipX += seekResult.x;
        shipY += seekResult.y;
    }

    public Vector2 seek(Ship ship){

        Vector2 result = new Vector2();

        // get direction to target
        result.set(ship.getCenterPosition().x - shipX, ship.getCenterPosition().y - shipY);

        // the velocity in this direction at max speed
        result.nor();
        result.set(result.x * maxSpeed, result.y * maxSpeed);

        return result;
    }
}
