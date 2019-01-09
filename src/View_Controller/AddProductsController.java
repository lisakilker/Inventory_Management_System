/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author LisaKilker
 */
public class AddProductsController implements Initializable {

    @FXML
    private TextField txt_addProduct_Search;
    @FXML
    private TextField txt_addProduct_IDFixed;
    @FXML
    private TextField txt_addProduct_Name;
    @FXML
    private TextField txt_addProduct_Inv;
    @FXML
    private TextField txt_addProduct_Cost;
    @FXML
    private TextField txt_addProduct_Min;
    @FXML
    private TextField txt_addProduct_Max;
    @FXML
    private Button btn_addProduct_Save;
    @FXML
    private Button btn_addProduct_Cancel;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> tablePartsColumnID;
    @FXML
    private TableColumn<Part, String> tablePartsColumnName;
    @FXML
    private TableColumn<Part, Integer> tablePartsColumnInvLevel;
    @FXML
    private TableColumn<Part, Double> tablePartsColumnCost;
    @FXML
    private TableView<Part> relatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> tableRelatedPartsColumnID;
    @FXML
    private TableColumn<Part, String> tableRelatedPartsColumnName;
    @FXML
    private TableColumn<Part, Integer> tableRelatedPartsColumnInvLevel;
    @FXML
    private TableColumn<Part, Double> tableRelatedPartsColumnCost;

    private final ObservableList<Part> productParts = FXCollections.observableArrayList();
    private int productID;
    private String errorMessage = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productID = Inventory.productIDCount();
        txt_addProduct_IDFixed.setText("Fixed: " + productID);

        tablePartsColumnID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        tablePartsColumnName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        tablePartsColumnInvLevel.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        tablePartsColumnCost.setCellValueFactory(cellData -> cellData.getValue().partCostProperty().asObject());
        partsTable.setItems(Inventory.partsList);
        tableRelatedPartsColumnID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        tableRelatedPartsColumnName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        tableRelatedPartsColumnInvLevel.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        tableRelatedPartsColumnCost.setCellValueFactory(cellData -> cellData.getValue().partCostProperty().asObject());
    }

    @FXML
    private void addProductSearchHandler(ActionEvent event) {
        ObservableList<Part> searchedPartList = FXCollections.observableArrayList();
        String searchParts = txt_addProduct_Search.getText().toLowerCase();

        try {
            int partInvNumber = Integer.parseInt(searchParts);
            for (Part part : Inventory.partsList) {
                if (part.getPartID() == partInvNumber) {
                    searchedPartList.add(part);
                    partsTable.setItems(searchedPartList);
                }
            }

        } catch (NumberFormatException e) {
            for (Part part : Inventory.partsList) {
                if (part.getPartName().toLowerCase().equals(searchParts)) {
                    searchedPartList.add(part);
                    partsTable.setItems(searchedPartList);
                } else if (searchParts.equals("")) {
                    txt_addProduct_Search.setText("");
                    partsTable.setItems(Inventory.partInfo());
                    return;
                }
            }
        }

        if (searchedPartList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OOPS!");
            alert.setHeaderText(null);
            alert.setContentText("Make sure to enter the exact product ID or name.");
            alert.showAndWait();
        }
    }

    @FXML
    private void addProductAddHandler(ActionEvent event) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        productParts.add(selectedPart);
        relatedPartsTable.setItems(productParts);
    }

    @FXML
    private void addProductDeleteHandler(ActionEvent event) {
        Part selectedPart = relatedPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(null);
            alert.setContentText("Please confirm deletion of: " + selectedPart.getPartName());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                relatedPartsTable.getItems().remove(selectedPart);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OOPS!");
            alert.setHeaderText(null);
            alert.setContentText("You have not selected a part to delete!");
            alert.showAndWait();
        }
    }

    @FXML
    private void addProductSaveHandler(ActionEvent event) throws IOException {
        String productName = txt_addProduct_Name.getText();
        String productInv = txt_addProduct_Inv.getText();
        String productCost = txt_addProduct_Cost.getText();
        String productMin = txt_addProduct_Min.getText();
        String productMax = txt_addProduct_Max.getText();

        try {
            errorMessage = Product.isProductValid(productName, Double.parseDouble(productCost), Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv), productParts, errorMessage);
            if (errorMessage.length() != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";

            } else {
                Product newProduct = new Product();

                newProduct.setProductID(productID);
                newProduct.setProductName(productName);
                newProduct.setProductCost(Double.parseDouble(productCost));
                newProduct.setProductInv(Integer.parseInt(productInv));
                newProduct.setProductMin(Integer.parseInt(productMin));
                newProduct.setProductMax(Integer.parseInt(productMax));
                newProduct.setProductRelatedParts(productParts);
                Inventory.addProduct(newProduct);

                Stage stage;
                Parent root;
                stage = (Stage) btn_addProduct_Save.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText(null);
            alert.setContentText("Please complete the form!");
            alert.showAndWait();
        }
    }

    @FXML
    private void addProductCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) btn_addProduct_Cancel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
