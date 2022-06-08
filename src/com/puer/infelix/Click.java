package com.puer.infelix;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Click implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        Board.x = e.getX();
        Board.y = e.getY();

        if (GUI.inBoxX() != -1 && GUI.inBoxY() != -1) {
            if (!GUI.victory && !GUI.defeat) {
                if (GUI.flagger && !GUI.revealed[GUI.inBoxX()][GUI.inBoxY()]) {
                    GUI.flagged[GUI.inBoxX()][GUI.inBoxY()] = !GUI.flagged[GUI.inBoxX()][GUI.inBoxY()];
                } else {
                    GUI.revealed[GUI.inBoxX()][GUI.inBoxY()] = !GUI.flagged[GUI.inBoxX()][GUI.inBoxY()];
                }
            }
        }

        if (GUI.inFlagger()) {
            GUI.flagger = !GUI.flagger;
        }

        if (GUI.inSmile()) {
            GUI.resetBoard();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
