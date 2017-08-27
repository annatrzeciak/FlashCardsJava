package pl.app.flashcards.utils;

import pl.app.flashcards.database.dao.WordDao;
import pl.app.flashcards.database.dao.CategoryDao;
import pl.app.flashcards.database.dbuitls.DbManager;

import pl.app.flashcards.database.models.Word;
import pl.app.flashcards.database.models.Category;
import pl.app.flashcards.utils.exceptions.ApplicationException;


public class FillDatabase {
    public static  void fillDatabase(){
        Category category1 = new Category();
        category1.setName("IT");
        Word word1 = new Word();
        word1.setWord("achieve");
        word1.setDefinition("osiągać");
        word1.setCategoryID(category1);
        Word word2 = new Word();
        word2.setWord("coherent solution");
        word2.setDefinition("spójne rozwiązanie");
        word2.setCategoryID(category1);
        Category category2= new Category();
        category2.setName("Math");
        Word word3 = new Word();
        word3.setWord("percent");
        word3.setDefinition("procent");
        word3.setCategoryID(category2);
        Word word4 = new Word();
        word4.setWord("sign");
        word4.setDefinition("znak");
        word4.setCategoryID(category2);
        Word word5 = new Word();
        word5.setWord("fraction");
        word5.setDefinition("ułamek");
        word5.setCategoryID(category2);
        Category category3= new Category();
        category3.setName("Kolory");
        Word word6 = new Word();
        word6.setWord("green");
        word6.setDefinition("zielony");
        word6.setCategoryID(category3);
        Word word7 = new Word();
        word7.setWord("red");
        word7.setDefinition("czerwony");
        word7.setCategoryID(category3);


        CategoryDao categoryDao = new CategoryDao();
        try {
            categoryDao.creatOrUpdate(category2);
            DbManager.closeConnectionSource();
        } catch (ApplicationException e) {
            DialogsUtil.errorDialog(e.getMessage());
        }

        WordDao wordDao = new WordDao();
        try {
            wordDao.creatOrUpdate(word1);
            wordDao.creatOrUpdate(word2);
            wordDao.creatOrUpdate(word3);
            wordDao.creatOrUpdate(word4);
            wordDao.creatOrUpdate(word5);
            wordDao.creatOrUpdate(word6);
            wordDao.creatOrUpdate(word7);
        } catch (ApplicationException e) {
           DialogsUtil.errorDialog(e.getMessage());
        }
        DbManager.closeConnectionSource();
    }
}