package com.ecampix.gui.back.livreur;


import com.ecampix.entities.Livreur;
import com.ecampix.gui.back.MainWindowController;
import com.ecampix.services.LivreurService;
import com.ecampix.utils.AlertUtils;
import com.ecampix.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public TextField nomTF;
    @FXML
    public TextField prenomTF;
    @FXML
    public TextField adresseTF;
    @FXML
    public TextField tarifTF;
    @FXML
    public TextField emailTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Livreur currentLivreur;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentLivreur = ShowAllController.currentLivreur;

        if (currentLivreur != null) {
            topText.setText("Modifier livreur");
            btnAjout.setText("Modifier");

            try {
                nomTF.setText(currentLivreur.getNom());
                prenomTF.setText(currentLivreur.getPrenom());
                adresseTF.setText(currentLivreur.getAdresse());
                tarifTF.setText(String.valueOf(currentLivreur.getTarif()));
                emailTF.setText(currentLivreur.getEmail());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter livreur");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            Livreur livreur = new Livreur(
                    nomTF.getText(),
                    prenomTF.getText(),
                    adresseTF.getText(),
                    Float.parseFloat(tarifTF.getText()),
                    emailTF.getText()
            );

            if (currentLivreur == null) {
                if (LivreurService.getInstance().add(livreur)) {
                    AlertUtils.makeNotification("Succes", "Livreur ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_LIVREUR);
                } else {
                    AlertUtils.makeError("livreur existe deja");
                }
            } else {
                livreur.setId(currentLivreur.getId());
                if (LivreurService.getInstance().edit(livreur)) {
                    AlertUtils.makeNotification("Succes", "Livreur modifié avec succés");
                    ShowAllController.currentLivreur = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_LIVREUR);
                } else {
                    AlertUtils.makeError("livreur existe deja");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (nomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("nom ne doit pas etre vide");
            return false;
        }


        if (prenomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("prenom ne doit pas etre vide");
            return false;
        }


        if (adresseTF.getText().isEmpty()) {
            AlertUtils.makeInformation("adresse ne doit pas etre vide");
            return false;
        }


        if (tarifTF.getText().isEmpty()) {
            AlertUtils.makeInformation("tarif ne doit pas etre vide");
            return false;
        }

        try {
            Float.parseFloat(tarifTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("tarif doit etre un réel");
            return false;
        }
        if (emailTF.getText().isEmpty()) {
            AlertUtils.makeInformation("email ne doit pas etre vide");
            return false;
        }


        return true;
    }
}