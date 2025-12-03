package ma.TeethCare.repository.medicament;

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
            st.executeUpdate("DELETE FROM medicament");
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
                INSERT INTO medicament (id, nomCommercial, principeActif, forme, dosage, type, remboursable, prixUnitaire, description)
                VALUES
                    (1, 'Paracetamol 500mg', 'Paracetamol', 'Comprimé', '500mg', 'Analgésique', 1, 15.00, 'Analgésique antipyrétique'),
                    (2, 'Amoxicilline 1g', 'Amoxicilline', 'Comprimé', '1g', 'Antibiotique', 1, 25.00, 'Antibiotique beta-lactamine'),
                    (3, 'Ibuprofène 200mg', 'Ibuprofène', 'Comprimé', '200mg', 'Anti-inflammatoire', 1, 12.00, 'AINS');
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
