package pl.app.flashcards.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.IntegerProperty;
import pl.app.flashcards.modelFx.WordFx;

@DatabaseTable(tableName = "CATEGORIES")
public class Category implements BaseModel {
    private WordFx value;

    public Category() {
    }
    @DatabaseField(columnName = "CATEGORY_NAME", canBeNull = false, unique = true)
    private String name;

    @DatabaseField(generatedId = true)
    private int id;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Word> words;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public ForeignCollection<Word> getWords() {
        return words;
    }

    public void setWords(ForeignCollection<Word> words) {
        this.words = words;
    }

    public WordFx getValue() {
        return value;
    }

    public void setValue(WordFx value) {
        this.value = value;
    }

    public void setId(IntegerProperty id) {
    }
}