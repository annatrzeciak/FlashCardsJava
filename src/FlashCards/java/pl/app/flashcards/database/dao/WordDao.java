package pl.app.flashcards.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import pl.app.flashcards.database.models.Word;
import pl.app.flashcards.utils.exceptions.ApplicationException;

import java.sql.SQLException;

public class WordDao extends CommonDao {


    public WordDao() {
        super();
    }

    public void deleteByColumnName(String columnName, int id) throws ApplicationException, SQLException {
        Dao<Word, Object> dao = getDao(Word.class);
        DeleteBuilder<Word, Object> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq(columnName, id);
        dao.delete(deleteBuilder.prepare());
    }


}
