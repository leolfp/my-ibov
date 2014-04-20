package biz.myibov

import data.myibov.DataAccess

def dataAccess = new DataAccess()

// Use esse script para atualizar os dados de forma incremental

// Faz download das datas faltantes
print "Downloading additional files... "
def files = LoadPlanner.listUpdateDownloads()
def latest = Downloader.download(files)
if (latest != null) LoadPlanner.saveLatestDay(latest)
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
