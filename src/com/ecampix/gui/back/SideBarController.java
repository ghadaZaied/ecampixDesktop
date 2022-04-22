package com.ecampix.gui.back;

import com.ecampix.MainApp;
import com.ecampix.utils.Animations;
import com.ecampix.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.web("#053F6E");
    private final Color COLOR_DARK = new Color(0.9, 0.9, 0.9, 1);
    private Button[] liens;

    @FXML
    private Button btnLivraisons;
    @FXML
    private Button btnLivreurs;
    @FXML
    private Button btnReclamations;
    @FXML
    private Button btnReponses;
    @FXML
    private AnchorPane mainComponent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnLivraisons,
                btnLivreurs,
                btnReclamations,
                btnReponses,
        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }
        btnLivraisons.setTextFill(Color.WHITE);
        btnLivreurs.setTextFill(Color.WHITE);
        btnReclamations.setTextFill(Color.WHITE);
        btnReponses.setTextFill(Color.WHITE);

    }

    @FXML
    private void afficherLivraisons(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_LIVRAISON);

        btnLivraisons.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnLivraisons, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherLivreurs(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_LIVREUR);

        btnLivreurs.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnLivreurs, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherReclamations(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_RECLAMATION);

        btnReclamations.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnReclamations, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherReponses(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_REPONSE);

        btnReponses.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnReponses, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    private void goToLink(String link) {
        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            Animations.animateButton(lien, COLOR_GRAY, COLOR_DARK, COLOR_PRIMARY, 0, false);
        }
        MainWindowController.getInstance().loadInterface(link);
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        MainApp.getInstance().logout();
    }
}
