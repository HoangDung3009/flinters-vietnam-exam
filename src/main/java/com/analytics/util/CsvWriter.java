package com.analytics.util;

import com.analytics.model.CampaignAnalytics;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for writing campaign analytics to CSV files
 */
public class CsvWriter {

    /**
     * Write campaign analytics to CSV file
     * @param filePath Output file path
     * @param campaigns List of campaigns to write
     * @throws IOException If file cannot be written
     */
    public static void writeCsv(String filePath, List<CampaignAnalytics> campaigns) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Write header
            String[] header = {"campaign_id", "total_impressions", "total_clicks", 
                             "total_spend", "total_conversions", "CTR", "CPA"};
            writer.writeNext(header);

            // Write data rows
            for (CampaignAnalytics campaign : campaigns) {
                String[] row = {
                        campaign.getCampaignId(),
                        String.valueOf(campaign.getTotalImpressions()),
                        String.valueOf(campaign.getTotalClicks()),
                        String.format("%.2f", campaign.getTotalSpend()),
                        String.valueOf(campaign.getTotalConversions()),
                        String.format("%.4f", campaign.getCtr()),
                        campaign.getCpa() != null ? String.format("%.2f", campaign.getCpa()) : "N/A"
                };
                writer.writeNext(row);
            }
        }
    }
}
