/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.sznn.action;

import hu.sznn.entity.Arena;
import hu.sznn.entity.Position;
import hu.sznn.entity.Robot;

/**
 *
 * @author Nati
 */
public class GameExecutor {

    private int rounds;
    private Arena arena;
    private Robot robotA;
    private Robot robotB;
    private String winner;

    public GameExecutor() {
    }

    public GameExecutor(int rounds, Arena arena, Robot robotA, Robot robotB) {
        this.rounds = rounds;
        this.arena = arena;
        this.robotA = robotA;
        this.robotB = robotB;
    }

    public void executeGame() {
        setStartingPositions();
        drawRound("Starting position");

        int roundCounter = 1;
        boolean isWinner = false;

        while (roundCounter <= rounds && !isWinner) {
            setActionDone(); //it won't let a new action until a new round starts

            chooseActions(); //invoke robot method chooseAction for robotA and robotB

            compareActions(); //compare the choosen actions and decides whether they can be executed or not, and the order of execution

            drawRound("Round: " + roundCounter);

            isWinner = endWithWinner();

            roundCounter++;

        }

        if (!isWinner) {//
            chooseFinalWinner();
        }

        endGame();
    }

    private void drawRound(String round) {

        arena.arenaInitialize();
        System.out.println(round);
        arena.arenaDraw();
        System.out.println();
        System.out.println();
        System.out.println("Robot A: " + robotA);
        System.out.println();
        System.out.println("Robot B: " + robotB);
        System.out.println();
        System.out.println();

    }

    private void setStartingPositions() {

        int xRobotA = 1; //place robotA in the second row
        int yRobotA = arena.getWidth() / 2; //and in the middle horizontally
        int xRobotB = arena.getHeight() - 2; //place robotB in the second last row
        int yRobotB = arena.getWidth() / 2; //and in the middle horizontally

        robotA.setPosition(new Position(xRobotA, yRobotA));
        robotB.setPosition(new Position(xRobotB, yRobotB));

    }

    private boolean endWithWinner() {
        if (robotA.getShield() == 0 && robotB.getShield() != 0) {
            winner = "The winner is Robot B";
            return true;
        } else if (robotB.getShield() == 0 && robotA.getShield() != 0) {
            winner = "The winner is Robot A";
            return true;
        } else if (robotB.getShield() == 0 && robotA.getShield() == 0) {
            winner = "No winner";
            return true;
        }

        return false;
    }

    private void endGame() {
        System.out.println("End of game");
        System.out.println(winner);

    }

    private void chooseActions() {
        robotA.chooseAction();
        robotB.chooseAction();
    }

    private void compareActions() {

        Action actionRobotA = robotA.getAction();
        Action actionRobotB = robotB.getAction();

        if (actionRobotA.getActionType() == Robot.MOVE && actionRobotB.getActionType() == Robot.MOVE) {//if both robots move examines if they step to the same place
            if (!compareMoves()) {
                robotA.executeAction();
                robotB.executeAction();

            } else {//if the robots would step to the same place, actions won't be executed but they can't have a new action in the same round
                robotA.setActionDone(true);
                robotB.setActionDone(true);
            }
        } else if (actionRobotA.getActionType() == Robot.MOVE && actionRobotB.getActionType() != Robot.MOVE) {//checks if robot A moves ant the other stays not to move the same place
            if (!checkSamePlace(robotA, robotB)) {
                robotA.executeAction();
                robotB.executeAction();
            } else{
                robotB.executeAction();
                robotA.setActionDone(true);
            }

        } else if (actionRobotA.getActionType() != Robot.MOVE && actionRobotB.getActionType() == Robot.MOVE) {//if one of the robots moves and the other atacks, order should be move then attack
            if (!checkSamePlace(robotB, robotA)) {
                robotB.executeAction();
                robotA.executeAction();
            } else{
                robotA.executeAction();
                robotB.setActionDone(true);
            }

        } else {//if none of them move order is not important
            robotA.executeAction();
            robotB.executeAction();
        }
    }

    private boolean compareMoves() {

        Position tempPositionRobotA = new Position(robotA.getPosition().getX(), robotA.getPosition().getY()); //temporary variables for modeling the move
        Position tempPositionRobotB = new Position(robotB.getPosition().getX(), robotB.getPosition().getY());

        int moveDirectionRobotA = robotA.getAction().getDirection();
        int moveDirectionRobotB = robotB.getAction().getDirection();

        tempPositionRobotA = newPositionAfterMove(tempPositionRobotA.getX(), tempPositionRobotA.getY(), moveDirectionRobotA); //here modeling the move with this method
        tempPositionRobotB = newPositionAfterMove(tempPositionRobotB.getX(), tempPositionRobotB.getY(), moveDirectionRobotB);

        
        return tempPositionRobotA.equals(tempPositionRobotB); //returns true, if robots would step the same place else return false

    }

    private Position newPositionAfterMove(int x, int y, int direction) {
       

        switch (direction) {
            case 0:
                x--; //up
                break;
            case 1:
                x++; //down
                break;
            case 2:
                y--; //left
                break;
            case 3:
                y++; //right
                break;
        }

        return new Position(x, y);
    }

    private void chooseFinalWinner() {
        if (robotA.getShield() > robotB.getShield()) {
            winner = "The winner is Robot A";
        } else if (robotA.getShield() < robotB.getShield()) {
            winner = "The winner is Robot B";
        } else {
            winner = "Points eqaul, no winner";
        }
    }

    private void setActionDone() {
        robotA.setActionDone(false);
        robotB.setActionDone(false);
    }

    private boolean checkSamePlace(Robot robotMove, Robot robotNotMove) {
        Position positionMove = new Position(robotMove.getPosition().getX(), robotMove.getPosition().getY()) ;
        Position positionNotMove = new Position(robotNotMove.getPosition().getX(), robotNotMove.getPosition().getY());

        int direction = robotMove.getAction().getDirection();

        switch (direction) {//modeling the move of the robot
            case 0:
                positionMove.setX(positionMove.getX() - 1);
                positionMove.setY(positionMove.getY());
                break;
            case 1:
                positionMove.setX(positionMove.getX() + 1);
                positionMove.setY(positionMove.getY());
                break;
            case 2:
                positionMove.setX(positionMove.getX());
                positionMove.setY(positionMove.getY() - 1);
                break;
            case 3:
                positionMove.setX(positionMove.getX());
                positionMove.setY(positionMove.getY() + 1);
                break;
        }
        
        return positionMove.equals(positionNotMove);//returns true if the positions are equal
    }

}
