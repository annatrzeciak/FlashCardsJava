package pl.app.flashcards.controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.app.flashcards.modelFx.TopListFx;
import pl.app.flashcards.modelFx.TopListModel;
import pl.app.flashcards.utils.DialogsUtil;
import pl.app.flashcards.utils.exceptions.ApplicationException;

import java.time.LocalDate;

public class TopListController {

    public Button deleteAllScoreButton;
    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<TopListFx, String> scoreColumn;

    @FXML
    private TableColumn<TopListFx, String> nicknameColumn;

    @FXML
    private TableColumn<TopListFx, LocalDate> dateColumn;

    private TopListModel topListModel;

    @FXML
    private void initialize() {

        this.topListModel = new TopListModel();
        try {
            this.topListModel.init();
        } catch (ApplicationException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }

        this.tableView.setItems(this.topListModel.getTopListFxObservableList());

        this.nicknameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        this.dateColumn.setCellValueFactory(param -> param.getValue().dateProperty());
        this.scoreColumn.setCellValueFactory(param -> param.getValue().scoreProperty().asString());


    }


    public TableView getTableView() {
        return tableView;
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }

    public TableColumn<TopListFx, String> getScoreColumn() {
        return scoreColumn;
    }

    public void setScoreColumn(TableColumn<TopListFx, String> scoreColumn) {
        this.scoreColumn = scoreColumn;
    }

    public TableColumn<TopListFx, String> getNicknameColumn() {
        return nicknameColumn;
    }

    public void setNicknameColumn(TableColumn<TopListFx, String> nicknameColumn) {
        this.nicknameColumn = nicknameColumn;
    }

    public TableColumn<TopListFx, String> getNicknameColum() {
        return nicknameColumn;
    }

    public void setNicknameColum(TableColumn<TopListFx, String> nicknameColum) {
        this.nicknameColumn = nicknameColum;
    }

    public TableColumn<TopListFx, LocalDate> getDateColumn() {
        return dateColumn;
    }

    public void setDateColumn(TableColumn<TopListFx, LocalDate> dateColumn) {
        this.dateColumn = dateColumn;
    }

    public TopListModel getTopListModel() {
        return topListModel;
    }

    public void setTopListModel(TopListModel topListModel) {
        this.topListModel = topListModel;
    }

    }

