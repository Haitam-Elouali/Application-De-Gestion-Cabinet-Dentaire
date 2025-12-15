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
        staffRepo.create(this.staffMember = staff.createTestInstance());


        patientRepo.create(this.patient = Patient.createTestInstance());
        medecinRepo.create(this.medecin = medecin.createTestInstance());
        secretaireRepo.create(this.secretaire = secretaire.createTestInstance());
        adminRepo.create(this.admin = admin.createTestInstance());


        dossieRepo.create(this.dossier = dossierMedicale.createTestInstance(this.patient));
        antecedentRepo.create(this.antecedent = antecedent.createTestInstance(this.dossier));
        rdvRepo.create(this.rdv = rdv.createTestInstance(this.patient));
        agendaRepo.create(this.agenda = agenda.createTestInstance(this.medecin));
        consultationRepo.create(this.consultation = consultation.createTestInstance(this.patient, this.medecin));
        ordonnanceRepo.create(this.ordonnance = ordonnance.createTestInstance(this.consultation));
        medicamentRepo.create(this.medicament = medicaments.createTestInstance());
        prescriptionRepo.create(this.prescription = prescription.createTestInstance(this.ordonnance, this.medicament));
        certificatRepo.create(this.certificat = certificat.createTestInstance(this.consultation));
        interventionRepo.create(this.intervention = interventionMedecin.createTestInstance(this.consultation));

        factureRepo.create(this.facture = facture.createTestInstance(this.patient, this.consultation));
        sfRepo.create(this.sf = situationFinanciere.createTestInstance(this.dossier));
        chargesRepo.create(this.charge = charges.createTestInstance());
        revenuesRepo.create(this.revenu = revenues.createTestInstance());

        actesRepo.create(this.acte = actes.createTestInstance());
        notificationRepo.create(this.notification = notification.createTestInstance(this.notification.getUtilisateur()));
        logRepo.create(this.log = log.createTestInstance());
        statRepo.create(this.stat = statistique.createTestInstance());
    }

    public void selectTestRepo()
    {
        this.cabinet = cabinetRepo.findById(1L);
        this.role = roleRepo.findById(1L);
        this.staffMember = staffRepo.findById(1L);

        this.patient = patientRepo.findById(1L);
        this.medecin = medecinRepo.findById(1L);
        this.secretaire = secretaireRepo.findById(1L);
        this.admin = adminRepo.findById(1L);

        this.dossier = dossieRepo.findById(1L);
        this.antecedent = antecedentRepo.findById(1L);
        this.rdv = rdvRepo.findById(1L);
        this.agenda = agendaRepo.findById(1L);
        this.consultation = consultationRepo.findById(1L);
        this.ordonnance = ordonnanceRepo.findById(1L);
        this.medicament = medicamentRepo.findById(1L);
        this.prescription = prescriptionRepo.findById(1L);
        this.certificat = certificatRepo.findById(1L);
        this.intervention = interventionRepo.findById(1L);

        this.facture = factureRepo.findById(1L);
        this.sf = sfRepo.findById(1L);
        this.charge = chargesRepo.findById(1L);
        this.revenu = revenuesRepo.findById(1L);

        this.acte = actesRepo.findById(1L);
        this.notification = notificationRepo.findById(1L);
        this.log = logRepo.findById(1L);
        this.stat = statRepo.findById(1L);
    }

    public void updateTestRepo()
    {
        // Update cabinet - assuming it has nom property from utilisateur
        this.cabinet.setNomCabinet("Cabinet Dentaire Updated");
        cabinetRepo.update(this.cabinet);

        // Update role - using description property
        this.role.setLibelle("Role Updated");
        roleRepo.update(this.role);

        // Update staff - assuming it inherits nom and email from utilisateur
        this.staffMember.setNom("Staff Updated");
        this.staffMember.setEmail("staff.updated@test.com");
        staffRepo.update(this.staffMember);

        // Update patient - using nom and email
        this.patient.setNom("Dupont Updated");
        this.patient.setEmail("jean.updated@example.com");
        patientRepo.update(this.patient);

        // Update medecin - using nom and specialite
        this.medecin.setNom("House Updated");
        this.medecin.setSpecialite("Diagnostician Updated");
        medecinRepo.update(this.medecin);

        // Update secretaire - using nom
        this.secretaire.setNom("Secretaire Updated");
        secretaireRepo.update(this.secretaire);

        // Update admin - using nom
        this.admin.setNom("Admin Updated");
        adminRepo.update(this.admin);

        // Update dossier - using numDossier
        this.dossier.setId(1L);
        dossieRepo.update(this.dossier);


        // Update rdv - using motif
        this.rdv.setMotif("Motif Updated");
        rdvRepo.update(this.rdv);

        // Update agenda - using description if it exists, otherwise skip
        // this.agenda.setDescription("Agenda Updated"); // Property may not exist
        agendaRepo.update(this.agenda);

        // Update consultation - using diagnostique
        this.consultation.setDiagnostic("Diagnostic Updated");
        consultationRepo.update(this.consultation);

        // Update ordonnance - using duree
        // this.ordonnance.setDuree("7 days"); // Property removed
        ordonnanceRepo.update(this.ordonnance);

        // Update medicament - using nom
        this.medicament.setNomCommercial("Medicament Updated");
        medicamentRepo.update(this.medicament);

        // Update prescription - using quantite
        this.prescription.setQuantite(10);
        prescriptionRepo.update(this.prescription);

        // Update certificat - using motif if exists
        // this.certificat.setMotif("Motif Updated"); // Property may not exist
        certificatRepo.update(this.certificat);

        // Update intervention - using description if exists
        // this.intervention.setDescription("Intervention Updated"); // Property may not exist
        interventionRepo.update(this.intervention);

        // Update facture - using montant
        this.facture.setTotaleFacture(200.0);
        factureRepo.update(this.facture);

        // Update situation financiere - using montantTotal if exists
        // this.sf.setMontantTotal(1500.0); // Property may not exist
        sfRepo.update(this.sf);

        // Update charges - using montant
        this.charge.setMontant(500.0);
        chargesRepo.update(this.charge);

        // Update revenues - using montant
        this.revenu.setMontant(1000.0);
        revenuesRepo.update(this.revenu);

        // Update actes - using libeller
        this.acte.setNom("Acte Updated");
        actesRepo.update(this.acte);

        // Update notification - using message
        this.notification.setMessage("Message Updated");
        notificationRepo.update(this.notification);

        // Update log - using action
        this.log.setMessage("Action Updated");
        logRepo.update(this.log);

        // Update statistique
        this.stat.setNom("Stat Updated");
        statRepo.update(this.stat);
    }

    public void deleteTestRepo()
    {
        statRepo.deleteById(1L);
        logRepo.deleteById(1L);
        notificationRepo.deleteById(1L);
        actesRepo.deleteById(1L);

        revenuesRepo.deleteById(1L);
        chargesRepo.deleteById(1L);
        sfRepo.deleteById(1L);
        factureRepo.deleteById(1L);

        interventionRepo.deleteById(1L);
        certificatRepo.deleteById(1L);
        prescriptionRepo.deleteById(1L);
        medicamentRepo.deleteById(1L);
        ordonnanceRepo.deleteById(1L);
        consultationRepo.deleteById(1L);
        agendaRepo.deleteById(1L);
        rdvRepo.deleteById(1L);
        antecedentRepo.deleteById(1L);
        dossieRepo.deleteById(1L);

        adminRepo.deleteById(1L);
        secretaireRepo.deleteById(1L);
        medecinRepo.deleteById(1L);
        patientRepo.deleteById(1L);

        staffRepo.deleteById(1L);
        roleRepo.deleteById(1L);
        cabinetRepo.deleteById(1L);
    }

}