/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *
 * @author LisaKilker
 */
public class Product {

    private final IntegerProperty productID = new SimpleIntegerProperty(0);
    private final StringProperty productName = new SimpleStringProperty("");
    private final DoubleProperty productCost = new SimpleDoubleProperty(0.0);
    private final IntegerProperty productInv = new SimpleIntegerProperty(0);
    private final IntegerProperty productMin = new SimpleIntegerProperty(0);
    private final IntegerProperty productMax = new SimpleIntegerProperty(0);
    private ObservableList<Part> productRelatedParts = FXCollections.observableArrayList();

    public Product() {
        this(0, "", 0.0, 0, 0, 0, null);
    }

    public Product(int productID, String productName, double productCost, int productInv, int productMin, int productMax, Part productIntPart) {
        setProductID(productID);
        setProductName(productName);
        setProductCost(productCost);
        setProductInv(productInv);
        setProductMin(productMin);
        setProductMax(productMax);
        productRelatedParts.add(productIntPart);
    }

    //Getters
    public int getProductID() {
        return productID.get();
    }

    public String getProductName() {
        return productName.get();
    }

    public double getProductCost() {
        return productCost.get();
    }

    public int getProductInv() {
        return productInv.get();
    }

    public int getProductMin() {
        return productMin.get();
    }

    public int getProductMax() {
        return productMax.get();
    }

    //Setters
    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public void setProductName(String name) {
        this.productName.set(name);
    }

    public void setProductCost(double cost) {
        this.productCost.set(cost);
    }

    public void setProductInv(int inv) {
        this.productInv.set(inv);
    }

    public void setProductMin(int min) {
        this.productMin.set(min);
    }

    public void setProductMax(int max) {
        this.productMax.set(max);
    }

    public void setProductRelatedParts(ObservableList<Part> relatedParts) {
        this.productRelatedParts = relatedParts;
    }

    //Returns
    public IntegerProperty productIDProperty() {
        return productID;
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public DoubleProperty productCostProperty() {
        return productCost;
    }

    public IntegerProperty productInvProperty() {
        return productInv;
    }

    public IntegerProperty productMinProperty() {
        return productMin;
    }

    public IntegerProperty productMaxProperty() {
        return productMax;
    }

    public ObservableList getProductRelatedParts() {
        return productRelatedParts;
    }

    public void addProductRelatedPart(Part part) {
        productRelatedParts.add(part);
    }

    //Search related Parts
    public int relatedPartSearch(String partSearch) {
        int partIndex;

        if (isInteger(partSearch)) {
            for (int i = 0; i < productRelatedParts.size(); i++) {
                if (Integer.parseInt(partSearch) == productRelatedParts.get(i).getPartID()) {
                    return partIndex = i;
                }
            }
        } else {
            for (int i = 0; i < productRelatedParts.size(); i++) {
                if (partSearch.equals(productRelatedParts.get(i).getPartName())) {
                    return partIndex = i;
                }
            }
        }
        return -1;
    }

    //Remove Related Part
    public void removeRelatedPart(Part part) {
        productRelatedParts.remove(part);
    }

    //Checks if Product fields are filled with correct data
    public static String isProductValid(String name, double cost, int inv, int min, int max, ObservableList<Part> relatedParts, String productErrorMessage) {
        double partsTotalCost = 0.0;

        for (int i = 0; i < relatedParts.size(); i++) {
            partsTotalCost += relatedParts.get(i).getPartCost();
        }

        if (name.length() == 0) {
            productErrorMessage = ("Product name cannot be blank. ");
        } else if (cost < partsTotalCost) {
            productErrorMessage = ("Product cost must be greater than or equal to part cost. ");
        } else if (cost <= 0) {
            productErrorMessage = ("Product cost must be greater than $0. ");
        } else if (inv <= 0) {
            productErrorMessage = ("Product inventory level must be greater than 0. ");
//        } else if (min <= 0) {
//            productErrorMessage = ("Min must be greater than 0.  ");
//        } else if (inv < min) {
//            productErrorMessage = ("Product inventory level must be greater than min. ");
//        } else if (inv > max) {
//            productErrorMessage = ("Product inventory level must be less than max. ");
//        } else if (max < min) {
//            productErrorMessage = ("Max inventory value must be greater than the min. ");
        } else if (relatedParts.size() < 1) {
            productErrorMessage = ("The product must have at least 1 part. ");
        }

        return productErrorMessage;
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
