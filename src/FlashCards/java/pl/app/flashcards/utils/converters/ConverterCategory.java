package pl.app.flashcards.utils.converters;

import pl.app.flashcards.database.models.Category;
import pl.app.flashcards.modelFx.CategoryFx;

public class ConverterCategory {

    public static CategoryFx convertToCategoryFx(Category category){
        CategoryFx categoryFx = new CategoryFx();
        categoryFx.setId(category.getId());
        categoryFx.setName(category.getName().toUpperCase());
        return categoryFx;
    }
    public static Category convertToCategory (CategoryFx categoryFx){
        Category category= new Category();
        category.setId(categoryFx.getId());
        category.setName(categoryFx.getName().toUpperCase());
        return category;
    }
}
