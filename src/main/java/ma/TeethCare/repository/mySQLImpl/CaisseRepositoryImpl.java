package ma.TeethCare.repository.modules.caisse.inMemDB_implementation;

import ma.TeethCare.conf.SessionFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class CaisseRepositoryImpl {

    @Override
    protected Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }
}


