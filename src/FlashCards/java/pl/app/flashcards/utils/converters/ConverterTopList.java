package pl.app.flashcards.utils.converters;

import pl.app.flashcards.database.models.TopList;
import pl.app.flashcards.modelFx.TopListFx;

public class ConverterTopList {

    public static TopListFx convertToTopListFx(TopList topList){
        TopListFx topListFx = new TopListFx();
        topListFx.setId(topList.getId());
        topListFx.setName(topList.getName());
        topListFx.setScore(topList.getScore());
        topListFx.setDate(ConverterDate.convertToLocalDate(topList.getDate()));


        return topListFx;
    }

}
