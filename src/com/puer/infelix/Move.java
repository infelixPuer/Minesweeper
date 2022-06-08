package com.puer.infelix;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Move implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
       Board.x = e.getX();
       Board.y = e.getY();
    }
}