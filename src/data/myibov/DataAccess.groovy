package data.myibov

import javax.persistence.Persistence

/**
 * Created by leonardo on 4/11/14.
 */
class DataAccess {
    static factory = Persistence.createEntityManagerFactory("MyIbovStore")
    static manager = factory.createEntityManager()

    static void store(List<Quote> quotes){
        manager.transaction.begin()
        quotes.each { manager.persist it }
        manager.transaction.commit()
    }
}
