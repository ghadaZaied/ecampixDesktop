package com.ecampix.gui.back.livraison;

import com.ecampix.entities.Livraison;
import com.ecampix.services.LivraisonService;
import com.ecampix.utils.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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

    public static Livraison currentLivraison;

    @FXML
    public Text topText;

    public VBox mainVBox;


    List<Livraison> listLivraison;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listLivraison = LivraisonService.getInstance().getAll();

        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listLivraison);

        if (!listLivraison.isEmpty()) {
            for (Livraison livraison : listLivraison) {

                mainVBox.getChildren().add(makeLivraisonModel(livraison));

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeLivraisonModel(
            Livraison livraison
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_LIVRAISON)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#livreurIdText")).setText("Livreur : " + livraison.getLivreurId());
            ((Text) innerContainer.lookup("#numSerieText")).setText("NumSerie : " + livraison.getNumSerie());
            ((Text) innerContainer.lookup("#adresseText")).setText("Adresse : " + livraison.getAdresse());
            ((Text) innerContainer.lookup("#dateText")).setText("Date : " + livraison.getDate());


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }


    private void specialAction(Livraison livraison) {

    }
}
