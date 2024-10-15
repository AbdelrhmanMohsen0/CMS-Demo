package com.cms.demo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    @FXML
    private AnchorPane sideMenu;

    @FXML
    private VBox pagesVBox;

    @FXML
    private TextField searchBar;

    @FXML
    private BorderPane mainPane;

    @FXML
    private AnchorPane activePage;

    private boolean fullSideMenu = true;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleTogglingBetweenPages();
        handleSearchBarFocus();
        loadPage("home-page.fxml");
    }

    public void toggleSideMenu() {
        Timeline timeline = new Timeline();
        KeyValue widthValue = new KeyValue(sideMenu.prefWidthProperty(), fullSideMenu ? 60.0 : 210.0);
        fullSideMenu = !fullSideMenu;
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), widthValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void handleTogglingBetweenPages() {
        List<Node> labels = pagesVBox.getChildren();
        List<BooleanProperty> labelToggleStates = new ArrayList<>();

        for (Node label : labels) {
            Label page = (Label) label;
            BooleanProperty isToggled = new SimpleBooleanProperty(false);
            labelToggleStates.add(isToggled);

            page.setOnMouseClicked(mouseEvent -> {
                labelToggleStates.forEach(state -> state.set(false));
                isToggled.set(true);
                loadPage(label.getId() + ".fxml");
            });

            isToggled.addListener((obs, wasToggled, isNowToggled) -> {
                ImageView imageView = (ImageView) page.getGraphic();
                if (isNowToggled) {
                    page.getStyleClass().add("active-side-menu-item");
                    String imageUrl = imageView.getImage().getUrl().replace(".png", "-white.png");
                    imageView.setImage(new Image(imageUrl));
                }
                else {
                    page.getStyleClass().remove("active-side-menu-item");
                    String imageUrl = imageView.getImage().getUrl().replace("-white.png", ".png");
                    imageView.setImage(new Image(imageUrl));
                }
            });
        }
        labelToggleStates.getFirst().set(true);
    }

    private void handleSearchBarFocus() {
        mainPane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getTarget() != searchBar) {
                mainPane.requestFocus();
            }
        });
    }

    private void loadPage(String pageFile) {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(pageFile)));
            activePage.getChildren().removeAll();
            activePage.getChildren().setAll(fxml);
            AnchorPane.setBottomAnchor(fxml, 0.0);
            AnchorPane.setLeftAnchor(fxml, 0.0);
            AnchorPane.setRightAnchor(fxml, 0.0);
            AnchorPane.setTopAnchor(fxml, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}