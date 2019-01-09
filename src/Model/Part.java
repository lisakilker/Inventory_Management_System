/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author LisaKilker
 */
public abstract class Part {

    private final IntegerProperty partID = new SimpleIntegerProperty(0);
    private final StringProperty partName = new SimpleStringProperty("");
    private final DoubleProperty partCost = new SimpleDoubleProperty(0.0);
    private final IntegerProperty partInv = new SimpleIntegerProperty(0);
    private final IntegerProperty partMin = new SimpleIntegerProperty(0);
    private final IntegerProperty partMax = new SimpleIntegerProperty(0);

    public Part() {
        this(0, "", 0.0, 0, 0, 0);
    }

    public Part(int partID, String name, double cost, int inv, int min, int max) {
        setPartID(partID);
        setPartName(name);
        setPartCost(cost);
        setPartInv(inv);
        setPartMin(min);
        setPartMax(max);
    }
    //Getters
    public int getPartID() {
        return partID.get();
    }

    public String getPartName() {
        return partName.get();
    }

    public double getPartCost() {
        return partCost.get();
    }

    public int getPartInv() {
        return partInv.get();
    }

    public int getPartMin() {
        return partMin.get();
    }

    public int getPartMax() {
        return partMax.get();
    }
    //Setters
    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public void setPartName(String name) {
        this.partName.set(name);
    }

    public void setPartCost(double cost) {
        this.partCost.set(cost);
    }

    public void setPartInv(int inv) {
        this.partInv.set(inv);
    }

    public void setPartMin(int min) {
        this.partMin.set(min);
    }

    public void setPartMax(int max) {
        this.partMax.set(max);
    }
    //Returns
    public IntegerProperty partIDProperty() {
        return partID;
    }

    public StringProperty partNameProperty() {
        return partName;
    }

    public DoubleProperty partCostProperty() {
        return partCost;
    }

    public IntegerProperty partInvProperty() {
        return partInv;
    }

    public IntegerProperty partMinProperty() {
        return partMin;
    }

    public IntegerProperty partMaxProperty() {
        return partMax;
    }
    //Checks if Part fields are filled with correct data
    public static String isPartValid(String name, int min, int max, int inv, double cost, String partErrorMessage) {

        if (name.length() == 0) {
            partErrorMessage = ("Part name cannot be empty. ");
        } else if (cost <= 0) {
            partErrorMessage = ("Part cost must be greater than 0 ");
        } else if (inv <= 0) {
            partErrorMessage = ("Part inventory level must be greater than 0. ");
        } else if (min <= 0) {
            partErrorMessage = ("Min must be greater than 0.  ");
//        } else if (inv < min) {
//            partErrorMessage = ("Part inventory level must be greater than min. ");
//        } else if (inv > max) {
//            partErrorMessage = ("Part inventory level must be less than max. ");
//        } else if (max < min) {
//            partErrorMessage = ("Max inventory value must be greater than the min. ");
        }

        return partErrorMessage;
    }
    
     public boolean isInteger(String input) {
        try {

            Integer.parseInt(input);
            return true;

        } catch (Exception e) {

            return false;
        }
    }
}
