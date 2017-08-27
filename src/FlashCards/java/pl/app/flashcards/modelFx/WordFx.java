package pl.app.flashcards.modelFx;

import javafx.beans.property.*;

public class WordFx {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty word = new SimpleStringProperty();
    private StringProperty definition = new SimpleStringProperty();
    private ObjectProperty<CategoryFx> categoryFx = new SimpleObjectProperty<>();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getWord() {
        return word.get();
    }

    public StringProperty wordProperty() {
        return word;
    }

    public void setWord(String word) {
        this.word.set(word);
    }

    public String getDefinition() {
        return definition.get();
    }

    public StringProperty definitionProperty() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition.set(definition);
    }

    public CategoryFx getCategoryFx() {
        return categoryFx.get();
    }

    public ObjectProperty<CategoryFx> categoryFxProperty() {
        return categoryFx;
    }

    public void setCategoryFx(CategoryFx categoryFx) {
        this.categoryFx.set(categoryFx);
    }



}