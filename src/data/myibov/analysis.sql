select codNeg, data, coalesce(preExe,0) preExe, preAbe, preMax, preMed, preMin, preUlt, volTot, coalesce(preExe,0) + preUlt valor
from quote
where ((codBdi = 78 and nomRes = 'PETR' and datVen = '2014-05-19') or (codNeg = 'PETR4'))
and data >= '2014-02-01'

