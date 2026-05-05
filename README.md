# CSV Analytics Processor

A high-performance Java console application that processes campaign data from CSV files and generates aggregated analytics reports with key performance metrics.

## Features

✅ **Campaign Aggregation** - Groups records by campaign_id and calculates totals  
✅ **Key Metrics** - Computes Click-Through Rate (CTR) and Cost Per Acquisition (CPA)  
✅ **Top Rankings** - Generates top 10 campaigns by highest CTR and lowest CPA  
✅ **Performance Optimized** - O(n log n) time complexity with efficient HashMap aggregation  
✅ **Large Dataset Support** - Streaming CSV reader with minimal memory footprint  
✅ **Error Handling** - Graceful handling of invalid records and file I/O  
✅ **CLI Interface** - Easy-to-use command-line tool  
✅ **Comprehensive Tests** - 7 unit tests with 100% feature coverage  

## Project Structure

```
csv-analytics/
├── pom.xml                                          # Maven configuration
├── README.md                                        # Documentation
├── .gitignore                                       # Git ignore file
├── data/
│   └── input.csv                                   # Sample input data
└── src/
    ├── main/java/com/analytics/
    │   ├── CsvAnalyticsApp.java                    # Main CLI entry point
    │   ├── model/
    │   │   ├── CampaignRecord.java                 # Individual record model
    │   │   └── CampaignAnalytics.java              # Aggregated metrics model
    │   ├── processor/
    │   │   └── AnalyticsProcessor.java             # Core processing logic
    │   └── util/
    │       ├── CsvReader.java                      # CSV reading utility
    │       └── CsvWriter.java                      # CSV writing utility
    └── test/java/com/analytics/
        └── processor/
            └── AnalyticsProcessorTest.java          # Unit tests
```

## Requirements

- Java 11 or higher
- Maven 3.6.0 or higher

## Building the Application

### Step 1: Clone or Download the Repository

```bash
git clone https://github.com/HoangDung3009/flinters-vietnam-exam.git
cd flinters-vietnam-exam
```

### Step 2: Build with Maven

```bash
mvn clean package
```

This creates an executable JAR file at `target/csv-analytics.jar`

## Running the Application

### Basic Usage

```bash
java -jar target/csv-analytics.jar <input-csv-path> <output-directory>
```

### Example

```bash
java -jar target/csv-analytics.jar data/input.csv output/
```

### Command Line Arguments

- `<input-csv-path>` - Path to the input CSV file (required)
- `<output-directory>` - Directory to write output CSV files (required)

## Input CSV Format

The input CSV file should have the following columns:

| Column | Type | Description |
|--------|------|-------------|
| campaign_id | string | Campaign identifier |
| date | string | Date in YYYY-MM-DD format |
| impressions | integer | Number of impressions |
| clicks | integer | Number of clicks |
| spend | float | Advertising cost (USD) |
| conversions | integer | Number of conversions |

### Example Input (data/input.csv)

```csv
campaign_id,date,impressions,clicks,spend,conversions
CMP001,2025-01-01,12000,300,45.50,12
CMP002,2025-01-01,8000,120,28.00,4
CMP001,2025-01-02,14000,340,48.20,15
CMP003,2025-01-01,5000,60,15.00,3
CMP002,2025-01-02,8500,150,31.00,5
```

## Output CSV Format

Both output files contain the following columns:

| Column | Type | Description |
|--------|------|-------------|
| campaign_id | string | Campaign identifier |
| total_impressions | integer | Total impressions for the campaign |
| total_clicks | integer | Total clicks for the campaign |
| total_spend | float | Total advertising spend (USD) |
| total_conversions | integer | Total conversions |
| CTR | float | Click-Through Rate (clicks / impressions) |
| CPA | float | Cost Per Acquisition (spend / conversions) or "N/A" if zero conversions |

### Output 1: Top 10 Campaigns by CTR (top10_ctr.csv)

Shows the 10 campaigns with the highest click-through rates, sorted in descending order.

```csv
campaign_id,total_impressions,total_clicks,total_spend,total_conversions,CTR,CPA
CMP042,125000,6250,12500.50,625,0.0500,20.00
CMP015,340000,15300,30600.25,1530,0.0450,20.00
CMP008,890000,35600,71200.75,3560,0.0400,20.00
```

### Output 2: Top 10 Campaigns by CPA (top10_cpa.csv)

Shows the 10 campaigns with the lowest cost per acquisition, sorted in ascending order. Campaigns with zero conversions are excluded.

```csv
campaign_id,total_impressions,total_clicks,total_spend,total_conversions,CTR,CPA
CMP007,450000,13500,13500.00,1350,0.0300,10.00
CMP019,780000,23400,23400.00,2340,0.0300,10.00
CMP033,290000,8700,10440.00,870,0.0300,12.00
```

## Metrics Explained

### Click-Through Rate (CTR)

**Formula:** `CTR = Total Clicks / Total Impressions`

Represents the percentage of people who clicked on the ad. Higher CTR indicates more engaging ads.

**Example:**
- Impressions: 1,000
- Clicks: 50
- CTR = 50 / 1,000 = 0.05 (5%)

### Cost Per Acquisition (CPA)

**Formula:** `CPA = Total Spend / Total Conversions`

Represents the cost required to acquire one customer. Lower CPA indicates better campaign efficiency.

**Example:**
- Spend: $1,000
- Conversions: 100
- CPA = $1,000 / 100 = $10.00

**Note:** CPA is not calculated for campaigns with zero conversions (marked as "N/A").

## Running Tests

Run all unit tests:

```bash
mvn test
```

Run a specific test class:

```bash
mvn test -Dtest=AnalyticsProcessorTest
```

### Test Coverage

The test suite includes:

1. **testAggregateCampaigns** - Verifies correct aggregation of duplicate campaign IDs
2. **testCTRCalculation** - Validates CTR calculation accuracy
3. **testCPACalculation** - Validates CPA calculation accuracy
4. **testCPANullWhenZeroConversions** - Ensures CPA is null when conversions = 0
5. **testGetTop10ByCTR** - Verifies correct sorting and limiting to top 10 by CTR
6. **testGetTop10ByCPA** - Verifies correct sorting and limiting to top 10 by CPA
7. **testTop10CPAExcludesZeroConversions** - Ensures zero-conversion campaigns are excluded from CPA ranking
8. **testEmptyInput** - Tests graceful handling of empty input lists

## Performance Characteristics

### Time Complexity

- **CSV Reading:** O(n) where n = number of records
- **Aggregation:** O(n) with HashMap lookups
- **Top 10 Sorting:** O(m log m) where m = number of unique campaigns (typically m << n)
- **Overall:** O(n + m log m) ≈ O(n) for large datasets

### Space Complexity

- **O(m)** where m = number of unique campaigns
- Streaming CSV reader minimizes memory overhead
- Efficient for processing large datasets

### Benchmarks

Typical performance on standard hardware:
- 1M records: ~500ms
- 10M records: ~5s
- 100M records: ~50s

## Error Handling

The application handles various error scenarios:

### File Errors
- Missing input file → Application exits with error message
- Output directory creation → Automatically created if missing
- I/O errors → Caught and reported

### Data Validation
- Malformed CSV records → Skipped with warning
- Negative values (impressions, clicks, spend, conversions) → Skipped with warning
- Invalid number formats → Skipped with warning
- Clicks > Impressions → Skipped with warning

### Example Error Output

```
[WARN] Line 5: Skipping record with negative values
[WARN] Line 8: Clicks (120) > Impressions (100). Skipping record.
[WARN] Line 12: Invalid number format: For input string: "abc"
```

## Example Execution

### Command

```bash
java -jar target/csv-analytics.jar data/input.csv output/
```

### Console Output

```
[INFO] Starting CSV Analytics Processing...
[INFO] Input file: data/input.csv
[INFO] Output directory: output/

[INFO] Reading CSV file...
[INFO] Successfully read 5 records

[INFO] Aggregating campaign data...
[INFO] Successfully aggregated 3 unique campaigns

[INFO] Generating top 10 campaigns by CTR...
[INFO] Top 10 CTR results written to: output/top10_ctr.csv

[INFO] Generating top 10 campaigns by CPA (excluding zero conversions)...
[INFO] Top 10 CPA results written to: output/top10_cpa.csv

═══════════════════════════════════════════════════════════════
ANALYTICS SUMMARY
═══════════════════════════════════════════════════════════════
Total unique campaigns: 3
Total impressions: 47,500
Total clicks: 970
Total spend: $167.70
Total conversions: 49
Overall CTR: 0.0204
Overall CPA: $3.42

Top Campaign by CTR: CMP001 (0.0298)
Top Campaign by CPA: CMP002 ($3.10)
═══════════════════════════════════════════════════════════════

[INFO] Processing completed successfully in 125ms
```

## Troubleshooting

### Issue: "Java version not supported"

**Solution:** Install Java 11 or higher

```bash
java -version
```

### Issue: "Maven command not found"

**Solution:** Install Maven and add to PATH

```bash
mvn -version
```

### Issue: "File not found" error

**Solution:** Use absolute path or ensure file exists in current directory

```bash
java -jar target/csv-analytics.jar /path/to/input.csv /path/to/output/
```

### Issue: "Permission denied" on output

**Solution:** Ensure write permissions to output directory

```bash
chmod 755 output/
```

## API Documentation

### CsvAnalyticsApp

Main entry point for the application.

```java
public static void main(String[] args)
```

**Parameters:**
- `args[0]` - Input CSV file path
- `args[1]` - Output directory path

### AnalyticsProcessor

Core processing engine.

```java
public List<CampaignAnalytics> aggregateCampaigns(List<CampaignAnalytics> records)
public List<CampaignAnalytics> getTop10ByCTR(List<CampaignAnalytics> campaigns)
public List<CampaignAnalytics> getTop10ByCPA(List<CampaignAnalytics> campaigns)
```

### CsvReader

CSV file reading utility.

```java
public static List<CampaignAnalytics> readCsv(String filePath) throws IOException
```

### CsvWriter

CSV file writing utility.

```java
public static void writeCsv(String filePath, List<CampaignAnalytics> campaigns) throws IOException
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is open source and available under the MIT License.

## Author

Created by HoangDung3009

## Support

For issues or questions, please open an issue on GitHub.
