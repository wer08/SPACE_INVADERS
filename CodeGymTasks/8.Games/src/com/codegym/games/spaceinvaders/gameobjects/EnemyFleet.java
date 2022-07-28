package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.engine.cell.Game;
import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;
import com.codegym.games.spaceinvaders.SpaceInvadersGame;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;


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
        int random = 0;
        ships = new ArrayList<EnemyShip>();
        for (int x = 0; x < COLUMNS_COUNT; x++)
        {
            for (int y = 0; y < ROWS_COUNT; y++)
            {
                random = (int)(Math.random() *100);
                if(random<5)
                {
                    ships.add(new EnemyShipInvincibility(x*STEP,y*STEP +12));
                }
                else
                {
                    ships.add(new EnemyShip(x * STEP, y * STEP + 12));
                }
            }
        }
        ships.add(new Boss(STEP * COLUMNS_COUNT / 2 - ShapeMatrix.BOSS_ANIMATION_FIRST.length / 2 - 1 , 5));
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
    public int checkHit(List<Bullet> bullets)
    {
        int score = 0;
        if(bullets.isEmpty())
            return score;
        else
        {
            boolean flag = false;
            for (EnemyShip ship : ships)
            {
                for (Bullet bullet : bullets)
                {
                    flag = ship.isCollision(bullet);
                    if (flag && bullet.isAlive && ship.isAlive)
                    {
                        if(ship instanceof EnemyShipInvincibility)
                        {
                            PlayerShip.setInvincible(true);
                            PlayerShip.count = 0;
                            bullet.kill();
                            ship.kill();
                            score += ship.score;
                        }
                        else
                        {
                            bullet.kill();
                            ship.kill();
                            score += ship.score;
                        }
                    }
                }
            }
        }
        return score;
    }

    public void deleteHiddenShips()
    {
        EnemyShip ship;
        ListIterator<EnemyShip> iterator = ships.listIterator();
        while (iterator.hasNext())
        {
            ship = iterator.next();
            if(!ship.isVisible())
            {
                iterator.remove();
            }
        }

    }

    public double getBottomBorder()
    {
        List<Double> yShips = new ArrayList<>();
        if(!ships.isEmpty())
        {
            for (EnemyShip ship : ships) {
                yShips.add(ship.y + ship.height);
            }
            Collections.sort(yShips);
            return yShips.get(yShips.size() - 1);
        }
        else
        {
            return 0;
        }
    }
    public int getShipCount()
    {
        return ships.size();
    }

}
