package com.analytics.model;

import java.time.LocalDate;

/**
 * Represents a single campaign record from the CSV file
 */
public class CampaignRecord {
    private String campaignId;
    private LocalDate date;
    private long impressions;
    private long clicks;
    private double spend;
    private long conversions;

    public CampaignRecord(String campaignId, LocalDate date, long impressions, 
                         long clicks, double spend, long conversions) {
        this.campaignId = campaignId;
        this.date = date;
        this.impressions = impressions;
        this.clicks = clicks;
        this.spend = spend;
        this.conversions = conversions;
    }

    // Getters
    public String getCampaignId() {
        return campaignId;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getImpressions() {
        return impressions;
    }

    public long getClicks() {
        return clicks;
    }

    public double getSpend() {
        return spend;
    }

    public long getConversions() {
        return conversions;
    }

    @Override
    public String toString() {
        return "CampaignRecord{" +
                "campaignId='" + campaignId + '\'' +
                ", date=" + date +
                ", impressions=" + impressions +
                ", clicks=" + clicks +
                ", spend=" + spend +
                ", conversions=" + conversions +
                '}';
    }
}
