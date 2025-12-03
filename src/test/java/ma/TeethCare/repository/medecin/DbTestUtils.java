package ma.TeethCare.repository.medecin;

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
            st.executeUpdate("DELETE FROM patient_medecin");
            st.executeUpdate("DELETE FROM medecin");
            st.executeUpdate("DELETE FROM staff");
            st.executeUpdate("DELETE FROM utilisateur");
            st.executeUpdate("DELETE FROM role");
            st.execute("ALTER TABLE medecin AUTO_INCREMENT = 1");
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

            // Roles
            st.executeUpdate("INSERT INTO role (libelle) VALUES ('MEDECIN'), ('ADMIN')");

            // Utilisateurs/Staff/Medecins
            st.executeUpdate("""
                INSERT INTO utilisateur (id, nom, prenom, email, tele, sexe, dateNaissance, role_id)
                VALUES
                    (1, 'Bernard', 'Luc', 'luc.bernard@example.com', '0612345678', 'Homme', '1985-03-10', 
                     (SELECT id FROM role WHERE libelle = 'MEDECIN')),
                    (2, 'Dubois', 'Marie', 'marie.dubois@example.com', '0687654321', 'Femme', '1990-07-22',
                     (SELECT id FROM role WHERE libelle = 'MEDECIN')),
                    (3, 'Moreau', 'Pierre', 'pierre.moreau@example.com', '0611223344', 'Homme', '1992-01-15',
                     (SELECT id FROM role WHERE libelle = 'MEDECIN'));
            """);

            st.executeUpdate("""
                INSERT INTO staff (id, salaire, dateRecrutement)
                VALUES
                    (1, 8000.00, '2020-05-15'),
                    (2, 7500.00, '2021-03-20'),
                    (3, 8200.00, '2019-08-10');
            """);

            st.executeUpdate("""
                INSERT INTO medecin (id, specialite)
                VALUES
                    (1, 'Dentiste'),
                    (2, 'Orthodontiste'),
                    (3, 'Implantologue');
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
