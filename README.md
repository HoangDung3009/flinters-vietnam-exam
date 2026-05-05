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

### Library Used
- OpenCSV

### Benchmarks

Typical performance on standard hardware:
- 1M records: ~500ms
- 10M records: ~5s
- Processing time for 1GB File: 9256ms

