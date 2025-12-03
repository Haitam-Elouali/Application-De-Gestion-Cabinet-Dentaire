package ma.TeethCare.repository.cabinetMedicale;

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
            st.executeUpdate("DELETE FROM cabinetMedicale");
            st.execute("ALTER TABLE cabinetMedicale AUTO_INCREMENT = 1");
            st.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Nettoyage BD échoué", e);
        }
    }

    public static void seedFullDataset() {
        try (Connection c = JdbcConfig.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                INSERT INTO cabinetMedicale (id, nomCabinet, adresse, tele, email, logo, instagram, siteWeb, description)
                VALUES
                    (1, 'Cabinet Dental Central', '12 Rue de la Santé, Casablanca', '0522123456', 'info@cabinet.ma', 'logo.png', '@cabinet_dental', 'cabinet.ma', 'Cabinet dentaire moderne'),
                    (2, 'Clinique Ibn Sina', '45 Avenue Hassan II, Rabat', '0537234567', 'contact@ibnsina.ma', 'ibnsina.png', '@ibnsina', 'ibnsina.ma', 'Clinique spécialisée'),
                    (3, 'Dental Plus', '78 Boulevard, Fès', '0535345678', 'hello@dentalplus.ma', 'dp.png', '@dentalplus', 'dentalplus.ma', 'Services dentaires complets');
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Seed complet échoué", e);
        }
    }
}
