package pl.app.flashcards.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "TOPLIST")
public class TopList implements BaseModel{
    public TopList() {
    }
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "SCORE", canBeNull = true)
    private int score;

    @DatabaseField(columnName = "NAME", canBeNull = false)
    private String name;

    @DatabaseField(columnName = "DATE", canBeNull = false)
    private Date date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "name: "+getName()+" score: "+getScore();
    }
}
