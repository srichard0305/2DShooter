package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

    final float maxSpeecd = 200f;
    int timer;

    public Vector2 position;
    public Vector2 direction;

    Rectangle boundingRec;

    public Bullet(Vector2 position, Vector2 direction, int timer) {
        this.position = position;
        this.direction = direction;
        this.timer = timer;
        boundingRec = new Rectangle(position.x, position.y, 5, 5);

    }

    public void updateBullet(float delta){
        position.add(direction.x * delta * maxSpeecd, direction.y * delta * maxSpeecd);
        boundingRec.setPosition(position.x, position.y);
    }
}
