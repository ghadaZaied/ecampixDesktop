package com.ecampix.gui.front.livraison;


import com.ecampix.entities.Livraison;
import com.ecampix.gui.front.MainWindowController;
import com.ecampix.services.LivraisonService;
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
    public ComboBox<RelationObject> livreurCB;
    @FXML
    public TextField numSerieTF;
    @FXML
    public TextField adresseTF;
    @FXML
    public DatePicker dateDP;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Livraison currentLivraison;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (RelationObject livreur : LivraisonService.getInstance().getAllLivreurs()) {
            livreurCB.getItems().add(livreur);
        }

        currentLivraison = ShowAllController.currentLivraison;

        if (currentLivraison != null) {
            topText.setText("Modifier livraison");
            btnAjout.setText("Modifier");

            try {
                livreurCB.setValue(currentLivraison.getLivreurId());
                numSerieTF.setText(currentLivraison.getNumSerie());
                adresseTF.setText(currentLivraison.getAdresse());
                dateDP.setValue(currentLivraison.getDate());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter livraison");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            Livraison livraison = new Livraison(
                    livreurCB.getValue(),
                    numSerieTF.getText(),
                    adresseTF.getText(),
                    dateDP.getValue()
            );

            if (currentLivraison == null) {
                if (LivraisonService.getInstance().add(livraison)) {
                    AlertUtils.makeNotification("Succes", "Livraison ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_LIVRAISON);
                } else {
                    AlertUtils.makeError("livraison existe deja");
                }
            } else {
                livraison.setId(currentLivraison.getId());
                if (LivraisonService.getInstance().edit(livraison)) {
                    AlertUtils.makeNotification("Succes", "Livraison modifié avec succés");
                    ShowAllController.currentLivraison = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_LIVRAISON);
                } else {
                    AlertUtils.makeError("livraison existe deja");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (livreurCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir livreur");
            return false;
        }


        if (numSerieTF.getText().isEmpty()) {
            AlertUtils.makeInformation("numSerie ne doit pas etre vide");
            return false;
        }


        if (adresseTF.getText().isEmpty()) {
            AlertUtils.makeInformation("adresse ne doit pas etre vide");
            return false;
        }


        if (dateDP.getValue() == null) {
            AlertUtils.makeInformation("Choisir une date pour date");
            return false;
        }


        return true;
    }
}