/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
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
import static View_Controller.MainScreenController.partToModify;
import static Model.Inventory.partInfo;

/**
 * FXML Controller class
 *
 * @author LisaKilker
 */
public class ModifyPartsController implements Initializable {

    @FXML
    private RadioButton rad_modifyPart_InHouse;
    @FXML
    private RadioButton rad_modifyPart_Outsourced;
    @FXML
    private TextField txt_modifyPart_Name;
    @FXML
    private TextField txt_modifyPart_Inv;
    @FXML
    private TextField txt_modifyPart_Cost;
    @FXML
    private TextField txt_modifyPart_Max;
    @FXML
    private TextField txt_modifyPart_Min;
    @FXML
    private Button btn_modifyPart_Save;
    @FXML
    private Button btn_modifyPart_Cancel;
    @FXML
    private Label lbl_modifyPart_Dynamic;
    @FXML
    private TextField txt_modifyPart_Dynamic;
    @FXML
    private TextField txt_modifyPart_IDFixed;
    
    private int partID;
    private final int partIndex = partToModify();
    private String errorMessage = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part part = partInfo().get(partIndex);
        partID = partInfo().get(partIndex).getPartID();
        txt_modifyPart_IDFixed.setText("Part ID: " + partID);
        txt_modifyPart_Name.setText(part.getPartName());
        txt_modifyPart_Inv.setText(Integer.toString(part.getPartInv()));
        txt_modifyPart_Cost.setText(Double.toString(part.getPartCost()));
        txt_modifyPart_Min.setText(Integer.toString(part.getPartMin()));
        txt_modifyPart_Max.setText(Integer.toString(part.getPartMax()));
        
        if (part instanceof InHousePart) {
            txt_modifyPart_Dynamic.setText(Integer.toString(((InHousePart) partInfo().get(partIndex)).getMachineID()));
            lbl_modifyPart_Dynamic.setText("Machine ID");
            rad_modifyPart_InHouse.setSelected(true);

        } else {
            txt_modifyPart_Dynamic.setText(((OutsourcedPart) partInfo().get(partIndex)).getCompName());
            lbl_modifyPart_Dynamic.setText("Company Name");
            rad_modifyPart_Outsourced.setSelected(true);
        }
    } 
    //InHouse Button
    @FXML
    private void modifyPartInHouseBtnHandler(ActionEvent event) {
        if (rad_modifyPart_InHouse.isSelected()) {
            rad_modifyPart_Outsourced.setSelected(false);
            lbl_modifyPart_Dynamic.setText("Machine ID: ");
        }
    }
    //Outsourced Button
    @FXML
    private void modifyPartOutsourcedBtnHandler(ActionEvent event) {
        if (rad_modifyPart_Outsourced.isSelected()) {
            rad_modifyPart_InHouse.setSelected(false);
            lbl_modifyPart_Dynamic.setText("Company Name: ");
        }
    }
    //Save
    @FXML
    private void modifyPartSaveHandler(ActionEvent event) throws IOException {
        String partName = txt_modifyPart_Name.getText();
        String partInv = txt_modifyPart_Inv.getText();
        String partCost = txt_modifyPart_Cost.getText();
        String partMin = txt_modifyPart_Min.getText();
        String partMax = txt_modifyPart_Max.getText();
        String partDynamic = txt_modifyPart_Dynamic.getText();

        try {
            errorMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partCost), errorMessage);
            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";

            } else {
                if (rad_modifyPart_InHouse.isSelected()) {
                    InHousePart newInHousePart = new InHousePart();
                    newInHousePart.setPartID(partID);
                    newInHousePart.setPartName(partName);
                    newInHousePart.setPartCost(Double.parseDouble(partCost));
                    newInHousePart.setPartInv(Integer.parseInt(partInv));
                    newInHousePart.setPartMin(Integer.parseInt(partMin));
                    newInHousePart.setPartMax(Integer.parseInt(partMax));
                    newInHousePart.setMachineID(Integer.parseInt(partDynamic));
                    Inventory.updatePart(partIndex, newInHousePart);

                } else {
                    OutsourcedPart newOutsourcedPart = new OutsourcedPart();
                    newOutsourcedPart.setPartID(partID);
                    newOutsourcedPart.setPartName(partName);
                    newOutsourcedPart.setPartCost(Double.parseDouble(partCost));
                    newOutsourcedPart.setPartInv(Integer.parseInt(partInv));
                    newOutsourcedPart.setPartMin(Integer.parseInt(partMin));
                    newOutsourcedPart.setPartMax(Integer.parseInt(partMax));
                    newOutsourcedPart.setCompanyName(partDynamic);
                    Inventory.updatePart(partIndex, newOutsourcedPart);
                }
                
                Stage stage;
                Parent root;
                stage = (Stage) btn_modifyPart_Save.getScene().getWindow();
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
    //Cancel
    @FXML
    private void modifyPartCancelHandler(ActionEvent event) throws IOException {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) btn_modifyPart_Cancel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
