package pl.app.flashcards.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.app.flashcards.database.dao.CategoryDao;
import pl.app.flashcards.database.models.Category;
import pl.app.flashcards.utils.DialogsUtil;
import pl.app.flashcards.utils.converters.ConverterCategory;
import pl.app.flashcards.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;

public class CategoryModel {

    private ObservableList<CategoryFx> categoryList = FXCollections.observableArrayList();
    private ObjectProperty<CategoryFx> category = new SimpleObjectProperty<>();

    public void init() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = null;
        try {
            categories = categoryDao.getQueryBuilder(Category.class).orderByRaw("CATEGORY_NAME").query();
        } catch (SQLException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }
        initCategoryList(categories);

    }


    private void initCategoryList(List<Category> categories) {
        this.categoryList.clear();

        categories.forEach(c-> {
            CategoryFx categoryFx = ConverterCategory.convertToCategoryFx(c);
            this.categoryList.add(categoryFx);
        });
    }

    public void deleteCategoryById() throws ApplicationException, SQLException {
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.deleteById(Category.class, category.get().getId().asObject().get());

        init();


    }

    public ObservableList<CategoryFx> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ObservableList<CategoryFx> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryFx getCategory() {
        return category.get();
    }

    public ObjectProperty<CategoryFx> categoryProperty() {
        return category;
    }

    public void setCategory(CategoryFx category) {
        this.category.set(category);
    }
}
