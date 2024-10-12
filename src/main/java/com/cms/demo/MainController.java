package com.cms.demo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane sideMenu;

    @FXML
    private VBox pagesVBox;

    private boolean fullSideMenu = true;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleTogglingBetweenPages();
    }

    public void toggleSideMenu() {
        Timeline timeline = new Timeline();
        KeyValue widthValue = new KeyValue(sideMenu.prefWidthProperty(), fullSideMenu ? 60.0 : 210.0);
        fullSideMenu = !fullSideMenu;
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), widthValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void handleTogglingBetweenPages() {
        List<Node> labels = pagesVBox.getChildren();
        List<BooleanProperty> labelToggleStates = new ArrayList<>();

        for (Node label : labels) {
            Label page = (Label) label;
            BooleanProperty isToggled = new SimpleBooleanProperty(false);
            labelToggleStates.add(isToggled);

            page.setOnMouseClicked(mouseEvent -> {
                labelToggleStates.forEach(state -> state.set(false));
                isToggled.set(true);
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
}