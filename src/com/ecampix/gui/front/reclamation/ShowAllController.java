package com.ecampix.gui.front.reclamation;

import com.ecampix.entities.Reclamation;
import com.ecampix.gui.front.MainWindowController;
import com.ecampix.services.ReclamationService;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Reclamation currentReclamation;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;

    List<Reclamation> listReclamation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listReclamation = ReclamationService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listReclamation);

        if (!listReclamation.isEmpty()) {
            for (Reclamation reclamation : listReclamation) {
                if (reclamation.getTitre().toLowerCase().startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeReclamationModel(reclamation));
                }

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeReclamationModel(
            Reclamation reclamation
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_RECLAMATION)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#clientIdText")).setText("Client : " + reclamation.getClientId());
            ((Text) innerContainer.lookup("#titreText")).setText("Titre : " + reclamation.getTitre());
            ((Text) innerContainer.lookup("#textText")).setText("Text : " + reclamation.getText());
            ((Text) innerContainer.lookup("#dateText")).setText("Date : " + reclamation.getDate());


            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierReclamation(reclamation));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerReclamation(reclamation));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterReclamation(ActionEvent event) {
        currentReclamation = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_RECLAMATION);
    }

    private void modifierReclamation(Reclamation reclamation) {
        currentReclamation = reclamation;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_RECLAMATION);
    }

    private void supprimerReclamation(Reclamation reclamation) {
        currentReclamation = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer reclamation ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (ReclamationService.getInstance().delete(reclamation.getId())) {
                MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_RECLAMATION);
            } else {
                AlertUtils.makeError("Could not delete reclamation");
            }
        }
    }



    private void specialAction(Reclamation reclamation) {

    }
}
