package pl.app.flashcards.modelFx;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.app.flashcards.controllers.LearningController;
import pl.app.flashcards.database.dao.CategoryDao;
import pl.app.flashcards.database.dao.WordDao;
import pl.app.flashcards.database.models.Category;
import pl.app.flashcards.database.models.Learning;
import pl.app.flashcards.database.models.Word;
import pl.app.flashcards.utils.DialogsUtil;
import pl.app.flashcards.utils.converters.ConverterCategory;
import pl.app.flashcards.utils.converters.ConverterWord;
import pl.app.flashcards.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LearningModel {


    private ObservableList<WordFx> wordFxObservableListAll = FXCollections.observableArrayList();
    private ObservableList<WordFx> wordFxObservableListChoiceCategory = FXCollections.observableArrayList();
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();

    private ObjectProperty<CategoryFx> categoryFxObjectProperty = new SimpleObjectProperty<>();

    private List<WordFx> wordFxList = new ArrayList<>();
    private Category category;
    private LearningController learningController= new LearningController();
    private LearningFx learningFx;
    private Learning learning = new Learning();
    List<String> listAnswer;
    List<Word> listWords ;
    int indexNumber;

    private String goodAnswer;
    Word word;
    private int lifes;
    private int score;
    Random random = new Random();


    public void init() throws ApplicationException {
        this.learningFx = new LearningFx();
        this.learning = new Learning();
        this.learningController = new LearningController();
        WordDao wordDao = new WordDao();
        List<Word> words = wordDao.queryForAll(Word.class);
        words.forEach(word -> {
            this.wordFxObservableListAll.add(ConverterWord.convertToWordFx(word));
        });

        initCategory();

        listAnswer = new ArrayList<>(3);
        listAnswer.add("A");
        listAnswer.add("B");
        listAnswer.add("C");

        lifes = 3;
        score = 0;

        listWords = new ArrayList<>();


    }

    private void initCategory() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = null;
        try {
            categories = categoryDao.getQueryBuilder(Category.class).orderBy("CATEGORY_NAME", true).query();
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
        this.wordFxObservableListChoiceCategory.clear();
        words.forEach(e -> {
            this.wordFxObservableListChoiceCategory.add(ConverterWord.convertToWordFx(e));
        });


    }

    public Learning randomWordFromAll() {

        listWords.clear();
        wordFxObservableListAll.forEach(e -> {
            Word word = ConverterWord.convertToWord(e);
            listWords.add(word);
        });

        return randomLearningModel(listWords);

    }

    public Learning randomWordFromCategory() {

        listWords.clear();
        wordFxObservableListChoiceCategory.forEach(e -> {
            Word word = ConverterWord.convertToWord(e);
            listWords.add(word);
        });

        return randomLearningModel(listWords);

    }

    public Learning randomLearningModel(List<Word> wordList) {

        if (lifes > 0 && listWords.size() > 0) {

            String answerA = null;
            String answerB = null;
            String answerC = null;

            int number = (int) Math.round(random.nextDouble() * (listAnswer.size() - 1));
            String randomAnswer = listAnswer.get(number);

            indexNumber = (int) Math.round(random.nextDouble() * (wordList.size() - 1));
            word = (wordList.get(indexNumber));


            if (randomAnswer.equals("A")) {
                answerA = word.getDefinition();

                for (; answerB == null || answerA.equals(answerB); ) {
                    answerB = randomAnotherAnswer();

                }
                for (; answerC == null || answerA.equals(answerC) || answerB.equals(answerC); ) {
                    answerC = randomAnotherAnswer();

                }

            } else if (randomAnswer.equals("B")) {
                answerB = word.getDefinition();

                for (; answerA == null || answerA.equals(answerB); ) {
                    answerA = randomAnotherAnswer();

                }
                for (; answerC == null || answerB.equals(answerC) || answerC.equals(answerA); ) {
                    answerC = randomAnotherAnswer();

                }

            } else if (randomAnswer.equals("C")) {
                answerC = word.getDefinition();

                for (; answerA == null || answerA.equals(answerC); ) {
                    answerA = randomAnotherAnswer();

                }
                for (; answerB == null || answerB.equals(answerC) || answerB.equals(answerA); ) {
                    answerB = randomAnotherAnswer();

                }

            }

            this.learning.setWord(word.getWord());
            this.learning.setAnswerA(answerA);
            this.learning.setAnswerB(answerB);
            this.learning.setAnswerC(answerC);
            this.learning.setGoodAnswer(randomAnswer);
            this.learning.setScore( score);
            this.learning.setLifes(lifes);

            return learning;
        } else if (lifes == 0) {
            learningController.setPlay(false);

            this.learning.setWord(null);
            this.learning.setAnswerA(null);
            this.learning.setAnswerB(null);
            this.learning.setAnswerC(null);
            this.learning.setGoodAnswer(null);
            this.learning.setScore(0);
            this.learning.setLifes(0);

            Optional<String> result = DialogsUtil.endLifesDialog(score);
            TopListModel topListModel= new TopListModel();
            if (result.isPresent()){
                System.out.println(result.get());
                topListModel.addScore( score, result.get());
            }



        } else if (listWords.size() == 0) {

                learningController.setPlay(false);


                this.learning.setWord(null);
                this.learning.setAnswerA(null);
                this.learning.setAnswerB(null);
                this.learning.setAnswerC(null);
                this.learning.setGoodAnswer(null);
                this.learning.setScore(0);
                this.learning.setLifes(0);

                saveScoreInTopList();

        }

        return learning;
    }

    public void saveScoreInTopList() {
        Optional<String> result = DialogsUtil.endWordsDialog(score, lifes);
        TopListModel topListModel= new TopListModel();
        if (result.isPresent()){

            topListModel.addScore( score+lifes,result.get());
        }
    }


    public String randomAnotherAnswer() {
        Random random = new Random();
        String anotherAnswer;

        anotherAnswer = ConverterWord.convertToWord(wordFxObservableListAll.get((int) (random.nextDouble() *
                (wordFxObservableListAll.size() - 1))))
                .getDefinition();

        return anotherAnswer;
    }


    public Learning goodAnswer() {

        listWords.remove(indexNumber);

        score ++;

        return randomLearningModel(listWords);

    }

    public Learning wrongAnswer() {
        DialogsUtil.wrongAnswer(learning);

        listWords.remove(indexNumber);
        lifes--;

        listWords.add(word);
        return randomLearningModel(listWords);

    }


    public ObservableList<WordFx> getWordFxObservableListAll() {
        return wordFxObservableListAll;
    }

    public void setWordFxObservableListAll(ObservableList<WordFx> wordFxObservableListAll) {
        this.wordFxObservableListAll = wordFxObservableListAll;
    }

    public Learning getLearning() {
        return learning;
    }

    public void setLearning(Learning learning) {
        this.learning = learning;
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

    public String getGoodAnswer() {
        return goodAnswer;
    }

    public void setGoodAnswer(String goodAnswer) {
        this.goodAnswer = goodAnswer;
    }

    public LearningFx getLearningFx() {
        return learningFx;
    }

    public void setLearningFx(LearningFx learningFx) {
        this.learningFx = learningFx;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public int  getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LearningController getLearningController() {
        return learningController;
    }

    public void setLearningController(LearningController learningController) {
        this.learningController = learningController;
    }

    public ObservableList<WordFx> getWordFxObservableListChoiceCategory() {
        return wordFxObservableListChoiceCategory;
    }

    public void setWordFxObservableListChoiceCategory(ObservableList<WordFx> wordFxObservableListChoiceCategory) {
        this.wordFxObservableListChoiceCategory = wordFxObservableListChoiceCategory;
    }
}
