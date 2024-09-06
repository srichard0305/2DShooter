package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

    final float maxSpeecd = 200f;

    public Vector2 position;
    public Vector2 direction;

    public Bullet(Vector2 position, Vector2 direction) {
        this.position = position;
        this.direction = direction;
    }

    public void updateBullet(float delta){
        position.add(direction.x * delta * maxSpeecd, direction.y * delta * maxSpeecd);
    }
}
