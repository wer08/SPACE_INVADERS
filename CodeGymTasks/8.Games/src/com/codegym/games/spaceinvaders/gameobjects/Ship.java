package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship extends GameObject
{
    public boolean isAlive = true;
    private List<int[][]> frames;
    private int frameIndex;
    public Ship(double x, double y) {
        super(x, y);
    }

    public void setStaticView(int[][] viewFrame)
    {
        frames = new ArrayList<int[][]>();
        frames.add(viewFrame);
        frameIndex = 0;
        setMatrix(viewFrame);
    }
    public Bullet fire()
    {
        return null;
    }
    public void kill()
    {
        isAlive = false;
    }
    public void setAnimatedView(int[][]... viewFrames)
    {
        setMatrix(viewFrames[0]);
        frames = Arrays.asList(viewFrames);
        frameIndex = 0;
    }

    public void nextFrame()
    {
        if(frameIndex >= frames.size() -1)
        {
            return;
        }
        else
        {
            frameIndex++;
            setMatrix(frames.get(frameIndex));
        }
    }

    @Override
    public void draw(Game game) {
        super.draw(game);
        nextFrame();
    }
}
