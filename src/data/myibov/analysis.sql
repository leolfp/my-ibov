-- Stock and options - choose quotes on where clause
SELECT
  codNeg,
  data,
  coalesce(preExe, 0)          AS 'preExe',
  preAbe,
  preMax,
  preMed,
  preMin,
  preUlt,
  volTot,
  coalesce(preExe, 0) + preUlt AS 'valor'
FROM quote
WHERE ((codBdi = 78 AND nomRes = 'PETR' AND datVen = '2015-10-19') OR (codNeg = 'PETR4'))
      AND data >= DATE_ADD(NOW(), INTERVAL -90 DAY);


-- Options volumes query, by expiration date
SELECT
  nomRes,
  codNeg,
  datVen,
  volTot
FROM quote
WHERE (codBdi = 78) AND (data = (SELECT max(data) FROM quote));


-- Candles query - choose quote on where clause
SELECT
  data,
  volTot,
  preAbe,
  preMax,
  preMin,
  preUlt
FROM quote
WHERE codNeg = 'PETR4' AND
      data >= DATE_ADD(NOW(), INTERVAL -90 DAY) AND
      data <= NOW();


-- All options series from now - choose quote on where clause
SELECT DISTINCT
  datVen
FROM quote
WHERE codBdi = 78 AND
      nomRes = 'PETR' AND
      datVen > NOW()
ORDER BY datVen;


-- Next option series - choose quote on where clause
SELECT
  MIN(datVen)
FROM quote
WHERE codBdi = 78 AND
      nomRes = 'PETR' AND
      datVen > NOW();


-- All traded options for given series - choose quote and series on where clause
SELECT DISTINCT
  codNeg,
  right(codNeg, length(codNeg)-(length(nomRes)+1)) AS 'codOpc',
  preExe
FROM quote
WHERE codBdi = 78 AND
      nomRes = 'PETR' AND
      datVen = '2015-10-19'
ORDER BY preExe;


-- All options for given series traded last business day - choose quote and series on where clause
SELECT
  codNeg,
  right(codNeg, length(codNeg)-(length(nomRes)+1)) AS 'codOpc',
  preExe,
  volTot
FROM quote
WHERE codBdi = 78 AND
      nomRes = 'PETR' AND
      datVen = '2015-10-19' AND
      data = (SELECT MAX(data) FROM quote)
ORDER BY preExe;


-- Variance and stddev
SELECT
  codNeg,
  sum(volTot),
  min(preMin),
  max(preMax),
  ((max(preMax)/min(preMin))-1)*100   AS 'amplitude',
  stddev_samp(preUlt)*100             AS 'desvio'
FROM quote
WHERE data >= DATE_ADD(NOW(), INTERVAL -90 DAY) AND
      codBdi = 02
GROUP BY codNeg
ORDER BY 2 DESC;