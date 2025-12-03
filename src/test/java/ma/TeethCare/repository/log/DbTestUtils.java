package ma.TeethCare.repository.log;

import ma.TeethCare.repository.common.JdbcConfig;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DbTestUtils {
    private DbTestUtils() {}

    public static void cleanAll() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.execute("SET FOREIGN_KEY_CHECKS = 0");
            st.executeUpdate("DELETE FROM log");
            st.execute("ALTER TABLE log AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO log (idLog, action, utilisateur, dateAction, description, adresseIP)
                VALUES
                    (1, 'LOGIN', 'user1', '2025-01-15 09:00:00', 'Connexion de user1', '192.168.1.100'),
                    (2, 'CREATE', 'user2', '2025-01-15 10:30:00', 'Création de patient', '192.168.1.101'),
                    (3, 'UPDATE', 'user1', '2025-01-15 11:45:00', 'Modification de patient', '192.168.1.100'),
                    (4, 'DELETE', 'admin', '2025-01-15 14:20:00', 'Suppression de consultation', '192.168.1.102'),
                    (5, 'LOGOUT', 'user1', '2025-01-15 17:00:00', 'Déconnexion de user1', '192.168.1.100');
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
