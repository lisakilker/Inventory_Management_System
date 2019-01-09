/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author LisaKilker
 */
public class InHousePart extends Part {
    
    private final IntegerProperty machineID = new SimpleIntegerProperty(0);
    
    public InHousePart() {
        this(0, "", 0.0, 0, 0, 0, 0);
    }
      
    public InHousePart(int partID, String partName, double partCost, int partInv, int partMin, int partMax, int machineID) {
        super(partID, partName, partCost, partInv, partMin, partMax);
        setMachineID(machineID);
    }
    //Getters
    public int getMachineID() {
        return machineID.get();
    }
    //Setters
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
    //Returns  
    public IntegerProperty machineIDProperty() {
        return machineID;
    }
}
//Defines the backend data
