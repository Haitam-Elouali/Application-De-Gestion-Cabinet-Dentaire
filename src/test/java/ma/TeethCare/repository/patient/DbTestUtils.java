package ma.TeethCare.repository.patient;

import ma.TeethCare.repository.common.JdbcConfig;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DbTestUtils {
    private DbTestUtils() {}

    /** Nettoie les tables de patient dans l'ordre FK (jointure -> feuilles) */
    public static void cleanAll() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.execute("SET FOREIGN_KEY_CHECKS = 0");
            st.executeUpdate("DELETE FROM patient_antecedent");
            st.executeUpdate("DELETE FROM patient_medecin");
            st.executeUpdate("DELETE FROM patient_secretaire");
            st.executeUpdate("DELETE FROM patient");
            st.executeUpdate("DELETE FROM antecedants");
            st.execute("ALTER TABLE patient AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE antecedants AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    /** Seed complet : 6 patients et 12 antécédents avec liaisons */
    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            // Antécédents (12 au total)
            st.executeUpdate("""
                INSERT INTO antecedants (nom, categorie, niveauDeRisque) VALUES
                ('Allergie à la pénicilline', 'ALLERGIE', 'CRITIQUE'),
                ('Allergie au latex', 'ALLERGIE', 'ELEVE'),
                ('Allergie aux anesthésiques locaux', 'ALLERGIE', 'CRITIQUE'),
                ('Diabète de type 2', 'MALADIE_CHRONIQUE', 'MODERE'),
                ('Hypertension artérielle', 'MALADIE_CHRONIQUE', 'MODERE'),
                ('Asthme', 'MALADIE_CHRONIQUE', 'ELEVE'),
                ('Sous traitement anticoagulant', 'CONTRE_INDICATION', 'ELEVE'),
                ('Grossesse', 'CONTRE_INDICATION', 'MODERE'),
                ('Prothèse valvulaire cardiaque', 'ANTECEDENT_CHIRURGICAL', 'ELEVE'),
                ('Hépatite B ancienne', 'ANTECEDENT_INFECTIEUX', 'MODERE'),
                ('Tabagisme chronique', 'HABITUDE_DE_VIE', 'MODERE'),
                ('Alcoolisme', 'HABITUDE_DE_VIE', 'ELEVE');
            """);

            // Patients (IDs fixés 1-6)
            st.executeUpdate("""
                INSERT INTO patient 
                (id, nom, prenom, dateNaissance, sexe, telephone, assurance)
                VALUES
                    (1, 'Amal', 'Zahra', '1995-05-12', 'Femme', '0611111111', 'CNSS'),
                    (2, 'Omar', 'Badr', '1989-09-23', 'Homme', '0622222222', 'CNOPS'),
                    (3, 'Nour', 'Chafi', '2000-02-02', 'Femme', '0633333333', 'Autre'),
                    (4, 'Youssef', 'Dari', '1992-11-01', 'Homme', '0644444444', 'Aucune'),
                    (5, 'Hiba', 'Zerouali', '2001-03-14', 'Femme', '0655555555', 'CNSS'),
                    (6, 'Mahdi', 'ElMidaoui', '1990-07-18', 'Homme', '0666666666', 'Autre');
            """);

            // Liaisons Patient-Antécédent (many-to-many)
            st.executeUpdate("INSERT INTO patient_antecedent (patient_id, antecedent_id) VALUES (1, 2), (1, 4)");
            st.executeUpdate("INSERT INTO patient_antecedent (patient_id, antecedent_id) VALUES (2, 5), (2, 11), (2, 2)");
            st.executeUpdate("INSERT INTO patient_antecedent (patient_id, antecedent_id) VALUES (3, 1), (3, 8)");
            st.executeUpdate("INSERT INTO patient_antecedent (patient_id, antecedent_id) VALUES (4, 9)");

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
