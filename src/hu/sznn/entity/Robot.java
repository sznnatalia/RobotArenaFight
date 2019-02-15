/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.sznn.entity;

import hu.sznn.action.RobotAction;
import hu.sznn.action.Action;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Nati
 */
@Getter
@Setter
public abstract class Robot implements RobotAction {

    protected Position position;
    protected Robot opponent;
    protected Action action;
    protected Arena arena;
    protected int shield;
    protected int shieldMax;
    protected String sign;
    protected boolean actionDone = false; //if the robot had an action in the round, turns true and doesn't let a new action until a new round

    public Robot() {
    }

   

    @Override
    public void move(int direction) {
        Position newPosition = new Position();
        switch (direction) {
            case 0:
                newPosition.setX(position.getX() - 1);
                newPosition.setY(position.getY());
                break;
            case 1:
                newPosition.setX(position.getX() + 1);
                newPosition.setY(position.getY());
                break;
            case 2:
                newPosition.setX(position.getX());
                newPosition.setY(position.getY() - 1);
                break;
            case 3:
                newPosition.setX(position.getX());
                newPosition.setY(position.getY() + 1);
                break;
        }

        if (!outOfArena(newPosition)) { //checks if the new position would be out of the arena or opponent is on the place where robot wants to move - if not the robot's position will be the new position
            position = newPosition;
        } 
        actionDone = true;

    }

    @Override
    public void attack(int direction) {
        if (isNext() == direction) {//if opponent is really in the direction of attack
            opponent.shield--;
        }

        actionDone = true;
    }

    @Override
    public void waiting() {
        actionDone = true;
    }

    @Override
    public void defense(int direction) {
        if (isNext() == direction && opponent.action.equals(new Action(directionOpposite(direction), ATTACK))) {//check if the opponent is next to and attacks this robot
            shield++; //if the opponent has attacked it neutralizes the damage   

        }
        actionDone = true;

    }

    @Override
    public void chooseAction() {
        Random random = new Random();

        Action randomAction = new Action();
        int direction;

        if (isNext() == (-1)) { //if opponent is not next robot will move in random direction or wait

            direction = random.nextInt(5);//random number between 0-4
            
            if (direction < 4) {//if number is less then 4, robot will move that direction
                randomAction.setActionType(MOVE);
                randomAction.setDirection(direction);
            } else { //if it is 5 robot will wait
                randomAction.setActionType(WAIT);
                randomAction.setDirection(-1);
            }

        } else {//if opponent is next, it decides whether to move, attack or defende

            int randomActionType = random.nextInt(20);

            int moveWeight;
            int attackWeight;
            int defendeWeight;

            if (opponent.shield - shield > 2) {//every action type has a weight depends on the damage of the shield
                
                moveWeight = 7; //weight is 7
                attackWeight = 12; //weight is 5
                defendeWeight = 20; //weight is 8

            } else {
                moveWeight = 4; //weigth is 4
                attackWeight = 13; //weigth is 9
                defendeWeight = 20; //weight is 7
            }

            if (randomActionType < moveWeight) {//
                direction = random.nextInt(4);//choose a random direction
                randomAction.setActionType(MOVE);
                randomAction.setDirection(direction);
            } else if (randomActionType < attackWeight) {
                randomAction.setActionType(ATTACK);
                randomAction.setDirection(isNext()); //makes an attack to the direction of opponent
            } else if (randomActionType < defendeWeight) {
                randomAction.setActionType(DEFENSE); //makes a defense to the direction of opponent
                randomAction.setDirection(isNext());
            }
        }

        action = randomAction;
    }

    @Override
    public void executeAction() {
        if (!actionDone) {
            switch (action.getActionType()) {
                case MOVE:
                    move(action.getDirection());
                    break;
                case ATTACK:
                    attack(action.getDirection());
                    break;
                case DEFENSE:
                    defense(action.getDirection());
                    break;
                case WAIT:
                    waiting();
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " Shield: " + shield + "/" + shieldMax;
                
    }

    public int isNext() {//returns the direction, where the opponent is
        if (opponent.position.getX() == position.getX()) {//the opponent is on the left or right side
            if (opponent.position.getY() - position.getY() == 1) {
                return RIGHT; //opponent is on the right
            } else if (position.getY() - opponent.position.getY() == 1) {
                return LEFT; //opponent is on the left
            }
        } else if (opponent.position.getY() == position.getY()) {
            if (opponent.position.getX() - position.getX() == 1) {
                return DOWN;//opponent is below
            } else if (position.getX() - opponent.position.getX() == 1) {
                return UP;//opponent is up
            }

        }

        return -1;//if opponent is not next returns -1
    }

    public static int directionOpposite(int direction) {//returns the opposite of the parameter direction
        switch (direction) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return -1;

        }

    }

    private boolean outOfArena(Position position) {
        
        if (position.getX() < 0 || position.getY() < 0) {
            return true;
        } else if (position.getX() >= arena.getTable().length || position.getY() >= arena.getTable()[0].length) {
            return true;
        }

        return false;
    }
    
   

}
