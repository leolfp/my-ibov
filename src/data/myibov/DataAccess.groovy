package data.myibov

import groovy.sql.Sql

/**
 * Created by leonardo on 4/11/14.
 */
class DataAccess {
    Sql sql

    DataAccess (String database, String user, String password){
        sql = Sql.newInstance("jdbc:mysql://localhost/${database}", user, password, 'com.mysql.jdbc.Driver')
    }

    DataAccess(){
        DataAccess('myibov', 'stock', 'try123')
    }


}
