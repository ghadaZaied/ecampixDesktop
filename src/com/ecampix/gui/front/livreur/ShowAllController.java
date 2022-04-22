package com.ecampix.gui.front.livreur;

import com.ecampix.entities.Livreur;
import com.ecampix.services.LivreurService;
import com.ecampix.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShowAllController implements Initializable {

    public static Livreur currentLivreur;

    @FXML
    public Text topText;

    public VBox mainVBox;

    List<Livreur> listLivreur;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listLivreur = LivreurService.getInstance().getAll();
        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listLivreur);

        if (!listLivreur.isEmpty()) {
            for (Livreur livreur : listLivreur) {

                mainVBox.getChildren().add(makeLivreurModel(livreur));

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeLivreurModel(
            Livreur livreur
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_LIVREUR)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + livreur.getNom());
            ((Text) innerContainer.lookup("#prenomText")).setText("Prenom : " + livreur.getPrenom());
            ((Text) innerContainer.lookup("#adresseText")).setText("Adresse : " + livreur.getAdresse());
            ((Text) innerContainer.lookup("#tarifText")).setText("Tarif : " + livreur.getTarif());
            ((Text) innerContainer.lookup("#emailText")).setText("Email : " + livreur.getEmail());


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }


    private void specialAction(Livreur livreur) {

    }
}
