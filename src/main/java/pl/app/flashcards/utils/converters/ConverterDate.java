package pl.app.flashcards.utils.converters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class ConverterDate {

    public static Date convertToDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertToLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
