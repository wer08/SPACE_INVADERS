package com.codegym.games.spaceinvaders.gameobjects;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;
import com.codegym.engine.cell.*;

public class Star extends GameObject
{
    public Star(double x, double y) {
        super(x, y);
    }
    private static final String STAR_SIGN = "\u2605";
    public void draw(Game game)
    {
        game.setCellValueEx((int) x, (int) y, Color.NONE, STAR_SIGN, Color.WHITE, 100);

    }
}
