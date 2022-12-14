package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.games.spaceinvaders.Direction;
import com.codegym.games.spaceinvaders.ShapeMatrix;
import com.codegym.games.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.List;

public class PlayerShip extends Ship
{
    public static boolean isInvincible = false;
    public boolean change = false;
    public static boolean isHItDoubleShot = false;

    public static int count =0;

    public static void setInvincible(boolean invincible)
    {
        isInvincible = invincible;
    }
    private Direction direction = Direction.UP;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction newDirection) {
        if(newDirection != Direction.DOWN)
        {
            direction = newDirection;
        }
    }

    public PlayerShip()
    {
        super(SpaceInvadersGame.WIDTH / 2.0, SpaceInvadersGame.HEIGHT - ShapeMatrix.PLAYER.length - 1);
        setStaticView(ShapeMatrix.PLAYER);
    }
    public void checkHit(List<Bullet> bullets)
    {

        if(bullets.isEmpty())
        {
            return;
        }
        else
        {
            if(this.isAlive)
            {
                for (Bullet bullet:bullets)
                {

                    if(bullet.isAlive)
                    {
                        if (isCollision(bullet))
                        {
                            System.out.println(count);
                            if (count == 1)
                            {
                                change = true;
                            }
                            if(count == 2)
                            {
                                setInvincible(false);
                                change = false;
                            }
                            bullet.kill();
                            kill();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void kill()
    {
        if(!this.isAlive)
            return;
        else
        {
            if(PlayerShip.isInvincible)
            {
                count++;
            }
            else
            {
                isAlive = false;
                setAnimatedView(false, ShapeMatrix.KILL_PLAYER_ANIMATION_FIRST, ShapeMatrix.KILL_PLAYER_ANIMATION_SECOND, ShapeMatrix.KILL_PLAYER_ANIMATION_THIRD, ShapeMatrix.DEAD_PLAYER);
            }
        }
    }


    public void move()
    {
        if(!isAlive)
        {
            return;
        }
        if(direction == Direction.LEFT)
        {
            x--;
        }
        if(direction == Direction.RIGHT)
        {
            x++;
        }
        if(x<0)
        {
            x=0;
        }
        if(x + this.width>SpaceInvadersGame.WIDTH )
        {
            x = SpaceInvadersGame.WIDTH - this.width;
        }
    }
    public List<Bullet> fireLaser()
    {
        List<Bullet> bulletList = new ArrayList<>();
        if(!this.isAlive)
        {
            return null;
        }
        else if(isHItDoubleShot)
        {
            System.out.println("DOUBLE SHOT ACTIVATED");
            bulletList.add(new Laser(x , y - ShapeMatrix.BULLET.length, Direction.UP));
            bulletList.add(new Laser(x + 4, y - ShapeMatrix.BULLET.length, Direction.UP));
            return bulletList;
        }
        else
        {
            bulletList.add(new Laser(x + 2, y - ShapeMatrix.BULLET.length, Direction.UP));
            return bulletList;
        }
    }
    @Override
    public List<Bullet> fire()
    {
        List<Bullet> bulletList = new ArrayList<>();
        if(!this.isAlive)
        {
            return null;
        }
        else if(isHItDoubleShot)
        {
            bulletList.add(new Bullet(x , y - ShapeMatrix.BULLET.length, Direction.UP));
            bulletList.add(new Bullet(x + 4, y - ShapeMatrix.BULLET.length, Direction.UP));
            return bulletList;
        }
        else
        {
            bulletList.add(new Bullet(x + 2, y - ShapeMatrix.BULLET.length, Direction.UP));
            return bulletList;
        }
    }


    public void win()
    {
        setStaticView(ShapeMatrix.WIN_PLAYER);

    }
}
