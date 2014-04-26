package biz.myibov

import java.text.SimpleDateFormat

import static java.util.Calendar.*

/**
 * Created by leonardo on 4/11/14.
 */
class LoadPlanner {
    private static firstYearFile = new File('./Hist/firstYear.txt')
    private static latestDayFile = new File('./Hist/latestDay.txt')
    private static formatter = new SimpleDateFormat('yyyyMMdd')

    private static dailyFileMask = 'COTAHIST_D%02d%02d%04d.ZIP'
    private static yearlyFileMask = 'COTAHIST_A%04d.ZIP'

    static List listUpdateDownloads(Calendar latestDay = loadLatestDay(), Calendar today = loadToday()) {
        def fileNames = []
        def day

        latestDay.roll DAY_OF_MONTH, 1

        for(day = latestDay; day.before(today); day.roll(DAY_OF_MONTH, 1))
            if(day.get(DAY_OF_WEEK) != SATURDAY && day.get(DAY_OF_WEEK) != SUNDAY)
                fileNames.add([day.clone(), String.format(dailyFileMask, day.get(DAY_OF_MONTH), day.get(MONTH) + 1, day.get(YEAR))])

        return fileNames
    }

    static List listStartupDownloads(int year = loadFirstYear(), Calendar today = loadToday()){
        def fileNames = []
        def day = Calendar.instance

        for(day.set(year, DECEMBER, 31); day.before(today); day.roll(YEAR, 1))
            fileNames.add([day.clone(), String.format(yearlyFileMask, day.get(YEAR))])

        fileNames.add([today.clone(), String.format(yearlyFileMask, today.get(YEAR))])

        return fileNames
    }

    static saveLatestDay(Calendar day){
        latestDayFile.withWriter { out ->
            out.println formatter.format(day.time)
        }
    }

    static Calendar loadLatestDay(){
        def cal = Calendar.instance
        cal.setTime(formatter.parse(latestDayFile.readLines().first()))
        cal.set HOUR, 19
        return cal
    }

    static int loadFirstYear(){
        return Integer.parseInt(firstYearFile.readLines().first())
    }

    static Calendar loadToday(){
        def today = Calendar.instance
        return today;
    }
}
