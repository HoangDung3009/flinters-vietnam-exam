package com.analytics;

import com.analytics.model.CampaignAnalytics;
import com.analytics.processor.AnalyticsProcessor;
import com.analytics.util.CsvReader;
import com.analytics.util.CsvWriter;

import java.io.File;
import java.util.List;

/**
 * Main entry point for the CSV Analytics application
 * Processes campaign data from CSV and generates aggregated analytics reports
 */
public class CsvAnalyticsApp {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java -jar csv-analytics.jar <input-csv-path> <output-directory>");
            System.err.println("Example: java -jar csv-analytics.jar data/ad_data.csv output/");
            System.exit(1);
        }

        String inputPath = args[0];
        String outputDir = args[1];

        try {
            // Validate input file exists
            File inputFile = new File(inputPath);
            if (!inputFile.exists()) {
                System.err.println("Error: Input file not found: " + inputPath);
                System.exit(1);
            }

            // Create output directory if it doesn't exist
            File outputDirectory = new File(outputDir);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            System.out.println("[INFO] Starting CSV Analytics Processing...");
            System.out.println("[INFO] Input file: " + inputPath);
            System.out.println("[INFO] Output directory: " + outputDir);
            System.out.println();

            long startTime = System.currentTimeMillis();

            // Read CSV file
            System.out.println("[INFO] Reading CSV file...");
            List<CampaignAnalytics> allCampaigns = CsvReader.readCsv(inputPath);
            System.out.println("[INFO] Successfully read " + allCampaigns.size() + " records");
            System.out.println();

            // Process and aggregate data
            System.out.println("[INFO] Aggregating campaign data...");
            AnalyticsProcessor processor = new AnalyticsProcessor();
            List<CampaignAnalytics> aggregated = processor.aggregateCampaigns(allCampaigns);
            System.out.println("[INFO] Successfully aggregated " + aggregated.size() + " unique campaigns");
            System.out.println();

            // Generate top 10 by CTR
            System.out.println("[INFO] Generating top 10 campaigns by CTR...");
            List<CampaignAnalytics> top10Ctr = processor.getTop10ByCTR(aggregated);
            String ctrOutputPath = outputDir + (outputDir.endsWith("/") ? "" : "/") + "top10_ctr.csv";
            CsvWriter.writeCsv(ctrOutputPath, top10Ctr);
            System.out.println("[INFO] Top 10 CTR results written to: " + ctrOutputPath);
            System.out.println();

            // Generate top 10 by CPA (exclude zero conversions)
            System.out.println("[INFO] Generating top 10 campaigns by CPA (excluding zero conversions)...");
            List<CampaignAnalytics> top10Cpa = processor.getTop10ByCPA(aggregated);
            String cpaOutputPath = outputDir + (outputDir.endsWith("/") ? "" : "/") + "top10_cpa.csv";
            CsvWriter.writeCsv(cpaOutputPath, top10Cpa);
            System.out.println("[INFO] Top 10 CPA results written to: " + cpaOutputPath);
            System.out.println();

            // Print summary statistics
            printSummary(aggregated, top10Ctr, top10Cpa);

            long endTime = System.currentTimeMillis();
            System.out.println("[INFO] Processing completed successfully in " + (endTime - startTime) + "ms");

        } catch (Exception e) {
            System.err.println("[ERROR] Processing failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Print summary statistics to console
     */
    private static void printSummary(List<CampaignAnalytics> allCampaigns,
                                     List<CampaignAnalytics> top10Ctr,
                                     List<CampaignAnalytics> top10Cpa) {
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("ANALYTICS SUMMARY");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("Total unique campaigns: " + allCampaigns.size());

        // Calculate totals
        long totalImpressions = allCampaigns.stream().mapToLong(CampaignAnalytics::getTotalImpressions).sum();
        long totalClicks = allCampaigns.stream().mapToLong(CampaignAnalytics::getTotalClicks).sum();
        double totalSpend = allCampaigns.stream().mapToDouble(CampaignAnalytics::getTotalSpend).sum();
        long totalConversions = allCampaigns.stream().mapToLong(CampaignAnalytics::getTotalConversions).sum();

        System.out.println("Total impressions: " + String.format("%,d", totalImpressions));
        System.out.println("Total clicks: " + String.format("%,d", totalClicks));
        System.out.println("Total spend: $" + String.format("%.2f", totalSpend));
        System.out.println("Total conversions: " + String.format("%,d", totalConversions));

        double overallCtr = totalImpressions > 0 ? (double) totalClicks / totalImpressions : 0;
        double overallCpa = totalConversions > 0 ? totalSpend / totalConversions : 0;
        System.out.println("Overall CTR: " + String.format("%.4f", overallCtr));
        System.out.println("Overall CPA: $" + String.format("%.2f", overallCpa));
        System.out.println();

        System.out.println("Top Campaign by CTR: " + (top10Ctr.isEmpty() ? "N/A" : top10Ctr.get(0).getCampaignId() + " (" + String.format("%.4f", top10Ctr.get(0).getCtr()) + ")"));
        System.out.println("Top Campaign by CPA: " + (top10Cpa.isEmpty() ? "N/A" : top10Cpa.get(0).getCampaignId() + " ($" + String.format("%.2f", top10Cpa.get(0).getCpa()) + ")"));
        System.out.println("═══════════════════════════════════════════════════════════════");
    }
}
