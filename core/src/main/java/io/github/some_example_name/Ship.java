package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;

public class Ship {

    private final float maxSpeed = 8.0f;

    private float shipX, shipY;

    private int timer;

    private Texture spaceShipTexture;

    private Sprite spaceShip;

    ArrayList<Bullet> lasers;

    Rectangle boundingRec;


    public Ship(float shipX, float shipY, Texture spaceShipTexture) {
        this.shipX = shipX;
        this.shipY = shipY;
        this.spaceShipTexture = spaceShipTexture;
        spaceShip = new Sprite(spaceShipTexture);
        lasers = new ArrayList<>();
        boundingRec = new Rectangle(shipX, shipY, spaceShip.getWidth(), spaceShip.getHeight());
        timer = 0;
    }

    public void draw(Batch batch){
        spaceShip.setPosition(shipX, shipY);
        boundingRec.setPosition(shipX, shipY);
        spaceShip.draw(batch);
    }

    //moves player around world
    public void movePlayer() {

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            shipY += maxSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            shipX -= maxSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            shipY -= maxSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            shipX += maxSpeed;
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

    public void shootLaser(){
        timer++;
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            if(timer > 15) {
                lasers.add(new Bullet(this.getCenterPosition(),
                    new Vector2(1.0f, 0.0f).rotateDeg(this.getRotationOfShip() + 90),
                    0));
                timer = 0;
            }
        }

        ArrayList<Bullet> temp = new ArrayList();
        for(Bullet bullet : lasers) {
            bullet.updateBullet(Gdx.graphics.getDeltaTime());
            bullet.timer++;
            if(bullet.timer > 300 ){
                temp.add(bullet);
            }
        }
        lasers.removeAll(temp);

    }


   public Vector2 getCenterPosition(){
        return new Vector2(spaceShip.getX() + (spaceShip.getWidth()/2), spaceShip.getY() + (spaceShip.getHeight()/2));
   }

   public float getRotationOfShip(){
        return spaceShip.getRotation();
   }


}
