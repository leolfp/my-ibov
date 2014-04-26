package data.myibov

import javax.persistence.Persistence

/**
 * Created by leonardo on 4/11/14.
 */
class DataAccess {
    def factory

    DataAccess(){
        factory = Persistence.createEntityManagerFactory 'MyIbovStore'
    }

    boolean store(List quotes){
        def manager = factory.createEntityManager()
        try {
            try {
                manager.transaction.begin()
                quotes.each { if (it instanceof Quote) manager.persist it }
                manager.transaction.commit()
                return true
            } catch (e) {
                e.printStackTrace System.err
                return false
            } finally {
                if (manager.transaction.active) manager.transaction.rollback()
            }
        } finally {
            manager.close()
        }
    }
}
