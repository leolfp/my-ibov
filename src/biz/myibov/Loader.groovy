package biz.myibov

import com.blackbear.flatworm.ConfigurationReader
import com.blackbear.flatworm.MatchedRecord

import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * Created by leonardo on 4/13/14.
 */
class Loader {
    static fileFormat = new ConfigurationReader().loadConfigurationFile('./Hist/histFormat.xml')
    static loadFolder = new File('./Hist/loaded/')

    static List load(File file){
        def beans = []

        ZipFile zip = new ZipFile(file, ZipFile.OPEN_READ)
        BufferedReader br
        try {
            ZipEntry entry = zip.entries().nextElement()
            br = new BufferedReader(new InputStreamReader(zip.getInputStream(entry)))
            MatchedRecord res
            while ((res = fileFormat.getNextRecord(br)) != null) {
                beans.add(res.getBean(res.recordName))
            }
        } catch(e) {
            e.printStackTrace(System.err)
        } finally {
            zip.close()
            if (br != null) br.close()
        }
        return beans
    }
}
