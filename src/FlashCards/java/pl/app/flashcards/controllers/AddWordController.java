package pl.app.flashcards.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.app.flashcards.modelFx.CategoryFx;
import pl.app.flashcards.modelFx.WordModel;
import pl.app.flashcards.utils.DialogsUtil;
import pl.app.flashcards.utils.exceptions.ApplicationException;


public class AddWordController {
    @FXML
    private Button addCategoryButton;
    @FXML
    private TextField newWord;
    @FXML
    private TextField newWordDefinition;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private Button addWordButton;

    private WordModel wordModel;

    @FXML
    public void initialize() throws ApplicationException {
        this.wordModel = new WordModel();

        this.wordModel.init();

        bindings();
        validation();

    }

    public void bindings() {
        this.categoryComboBox.setItems(this.wordModel.getCategoryFxObservableList());

        this.categoryComboBox.valueProperty().bindBidirectional(this.wordModel.getWordsFxObjectProperty().categoryFxProperty());
        this.newWord.textProperty().bindBidirectional(this.wordModel.getWordsFxObjectProperty().wordProperty());
        this.newWordDefinition.textProperty().bindBidirectional(this.wordModel.getWordsFxObjectProperty().definitionProperty());
    }

    private void validation() {
        this.addWordButton.disableProperty().bind(this.categoryComboBox.valueProperty().isNull().or(this.newWord
                .textProperty().isEmpty()).or(this.newWordDefinition.textProperty().isEmpty()));
    }


    public void addWordOnAction() throws ApplicationException {
        try {
            this.wordModel.saveWordInDataBase();
            clearFields();

        } catch (ApplicationException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }

        this.wordModel.init();

    }

    private void clearFields() {
        this.categoryComboBox.getSelectionModel().clearSelection();
        this.newWord.clear();
        this.newWordDefinition.clear();
    }

    public void addCategoryOnAction() {
        try {
            this.wordModel.addCategory();
        } catch (ApplicationException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }

    }

}
