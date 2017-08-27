package pl.app.flashcards;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.app.flashcards.database.dbuitls.DbManager;
import pl.app.flashcards.utils.DialogsUtil;
import pl.app.flashcards.utils.FillDatabase;
import pl.app.flashcards.utils.FxmlUtils;

import java.util.Locale;
import java.util.Optional;

public class Main extends Application {

    public static final String BORDER_PANE_MAIN_FXML = "/fxml/BorderPaneMain.fxml";

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        //Locale.setDefault(new Locale("en"));

        Pane borderPane = FxmlUtils.ffxmlLoader(BORDER_PANE_MAIN_FXML);
        Scene scene = new Scene (borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("title.application"));
        primaryStage.setOnCloseRequest(event -> {
            Optional<ButtonType> result = DialogsUtil.confirmationDialog();
            if (result.get()==ButtonType.OK){
                Platform.exit();

            }
        });
        primaryStage.getIcons().add(new Image("/icons/icon.png"));
        primaryStage.show();

        DbManager.initDatabase();

        FillDatabase.fillDatabase();

    }
}
