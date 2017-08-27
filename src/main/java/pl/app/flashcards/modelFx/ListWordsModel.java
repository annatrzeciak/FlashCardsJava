package pl.app.flashcards.modelFx;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import pl.app.flashcards.database.dao.CategoryDao;
import pl.app.flashcards.database.dao.WordDao;
import pl.app.flashcards.database.models.Category;
import pl.app.flashcards.database.models.Word;
import pl.app.flashcards.utils.DialogsUtil;
import pl.app.flashcards.utils.converters.ConverterCategory;
import pl.app.flashcards.utils.converters.ConverterWord;
import pl.app.flashcards.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class ListWordsModel {


    private ObservableList<WordFx> wordFxObservableList = FXCollections.observableArrayList();
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();

    private ObjectProperty<CategoryFx> categoryFxObjectProperty = new SimpleObjectProperty<>();
    private ObjectProperty<WordFx> wordFxObjectProperty = new SimpleObjectProperty<>();

    private List<WordFx> wordFxList = new ArrayList<>();
    private Category category;

    public void init() throws ApplicationException {
        WordDao wordDao = new WordDao();
        CategoryDao categoryDao= new CategoryDao();

        List<Word> words = null;

        try {
            words = wordDao.getQueryBuilder(Word.class).selectColumns().join(categoryDao.getQueryBuilder(Category.class).orderBy("CATEGORY_NAME",true)).query();
        } catch (SQLException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }


        wordFxList.clear();
        words.forEach(word -> {
            this.wordFxList.add(ConverterWord.convertToWordFx(word));
        });
        this.wordFxObservableList.setAll(wordFxList);

        initCategory();
    }

    private void initCategory() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = null;
        try {
            categories = categoryDao.getQueryBuilder(Category.class).orderByRaw("CATEGORY_NAME").query();
        } catch (SQLException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }
        this.categoryFxObservableList.clear();
        categories.forEach(c -> {
            CategoryFx categoryFx = ConverterCategory.convertToCategoryFx(c);
            this.categoryFxObservableList.add(categoryFx);
        });
    }

    public void filterWordsList() throws ApplicationException, SQLException {
        WordDao wordDao = new WordDao();
        List<Word> words = wordDao.getQueryBuilder(Word.class).where().eq("CATEGORY_ID", this
                .categoryFxObjectProperty.getValue().getId().get()).query();
        List<WordFx> wordsFx = new ArrayList<>();
        this.wordFxObservableList.clear();
        words.forEach(e -> {
            wordsFx.add(ConverterWord.convertToWordFx(e));
        });
        this.wordFxObservableList.setAll(wordsFx);


    }


    public void deleteCategoryById() throws ApplicationException, SQLException {
        Optional result = DialogsUtil.deleteCategoryAlert();
        if (result.get() == ButtonType.OK) {
            CategoryDao categoryDao = new CategoryDao();
            categoryDao.deleteById(Category.class, categoryFxObjectProperty.get().getId().get());
            WordDao wordDao = new WordDao();
            wordDao.deleteByColumnName(Word.CATEGORY_ID, categoryFxObjectProperty.get().getId().get());
            init();
        }

    }

    public void editCategory() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        Optional<String> result = DialogsUtil.editDialog(categoryFxObjectProperty.get().getName());
        System.out.println(result);

        if (result.isPresent()) {

            String newName = result.get();

            if (newName != null) {
                Category tempCategory = categoryDao.findById(Category.class, categoryFxObjectProperty
                        .get().getId().get());
                tempCategory.setName(newName);
                categoryDao.creatOrUpdate(tempCategory);
            }

            init();
        }
    }

    public void deleteWord(WordFx wordFx) throws ApplicationException {
        WordDao wordDao = new WordDao();
        wordDao.deleteById(Word.class, wordFx.getId());
        init();
    }


    public ObservableList<WordFx> getWordFxObservableList() {
        return wordFxObservableList;
    }

    public void setWordFxObservableList(ObservableList<WordFx> wordFxObservableList) {
        this.wordFxObservableList = wordFxObservableList;
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public CategoryFx getCategoryFxObjectProperty() {
        return categoryFxObjectProperty.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyProperty() {
        return categoryFxObjectProperty;
    }

    public void setCategoryFxObjectProperty(CategoryFx categoryFxObjectProperty) {
        this.categoryFxObjectProperty.set(categoryFxObjectProperty);
    }

    public List<WordFx> getWordFxList() {
        return wordFxList;
    }

    public void setWordFxList(List<WordFx> wordFxList) {
        this.wordFxList = wordFxList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public WordFx getWordFxObjectProperty() {
        return wordFxObjectProperty.get();
    }

    public ObjectProperty<WordFx> wordFxObjectPropertyProperty() {
        return wordFxObjectProperty;
    }

    public void setWordFxObjectProperty(WordFx wordFxObjectProperty) {
        this.wordFxObjectProperty.set(wordFxObjectProperty);
    }


}
