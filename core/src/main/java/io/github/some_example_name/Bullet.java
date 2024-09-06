package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

    final float maxSpeecd = 200f;
    float deltaTime;

    public Vector2 position;
    public Vector2 direction;

    Rectangle boundingRec;

    public Bullet(Vector2 position, Vector2 direction, float deltaTime) {
        this.position = position;
        this.direction = direction;
        this.deltaTime = deltaTime;
        boundingRec = new Rectangle(position.x, position.y, 5, 5);

    }

    public void updateBullet(float delta){
        position.add(direction.x * delta * maxSpeecd, direction.y * delta * maxSpeecd);
        boundingRec.setPosition(position.x, position.y);
    }
}
