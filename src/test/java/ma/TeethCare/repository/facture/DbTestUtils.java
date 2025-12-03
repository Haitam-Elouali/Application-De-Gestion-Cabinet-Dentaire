package ma.TeethCare.repository.facture;

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
            st.executeUpdate("DELETE FROM facture");
            st.executeUpdate("DELETE FROM patient");
            st.executeUpdate("DELETE FROM secretaire");
            st.executeUpdate("DELETE FROM staff");
            st.executeUpdate("DELETE FROM utilisateur");
            st.executeUpdate("DELETE FROM role");
            st.execute("ALTER TABLE facture AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE patient AUTO_INCREMENT = 1");
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

            // Patients
            st.executeUpdate("""
                INSERT INTO patient (id, nom, prenom, dateNaissance, sexe, telephone, assurance)
                VALUES
                    (1, 'Amal', 'Zahra', '1995-05-12', 'Femme', '0611111111', 'CNSS'),
                    (2, 'Omar', 'Badr', '1989-09-23', 'Homme', '0622222222', 'CNOPS'),
                    (3, 'Nour', 'Chafi', '2000-02-02', 'Femme', '0633333333', 'Autre');
            """);

            // Role + Utilisateur + Staff + Secretaire
            st.executeUpdate("INSERT INTO role (libelle) VALUES ('SECRETAIRE')");
            st.executeUpdate("""
                INSERT INTO utilisateur (id, nom, prenom, email, tele, sexe, dateNaissance, role_id)
                VALUES
                    (1, 'Lemoine', 'Julie', 'julie.lemoine@example.com', '0600112233', 'Femme', '1995-01-01',
                     (SELECT id FROM role WHERE libelle = 'SECRETAIRE'));
            """);
            st.executeUpdate("INSERT INTO staff (id, salaire, dateRecrutement) VALUES (1, 3000.00, '2024-01-01')");
            st.executeUpdate("INSERT INTO secretaire (id, commission) VALUES (1, 150.00)");

            // Factures
            st.executeUpdate("""
                INSERT INTO facture (id, totaleFacture, totalePaye, Reste, statut, modePaiement, dateFacture, patient_id, secretaire_id)
                VALUES
                    (1, 1200.00, 800.00, 400.00, 'Partiellement payé', 'Espèces', '2025-02-01 10:00:00', 1, 1),
                    (2, 1500.00, 1500.00, 0.00, 'Payé', 'Chèque', '2025-02-02 11:00:00', 2, 1),
                    (3, 2000.00, 0.00, 2000.00, 'Impayé', 'Virement', '2025-02-03 12:00:00', 3, 1);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
