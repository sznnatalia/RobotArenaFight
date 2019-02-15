/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.sznn.action;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Nati
 */
@Getter
@Setter
public class Action {
    
    private int direction;
    private int actionType;

    public Action() {
    }

    public Action(int direction, int action) {
        this.direction = direction;
        this.actionType = action;
    }

    @Override
    public boolean equals(Object obj) {
       if(obj==this){
           return true;
       }
       
       if(!(obj instanceof Action)){
           return false;
       }
       
       Action compareAction = (Action) obj;
       
       if(compareAction.actionType==actionType && compareAction.direction==direction){
           return true;
       }
       
       return false;
        
    }

    @Override
    public String toString() {
        return "Action{" + "direction=" + direction + ", actionType=" + actionType + '}';
    }
    
    

    
}
