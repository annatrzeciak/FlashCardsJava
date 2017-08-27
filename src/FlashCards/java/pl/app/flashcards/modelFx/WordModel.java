package pl.app.flashcards.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.app.flashcards.database.dao.CategoryDao;
import pl.app.flashcards.database.dao.WordDao;
import pl.app.flashcards.database.models.Category;
import pl.app.flashcards.database.models.Word;
import pl.app.flashcards.utils.DialogsUtil;
import pl.app.flashcards.utils.converters.ConverterCategory;
import pl.app.flashcards.utils.converters.ConverterWord;
import pl.app.flashcards.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class WordModel {

    private ObjectProperty<WordFx> wordsFxObjectProperty = new SimpleObjectProperty<>(new WordFx());
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();


    public void init() throws ApplicationException {
        initCategoryList();

    }
    private void initCategoryList() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();

        List<Category> categoryList = null;
        try {
            categoryList = categoryDao.getQueryBuilder(Category.class).orderByRaw("CATEGORY_NAME").query();
        } catch (SQLException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }

        categoryFxObservableList.clear();

        categoryList.forEach(c->{
            CategoryFx categoryFx = ConverterCategory.convertToCategoryFx(c);
            categoryFxObservableList.add(categoryFx);
        });




    }

    //    public void saveEditInDataBase() throws ApplicationException {
//        saveOrUpdate(this.getWordsFxObjectPropertyEdit());
//    }
    public void addCategory() throws ApplicationException {
        Optional<String> result = DialogsUtil.addNewCategory();
        Category category=null;
        if (!result.equals(Optional.empty()) && !result.get().equals("")) {
            CategoryDao categoryDao = new CategoryDao();
            category = new Category();
            category.setName(result.get());
            categoryDao.creatOrUpdate(category);

            init();
        }

    }

    public void saveWordInDataBase() throws ApplicationException {
        Word word = ConverterWord.convertToWord(this.getWordsFxObjectProperty());

        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.findById(Category.class,  this.getWordsFxObjectProperty().getCategoryFx()
                .getId().get());


        word.setCategoryID(category);

        WordDao wordDao = new WordDao();
        wordDao.creatOrUpdate(word);


    }





    public WordFx getWordsFxObjectProperty() {
        return wordsFxObjectProperty.get();
    }

    public ObjectProperty<WordFx> wordsFxObjectPropertyProperty() {
        return wordsFxObjectProperty;
    }

    public void setWordsFxObjectProperty(WordFx wordsFxObjectProperty) {
        this.wordsFxObjectProperty.set(wordsFxObjectProperty);
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

}