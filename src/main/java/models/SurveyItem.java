package models;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class SurveyItem {

    @CsvBindByName(column = "surveyId")
    String surveyId;

    @CsvBindByName(column = "participantId")
    String participantId;

    @CsvBindByName(column = "projectId")
    String projectId;

    @CsvBindByName(column = "container")
    String container;

    @CsvBindByName(column = "uniqueId")
    String uniqueId;

    @CsvBindByName(column = "surveyURL")
    String surveyUrl;
}
