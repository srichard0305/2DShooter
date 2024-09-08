package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class EnemyShip {

    private final float maxSpeed = 4.0f;

    float shipX, shipY;

    private Texture enemySpaceShipTexture;

    Sprite enemySpaceShip;

    Rectangle boundingRec;

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
        enemySpaceShip.setRotation(align(ship));
        enemySpaceShip.draw(batch);
    }

    // method to move the ship based on the result from seek and separate
    public void moveEnemyShip(Ship ship, ArrayList<EnemyShip> boid){

        Vector2 seekResult = this.seek(ship);
        Vector2 separateResult = this.separate(boid);
        shipX += seekResult.x + separateResult.x;
        shipY += seekResult.y + separateResult.y;

    }


    // AI steering behaviour seek, directs enemy ship to player
    public Vector2 seek(Ship ship){

        Vector2 result = new Vector2();

        // get direction to target
        result.set(ship.getCenterPosition().x - shipX, ship.getCenterPosition().y - shipY);

        // the velocity in this direction at max speed
        result.nor();
        result.set(result.x * maxSpeed, result.y * maxSpeed);

        return result;
    }

    public Vector2 separate(ArrayList<EnemyShip> boid){
        float desiredSpearation = (enemySpaceShip.getWidth() + enemySpaceShip.getHeight()) * 2;
        Vector2 sum = new Vector2();
        int count = 0;

        for(EnemyShip ship : boid){
            float d = Vector2.dst(shipX, shipY, ship.shipX, ship.shipY);

            if(d > 0 && d < desiredSpearation){
                Vector2 diff = new Vector2(shipX - ship.shipX, shipY - ship.shipY);
                diff.nor();
                diff.set(diff.x/d, diff.y/d);
                sum.add(diff);
                count++;
            }
        }

        if(count > 0){

            sum.set(sum.x/(float) count, sum.y/(float) count);
            sum.nor();
            sum.set(sum.x*maxSpeed, sum.y*maxSpeed);
            return sum;
        }
        else
            return new Vector2(0,0);

    }

    public float align(Ship ship){
        float angle = (float) Math.atan2(ship.getCenterPosition().y - shipY, ship.getCenterPosition().x - shipX);
        angle -= Math.PI/2;
        angle = angle * MathUtils.radDeg;
        return angle;
    }



}
