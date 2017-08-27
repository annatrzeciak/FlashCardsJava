package pl.app.flashcards.database.dbuitls;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import pl.app.flashcards.database.models.TopList;
import pl.app.flashcards.database.models.Category;
import pl.app.flashcards.database.models.Word;


import java.io.IOException;
import java.sql.SQLException;

public class DbManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

    private static final String JDBC_DRIVER_HD = "jdbc:h2:./libraryDB";
    private static final String USER = "admin";
    private static final String PASS = "admin";

    private static ConnectionSource connectionSource;

    public static void initDatabase() {
        createConnectionSource();
        dropTable();

        createTable();
        closeConnectionSource();
    }

    private static void createConnectionSource() {
        try {
            connectionSource = new JdbcConnectionSource(JDBC_DRIVER_HD, USER, PASS);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public static ConnectionSource getConnectionSource() {
        if (connectionSource == null) {
            createConnectionSource();
        }
        return connectionSource;
    }

    public static void closeConnectionSource() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

    private static void createTable() {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Word.class);
            TableUtils.createTableIfNotExists(connectionSource, TopList.class);
            TableUtils.createTableIfNotExists(connectionSource, Category.class);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    private static void dropTable() {
        try {
            TableUtils.dropTable(connectionSource, Word.class, true);
            TableUtils.dropTable(connectionSource, TopList.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }
}