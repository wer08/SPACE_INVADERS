package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.engine.cell.Game;
import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;
import com.codegym.games.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EnemyFleet
{
    private final static int ROWS_COUNT=3;
    private final static int COLUMNS_COUNT=10;
    private final static int STEP = ShapeMatrix.ENEMY.length+1;
    private List<EnemyShip> ships;

    private Direction direction = Direction.RIGHT;


    public EnemyFleet()
    {
        createShips();
    }
    public void draw(Game game)
    {
        for (EnemyShip objects:ships)
        {
         objects.draw(game);
        }
    }

    private void createShips()
    {
        ships = new ArrayList<EnemyShip>();
        for (int x = 0; x < COLUMNS_COUNT; x++)
        {
            for (int y = 0; y < ROWS_COUNT; y++)
            {
                ships.add(new EnemyShip(x*STEP,y*STEP +12)) ;
            }
        }
    }
    private double getLeftBorder()
    {
       List<Double> xValues = new ArrayList<Double>();
        for (EnemyShip objects: ships)
        {
            xValues.add(objects.x);
        }
        Collections.sort(xValues);
        return xValues.get(0);
    }
    private double getRightBorder()
    {
        List<Double> xValues = new ArrayList<Double>();
        int width = 0;
        for (EnemyShip objects: ships)
        {
            width = objects.width;
            xValues.add(objects.x);
        }
        Collections.sort(xValues);
        return xValues.get(xValues.size()-1) + width;
    }
    private double getSpeed()
    {
        return Math.min(2.0,3.0/ships.size());
    }
    public void move()
    {
        if(ships.isEmpty())
        {
            return;
        }
        else
        {
            boolean isChanged = false;
            if (direction == Direction.LEFT && getLeftBorder() < 0) {
                direction = Direction.RIGHT;
                isChanged = true;

            }
            else if (direction == Direction.RIGHT && getRightBorder() > SpaceInvadersGame.WIDTH)
            {
                direction = Direction.LEFT;
                isChanged = true;
            }
            else
            {
                isChanged = false;
            }
                for (EnemyShip objects : ships)
                {
                    if (isChanged)
                    {
                        objects.move(Direction.DOWN, getSpeed());
                    }
                    else
                    {
                        objects.move(direction, getSpeed());
                    }
                }

        }
    }
    public Bullet fire(Game game)
    {
        if(ships.isEmpty())
        {
            return null;
        }
        else
        {
           if(game.getRandomNumber(100 / SpaceInvadersGame.DIFFICULTY)>0)
           {
               return null;
           }
           else
           {
               return ships.get(game.getRandomNumber(ships.size())).fire();
           }
        }
    }

}
