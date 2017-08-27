package pl.app.flashcards.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import pl.app.flashcards.database.models.Learning;
import pl.app.flashcards.modelFx.CategoryFx;
import pl.app.flashcards.modelFx.LearningModel;
import pl.app.flashcards.modelFx.TopListModel;
import pl.app.flashcards.utils.DialogsUtil;
import pl.app.flashcards.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.Optional;

public class LearningController {

    @FXML
    private Button stopLearningButton, answerAButton, answerBButton, answerCButton;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;

    @FXML
    private ToggleButton startToggleButton, loadAllButton;

    @FXML
    private Label theBestScoreLabel, lifeLabel, scoreLabel, wordLabel;

    private BooleanProperty play = new SimpleBooleanProperty();
    private Learning learning;
    private LearningModel learningModel;
    private TopListModel topListModel;
    private String givenAnswer = "";

    private String heart = "\u2764";


    public void initialize() {
        this.learningModel = new LearningModel();

        try {
            this.learningModel.init();
        } catch (ApplicationException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }
        this.topListModel = new TopListModel();
        try {
            this.topListModel.init();
        } catch (ApplicationException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }

        bindings();

    }

    private void bindings() {
        this.lifeLabel.setFont(new Font(24));

        this.categoryComboBox.setItems(this.learningModel.getCategoryFxObservableList());
        this.learningModel.categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());

        this.loadAllButton.disableProperty().bind(this.play);
        this.categoryComboBox.disableProperty().bind(this.play.or(loadAllButton.selectedProperty()));


        this.startToggleButton.disableProperty().bind(this.loadAllButton.selectedProperty().not().and(this.categoryComboBox
                .valueProperty().isNull()).or(this.play));

        this.stopLearningButton.disableProperty().bind(this.play.not());

        this.answerAButton.disableProperty().bind(this.wordLabel.textProperty().isEmpty());
        this.answerBButton.disableProperty().bind(this.wordLabel.textProperty().isEmpty());
        this.answerCButton.disableProperty().bind(this.wordLabel.textProperty().isEmpty());
    }


    @FXML
    void answerAAction() {
        givenAnswer = "A";
        goodOrWrongAnswer();

    }

    @FXML
    void answerBAction() {
        givenAnswer = "B";
        goodOrWrongAnswer();

    }

    @FXML
    void answerCAction() {
        givenAnswer = "C";
        goodOrWrongAnswer();

    }

    private void goodOrWrongAnswer() {
        if (this.learning.getGoodAnswer().equals(givenAnswer))
            learning = learningModel.goodAnswer();

        else if (!this.learning.getGoodAnswer().equals(givenAnswer))

            learning = learningModel.wrongAnswer();

        loadLabels(learning);
    }

    @FXML
    void categoryComboBoxAction() {
        try {
            this.learningModel.filterWordsList();
        } catch (ApplicationException e) {
            DialogsUtil
                    .errorDialog(e.getMessage());
        } catch (SQLException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }

    }


    @FXML
    public void startAction() {
        if (learningModel.getWordFxObservableListAll().size() != 0) {
            setPlay(true);


            this.learningModel.setLifes(3);
            this.learningModel.setScore(0);


            if (loadAllButton.isSelected()) {
                learning = learningModel.randomWordFromAll();
                loadLabels(learning);

            } else if (!loadAllButton.isSelected())
                if (learningModel.getWordFxObservableListChoiceCategory().size() != 0) {
                    learning = learningModel.randomWordFromCategory();
                    loadLabels(learning);
                } else {
                    setPlay(false);
                    this.learningModel.setLifes(0);
                    this.learningModel.setScore(0);
                    this.startToggleButton.setSelected(false);
                    DialogsUtil.emptyDataBase();
                }
        } else {
            this.startToggleButton.setSelected(false);
            DialogsUtil.emptyDataBase();
        }


    }

    public void stopLearningAction() {

        Optional<ButtonType> result = DialogsUtil.stopLearning();

        if (result.get() == ButtonType.OK) {

            learningModel.saveScoreInTopList();
            loadAllButton.setSelected(false);
            startToggleButton.setSelected(false);
            setPlay(false);
        }


    }


    public void loadLabels(Learning learning) {

        wordLabel.setText(learning.getWord());
        answerAButton.setText(learning.getAnswerA());
        answerBButton.setText(learning.getAnswerB());
        answerCButton.setText(learning.getAnswerC());
        scoreLabel.setText(String.valueOf(learning.getScore()));
        if (learning.getLifes() == 1)
            lifeLabel.setText(heart);
        else if (learning.getLifes() == 2)
            lifeLabel.setText(heart + heart);
        else if ((learning.getLifes() == 3))
            lifeLabel.setText(heart + heart + heart);
        if (topListModel.getMaxValue() >= learning.getScore())
            theBestScoreLabel.setText(String.valueOf(topListModel.getMaxValue()));
        else if (topListModel.getMaxValue() < learning.getScore())
            topListModel.setMaxValue(learning.getScore());
        theBestScoreLabel.setText(String.valueOf(topListModel.getMaxValue()));


    }


//-----------------------------------------------------------------------------------------------


    public ToggleButton getStartToggleButton() {
        return startToggleButton;
    }

    public void setStartToggleButton(ToggleButton startToggleButton) {
        this.startToggleButton = startToggleButton;
    }

    public boolean isPlay() {
        return play.get();
    }

    public BooleanProperty playProperty() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play.set(play);
    }
}
