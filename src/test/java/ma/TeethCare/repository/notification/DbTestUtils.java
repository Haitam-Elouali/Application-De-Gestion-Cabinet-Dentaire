package ma.TeethCare.repository.notification;

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
            st.executeUpdate("DELETE FROM utilisateur_notification");
            st.executeUpdate("DELETE FROM notification");
            st.execute("ALTER TABLE notification AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO notification (idNotif, titre, message, dateEnvoi, type, lue)
                VALUES
                    (1, 'Nouveau patient', 'Patient Jean inscrit', '2025-01-10 09:00:00', 'INSCRIPTION', 1),
                    (2, 'RDV confirmé', 'RDV à 14h avec Dr. Dupont', '2025-01-11 10:30:00', 'RDV', 0),
                    (3, 'Facture payée', 'Facture de 500€ payée', '2025-01-12 11:45:00', 'PAIEMENT', 1),
                    (4, 'Résultats prêts', 'Vos résultats d''examen sont prêts', '2025-01-13 14:20:00', 'RESULTAT', 0),
                    (5, 'Rappel consultation', 'Consultation prévue demain', '2025-01-14 17:00:00', 'RAPPEL', 0);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
