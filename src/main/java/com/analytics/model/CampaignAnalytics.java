package com.analytics.model;

/**
 * Represents aggregated analytics for a campaign
 * Contains calculated metrics like CTR and CPA
 */
public class CampaignAnalytics {
    private String campaignId;
    private long totalImpressions;
    private long totalClicks;
    private double totalSpend;
    private long totalConversions;
    private double ctr;  // Click-Through Rate
    private Double cpa;  // Cost Per Acquisition (null if conversions = 0)

    public CampaignAnalytics(String campaignId, long totalImpressions, long totalClicks,
                            double totalSpend, long totalConversions) {
        this.campaignId = campaignId;
        this.totalImpressions = totalImpressions;
        this.totalClicks = totalClicks;
        this.totalSpend = totalSpend;
        this.totalConversions = totalConversions;
        calculateMetrics();
    }

    /**
     * Calculate CTR and CPA metrics
     */
    private void calculateMetrics() {
        // Calculate CTR: clicks / impressions
        this.ctr = totalImpressions > 0 ? (double) totalClicks / totalImpressions : 0.0;

        // Calculate CPA: spend / conversions (null if no conversions)
        this.cpa = totalConversions > 0 ? totalSpend / totalConversions : null;
    }

    // Getters
    public String getCampaignId() {
        return campaignId;
    }

    public long getTotalImpressions() {
        return totalImpressions;
    }

    public long getTotalClicks() {
        return totalClicks;
    }

    public double getTotalSpend() {
        return totalSpend;
    }

    public long getTotalConversions() {
        return totalConversions;
    }

    public double getCtr() {
        return ctr;
    }

    public Double getCpa() {
        return cpa;
    }

    @Override
    public String toString() {
        return "CampaignAnalytics{" +
                "campaignId='" + campaignId + '\'' +
                ", totalImpressions=" + totalImpressions +
                ", totalClicks=" + totalClicks +
                ", totalSpend=" + totalSpend +
                ", totalConversions=" + totalConversions +
                ", ctr=" + String.format("%.4f", ctr) +
                ", cpa=" + (cpa != null ? String.format("%.2f", cpa) : "N/A") +
                '}';
    }
}
