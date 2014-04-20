package data.myibov

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by leonardo on 4/13/14.
 */
@Entity
class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    Date data
    String codBdi
    String codNeg
    Long tpMerc
    String nomRes
    String especi
    Long prazoT
    String modRef
    BigDecimal preAbe
    BigDecimal preMax
    BigDecimal preMin
    BigDecimal preMed
    BigDecimal preUlt
    BigDecimal preOfC
    BigDecimal preOfV
    Long totNeg
    BigInteger quaTot
    BigDecimal volTot
    BigDecimal preExe
    String indOpc
    Date datVen
    Long fatCot
    BigDecimal ptoExe
    String codIsi
    Long disMes
}
