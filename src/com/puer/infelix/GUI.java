package com.puer.infelix;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GUI extends JFrame{
    public static boolean resetter = false;
    public static boolean flagger = false;

    public static Date startDate = new Date();
    public static Date endDate;
    public static int timeX = 1130;
    public static int timeY = 5;
    public static int sec = 0;

    public static int[][] mines = new int[16][9];
    public static int[][] neighbours = new int[16][9];
    public static boolean[][] revealed = new boolean[16][9];
    public static   boolean[][] flagged = new boolean[16][9];
    public static int neighs;

    public static int smileX = 605;
    public static int smileY = 5;
    public static int smileCenterX = smileX + 35;
    public static int smileCenterY = smileY + 35;
    public static boolean happy = true;
    public static boolean victory = false;
    public static boolean defeat = false;

    public static int vicMesX = 700;
    public static int vicMesY = -50;
    public static String vicMessage;

    public static int flaggerX = 445;
    public static int flaggerY = 5;
    public static int flaggerCenterX = flaggerX + 35;
    public static int flaggerCenterY = flaggerY + 35;

    public static Random rand = new Random();

    public GUI() {
        this.setTitle("Minesweeper");
        this.setSize(new Dimension(1296, 839));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (rand.nextInt(100) < 20) {
                    mines[i][j] = 1;
                } else {
                    mines[i][j] = 0;
                }
                revealed[i][j] = false;
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                neighs = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 9; n++) {
                        if (!(m == i && n == j)) {
                            if (isNeighbour(i, j, m, n)) {
                                neighs++;
                            }
                        }
                    }
                }
                neighbours[i][j] = neighs;
            }
        }

        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);

        this.setVisible(true);
    }

    public void checkVictoryStatus() {
        if (!defeat) {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 9; j++) {
                    if (revealed[i][j] && mines[i][j] == 1) {
                        defeat = true;
                        happy = false;
                        endDate = new Date();
                    }
                }
            }
        }

        if ((totalBoxesRevealed() >= 144 - totalMines()) && !victory) {
            victory = true;
            endDate = new Date();
        }
    }

    public static int totalMines() {
        int totalM = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (mines[i][j] == 1) {
                    totalM++;
                }
            }
        }

        return totalM;
    }

    public static int totalBoxesRevealed() {
        int totalR = 0;

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (revealed[i][j]) {
                    totalR++;
                }
            }
        }

        return totalR;
    }

    public static void resetBoard() {
        resetter = true;
        flagger = false;
        startDate = new Date();
        happy = true;
        victory = false;
        defeat = false;
        vicMesY = -50;

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (rand.nextInt(100) < 20) {
                    mines[i][j] = 1;
                } else {
                    mines[i][j] = 0;
                }
                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                neighs = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 9; n++) {
                        if (!(m == i && n == j)) {
                            if (isNeighbour(i, j, m, n)) {
                                neighs++;
                            }
                        }
                    }
                }
                neighbours[i][j] = neighs;
            }
        }

        resetter = false;
    }

    public static boolean inSmile() {
        int dif = (int) Math.sqrt(Math.abs(Board.x - 10 - smileCenterX) * Math.abs(Board.x - 10 - smileCenterX)
                + Math.abs(Board.y - 30 - smileCenterY) * Math.abs(Board.y - 30 - smileCenterY));

        return dif <= 35;
    }

    public static boolean inFlagger() {
        int dif = (int) Math.sqrt(Math.abs(Board.x - 10 - flaggerCenterX) * Math.abs(Board.x - 10 - flaggerCenterX)
                + Math.abs(Board.y - 30 - flaggerCenterY) * Math.abs(Board.y - 30 - flaggerCenterY));

        return dif <= 35;
    }

    public static int inBoxX() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (Board.x >= i * Board.WIDTH + Board.OFFSET + 8
                        && Board.x < i * Board.WIDTH + 80 - 2 * Board.OFFSET + Board.OFFSET + 9
                        && Board.y >= j * Board.WIDTH + Board.WIDTH + Board.OFFSET + 30
                        && Board.y < Board.OFFSET + j * Board.WIDTH + Board.WIDTH + Board.WIDTH - 2 * Board.OFFSET + 31) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int inBoxY() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (Board.x >= i * Board.WIDTH + Board.OFFSET + 8
                        && Board.x < i * Board.WIDTH + 80 - 2 * Board.OFFSET + Board.OFFSET + 9
                        && Board.y >= j * Board.WIDTH + Board.WIDTH + Board.OFFSET + 30
                        && Board.y < Board.OFFSET + j * Board.WIDTH + Board.WIDTH + Board.WIDTH - 2 * Board.OFFSET + 31) {
                    return j;
                }
            }
        }

        return -1;
    }

    public static boolean isNeighbour(int mX, int mY, int cX, int cY) {
        return mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mY - cY > -2 && mines[cX][cY] == 1;
    }
}

