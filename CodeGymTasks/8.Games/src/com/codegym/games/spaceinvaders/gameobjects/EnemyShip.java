package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;

import java.util.ArrayList;
import java.util.List;

public class EnemyShip extends Ship
{
    public int score =15;



    public EnemyShip(double x, double y)
    {
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
    public List<Bullet> fire()
    {
        List<Bullet> bulletList = new ArrayList<>();
        bulletList.add(new Bullet(x + 1, y + height,Direction.DOWN));
        return bulletList;
    }

    @Override
    public void kill()
    {
        if(!this.isAlive)
        {
            return;
        }
        isAlive = false;


        setAnimatedView(false, ShapeMatrix.KILL_ENEMY_ANIMATION_FIRST, ShapeMatrix.KILL_ENEMY_ANIMATION_SECOND, ShapeMatrix.KILL_ENEMY_ANIMATION_THIRD);
    }
}
