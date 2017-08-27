package pl.app.flashcards.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.app.flashcards.database.dao.TopListDao;
import pl.app.flashcards.database.models.TopList;
import pl.app.flashcards.utils.DialogsUtil;
import pl.app.flashcards.utils.converters.ConverterDate;
import pl.app.flashcards.utils.converters.ConverterTopList;
import pl.app.flashcards.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TopListModel {

    private ObservableList<TopListFx> topListFxObservableList = FXCollections.observableArrayList();
    private IntegerProperty maxValue = new SimpleIntegerProperty();
    List<TopListFx> topListFxList = new ArrayList<>();


    public void init() throws ApplicationException {
        TopListDao topListDao = new TopListDao();
        List<TopList> topListList = new ArrayList<>();

        try {
            topListList = topListDao.getQueryBuilder(TopList.class).orderBy("SCORE", false).query();
        } catch (SQLException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }
        if (topListList.size() > 0)
            setMaxValue(topListList.get(0).getScore());
        else
            setMaxValue(0);


        this.topListFxList.clear();
        topListList.forEach(e -> {
            this.topListFxList.add(ConverterTopList.convertToTopListFx(e));
        });
        this.topListFxObservableList.setAll(topListFxList);

    }

    public void addScore(int score, String name) {


        TopList topList = new TopList();
        topList.setScore(score);
        topList.setName(name);
        topList.setDate(ConverterDate.convertToDate(LocalDate.now()));


        TopListDao topListDao = new TopListDao();

        try {
            topListDao.getDao(TopList.class).create(topList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }


    public ObservableList<TopListFx> getTopListFxObservableList() {
        return topListFxObservableList;
    }

    public void setTopListFxObservableList(ObservableList<TopListFx> topListFxObservableList) {
        this.topListFxObservableList = topListFxObservableList;
    }

    public List<TopListFx> getTopListFxList() {
        return topListFxList;
    }

    public void setTopListFxList(List<TopListFx> topListFxList) {
        this.topListFxList = topListFxList;
    }


    public int getMaxValue() {
        return maxValue.get();
    }

    public IntegerProperty maxValueProperty() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue.set(maxValue);
    }
}
