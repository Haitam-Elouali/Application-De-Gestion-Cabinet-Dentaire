package ma.TeethCare.repository.utilisateur;

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
            st.executeUpdate("DELETE FROM utilisateur");
            st.executeUpdate("DELETE FROM role");
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

            st.executeUpdate("INSERT INTO role (libelle) VALUES ('ADMIN'), ('MEDECIN'), ('SECRETAIRE')");

            st.executeUpdate("""
                INSERT INTO utilisateur (nom, prenom, email, tele, sexe, dateNaissance, role_id)
                VALUES
                    ('Dupont', 'Michel', 'michel@example.com', '0612345678', 'Homme', '1980-03-10',
                     (SELECT id FROM role WHERE libelle = 'ADMIN')),
                    ('Bernard', 'Luc', 'luc@example.com', '0687654321', 'Homme', '1985-07-22',
                     (SELECT id FROM role WHERE libelle = 'MEDECIN')),
                    ('Lemoine', 'Julie', 'julie@example.com', '0611223344', 'Femme', '1995-01-01',
                     (SELECT id FROM role WHERE libelle = 'SECRETAIRE'));
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
