package com.puer.infelix;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Board extends JPanel{
    public static final int OFFSET = 5;
    public static final int WIDTH = 80;
    public static int x;
    public static int y;

    public void paintComponent(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 1280, 800);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                g.setColor(Color.GRAY);
                if (GUI.revealed[i][j]) {
                    if (GUI.mines[i][j] == 1) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                }

                if (x >= i * WIDTH + OFFSET + 8 && x < i * WIDTH + 80 - 2 * OFFSET + OFFSET + 9
                        && y >= j * WIDTH + WIDTH + OFFSET + 30 && y < OFFSET + j * WIDTH + WIDTH + WIDTH - 2 * OFFSET + 31) {
                    g.setColor(Color.lightGray);
                }

                g.fillRect(i * WIDTH + OFFSET, j * WIDTH + WIDTH + OFFSET, WIDTH - 2 * OFFSET, WIDTH - 2 * OFFSET);

                if (GUI.revealed[i][j]) {
                    g.setColor(Color.black);
                    if (GUI.mines[i][j] == 0 && GUI.neighbours[i][j] != 0) {
                        if (GUI.neighbours[i][j] == 1) {
                            g.setColor(Color.blue);
                        } else if (GUI.neighbours[i][j] == 2) {
                            g.setColor(Color.green);
                        } else if (GUI.neighbours[i][j] == 3) {
                            g.setColor(Color.RED);
                        } else if (GUI.neighbours[i][j] == 4) {
                            g.setColor(new Color(0,0,128));
                        } else if (GUI.neighbours[i][j] == 5) {
                            g.setColor(new Color(178, 34, 34));
                        } else if (GUI.neighbours[i][j] == 6) {
                            g.setColor(new Color(72, 209, 204));
                        }  else if (GUI.neighbours[i][j] == 8) {
                            g.setColor(Color.DARK_GRAY);
                        }
                        g.setFont(new Font("Tahoma", Font.BOLD, 40));
                        g.drawString(Integer.toString(GUI.neighbours[i][j]), i * WIDTH + 27, j * WIDTH + 135);
                    } else if (GUI.mines[i][j] == 1) {
                        g.fillRect(i * WIDTH + 30, j * WIDTH + WIDTH + 20, 20, 40);
                        g.fillRect(i * WIDTH + 20, j * WIDTH + WIDTH + 30, 40, 20);
                        g.fillRect(i * WIDTH + 25, j * WIDTH + WIDTH + 25, 30, 30);
                        g.fillRect(i * WIDTH + 38, j * WIDTH + WIDTH + 15, 4, 50);
                        g.fillRect(i * WIDTH + 15, j * WIDTH + WIDTH + 38, 50, 4);
                    }
                }

                if (GUI.defeat) {
                    if (GUI.mines[i][j] == 1) {
                        GUI.flagged[i][j] = false;
                        GUI.revealed[i][j] = true;
                    }
                }

                // painting flags

                if (GUI.flagged[i][j]) {
                    g.setColor(Color.black);
                    g.fillRect(i * WIDTH + 37, j * WIDTH + WIDTH + 20, 5, 40);
                    g.fillRect(i * WIDTH + 25, j * WIDTH + WIDTH + 55   , 30, 10);
                    g.setColor(Color.red);
                    g.fillRect(i * WIDTH + 21, j * WIDTH + WIDTH + 20, 20, 15);
                    g.setColor(Color.BLACK);
                    g.drawRect(i * WIDTH + 21, j * WIDTH + WIDTH + 20, 20, 15);
                    g.drawRect(i * WIDTH + 22, j * WIDTH + WIDTH + 21, 18, 13);
                    g.drawRect(i * WIDTH + 23, j * WIDTH + WIDTH + 22, 16, 11);
                }
            }
        }

        // painting smile

        g.setColor(Color.YELLOW);
        g.fillOval(GUI.smileX, GUI.smileY, 70, 70);
        g.setColor(Color.black);
        g.fillOval(GUI.smileX + 15, GUI.smileY + 20, 10, 10);
        g.fillOval(GUI.smileX + 45, GUI.smileY + 20, 10, 10);
        if (GUI.happy) {
            g.fillRect(GUI.smileX + 20, GUI.smileY + 50, 30, 5);
            g.fillRect(GUI.smileX + 17, GUI.smileY + 45, 5, 5);
            g.fillRect(GUI.smileX + 48, GUI.smileY + 45, 5, 5);
        } else {
            g.fillRect(GUI.smileX + 20, GUI.smileY + 45, 30, 5);
            g.fillRect(GUI.smileX + 17, GUI.smileY + 50, 5, 5);
            g.fillRect(GUI.smileX + 48, GUI.smileY + 50, 5, 5);
        }

        // painting flagger

        g.setColor(Color.black);
        g.fillRect(GUI.flaggerX + 32, GUI.flaggerY + 15, 5, 40);
        g.fillRect(GUI.flaggerX + 20, GUI.flaggerY + 50, 30, 10);
        g.setColor(Color.red);
        g.fillRect(GUI.flaggerX + 16, GUI.flaggerY + 15, 20, 15);
        g.setColor(Color.BLACK);
        g.drawRect(GUI.flaggerX + 16, GUI.flaggerY + 15, 20, 15);
        g.drawRect(GUI.flaggerX + 17, GUI.flaggerY + 16, 18, 13);
        g.drawRect(GUI.flaggerX + 18, GUI.flaggerY + 17, 16, 11);

        if (GUI.flagger) {
            g.setColor(Color.red);
        }

        g.drawOval(GUI.flaggerX, GUI.flaggerY, 70, 70);
        g.drawOval(GUI.flaggerX + 1, GUI.flaggerY + 1, 68, 68);
        g.drawOval(GUI.flaggerX + 2, GUI.flaggerY + 2, 66, 66);

        // painting time

        g.setColor(Color.black);
        g.fillRect(GUI.timeX, GUI.timeY, 140, 70);
        if (!GUI.defeat && !GUI.victory) {
            GUI.sec = (int) (new Date().getTime() - GUI.startDate.getTime()) / 1000;
        }
        if (GUI.sec > 999) {
            GUI.sec = 999;
        }
        g.setColor(Color.WHITE);
        if (GUI.victory) {
            g.setColor(Color.green);
        } else if (GUI.defeat) {
            g.setColor(Color.RED);
        }
        g.setFont(new Font("Tahoma", Font.PLAIN, 80));
        if (GUI.sec < 10) {
            g.drawString("00" +GUI.sec, GUI.timeX, GUI.timeY + 65);
        } else if (GUI.sec < 100) {
            g.drawString("0" +GUI.sec, GUI.timeX, GUI.timeY + 65);
        } else {
            g.drawString(Integer.toString(GUI.sec), GUI.timeX, GUI.timeY + 65);
        }

        // painting message

        if (GUI.victory) {
            g.setColor(Color.green);
            GUI.vicMessage = "YOU WON!";
        } else if (GUI.defeat) {
            g.setColor(Color.red);
            GUI.vicMessage = "YOU LOST!";
        }

        if (GUI.victory || GUI.defeat) {
            GUI.vicMesY = (int) (-50 + (new Date().getTime() - GUI.endDate.getTime()) / 10);
            if (GUI.vicMesY > 70) {
                GUI.vicMesY = 70;
            }
            g.setFont(new Font("Tahoma", Font.PLAIN, 80));
            g.drawString(GUI.vicMessage, GUI.vicMesX, GUI.vicMesY);
        }
    }
}
