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
    private static final int PLAYER_BULLETS_MAX =1;

    private List<Bullet> enemyBullets;
    private List<Bullet> playerBullets;

    private EnemyFleet enemyFleet;

    private List<Star> stars;

    private PlayerShip playerShip;

    private boolean isGameStopped;

    private int animationsCount;

    private int score;

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
        playerBullets = new ArrayList<Bullet>();
        isGameStopped = false;
        animationsCount = 0;
        score = 0;
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
        for (Bullet bullets:playerBullets)
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
        setScore(score);
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
        playerShip.move();

        for (Bullet bullet:enemyBullets)
        {
            if(bullet!=null)
            {
                bullet.move();
            }
        }
        for (Bullet bullet:playerBullets)
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
                    if ( bullet.y >= HEIGHT - 2 || !bullet.isAlive)
                    {
                        iterator.remove();
                    }
                }
            }
        ListIterator<Bullet> iterator2 = playerBullets.listIterator();
            while (iterator2.hasNext())
            {
                Bullet bullet = iterator2.next();
                if(bullet != null)
                {
                    if ( bullet.y + bullet.height < 0 || !bullet.isAlive)
                    {
                        iterator2.remove();
                    }
                }
            }
    }
    private void check()
    {
        playerShip.checkHit(enemyBullets);
        score += enemyFleet.checkHit(playerBullets);
        enemyFleet.deleteHiddenShips();
        removeDeadBullets();
        if(enemyFleet.getBottomBorder() >= playerShip.y)
        {
            playerShip.kill();
        }
        if(enemyFleet.getShipCount() == 0)
        {
            playerShip.win();
            stopGameWithDelay();
        }
        if(!playerShip.isAlive)
        {
            stopGameWithDelay();
        }
    }

    private void stopGame(boolean isWin)
    {
        isGameStopped = true;
        stopTurnTimer();
        if(isWin)
        {
            showMessageDialog(Color.ALICEBLUE,"SUCCESS",Color.GREEN,26);
        }
        else
        {
            showMessageDialog(Color.ALICEBLUE,"FAILURE",Color.RED,26);
        }
    }

    private void stopGameWithDelay()
    {
        animationsCount++;
        if(animationsCount >=10)
        {
            stopGame(playerShip.isAlive);
        }
    }

    @Override
    public void onKeyPress(Key key) {
        if(key == Key.SPACE&&isGameStopped)
        {
            createGame();
        }
        if(key == Key.SPACE)
        {
            Bullet bullet = playerShip.fire();
            if(bullet != null && playerBullets.size() < PLAYER_BULLETS_MAX)
            {
                playerBullets.add(bullet);
            }
        }
        if(key == Key.LEFT)
        {
            playerShip.setDirection(Direction.LEFT);
        }
        if(key == Key.RIGHT)
        {
            playerShip.setDirection((Direction.RIGHT));
        }
    }

    @Override
    public void onKeyReleased(Key key)
    {
        if(key == Key.LEFT&&playerShip.getDirection()==Direction.LEFT)
        {
            playerShip.setDirection(Direction.UP);
        }
        if(key == Key.RIGHT&&playerShip.getDirection()==Direction.RIGHT)
        {
            playerShip.setDirection(Direction.UP);
        }
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value)
    {
        if(x < 0 || x >= WIDTH || y < 0 || y>= HEIGHT)
        {
            return;
        }
        super.setCellValueEx(x, y, cellColor, value);
    }
}
