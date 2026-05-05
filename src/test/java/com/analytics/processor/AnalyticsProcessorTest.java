package com.analytics.processor;

import com.analytics.model.CampaignAnalytics;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for AnalyticsProcessor
 */
public class AnalyticsProcessorTest {
    private AnalyticsProcessor processor;
    private List<CampaignAnalytics> testCampaigns;

    @Before
    public void setUp() {
        processor = new AnalyticsProcessor();
        testCampaigns = new ArrayList<>();
    }

    @Test
    public void testAggregateCampaigns() {
        // Create test data with duplicate campaign IDs
        testCampaigns.add(new CampaignAnalytics("CMP001", 1000, 50, 100.0, 5));
        testCampaigns.add(new CampaignAnalytics("CMP001", 2000, 100, 150.0, 10));
        testCampaigns.add(new CampaignAnalytics("CMP002", 3000, 90, 200.0, 15));

        List<CampaignAnalytics> aggregated = processor.aggregateCampaigns(testCampaigns);

        assertEquals(2, aggregated.size());

        CampaignAnalytics cmp001 = aggregated.stream()
                .filter(c -> c.getCampaignId().equals("CMP001"))
                .findFirst()
                .orElse(null);
        assertNotNull(cmp001);
        assertEquals(3000, cmp001.getTotalImpressions());
        assertEquals(150, cmp001.getTotalClicks());
        assertEquals(250.0, cmp001.getTotalSpend(), 0.01);
        assertEquals(15, cmp001.getTotalConversions());
    }

    @Test
    public void testCTRCalculation() {
        CampaignAnalytics campaign = new CampaignAnalytics("CMP001", 1000, 50, 100.0, 5);
        double expectedCtr = 50.0 / 1000.0;
        assertEquals(expectedCtr, campaign.getCtr(), 0.0001);
    }

    @Test
    public void testCPACalculation() {
        CampaignAnalytics campaign = new CampaignAnalytics("CMP001", 1000, 50, 100.0, 10);
        double expectedCpa = 100.0 / 10.0;
        assertEquals(expectedCpa, campaign.getCpa(), 0.01);
    }

    @Test
    public void testCPANullWhenZeroConversions() {
        CampaignAnalytics campaign = new CampaignAnalytics("CMP001", 1000, 50, 100.0, 0);
        assertNull(campaign.getCpa());
    }

    @Test
    public void testGetTop10ByCTR() {
        // Create test campaigns with different CTRs
        testCampaigns.add(new CampaignAnalytics("CMP001", 1000, 100, 50.0, 10));  // CTR = 0.10
        testCampaigns.add(new CampaignAnalytics("CMP002", 1000, 50, 50.0, 5));    // CTR = 0.05
        testCampaigns.add(new CampaignAnalytics("CMP003", 1000, 200, 100.0, 20)); // CTR = 0.20
        testCampaigns.add(new CampaignAnalytics("CMP004", 1000, 75, 75.0, 7));    // CTR = 0.075

        List<CampaignAnalytics> top10 = processor.getTop10ByCTR(testCampaigns);

        assertEquals(4, top10.size());
        assertEquals("CMP003", top10.get(0).getCampaignId()); // Highest CTR
        assertEquals("CMP001", top10.get(1).getCampaignId());
        assertEquals("CMP004", top10.get(2).getCampaignId());
        assertEquals("CMP002", top10.get(3).getCampaignId()); // Lowest CTR
    }

    @Test
    public void testGetTop10ByCPA() {
        // Create test campaigns with different CPAs
        testCampaigns.add(new CampaignAnalytics("CMP001", 1000, 100, 100.0, 10));  // CPA = 10.0
        testCampaigns.add(new CampaignAnalytics("CMP002", 1000, 50, 150.0, 10));    // CPA = 15.0
        testCampaigns.add(new CampaignAnalytics("CMP003", 1000, 200, 50.0, 10));    // CPA = 5.0
        testCampaigns.add(new CampaignAnalytics("CMP004", 1000, 75, 0.0, 0));       // CPA = null (0 conversions)

        List<CampaignAnalytics> top10 = processor.getTop10ByCPA(testCampaigns);

        assertEquals(3, top10.size()); // CMP004 excluded (zero conversions)
        assertEquals("CMP003", top10.get(0).getCampaignId()); // Lowest CPA = 5.0
        assertEquals("CMP001", top10.get(1).getCampaignId()); // CPA = 10.0
        assertEquals("CMP002", top10.get(2).getCampaignId()); // CPA = 15.0
    }

    @Test
    public void testTop10CPAExcludesZeroConversions() {
        // Create campaigns, some with zero conversions
        testCampaigns.add(new CampaignAnalytics("CMP001", 1000, 100, 100.0, 0));   // No conversions
        testCampaigns.add(new CampaignAnalytics("CMP002", 1000, 50, 100.0, 10));   // CPA = 10.0
        testCampaigns.add(new CampaignAnalytics("CMP003", 1000, 200, 50.0, 0));    // No conversions

        List<CampaignAnalytics> top10 = processor.getTop10ByCPA(testCampaigns);

        assertEquals(1, top10.size());
        assertEquals("CMP002", top10.get(0).getCampaignId());
    }

    @Test
    public void testEmptyInput() {
        List<CampaignAnalytics> aggregated = processor.aggregateCampaigns(new ArrayList<>());
        assertEquals(0, aggregated.size());

        List<CampaignAnalytics> top10Ctr = processor.getTop10ByCTR(new ArrayList<>());
        assertEquals(0, top10Ctr.size());

        List<CampaignAnalytics> top10Cpa = processor.getTop10ByCPA(new ArrayList<>());
        assertEquals(0, top10Cpa.size());
    }
}
