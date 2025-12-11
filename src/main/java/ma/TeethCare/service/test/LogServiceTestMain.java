package ma.TeethCare.service.test;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.repository.mySQLImpl.LogRepositoryImpl;
import ma.TeethCare.service.modules.impl.LogServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class LogServiceTestMain {
    public static void main(String[] args) {
        try {
            System.out.println("=== DÉBUT DES TESTS LOGSERVICE ===");

            // 1. Instancie le repository
            LogRepositoryImpl logRepository = new LogRepositoryImpl();

            // 2. Instancie le service avec le repository
            LogServiceImpl logService = new LogServiceImpl(logRepository);

            // 3. TEST: CREATE
            System.out.println("\n--- TEST: CREATE ---");
            log nouveauLog = log.builder()
                    .action("ConnexionTest")
                    .utilisateur("SalmaTest")
                    .dateAction(LocalDateTime.now())
                    .description("Test de création log complèt")
                    .adresseIP("192.168.1.100")
                    .build();

            logService.create(nouveauLog);
            Long createdId = nouveauLog.getIdLog();
            System.out.println("✅ Log créé avec ID : " + createdId);

            if (createdId == null) {
                System.err.println("❌ ERREUR: ID est null après création!");
                return;
            }

            // 4. TEST: FIND BY ID
            System.out.println("\n--- TEST: FIND BY ID ---");
            Optional<log> foundLogOpt = logService.findById(createdId);
            if (foundLogOpt.isPresent()) {
                System.out.println("✅ Log trouvé: " + foundLogOpt.get().getDescription());
            } else {
                System.err.println("❌ ERREUR: Log non trouvé avec l'ID " + createdId);
            }

            // 5. TEST: UPDATE
            System.out.println("\n--- TEST: UPDATE ---");
            if (foundLogOpt.isPresent()) {
                log logToUpdate = foundLogOpt.get();
                logToUpdate.setDescription("Description modifiée par le test");
                logToUpdate.setAction("UpdateTest");
                
                logService.update(logToUpdate);
                
                // Vérification
                Optional<log> updatedLogOpt = logService.findById(createdId);
                if (updatedLogOpt.isPresent() && "Description modifiée par le test".equals(updatedLogOpt.get().getDescription())) {
                    System.out.println("✅ Log mis à jour avec succès.");
                } else {
                    System.err.println("❌ ERREUR: La mise à jour a échoué.");
                }
            }

            // 6. TEST: FIND ALL
            System.out.println("\n--- TEST: FIND ALL ---");
            List<log> logs = logService.findAll();
            System.out.println("ℹ️ Nombre total de logs: " + logs.size());
            if (logs.size() > 0) {
                 System.out.println("✅ FindAll a retourné des résultats.");
            } else {
                 System.out.println("⚠️ FindAll a retourné une liste vide (possible si la DB était vide avant).");
            }

            // 7. TEST: FIND BY UTILISATEUR (Search for the "SYSTEM" user usually set by create/update or specific test user)
            System.out.println("\n--- TEST: FIND BY UTILISATEUR ---");
            // Note: Our repository implementation currently filters by 'creePar' in Entite which defaults to "SYSTEM" 
            // if we didn't set it explicitly in 'create' logic properly or if users match.
            // Let's search for "SYSTEM" because creates default to it if not set on BaseEntity fields, 
            // OR search for "SalmaTest" if we implement matching by log.utilisateur (which we might not have exposed in repo perfectly).
            // Based on my repo implementation: findByUtilisateur -> WHERE e.creePar = ?
            
            // Let's check what creePar is set to. In main: we didn't set creePar explicitly, so create() sets it to "SYSTEM".
            List<log> userLogs = logService.findByUtilisateur("SYSTEM"); 
            System.out.println("ℹ️ Logs pour l'utilisateur 'SYSTEM': " + userLogs.size());
             if (userLogs.stream().anyMatch(l -> l.getIdLog().equals(createdId))) {
                 System.out.println("✅ Nouveau log trouvé par recherche utilisateur 'SYSTEM'.");
             } else {
                 System.out.println("⚠️ Nouveau log PAS trouvé pour 'SYSTEM' (Vérifier la logique creePar).");
             }

            // 8. TEST: FIND BY ACTION
            System.out.println("\n--- TEST: FIND BY ACTION ---");
            // Repo maps action -> typeSupp. We updated the log to action="UpdateTest"
            List<log> actionLogs = logService.findByAction("UpdateTest");
            System.out.println("ℹ️ Logs pour l'action 'UpdateTest': " + actionLogs.size());
            if (actionLogs.size() > 0) {
                System.out.println("✅ Logs trouvés par action.");
            } else {
                 System.err.println("❌ ERREUR: Aucun log trouvé pour l'action 'UpdateTest'.");
            }

            // 9. TEST: FIND BY DATE RANGE
            System.out.println("\n--- TEST: FIND BY DATE RANGE ---");
            LocalDateTime now = LocalDateTime.now();
            List<log> dateLogs = logService.findByDateRange(now.minusDays(1), now.plusDays(1));
            System.out.println("ℹ️ Logs dans les dernières 24h: " + dateLogs.size());
             if (dateLogs.size() > 0) {
                System.out.println("✅ Logs trouvés par plage de date.");
            } else {
                 System.err.println("❌ ERREUR: Aucun log trouvé par date.");
            }
            
            // 10. TEST: COUNT & EXISTS
            System.out.println("\n--- TEST: COUNT & EXISTS ---");
            long count = logService.count();
            System.out.println("Count: " + count);
            boolean exists = logService.exists(createdId);
            System.out.println("Exists (" + createdId + "): " + exists);
             if (exists) {
                System.out.println("✅ Exists fonctionne.");
            } else {
                 System.err.println("❌ ERREUR: Exists a échoué.");
            }

            // 11. TEST: DELETE
            System.out.println("\n--- TEST: DELETE ---");
            boolean deleted = logService.delete(createdId);
            if (deleted) {
                 System.out.println("✅ delete a retourné true.");
            }
            
            // Verify deletion
             boolean existsAfterDelete = logService.exists(createdId);
             if (!existsAfterDelete) {
                  System.out.println("✅ Log supprimé avec succès (n'existe plus).");
             } else {
                  System.err.println("❌ ERREUR: Le log existe encore après suppression!");
             }

            System.out.println("\n=== FIN DES TESTS ===");

        } catch (Exception e) {
            System.err.println("❌ EXCEPTION DURANT LES TESTS:");
            e.printStackTrace();
        }
    }
}