package com.ecampix.gui.front.reclamation;


import com.ecampix.entities.Reclamation;
import com.ecampix.gui.front.MainWindowController;
import com.ecampix.services.ReclamationService;
import com.ecampix.utils.AlertUtils;
import com.ecampix.utils.Constants;
import com.ecampix.utils.RelationObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public ComboBox<RelationObject> clientCB;
    @FXML
    public TextField titreTF;
    @FXML
    public TextField textTF;
    @FXML
    public DatePicker dateDP;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Reclamation currentReclamation;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (RelationObject client : ReclamationService.getInstance().getAllClients()) {
            clientCB.getItems().add(client);
        }

        currentReclamation = ShowAllController.currentReclamation;

        if (currentReclamation != null) {
            topText.setText("Modifier reclamation");
            btnAjout.setText("Modifier");

            try {
                clientCB.setValue(currentReclamation.getClientId());
                titreTF.setText(currentReclamation.getTitre());
                textTF.setText(currentReclamation.getText());
                dateDP.setValue(currentReclamation.getDate());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter reclamation");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            Reclamation reclamation = new Reclamation(
                    clientCB.getValue(),
                    titreTF.getText(),
                    textTF.getText(),
                    dateDP.getValue()
            );

            if (currentReclamation == null) {
                if (ReclamationService.getInstance().add(reclamation)) {
                    AlertUtils.makeInformation("Reclamation ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_RECLAMATION);
                } else {
                    AlertUtils.makeError("reclamation existe deja");
                }
            } else {
                reclamation.setId(currentReclamation.getId());
                if (ReclamationService.getInstance().edit(reclamation)) {
                    AlertUtils.makeInformation("Reclamation modifié avec succés");
                    ShowAllController.currentReclamation = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_RECLAMATION);
                } else {
                    AlertUtils.makeError("reclamation existe deja");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (clientCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir client");
            return false;
        }


        if (titreTF.getText().isEmpty()) {
            AlertUtils.makeInformation("titre ne doit pas etre vide");
            return false;
        }


        if (textTF.getText().isEmpty()) {
            AlertUtils.makeInformation("text ne doit pas etre vide");
            return false;
        }


        if (dateDP.getValue() == null) {
            AlertUtils.makeInformation("Choisir une date pour date");
            return false;
        }


        return true;
    }
}