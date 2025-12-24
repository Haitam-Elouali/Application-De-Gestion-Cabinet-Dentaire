package ma.TeethCare.repository.common;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.entities.log.log;
import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.common.enums.*;
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
import ma.TeethCare.entities.statistique.statistique;

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

        if (hasColumn(rs, "id"))
            p.setIdEntite(getLongOrNull(rs, "id"));
        else
            p.setIdEntite(getLongOrNull(rs, "idEntite"));

        p.setNom(getStringOrNull(rs, "nom"));
        p.setPrenom(getStringOrNull(rs, "prenom"));
        // p.setAdresse(getStringOrNull(rs, "adresse")); // Removed from entity
        p.setTelephone(getStringOrNull(rs, "telephone"));
        // p.setEmail(getStringOrNull(rs, "email")); // Removed from entity

        Date dn = rs.getDate("dateNaissance");
        if (dn != null)
            p.setDateNaissance(dn.toLocalDate());

        // dateCreation might be in baseEntity, mapped generically or here if needed

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null) {
            try {
                p.setSexe(Sexe.valueOf(sexeStr));
            } catch (Exception e) {
            }
        }

        String assuranceStr = getStringOrNull(rs, "assurance");
        if (assuranceStr != null) {
            try {
                p.setAssurance(Assurance.valueOf(assuranceStr));
            } catch (Exception e) {
            }
        }

        return p;
    }

    public static antecedent mapAntecedent(ResultSet rs) throws SQLException {
        antecedent a = new antecedent();
        a.setIdEntite(getLongOrNull(rs, "id"));
        a.setId(getLongOrNull(rs, "id"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            a.setDateCreation(dateCreationSql.toLocalDate());

        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            a.setDateDerniereModification(dateModifSql.toLocalDateTime());

        a.setCreePar(getStringOrNull(rs, "creePar"));
        a.setModifierPar(getStringOrNull(rs, "modifierPar"));

        a.setNom(getStringOrNull(rs, "nom"));
        a.setCategorie(getStringOrNull(rs, "categorie"));

        String niveauRisqueStr = getStringOrNull(rs, "niveauDeRisque"); // Schema name
        if (niveauRisqueStr == null)
            niveauRisqueStr = getStringOrNull(rs, "niveauRisque");

        if (niveauRisqueStr != null) {
            try {
                a.setNiveauDeRisque(niveauDeRisque.valueOf(niveauRisqueStr));
            } catch (Exception e) {
            }
        }

        // dossierMedicale relation
        Long dmId = getLongOrNull(rs, "dossierMedicale_id");
        if (dmId == null)
            dmId = getLongOrNull(rs, "dossierMedicaleId");

        if (dmId != null) {
            dossierMedicale dm = new dossierMedicale();
            dm.setIdEntite(dmId);
            a.setDossierMedicale(dm);
        }

        return a;
    }

    public static actes mapActes(ResultSet rs) throws SQLException {
        actes entity = new actes();
        entity.setIdEntite(getLongOrNull(rs, "id"));
        if (entity.getIdEntite() == null) {
            entity.setIdEntite(getLongOrNull(rs, "idEntite"));
        }

        String nom = getStringOrNull(rs, "nom");
        if (nom == null)
            nom = getStringOrNull(rs, "libeller");
        entity.setNom(nom); // Field renamed to nom

        entity.setCategorie(getStringOrNull(rs, "categorie"));
        entity.setDescription(getStringOrNull(rs, "description")); // New field

        Double prix = null;
        try {
            prix = rs.getDouble("prix");
        } catch (SQLException e) {
        }
        if (prix == null || prix == 0.0) { // check if 0.0 returned for null double
            try {
                prix = rs.getDouble("prixDeBase");
            } catch (SQLException e) {
            }
        }
        if (prix != null)
            entity.setPrix(prix); // Field renamed to prix

        if (hasColumn(rs, "code"))
            entity.setCode(getStringOrNull(rs, "code"));
        else if (hasColumn(rs, "codeSECU"))
            entity.setCode(getStringOrNull(rs, "codeSECU"));

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
        entity.setIdEntite(getLongOrNull(rs, "id"));
        if (entity.getIdEntite() == null)
            entity.setIdEntite(getLongOrNull(rs, "idEntite"));

        // Fix: Set idUser as well since delete() relies on it.
        // entity.setIdUser(entity.getIdEntite()); // entity now uses id from
        // BaseEntity/Utilisateur
        entity.setId(getLongOrNull(rs, "id"));
        if (entity.getId() == null)
            entity.setId(getLongOrNull(rs, "idUser"));
        if (entity.getId() == null)
            entity.setId(getLongOrNull(rs, "idEntite"));

        // Using getStringOrNull to avoid crash if mismatched, but target is
        // 'permissionAdmin' (missing in DB)
        // entity.setPermissionAdmin(getStringOrNull(rs, "permissionAdmin")); // Removed
        // entity.setDomaine(getStringOrNull(rs, "domaine")); // Removed

        entity.setNom(getStringOrNull(rs, "nom"));
        entity.setPrenom(getStringOrNull(rs, "prenom")); // New

        entity.setEmail(getStringOrNull(rs, "email"));

        // Mapping 'tele' from DB to 'tel' field -> Entity 'telephone'
        String tel = getStringOrNull(rs, "tele");
        if (tel == null)
            tel = getStringOrNull(rs, "telephone"); // fallback
        if (tel == null)
            tel = getStringOrNull(rs, "tel"); // fallback
        entity.setTelephone(tel);

        entity.setCin(getStringOrNull(rs, "cin"));
        entity.setAdresse(getStringOrNull(rs, "adresse"));
        entity.setImage(getStringOrNull(rs, "image"));

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null) {
            try {
                entity.setSexe(ma.TeethCare.common.enums.Sexe.valueOf(sexeStr));
            } catch (Exception e) {
            }
        }

        // Mapping 'username'
        String login = getStringOrNull(rs, "username");
        if (login == null)
            login = getStringOrNull(rs, "login");
        entity.setUsername(login);

        // Mapping 'password'
        String pwd = getStringOrNull(rs, "password");
        if (pwd == null)
            pwd = getStringOrNull(rs, "motDePasse");
        entity.setPassword(pwd);

        if (hasColumn(rs, "dateCreation")) {
            Date d = rs.getDate("dateCreation");
            if (d != null)
                entity.setDateCreation(d.toLocalDate());
        }
        if (hasColumn(rs, "dateModification") || hasColumn(rs, "dateDerniereModification")) {
            Timestamp t = null;
            try {
                t = rs.getTimestamp("dateDerniereModification");
            } catch (SQLException e) {
            }
            if (t == null)
                try {
                    t = rs.getTimestamp("dateModification");
                } catch (SQLException e) {
                }

            if (t != null)
                entity.setDateDerniereModification(t.toLocalDateTime());
        }

        // cabinetMedicale removed from Admin entity?
        // "Removed Fields: ... cabinetMedicale"
        /*
         * Long cabinetId = getLongOrNull(rs, "cabinetId");
         * if (cabinetId != null) {
         * cabinetMedicale c = new cabinetMedicale();
         * c.setIdEntite(cabinetId);
         * entity.setCabinetMedicale(c);
         * }
         */
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

        c.setNomCabinet(getStringOrNull(rs, "nomCabinet")); // Was nom
        if (c.getNomCabinet() == null)
            c.setNomCabinet(getStringOrNull(rs, "nom"));

        c.setEmail(getStringOrNull(rs, "email"));
        c.setLogo(getStringOrNull(rs, "logo"));
        // c.setCin(getStringOrNull(rs, "cin")); // Removed from entity

        c.setTele(getStringOrNull(rs, "tele")); // Was tel1
        if (c.getTele() == null)
            c.setTele(getStringOrNull(rs, "tel1"));

        // c.setTel2(getStringOrNull(rs, "tel2")); // Removed
        c.setSiteWeb(getStringOrNull(rs, "siteWeb")); // Check if removed? Schema doesn't list it. Assuming user wants
                                                      // strict sync.
        // c.setInstagram(getStringOrNull(rs, "instagram")); // Removed
        // c.setFacebook(getStringOrNull(rs, "facebook")); // Removed
        c.setAdresse(getStringOrNull(rs, "adresse")); // Was description? No, schema has adresse. Description removed?
        // c.setDescription(getStringOrNull(rs, "description"));
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

        c.setId(getLongOrNull(rs, "id")); // Was idCertif
        if (c.getId() == null)
            c.setId(getLongOrNull(rs, "idCertif"));

        Date dateDebutSql = rs.getDate("dateDebut");
        if (dateDebutSql != null)
            c.setDateDebut(dateDebutSql.toLocalDate());

        Date dateFinSql = rs.getDate("dateFin");
        if (dateFinSql != null)
            c.setDateFin(dateFinSql.toLocalDate());

        // c.setDuree(rs.getInt("duree"));
        c.setNote(getStringOrNull(rs, "note")); // Was noteMedecin
        if (c.getNote() == null)
            c.setNote(getStringOrNull(rs, "noteMedecin"));

        Long consultId = getLongOrNull(rs, "consultationId");
        if (consultId != null) {
            consultation cons = new consultation();
            cons.setIdEntite(consultId);
            c.setConsultation(cons);
        }

        /*
         * Long medId = getLongOrNull(rs, "medecinId");
         * if (medId != null) {
         * medecin m = new medecin();
         * m.setIdEntite(medId);
         * c.setMedecin(m);
         * }
         * Long patId = getLongOrNull(rs, "patientId");
         * if (patId != null) {
         * Patient p = new Patient();
         * p.setIdEntite(patId);
         * c.setPatient(p);
         * }
         */
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
        c.setId(getLongOrNull(rs, "id")); // Was idCharge/id
        if (c.getId() == null)
            c.setId(getLongOrNull(rs, "idCharge"));

        Timestamp dateSql = rs.getTimestamp("date");
        if (dateSql != null)
            c.setDate(dateSql.toLocalDateTime());

        Long cabId = getLongOrNull(rs, "cabinetId");
        if (cabId != null) {
            c.setCabinetId(cabId);
            cabinetMedicale cab = new cabinetMedicale();
            cab.setIdEntite(cabId);
            c.setCabinetMedicale(cab);
        }
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

        c.setId(getLongOrNull(rs, "id"));
        if (c.getId() == null)
            c.setId(getLongOrNull(rs, "idConsultation"));

        Date dateSql = rs.getDate("Date");
        if (dateSql != null)
            c.setDate(dateSql.toLocalDate());

        String statutStr = getStringOrNull(rs, "statut");
        if (statutStr != null) {
            try {
                c.setStatut(Statut.valueOf(statutStr));
            } catch (Exception e) {
            }
        }

        c.setObservation(getStringOrNull(rs, "observation")); // Was observationMedecin
        if (c.getObservation() == null)
            c.setObservation(getStringOrNull(rs, "observationMedecin"));

        c.setDiagnostic(getStringOrNull(rs, "diagnostic")); // Was diagnostique
        if (c.getDiagnostic() == null)
            c.setDiagnostic(getStringOrNull(rs, "diagnostique"));

        c.setMotif(getStringOrNull(rs, "motif")); // New field

        c.setPatientId(getLongOrNull(rs, "patientId"));
        c.setMedecinId(getLongOrNull(rs, "medecinId"));

        // Removed rdvId from schema?
        /*
         * Long rdvId = getLongOrNull(rs, "rdvId");
         * if (rdvId != null) {
         * rdv r = new rdv();
         * r.setIdEntite(rdvId);
         * c.setRdv(r);
         * }
         */
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

        d.setId(getLongOrNull(rs, "id"));
        if (d.getId() == null)
            d.setId(getLongOrNull(rs, "idDM"));

        Timestamp ddc = rs.getTimestamp("dateDeCreation");
        if (ddc != null) {
            d.setDateDeCreation(ddc.toLocalDateTime());
        }

        d.setPatientId(getLongOrNull(rs, "patientId"));
        if (d.getPatientId() != null) {
            Patient p = new Patient();
            p.setIdEntite(d.getPatientId());
            d.setPatient(p);
        }
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

        i.setId(getLongOrNull(rs, "id"));
        if (i.getId() == null)
            i.setId(getLongOrNull(rs, "idIM"));

        // Fields removed from schema:
        // prixDePatient, numDent, medecinId, acteId

        // Added fields:
        i.setDuree(rs.getInt("duree"));
        i.setNote(getStringOrNull(rs, "note"));
        i.setResultatImagerie(getStringOrNull(rs, "resultatImagerie"));

        // i.setPrixDePatient(rs.getDouble("prixDePatient"));
        // i.setNumDent(rs.getInt("numDent"));

        /*
         * Long medId = getLongOrNull(rs, "medecinId");
         * if (medId != null) {
         * medecin m = new medecin();
         * m.setIdEntite(medId);
         * i.setMedecin(m);
         * }
         * Long acteId = getLongOrNull(rs, "acteId");
         * if (acteId != null) {
         * actes a = new actes();
         * a.setIdEntite(acteId);
         * i.setActe(a);
         * }
         */
        Long consId = getLongOrNull(rs, "consultationId");
        if (consId != null) {
            i.setConsultationId(consId); // Fix: Set the ID field directly
            consultation cons = new consultation();
            cons.setIdEntite(consId);
            i.setConsultation(cons);
        }
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

        m.setId(getLongOrNull(rs, "id"));
        if (m.getId() == null)
            m.setId(getLongOrNull(rs, "idMed"));

        m.setNomCommercial(getStringOrNull(rs, "nomCommercial")); // Was nom
        if (m.getNomCommercial() == null)
            m.setNomCommercial(getStringOrNull(rs, "nom"));

        m.setPrincipeActif(getStringOrNull(rs, "principeActif")); // New
        m.setForme(getStringOrNull(rs, "forme"));
        m.setDosage(getStringOrNull(rs, "dosage")); // New

        // m.setLaboratoire(getStringOrNull(rs, "laboratoire")); // Potentially removed
        // or kept? Schema doesn't list it clearly. Assuming keeping for now if not
        // strictly removed. "Modified Fields: idAct -> id, libeller -> nom, prixDeBase
        // -> prix, codeSECU -> code." "Added Fields: description, interventionId."
        // "Removed Fields: ..."
        // Wait, this is Medicaments. 348 says: "Modified fields: idMed -> id, nom ->
        // nomCommercial." "Added fields: principeActif, dosage." "Removed fields:
        // forms??". "forme" is present in lines I added in 352.

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

        o.setId(getLongOrNull(rs, "id"));
        if (o.getId() == null)
            o.setId(getLongOrNull(rs, "idOrd"));
        Date dateSql = rs.getDate("dateOrdonnance");
        if (dateSql != null)
            o.setDateOrdonnance(dateSql.toLocalDate());

        Long consultId = getLongOrNull(rs, "consultationId");
        if (consultId != null) {
            o.setConsultationId(consultId);
            consultation c = new consultation();
            c.setIdEntite(consultId);
            o.setConsultation(c);
        }
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

        p.setId(getLongOrNull(rs, "id"));
        if (p.getId() == null)
            p.setId(getLongOrNull(rs, "idPr"));

        p.setQuantite(rs.getInt("quantite"));
        p.setPosologie(getStringOrNull(rs, "posologie")); // New
        // p.setFrequence(getStringOrNull(rs, "frequence")); // Removed
        p.setDureeEnJours(rs.getInt("dureeEnjours")); // Capital J change? check schema/entity. Entity 'dureeEnJours'
                                                      // lines 367.
        // RS column might still be 'dureeEnjours'. map it.
        if (p.getDureeEnJours() == 0)
            p.setDureeEnJours(rs.getInt("dureeEnJours"));

        Long ordId = getLongOrNull(rs, "ordonnanceId");
        if (ordId != null) {
            p.setOrdonnanceId(ordId);
            ordonnance o = new ordonnance();
            o.setIdEntite(ordId);
            p.setOrdonnance(o);
        }
        Long medId = getLongOrNull(rs, "medicamentId");
        if (medId != null) {
            p.setMedicamentId(medId);
            medicaments m = new medicaments();
            m.setIdEntite(medId);
            p.setMedicament(m);
        }
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

        r.setId(getLongOrNull(rs, "id"));
        if (r.getId() == null)
            r.setId(getLongOrNull(rs, "idRDV"));

        r.setNumero(getLongOrNull(rs, "numero")); // New

        Date dateSql = rs.getDate("date");
        if (dateSql != null)
            r.setDate(dateSql.toLocalDate());

        Time heureSql = rs.getTime("heure");
        if (heureSql != null)
            r.setHeure(heureSql.toLocalTime());

        // r.setMotif(getStringOrNull(rs, "motif")); // Removed

        String statutStr = getStringOrNull(rs, "statut");
        if (statutStr != null) {
            try {
                r.setStatut(Statut.valueOf(statutStr));
            } catch (Exception e) {
            }
        }

        // r.setNoteMedecin(getStringOrNull(rs, "noteMedecin")); // Removed

        Long patId = getLongOrNull(rs, "patientId");
        if (patId != null) {
            r.setPatientId(patId);
            Patient p = new Patient();
            p.setIdEntite(patId);
            r.setPatient(p);
        }

        Long sfId = getLongOrNull(rs, "situationFinanciereId");
        if (sfId != null) {
            r.setSituationfinancierId(sfId);
            situationFinanciere sf = new situationFinanciere();
            sf.setIdEntite(sfId);
            r.setSituationFinanciere(sf);
        }

        // Medecin removed from RDV entity?
        /*
         * Long medId = getLongOrNull(rs, "medecinId");
         * if (medId != null) {
         * medecin m = new medecin();
         * m.setIdEntite(medId);
         * r.setMedecin(m);
         * }
         */
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

        r.setId(getLongOrNull(rs, "id"));
        if (r.getId() == null)
            r.setId(getLongOrNull(rs, "idRevenue"));

        r.setTitre(getStringOrNull(rs, "titre"));
        r.setDescription(getStringOrNull(rs, "description"));
        r.setMontant(rs.getDouble("montant"));
        r.setCategorie(getStringOrNull(rs, "categorie")); // New

        Timestamp dateSql = rs.getTimestamp("date");
        if (dateSql != null)
            r.setDate(dateSql.toLocalDateTime());

        // facture removed from Revenues?
        // "Removed Fields: factureId, ..."
        /*
         * Long facId = getLongOrNull(rs, "factureId");
         * if (facId != null) {
         * facture f = new facture();
         * f.setIdEntite(facId);
         * r.setFacture(f);
         * }
         */
        Long cabId = getLongOrNull(rs, "cabinetId");
        if (cabId != null) {
            r.setCabinetId(cabId);
        }
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

        r.setId(getLongOrNull(rs, "id"));
        if (r.getId() == null)
            r.setId(getLongOrNull(rs, "idRole"));

        String libelleStr = getStringOrNull(rs, "libelle");
        if (libelleStr == null)
            libelleStr = getStringOrNull(rs, "libeller"); // Fallback

        r.setLibelle(libelleStr); // String now, not Enum

        Long uId = getLongOrNull(rs, "utilisateur_id");
        if (uId != null) {
            r.setUtilisateurId(uId);
        }
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

        s.setId(getLongOrNull(rs, "id"));
        if (s.getId() == null)
            s.setId(getLongOrNull(rs, "idUser"));

        s.setNom(getStringOrNull(rs, "nom"));
        s.setPrenom(getStringOrNull(rs, "prenom")); // New
        s.setEmail(getStringOrNull(rs, "email"));
        s.setAdresse(getStringOrNull(rs, "adresse"));
        s.setCin(getStringOrNull(rs, "cin"));
        s.setImage(getStringOrNull(rs, "image")); // New

        String tel = getStringOrNull(rs, "tel");
        if (tel == null)
            tel = getStringOrNull(rs, "telephone");
        s.setTelephone(tel);

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null) {
            try {
                s.setSexe(Sexe.valueOf(sexeStr));
            } catch (Exception e) {
            }
        }

        String login = getStringOrNull(rs, "login");
        if (login == null)
            login = getStringOrNull(rs, "username");
        s.setUsername(login);

        String pwd = getStringOrNull(rs, "motDePasse");
        if (pwd == null)
            pwd = getStringOrNull(rs, "password");
        s.setPassword(pwd);

        // Date lastLoginSql = rs.getDate("lastLoginDate");
        // if (lastLoginSql != null)
        // s.setLastLoginDate(lastLoginSql.toLocalDate());

        Date dateNaissanceSql = rs.getDate("dateNaissance");
        if (dateNaissanceSql != null)
            s.setDateNaissance(dateNaissanceSql.toLocalDate());

        s.setSalaire(rs.getDouble("salaire"));
        // s.setPrime(rs.getDouble("prime")); // Removed from entity

        Date dateEmbaucheSql = rs.getDate("dateEmbauche");
        if (dateEmbaucheSql != null)
            s.setDateEmbauche(dateEmbaucheSql.toLocalDate());
        else {
            Date dateRecrutementSql = rs.getDate("dateRecrutement");
            if (dateRecrutementSql != null)
                s.setDateEmbauche(dateRecrutementSql.toLocalDate());
        }

        // s.setSoldeConge(rs.getInt("soldeConge")); // Removed

        Long cabId = getLongOrNull(rs, "cabinetId");
        if (cabId != null) {
            cabinetMedicale c = new cabinetMedicale();
            c.setIdEntite(cabId);
            s.setCabinetMedicale(c);
        }
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

        sf.setId(getLongOrNull(rs, "id"));
        if (sf.getId() == null)
            sf.setId(getLongOrNull(rs, "idSF"));

        sf.setTotalDesActes(rs.getDouble("totalDesActes"));
        // Fallback if needed, but schema uses totalDesActes
        if (sf.getTotalDesActes() == 0.0)
            sf.setTotalDesActes(rs.getDouble("montantGlobal"));

        sf.setTotalPaye(rs.getDouble("totalPaye"));
        if (sf.getTotalPaye() == 0.0)
            sf.setTotalPaye(rs.getDouble("montantPaye"));

        sf.setCredit(rs.getDouble("credit"));

        String statutStr = getStringOrNull(rs, "statut");
        if (statutStr != null) {
            try {
                sf.setStatut(Statut.valueOf(statutStr));
            } catch (Exception e) {
            }
        }

        String promoStr = getStringOrNull(rs, "enPromo");
        if (promoStr != null) {
            try {
                sf.setEnPromo(Promo.valueOf(promoStr));
            } catch (Exception e) {
            }
        }

        // Removed reste, date logic as not in schema/entity

        Long dmId = getLongOrNull(rs, "dossiermedicale_id");
        if (dmId == null)
            dmId = getLongOrNull(rs, "dossierId"); // alias fallback

        sf.setDossierMedicaleId(dmId);

        if (dmId != null) {
            dossierMedicale dm = new dossierMedicale();
            dm.setIdEntite(dmId);
            sf.setDossierMedicale(dm);
        }
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

        s.setId(getLongOrNull(rs, "id"));
        if (s.getId() == null)
            s.setId(getLongOrNull(rs, "idUser"));

        s.setNom(getStringOrNull(rs, "nom"));
        s.setPrenom(getStringOrNull(rs, "prenom")); // New

        s.setEmail(getStringOrNull(rs, "email"));
        s.setAdresse(getStringOrNull(rs, "adresse"));
        s.setCin(getStringOrNull(rs, "cin"));
        s.setImage(getStringOrNull(rs, "image"));

        String tel = getStringOrNull(rs, "tel");
        if (tel == null)
            tel = getStringOrNull(rs, "telephone");
        s.setTelephone(tel);

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null) {
            try {
                s.setSexe(Sexe.valueOf(sexeStr));
            } catch (Exception e) {
            }
        }

        String login = getStringOrNull(rs, "login");
        if (login == null)
            login = getStringOrNull(rs, "username");
        s.setUsername(login);

        String pwd = getStringOrNull(rs, "motDePasse");
        if (pwd == null)
            pwd = getStringOrNull(rs, "password");
        s.setPassword(pwd);

        // Date lastLoginSql = rs.getDate("lastLoginDate");
        // if (lastLoginSql != null)
        // s.setLastLoginDate(lastLoginSql.toLocalDate());

        Date dateNaissanceSql = rs.getDate("dateNaissance");
        if (dateNaissanceSql != null)
            s.setDateNaissance(dateNaissanceSql.toLocalDate());

        s.setSalaire(rs.getDouble("salaire"));
        // s.setPrime(rs.getDouble("prime"));

        Date dateEmbaucheSql = rs.getDate("dateEmbauche");
        if (dateEmbaucheSql != null)
            s.setDateEmbauche(dateEmbaucheSql.toLocalDate());
        else {
            Date dateRecrutementSql = rs.getDate("dateRecrutement");
            if (dateRecrutementSql != null)
                s.setDateEmbauche(dateRecrutementSql.toLocalDate());
        }

        // s.setSoldeConge(rs.getInt("soldeConge")); // Removed
        // s.setNumCNSS(getStringOrNull(rs, "numCNSS")); // Removed
        // s.setCommission(rs.getDouble("commission")); // Removed

        s.setCommission(rs.getInt("commission")); // New
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

        u.setId(getLongOrNull(rs, "id"));
        if (u.getId() == null)
            u.setId(getLongOrNull(rs, "idUser"));

        u.setNom(getStringOrNull(rs, "nom"));
        u.setPrenom(getStringOrNull(rs, "prenom")); // New

        u.setEmail(getStringOrNull(rs, "email"));
        u.setAdresse(getStringOrNull(rs, "adresse"));
        u.setCin(getStringOrNull(rs, "cin"));

        u.setImage(getStringOrNull(rs, "image")); // New

        // Mapping 'tele' -> 'telephone' (Entity has telephone)
        String tele = getStringOrNull(rs, "tele");
        if (tele == null)
            tele = getStringOrNull(rs, "telephone");
        if (tele == null)
            tele = getStringOrNull(rs, "tel");
        u.setTelephone(tele);

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null) {
            try {
                u.setSexe(Sexe.valueOf(sexeStr));
            } catch (Exception e) {
            }
        }

        // Mapping 'username' -> 'username' in Entity
        String username = getStringOrNull(rs, "username");
        if (username == null)
            username = getStringOrNull(rs, "login");
        u.setUsername(username);

        // Mapping 'password' -> 'password'
        String pwd = getStringOrNull(rs, "password");
        if (pwd == null)
            pwd = getStringOrNull(rs, "motDePasse");
        u.setPassword(pwd);

        // Date lastLoginSql = rs.getDate("lastLoginDate");
        // if (lastLoginSql != null)
        // u.setLastLoginDate(lastLoginSql.toLocalDate());

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

        // Schema has 'id' [PK], Entity expects idLog.
        // Queries usually select id as idLog, let's stick to that if repository does
        // alias.
        // But mapLog typically reads what is in RS.
        // Schema has 'id' [PK], Entity expects id
        l.setId(getLongOrNull(rs, "id"));
        if (l.getId() == null)
            l.setId(getLongOrNull(rs, "idLog"));

        // Mapping Schema 'typeSupp' -> Entity 'typeSupp'
        l.setTypeSupp(getStringOrNull(rs, "typeSupp"));

        // Mapping Schema 'message' -> Entity 'message'
        l.setMessage(getStringOrNull(rs, "message"));
        if (l.getMessage() == null)
            l.setMessage(getStringOrNull(rs, "description"));

        Timestamp dateActionSql = rs.getTimestamp("dateAction");
        // timestamp might be dateEnvoi? No, schema says 'date', 'heure'.
        // Entity log added 'date' ? Step 348 "Modified: idLog->id,
        // description->message. Added: utilisateurId, typeSupp."
        // removed action, utilisateur...
        // Does Log entity have dateAction?

        if (dateActionSql != null) {
            // l.setDateAction(dateActionSql.toLocalDateTime()); // If field exists
        }

        // adresseIP not in schema
        // l.setAdresseIP(getStringOrNull(rs, "adresseIP"));

        Long userId = getLongOrNull(rs, "utilisateur_id");
        if (userId != null) {
            l.setUtilisateurId(userId);
            utilisateur u = new utilisateur();
            u.setIdEntite(userId);
            l.setUtilisateurEntity(u); // If relation exists
        }
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

        f.setId(getLongOrNull(rs, "id"));
        if (f.getId() == null)
            f.setId(getLongOrNull(rs, "idFacture"));

        f.setConsultationId(getLongOrNull(rs, "consultationId"));
        f.setPatientId(getLongOrNull(rs, "patientId"));
        f.setSecretaireId(getLongOrNull(rs, "secretaireId")); // New

        f.setTotaleFacture(rs.getDouble("totaleFacture"));
        f.setTotalePaye(rs.getDouble("totalPaye")); // Was totalPaye? check field name. Entitiy has 'totalePaye'.
        // if totalPaye column exists:
        if (f.getTotalePaye() == 0.0)
            f.setTotalePaye(rs.getDouble("totalePaye"));

        f.setReste(rs.getDouble("reste"));
        f.setModePaiement(getStringOrNull(rs, "modePaiement")); // New

        String statutStr = getStringOrNull(rs, "statut");
        if (statutStr != null) {
            try {
                f.setStatut(Statut.valueOf(statutStr));
            } catch (Exception e) {
            }
        }

        Timestamp dateFactureSql = rs.getTimestamp("dateFacture");
        if (dateFactureSql != null)
            f.setDateFacture(dateFactureSql.toLocalDateTime());

        Long consId = getLongOrNull(rs, "consultationId");
        if (consId != null) {
            consultation c = new consultation();
            c.setIdEntite(consId);
            f.setConsultation(c);
        }
        Long sfId = getLongOrNull(rs, "situationFinanciereId");
        if (sfId != null) {
            situationFinanciere sf = new situationFinanciere();
            sf.setIdEntite(sfId);
            f.setSituationFinanciere(sf);
        }
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

        m.setId(getLongOrNull(rs, "id"));
        if (m.getId() == null)
            m.setId(getLongOrNull(rs, "idMedecin"));
        if (m.getId() == null)
            m.setId(getLongOrNull(rs, "idUser"));

        m.setNom(getStringOrNull(rs, "nom"));
        m.setPrenom(getStringOrNull(rs, "prenom")); // New
        m.setEmail(getStringOrNull(rs, "email"));
        m.setAdresse(getStringOrNull(rs, "adresse"));
        m.setCin(getStringOrNull(rs, "cin"));
        m.setImage(getStringOrNull(rs, "image")); // New

        String tel = getStringOrNull(rs, "tel");
        if (tel == null)
            tel = getStringOrNull(rs, "tele");
        if (tel == null)
            tel = getStringOrNull(rs, "telephone");
        m.setTelephone(tel);

        String sexeStr = getStringOrNull(rs, "sexe");
        if (sexeStr != null) {
            try {
                m.setSexe(Sexe.valueOf(sexeStr));
            } catch (Exception e) {
            }
        }

        String login = getStringOrNull(rs, "login");
        if (login == null)
            login = getStringOrNull(rs, "username");
        m.setUsername(login);

        String pwd = getStringOrNull(rs, "motDePasse");
        if (pwd == null)
            pwd = getStringOrNull(rs, "password");
        m.setPassword(pwd);

        // lastLoginDate not in schema
        // Date lastLoginSql = rs.getDate("lastLoginDate");
        // if (lastLoginSql != null)
        // m.setLastLoginDate(lastLoginSql.toLocalDate());

        Date dateNaissanceSql = rs.getDate("dateNaissance");
        if (dateNaissanceSql != null)
            m.setDateNaissance(dateNaissanceSql.toLocalDate());

        m.setSalaire(rs.getDouble("salaire"));

        // m.setPrime(rs.getDouble("prime")); // Not in schema

        Date dateRecrutementSql = rs.getDate("dateRecrutement");
        if (dateRecrutementSql != null)
            m.setDateEmbauche(dateRecrutementSql.toLocalDate());

        // m.setSoldeConge(rs.getInt("soldeConge")); // Not in schema

        // m.setIdMedecin(getLongOrNull(rs, "idMedecin")); // Removed
        m.setSpecialite(getStringOrNull(rs, "specialite"));
        // m.setNumeroOrdre(getStringOrNull(rs, "numeroOrdre")); // Not in schema
        // m.setDiplome(getStringOrNull(rs, "diplome")); // Not in schema

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

        n.setId(getLongOrNull(rs, "id"));
        if (n.getId() == null)
            n.setId(getLongOrNull(rs, "idNotif"));

        n.setTitre(getStringOrNull(rs, "titre"));
        n.setMessage(getStringOrNull(rs, "message"));

        Date dateSql = rs.getDate("date");
        if (dateSql != null)
            n.setDate(dateSql.toLocalDate()); // Added date field

        Time timeSql = rs.getTime("time");
        if (timeSql != null)
            n.setTime(timeSql.toLocalTime()); // Added time field

        // Combine for old dateEnvoi if needed, or remove dateEnvoi mapping if field
        // removed
        // n.setDateEnvoi(...)

        n.setType(getStringOrNull(rs, "type"));
        n.setPriorite(getStringOrNull(rs, "priorite")); // New field

        String statut = getStringOrNull(rs, "statut");
        n.setStatut(statut); // Was lue (boolean), now String statut
        // n.setLue("LUE".equalsIgnoreCase(statut)); // Removed lue

        Long userId = getLongOrNull(rs, "utilisateur_id");
        if (userId != null) {
            n.setUtilisateurId(userId);
            utilisateur u = new utilisateur();
            u.setIdEntite(userId);
            n.setUtilisateur(u);
        }
        if (userId != null) {
            utilisateur u = new utilisateur();
            u.setIdEntite(userId);
            n.setUtilisateur(u);
        }
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

        c.setIdCaisse(getLongOrNull(rs, "id"));
        if (c.getIdCaisse() == null)
            c.setIdCaisse(getLongOrNull(rs, "idCaisse"));
        c.setFactureId(getLongOrNull(rs, "factureId"));
        c.setMontant(rs.getDouble("montant"));

        Date dateEncaissementSql = rs.getDate("dateEncaissement");
        if (dateEncaissementSql != null)
            c.setDateEncaissement(dateEncaissementSql.toLocalDate());

        c.setModeEncaissement(getStringOrNull(rs, "modeEncaissement"));
        c.setReference(getStringOrNull(rs, "reference"));

        Long facId = getLongOrNull(rs, "factureId");
        if (facId != null) {
            facture f = new facture();
            f.setIdEntite(facId);
            c.setFacture(f);
        }
        return c;
    }

    public static agenda mapAgenda(ResultSet rs) throws SQLException {
        agenda a = new agenda();
        a.setId(getLongOrNull(rs, "id"));
        if (a.getId() == null)
            a.setId(getLongOrNull(rs, "idAgenda"));

        // a.setDateCreation(LocalDate.now()); // Schema has annee?
        // if schema has annee column:
        a.setAnnee(rs.getInt("annee"));

        a.setMedecinId(getLongOrNull(rs, "medecin_id"));

        String moisStr = getStringOrNull(rs, "mois");
        if (moisStr != null) {
            try {
                a.setMois(Mois.valueOf(moisStr));
            } catch (Exception e) {
            }
        }

        String joursStr = getStringOrNull(rs, "joursNonDisponibles");
        if (joursStr != null && !joursStr.isEmpty()) {
            java.util.List<Jour> jours = new java.util.ArrayList<>();
            for (String j : joursStr.split(",")) {
                try {
                    jours.add(Jour.valueOf(j.trim()));
                } catch (IllegalArgumentException e) {

                }
            }
            a.setJoursNonDisponibles(jours);
        }

        Long medId = getLongOrNull(rs, "medecin_id");
        if (medId != null) {
            medecin m = new medecin();
            m.setIdEntite(medId);
            a.setMedecin(m);
        }
        return a;
    }

    public static ma.TeethCare.entities.statistique.statistique mapStatistique(ResultSet rs) throws SQLException {
        statistique s = new statistique();
        s.setIdEntite(getLongOrNull(rs, "idEntite"));

        Date dateCreationSql = rs.getDate("dateCreation");
        if (dateCreationSql != null)
            s.setDateCreation(dateCreationSql.toLocalDate());
        Timestamp dateModifSql = rs.getTimestamp("dateDerniereModification");
        if (dateModifSql != null)
            s.setDateDerniereModification(dateModifSql.toLocalDateTime());

        s.setCreePar(getStringOrNull(rs, "creePar"));
        s.setModifierPar(getStringOrNull(rs, "modifierPar"));

        s.setId(getLongOrNull(rs, "id"));

        s.setNom(getStringOrNull(rs, "nom"));
        s.setChiffre(rs.getDouble("chiffre"));
        s.setType(getStringOrNull(rs, "type"));

        Date dateCalculSql = rs.getDate("dateCalcul");
        if (dateCalculSql != null)
            s.setDateCalcul(dateCalculSql.toLocalDate());

        Long cabId = getLongOrNull(rs, "cabinet_id");
        if (cabId == null)
            cabId = getLongOrNull(rs, "cabinetId");

        if (cabId != null) {
            s.setCabinetId(cabId);
            cabinetMedicale cm = new cabinetMedicale();
            cm.setIdEntite(cabId);
            s.setCabinetMedicale(cm);
        }
        return s;
    }
}
