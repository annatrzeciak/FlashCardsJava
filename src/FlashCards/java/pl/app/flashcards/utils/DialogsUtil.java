package pl.app.flashcards.utils;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pl.app.flashcards.database.models.Learning;
import pl.app.flashcards.modelFx.CategoryFx;
import pl.app.flashcards.modelFx.WordFx;
import pl.app.flashcards.modelFx.WordModel;
import pl.app.flashcards.utils.exceptions.ApplicationException;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogsUtil {
    private static WordModel wordModel = new WordModel();
    ;
    static ResourceBundle bundle = FxmlUtils.getResourceBundle();

    public static void dialogAboutApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("about.title"));
        informationAlert.setHeaderText(bundle.getString("about.header"));
        informationAlert.setContentText(bundle.getString("about.content"));

        Stage stage = (Stage) informationAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));

        informationAlert.showAndWait();
    }

    public static Optional<ButtonType> confirmationDialog() {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(bundle.getString("confirmation.title"));
        confirmationAlert.setHeaderText(bundle.getString("confirmation.header"));
        Stage stage = (Stage) confirmationAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));

        Optional<ButtonType> result = confirmationAlert.showAndWait();


        return result;
    }

    public static void errorDialog(String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("error.title"));
        errorAlert.setHeaderText(bundle.getString("error.header"));

        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        Stage stage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));
        errorAlert.showAndWait();
    }

    public static Optional<String> addNewCategory() {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(bundle.getString("add.new.category"));
        dialog.setHeaderText(bundle.getString("add.category.header"));

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));

        Optional<String> result = dialog.showAndWait();


        return result;


    }

    public static Optional<String> editDialog(String name) {
        TextInputDialog dialog = new TextInputDialog(name);
        dialog.setTitle(bundle.getString("edit.title"));
        dialog.setHeaderText(bundle.getString("edit.header"));
        dialog.setContentText(bundle.getString("edit.content"));

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));
        Optional<String> result = dialog.showAndWait();

        return result;
    }

    public static Optional<String> endLifesDialog(int score) {
        TextInputDialog endLifeDialog = new TextInputDialog();
        endLifeDialog.setTitle(bundle.getString("end.lifes.title"));
        endLifeDialog.setHeaderText(bundle.getString("end.lifes.header") + " " + score);
        endLifeDialog.setContentText(bundle.getString("end.lifes.content"));

        Stage stage = (Stage) endLifeDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));
        Optional<String> result = endLifeDialog.showAndWait();

        return result;


    }

    public static Optional<String> endWordsDialog(int score, int lifes) {
        TextInputDialog endWordsAlert = new TextInputDialog();
        endWordsAlert.setTitle(bundle.getString("end.words.title"));
        endWordsAlert.setHeaderText(bundle.getString("end.words.header") + " " + score + " + " + lifes + " " + bundle.getString
                ("end.words.header2"));
        endWordsAlert.setContentText(bundle.getString("end.lifes.content"));
        Stage stage = (Stage) endWordsAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));

        Optional<String> result = endWordsAlert.showAndWait();

        return result;


    }

    public static Optional editWord(WordFx item) throws ApplicationException {

        wordModel.init();
        wordModel.setWordsFxObjectProperty(item);
        System.out.println(wordModel.getWordsFxObjectProperty().getWord());

        Dialog dialog = new Dialog();

        dialog.setTitle(bundle.getString("edit.dialog.title"));
        GridPane gridPane = new GridPane();
        HBox hBox = new HBox();

        Label labelCategory = new Label(bundle.getString("category.name"));
        Label labelWord = new Label(bundle.getString("word"));
        Label labelDefinition = new Label(bundle.getString("definition"));
        TextField textFieldWord = new TextField();
        TextField textFieldDefinition = new TextField();
        ComboBox<CategoryFx> categoryFxComboBox = new ComboBox<>();


        ButtonType buttonTypeOK = ButtonType.OK;
        ButtonType buttonTypeCancel = ButtonType.CANCEL;
        Button buttonAdd = new Button();
        Image image = new Image("/icons/add-icon-2.png");
        ImageView imageView = new ImageView(image);

        buttonAdd.setGraphic(imageView);
        buttonAdd.setOnAction(event -> {
            try {
                wordModel.addCategory();
            } catch (ApplicationException e) {
                DialogsUtil.errorDialog(e.getMessage());
            }
        });
        imageView.setFitHeight(17);
        imageView.setFitWidth(17);
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        hBox.setSpacing(15);


        hBox.getChildren().addAll(categoryFxComboBox, buttonAdd);
        gridPane.add(labelCategory, 1, 1);
        gridPane.add(hBox, 2, 1);
        gridPane.add(labelWord, 1, 2);
        gridPane.add(textFieldWord, 2, 2);
        gridPane.add(labelDefinition, 1, 3);
        gridPane.add(textFieldDefinition, 2, 3);

        dialog.getDialogPane().getButtonTypes().add(buttonTypeOK);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);


        dialog.getDialogPane().setContent(gridPane);

        categoryFxComboBox.setItems(wordModel.getCategoryFxObservableList());


        categoryFxComboBox.valueProperty().bindBidirectional(wordModel.getWordsFxObjectProperty().categoryFxProperty());
        textFieldWord.textProperty().bindBidirectional(wordModel.getWordsFxObjectProperty().wordProperty());
        textFieldDefinition.textProperty().bindBidirectional(wordModel.getWordsFxObjectProperty().definitionProperty());

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));
        Optional result = dialog.showAndWait();
        return result;


    }

    public static Optional deleteCategoryAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(bundle.getString("delete.category.title"));
        alert.setHeaderText(bundle.getString("delete.category.header"));
        alert.setContentText(bundle.getString("delete.category.content"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));
        Optional<ButtonType> result = alert.showAndWait();

        return result;

    }

    public static Optional<ButtonType> stopLearning() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(bundle.getString("stop.learning.title"));
        alert.setHeaderText(bundle.getString("stop.learning.header"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));

        Optional<ButtonType> result = alert.showAndWait();

        return result;

    }

    public static void wrongAnswer(Learning learning) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String goodAnswerText = null;
        if (learning.getGoodAnswer().equals("A"))
            goodAnswerText = learning.getAnswerA();
        else if (learning.getGoodAnswer().equals("B"))
            goodAnswerText = learning.getAnswerB();
        else if (learning.getGoodAnswer().equals("C"))
            goodAnswerText = learning.getAnswerC();

        alert.setTitle(bundle.getString("wrong.answer.title"));
        alert.setHeaderText(bundle.getString("wrong.answer.header")+" "+learning.getWord() + " = " + goodAnswerText);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));

        alert.showAndWait();
    }

    public static void emptyDataBase() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(bundle.getString("empty.date.base.title"));
        alert.setHeaderText(bundle.getString("empty.date.base.header"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/icon.png"));

        alert.showAndWait();

    }
}
