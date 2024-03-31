/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader; 
    //where to calculate the weeky access counts
    private int[] dailyCounts;
    //where to calculate the weeky access counts
    private int[] weeklyCounts;
    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String name) 
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dailyCounts = new int[366];
        // Create the reader to obtain the data.
        reader = new LogfileReader(name);
        weeklyCounts = new int[7];
    }
    
    public class Main {
        public static void main(String[] args) {
            LogfileCreator creator = new LogfileCreator();
            creator.createFile("entries.txt", 8);

            LogAnalyzer analyzer = new LogAnalyzer("entries.txt");
            analyzer.analyzeHourlyData();
            analyzer.printHourlyCounts();
            System.out.println(analyzer.numberOfAccesses());

        }
    }
    

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
     public int busiestHour() {
        int maxCount = 0;
        int busiestHour = 0;
        for(int i = 0; i < hourCounts.length; i++)
            if(hourCounts[i] > maxCount) {
                busiestHour = i; 
                maxCount = hourCounts[i];
            }
        return busiestHour;
    }
    
      public int quietestHour() {
        int minCount = numberOfAccesses();
        int quietestHour = 0;
        for(int i = 0; i < hourCounts.length; i++)
            if(hourCounts[i] < minCount) {
                quietestHour = i;
                minCount = hourCounts[i];
            }
        return quietestHour;
    }

     public int busiestTwoHours() {
        int maxCount = 0;
        int firstOfBusiestHourPair = 0;
        for(int i = 0; i < hourCounts.length/2; i++) {
            int hourPair = hourCounts[i * 2] + hourCounts[i * 2 + 1];
            if (hourPair > maxCount) {
                firstOfBusiestHourPair = i;
            }
        }
        return firstOfBusiestHourPair;
    }
    
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    public void numberOfAccesses()
    {
    }
    }
   
    
