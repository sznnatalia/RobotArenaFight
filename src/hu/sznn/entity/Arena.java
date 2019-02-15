/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.sznn.entity;

import lombok.Getter;


/**
 *
 * @author Nati
 */

public class Arena {

    @Getter
    private String[][] table;
    @Getter
    private final int height;
    @Getter
    private final int width;
    private Robot robotA;
    private Robot robotB;

    public Arena(int height, int width, Robot robotA, Robot robotB) {
        this.height = height;
        this.width = width;
        this.robotA = robotA;
        this.robotB = robotB;
    }

    

   
    public void arenaInitialize() {//fill the arena array
        table = new String[height][width];

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = "_";
            }
        }
        
        int xRobotA = robotA.getPosition().getX();
        int yRobotA = robotA.getPosition().getY();
        
        int xRobotB = robotB.getPosition().getX();
        int yRobotB = robotB.getPosition().getY();
        
        table[xRobotA][yRobotA] = robotA.getSign();
        table[xRobotB][yRobotB] = robotB.getSign();
        
    }

    public void arenaDraw() {//kirajzolja az arenat
        
        for (int i = 0; i < (table[0].length)*2+1; i++) {
            System.out.print("#");
        }
        System.out.println();

        for (int i = 0; i < table.length; i++) {
            System.out.print("#");
            for (int j = 0; j < table[i].length; j++) {

                if (j == table[i].length - 1) {
                    System.out.print(table[i][j]);
                } else {
                    System.out.print(table[i][j] + "|");
                }

                
            }

            System.out.print("#");
            System.out.println();

        }

        for (int i = 0; i < (table[0].length)*2+1; i++) {
            System.out.print("#");
        }
        System.out.println();

    }
}
