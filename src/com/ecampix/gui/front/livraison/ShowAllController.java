package com.ecampix.gui.front.livraison;

import com.ecampix.entities.Livraison;
import com.ecampix.gui.front.MainWindowController;
import com.ecampix.services.LivraisonService;
import com.ecampix.utils.AlertUtils;
import com.ecampix.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Livraison currentLivraison;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
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
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_LIVRAISON)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#livreurIdText")).setText("Livreur : " + livraison.getLivreurId());
            ((Text) innerContainer.lookup("#numSerieText")).setText("NumSerie : " + livraison.getNumSerie());
            ((Text) innerContainer.lookup("#adresseText")).setText("Adresse : " + livraison.getAdresse());
            ((Text) innerContainer.lookup("#dateText")).setText("Date : " + livraison.getDate());


            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierLivraison(livraison));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerLivraison(livraison));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterLivraison(ActionEvent event) {
        currentLivraison = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_LIVRAISON);
    }

    private void modifierLivraison(Livraison livraison) {
        currentLivraison = livraison;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_LIVRAISON);
    }

    private void supprimerLivraison(Livraison livraison) {
        currentLivraison = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer livraison ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (LivraisonService.getInstance().delete(livraison.getId())) {
                MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_LIVRAISON);
            } else {
                AlertUtils.makeError("Could not delete livraison");
            }
        }
    }


    private void specialAction(Livraison livraison) {

    }
}
