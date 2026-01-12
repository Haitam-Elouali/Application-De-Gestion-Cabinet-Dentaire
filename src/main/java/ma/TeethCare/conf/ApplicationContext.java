package ma.TeethCare.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
// import ma.TeethCare.mvc.controllers.modules.patient.api.PatientController;
import ma.TeethCare.repository.api.PatientRepository;
import ma.TeethCare.service.modules.agenda.api.agendaService;
import ma.TeethCare.service.modules.agenda.api.rdvService;
import ma.TeethCare.service.modules.patient.api.PatientService;
import ma.TeethCare.service.modules.users.api.adminService;
import ma.TeethCare.service.modules.log.api.logService;
import ma.TeethCare.service.modules.cabinet.api.cabinetMedicaleService;
import ma.TeethCare.service.modules.caisse.api.chargesService;
import ma.TeethCare.service.modules.caisse.api.factureService;
import ma.TeethCare.service.modules.caisse.api.revenuesService;
import ma.TeethCare.service.modules.caisse.api.situationFinanciereService;
import ma.TeethCare.service.modules.dashboard_statistiques.api.statistiqueService;
import ma.TeethCare.service.modules.dossierMedical.api.antecedentService;
import ma.TeethCare.service.modules.users.api.medecinService;
import ma.TeethCare.service.modules.dossierMedical.api.medicamentsService;
import ma.TeethCare.service.modules.notifications.api.notificationService;
import ma.TeethCare.service.modules.dossierMedical.api.ordonnanceService;
import ma.TeethCare.service.modules.dossierMedical.api.prescriptionService;
import ma.TeethCare.service.modules.users.api.roleService;
import ma.TeethCare.service.modules.users.api.secretaireService;
import ma.TeethCare.service.modules.users.api.staffService;
import ma.TeethCare.service.modules.users.api.utilisateurService;
import ma.TeethCare.service.modules.dossierMedical.api.actesService;
import ma.TeethCare.service.modules.dossierMedical.api.certificatService;
import ma.TeethCare.service.modules.dossierMedical.api.consultationService;
import ma.TeethCare.service.modules.dossierMedical.api.dossierMedicaleService;
import ma.TeethCare.service.modules.dossierMedical.api.interventionMedecinService;

public class ApplicationContext {

        private static final Map<Class<?>, Object> context = new HashMap<>();
        private static final Map<String, Object> contextByName = new HashMap<>(); // Ajout d'une deuxième map

        static {
                var configFile = Thread.currentThread().getContextClassLoader()
                                .getResourceAsStream("config/beans.properties");

                if (configFile != null) {
                        Properties properties = new Properties();
                        try {
                                properties.load(configFile);

                                // --- REPOSITORIES ---

                                // Patient
                                String patientRepoClass = properties.getProperty("patientRepo");
                                Class<?> cPatientRepo = Class.forName(patientRepoClass);
                                PatientRepository patientRepository = (PatientRepository) cPatientRepo
                                                .getDeclaredConstructor()
                                                .newInstance();
                                context.put(PatientRepository.class, patientRepository);
                                contextByName.put("patientRepo", patientRepository);

                                // Actes
                                String actesRepoClass = properties.getProperty("actesRepo");
                                Class<?> cActesRepo = Class.forName(actesRepoClass);
                                ma.TeethCare.repository.api.ActesRepository actesRepository = (ma.TeethCare.repository.api.ActesRepository) cActesRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.ActesRepository.class, actesRepository);
                                contextByName.put("actesRepo", actesRepository);

                                // Admin
                                String adminRepoClass = properties.getProperty("adminRepo");
                                Class<?> cAdminRepo = Class.forName(adminRepoClass);
                                ma.TeethCare.repository.api.AdminRepository adminRepository = (ma.TeethCare.repository.api.AdminRepository) cAdminRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.AdminRepository.class, adminRepository);
                                contextByName.put("adminRepo", adminRepository);

                                // Agenda
                                String agendaRepoClass = properties.getProperty("agendaRepo");
                                Class<?> cAgendaRepo = Class.forName(agendaRepoClass);
                                ma.TeethCare.repository.api.AgendaRepository agendaRepository = (ma.TeethCare.repository.api.AgendaRepository) cAgendaRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.AgendaRepository.class, agendaRepository);
                                contextByName.put("agendaRepo", agendaRepository);

                                // Antecedent
                                String antecedentRepoClass = properties.getProperty("antecedentRepo");
                                Class<?> cAntecedentRepo = Class.forName(antecedentRepoClass);
                                ma.TeethCare.repository.api.AntecedentRepository antecedentRepository = (ma.TeethCare.repository.api.AntecedentRepository) cAntecedentRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.AntecedentRepository.class,
                                                antecedentRepository);
                                contextByName.put("antecedentRepo", antecedentRepository);

                                // CabinetMedicale
                                String cabinetRepoClass = properties.getProperty("cabinetMedicaleRepo");
                                Class<?> cCabinetRepo = Class.forName(cabinetRepoClass);
                                ma.TeethCare.repository.api.CabinetMedicaleRepository cabinetRepository = (ma.TeethCare.repository.api.CabinetMedicaleRepository) cCabinetRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.CabinetMedicaleRepository.class,
                                                cabinetRepository);
                                contextByName.put("cabinetMedicaleRepo", cabinetRepository);

                                // Certificat
                                String certificatRepoClass = properties.getProperty("certificatRepo");
                                Class<?> cCertificatRepo = Class.forName(certificatRepoClass);
                                ma.TeethCare.repository.api.CertificatRepository certificatRepository = (ma.TeethCare.repository.api.CertificatRepository) cCertificatRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.CertificatRepository.class,
                                                certificatRepository);
                                contextByName.put("certificatRepo", certificatRepository);

                                // Charges
                                String chargesRepoClass = properties.getProperty("chargesRepo");
                                Class<?> cChargesRepo = Class.forName(chargesRepoClass);
                                ma.TeethCare.repository.api.ChargesRepository chargesRepository = (ma.TeethCare.repository.api.ChargesRepository) cChargesRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.ChargesRepository.class, chargesRepository);
                                contextByName.put("chargesRepo", chargesRepository);

                                // Consultation
                                String consultationRepoClass = properties.getProperty("consultationRepo");
                                Class<?> cConsultationRepo = Class.forName(consultationRepoClass);
                                ma.TeethCare.repository.api.ConsultationRepository consultationRepository = (ma.TeethCare.repository.api.ConsultationRepository) cConsultationRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.ConsultationRepository.class,
                                                consultationRepository);
                                contextByName.put("consultationRepo", consultationRepository);

                                // DossierMedicale
                                String dmRepoClass = properties.getProperty("dossierMedicaleRepo");
                                Class<?> cDmRepo = Class.forName(dmRepoClass);
                                ma.TeethCare.repository.api.DossierMedicaleRepository dmRepository = (ma.TeethCare.repository.api.DossierMedicaleRepository) cDmRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.DossierMedicaleRepository.class, dmRepository);
                                contextByName.put("dossierMedicaleRepo", dmRepository);

                                // Facture
                                String factureRepoClass = properties.getProperty("factureRepo");
                                Class<?> cFactureRepo = Class.forName(factureRepoClass);
                                ma.TeethCare.repository.api.FactureRepository factureRepository = (ma.TeethCare.repository.api.FactureRepository) cFactureRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.FactureRepository.class, factureRepository);
                                contextByName.put("factureRepo", factureRepository);

                                // InterventionMedecin
                                String interventionRepoClass = properties.getProperty("interventionMedecinRepo");
                                Class<?> cInterventionRepo = Class.forName(interventionRepoClass);
                                ma.TeethCare.repository.api.InterventionMedecinRepository interventionRepository = (ma.TeethCare.repository.api.InterventionMedecinRepository) cInterventionRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.InterventionMedecinRepository.class,
                                                interventionRepository);
                                contextByName.put("interventionMedecinRepo", interventionRepository);

                                // Log
                                String logRepoClass = properties.getProperty("logRepo");
                                Class<?> cLogRepo = Class.forName(logRepoClass);
                                ma.TeethCare.repository.api.LogRepository logRepository = (ma.TeethCare.repository.api.LogRepository) cLogRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.LogRepository.class, logRepository);
                                contextByName.put("logRepo", logRepository);

                                // Medecin
                                String medecinRepoClass = properties.getProperty("medecinRepo");
                                Class<?> cMedecinRepo = Class.forName(medecinRepoClass);
                                ma.TeethCare.repository.api.MedecinRepository medecinRepository = (ma.TeethCare.repository.api.MedecinRepository) cMedecinRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.MedecinRepository.class, medecinRepository);
                                contextByName.put("medecinRepo", medecinRepository);

                                // Medicament
                                String medicamentRepoClass = properties.getProperty("medicamentRepo");
                                Class<?> cMedicamentRepo = Class.forName(medicamentRepoClass);
                                ma.TeethCare.repository.api.MedicamentRepository medicamentRepository = (ma.TeethCare.repository.api.MedicamentRepository) cMedicamentRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.MedicamentRepository.class,
                                                medicamentRepository);
                                contextByName.put("medicamentRepo", medicamentRepository);

                                // Notification
                                String notificationRepoClass = properties.getProperty("notificationRepo");
                                Class<?> cNotificationRepo = Class.forName(notificationRepoClass);
                                ma.TeethCare.repository.api.NotificationRepository notificationRepository = (ma.TeethCare.repository.api.NotificationRepository) cNotificationRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.NotificationRepository.class,
                                                notificationRepository);
                                contextByName.put("notificationRepo", notificationRepository);

                                // Ordonnance
                                String ordonnanceRepoClass = properties.getProperty("ordonnanceRepo");
                                Class<?> cOrdonnanceRepo = Class.forName(ordonnanceRepoClass);
                                ma.TeethCare.repository.api.OrdonnanceRepository ordonnanceRepository = (ma.TeethCare.repository.api.OrdonnanceRepository) cOrdonnanceRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.OrdonnanceRepository.class,
                                                ordonnanceRepository);
                                contextByName.put("ordonnanceRepo", ordonnanceRepository);

                                // Prescription
                                String prescriptionRepoClass = properties.getProperty("prescriptionRepo");
                                Class<?> cPrescriptionRepo = Class.forName(prescriptionRepoClass);
                                ma.TeethCare.repository.api.PrescriptionRepository prescriptionRepository = (ma.TeethCare.repository.api.PrescriptionRepository) cPrescriptionRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.PrescriptionRepository.class,
                                                prescriptionRepository);
                                contextByName.put("prescriptionRepo", prescriptionRepository);

                                // Rdv
                                String rdvRepoClass = properties.getProperty("rdvRepo");
                                Class<?> cRdvRepo = Class.forName(rdvRepoClass);
                                ma.TeethCare.repository.api.RdvRepository rdvRepository = (ma.TeethCare.repository.api.RdvRepository) cRdvRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.RdvRepository.class, rdvRepository);
                                contextByName.put("rdvRepo", rdvRepository);

                                // Revenues
                                String revenuesRepoClass = properties.getProperty("revenuesRepo");
                                Class<?> cRevenuesRepo = Class.forName(revenuesRepoClass);
                                ma.TeethCare.repository.api.RevenuesRepository revenuesRepository = (ma.TeethCare.repository.api.RevenuesRepository) cRevenuesRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.RevenuesRepository.class, revenuesRepository);
                                contextByName.put("revenuesRepo", revenuesRepository);

                                // Role
                                String roleRepoClass = properties.getProperty("roleRepo");
                                Class<?> cRoleRepo = Class.forName(roleRepoClass);
                                ma.TeethCare.repository.api.RoleRepository roleRepository = (ma.TeethCare.repository.api.RoleRepository) cRoleRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.RoleRepository.class, roleRepository);
                                contextByName.put("roleRepo", roleRepository);

                                // Secretaire
                                String secretaireRepoClass = properties.getProperty("secretaireRepo");
                                Class<?> cSecretaireRepo = Class.forName(secretaireRepoClass);
                                ma.TeethCare.repository.api.SecretaireRepository secretaireRepository = (ma.TeethCare.repository.api.SecretaireRepository) cSecretaireRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.SecretaireRepository.class,
                                                secretaireRepository);
                                contextByName.put("secretaireRepo", secretaireRepository);

                                // SituationFinanciere
                                String sfRepoClass = properties.getProperty("situationFinanciereRepo");
                                Class<?> cSfRepo = Class.forName(sfRepoClass);
                                ma.TeethCare.repository.api.SituationFinanciereRepository sfRepository = (ma.TeethCare.repository.api.SituationFinanciereRepository) cSfRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.SituationFinanciereRepository.class,
                                                sfRepository);
                                contextByName.put("situationFinanciereRepo", sfRepository);

                                // Staff
                                String staffRepoClass = properties.getProperty("staffRepo");
                                Class<?> cStaffRepo = Class.forName(staffRepoClass);
                                ma.TeethCare.repository.api.StaffRepository staffRepository = (ma.TeethCare.repository.api.StaffRepository) cStaffRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.StaffRepository.class, staffRepository);
                                contextByName.put("staffRepo", staffRepository);

                                // Utilisateur
                                String utilisateurRepoClass = properties.getProperty("utilisateurRepo");
                                Class<?> cUtilisateurRepo = Class.forName(utilisateurRepoClass);
                                ma.TeethCare.repository.api.UtilisateurRepository utilisateurRepository = (ma.TeethCare.repository.api.UtilisateurRepository) cUtilisateurRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.UtilisateurRepository.class,
                                                utilisateurRepository);
                                contextByName.put("utilisateurRepo", utilisateurRepository);

                                // Statistique
                                String statistiqueRepoClass = properties.getProperty("statistiqueRepo");
                                Class<?> cStatistiqueRepo = Class.forName(statistiqueRepoClass);
                                ma.TeethCare.repository.api.StatistiqueRepository statistiqueRepository = (ma.TeethCare.repository.api.StatistiqueRepository) cStatistiqueRepo
                                                .getDeclaredConstructor().newInstance();
                                context.put(ma.TeethCare.repository.api.StatistiqueRepository.class,
                                                statistiqueRepository);
                                contextByName.put("statistiqueRepo", statistiqueRepository);

                                // --- SERVICES ---

                                // Patient Service
                                String patientServiceClass = properties.getProperty("patientService");
                                Class<?> cPatientService = Class.forName(patientServiceClass);
                                PatientService patientService = (PatientService) cPatientService
                                                .getDeclaredConstructor(PatientRepository.class)
                                                .newInstance(patientRepository);
                                context.put(PatientService.class, patientService);
                                contextByName.put("patientService", patientService);

                                // Actes Service
                                String actesServiceClass = properties.getProperty("actesService");
                                Class<?> cActesService = Class.forName(actesServiceClass);
                                actesService actesService = (actesService) cActesService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.ActesRepository.class)
                                                .newInstance(actesRepository);
                                context.put(actesService.class, actesService);
                                contextByName.put("actesService", actesService);

                                // Admin Service
                                String adminServiceClass = properties.getProperty("adminService");
                                Class<?> cAdminService = Class.forName(adminServiceClass);
                                adminService adminService = (adminService) cAdminService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.AdminRepository.class)
                                                .newInstance(adminRepository);
                                context.put(adminService.class, adminService);
                                contextByName.put("adminService", adminService);

                                // Agenda Service
                                String agendaServiceClass = properties.getProperty("agendaService");
                                Class<?> cAgendaService = Class.forName(agendaServiceClass);
                                agendaService agendaService = (agendaService) cAgendaService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.AgendaRepository.class)
                                                .newInstance(agendaRepository);
                                context.put(agendaService.class, agendaService);
                                contextByName.put("agendaService", agendaService);

                                // Antecedent Service
                                String antecedentServiceClass = properties.getProperty("antecedentService");
                                Class<?> cAntecedentService = Class.forName(antecedentServiceClass);
                                antecedentService antecedentService = (antecedentService) cAntecedentService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.AntecedentRepository.class)
                                                .newInstance(antecedentRepository);
                                context.put(antecedentService.class, antecedentService);
                                contextByName.put("antecedentService", antecedentService);

                                // CabinetMedicale Service
                                String cabinetServiceClass = properties.getProperty("cabinetMedicaleService");
                                Class<?> cCabinetService = Class.forName(cabinetServiceClass);
                                cabinetMedicaleService cabinetService = (cabinetMedicaleService) cCabinetService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.CabinetMedicaleRepository.class)
                                                .newInstance(cabinetRepository);
                                context.put(cabinetMedicaleService.class, cabinetService);
                                contextByName.put("cabinetMedicaleService", cabinetService);

                                // Certificat Service
                                String certificatServiceClass = properties.getProperty("certificatService");
                                Class<?> cCertificatService = Class.forName(certificatServiceClass);
                                certificatService certificatService = (certificatService) cCertificatService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.CertificatRepository.class)
                                                .newInstance(certificatRepository);
                                context.put(certificatService.class, certificatService);
                                contextByName.put("certificatService", certificatService);

                                // Charges Service
                                String chargesServiceClass = properties.getProperty("chargesService");
                                Class<?> cChargesService = Class.forName(chargesServiceClass);
                                chargesService chargesService = (chargesService) cChargesService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.ChargesRepository.class)
                                                .newInstance(chargesRepository);
                                context.put(chargesService.class, chargesService);
                                contextByName.put("chargesService", chargesService);

                                // Consultation Service
                                String consultationServiceClass = properties.getProperty("consultationService");
                                Class<?> cConsultationService = Class.forName(consultationServiceClass);
                                consultationService consultationService = (consultationService) cConsultationService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.ConsultationRepository.class)
                                                .newInstance(consultationRepository);
                                context.put(consultationService.class, consultationService);
                                contextByName.put("consultationService", consultationService);

                                // DossierMedicale Service
                                String dmServiceClass = properties.getProperty("dossierMedicaleService");
                                Class<?> cDmService = Class.forName(dmServiceClass);
                                dossierMedicaleService dmService = (dossierMedicaleService) cDmService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.DossierMedicaleRepository.class)
                                                .newInstance(dmRepository);
                                context.put(dossierMedicaleService.class, dmService);
                                contextByName.put("dossierMedicaleService", dmService);

                                // Facture Service
                                String factureServiceClass = properties.getProperty("factureService");
                                Class<?> cFactureService = Class.forName(factureServiceClass);
                                factureService factureService = (factureService) cFactureService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.FactureRepository.class)
                                                .newInstance(factureRepository);
                                context.put(factureService.class, factureService);
                                contextByName.put("factureService", factureService);

                                // InterventionMedecin Service
                                String interventionServiceClass = properties.getProperty("interventionMedecinService");
                                Class<?> cInterventionService = Class.forName(interventionServiceClass);
                                interventionMedecinService interventionService = (interventionMedecinService) cInterventionService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.InterventionMedecinRepository.class)
                                                .newInstance(interventionRepository);
                                context.put(interventionMedecinService.class, interventionService);
                                contextByName.put("interventionMedecinService", interventionService);

                                // Log Service
                                String logServiceClass = properties.getProperty("logService");
                                Class<?> cLogService = Class.forName(logServiceClass);
                                logService logService = (logService) cLogService
                                                .getDeclaredConstructor(ma.TeethCare.repository.api.LogRepository.class)
                                                .newInstance(logRepository);
                                context.put(logService.class, logService);
                                contextByName.put("logService", logService);

                                // Medecin Service
                                String medecinServiceClass = properties.getProperty("medecinService");
                                Class<?> cMedecinService = Class.forName(medecinServiceClass);
                                medecinService medecinService = (medecinService) cMedecinService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.MedecinRepository.class)
                                                .newInstance(medecinRepository);
                                context.put(medecinService.class, medecinService);
                                contextByName.put("medecinService", medecinService);

                                // Medicament Service
                                String medicamentServiceClass = properties.getProperty("medicamentService");
                                Class<?> cMedicamentService = Class.forName(medicamentServiceClass);
                                medicamentsService medicamentService = (medicamentsService) cMedicamentService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.MedicamentRepository.class)
                                                .newInstance(medicamentRepository);
                                context.put(medicamentsService.class, medicamentService);
                                contextByName.put("medicamentService", medicamentService);

                                // Notification Service
                                String notificationServiceClass = properties.getProperty("notificationService");
                                Class<?> cNotificationService = Class.forName(notificationServiceClass);
                                notificationService notificationService = (notificationService) cNotificationService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.NotificationRepository.class)
                                                .newInstance(notificationRepository);
                                context.put(notificationService.class, notificationService);
                                contextByName.put("notificationService", notificationService);

                                // Ordonnance Service
                                String ordonnanceServiceClass = properties.getProperty("ordonnanceService");
                                Class<?> cOrdonnanceService = Class.forName(ordonnanceServiceClass);
                                ordonnanceService ordonnanceService = (ordonnanceService) cOrdonnanceService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.OrdonnanceRepository.class)
                                                .newInstance(ordonnanceRepository);
                                context.put(ordonnanceService.class, ordonnanceService);
                                contextByName.put("ordonnanceService", ordonnanceService);

                                // Prescription Service
                                String prescriptionServiceClass = properties.getProperty("prescriptionService");
                                Class<?> cPrescriptionService = Class.forName(prescriptionServiceClass);
                                prescriptionService prescriptionService = (prescriptionService) cPrescriptionService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.PrescriptionRepository.class)
                                                .newInstance(prescriptionRepository);
                                context.put(prescriptionService.class, prescriptionService);
                                contextByName.put("prescriptionService", prescriptionService);

                                // Rdv Service
                                String rdvServiceClass = properties.getProperty("rdvService");
                                Class<?> cRdvService = Class.forName(rdvServiceClass);
                                rdvService rdvService = (rdvService) cRdvService
                                                .getDeclaredConstructor(ma.TeethCare.repository.api.RdvRepository.class)
                                                .newInstance(rdvRepository);
                                context.put(rdvService.class, rdvService);
                                contextByName.put("rdvService", rdvService);

                                // Revenues Service
                                String revenuesServiceClass = properties.getProperty("revenuesService");
                                Class<?> cRevenuesService = Class.forName(revenuesServiceClass);
                                revenuesService revenuesService = (revenuesService) cRevenuesService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.RevenuesRepository.class)
                                                .newInstance(revenuesRepository);
                                context.put(revenuesService.class, revenuesService);
                                contextByName.put("revenuesService", revenuesService);

                                // Role Service
                                String roleServiceClass = properties.getProperty("roleService");
                                Class<?> cRoleService = Class.forName(roleServiceClass);
                                roleService roleService = (roleService) cRoleService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.RoleRepository.class)
                                                .newInstance(roleRepository);
                                context.put(roleService.class, roleService);
                                contextByName.put("roleService", roleService);

                                // Secretaire Service
                                String secretaireServiceClass = properties.getProperty("secretaireService");
                                Class<?> cSecretaireService = Class.forName(secretaireServiceClass);
                                secretaireService secretaireService = (secretaireService) cSecretaireService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.SecretaireRepository.class)
                                                .newInstance(secretaireRepository);
                                context.put(secretaireService.class, secretaireService);
                                contextByName.put("secretaireService", secretaireService);

                                // SituationFinanciere Service
                                String sfServiceClass = properties.getProperty("situationFinanciereService");
                                Class<?> cSfService = Class.forName(sfServiceClass);
                                situationFinanciereService sfService = (situationFinanciereService) cSfService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.SituationFinanciereRepository.class)
                                                .newInstance(sfRepository);
                                context.put(situationFinanciereService.class, sfService);
                                contextByName.put("situationFinanciereService", sfService);

                                // Staff Service
                                String staffServiceClass = properties.getProperty("staffService");
                                Class<?> cStaffService = Class.forName(staffServiceClass);
                                staffService staffService = (staffService) cStaffService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.StaffRepository.class)
                                                .newInstance(staffRepository);
                                context.put(staffService.class, staffService);
                                contextByName.put("staffService", staffService);

                                // Utilisateur Service
                                String utilisateurServiceClass = properties.getProperty("utilisateurService");
                                Class<?> cUtilisateurService = Class.forName(utilisateurServiceClass);
                                utilisateurService utilisateurService = (utilisateurService) cUtilisateurService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.UtilisateurRepository.class)
                                                .newInstance(utilisateurRepository);
                                context.put(utilisateurService.class, utilisateurService);
                                contextByName.put("utilisateurService", utilisateurService);

                                // Statistique Service
                                String statistiqueServiceClass = properties.getProperty("statistiqueService");
                                Class<?> cStatistiqueService = Class.forName(statistiqueServiceClass);
                                statistiqueService statistiqueService = (statistiqueService) cStatistiqueService
                                                .getDeclaredConstructor(
                                                                ma.TeethCare.repository.api.StatistiqueRepository.class)
                                                .newInstance(statistiqueRepository);
                                context.put(statistiqueService.class, statistiqueService);
                                contextByName.put("statistiqueService", statistiqueService);

                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                } else {
                        System.err.println("Erreur : Le fichier beans.properties est introuvable !");
                }
        }

        /**
         * Retourne un composant bean en fonction de son nom (String).
         */
        public static Object getBean(String beanName) {
                return contextByName.get(beanName);
        }

        /**
         * Retourne un composant bean en fonction de sa classe.
         */
        public static <T> T getBean(Class<T> beanClass) {
                return beanClass.cast(context.get(beanClass));
        }

}
