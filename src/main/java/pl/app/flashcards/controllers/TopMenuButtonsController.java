package pl.app.flashcards.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import pl.app.flashcards.utils.FxmlUtils;

import java.util.ResourceBundle;

public class TopMenuButtonsController {
    ResourceBundle bundle = FxmlUtils.getResourceBundle();

    public static final String LEARNING_FXML = "/fxml/LearningView.fxml";
    public static final String DATABASE_FXML = "/fxml/DataBaseView.fxml";
    public static final String TOPLIST_FXML = "/fxml/TopListView.fxml";
    public ToggleButton learningButton, dataBaseButton, topListButton;


    private MainController mainController;

    private BorderPane borderPane;

    @FXML
    private ToggleGroup toggleButtons;


    @FXML
    public void openLearning() {

       mainController.setCentre(LEARNING_FXML);
    }

    @FXML
    public void openDataBase() {

        mainController.setCentre(DATABASE_FXML);
    }

    @FXML
    public void openTopList() {
        mainController.setCentre(TOPLIST_FXML);

   }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public ToggleButton getLearningButton() {
        return learningButton;
    }

    public void setLearningButton(ToggleButton learningButton) {
        this.learningButton = learningButton;
    }

    public ToggleButton getDataBaseButton() {
        return dataBaseButton;
    }

    public void setDataBaseButton(ToggleButton dataBaseButton) {
        this.dataBaseButton = dataBaseButton;
    }

    public ToggleButton getTopListButton() {
        return topListButton;
    }

    public void setTopListButton(ToggleButton topListButton) {
        this.topListButton = topListButton;
    }

}