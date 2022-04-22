package com.ecampix.gui.front.reponse;

import com.ecampix.entities.Reponse;
import com.ecampix.services.ReponseService;
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

    public static Reponse currentReponse;

    @FXML
    public Text topText;

    public VBox mainVBox;


    List<Reponse> listReponse;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listReponse = ReponseService.getInstance().getAll();

        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listReponse);

        if (!listReponse.isEmpty()) {
            for (Reponse reponse : listReponse) {

                mainVBox.getChildren().add(makeReponseModel(reponse));

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeReponseModel(
            Reponse reponse
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_REPONSE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#adminIdText")).setText("Admin : " + reponse.getAdmin());
            ((Text) innerContainer.lookup("#reclamationIdText")).setText("Reclamation : " + reponse.getReclamationId());
            ((Text) innerContainer.lookup("#textText")).setText("Text : " + reponse.getText());


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }


    private void specialAction(Reponse reponse) {

    }
}
