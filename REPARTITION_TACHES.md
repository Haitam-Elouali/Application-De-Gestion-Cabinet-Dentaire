# 📋 Répartition des tâches - 5 développeurs

## ✅ ÉTAT ACTUEL DU PROJET (Mise à jour 3 Décembre 2025)

### Travail restant
- 🔴 **Création des RowMappers** (Priorité absolue)
- 🔴 **Implémentation logique métier** (ServiceImpl)
- 🔴 **Implémentation requêtes SQL** (RepositoryImpl - méthodes spécifiques)
- 🔴 **Tests unitaires et intégration**

## 👨‍💻 Développeur 1 : GESTION DES PATIENTS, DOSSIERS ET UTILISATEURS

**Modules:** Patient, DossierMedicale, Antecedent, Utilisateur, Role
**Charge:** ~20% (interfaces créées, implémentations à faire)

### Points d'intégration
- Utilise BaseEntity (Dev 5)
- Utilise BaseService (Dev 5)
- Utilise BaseRepository (Dev 5)
- Fournit Patient à Dev 2 et Dev 3
- Fournit Utilisateur et Role à tous les modules (authentification)

---

## 👨‍💻 Développeur 2 : GESTION DES AGENDAS ET CONSULTATIONS

**Modules:** RDV, Agenda, Medecin, Consultation, Actes, InterventionMedecin
**Charge:** ~18% (interfaces créées, implémentations à faire)

### Points d'intégration
- Dépend de Patient (Dev 1)
- Fournit Consultation à Dev 3 et Dev 4
- Fournit Actes à Dev 3

---

## 👨‍💻 Développeur 3 : GESTION FINANCIÈRE ET ADMINISTRATIVE

**Modules:** Facture, Caisse, Charges, Revenues, CabinetMedicale, SituationFinanciere
**Charge:** ~17% (interfaces créées, implémentations à faire)

### Points d'intégration
- Dépend de Consultation (Dev 2)
- Dépend de Patient (Dev 1)

---

## 👨‍💻 Développeur 4 : DOCUMENTS ET MÉDICAMENTS

**Modules:** Ordonnance, Prescription, Certificat, Medicament, Notification, Log
**Charge:** ~20% (interfaces créées, implémentations à faire)

### Points d'intégration
- Dépend de Consultation (Dev 2)
- Fournit Notification à tous les modules
- Fournit Log à tous les modules (audit)

---

## 👨‍💻 Développeur 5 : INFRASTRUCTURE ET AUTHENTIFICATION

**Modules:** Core, BaseEntity, BaseService, BaseRepository, Auth, Utilisateur, Role, Admin, Staff, Secretaire
**Charge:** ~10% (travail fondationnel - EN COURS)

### Tâches restantes

#### Phase 1 - Interfaces de base (CRITIQUE)
1. **Créer BaseService<T, ID> interface** 
   - Methods: create, read, update, delete, exists, count, getAll
   - À étendre par tous les autres Services
2. **Créer BaseRepository<T, ID> interface**
   - Methods: save, findById, update, delete, exists, count, findAll
   - À étendre par tous les autres Repositories

#### Phase 2 - Classes abstraites (CRITIQUE)
3. **Créer AbstractJdbcRepository<T, ID>** (implémentation JDBC commune)
4. **Créer AbstractService<T, ID>** (implémentation service commune)

#### Phase 3 - Authentification
5. **Créer AuthService interface** (login, logout, validateToken, refreshToken)
6. **Implémenter AuthServiceImpl**
7. **Créer AuthController interface**
8. **Implémenter AuthControllerImpl** (REST endpoints /auth/login, /auth/logout)

#### Phase 4 - Gestion admin (Impl)
9. **Implémenter 4 ServiceImpl** (AdminServiceImpl, StaffServiceImpl, SecretaireServiceImpl, LogServiceImpl)
10. **Implémenter 4 RepositoryImpl** (JDBC)
11. **Implémenter 4 ControllerImpl** (REST)

#### Phase 5 - Configuration et Build
12. **Mettre à jour pom.xml:**
    - Résoudre conflits Java version
    - Ajouter dépendances: JUnit 5, Lombok, SLF4J, Logback
    - Configurer Maven plugins (Compiler, Shade, Enforcer)
13. **Mettre à jour ApplicationContext.java** (Dependency Injection)
14. **Configurer beans.properties** (configuration des services)
15. **Configurer db.properties** (connexion base de données)

#### Phase 6 - Base de données
16. **Créer schema.sql COMPLET** avec tous les CREATE TABLE (27 tables)
17. **Créer seed.sql** (données initiales pour tests)
18. **Merger les SQL de Dev 1-4** en schéma unique

#### Phase 7 - Documentation et build
19. **Créer documentation techniques** (architecture, design patterns)
20. **Tester compilation:** `mvn clean install`
21. **Vérifier tous les imports** et dépendances entre modules

### Points d'intégration
- Fournit **BaseService** et **BaseRepository** à TOUS les développeurs
- Fournit **Crypto**, **Validators**, **Exceptions** à TOUS
- Assure la cohérence technique globale
- Gère l'authentification pour toute l'application
- Synchronise le build Maven

---

## 📊 Résumé charge de travail - ÉTAT RÉEL

| Développeur | Modules | Mappers | Services | Repositories | Controllers | Charge | Status |
|---|---|---|---|---|---|---|---|
| 1 | Patient/Dossier/Antec/User/Role | 5 ✅ | 5 ✅ | 5 ✅ | 5 ✅ | 20% | Impl à faire |
| 2 | RDV/Medecin/Consult/Actes/Inter/Agenda | 6 ✅ | 6 ✅ | 6 ✅ | 5 ✅ | 20% | Impl à faire |
| 3 | Finance/Cabinet/Situation/Caisse/Revenue | 5 ❌ +1 | 6 ✅ | 6 ✅ | 4 ✅ | 20% | Impl à faire |
| 4 | Ordonnance/Prescrip/Certificat/Medicam/Notif/Log | 6 ✅ | 6 ✅ | 6 ✅ | 5 ✅ | 20% | Impl à faire |
| 5 | Core/BaseEntity/Admin/Staff/Secretaire + Infrastructure | 4 ✅ | 4 ✅ | 4 ✅ | 4 ✅ | 20% | **Interfaces bases manquent** |
| **TOTAL** | **26 modules** | **26/26 ✅** | **27 ✅** | **27 ✅** | **26 ✅** | **100%** | **Équilibré!** |

### Légende
- ✅ Créé/Complet
- ❌ Manquant
- ⚠️ En progress

---

## 🔗 Dépendances entre modules et développeurs

**Graph de dépendances:**
```
Dev 5 (Core)
    ├── BaseService interface (MANQUE ⚠️)
    ├── BaseRepository interface (MANQUE ⚠️)
    ├── Exceptions & Crypto & Validators
    └── Utilisateur, Role, Admin, Staff, Secretaire
         ↓ (dépend)
    
    Dev 1 (Patient)
        ├── PatientService → extends BaseService
        ├── PatientRepository → extends BaseRepository
        └── Fournit Patient à Dev 2 et Dev 3
             ↓
        
        Dev 2 (RDV/Consultation)
            ├── RdvService, ConsultationService, ActesService
            ├── Dépend de Patient (Dev 1)
            └── Fournit Consultation à Dev 3 et Dev 4
                 ↓
                
                Dev 3 (Finances)
                    ├── FactureService, CaisseService
                    ├── Dépend de Consultation (Dev 2)
                    └── Fournit Facture à Dev 4
                
                Dev 4 (Documents)
                    ├── OrdonnanceService, NotificationService, LogService
                    ├── Dépend de Consultation (Dev 2)
                    └── Notification et Log = utilisés partout
```

---

## 🚀 Priorités actuelles (Mise à jour 4 Décembre 2025)

### 🔴 CRITIQUE (Infrastructure & Tests)
1. **Repository Completeness:** Vérifier que tous les repositories sont complets et fonctionnels.
2. **Test Repo Class:** Développer une classe de test pour valider les processus CRUD (Create, Read, Update, Delete).
3. **RowMappers:** Ajouter les classes `RowMapper` dans le dossier `TeethCare/common` pour standardiser le mapping JDBC.

### 🟠 URGENT (Implémentation & Tests)
4. **Services & Tests (2 modules/dev):** Chaque développeur doit finaliser les services pour 2 modules et ajouter les packages/classes de test correspondants.
   - Créer les packages de test dans `src/test/java/ma/TeethCare/service/modules/...`
   - Implémenter les tests unitaires pour chaque service.

### 🟡 IMPORTANT
5. **Création des Mappers:** Continuer la création des Mappers DTO <-> Entity.
6. **Nettoyage:** Supprimer les fichiers obsolètes si ce n'est pas déjà fait.

---

## 💾 Fichiers manquants à créer (Priorité)

### 🔴 Interfaces de base (BLOCKING - Dev 5)
```
ma/TeethCare/service/common/BaseService.java
ma/TeethCare/repository/common/BaseRepository.java
```

### 🔴 Mappers manquants (Dev 1 et Dev 3)
```
ma/TeethCare/mvc/mappers/antecedent/AntecedentMapper.java (Dev 1)
ma/TeethCare/mvc/mappers/situationFinanciere/SituationFinanciereMapper.java (Dev 3)
```

### 🟠 Classes abstraites (après interfaces - Dev 5)
```
ma/TeethCare/service/common/AbstractService.java
ma/TeethCare/repository/common/AbstractJdbcRepository.java
```

### 🟠 Implémentations par développeur (27 Services + 27 Repositories + 26 Controllers)

**Dev 1 (5 modules = 5 Services + 5 Repos + 5 Controllers):**
```
ma/TeethCare/service/modules/[patient|dossierMedicale|antecedent|utilisateur|role]/impl/[...]ServiceImpl.java
ma/TeethCare/repository/modules/[patient|dossierMedicale|antecedent|utilisateur|role]/impl/[...]RepositoryImpl.java
ma/TeethCare/mvc/controllers/[patient|dossierMedicale|antecedent|utilisateur]/[...]ControllerImpl.java
```

**Dev 2 (6 modules = 6 Services + 6 Repos + 5 Controllers):**
```
ma/TeethCare/service/modules/[rdv|medecin|consultation|actes|agenda|interventionMedecin]/impl/[...]ServiceImpl.java
ma/TeethCare/repository/modules/[rdv|medecin|consultation|actes|agenda|interventionMedecin]/impl/[...]RepositoryImpl.java
ma/TeethCare/mvc/controllers/[rdv|medecin|consultation|actes|agenda|interventionMedecin]/[...]ControllerImpl.java
```

**Dev 3 (6 modules = 6 Services + 6 Repos + 4 Controllers):**
```
ma/TeethCare/service/modules/[facture|caisse|charges|revenues|cabinetMedicale|situationFinanciere]/impl/[...]ServiceImpl.java
ma/TeethCare/repository/modules/[facture|caisse|charges|revenues|cabinetMedicale|situationFinanciere]/impl/[...]RepositoryImpl.java
ma/TeethCare/mvc/controllers/[facture|caisse|charges|revenues|cabinetMedicale]/[...]ControllerImpl.java
```

**Dev 4 (6 modules = 6 Services + 6 Repos + 5 Controllers):**
```
ma/TeethCare/service/modules/[ordonnance|prescription|certificat|medicament|notification|log]/impl/[...]ServiceImpl.java
ma/TeethCare/repository/modules/[ordonnance|prescription|certificat|medicament|notification|log]/impl/[...]RepositoryImpl.java
ma/TeethCare/mvc/controllers/[ordonnance|prescription|certificat|medicament|notification|log]/[...]ControllerImpl.java
```

**Dev 5 (5 modules core = 4 Services + 4 Repos + 4 Controllers + Infrastructure):**
```
ma/TeethCare/service/modules/[admin|staff|secretaire|log]/impl/[...]ServiceImpl.java
ma/TeethCare/repository/modules/[admin|staff|secretaire|log]/impl/[...]RepositoryImpl.java
ma/TeethCare/mvc/controllers/[admin|staff|secretaire|log]/[...]ControllerImpl.java

PLUS Infrastructure:
ma/TeethCare/service/modules/auth/impl/AuthServiceImpl.java
ma/TeethCare/service/common/AbstractService.java
ma/TeethCare/repository/common/AbstractJdbcRepository.java
src/main/resources/dataBase/schema.sql
src/main/resources/dataBase/seed.sql
pom.xml (résolu)
