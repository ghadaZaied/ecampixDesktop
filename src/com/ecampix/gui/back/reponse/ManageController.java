package com.ecampix.gui.back.reponse;


import com.ecampix.entities.Reponse;
import com.ecampix.gui.back.MainWindowController;
import com.ecampix.services.ReponseService;
import com.ecampix.utils.AlertUtils;
import com.ecampix.utils.Constants;
import com.ecampix.utils.RelationObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public ComboBox<RelationObject> adminCB;
    @FXML
    public ComboBox<RelationObject> reclamationCB;
    @FXML
    public TextField textTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;
    @FXML
    public TextField emailTF;

    Reponse currentReponse;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (RelationObject reclamation : ReponseService.getInstance().getAllReclamations()) {
            reclamationCB.getItems().add(reclamation);
        }

        for (RelationObject admin : ReponseService.getInstance().getAllAdmins()) {
            adminCB.getItems().add(admin);
        }

        currentReponse = ShowAllController.currentReponse;

        if (currentReponse != null) {
            topText.setText("Modifier reponse");
            btnAjout.setText("Modifier");

            try {
                adminCB.setValue(currentReponse.getAdmin());
                reclamationCB.setValue(currentReponse.getReclamationId());
                textTF.setText(currentReponse.getText());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter reponse");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) throws Exception {

        if (controleDeSaisie()) {

            Reponse reponse = new Reponse(
                    adminCB.getValue(),
                    reclamationCB.getValue(),
                    textTF.getText()
            );

            if (currentReponse == null) {
                if (ReponseService.getInstance().add(reponse)) {
                    AlertUtils.makeNotification("Succes", "Reponse ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_REPONSE);
                } else {
                    AlertUtils.makeError("reponse existe deja");
                }
            } else {
                reponse.setId(currentReponse.getId());
                if (ReponseService.getInstance().edit(reponse)) {
                    AlertUtils.makeNotification("Succes", "Reponse modifié avec succés");
                    ShowAllController.currentReponse = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_REPONSE);
                } else {
                    AlertUtils.makeError("reponse existe deja");
                }
            }
        }
    }


    private boolean controleDeSaisie() {


        if (adminCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir admin");
            return false;
        }


        if (reclamationCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir reclamation");
            return false;
        }


        if (textTF.getText().isEmpty()) {
            AlertUtils.makeInformation("text ne doit pas etre vide");
            return false;
        }


        return true;
    }
}