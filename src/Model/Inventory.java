/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author LisaKilker
 */
public class Inventory {

    private static int partIDCount;
    private static int productIDCount;
    public static ObservableList<Part> partsList = FXCollections.observableArrayList();
    public static ObservableList<Product> productsList = FXCollections.observableArrayList();

    public Inventory() {
    }

    //Add Part
    public static void addPart(Part part) {
        partsList.add(part);
    }

    public static int partIDCount() {
        partIDCount++;
        return partIDCount;
    }

    public static ObservableList<Part> partInfo() {
        return partsList;
    }

    //Update Part
    public static void updatePart(int partIndex, Part part) {
        partsList.set(partIndex, part);
    }

    //Remove Part
    public void removePart(Part part) {
        partsList.remove(part);
    }

    //Part Search
    public static int partSearch(String partSearch) {
        int partIndex;

        if (isInteger(partSearch)) {
            for (int i = 0; i < partsList.size(); i++) {
                if (Integer.parseInt(partSearch) == partsList.get(i).getPartID()) {
                    return partIndex = i;
                }
            }

        } else {
            for (int i = 0; i < partsList.size(); i++) {
                if (partSearch.equals(partsList.get(i).getPartName())) {
                    return partIndex = i;
                }
            }
        }

        return -1;
    }

    //Add Product
    public static void addProduct(Product product) {
        productsList.add(product);
    }

    public static int productIDCount() {
        productIDCount++;
        return productIDCount;
    }

    public static ObservableList<Product> productInfo() {
        return productsList;
    }

    //Update Product
    public static void updateProduct(int productID, Product product) {
        productsList.set(productID, product);
    }

    //Remove Product
    public void removeProduct(Product product) {
        productsList.remove(product);
    }

    //Product Search
    public int productSearch(String productSearch) {
        int productIndex;

        if (isInteger(productSearch)) {
            for (int i = 0; i < productsList.size(); i++) {
                if (Integer.parseInt(productSearch) == productsList.get(i).getProductID()) {
                    productIndex = i;
                }
            }

        } else {
            for (int i = 0; i < productsList.size(); i++) {
                if (productSearch.equals(productsList.get(i).getProductName())) {
                    productIndex = i;
                }
            }
        }

        return -1;
    }

    public static boolean isInteger(String input) {
        try {

            Integer.parseInt(input);
            return true;

        } catch (Exception e) {

            return false;
        }
    }
}
