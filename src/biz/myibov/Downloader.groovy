package biz.myibov

/**
 * Created by leonardo on 4/11/14.
 */
class Downloader {
    static ant = new AntBuilder()
    static urlPrefix = 'http://www.bmfbovespa.com.br/InstDados/SerHist/'
    // COTAHIST_A2010.ZIP # year
    // COTAHIST_M032010.ZIP # month
    // COTAHIST_D01042010.ZIP # day
    static downloadFolder = new File('./Hist/downloaded/')

    static Calendar download(List files){
        def latestSuccessful;

        files.eachWithIndex { obj, i ->
            def date = obj[0]
            def fileName = obj[1]
            try {
                ant.get(src: "${urlPrefix}${fileName}", dest: "${downloadFolder.path}/${fileName}", verbose: false, usetimestamp: true)
                latestSuccessful = date
            } catch (e) {
                System.err.println "Error downloading file ${fileName}. Possibly holiday."
            }
        }

        return latestSuccessful
    }
}
