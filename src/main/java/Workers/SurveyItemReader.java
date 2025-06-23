package Workers;

import com.opencsv.bean.CsvToBeanBuilder;
import models.SurveyItem;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyItemReader {
    public static void main(String[] args) throws IOException {
        String csvPath = "/Users/saugatchetry/Downloads/very_large_updated_survey.csv";
        String outputPath = "missing_participant_rows.csv";

        // 1. Load all participant rows from CSV
        Map<String, SurveyItem> participantMap = loadCsv(csvPath);
        if (participantMap.isEmpty()) {
            System.err.println("CSV file is empty or missing participantId");
            return;
        }

        // 2. Extract surveyId (from any row)
        String surveyId = participantMap.values().iterator().next().getSurveyId();

        System.out.println("Size " + participantMap.size());
    }


    // Load CSV into Map<participantId, CsvParticipant>
    private static Map<String, SurveyItem> loadCsv(String csvPath) throws IOException {
        try (Reader reader = new FileReader(csvPath)) {
            List<SurveyItem> participants = new CsvToBeanBuilder<SurveyItem>(reader)
                    .withType(SurveyItem.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

            Map<String, SurveyItem> map = new HashMap<>();
            for (SurveyItem p : participants) {
                if (p.getParticipantId() != null && !p.getParticipantId().isEmpty()) {
                    map.put(p.getParticipantId(), p);
                }
            }
            return map;
        }
    }
}
