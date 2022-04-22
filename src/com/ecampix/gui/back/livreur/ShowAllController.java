package com.ecampix.gui.back.livreur;

import com.ecampix.entities.Livreur;
import com.ecampix.gui.back.MainWindowController;
import com.ecampix.services.LivreurService;
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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Livreur currentLivreur;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
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
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_LIVREUR)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + livreur.getNom());
            ((Text) innerContainer.lookup("#prenomText")).setText("Prenom : " + livreur.getPrenom());
            ((Text) innerContainer.lookup("#adresseText")).setText("Adresse : " + livreur.getAdresse());
            ((Text) innerContainer.lookup("#tarifText")).setText("Tarif : " + livreur.getTarif());
            ((Text) innerContainer.lookup("#emailText")).setText("Email : " + livreur.getEmail());


            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierLivreur(livreur));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerLivreur(livreur));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterLivreur(ActionEvent event) {
        currentLivreur = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_LIVREUR);
    }

    private void modifierLivreur(Livreur livreur) {
        currentLivreur = livreur;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_LIVREUR);
    }

    private void supprimerLivreur(Livreur livreur) {
        currentLivreur = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer livreur ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (LivreurService.getInstance().delete(livreur.getId())) {
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_LIVREUR);
            } else {
                AlertUtils.makeError("Could not delete livreur");
            }
        }
    }



    private void specialAction(Livreur livreur) {

    }
}
