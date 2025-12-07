package ma.TeethCare.repository.common;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.entities.log.log;
import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.entities.enums.*;
import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.entities.role.role;
import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.entities.notification.notification;

public final class RowMappers {

    private RowMappers() {
    }

    // --- Helper Methods ---

    private static String getStringOrNull(ResultSet rs, String colName) {
        try {
            return rs.getString(colName);
        } catch (SQLException e) {
            return null;
        }
    }

    private static Long getLongOrNull(ResultSet rs, String colName) {
        try {
            long val = rs.getLong(colName);
            if (rs.wasNull())
                return null;
            return val;
        } catch (SQLException e) {
            return null;
        }
    }

    private static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x)))
                return true;
        }
        return false;
    }

    // --- Mappers ---

    public static Patient mapPatient(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        try {
            Long id = getLongOrNull(rs, "id");
            if (id == null)
                id = getLongOrNull(rs, "idEntite");
            p.setId(id);
        } catch (Exception e) {
        }

        p.setNom(getStringOrNull(rs, "nom"));
        p.setPrenom(getStringOrNull(rs, "prenom"));
        p.setAdresse(getStringOrNull(rs, "adresse"));
        p.setTelephone(getStringOrNull(rs, "telephone"));
        p.setEmail(getStringOrNull(rs, "email"));

        Date dn = rs.getDate("dateNaissance");
        if (dn != null)
            p.setDateNaissance(dn.toLocalDate());

        Timestamp dc = rs.getTimestamp("dateCreation");
        if (dc != null)
            p.setDateCreation(dc.toLocalDateTime());

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null)
            p.setSexe(Sexe.valueOf(sexeStr));

        String assuranceStr = getStringOrNull(rs, "assurance");
        if (assuranceStr != null)
            p.setAssurance(Assurance.valueOf(assuranceStr));

        return p;
    }

    public static antecedent mapAntecedent(ResultSet rs) throws SQLException {
        antecedent a = new antecedent();
        a.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            a.setDateCreation(dateCreationSql.toLocalDate());

        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            a.setDateDerniereModification(dateModifSql.toLocalDateTime());

        a.setCreePar(getStringOrNull(rs, "creePar"));
        a.setModifierPar(getStringOrNull(rs, "modifierPar"));

        a.setIdAntecedent(getLongOrNull(rs, "id_Antecedent"));
        a.setNom(getStringOrNull(rs, "nom"));
        a.setCategorie(getStringOrNull(rs, "categorie"));

        String niveauRisqueStr = getStringOrNull(rs, "niveauRisque");
        if (niveauRisqueStr != null) {
            a.setNiveauRisque(ma.TeethCare.entities.enums.niveauDeRisque.valueOf(niveauRisqueStr));
        }
        return a;
    }

    public static actes mapActes(ResultSet rs) throws SQLException {
        actes entity = new actes();
        entity.setIdEntite(getLongOrNull(rs, "idEntite"));
        entity.setLibeller(getStringOrNull(rs, "libeller"));
        entity.setCategorie(getStringOrNull(rs, "categorie"));
        entity.setPrixDeBase(rs.getDouble("prixDeBase"));

        if (hasColumn(rs, "codeSECU"))
            entity.setCodeSECU(getStringOrNull(rs, "codeSECU"));

        if (hasColumn(rs, "dateCreation")) {
            Date d = rs.getDate("dateCreation");
            if (d != null)
                entity.setDateCreation(d.toLocalDate());
        }
        if (hasColumn(rs, "dateDerniereModification")) {
            Timestamp t = rs.getTimestamp("dateDerniereModification");
            if (t != null)
                entity.setDateDerniereModification(t.toLocalDateTime());
        }
        if (hasColumn(rs, "modifierPar"))
            entity.setModifierPar(getStringOrNull(rs, "modifierPar"));
        if (hasColumn(rs, "creePar"))
            entity.setCreePar(getStringOrNull(rs, "creePar"));

        return entity;
    }

    public static admin mapAdmin(ResultSet rs) throws SQLException {
        admin entity = new admin();
        // Assuming 'id' column maps to idEntite inherited from baseEntity
        entity.setIdEntite(getLongOrNull(rs, "id"));
        if (entity.getIdEntite() == null)
            entity.setIdEntite(getLongOrNull(rs, "idEntite"));

        entity.setPermissionAdmin(getStringOrNull(rs, "permissionAdmin"));
        entity.setDomaine(getStringOrNull(rs, "domaine"));
        entity.setNom(getStringOrNull(rs, "nom"));
        // entity.setPrenom(getStringOrNull(rs, "prenom")); // Removed: field does not
        // exist in utilisateur
        entity.setEmail(getStringOrNull(rs, "email"));
        entity.setTel(getStringOrNull(rs, "telephone")); // Mapped 'telephone' column to 'tel' field

        if (hasColumn(rs, "dateCreation")) {
            Date d = rs.getDate("dateCreation");
            if (d != null)
                entity.setDateCreation(d.toLocalDate());
        }
        if (hasColumn(rs, "dateModification")) {
            Timestamp t = rs.getTimestamp("dateModification");
            if (t != null)
                entity.setDateDerniereModification(t.toLocalDateTime());
        }
        return entity;
    }

    public static cabinetMedicale mapCabinetMedicale(ResultSet rs) throws SQLException {
        cabinetMedicale c = new cabinetMedicale();
        c.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            c.setDateCreation(dateCreationSql.toLocalDate());

        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            c.setDateDerniereModification(dateModifSql.toLocalDateTime());

        c.setCreePar(getStringOrNull(rs, "creePar"));
        c.setModifierPar(getStringOrNull(rs, "modifierPar"));

        c.setNom(getStringOrNull(rs, "nom"));
        c.setEmail(getStringOrNull(rs, "email"));
        c.setLogo(getStringOrNull(rs, "logo"));
        c.setCin(getStringOrNull(rs, "cin"));
        c.setTel1(getStringOrNull(rs, "tel1"));
        c.setTel2(getStringOrNull(rs, "tel2"));
        c.setSiteWeb(getStringOrNull(rs, "siteWeb"));
        c.setInstagram(getStringOrNull(rs, "instagram"));
        c.setFacebook(getStringOrNull(rs, "facebook"));
        c.setDescription(getStringOrNull(rs, "description"));
        return c;
    }

    public static certificat mapCertificat(ResultSet rs) throws SQLException {
        certificat c = new certificat();
        c.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            c.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            c.setDateDerniereModification(dateModifSql.toLocalDateTime());

        c.setCreePar(getStringOrNull(rs, "creePar"));
        c.setModifierPar(getStringOrNull(rs, "modifierPar"));

        c.setIdCertif(getLongOrNull(rs, "idCertif"));

        Date dateDebutSql = rs.getDate("dateDebut");
        if (dateDebutSql != null)
            c.setDateDebut(dateDebutSql.toLocalDate());

        Date dateFinSql = rs.getDate("dateFin");
        if (dateFinSql != null)
            c.setDateFin(dateFinSql.toLocalDate());

        c.setDuree(rs.getInt("dureer"));
        c.setNoteMedecin(getStringOrNull(rs, "noteMedecin"));
        return c;
    }

    public static charges mapCharges(ResultSet rs) throws SQLException {
        charges c = new charges();
        c.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            c.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            c.setDateDerniereModification(dateModifSql.toLocalDateTime());

        c.setCreePar(getStringOrNull(rs, "creePar"));
        c.setModifierPar(getStringOrNull(rs, "modifierPar"));

        c.setTitre(getStringOrNull(rs, "titre"));
        c.setDescription(getStringOrNull(rs, "description"));
        c.setMontant(rs.getDouble("montant"));
        c.setCategorie(getStringOrNull(rs, "categorie"));

        Timestamp dateSql = rs.getTimestamp("date");
        if (dateSql != null)
            c.setDate(dateSql.toLocalDateTime());
        return c;
    }

    public static consultation mapConsultation(ResultSet rs) throws SQLException {
        consultation c = new consultation();
        c.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            c.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            c.setDateDerniereModification(dateModifSql.toLocalDateTime());

        c.setCreePar(getStringOrNull(rs, "creePar"));
        c.setModifierPar(getStringOrNull(rs, "modifierPar"));

        c.setIdConsultation(getLongOrNull(rs, "idConsultation"));

        Date dateSql = rs.getDate("Date");
        if (dateSql != null)
            c.setDate(dateSql.toLocalDate());

        String statutStr = getStringOrNull(rs, "statut");
        if (statutStr != null)
            c.setStatut(Statut.valueOf(statutStr));

        c.setObservationMedecin(getStringOrNull(rs, "observationMedecin"));
        return c;
    }

    public static dossierMedicale mapDossierMedicale(ResultSet rs) throws SQLException {
        dossierMedicale d = new dossierMedicale();
        d.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            d.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            d.setDateDerniereModification(dateModifSql.toLocalDateTime());

        d.setCreePar(getStringOrNull(rs, "creePar"));
        d.setModifierPar(getStringOrNull(rs, "modifierPar"));

        d.setIdDM(getLongOrNull(rs, "idDM"));

        Timestamp ddc = rs.getTimestamp("dateDeCreation");
        if (ddc != null) {
            d.setDateDeCreation(ddc.toLocalDateTime());
        }

        d.setPatientId(getLongOrNull(rs, "patientId"));
        return d;
    }

    public static interventionMedecin mapInterventionMedecin(ResultSet rs) throws SQLException {
        interventionMedecin i = new interventionMedecin();
        i.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            i.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            i.setDateDerniereModification(dateModifSql.toLocalDateTime());

        i.setCreePar(getStringOrNull(rs, "creePar"));
        i.setModifierPar(getStringOrNull(rs, "modifierPar"));

        i.setIdIM(getLongOrNull(rs, "idIM"));
        i.setPrixDePatient(rs.getDouble("prixDePatient"));
        i.setNumDent(rs.getInt("numDent"));
        return i;
    }

    public static medicaments mapMedicaments(ResultSet rs) throws SQLException {
        medicaments m = new medicaments();
        m.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            m.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            m.setDateDerniereModification(dateModifSql.toLocalDateTime());

        m.setCreePar(getStringOrNull(rs, "creePar"));
        m.setModifierPar(getStringOrNull(rs, "modifierPar"));

        m.setIdMed(getLongOrNull(rs, "idMed"));
        m.setNom(getStringOrNull(rs, "nom"));
        m.setLaboratoire(getStringOrNull(rs, "laboratoire"));
        m.setType(getStringOrNull(rs, "type"));
        m.setRemboursable(rs.getBoolean("remboursable"));
        m.setPrixUnitaire(rs.getDouble("prixUnitaire"));
        m.setDescription(getStringOrNull(rs, "description"));
        return m;
    }

    public static ordonnance mapOrdonnance(ResultSet rs) throws SQLException {
        ordonnance o = new ordonnance();
        o.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            o.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            o.setDateDerniereModification(dateModifSql.toLocalDateTime());

        o.setCreePar(getStringOrNull(rs, "creePar"));
        o.setModifierPar(getStringOrNull(rs, "modifierPar"));

        o.setIdOrd(getLongOrNull(rs, "idOrd"));
        Date dateSql = rs.getDate("date");
        if (dateSql != null)
            o.setDate(dateSql.toLocalDate());
        return o;
    }

    public static prescription mapPrescription(ResultSet rs) throws SQLException {
        prescription p = new prescription();
        p.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            p.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            p.setDateDerniereModification(dateModifSql.toLocalDateTime());

        p.setCreePar(getStringOrNull(rs, "creePar"));
        p.setModifierPar(getStringOrNull(rs, "modifierPar"));

        p.setIdPr(getLongOrNull(rs, "idPr"));
        p.setQuantite(rs.getInt("quantite"));
        p.setFrequence(getStringOrNull(rs, "frequence"));
        p.setDureeEnjours(rs.getInt("dureeEnjours"));
        return p;
    }

    public static rdv mapRdv(ResultSet rs) throws SQLException {
        rdv r = new rdv();
        r.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            r.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            r.setDateDerniereModification(dateModifSql.toLocalDateTime());

        r.setCreePar(getStringOrNull(rs, "creePar"));
        r.setModifierPar(getStringOrNull(rs, "modifierPar"));

        r.setIdRDV(getLongOrNull(rs, "idRDV"));

        Date dateSql = rs.getDate("date");
        if (dateSql != null)
            r.setDate(dateSql.toLocalDate());

        Time heureSql = rs.getTime("heure");
        if (heureSql != null)
            r.setHeure(heureSql.toLocalTime());

        r.setMotif(getStringOrNull(rs, "motif"));

        String statutStr = getStringOrNull(rs, "statut");
        if (statutStr != null)
            r.setStatut(Statut.valueOf(statutStr));

        r.setNoteMedecin(getStringOrNull(rs, "noteMedecin"));
        return r;
    }

    public static revenues mapRevenues(ResultSet rs) throws SQLException {
        revenues r = new revenues();
        r.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            r.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            r.setDateDerniereModification(dateModifSql.toLocalDateTime());

        r.setCreePar(getStringOrNull(rs, "creePar"));
        r.setModifierPar(getStringOrNull(rs, "modifierPar"));

        r.setTitre(getStringOrNull(rs, "titre"));
        r.setDescription(getStringOrNull(rs, "description"));
        r.setMontant(rs.getDouble("montant"));

        Timestamp dateSql = rs.getTimestamp("date");
        if (dateSql != null)
            r.setDate(dateSql.toLocalDateTime());
        return r;
    }

    public static role mapRole(ResultSet rs) throws SQLException {
        role r = new role();
        r.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            r.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            r.setDateDerniereModification(dateModifSql.toLocalDateTime());

        r.setCreePar(getStringOrNull(rs, "creePar"));
        r.setModifierPar(getStringOrNull(rs, "modifierPar"));

        r.setIdRole(getLongOrNull(rs, "idRole"));

        String libellerStr = getStringOrNull(rs, "libeller");
        if (libellerStr != null)
            r.setLibeller(Libeller.valueOf(libellerStr));
        return r;
    }

    public static staff mapStaff(ResultSet rs) throws SQLException {
        staff s = new staff();
        s.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            s.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            s.setDateDerniereModification(dateModifSql.toLocalDateTime());

        s.setCreePar(getStringOrNull(rs, "creePar"));
        s.setModifierPar(getStringOrNull(rs, "modifierPar"));

        s.setIdUser(getLongOrNull(rs, "idUser"));
        s.setNom(getStringOrNull(rs, "nom"));
        s.setEmail(getStringOrNull(rs, "email"));
        s.setAdresse(getStringOrNull(rs, "adresse"));
        s.setCin(getStringOrNull(rs, "cin"));
        s.setTel(getStringOrNull(rs, "tel"));

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null)
            s.setSexe(Sexe.valueOf(sexeStr));

        s.setLogin(getStringOrNull(rs, "login"));
        s.setMotDePasse(getStringOrNull(rs, "motDePasse"));

        Date lastLoginSql = rs.getDate("lastLoginDate");
        if (lastLoginSql != null)
            s.setLastLoginDate(lastLoginSql.toLocalDate());

        Date dateNaissanceSql = rs.getDate("dateNaissance");
        if (dateNaissanceSql != null)
            s.setDateNaissance(dateNaissanceSql.toLocalDate());

        s.setSalaire(rs.getDouble("salaire"));
        s.setPrime(rs.getDouble("prime"));

        Date dateRecrutementSql = rs.getDate("dateRecrutement");
        if (dateRecrutementSql != null)
            s.setDateRecrutement(dateRecrutementSql.toLocalDate());

        s.setSoldeConge(rs.getInt("soldeConge"));
        return s;
    }

    public static situationFinanciere mapSituationFinanciere(ResultSet rs) throws SQLException {
        situationFinanciere sf = new situationFinanciere();
        sf.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            sf.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            sf.setDateDerniereModification(dateModifSql.toLocalDateTime());

        sf.setCreePar(getStringOrNull(rs, "creePar"));
        sf.setModifierPar(getStringOrNull(rs, "modifierPar"));

        sf.setIdSF(getLongOrNull(rs, "idSF"));
        sf.setTotaleDesActes(rs.getDouble("totaleDesActes"));
        sf.setTotalPaye(rs.getDouble("totalPaye"));
        sf.setCredit(rs.getDouble("credit"));

        String statutStr = getStringOrNull(rs, "statut");
        if (statutStr != null)
            sf.setStatut(Statut.valueOf(statutStr));

        String promoStr = getStringOrNull(rs, "enPromo");
        if (promoStr != null)
            sf.setEnPromo(Promo.valueOf(promoStr));
        return sf;
    }

    public static secretaire mapSecretaire(ResultSet rs) throws SQLException {
        secretaire s = new secretaire();
        s.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            s.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            s.setDateDerniereModification(dateModifSql.toLocalDateTime());

        s.setCreePar(getStringOrNull(rs, "creePar"));
        s.setModifierPar(getStringOrNull(rs, "modifierPar"));

        s.setIdUser(getLongOrNull(rs, "idUser"));
        s.setNom(getStringOrNull(rs, "nom"));
        s.setEmail(getStringOrNull(rs, "email"));
        s.setAdresse(getStringOrNull(rs, "adresse"));
        s.setCin(getStringOrNull(rs, "cin"));
        s.setTel(getStringOrNull(rs, "tel"));

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null)
            s.setSexe(Sexe.valueOf(sexeStr));

        s.setLogin(getStringOrNull(rs, "login"));
        s.setMotDePasse(getStringOrNull(rs, "motDePasse"));

        Date lastLoginSql = rs.getDate("lastLoginDate");
        if (lastLoginSql != null)
            s.setLastLoginDate(lastLoginSql.toLocalDate());

        Date dateNaissanceSql = rs.getDate("dateNaissance");
        if (dateNaissanceSql != null)
            s.setDateNaissance(dateNaissanceSql.toLocalDate());

        s.setSalaire(rs.getDouble("salaire"));
        s.setPrime(rs.getDouble("prime"));

        Date dateRecrutementSql = rs.getDate("dateRecrutement");
        if (dateRecrutementSql != null)
            s.setDateRecrutement(dateRecrutementSql.toLocalDate());

        s.setSoldeConge(rs.getInt("soldeConge"));
        s.setNumCNSS(getStringOrNull(rs, "numCNSS"));
        s.setCommission(rs.getDouble("commission"));
        return s;
    }

    public static utilisateur mapUtilisateur(ResultSet rs) throws SQLException {
        utilisateur u = new utilisateur();
        u.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            u.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            u.setDateDerniereModification(dateModifSql.toLocalDateTime());

        u.setCreePar(getStringOrNull(rs, "creePar"));
        u.setModifierPar(getStringOrNull(rs, "modifierPar"));

        u.setIdUser(getLongOrNull(rs, "idUser"));
        u.setNom(getStringOrNull(rs, "nom"));
        u.setEmail(getStringOrNull(rs, "email"));
        u.setAdresse(getStringOrNull(rs, "adresse"));
        u.setCin(getStringOrNull(rs, "cin"));
        u.setTel(getStringOrNull(rs, "tel"));

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null)
            u.setSexe(Sexe.valueOf(sexeStr));

        u.setLogin(getStringOrNull(rs, "login"));
        u.setMotDePasse(getStringOrNull(rs, "motDePasse"));

        Date lastLoginSql = rs.getDate("lastLoginDate");
        if (lastLoginSql != null)
            u.setLastLoginDate(lastLoginSql.toLocalDate());

        Date dateNaissanceSql = rs.getDate("dateNaissance");
        if (dateNaissanceSql != null)
            u.setDateNaissance(dateNaissanceSql.toLocalDate());
        return u;
    }

    public static log mapLog(ResultSet rs) throws SQLException {
        log l = new log();
        l.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            l.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            l.setDateDerniereModification(dateModifSql.toLocalDateTime());

        l.setCreePar(getStringOrNull(rs, "creePar"));
        l.setModifierPar(getStringOrNull(rs, "modifierPar"));

        l.setIdLog(getLongOrNull(rs, "idLog"));
        l.setAction(getStringOrNull(rs, "action"));
        l.setUtilisateur(getStringOrNull(rs, "utilisateur"));

        Timestamp dateActionSql = rs.getTimestamp("dateAction");
        if (dateActionSql != null)
            l.setDateAction(dateActionSql.toLocalDateTime());

        l.setDescription(getStringOrNull(rs, "description"));
        l.setAdresseIP(getStringOrNull(rs, "adresseIP"));
        return l;
    }

    public static facture mapFacture(ResultSet rs) throws SQLException {
        facture f = new facture();
        f.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            f.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            f.setDateDerniereModification(dateModifSql.toLocalDateTime());

        f.setCreePar(getStringOrNull(rs, "creePar"));
        f.setModifierPar(getStringOrNull(rs, "modifierPar"));

        f.setIdFacture(getLongOrNull(rs, "idFacture"));
        f.setConsultationId(getLongOrNull(rs, "consultationId"));
        f.setPatientId(getLongOrNull(rs, "patientId"));
        f.setTotaleFacture(rs.getDouble("totaleFacture"));
        f.setTotalPaye(rs.getDouble("totalPaye"));
        f.setReste(rs.getDouble("reste"));

        String statutStr = getStringOrNull(rs, "statut");
        if (statutStr != null)
            f.setStatut(Statut.valueOf(statutStr));

        Timestamp dateFactureSql = rs.getTimestamp("dateFacture");
        if (dateFactureSql != null)
            f.setDateFacture(dateFactureSql.toLocalDateTime());
        return f;
    }

    public static medecin mapMedecin(ResultSet rs) throws SQLException {
        medecin m = new medecin();
        m.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            m.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            m.setDateDerniereModification(dateModifSql.toLocalDateTime());

        m.setCreePar(getStringOrNull(rs, "creePar"));
        m.setModifierPar(getStringOrNull(rs, "modifierPar"));

        m.setIdUser(getLongOrNull(rs, "idUser"));
        m.setNom(getStringOrNull(rs, "nom"));
        m.setEmail(getStringOrNull(rs, "email"));
        m.setAdresse(getStringOrNull(rs, "adresse"));
        m.setCin(getStringOrNull(rs, "cin"));
        m.setTel(getStringOrNull(rs, "tel"));

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null)
            m.setSexe(Sexe.valueOf(sexeStr));

        m.setLogin(getStringOrNull(rs, "login"));
        m.setMotDePasse(getStringOrNull(rs, "motDePasse"));

        Date lastLoginSql = rs.getDate("lastLoginDate");
        if (lastLoginSql != null)
            m.setLastLoginDate(lastLoginSql.toLocalDate());

        Date dateNaissanceSql = rs.getDate("dateNaissance");
        if (dateNaissanceSql != null)
            m.setDateNaissance(dateNaissanceSql.toLocalDate());

        m.setSalaire(rs.getDouble("salaire"));
        m.setPrime(rs.getDouble("prime"));

        Date dateRecrutementSql = rs.getDate("dateRecrutement");
        if (dateRecrutementSql != null)
            m.setDateRecrutement(dateRecrutementSql.toLocalDate());

        m.setSoldeConge(rs.getInt("soldeConge"));

        m.setIdMedecin(getLongOrNull(rs, "idMedecin"));
        m.setSpecialite(getStringOrNull(rs, "specialite"));
        m.setNumeroOrdre(getStringOrNull(rs, "numeroOrdre"));
        m.setDiplome(getStringOrNull(rs, "diplome"));

        return m;
    }

    public static notification mapNotification(ResultSet rs) throws SQLException {
        notification n = new notification();
        n.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            n.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            n.setDateDerniereModification(dateModifSql.toLocalDateTime());

        n.setCreePar(getStringOrNull(rs, "creePar"));
        n.setModifierPar(getStringOrNull(rs, "modifierPar"));

        n.setIdNotif(getLongOrNull(rs, "idNotif"));
        n.setTitre(getStringOrNull(rs, "titre"));
        n.setMessage(getStringOrNull(rs, "message"));

        Timestamp dateEnvoiSql = rs.getTimestamp("dateEnvoi");
        if (dateEnvoiSql != null)
            n.setDateEnvoi(dateEnvoiSql.toLocalDateTime());

        n.setType(getStringOrNull(rs, "type"));
        n.setLue(rs.getBoolean("lue"));
        return n;
    }

    public static caisse mapCaisse(ResultSet rs) throws SQLException {
        caisse c = new caisse();
        c.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            c.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            c.setDateDerniereModification(dateModifSql.toLocalDateTime());

        c.setCreePar(getStringOrNull(rs, "creePar"));
        c.setModifierPar(getStringOrNull(rs, "modifierPar"));

        c.setIdCaisse(getLongOrNull(rs, "idCaisse"));
        c.setFactureId(getLongOrNull(rs, "factureId"));
        c.setMontant(rs.getDouble("montant"));

        Date dateEncaissementSql = rs.getDate("dateEncaissement");
        if (dateEncaissementSql != null)
            c.setDateEncaissement(dateEncaissementSql.toLocalDate());

        c.setModeEncaissement(getStringOrNull(rs, "modeEncaissement"));
        c.setReference(getStringOrNull(rs, "reference"));

        return c;
    }

    public static agenda mapAgenda(ResultSet rs) throws SQLException {
        agenda a = new agenda();
        a.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            a.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            a.setDateDerniereModification(dateModifSql.toLocalDateTime());

        a.setCreePar(getStringOrNull(rs, "creePar"));
        a.setModifierPar(getStringOrNull(rs, "modifierPar"));

        a.setIdAgenda(getLongOrNull(rs, "idAgenda"));
        a.setMedecinId(getLongOrNull(rs, "medecinId"));

        String moisStr = getStringOrNull(rs, "mois");
        if (moisStr != null)
            a.setMois(Mois.valueOf(moisStr));

        Date dateDebutSql = rs.getDate("dateDebut");
        if (dateDebutSql != null)
            a.setDateDebut(dateDebutSql.toLocalDate());

        Date dateFinSql = rs.getDate("dateFin");
        if (dateFinSql != null)
            a.setDateFin(dateFinSql.toLocalDate());

        String joursStr = getStringOrNull(rs, "joursDisponible");
        if (joursStr != null && !joursStr.isEmpty()) {
            java.util.List<Jour> jours = new java.util.ArrayList<>();
            for (String j : joursStr.split(",")) {
                try {
                    jours.add(Jour.valueOf(j.trim()));
                } catch (IllegalArgumentException e) {

                }
            }
            a.setJoursDisponible(jours);
        }

        return a;
    }
}
