package ma.TeethCare.repository.admin;

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
            st.executeUpdate("DELETE FROM admin");
            st.executeUpdate("DELETE FROM staff");
            st.executeUpdate("DELETE FROM utilisateur");
            st.executeUpdate("DELETE FROM role");
            st.execute("ALTER TABLE admin AUTO_INCREMENT = 1");
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

            st.executeUpdate("INSERT INTO role (libelle) VALUES ('ADMIN')");

            st.executeUpdate("""
                INSERT INTO utilisateur (id, nom, prenom, email, tele, sexe, dateNaissance, role_id)
                VALUES
                    (1, 'Dupont', 'Michel', 'michel.dupont@example.com', '0612345678', 'Homme', '1980-03-10',
                     (SELECT id FROM role WHERE libelle = 'ADMIN')),
                    (2, 'Martin', 'Sophie', 'sophie.martin@example.com', '0687654321', 'Femme', '1985-07-22',
                     (SELECT id FROM role WHERE libelle = 'ADMIN'));
            """);

            st.executeUpdate("INSERT INTO staff (id, salaire, dateRecrutement) VALUES (1, 5000.00, '2020-01-15'), (2, 5500.00, '2019-06-20')");

            st.executeUpdate("""
                INSERT INTO admin (id, permissionAdmin, domaine)
                VALUES
                    (1, 'FULL_ACCESS', 'Gestion globale'),
                    (2, 'READ_ONLY', 'Consultation');
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
