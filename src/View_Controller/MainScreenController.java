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
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import Model.Product;
import Model.OutsourcedPart;
import Model.InHousePart;
import Model.Inventory;
import Model.Part;
import static Model.Inventory.partInfo;
import static Model.Inventory.productInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author LisaKilker
 */
public class MainScreenController implements Initializable {

    @FXML
    private Button btn_ms_PartsAdd;
    @FXML
    private Button btn_ms_PartsModify;
    @FXML
    private Button btn_ms_ProductsAdd;
    @FXML
    private Button btn_ms_ProductsModify;
    @FXML
    private TextField ms_Search_Parts;
    @FXML
    private TextField ms_Search_Products;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Part, Integer> tablePartsColumnID;
    @FXML
    private TableColumn<Part, String> tablePartsColumnName;
    @FXML
    private TableColumn<Part, Integer> tablePartsColumnInvLevel;
    @FXML
    private TableColumn<Part, Double> tablePartsColumnCost;
    @FXML
    private TableColumn<Product, Integer> tableProductsColumnID;
    @FXML
    private TableColumn<Product, String> tableProductsColumnName;
    @FXML
    private TableColumn<Product, Integer> tableProductsColumnInvLevel;
    @FXML
    private TableColumn<Product, Double> tableProductsColumnCost;

    private static Part modifyPart;
    private static int modifyPartIndex;
    private static Product modifyProduct;
    private static int modifyProductIndex;
    static boolean exists;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!exists) {
            Inventory.partsList.add(new InHousePart(10, "10-20mm lens", 250.00, 11, 1, 100, 4100));
            Inventory.productsList.add(new Product(10, "Sony A7", 900.00, 12, 1, 100, Inventory.partsList.get(0)));
            Inventory.partsList.add(new InHousePart(11, "Zoom lens", 350.00, 15, 1, 100, 3000));
            Inventory.productsList.add(new Product(11, "Sony A7N", 1900.00, 35, 1, 100, Inventory.partsList.get(1)));
            Inventory.partsList.add(new InHousePart(12, "50mm lens", 325.00, 22, 1, 100, 4000));
            Inventory.productsList.add(new Product(12, "Sony A7R", 1800.00, 18, 1, 100, Inventory.partsList.get(2)));
            Inventory.partsList.add(new InHousePart(13, "Wide angle lens", 475.00, 28, 1, 100, 5100));
            Inventory.productsList.add(new Product(13, "Sony A7A", 2200.00, 10, 1, 20, Inventory.partsList.get(3)));
            Inventory.partsList.add(new InHousePart(14, "Telephoto lens", 600.00, 19, 1, 100, 6100));
            Inventory.productsList.add(new Product(14, "Sony A7X", 2400.00, 15, 1, 20, Inventory.partsList.get(4)));
            exists = true;
        }

        tablePartsColumnID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        tablePartsColumnName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        tablePartsColumnInvLevel.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        tablePartsColumnCost.setCellValueFactory(cellData -> cellData.getValue().partCostProperty().asObject());
        partsTable.setItems(Inventory.partsList);
        tableProductsColumnID.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        tableProductsColumnName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        tableProductsColumnInvLevel.setCellValueFactory(cellData -> cellData.getValue().productInvProperty().asObject());
        tableProductsColumnCost.setCellValueFactory(cellData -> cellData.getValue().productCostProperty().asObject());
        productsTable.setItems(Inventory.productsList);
    }

    @FXML
    private void mainScreenPartsSearchHandler(ActionEvent event) {
        ObservableList<Part> searchedPartList = FXCollections.observableArrayList();
        String searchParts = ms_Search_Parts.getText().toLowerCase();

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
                    ms_Search_Parts.setText("");
                    partsTable.setItems(Inventory.partInfo());
                    return;
                }
            }
        }

        if (searchedPartList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OOPS!");
            alert.setHeaderText(null);
            alert.setContentText("Make sure to enter the exact part ID or name.");
            alert.showAndWait();
        }
    }

    @FXML
    private void mainScreenAddPartHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btn_ms_PartsAdd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddParts.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static int partToModify() {
        return modifyPartIndex;
    }

    @FXML
    private void mainScreenModifyPartHandler(ActionEvent event) throws IOException {
        modifyPart = partsTable.getSelectionModel().getSelectedItem();
        if (modifyPart != null) {
            modifyPartIndex = partInfo().indexOf(modifyPart);
            Stage stage;
            Parent root;
            if (event.getSource() == btn_ms_PartsModify) {
                stage = (Stage) btn_ms_PartsModify.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyParts.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        if (modifyPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText(null);
            alert.setContentText("Please choose a part to Modify!");
            alert.showAndWait();
        }
    }

    @FXML
    private void mainScreenDeletePartHandler(ActionEvent event) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(null);
            alert.setContentText("Please confirm deletion of: " + selectedPart.getPartName());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                partsTable.getItems().remove(selectedPart);
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
    private void mainScreenProductsSearchHandler(ActionEvent event) {
        ObservableList<Product> searchedProductList = FXCollections.observableArrayList();
        String searchProducts = ms_Search_Products.getText().toLowerCase();

        try {
            int productInvNumber = Integer.parseInt(searchProducts);
            for (Product product : Inventory.productsList) {
                if (product.getProductID() == productInvNumber) {
                    searchedProductList.add(product);
                    productsTable.setItems(searchedProductList);
                }
            }

        } catch (NumberFormatException e) {
            for (Product product : Inventory.productsList) {
                if (product.getProductName().toLowerCase().equals(searchProducts)) {
                    searchedProductList.add(product);
                    productsTable.setItems(searchedProductList);
                } else if (searchProducts.equals("")) {
                    ms_Search_Products.setText("");
                    productsTable.setItems(Inventory.productInfo());
                    return;
                }
            }
        }

        if (searchedProductList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OOPS!");
            alert.setHeaderText(null);
            alert.setContentText("Make sure to enter the exact product ID or name.");
            alert.showAndWait();
        }
    }

    @FXML
    private void mainScreenAddProductHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btn_ms_ProductsAdd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProducts.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static int productToModify() {
        return modifyProductIndex;
    }

    @FXML
    private void mainScreenModifyProductHandler(ActionEvent event) throws IOException {
        modifyProduct = productsTable.getSelectionModel().getSelectedItem();
        if (modifyProduct != null) {
            modifyProductIndex = productInfo().indexOf(modifyProduct);
            Stage stage;
            Parent root;
            if (event.getSource() == btn_ms_ProductsModify) {
                stage = (Stage) btn_ms_ProductsModify.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProducts.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        if (modifyProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops!");
            alert.setHeaderText(null);
            alert.setContentText("Please choose a product to Modify!");
            alert.showAndWait();
        }
    }

@FXML
        private void mainScreenDeleteProductHandler(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(null);
            alert.setContentText("Please confirm deletion of: " + selectedProduct.getProductName());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                productsTable.getItems().remove(selectedProduct);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OOPS!");
            alert.setHeaderText(null);
            alert.setContentText("You have not selected a product to delete!");
            alert.showAndWait();
        }
    }

    @FXML
        private void mainScreenExitHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
