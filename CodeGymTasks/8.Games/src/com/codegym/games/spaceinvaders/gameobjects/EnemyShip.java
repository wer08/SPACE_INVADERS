package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;

public class EnemyShip extends Ship
{
    public EnemyShip(double x, double y) {
        super(x, y);
        setStaticView(ShapeMatrix.ENEMY);
    }
    public void move(Direction direction, double speed)
    {
        if(direction == Direction.RIGHT)
        {
            x += speed;
        }
        if(direction == Direction.LEFT)
        {
            x -= speed;
        }
        if(direction == Direction.DOWN)
        {
            y += 2;
        }
    }

    @Override
    public Bullet fire() {
        return new Bullet(x + 1, y + height,Direction.DOWN);
    }
}
