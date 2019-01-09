/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author LisaKilker
 */
public class OutsourcedPart extends Part {
    
    private final StringProperty companyName = new SimpleStringProperty("");
    
    public OutsourcedPart() {
        this(0, "", 0.0, 0, 0, 0, "");
    }
    
    public OutsourcedPart(int partID, String partName, double partCost, int partInv, int partMin, int partMax, String companyName) {
        super(partID, partName, partCost, partInv, partMin, partMax);
        setCompanyName(companyName);
    }
    //Getters
    public String getCompName() {
        return companyName.get();
    }
    //Setters
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    } 
    //Returns
    public StringProperty compNameProperty() {
        return companyName;
    } 
}
//Defines the backend data