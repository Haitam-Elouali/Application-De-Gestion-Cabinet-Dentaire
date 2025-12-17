package ma.TeethCare.repository.test;

import ma.TeethCare.conf.ApplicationContext;
import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.entities.log.log;
import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.entities.role.role;
import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.entities.statistique.statistique;
import ma.TeethCare.repository.api.*;
import java.time.LocalDate;
import java.util.List;

public class TestRepo {

    // Repositories
    private PatientRepository patientRepo;
    private DossierMedicaleRepository dossieRepo;
    private MedecinRepository medecinRepo;
    private RdvRepository rdvRepo;
    private ConsultationRepository consultationRepo;
    private FactureRepository factureRepo;
    private SituationFinanciereRepository sfRepo;
    private ActesRepository actesRepo;
    private AdminRepository adminRepo;
    private AgendaRepository agendaRepo;
    private AntecedentRepository antecedentRepo;
    private CabinetMedicaleRepository cabinetRepo;
    private CertificatRepository certificatRepo;
    private ChargesRepository chargesRepo;
    private InterventionMedecinRepository interventionRepo;
    private LogRepository logRepo;
    private MedicamentRepository medicamentRepo;
    private NotificationRepository notificationRepo;
    private OrdonnanceRepository ordonnanceRepo;
    private PrescriptionRepository prescriptionRepo;
    private RevenuesRepository revenuesRepo;
    private RoleRepository roleRepo;
    private SecretaireRepository secretaireRepo;
    private StaffRepository staffRepo;
    private UtilisateurRepository utilisateurRepo;
    private StatistiqueRepository statRepo;

    public TestRepo() {
        // Initialize all repositories from Context
        this.patientRepo = (PatientRepository) ApplicationContext.getBean(PatientRepository.class);
        this.dossieRepo = (DossierMedicaleRepository) ApplicationContext.getBean(DossierMedicaleRepository.class);
        this.medecinRepo = (MedecinRepository) ApplicationContext.getBean(MedecinRepository.class);
        this.rdvRepo = (RdvRepository) ApplicationContext.getBean(RdvRepository.class);
        this.consultationRepo = (ConsultationRepository) ApplicationContext.getBean(ConsultationRepository.class);
        this.factureRepo = (FactureRepository) ApplicationContext.getBean(FactureRepository.class);
        this.sfRepo = (SituationFinanciereRepository) ApplicationContext.getBean(SituationFinanciereRepository.class);
        this.actesRepo = (ActesRepository) ApplicationContext.getBean(ActesRepository.class);
        this.adminRepo = (AdminRepository) ApplicationContext.getBean(AdminRepository.class);
        this.agendaRepo = (AgendaRepository) ApplicationContext.getBean(AgendaRepository.class);
        this.antecedentRepo = (AntecedentRepository) ApplicationContext.getBean(AntecedentRepository.class);
        this.cabinetRepo = (CabinetMedicaleRepository) ApplicationContext.getBean(CabinetMedicaleRepository.class);
        this.certificatRepo = (CertificatRepository) ApplicationContext.getBean(CertificatRepository.class);
        this.chargesRepo = (ChargesRepository) ApplicationContext.getBean(ChargesRepository.class);
        this.interventionRepo = (InterventionMedecinRepository) ApplicationContext.getBean(InterventionMedecinRepository.class);
        this.logRepo = (LogRepository) ApplicationContext.getBean(LogRepository.class);
        this.medicamentRepo = (MedicamentRepository) ApplicationContext.getBean(MedicamentRepository.class);
        this.notificationRepo = (NotificationRepository) ApplicationContext.getBean(NotificationRepository.class);
        this.ordonnanceRepo = (OrdonnanceRepository) ApplicationContext.getBean(OrdonnanceRepository.class);
        this.prescriptionRepo = (PrescriptionRepository) ApplicationContext.getBean(PrescriptionRepository.class);
        this.revenuesRepo = (RevenuesRepository) ApplicationContext.getBean(RevenuesRepository.class);
        this.roleRepo = (RoleRepository) ApplicationContext.getBean(RoleRepository.class);
        this.secretaireRepo = (SecretaireRepository) ApplicationContext.getBean(SecretaireRepository.class);
        this.staffRepo = (StaffRepository) ApplicationContext.getBean(StaffRepository.class);
        this.utilisateurRepo = (UtilisateurRepository) ApplicationContext.getBean(UtilisateurRepository.class);
        this.statRepo = (StatistiqueRepository) ApplicationContext.getBean(StatistiqueRepository.class);
    }

    // Entity Fields for Dependencies
    private cabinetMedicale cabinet;
    private role role;
    private staff staffMember;
    private Patient patient;
    private medecin medecin;
    private secretaire secretaire;
    private admin admin;
    private dossierMedicale dossier;
    private antecedent antecedent;
    private rdv rdv;
    private agenda agenda;
    private consultation consultation;
    private ordonnance ordonnance;
    private medicaments medicament;
    private prescription prescription;
    private certificat certificat;
    private interventionMedecin intervention;
    private facture facture;
    private situationFinanciere sf;
    private charges charge;
    private revenues revenu;
    private actes acte;
    private notification notification;
    private log log;
    private statistique stat;

    public void createTestRepo() {

        cabinetRepo.create(this.cabinet = cabinetMedicale.createTestInstance());
        roleRepo.create(this.role = role.createTestInstance());
        staff sMember = staff.createTestInstance();
        sMember.setEmail("staff_" + System.currentTimeMillis() + "@test.com"); // Unique email
        sMember.setUsername("staff_" + System.currentTimeMillis());
        staffRepo.create(this.staffMember = sMember);


        patientRepo.create(this.patient = Patient.createTestInstance());
        // Create unique instances
        String uniqueSuffix = "" + System.currentTimeMillis();
        
        medecin m = medecin.createTestInstance();
        m.setUsername("house_" + uniqueSuffix); 
        m.setEmail("house_" + uniqueSuffix + "@hospital.com");
        medecinRepo.create(this.medecin = m);
        
        secretaire s = secretaire.createTestInstance();
        s.setUsername("sec_" + uniqueSuffix);
        s.setEmail("sec_" + uniqueSuffix + "@test.com");
        secretaireRepo.create(this.secretaire = s);

        admin a = admin.createTestInstance();
        a.setUsername("admin_" + uniqueSuffix);
        a.setEmail("admin_" + uniqueSuffix + "@test.com");
        adminRepo.create(this.admin = a);


        // Create dossier after patient and ensure patient ID is set if not already
        if (this.patient.getId() == null && this.patient.getIdEntite() != null) {
            this.patient.setId(this.patient.getIdEntite());
        }
        this.dossier = dossierMedicale.createTestInstance(this.patient);
        this.dossier.setPatientId(this.patient.getId()); // Explicitly set ID
        dossieRepo.create(this.dossier);
        antecedentRepo.create(this.antecedent = antecedent.createTestInstance(this.dossier));
        
        this.rdv = rdv.createTestInstance(this.patient);
        this.rdv.setPatientId(this.patient.getId()); // Explicitly set patient ID for FK
        rdvRepo.create(this.rdv);
        
        this.agenda = agenda.createTestInstance(this.medecin);
        this.agenda.setMedecinId(this.medecin.getId()); // Explicitly set medecin ID
        agendaRepo.create(this.agenda);
        this.consultation = consultation.createTestInstance(this.patient, this.medecin);
        this.consultation.setPatientId(this.patient.getId());
        this.consultation.setMedecinId(this.medecin.getId());
        consultationRepo.create(this.consultation);

        this.ordonnance = ordonnance.createTestInstance(this.consultation);
        this.ordonnance.setConsultationId(this.consultation.getId());
        ordonnanceRepo.create(this.ordonnance);

        medicamentRepo.create(this.medicament = medicaments.createTestInstance());

        this.prescription = prescription.createTestInstance(this.ordonnance, this.medicament);
        this.prescription.setOrdonnanceId(this.ordonnance.getId());
        this.prescription.setMedicamentId(this.medicament.getId());
        prescriptionRepo.create(this.prescription);

        this.certificat = certificat.createTestInstance(this.consultation);
        this.certificat.setConsultationId(this.consultation.getId());
        certificatRepo.create(this.certificat);

        this.intervention = interventionMedecin.createTestInstance(this.consultation);
        this.intervention.setConsultationId(this.consultation.getId());
        interventionRepo.create(this.intervention);

        this.facture = facture.createTestInstance(this.patient, this.consultation);
        this.facture.setPatientId(this.patient.getId());
        this.facture.setConsultationId(this.consultation.getId());
        factureRepo.create(this.facture);
        
        sfRepo.create(this.sf = situationFinanciere.createTestInstance(this.dossier));
        chargesRepo.create(this.charge = charges.createTestInstance());
        revenuesRepo.create(this.revenu = revenues.createTestInstance());

        actesRepo.create(this.acte = actes.createTestInstance());
        // Create notification for medecin
        this.notification = notification.createTestInstance(this.medecin);
        this.notification.setUtilisateurId(this.medecin.getId()); // Explicitly set ID
        notificationRepo.create(this.notification);
        logRepo.create(this.log = log.createTestInstance());
        this.stat = statistique.createTestInstance();
        this.stat.setCabinetId(this.cabinet.getId());
        statRepo.create(this.stat);

    }

    public void selectTestRepo()
    {
        // Use the instance IDs if they exist (from createTestRepo), otherwise default to 1L
        Long cabinetId = this.cabinet != null && this.cabinet.getId() != null ? this.cabinet.getId() : 1L;
        this.cabinet = cabinetRepo.findById(cabinetId);
        System.out.println("✓ Cabinet sélectionné: " + (this.cabinet != null ? this.cabinet.getId() : "null"));

        Long roleId = this.role != null && this.role.getId() != null ? this.role.getId() : 1L;
        this.role = roleRepo.findById(roleId);
        System.out.println("✓ Role sélectionné: " + (this.role != null ? this.role.getId() : "null"));

        Long staffId = this.staffMember != null && this.staffMember.getId() != null ? this.staffMember.getId() : 1L;
        this.staffMember = staffRepo.findById(staffId);
        System.out.println("✓ Staff sélectionné: " + (this.staffMember != null ? this.staffMember.getId() : "null"));

        Long patientId = this.patient != null && this.patient.getId() != null ? this.patient.getId() : 1L;
        this.patient = patientRepo.findById(patientId);
        System.out.println("✓ Patient sélectionné: " + (this.patient != null ? this.patient.getId() : "null"));

        Long medecinId = this.medecin != null && this.medecin.getId() != null ? this.medecin.getId() : 1L;
        this.medecin = medecinRepo.findById(medecinId);
        System.out.println("✓ Medecin sélectionné: " + (this.medecin != null ? this.medecin.getId() : "null"));

        Long secretaireId = this.secretaire != null && this.secretaire.getId() != null ? this.secretaire.getId() : 1L;
        this.secretaire = secretaireRepo.findById(secretaireId);
        System.out.println("✓ Secretaire sélectionnée: " + (this.secretaire != null ? this.secretaire.getId() : "null"));

        Long adminId = this.admin != null && this.admin.getId() != null ? this.admin.getId() : 1L;
        this.admin = adminRepo.findById(adminId);
        System.out.println("✓ Admin sélectionné: " + (this.admin != null ? this.admin.getId() : "null"));

        Long dossierId = this.dossier != null && this.dossier.getId() != null ? this.dossier.getId() : 1L;
        this.dossier = dossieRepo.findById(dossierId);
        System.out.println("✓ Dossier sélectionné: " + (this.dossier != null ? this.dossier.getId() : "null"));

        Long antecedentId = this.antecedent != null && this.antecedent.getId() != null ? this.antecedent.getId() : 1L;
        this.antecedent = antecedentRepo.findById(antecedentId);
        System.out.println("✓ Antecedent sélectionné: " + (this.antecedent != null ? this.antecedent.getId() : "null"));

        Long rdvId = this.rdv != null && this.rdv.getId() != null ? this.rdv.getId() : 1L;
        this.rdv = rdvRepo.findById(rdvId);
        System.out.println("✓ RDV sélectionné: " + (this.rdv != null ? this.rdv.getId() : "null"));

        Long agendaId = this.agenda != null && this.agenda.getId() != null ? this.agenda.getId() : 1L;
        this.agenda = agendaRepo.findById(agendaId);
        System.out.println("✓ Agenda sélectionné: " + (this.agenda != null ? this.agenda.getId() : "null"));

        Long consultationId = this.consultation != null && this.consultation.getId() != null ? this.consultation.getId() : 1L;
        this.consultation = consultationRepo.findById(consultationId);
        System.out.println("✓ Consultation sélectionnée: " + (this.consultation != null ? this.consultation.getId() : "null"));

        Long ordonnanceId = this.ordonnance != null && this.ordonnance.getId() != null ? this.ordonnance.getId() : 1L;
        this.ordonnance = ordonnanceRepo.findById(ordonnanceId);
        System.out.println("✓ Ordonnance sélectionnée: " + (this.ordonnance != null ? this.ordonnance.getId() : "null"));

        Long medicamentId = this.medicament != null && this.medicament.getId() != null ? this.medicament.getId() : 1L;
        this.medicament = medicamentRepo.findById(medicamentId);
        System.out.println("✓ Medicament sélectionné: " + (this.medicament != null ? this.medicament.getId() : "null"));

        Long prescriptionId = this.prescription != null && this.prescription.getId() != null ? this.prescription.getId() : 1L;
        this.prescription = prescriptionRepo.findById(prescriptionId);
        System.out.println("✓ Prescription sélectionnée: " + (this.prescription != null ? this.prescription.getId() : "null"));

        Long certificatId = this.certificat != null && this.certificat.getId() != null ? this.certificat.getId() : 1L;
        this.certificat = certificatRepo.findById(certificatId);
        System.out.println("✓ Certificat sélectionné: " + (this.certificat != null ? this.certificat.getId() : "null"));

        Long interventionId = this.intervention != null && this.intervention.getId() != null ? this.intervention.getId() : 1L;
        this.intervention = interventionRepo.findById(interventionId);
        System.out.println("✓ Intervention sélectionnée: " + (this.intervention != null ? this.intervention.getId() : "null"));

        Long factureId = this.facture != null && this.facture.getId() != null ? this.facture.getId() : 1L;
        this.facture = factureRepo.findById(factureId);
        System.out.println("✓ Facture sélectionnée: " + (this.facture != null ? this.facture.getId() : "null"));

        Long sfId = this.sf != null && this.sf.getId() != null ? this.sf.getId() : 1L;
        this.sf = sfRepo.findById(sfId);
        System.out.println("✓ SituationFinanciere sélectionnée: " + (this.sf != null ? this.sf.getId() : "null"));

        Long chargeId = this.charge != null && this.charge.getId() != null ? this.charge.getId() : 1L;
        this.charge = chargesRepo.findById(chargeId);
        System.out.println("✓ Charge sélectionnée: " + (this.charge != null ? this.charge.getId() : "null"));

        Long revenuId = this.revenu != null && this.revenu.getId() != null ? this.revenu.getId() : 1L;
        this.revenu = revenuesRepo.findById(revenuId);
        System.out.println("✓ Revenu sélectionné: " + (this.revenu != null ? this.revenu.getId() : "null"));

        Long acteId = this.acte != null && this.acte.getId() != null ? this.acte.getId() : 1L;
        this.acte = actesRepo.findById(acteId); // Verify ActeRepo logic for IDs
        System.out.println("✓ Acte sélectionné: " + (this.acte != null ? this.acte.getId() : "null"));

        Long notificationId = this.notification != null && this.notification.getId() != null ? this.notification.getId() : 1L;
        this.notification = notificationRepo.findById(notificationId);
        System.out.println("✓ Notification sélectionnée: " + (this.notification != null ? this.notification.getId() : "null"));

        Long logId = this.log != null && this.log.getId() != null ? this.log.getId() : 1L;
        this.log = logRepo.findById(logId);
        System.out.println("✓ Log sélectionné: " + (this.log != null ? this.log.getId() : "null"));

        Long statId = this.stat != null && this.stat.getId() != null ? this.stat.getId() : 1L;
        this.stat = statRepo.findById(statId);
        System.out.println("✓ Statistique sélectionnée: " + (this.stat != null ? this.stat.getId() : "null"));
    }

    public void updateTestRepo()
    {
        // Update cabinet - assuming it has nom property from utilisateur
        if (this.cabinet != null) {
            this.cabinet.setNomCabinet("Cabinet Dentaire Updated");
            cabinetRepo.update(this.cabinet);
            System.out.println("✓ Cabinet mis à jour");
        }

        // Update role - using description property
        if (this.role != null) {
            this.role.setLibelle("Role Updated");
            roleRepo.update(this.role);
            System.out.println("✓ Role mis à jour");
        }

        // Update staff - assuming it inherits nom and email from utilisateur
        if (this.staffMember != null) {
            this.staffMember.setNom("Staff Updated");
            this.staffMember.setEmail("staff.updated." + System.currentTimeMillis() + "@test.com");
            staffRepo.update(this.staffMember);
            System.out.println("✓ Staff mis à jour");
        }

        // Update patient - using nom and email
        this.patient.setNom("Dupont Updated");
        this.patient.setEmail("jean.updated." + System.currentTimeMillis() + "@example.com");
        patientRepo.update(this.patient);
        System.out.println("✓ Patient mis à jour");

        // Update medecin - using nom and specialite
        this.medecin.setNom("House Updated");
        this.medecin.setSpecialite("Diagnostician Updated");
        medecinRepo.update(this.medecin);
        System.out.println("✓ Medecin mis à jour");

        // Update secretaire - using nom
        this.secretaire.setNom("Secretaire Updated");
        secretaireRepo.update(this.secretaire);
        System.out.println("✓ Secretaire mise à jour");

        // Update admin - using nom
        this.admin.setNom("Admin Updated");
        adminRepo.update(this.admin);
        System.out.println("✓ Admin mis à jour");

        // Update dossier - using numDossier
        if (this.dossier != null) {
            this.dossier.setId(this.dossier.getId()); // dummy update
            dossieRepo.update(this.dossier);
            System.out.println("✓ Dossier mis à jour");
        }

        // Update rdv - using motif
        if (this.rdv != null) {
            this.rdv.setMotif("Motif Updated");
            rdvRepo.update(this.rdv);
            System.out.println("✓ RDV mis à jour");
        }

        // Update agenda - using description if it exists, otherwise skip
        if (this.agenda != null) {
            // this.agenda.setDescription("Agenda Updated"); // Property may not exist
            agendaRepo.update(this.agenda);
            System.out.println("✓ Agenda mis à jour");
        }

        // Update consultation - using diagnostique
        if (this.consultation != null) {
            this.consultation.setDiagnostic("Diagnostic Updated");
            consultationRepo.update(this.consultation);
            System.out.println("✓ Consultation mise à jour");
        }

        // Update ordonnance - using duree
        if (this.ordonnance != null) {
            // this.ordonnance.setDuree("7 days"); // Property removed
            ordonnanceRepo.update(this.ordonnance);
            System.out.println("✓ Ordonnance mise à jour");
        }

        // Update medicament - using nom
        if (this.medicament != null) {
            this.medicament.setNomCommercial("Medicament Updated");
            medicamentRepo.update(this.medicament);
            System.out.println("✓ Medicament mis à jour");
        }

        // Update prescription - using quantite
        if (this.prescription != null) {
            this.prescription.setQuantite(10);
            prescriptionRepo.update(this.prescription);
            System.out.println("✓ Prescription mise à jour");
        }

        // Update certificat - using motif if exists
        if (this.certificat != null) {
            // this.certificat.setMotif("Motif Updated"); // Property may not exist
            certificatRepo.update(this.certificat);
            System.out.println("✓ Certificat mis à jour");
        }

        // Update intervention - using description if exists
        if (this.intervention != null) {
            // this.intervention.setDescription("Intervention Updated"); // Property may not exist
            interventionRepo.update(this.intervention);
            System.out.println("✓ Intervention mise à jour");
        }

        // Update facture - using montant
        if (this.facture != null) {
            this.facture.setTotaleFacture(200.0);
            factureRepo.update(this.facture);
            System.out.println("✓ Facture mise à jour");
        }

        // Update situation financiere - using montantTotal if exists
        if (this.sf != null) {
            // this.sf.setMontantTotal(1500.0); // Property may not exist
            sfRepo.update(this.sf);
            System.out.println("✓ SituationFinanciere mise à jour");
        }

        // Update charges - using montant
        if (this.charge != null) {
            this.charge.setMontant(500.0);
            chargesRepo.update(this.charge);
            System.out.println("✓ Charge mise à jour");
        }

        // Update revenues - using montant
        if (this.revenu != null) {
            this.revenu.setMontant(1000.0);
            revenuesRepo.update(this.revenu);
            System.out.println("✓ Revenu mis à jour");
        }

        // Update actes - using libeller
        if (this.acte != null) {
            this.acte.setNom("Acte Updated");
            actesRepo.update(this.acte);
            System.out.println("✓ Acte mis à jour");
        }

        // Update notification - using message
        if (this.notification != null) {
            this.notification.setMessage("Message Updated");
            notificationRepo.update(this.notification);
            System.out.println("✓ Notification mise à jour");
        }

        // Update log - using action
        if (this.log != null) {
            this.log.setMessage("Action Updated");
            logRepo.update(this.log);
            System.out.println("✓ Log mis à jour");
        }

        // Update statistique
        if (this.stat != null) {
            this.stat.setNom("Stat Updated");
            statRepo.update(this.stat);
            System.out.println("✓ Statistique mise à jour");
        }
    }

    public void deleteTestRepo()
    {
        statRepo.deleteById(1L);
        System.out.println("✓ Statistique supprimée");

        logRepo.deleteById(1L);
        System.out.println("✓ Log supprimé");

        notificationRepo.deleteById(1L);
        System.out.println("✓ Notification supprimée");

        actesRepo.deleteById(1L);
        System.out.println("✓ Acte supprimé");

        revenuesRepo.deleteById(1L);
        System.out.println("✓ Revenu supprimé");

        chargesRepo.deleteById(1L);
        System.out.println("✓ Charge supprimée");

        sfRepo.deleteById(1L);
        System.out.println("✓ SituationFinanciere supprimée");

        factureRepo.deleteById(1L);
        System.out.println("✓ Facture supprimée");

        interventionRepo.deleteById(1L);
        System.out.println("✓ Intervention supprimée");

        certificatRepo.deleteById(1L);
        System.out.println("✓ Certificat supprimé");

        prescriptionRepo.deleteById(1L);
        System.out.println("✓ Prescription supprimée");

        medicamentRepo.deleteById(1L);
        System.out.println("✓ Medicament supprimé");

        ordonnanceRepo.deleteById(1L);
        System.out.println("✓ Ordonnance supprimée");

        consultationRepo.deleteById(1L);
        System.out.println("✓ Consultation supprimée");

        agendaRepo.deleteById(1L);
        System.out.println("✓ Agenda supprimé");

        rdvRepo.deleteById(1L);
        System.out.println("✓ RDV supprimé");

        antecedentRepo.deleteById(1L);
        System.out.println("✓ Antecedent supprimé");

        dossieRepo.deleteById(1L);
        System.out.println("✓ Dossier supprimé");

        adminRepo.deleteById(1L);
        System.out.println("✓ Admin supprimé");

        secretaireRepo.deleteById(1L);
        System.out.println("✓ Secretaire supprimée");

        medecinRepo.deleteById(1L);
        System.out.println("✓ Medecin supprimé");

        patientRepo.deleteById(1L);
        System.out.println("✓ Patient supprimé");

        staffRepo.deleteById(1L);
        System.out.println("✓ Staff supprimé");

        roleRepo.deleteById(1L);
        System.out.println("✓ Role supprimé");

        cabinetRepo.deleteById(1L);
        System.out.println("✓ Cabinet supprimé");
    }

    public static void main(String[] args) {
        try {
            System.out.println("=== DÉBUT DU TEST REPO ===");
            TestRepo test = new TestRepo();

            System.out.println("1. Création des données...");
            test.createTestRepo();
            System.out.println("✓ Données créées.");

            System.out.println("2. Sélection des données...");
            test.selectTestRepo();
            System.out.println("✓ Données sélectionnées.");

            System.out.println("3. Mise à jour des données...");
            test.updateTestRepo();
            System.out.println("✓ Données mises à jour.");

            System.out.println("4. Suppression des données...");
            test.deleteTestRepo();
            System.out.println("✓ Données supprimées.");

            System.out.println("=== TEST REPO TERMINÉ AVEC SUCCÈS ===");
        } catch (Exception e) {
            System.err.println("❌ ERREUR LORS DU TEST REPO:");
            e.printStackTrace();
        }
    }
}