package biz.myibov

import data.myibov.DataAccess

import java.text.SimpleDateFormat

/**
 * Created by Leonardo on 18/05/2014.
 */
class MyIbov {
    public static void main(String[] args) {
        if(args.length == 1 && args[0].equals('startup')) {
            startup();
        } else if(args.length == 1 && args[0].equals('update')){
            update();
        } else if(args.length == 1 && args[0].equals('download')){
            download();
        } else if(args.length == 1 && args[0].equals('load')){
            load();
        } else if(args.length == 2 && args[0].equals('fetch')){
            fetch(args[1]);
        } else {
            println 'Usage: MyIbov { startup | update | download | load | fetch yyyy-mm-dd }'
        }
    }

    static void startup(){
        // Use esse método para a primeira execução
        def files = LoadPlanner.listStartupDownloads()
        def latest = Downloader.download(files)
        LoadPlanner.saveLatestDay(latest)
    }

    static void fetch(String date){
        def df = new SimpleDateFormat('yyyy-MM-dd')
        Calendar c = Calendar.instance
        c.setTime(df.parse(date))
        update(c)
    }

    static Calendar download(Calendar date = null){
        // Faz download das datas faltantes
        println 'Downloading additional files... '
        def files = (date == null) ? LoadPlanner.listUpdateDownloads() : LoadPlanner.getUpdateDownloads(date);
        def latest = Downloader.download(files)
        println 'Done.'
        return latest;
    }

    static void update(Calendar date = null){
        // Use esse método para atualizar os dados de forma incremental
        def latest = download(date);

        load();

        // Marca o último load
        if (date == null && latest != null) LoadPlanner.saveLatestDay(latest)
    }

    static void load(){
        def dataAccess = new DataAccess()

        // Atualiza o banco de dados
        Downloader.downloadFolder.listFiles().each {
            print "Loading quotes from ${it.path}... "
            def quotes = Loader.load(it)
            println "Done. ${quotes.size()} quotes loaded."

            print 'Storing quotes... '
            if(dataAccess.store(quotes))
                Downloader.ant.move(file:it, todir:Loader.loadFolder)
            println 'Done.'
        }
    }

}
