package ma.TeethCare.repository.ordonnance;

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
            st.executeUpdate("DELETE FROM prescription");
            st.executeUpdate("DELETE FROM ordonnance");
            st.executeUpdate("DELETE FROM medicament");
            st.executeUpdate("DELETE FROM consultation");
            st.executeUpdate("DELETE FROM patient");
            st.executeUpdate("DELETE FROM medecin");
            st.executeUpdate("DELETE FROM staff");
            st.executeUpdate("DELETE FROM utilisateur");
            st.executeUpdate("DELETE FROM role");
            st.execute("ALTER TABLE ordonnance AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE medicament AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE consultation AUTO_INCREMENT = 1");
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

            st.executeUpdate("INSERT INTO role (libelle) VALUES ('MEDECIN')");

            st.executeUpdate("""
                INSERT INTO patient (id, nom, prenom, dateNaissance, sexe, telephone, assurance)
                VALUES (1, 'Amal', 'Zahra', '1995-05-12', 'Femme', '0611111111', 'CNSS');
            """);

            st.executeUpdate("""
                INSERT INTO utilisateur (id, nom, prenom, email, tele, sexe, dateNaissance, role_id)
                VALUES (1, 'Bernard', 'Luc', 'luc@example.com', '0612345678', 'Homme', '1985-03-10',
                     (SELECT id FROM role WHERE libelle = 'MEDECIN'));
            """);

            st.executeUpdate("INSERT INTO staff (id, salaire, dateRecrutement) VALUES (1, 8000.00, '2020-05-15')");
            st.executeUpdate("INSERT INTO medecin (id, specialite) VALUES (1, 'Dentiste')");

            st.executeUpdate("""
                INSERT INTO consultation (date, motif, statut, patient_id, medecin_id)
                VALUES ('2025-02-01', 'Douleur', 'Terminée', 1, 1);
            """);

            st.executeUpdate("""
                INSERT INTO medicament (id, nomCommercial, principeActif, forme, dosage, prixUnitaire, remboursable)
                VALUES
                    (1, 'Paracetamol 500mg', 'Paracetamol', 'Comprimé', '500mg', 15.00, 1),
                    (2, 'Amoxicilline 1g', 'Amoxicilline', 'Comprimé', '1g', 25.00, 1);
            """);

            st.executeUpdate("""
                INSERT INTO ordonnance (dateOrdonnance, consultation_id)
                VALUES
                    ('2025-02-01', 1),
                    ('2025-02-02', 1),
                    ('2025-02-03', 1);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
