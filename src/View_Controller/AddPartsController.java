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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Model.InHousePart;
import Model.OutsourcedPart;
import Model.Inventory;
import Model.Part;

/**
 * FXML Controller class
 *
 * @author LisaKilker
 */
public class AddPartsController implements Initializable {

    @FXML
    private TextField txt_addPart_IDFixed;
    @FXML
    private RadioButton rad_addPart_InHouse;
    @FXML
    private RadioButton rad_addPart_Outsourced;
    @FXML
    private Label lbl_addPart_Dynamic;
    @FXML
    private TextField txt_addPart_Name;
    @FXML
    private TextField txt_addPart_Cost;
    @FXML
    private TextField txt_addPart_Inv;
    @FXML
    private TextField txt_addPart_Min;
    @FXML
    private TextField txt_addPart_Max;
    @FXML
    private TextField txt_addPart_Dynamic;
    @FXML
    private Button btn_addPart_Save;
    @FXML
    private Button btn_addPart_Cancel;
    
    private int partID;
    private String errorMessage = "";

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partID = Inventory.partIDCount();
        txt_addPart_IDFixed.setText("Fixed: " + partID);
    }

    @FXML
    private void addPartInHouseBtnHandler(ActionEvent event) {
        if (rad_addPart_InHouse.isSelected()) {
            rad_addPart_Outsourced.setSelected(false);
            lbl_addPart_Dynamic.setText("Machine ID: ");
        }
    }

    @FXML
    private void addPartOutsourcedBtnHandler(ActionEvent event) {
        if (rad_addPart_Outsourced.isSelected()) {
            rad_addPart_InHouse.setSelected(false);
            lbl_addPart_Dynamic.setText("Company Name: ");
        }
    }

    @FXML
    private void addPartSaveHandler(ActionEvent event) throws IOException {
        String partName = txt_addPart_Name.getText();
        String partInv = txt_addPart_Inv.getText();
        String partCost = txt_addPart_Cost.getText();
        String partMin = txt_addPart_Min.getText();
        String partMax = txt_addPart_Max.getText();
        String partDynamic = txt_addPart_Dynamic.getText();

        try {
            errorMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partCost), errorMessage);
            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";

            } else {
                if (rad_addPart_InHouse.isSelected()) {
                    InHousePart newInHousePart = new InHousePart();
                    newInHousePart.setPartID(partID);
                    newInHousePart.setPartName(partName);
                    newInHousePart.setPartCost(Double.parseDouble(partCost));
                    newInHousePart.setPartInv(Integer.parseInt(partInv));
                    newInHousePart.setPartMin(Integer.parseInt(partMin));
                    newInHousePart.setPartMax(Integer.parseInt(partMax));
                    newInHousePart.setMachineID(Integer.parseInt(partDynamic));
                    Inventory.addPart(newInHousePart);

                } else {
                    OutsourcedPart newOutsourcedPart = new OutsourcedPart();
                    newOutsourcedPart.setPartID(partID);
                    newOutsourcedPart.setPartName(partName);
                    newOutsourcedPart.setPartCost(Double.parseDouble(partCost));
                    newOutsourcedPart.setPartInv(Integer.parseInt(partInv));
                    newOutsourcedPart.setPartMin(Integer.parseInt(partMin));
                    newOutsourcedPart.setPartMax(Integer.parseInt(partMax));
                    newOutsourcedPart.setCompanyName(partDynamic);
                    Inventory.addPart(newOutsourcedPart);
                }
                
                Stage stage;
                Parent root;
                stage = (Stage) btn_addPart_Save.getScene().getWindow();
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
    private void addPartCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) btn_addPart_Cancel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
