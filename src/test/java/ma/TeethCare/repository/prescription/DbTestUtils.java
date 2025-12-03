package ma.TeethCare.repository.prescription;

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
            st.execute("ALTER TABLE prescription AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE ordonnance AUTO_INCREMENT = 1");
            st.execute("ALTER TABLE medicament AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO medicament (id, nomCommercial, principeActif, forme, dosage, prixUnitaire, remboursable)
                VALUES
                    (1, 'Paracetamol', 'Paracetamol', 'Comprimé', '500mg', 15.00, 1),
                    (2, 'Amoxicilline', 'Amoxicilline', 'Comprimé', '1g', 25.00, 1);
            """);

            st.executeUpdate("""
                INSERT INTO ordonnance (id, dateOrdonnance)
                VALUES (1, '2025-02-01');
            """);

            st.executeUpdate("""
                INSERT INTO prescription (quantite, posologie, dureeEnJours, ordonnance_id, medicament_id)
                VALUES
                    (2, '2 fois par jour', 5, 1, 1),
                    (1, '3 fois par jour', 7, 1, 2),
                    (3, '1 fois par jour', 10, 1, 1);
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
