## AI prompt to build console application (using Github Copilot)

### How you break down problems?
I divided problem into 3 part:
1. Input part: Read CSV file, parse records and validate data.
2. Process part: Define necessary column to aggregate, calculate CTR & CPA, sort and rank.
3. Output part: Write CSV files.

### My Prompt
For example, give a CSV file with structures below:
```csv
campaign_id,date,impressions,clicks,spend,conversions
CMP001,2025-01-01,12000,300,45.50,12
CMP002,2025-01-01,8000,120,28.00,4
```
Build a console application using Java to process the CSV file and export aggregated analytics 
with requirements below:
1. Aggregate data by campaign_id
   - total_impressions
   - total_clicks
   - total_spend
   - total_conversions
   - CTR = total_clicks / total_impressions
   - CPA = total_spend / total_conversions (If conversions = 0, ignore or return null for CPA)
2. Generate two output files
   - Rank top 10 campaigns with the highest CTR - create output file as as CSV format
   Expected output format (top10_ctr.csv) with columns below:
     - campaign_id	
     - total_impressions	
     - total_clicks	
     - total_spend	
     - total_conversions	
     - CTR	
     - CPA
   - Rank top 10 campaigns with the lowest CPA - create output file as CSV format (Exclude campaigns with zero conversions).
   Expected output format (top10_cpa.csv) with columns below:
     - campaign_id	
     - total_impressions	
     - total_clicks	
     - total_spend	
     - total_conversions	
     - CTR	
     - CPA 

Note: Technical Requirements
     - Try to handle large datasets (> 1M records) with good performance and memory optimization using Java.
     - The program should be runnable via CLI with file path input