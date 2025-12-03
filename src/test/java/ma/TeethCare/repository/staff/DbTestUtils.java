package ma.TeethCare.repository.staff;

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
            st.executeUpdate("DELETE FROM staff");
            st.executeUpdate("DELETE FROM utilisateur");
            st.executeUpdate("DELETE FROM role");
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

            st.executeUpdate("INSERT INTO role (libelle) VALUES ('MEDECIN'), ('SECRETAIRE')");

            st.executeUpdate("""
                INSERT INTO utilisateur (id, nom, prenom, email, tele, sexe, dateNaissance, role_id)
                VALUES
                    (1, 'Bernard', 'Luc', 'luc@example.com', '0612345678', 'Homme', '1985-03-10',
                     (SELECT id FROM role WHERE libelle = 'MEDECIN')),
                    (2, 'Lemoine', 'Julie', 'julie@example.com', '0600112233', 'Femme', '1995-01-01',
                     (SELECT id FROM role WHERE libelle = 'SECRETAIRE'));
            """);

            st.executeUpdate("""
                INSERT INTO staff (id, salaire, dateRecrutement, dateDepart)
                VALUES
                    (1, 8000.00, '2020-05-15', NULL),
                    (2, 3000.00, '2024-01-01', NULL);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
