package com.analytics.util;

import com.analytics.model.CampaignAnalytics;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading and parsing CSV files
 */
public class CsvReader {

    /**
     * Read CSV file and convert to CampaignAnalytics objects (single records)
     * @param filePath Path to the CSV file
     * @return List of campaign records
     * @throws IOException If file cannot be read
     */
    public static List<CampaignAnalytics> readCsv(String filePath) throws IOException, CsvValidationException {
        List<CampaignAnalytics> records = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(parser)
                .withSkipLines(1)  // Skip header
                .build()) {

            String[] line;
            int lineNumber = 2;  // Start at 2 (1 is header)

            while ((line = reader.readNext()) != null) {
                try {
                    if (line.length < 6) {
                        System.err.println("[WARN] Line " + lineNumber + ": Skipping malformed record (insufficient columns)");
                        lineNumber++;
                        continue;
                    }

                    String campaignId = line[0].trim();
                    String date = line[1].trim();
                    long impressions = Long.parseLong(line[2].trim());
                    long clicks = Long.parseLong(line[3].trim());
                    double spend = Double.parseDouble(line[4].trim());
                    long conversions = Long.parseLong(line[5].trim());

                    // Validate data
                    if (impressions < 0 || clicks < 0 || spend < 0 || conversions < 0) {
                        System.err.println("[WARN] Line " + lineNumber + ": Skipping record with negative values");
                        lineNumber++;
                        continue;
                    }

                    if (clicks > impressions) {
                        System.err.println("[WARN] Line " + lineNumber + ": Clicks (" + clicks + ") > Impressions (" + impressions + "). Skipping record.");
                        lineNumber++;
                        continue;
                    }

                    CampaignAnalytics record = new CampaignAnalytics(
                            campaignId, impressions, clicks, spend, conversions
                    );
                    records.add(record);

                } catch (NumberFormatException e) {
                    System.err.println("[WARN] Line " + lineNumber + ": Invalid number format: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("[WARN] Line " + lineNumber + ": Error parsing record: " + e.getMessage());
                }

                lineNumber++;
            }
        }

        return records;
    }
}
