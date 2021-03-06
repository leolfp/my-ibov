package data.myibov

import org.apache.openjpa.persistence.jdbc.Index

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery

/**
 * Created by leonardo on 4/13/14.
 */
@Entity(name="Quote")
@NamedQueries([
    @NamedQuery(name="Quote.findAll",
                query="SELECT q FROM Quote q"),
    @NamedQuery(name="Quote.findByCodNeg",
                query="SELECT q FROM Quote q WHERE q.codNeg = :codNeg"),
    @NamedQuery(name="Quote.findByCodNegAndData",
                query="SELECT q FROM Quote q WHERE q.codNeg = :codNeg and q.data = :data")
])
class Quote {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id

    @Column
    @Index
    Date data

    @Column(length=2)
    String codBdi

    @Column(length=12)
    @Index(columnNames = ["codNeg", "data"])
    String codNeg

    @Column(precision=3,scale=0)
    Short tpMerc

    @Column(length=12)
    @Index(columnNames = ["nomRes", "codBdi", "data"])
    String nomRes

    @Column(length=10)
    String especi

    @Column(precision=3,scale=0)
    Short prazoT

    @Column(length=4)
    String modRef

    @Column(precision=13,scale=2)
    BigDecimal preAbe

    @Column(precision=13,scale=2)
    BigDecimal preMax

    @Column(precision=13,scale=2)
    BigDecimal preMin

    @Column(precision=13,scale=2)
    BigDecimal preMed

    @Column(precision=13,scale=2)
    BigDecimal preUlt

    @Column(precision=13,scale=2)
    BigDecimal preOfC

    @Column(precision=13,scale=2)
    BigDecimal preOfV

    @Column(precision=5,scale=0)
    Integer totNeg

    @Column(precision=18,scale=0)
    BigInteger quaTot

    @Column(precision=18,scale=2)
    BigDecimal volTot

    @Column(precision=13,scale=2)
    BigDecimal preExe

    @Column(length=1)
    String indOpc

    @Column
    Date datVen

    @Column(precision=7,scale=0)
    Integer fatCot

    @Column(precision=13,scale=6)
    BigDecimal ptoExe

    @Column(length=12)
    String codIsi

    @Column(precision=3,scale=0)
    Short disMes
}
