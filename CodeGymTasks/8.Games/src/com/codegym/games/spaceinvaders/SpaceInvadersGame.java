package com.codegym.games.spaceinvaders;

import com.codegym.engine.cell.*;
import com.codegym.games.spaceinvaders.gameobjects.Bullet;
import com.codegym.games.spaceinvaders.gameobjects.EnemyFleet;
import com.codegym.games.spaceinvaders.gameobjects.PlayerShip;
import com.codegym.games.spaceinvaders.gameobjects.Star;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class SpaceInvadersGame extends Game
{
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int DIFFICULTY = 5;

    private List<Bullet> enemyBullets;

    private EnemyFleet enemyFleet;

    private List<Star> stars;
    private PlayerShip playerShip;
    @Override
    public void initialize()
    {
     setScreenSize(WIDTH,HEIGHT);
     createGame();
    }


    private void createGame()
    {
        createStars();
        enemyFleet = new EnemyFleet();
        enemyBullets = new ArrayList<Bullet>();
        playerShip = new PlayerShip();
        drawScene();
        setTurnTimer(40);
    }
    private void drawScene()
    {
        drawField();
        enemyFleet.draw(this);
        for (Bullet bullets:enemyBullets)
        {
            if( bullets != null)
            {
                bullets.draw(this);
            }
        }
        playerShip.draw(this);
    }
    private void drawField()
    {
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                setCellValueEx(x, y, Color.BLACK, "");
            }
        }
        for (Star star : stars) {
            star.draw(this);
        }
    }
    private void createStars()
    {
        stars = new ArrayList<Star>();
        stars.add(new Star(5,25));
        stars.add(new Star(15,50));
        stars.add(new Star(25,2));
        stars.add(new Star(35,62));
        stars.add(new Star(45,17));
        stars.add(new Star(55,38));
        stars.add(new Star(60,42));
        stars.add(new Star(39,31));
    }

    @Override
    public void onTurn(int step)
    {
        check();
        moveSpaceObjects();
        Bullet bullet = enemyFleet.fire(this);
        if(bullet != null)
        {
            enemyBullets.add(bullet);
        }
        drawScene();
    }
    private void moveSpaceObjects()
    {
        enemyFleet.move();

        for (Bullet bullet:enemyBullets)
        {
            if(bullet!=null)
            {
                bullet.move();
            }
        }
    }
    private void removeDeadBullets()
    {
        ListIterator<Bullet> iterator = enemyBullets.listIterator();
            while (iterator.hasNext())
            {
                Bullet bullet = iterator.next();
                if(bullet != null)
                {
                    if ( bullet.y >= HEIGHT - 1 || !bullet.isAlive)
                    {
                        iterator.remove();
                    }
                }
            }
    }
    private void check()
    {
        playerShip.checkHit(enemyBullets);
        removeDeadBullets();
    }
}
