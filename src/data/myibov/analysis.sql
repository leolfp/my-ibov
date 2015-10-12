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


-- Variance and stddev in latest 90 days, for stock only
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


-- Hyphotesis tests
SET @r1=0;
SET @r2=0;
SELECT
  t1.preUlt < t2.preAbe AS abriuEmAlta,
  t2.preAbe < t2.preUlt AS subiuAlemDaAbertura,
  t1.preUlt < t2.preUlt AS fechouEmAlta,
  count(*)
FROM
  (SELECT @r1:=@r1 + 1 AS n, data, preAbe, preUlt FROM quote WHERE codNeg = 'PETR4' ORDER BY data) AS t1,
  (SELECT @r2:=@r2 + 1 AS n, data, preAbe, preUlt FROM quote WHERE codNeg = 'PETR4' ORDER BY data) AS t2
WHERE t1.n + 1 = t2.n
GROUP BY 1, 2, 3;


-- Stock with recent negotiations
SELECT
  codNeg,
  min(data),
  max(data)
FROM
  quote
GROUP BY
  codNeg
HAVING
  max(data) >= adddate(curdate(), -30)
ORDER BY 3 DESC;


-- Variance and std dev
SELECT
  codNeg,
  avg(volTot),
  min(preMin),
  max(preMax),
  ((max(preMax)/min(preMin))-1)*100 as pct,
  stddev_samp(preUlt)*100,
  max(data)
FROM
  quote
WHERE
  data >= adddate(curdate(), -30)
GROUP BY
  codNeg
ORDER BY 2 DESC;


-- Stock history - choose quote on where clause
SET @r1=0;
SET @r2=0;
SELECT
  antes.data          AS start,
  depois.data         AS end,
  antes.preUlt        AS start_value,
  depois.preUlt       AS end_value,
  depois.n - antes.n  AS delta,
  ((depois.preUlt/antes.preUlt) - 1) * 100 AS pct,
  (pow(depois.preUlt/antes.preUlt, 1.0/(depois.n - antes.n)) - 1) * 100 as adu
FROM
  (SELECT @r1:=@r1 + 1 AS n, data, preUlt FROM quote WHERE codNeg = 'PETR4' AND data >= adddate(curdate(), -30) ORDER BY data) AS antes,
  (SELECT @r2:=@r2 + 1 AS n, data, preUlt FROM quote WHERE codNeg = 'PETR4' AND data >= adddate(curdate(), -30) ORDER BY data) AS depois
WHERE
  antes.n < depois.n AND
  depois.n - antes.n > 5
ORDER BY 7 DESC;


-- Top volumes
SELECT
  codNeg
FROM
  quote
WHERE
  data = (SELECT max(data) FROM quote)
ORDER BY
  volTot DESC
LIMIT 50;

-- Best week history up (DESC) or down (ASC)
SELECT
  antes.codNeg,
  antes.data          AS start,
  depois.data         AS end,
  antes.preUlt        AS start_value,
  depois.preUlt       AS end_value,
  datediff(depois.data, antes.data) AS delta,
  ((depois.preUlt/antes.preUlt) - 1) * 100 AS pct,
  (pow(depois.preUlt/antes.preUlt, 1.0/datediff(depois.data, antes.data)) - 1) * 100 AS ad
FROM
  quote AS antes,
  quote AS depois,
  (SELECT codNeg FROM quote WHERE data = (SELECT max(data) FROM quote) ORDER BY volTot DESC LIMIT 50) AS top
WHERE
  antes.codNeg = depois.codNeg AND
  top.codNeg = antes.codNeg AND
  adddate(antes.data, 5) < depois.data AND
  antes.data >= adddate(curdate(), -30) AND
  depois.data >= adddate(curdate(), -7)
ORDER BY 8 DESC;
