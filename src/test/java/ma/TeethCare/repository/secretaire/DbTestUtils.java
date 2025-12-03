package ma.TeethCare.repository.secretaire;

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
            st.executeUpdate("DELETE FROM secretaire");
            st.executeUpdate("DELETE FROM staff");
            st.executeUpdate("DELETE FROM utilisateur");
            st.executeUpdate("DELETE FROM role");
            st.execute("ALTER TABLE secretaire AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE staff AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE utilisateur AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE role AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("INSERT INTO role (libelle) VALUES ('SECRETAIRE')");

            st.executeUpdate("""
                INSERT INTO utilisateur (id, nom, prenom, email, tele, sexe, dateNaissance, role_id)
                VALUES
                    (1, 'Lemoine', 'Julie', 'julie@example.com', '0600112233', 'Femme', '1995-01-01',
                     (SELECT id FROM role WHERE libelle = 'SECRETAIRE')),
                    (2, 'Martin', 'Sophie', 'sophie@example.com', '0611223344', 'Femme', '1998-05-10',
                     (SELECT id FROM role WHERE libelle = 'SECRETAIRE'));
            """);

            st.executeUpdate("INSERT INTO staff (id, salaire, dateRecrutement) VALUES (1, 3000.00, '2024-01-01'), (2, 3200.00, '2023-06-15')");

            st.executeUpdate("INSERT INTO secretaire (id, commission) VALUES (1, 150.00), (2, 160.00)");

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
