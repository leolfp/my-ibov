package biz.myibov

// Use esse script para atualizar os dados de forma incremental
def files = LoadPlanner.listUpdateDownloads()
def latest = Downloader.download(files)
LoadPlanner.saveLatestDay(latest)

