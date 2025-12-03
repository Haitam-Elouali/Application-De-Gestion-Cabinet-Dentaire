package ma.TeethCare.repository.agenda;

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
            st.executeUpdate("DELETE FROM agenda");
            st.executeUpdate("DELETE FROM medecin");
            st.executeUpdate("DELETE FROM staff");
            st.executeUpdate("DELETE FROM utilisateur");
            st.executeUpdate("DELETE FROM role");
            st.execute("ALTER TABLE agenda AUTO_INCREMENT = 1");
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

            st.executeUpdate("INSERT INTO role (libelle) VALUES ('MEDECIN')");

            st.executeUpdate("""
                INSERT INTO utilisateur (id, nom, prenom, email, tele, sexe, dateNaissance, role_id)
                VALUES (1, 'Bernard', 'Luc', 'luc@example.com', '0612345678', 'Homme', '1985-03-10',
                     (SELECT id FROM role WHERE libelle = 'MEDECIN'));
            """);

            st.executeUpdate("INSERT INTO staff (id, salaire, dateRecrutement) VALUES (1, 8000.00, '2020-05-15')");
            st.executeUpdate("INSERT INTO medecin (id, specialite) VALUES (1, 'Dentiste')");

            st.executeUpdate("""
                INSERT INTO agenda (mois, annee, joursNonDisponibles, medecin_id)
                VALUES
                    ('JANVIER', 2025, '1,2,3', 1),
                    ('FEVRIER', 2025, '10,15,20', 1),
                    ('MARS', 2025, '5,12', 1);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
