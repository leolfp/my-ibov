package biz.myibov

import data.myibov.DataAccess

/**
 * Created by Leonardo on 18/05/2014.
 */
class MyIbov {
    public static void main(String[] args) {
        if(args.length == 1 && args[0].equals('startup')) {
            startup();
        } else if(args.length == 1 && args[0].equals('update')){
            update();
        } else {
            println('Usage: MyIbov {startup|update}')
        }
    }

    static void startup(){
        // Use esse método para a primeira execução
        def files = LoadPlanner.listStartupDownloads()
        def latest = Downloader.download(files)
        LoadPlanner.saveLatestDay(latest)
    }

    static void update(){
        def dataAccess = new DataAccess()

        // Use esse método para atualizar os dados de forma incremental

        // Faz download das datas faltantes
        println "Downloading additional files... "
        def files = LoadPlanner.listUpdateDownloads()
        def latest = Downloader.download(files)
        println "Done."

        // Atualiza o banco de dados
        Downloader.downloadFolder.listFiles().each {
            print "Loading quotes from ${it.path}... "
            def quotes = Loader.load(it)
            println "Done. ${quotes.size()} quotes loaded."

            print "Storing quotes... "
            if(dataAccess.store(quotes))
                Downloader.ant.move(file:it, todir:Loader.loadFolder)
            println "Done."
        }

        // Marca o último download
        if (latest != null) LoadPlanner.saveLatestDay(latest)
    }
}
