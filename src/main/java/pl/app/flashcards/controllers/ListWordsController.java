package pl.app.flashcards.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.app.flashcards.modelFx.CategoryFx;
import pl.app.flashcards.modelFx.ListWordsModel;
import pl.app.flashcards.modelFx.WordFx;
import pl.app.flashcards.modelFx.WordModel;
import pl.app.flashcards.utils.DialogsUtil;
import pl.app.flashcards.utils.FxmlUtils;
import pl.app.flashcards.utils.exceptions.ApplicationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListWordsController {
    @FXML
    private Button deleteWordButton;
    @FXML
    private Button editWordButton;
    @FXML
    private Button addNewWordButton;
    @FXML
    private Button clearFilterComboBox;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private Button addCategoryButton;
    @FXML
    private Button editCategoryButton;
    @FXML
    private Button deleteCategoryButton;

    @FXML
    private TableView<WordFx> tableView;
    @FXML
    private TableColumn<WordFx, CategoryFx> categoryColumn;

    @FXML
    private TableColumn<WordFx, String> wordColumn;

    @FXML
    private TableColumn<WordFx, String> definitionColumn;

    private ListWordsModel listWordsModel;

    static ResourceBundle bundle = FxmlUtils.getResourceBundle();


    @FXML
    public void initialize() {
        this.listWordsModel = new ListWordsModel();
        try {
            this.listWordsModel.init();
        } catch (ApplicationException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }

        this.categoryComboBox.setItems(this.listWordsModel.getCategoryFxObservableList());
        this.listWordsModel.categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());

        this.tableView.setItems(this.listWordsModel.getWordFxObservableList());
        this.categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryFxProperty());
        this.wordColumn.setCellValueFactory(cellData -> cellData.getValue().wordProperty());
        this.definitionColumn.setCellValueFactory(cellData -> cellData.getValue().definitionProperty());

        initBindings();

    }


    @FXML
    void deleteCategory( ) {
        try {
            this.listWordsModel.deleteCategoryById();
        } catch (ApplicationException e) {
            DialogsUtil.errorDialog(e.getMessage());
        } catch (SQLException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }
    }

    @FXML
    public void editCategory() {

        try {
            this.listWordsModel.editCategory();
        } catch (ApplicationException e) {
            DialogsUtil.editDialog(e.getMessage())
            ;
        }

    }

    private void initBindings() {

        this.editCategoryButton.disableProperty().bind(this.categoryComboBox.valueProperty().isNull());
        this.deleteCategoryButton.disableProperty().bind(this.categoryComboBox.valueProperty().isNull());
        this.clearFilterComboBox.disableProperty().bind(this.categoryComboBox.valueProperty().isNull());
        this.editWordButton.disableProperty().bind(this.tableView.getSelectionModel().selectedItemProperty().isNull());
        this.deleteWordButton.disableProperty().bind(this.tableView.getSelectionModel().selectedItemProperty().isNull());


    }

    public void filterOnActionComboBox() throws SQLException, ApplicationException {
        if (!categoryComboBox.getSelectionModel().isEmpty())
            this.listWordsModel.filterWordsList();
    }


    public void clearCategoryComboBox() throws ApplicationException {
        this.categoryComboBox.getSelectionModel().clearSelection();
        this.listWordsModel.init();
    }


    public void addNewWordOnAction( ) {

        FXMLLoader loader = FxmlUtils.getLoader("/fxml/AddWord.fxml");
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }
        AddWordController controller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(bundle.getString("edit.word"));
        stage.getIcons().add(new Image("/icons/icon.png"));
        stage.showAndWait();
        initialize();


    }

    public void editWord( ) throws ApplicationException {
        WordModel wordModel = new WordModel();
        wordModel.init();
        WordFx item = this.tableView.getSelectionModel().selectedItemProperty().get();
        wordModel.setWordsFxObjectProperty(item);

        Optional result = DialogsUtil.editWord(item);
        if (result.get() == ButtonType.OK) {
            wordModel.saveWordInDataBase();
        }
        initialize();

    }


    public void deleteWord( ) {
        WordFx item = this.tableView.getSelectionModel().selectedItemProperty().get();
        try {
            listWordsModel.deleteWord(item);
        } catch (ApplicationException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }
    }


}
