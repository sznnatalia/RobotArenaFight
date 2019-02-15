/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.sznn;

import hu.sznn.action.GameExecutor;
import hu.sznn.entity.Arena;
import hu.sznn.entity.Knight;
import hu.sznn.entity.Robot;
import hu.sznn.entity.Warrior;
import java.lang.reflect.Constructor;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Nati
 */
public class Main {

    public static void main(String[] args) {

        try {

            String robotClassA = args[0];
            String robotClassB = args[1];



            Class classA = Class.forName(robotClassA);

            Robot robotA = (Robot) classA.newInstance();

            Class exampleClassB = Class.forName(robotClassB);
            Robot robotB = (Robot) exampleClassB.newInstance();

            Scanner sc = new Scanner(System.in);
            int height=0;
            int width=0;
            int rounds=0;
            int shield=0;

            do {
                
               
                try{
                    System.out.println("Enter height of the arena (min. 5, max. 20): ");
                height = sc.nextInt();
                
                } catch(InputMismatchException ex){
                    sc.nextLine();
                    System.out.println("Invalid input data");
                }
                
                
            } while (height < 5 || height > 20);

            do {
                System.out.println("Enter width of the arena (min. 5, max. 20): ");
               
                try{
                width = sc.nextInt();
                
                } catch (InputMismatchException ex){
                    sc.nextLine();
                    System.out.println("Invalid input data");
                   
                }
            } while (width < 5 || width > 20);

            do {
                System.out.println("Enter number of rounds (min. 1): ");
                
                try{
                    rounds = sc.nextInt();
                } catch(InputMismatchException ex){
                    sc.nextLine();
                     System.out.println("Invalid input data");
                   
                }
                
            } while (rounds <= 0);

            do {
                System.out.println("Enter life points of robots (min. 1): ");
                
                try{
                shield = sc.nextInt();
                } catch(InputMismatchException ex){
                    sc.nextLine();
                     System.out.println("Invalid input data");
                    
                }
            } while (shield <= 0);

            robotA.setShield(shield);
            robotB.setShield(shield);
            robotA.setShieldMax(shield);
            robotB.setShieldMax(shield);

            robotA.setSign("A");
            robotB.setSign("B");

            robotA.setOpponent(robotB);
            robotB.setOpponent(robotA);

            Arena arena = new Arena(height, width, robotA, robotB);

            robotA.setArena(arena);
            robotB.setArena(arena);

            GameExecutor gameExecutor = new GameExecutor(rounds, arena, robotA, robotB);

            gameExecutor.executeGame();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
