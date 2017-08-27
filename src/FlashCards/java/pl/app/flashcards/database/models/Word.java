package pl.app.flashcards.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "WORDS")
public class Word implements BaseModel {


       public static final String CATEGORY_ID = "CATEGORY_ID";


    public Word() {
    }

    @DatabaseField(generatedId = true)
    private int id;


    @DatabaseField(columnName = CATEGORY_ID, foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true,
            canBeNull = false)
    private Category categoryID;


    @DatabaseField(columnName = "WORD", canBeNull = false)
    private String word;

    @DatabaseField(columnName = "DEFINITION", canBeNull = false)
    private String definition;

    public static String getCategoryId() {
        return CATEGORY_ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Category categoryID) {
        this.categoryID = categoryID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
