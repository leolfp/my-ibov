delimiter $$

CREATE DATABASE `myibov` /*!40100 DEFAULT CHARACTER SET utf8 */$$

CREATE TABLE `quote` (
  `id` bigint(20) NOT NULL,
  `codBdi` varchar(2) DEFAULT NULL,
  `codIsi` varchar(12) DEFAULT NULL,
  `codNeg` varchar(12) DEFAULT NULL,
  `datVen` datetime DEFAULT NULL,
  `data` datetime DEFAULT NULL,
  `disMes` bigint(3) DEFAULT NULL,
  `especi` varchar(10) DEFAULT NULL,
  `fatCot` bigint(7) DEFAULT NULL,
  `indOpc` varchar(1) DEFAULT NULL,
  `modRef` varchar(4) DEFAULT NULL,
  `nomRes` varchar(12) DEFAULT NULL,
  `prazoT` bigint(3) DEFAULT NULL,
  `preAbe` decimal(13,2) DEFAULT NULL,
  `preExe` decimal(13,2) DEFAULT NULL,
  `preMax` decimal(13,2) DEFAULT NULL,
  `preMed` decimal(13,2) DEFAULT NULL,
  `preMin` decimal(13,2) DEFAULT NULL,
  `preOfC` decimal(13,2) DEFAULT NULL,
  `preOfV` decimal(13,2) DEFAULT NULL,
  `preUlt` decimal(13,2) DEFAULT NULL,
  `ptoExe` decimal(13,6) DEFAULT NULL,
  `quaTot` bigint(18) DEFAULT NULL,
  `totNeg` bigint(5) DEFAULT NULL,
  `tpMerc` bigint(5) DEFAULT NULL,
  `volTot` decimal(18,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_QUOTE_CODNEG` (`codNeg`),
  KEY `I_QUOTE_DATA` (`data`),
  KEY `I_QUOTE_CODNEG_DATA` (`codNeg`,`data`),
  KEY `I_QUOTE_NOMRES_CODBDI_DATVEN` (`codBdi`,`nomRes`,`datVen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

