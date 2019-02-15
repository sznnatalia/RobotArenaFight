/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.sznn.action;

import hu.sznn.action.Action;

/**
 *
 * @author Droti
 */
public interface RobotAction {
    
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    
    public static final int MOVE = 0;
    public static final int ATTACK = 1;
    public static final int WAIT = 2;
    public static final int DEFENSE = 3;
    
    
    public void move(int direction);
    public void attack(int direction);
    public void waiting();
    public void defense(int direction);
    
    public void chooseAction();
    public void executeAction();
    
    
}
