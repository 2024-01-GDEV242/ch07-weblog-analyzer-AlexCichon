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
    
    private int[] monthCounts;
    
   
    
    private int[] dataValues;
    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String name) 
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dailyCounts = new int[28];
        monthCounts = new int[12];
        
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
    
    public void analyzeDailyData()
    { 
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dailyCounts[day]++;
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
    
    public int busiestDay() {
        int maxCount = 0;
        int busiestDay = 0;
        for(int i = 0; i < weeklyCounts.length; i++)
            if(weeklyCounts[i] > maxCount)
                busiestDay = i;
        return busiestDay;
    }
    
    public int quietestDay() {
        int minCount = numberOfAccesses();
        int quietestDay = 0;
        for(int i = 0; i < weeklyCounts.length; i++)
        if(hourCounts[i] < minCount) {
            quietestDay = i;
            minCount = weeklyCounts[i];
        }
        return quietestDay;
    }
    
    
    
    
    public int[] analyzeWeeklyPatterns() {
        for(int i = 0; i < 52; i++){
            for(int j = 0; j < 7; j++) { 
                weeklyCounts[j] += dailyCounts[i * 7 + j];
            }
        }
        weeklyCounts[0] += dailyCounts[364];
        weeklyCounts[1] += dailyCounts[365];
        return weeklyCounts;
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    public int numberOfAccesses()
    {
        int total = 0;
        for (int i =0; i < hourCounts.length ; i++)
        {
            total += hourCounts[i];
        }
        return total;
    }
    }
   
    
