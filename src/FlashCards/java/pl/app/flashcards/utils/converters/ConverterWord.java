package pl.app.flashcards.utils.converters;

import pl.app.flashcards.database.models.Word;
import pl.app.flashcards.modelFx.WordFx;

public class ConverterWord{


    public static Word convertToWord(WordFx wordFx){
        Word word= new Word();
        word.setId(wordFx.getId());
        word.setWord(wordFx.getWord());
        word.setDefinition(wordFx.getDefinition());
        word.setCategoryID(ConverterCategory.convertToCategory(wordFx.getCategoryFx()));

        return word;
    }


    public static WordFx convertToWordFx(Word word) {
        WordFx wordFx= new WordFx();
        wordFx.setId(word.getId());
        wordFx.setWord(word.getWord());
        wordFx.setDefinition(word.getDefinition());
        wordFx.setCategoryFx(ConverterCategory.convertToCategoryFx(word.getCategoryID()));
        return wordFx;
    }
}
