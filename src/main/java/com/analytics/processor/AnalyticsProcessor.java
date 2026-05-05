package com.analytics.processor;

import com.analytics.model.CampaignAnalytics;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Processes campaign data and generates analytics reports
 * Handles aggregation, sorting, and ranking of campaigns
 */
public class AnalyticsProcessor {

    /**
     * Aggregate campaign records by campaign_id
     * @param records List of individual campaign records
     * @return List of aggregated CampaignAnalytics objects
     */
    public List<CampaignAnalytics> aggregateCampaigns(List<CampaignAnalytics> records) {
        // Use LinkedHashMap to maintain insertion order, or TreeMap for sorted order
        Map<String, AggregationBuffer> aggregationMap = new HashMap<>();

        // Aggregate data
        for (CampaignAnalytics record : records) {
            String campaignId = record.getCampaignId();
            aggregationMap.putIfAbsent(campaignId, new AggregationBuffer());
            
            AggregationBuffer buffer = aggregationMap.get(campaignId);
            buffer.impressions += record.getTotalImpressions();
            buffer.clicks += record.getTotalClicks();
            buffer.spend += record.getTotalSpend();
            buffer.conversions += record.getTotalConversions();
        }

        // Convert to CampaignAnalytics objects
        return aggregationMap.entrySet().stream()
                .map(entry -> new CampaignAnalytics(
                        entry.getKey(),
                        entry.getValue().impressions,
                        entry.getValue().clicks,
                        entry.getValue().spend,
                        entry.getValue().conversions
                ))
                .collect(Collectors.toList());
    }

    /**
     * Get top 10 campaigns sorted by highest CTR (Click-Through Rate)
     * @param campaigns List of aggregated campaigns
     * @return Top 10 campaigns by CTR (descending)
     */
    public List<CampaignAnalytics> getTop10ByCTR(List<CampaignAnalytics> campaigns) {
        return campaigns.stream()
                .sorted((a, b) -> Double.compare(b.getCtr(), a.getCtr()))
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * Get top 10 campaigns sorted by lowest CPA (Cost Per Acquisition)
     * Excludes campaigns with zero conversions
     * @param campaigns List of aggregated campaigns
     * @return Top 10 campaigns by lowest CPA (ascending), excluding zero conversions
     */
    public List<CampaignAnalytics> getTop10ByCPA(List<CampaignAnalytics> campaigns) {
        return campaigns.stream()
                .filter(c -> c.getTotalConversions() > 0)  // Exclude zero conversions
                .sorted(Comparator.comparingDouble(CampaignAnalytics::getCpa))
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * Inner class to hold aggregation buffer data
     */
    private static class AggregationBuffer {
        long impressions = 0;
        long clicks = 0;
        double spend = 0.0;
        long conversions = 0;
    }
}
