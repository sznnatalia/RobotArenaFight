/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.sznn.entity;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Nati
 */


public class Position {
    
    @Getter @Setter
    private int x;
    @Getter @Setter
    private int y;

    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+x + ","+y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        
        if(obj==this){
           return true;
       }
       
       if(!(obj instanceof Position)){
           return false;
       }
       
       Position comparePosition = (Position) obj;
       
       if(comparePosition.x==x && comparePosition.y==y){
           return true;
       }
       
       return false;
    }
    
    

    
}
