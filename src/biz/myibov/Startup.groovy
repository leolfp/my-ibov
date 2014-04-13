package biz.myibov

/**
 * Created by leonardo on 4/13/14.
 */

// Use esse script para a primeira execução
def files = LoadPlanner.listStartupDownloads()
def latest = Downloader.download(files)
LoadPlanner.saveLatestDay(latest)

