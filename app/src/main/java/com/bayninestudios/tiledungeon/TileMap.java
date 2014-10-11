package com.bayninestudios.tiledungeon;

import android.util.Log;

import java.util.Random;

/**
 * Created by lee on 10/10/14.
 */
public class TileMap {
    public int SIZE = 50;
    public int[][] tiles = new int[SIZE][SIZE];

    public TileMap() {
        Random rand = new Random();
        rand.setSeed((long) 5);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles[i][j] = rand.nextInt(2);
            }
        }
    }

    public void initCheckers() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles[i][j] = i % 2;
            }
        }
    }

}
