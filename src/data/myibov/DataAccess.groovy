package data.myibov

import javax.persistence.Persistence

/**
 * Created by leonardo on 4/11/14.
 */
class DataAccess {
    def factory
    def manager

    DataAccess(){
        factory = Persistence.createEntityManagerFactory 'MyIbovStore'
        manager = factory.createEntityManager()
    }

    boolean store(List quotes){
        try {
            manager.transaction.begin()
            quotes.each { if(it instanceof Quote) manager.persist it }
            manager.transaction.commit()
            return true;
        } catch(e) {
            e.printStackTrace System.err
            return false;
        }
    }
}
