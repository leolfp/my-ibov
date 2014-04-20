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

    static List load(File file){
        def ret = []

        ZipFile zip = new ZipFile(file, ZipFile.OPEN_READ)
        try {
            ZipEntry entry = zip.entries().nextElement()
            BufferedReader br = new BufferedReader(new InputStreamReader(zip.getInputStream(entry)))
            MatchedRecord res
            while ((res = fileFormat.getNextRecord(br)) != null) {
                ret.add(res.getBean(res.recordName))
            }
        } catch(e) {
            e.printStackTrace(System.err)
        } finally {
            zip.close()
        }
        return ret
    }
}
