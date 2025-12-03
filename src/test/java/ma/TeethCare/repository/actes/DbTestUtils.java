package ma.TeethCare.repository.actes;

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
            st.executeUpdate("DELETE FROM actes");
            st.execute("ALTER TABLE actes AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO actes (categorie, nom, description, prix, code)
                VALUES
                    ('Consultation', 'Consultation Générale', 'Consultation standard', 300.00, 'ACT001'),
                    ('Imagerie', 'Radio panoramique', 'Examen radiologique complet', 500.00, 'ACT002'),
                    ('Détartrage', 'Détartrage simple', 'Nettoyage des dents', 200.00, 'ACT003'),
                    ('Obturation', 'Obturation résine', 'Traitement de carie', 400.00, 'ACT004'),
                    ('Détartrage', 'Détartrage avec fluor', 'Nettoyage + traitement', 250.00, 'ACT005');
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
