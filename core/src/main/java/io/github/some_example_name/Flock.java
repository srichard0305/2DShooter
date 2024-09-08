package io.github.some_example_name;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Flock {

    Ship playerShip;
    ArrayList<EnemyShip> ships;

    float orientation;

    Flock(Ship playerShip){

        this.playerShip = playerShip;
        ships = new ArrayList<>();

        for(int i = 0; i<5; i++){
            ships.add(new EnemyShip(-10,-10, new Texture("enemy.png")));
        }

    }


    public void flock(){
        //find the seek vector, separate vector, cohesion vectors, and alignment
        for(int i = 0; i < ships.size(); i++){
            Vector2 seek = new Vector2(ships.get(i).seek(playerShip));
            Vector2 separate = new Vector2(ships.get(i).separate(ships));
            Vector2 cohesion = new Vector2(cohesion(ships.get(i)));
            orientation = ships.get(i).align(playerShip);

            // sum all steering behaviours
            Vector2 sum = new Vector2(seek.x + separate.x + cohesion.x, seek.y +separate.y + cohesion.y);
            ships.get(i).shipX += sum.x;
            ships.get(i).shipY += sum.y;

            //set position
            ships.get(i).enemySpaceShip.setPosition(ships.get(i).shipX + sum.x, ships.get(i).shipY + sum.y);
            ships.get(i).boundingRec.setPosition(ships.get(i).shipX + sum.x, ships.get(i).shipY + sum.y);
            ships.get(i).enemySpaceShip.setRotation(orientation);
        }

    }

    // cohesion steering behaviour
    public Vector2 cohesion(EnemyShip enemyShip){
        Vector2 sum = new Vector2();
        int count = 0;

        //calculate the mass of all the shits based on their position
        for(EnemyShip ship : ships) {
            sum.set(sum.x + ship.shipX, sum.y + ship.shipY);
            count++;
        }

        //find the average mass, subtract it from the ships position, nornmalize
        if(count > 0){
            sum.set(sum.x/(float) count, sum.y/(float) count);
            sum.set(sum.x - enemyShip.shipX, sum.y - enemyShip.shipY);
            sum.nor();
            return sum;
        }
        else
            return new Vector2(0,0);

    }





}
