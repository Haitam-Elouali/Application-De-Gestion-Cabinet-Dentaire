# 🦷 TeethCare

---
### Application de Gestion de Cabinet Dentaire

---

> Projet – EMSI Rabat  
> Réalisé par : **CHOUKHAIRI Noureddine, ELOUALI Haitam, MHAMDI ALAOUI Hamza, MOKADAMI Zouhair, BAKAROUM Salma**  
> Encadré par : **M. EL MIDAOUI Omar**

---

## 📖 Sommaire
1. [Contexte du projet](#-contexte-du-projet)
2. [Problématique](#-problématique)
3. [Solution proposée](#-solution-proposée)
4. [Objectifs du projet](#-objectifs-du-projet)
5. [Technologies utilisées](#-technologies-utilisées)
6. [Architecture du projet](#-architecture-du-projet)
7. [Organisation du travail](#-organisation-du-travail)
8. [Fonctionnalités principales](#-fonctionnalités-principales)
9. [Base de données](#-base-de-données)
10. [Structure du code source](#-structure-du-code-source)
11. [Charte graphique & UI/UX](#-charte-graphique--uiux)
12. [Procédure d'installation et d'exécution](#-procédure-dinstallation-et-dexécution)
13. [Scénario de démonstration](#-scénario-de-démo)
14. [Conclusion et perspectives](#-conclusion-et-perspectives)

---

## 🩺 Contexte du projet
Dans une optique de modernisation et d'optimisation des pratiques médicales, le cabinet dentaire **TeethCare** souhaite renforcer sa performance administrative et médicale à travers la mise en place d'une application de gestion intégrée. Le système actuel, reposant sur des fichiers papier et des tableurs, montre aujourd'hui ses limites en matière d'efficacité et de fiabilité. Ainsi, la transition vers une solution numérique s'impose comme une étape essentielle pour améliorer la productivité et la qualité de gestion.

---

## ⚠️ Problématique
Dans de nombreux cabinets dentaires, la gestion des informations reste principalement manuelle ou s'appuie sur des outils informatiques peu adaptés aux besoins du domaine médical. Cette situation engendre fréquemment des erreurs dans la planification des rendez-vous, le suivi des traitements ou la facturation, ce qui peut nuire à la qualité du service offert aux patients.

La centralisation et la numérisation des données médicales et administratives apparaissent dès lors comme une solution essentielle pour renforcer la fiabilité, la précision et l'efficacité de la gestion interne.

---

## 💡 Solution proposée
La solution envisagée consiste à concevoir une **application de gestion métier** destinée exclusivement au personnel du cabinet dentaire TeethCare. Ce système leur permettra :

- D'enregistrer et de gérer les dossiers des patients
- De planifier et d'organiser les rendez-vous
- D'assurer le suivi des consultations et des traitements dentaires
- De gérer la facturation ainsi que le suivi des paiements

---

## 🎯 Objectifs du projet
Les principaux objectifs à atteindre lors du développement de cette application se structurent autour de la gestion des aspects administratifs/financiers, médicaux/patient et du personnel :

- **Centraliser** les données médicales et administratives au sein d'un seul système
- **Faciliter** la gestion des dossiers patients (coordonnées, antécédents, traitements, ordonnances, etc.)
- **Automatiser** la planification des rendez-vous pour éviter les erreurs et les chevauchements d'horaires
- **Assurer** le suivi des consultations et traitements dentaires de manière structurée et sécurisée
- **Simplifier** la gestion de la facturation et des paiements (création de factures, suivi des règlements)
- **Améliorer** la communication interne entre le personnel médical et administratif
- **Renforcer** la traçabilité et la sécurité des données (accès restreint selon les rôles)
- **Réduire** les tâches manuelles et le risque d'erreurs liés à la gestion sur papier ou via des tableurs
- **Optimiser** le temps de travail du personnel grâce à une interface intuitive et adaptée à leurs besoins

---

## 🧰 Technologies utilisées
| Catégorie | Technologies | Version |
|------------|---------------|---------|
| **Langage principal** | Java | 23 |
| **Build & Dépendances** | Maven | 3.6+ |
| **Annotations** | Lombok | 1.18.38 |
| **Base de données** | MySQL | 8.0.33 |
| **ORM / DAO** | JDBC | Natif |
| **API REST** | JAX-RS / Jersey | TBD |
| **Framework graphique** | Java Swing | Natif (futur) |
| **Serialization** | JSON (Jackson) | TBD |
| **Validation** | Jakarta Bean Validation | TBD |
| **IDE** | IntelliJ IDEA | Latest |
| **Conception UML** | StarUML / PlantUML | Latest |
| **Méthodologie** | Conception UML 100% conforme | ✅ Appliquée |

---

## 🏗️ Architecture du projet
L'application respecte une architecture **multi-couche MVC/DAO** basée sur le diagramme de classes UML avec 100% de conformité :

```
TeethCare/
├─ src/main/
│  ├─ java/ma/TeethCare/
│  │  ├─ MainApp.java                 → Point d'entrée de l'application
│  │  ├─ config/                      → Configuration et injection de dépendances
│  │  │  └─ ApplicationContext.java    → Contexte d'application
│  │  ├─ common/                      → Utilitaires partagés
│  │  │  ├─ exceptions/               → Exceptions personnalisées
│  │  │  │  ├─ AuthException.java
│  │  │  │  ├─ DaoException.java
│  │  │  │  ├─ ServiceException.java
│  │  │  │  └─ ValidationException.java
│  │  │  ├─ utilitaire/               → Classes utilitaires
│  │  │  │  └─ Crypto.java            → Chiffrement de mots de passe
│  │  │  └─ validation/               → Validateurs
│  │  │     └─ Validators.java        → Validations métier
│  │  ├─ entities/                    → 27 Entités métiers (100% UML)
│  │  │  ├─ baseEntity/               → Classe abstraite de base
│  │  │  ├─ patient/
│  │  │  ├─ utilisateur/
│  │  │  ├─ medecin/
│  │  │  ├─ staff/
│  │  │  ├─ secretaire/
│  │  │  ├─ admin/
│  │  │  ├─ dossierMedicale/
│  │  │  ├─ antecedent/
│  │  │  ├─ rdv/
│  │  │  ├─ consultation/
│  │  │  ├─ actes/
│  │  │  ├─ interventionMedecin/
│  │  │  ├─ ordonnance/
│  │  │  ├─ prescription/
│  │  │  ├─ certificat/
│  │  │  ├─ medicaments/
│  │  │  ├─ facture/
│  │  │  ├─ caisse/
│  │  │  ├─ charges/
│  │  │  ├─ revenues/
│  │  │  ├─ situationFinanciere/
│  │  │  ├─ cabinetMedicale/
│  │  │  ├─ agenda/
│  │  │  ├─ role/
│  │  │  ├─ notification/
│  │  │  ├─ log/
│  │  │  └─ enums/                    → Énumérations (Sexe, Mois, Jour, etc.)
│  │  ├─ repository/                  → Couche d'accès aux données (DAO/JDBC)
│  │  │  ├─ api/                      → 26 Repository interfaces (1 par entité)
│  │  │  ├─ mySQLImpl/                → 26 Repository implementations (JDBC)
│  │  │  └─ common/                   → Interfaces de base
│  │  │     └─ GenericJdbcRepository.java → Implémentation générique CRUD
│  │  ├─ service/                     → Couche de logique métier
│  │  │  ├─ common/                   → Services de base
│  │  │  │  └─ BaseService.java       → Interface générique service
│  │  │  └─ modules/                  → 26 Service interfaces (1 par entité)
│  │  └─ mvc/                         → Couche présentation MVC
│  │     ├─ dto/                      → 26 DTOs (Data Transfer Objects)
│  │     │  └─ [module]/
│  │     │     └─ [Entity]DTO.java
│  │     ├─ mappers/                  → 26 Mappers (Entity ↔ DTO)
│  │     │  └─ [module]/
│  │     │     └─ [Entity]Mapper.java
│  │     ├─ controllers/              → 26 REST Controllers (interfaces)
│  │     │  └─ [module]/
│  │     │     └─ [Entity]Controller.java
│  │     └─ ui/                       → Interface utilisateur Swing (futur)
│  │        └─ common/                → Composants réutilisables
│  └─ resources/                      → Fichiers de configuration et ressources
│     ├─ config/
│     │  ├─ beans.properties          → Configuration des beans
│     │  └─ db.properties             → Configuration base de données
│     └─ dataBase/
│        ├─ schema.sql                → Création des tables
│        └─ seed.sql                  → Données de test
└─ pom.xml                            → Configuration Maven
```

### 📊 État d'achèvement des couches

| Couche | Type | Nombre | État |
|--------|------|--------|------|
| **Entities** | Classes | 27 | ✅ 100% Complète |
| **DTOs** | Classes | 26 | ✅ 100% Créées |
| **Mappers** | Classes | 26 | ✅ 100% Créées |
| **Repositories** | Interfaces | 26 | ✅ 100% Interfaces |
| **Services** | Interfaces | 26 | ✅ 100% Interfaces |
| **Controllers** | Interfaces | 26 | ✅ 100% Interfaces |

### Hiérarchie des classes principales :
- **BaseEntity** : Classe abstraite de base avec traçabilité (dateCreated, dateModified, createdBy, updatedBy)
- **Utilisateur** → **Staff** → **Médecin** / **Secrétaire** → **Admin**
- **Patient** (1-1) **DossierMédicale** (1-*) **Consultation**, **RDV**, **Ordonnance**, **Certificat**, **Antecedent**
- **Consultation** (1-*) **InterventionMédecin**, **Facture**
- **SituationFinancière** (1-*) **Facture**
- **Médecin** (1-1) **Agenda** (planification mensuelle)

---

## 👥 Organisation du travail

### Équipe
Le projet est réalisé par **5 développeurs** travaillant en parallèle avec une répartition égale des modules.

### Distribution des modules par développeur (20% par dev)
| Développeur | Modules | Nombre | Entités |
|-------------|---------|--------|---------|
| **Dev 1** | Patient & Utilisateurs | 5 | Patient, DossierMédicale, Antecedent, Utilisateur, Role |
| **Dev 2** | RDV & Consultations | 6 | RDV, Médecin, Consultation, Actes, Intervention, Agenda |
| **Dev 3** | Finance & Cabinet | 6 | Facture, Caisse, Charges, Revenues, Cabinet, SituationFinancière |
| **Dev 4** | Ordonnances & Documents | 6 | Ordonnance, Prescription, Certificat, Médicament, Notification, Log |
| **Dev 5** | Infrastructure & Admin | 5 | BaseEntity, Admin, Staff, Secrétaire, Core infrastructure |

### Rôles et responsabilités
Les développeurs se concentrent sur :
- **Dev 1-4** : Implémentation des ServiceImpl, RepositoryImpl JDBC, ControllerImpl pour leurs modules
- **Dev 5** : Interfaces de base (BaseService, BaseRepository), AbstractService/AbstractJdbcRepository, configuration

### Planification
Le projet suit 5 phases bien structurées :
1. ✅ **Phase 1** : Conception (diagrammes UML complétés)
2. ✅ **Phase 2** : Création de l'architecture (27 entités + DTOs + Mappers)
3. ✅ **Phase 3** : Génération des interfaces (Repositories, Services, Controllers)
4. 🔄 **Phase 4** : Implémentation (ServiceImpl, RepositoryImpl JDBC, ControllerImpl)
5. 📅 **Phase 5** : Tests, validation et documentation

---

## 🎨 Fonctionnalités principales

### 👨‍⚕️ Espace Médecin
Le médecin hérite de toutes les fonctionnalités de la secrétaire, plus :

#### Gestion du Dossier Médical
- Créer, consulter et mettre à jour le dossier médical
- Gérer les antécédents médicaux des patients

#### Gestion des Consultations
- Créer et enregistrer les consultations
- Ajouter des observations médicales
- Consulter l'historique complet des consultations

#### Gestion des Actes Médicaux
- Créer, modifier et supprimer des actes dans le catalogue
- Enregistrer les interventions réalisées (InterventionMédecin)

#### Gestion des Ordonnances
- Créer et modifier des ordonnances
- Ajouter des prescriptions avec médicaments
- Imprimer les ordonnances

#### Gestion des Certificats
- Créer des certificats médicaux (arrêt de travail, aptitude, etc.)
- Définir les dates de validité
- Imprimer les certificats

#### Dashboard Médecin
- Consulter l'agenda de la semaine
- Visualiser les statistiques personnelles
- Accéder à la file d'attente

### 👩‍💼 Espace Secrétaire

#### Gestion des Patients
- Ajouter, modifier, supprimer des patients
- Rechercher des patients
- Assigner/désassigner des antécédents médicaux
- Gérer le catalogue des antécédents

#### Gestion des Rendez-vous
- Créer, modifier, annuler des rendez-vous
- Consulter l'agenda (jour, semaine, mois)
- Gérer les notes de rendez-vous

#### Gestion de l'Agenda Mensuel
- Créer l'emploi du temps mensuel du médecin
- Définir les jours non disponibles
- Modifier et consulter les agendas

#### Gestion de la Caisse
- Consulter et imprimer les factures
- Gérer les situations financières
- Suivre les paiements (totale facture, totale payé, reste)
- Appliquer promotions et rabais
- Consulter la recette du jour

#### Dashboard Secrétaire
- Visualiser l'agenda de la semaine courante
- Consulter les statistiques patients et RDV
- Accéder à la file d'attente
- Voir les RDV de la semaine

### 🔐 Espace Administrateur

#### Gestion des Utilisateurs
- Ajouter, modifier, supprimer des utilisateurs
- Consulter la liste et l'historique des connexions
- Activer/désactiver des comptes
- Réinitialiser les mots de passe
- Gérer les permissions

#### Gestion des Rôles
- Créer et modifier des rôles
- Attribuer des rôles aux utilisateurs
- Définir les permissions par rôle

#### Gestion des Données Référentielles
- **Catalogue des Actes** : CRUD complet
- **Catalogue des Médicaments** : CRUD complet
- **Catalogue des Antécédents** : CRUD complet

#### Gestion des Sauvegardes
- Créer des sauvegardes manuelles
- Planifier des sauvegardes automatiques
- Restaurer des sauvegardes

#### Gestion des Logs et Audit
- Consulter les logs système
- Consulter les logs d'audit (traçabilité complète)
- Filtrer par utilisateur, action ou date

#### Gestion des Sessions
- Consulter les sessions actives
- Gérer les connexions simultanées

### 📊 Fonctions Transverses

#### Gestion des Statistiques
- Générer des statistiques par catégorie
- Filtrer par date
- Calculer les indicateurs clés
- Exporter les données

#### Gestion des Charges et Revenus
- Enregistrer les charges du cabinet
- Suivre les revenus
- Calculer les totaux par période
- Rechercher par critères

#### Gestion des Notifications
- Créer et envoyer des notifications
- Définir type et priorité
- Marquer comme lues
- Rechercher par type ou date

#### Gestion du Cabinet Médical
- Modifier les informations du cabinet
- Gérer les contacts (téléphones, email, réseaux sociaux)
- Mettre à jour le logo et la description

---

## 🗄️ Base de données

La base de données MySQL implémente le diagramme de classes complet avec :

### Tables principales
- **Entité** : Table de base avec traçabilité
- **Utilisateur**, **Staff**, **Médecin**, **Secrétaire** : Hiérarchie d'héritage
- **Patient**, **DossierMédicale**, **Antécédents** : Gestion patients
- **RDV**, **Consultation**, **InterventionMédecin** : Gestion médicale
- **Ordonnance**, **Prescription**, **Médicament** : Gestion pharmacie
- **Certificat** : Documents médicaux
- **Facture**, **SituationFinancière** : Gestion financière
- **Acte** : Catalogue des prestations
- **AgendaMensuel** : Planning médecin
- **Statistiques** : Données analytiques
- **Log** : Traçabilité
- **Notification** : Communication
- **Charges**, **Revenues** : Comptabilité
- **CabinetMédicale** : Informations du cabinet
- **Role** : Gestion des permissions

### Relations clés
- Patient (1-1) DossierMédicale
- DossierMédicale (1-*) Consultation, RDV, Ordonnance, Certificat
- Consultation (1-*) InterventionMédecin, Facture
- SituationFinancière (1-*) Facture
- Médecin (1-1) AgendaMensuel
- Utilisateur (many-to-many) Role, Notification

**Scripts SQL** :
- `schema.sql` : Création complète du schéma
- `seed.sql` : Données de test

---

## 📁 Structure du code source

### Package entities
POJOs représentant le modèle de domaine (avec Lombok) :
- Classe de base `Entité` avec traçabilité
- Hiérarchie `Utilisateur` → `Staff` → `Médecin`/`Secrétaire`
- Classes métiers : `Patient`, `DossierMédicale`, `Consultation`, etc.

### Package repository
Interfaces DAO + implémentations JDBC pour chaque entité

### Package service
Couche de logique métier :
- `PatientService`, `ConsultationService`
- `FactureService`, `OrdonnanceService`
- `StatistiquesService`, `LogService`
- etc.

### Package mvc
- **controllers/** : Contrôleurs pour chaque module
- **dto/** : Objets de transfert de données
- **ui/** : Interfaces Swing organisées par module

### Package common
Utilitaires, exceptions personnalisées, validateurs

### Package config
Configuration et injection de dépendances

---

## 📈 État de développement actuel

### ✅ Phases complétées
- ✅ **UML Compliance** : 100% conformité au diagramme de classes (27 entités)
- ✅ **Architecture** : Skeleton complet (interfaces pour tous les modules)
- ✅ **DTOs** : 26 classes de transfert de données créées
- ✅ **Mappers** : 26 bidirectional mappers implémentés
- ✅ **Repositories** : 26 interfaces DAO avec méthodes spécialisées
- ✅ **Services** : 26 interfaces service avec logique métier
- ✅ **Controllers** : 26 interfaces REST avec endpoints

### 🔄 En cours d'implémentation
- 🔄 **ServiceImpl** : 26 implémentations de services (par développeur selon répartition)
- 🔄 **RepositoryImpl** : 26 implémentations JDBC (requêtes SQL par module)
- 🔄 **ControllerImpl** : 26 contrôleurs REST (endpoints REST par module)

### ⏳ À faire (Roadmap Immédiate)
- 🔴 **Test Repo Class** : Développement classe test pour CRUD
- 🔴 **RowMappers** : Ajout des RowMappers dans `common`
- 🔴 **Services & Tests** : 2 modules complets par développeur (avec tests unitaires)
- ⏳ **Repository Completeness** : Vérification globale

## 🎨 Charte graphique & UI/UX

### Logo et Slogan
**Logo** : TeethCare avec icône dentaire  
**Slogan** : *« SOURIS MIEUX, VIS MIEUX »*

### Thème : "Soft Spectrum"

#### Palette de couleurs
- **White** : #FFFFFF
- **Light Green** : #DBFCE7
- **Grey** : #E5E7EB
- **Green** : #00A63E (couleur principale)
- **Red** : #E7000B (alertes)
- **Blue** : #155DFC (actions)
- **Yellow** : #FFF085 (avertissements)
- **Black** : #2D0A0A (texte)
- **Violet** : #9810FA (accents)
- **Light Blue** : #DBEAFE (backgrounds)
- **Light Red** : #EA7474 (erreurs)

#### Typographie : Famille Poppins
- **Logo** : Poppins Bold (titre) / Poppins Light (slogan)
- **Navigation** : Poppins Regular
- **En-têtes** : Poppins Semi-Bold
- **Corps de texte** : Poppins Regular
- **Boutons** : Poppins Medium

### Principes UI/UX
- Fenêtres centrées et cohérentes
- Navigation claire par modules
- Tableaux bien structurés avec actions contextuelles
- Formulaires avec validation en temps réel
- Feedback visuel pour toutes les actions
- Icônes cohérentes et intuitives
- Respect de la hiérarchie visuelle

### Maquettes principales
Le document de spécification inclut les maquettes pour :
- Page de connexion
- Gestion des utilisateurs (Admin)
- Gestion des patients (Secrétaire/Médecin)
- Gestion des rendez-vous
- Gestion des ordonnances
- Dashboard

---

## ⚙️ Procédure d'installation et d'exécution

### Prérequis
- **Java JDK** : 23 ou supérieur
- **MySQL** : 8.0 ou supérieur
- **Maven** : 3.6 ou supérieur
- **Git** : pour cloner le repository
- **IntelliJ IDEA** : recommandé pour le développement

### Vérification des prérequis
```bash
# Vérifier Java
java -version
javac -version

# Vérifier Maven
mvn -version

# Vérifier MySQL
mysql --version
```

### Installation

1. **Cloner le projet**
   ```bash
   git clone https://github.com/Haitam-Elouali/Application-De-Gestion-Cabinet-Dentaire.git
   cd TeethCare
   ```

2. **Configurer la base de données**
   ```bash
   # Démarrer MySQL
   mysql -u root -p
   
   # Créer la base de données
   CREATE DATABASE teethcare;
   USE teethcare;
   
   # Importer le schéma (une fois créé)
   source src/main/resources/dataBase/schema.sql
   
   # Importer les données de test (optionnel)
   source src/main/resources/dataBase/seed.sql
   ```

3. **Configurer la connexion à la base de données**
   Éditer `src/main/resources/config/db.properties` :
   ```properties
   db.url=jdbc:mysql://localhost:3306/teethcare
   db.username=root
   db.password=votre_mot_de_passe
   db.driver=com.mysql.cj.jdbc.Driver
   ```

4. **Installer les dépendances et compiler**
   ```bash
   mvn clean install
   ```

5. **Exécuter l'application**
   ```bash
   # Depuis le terminal
   mvn exec:java -Dexec.mainClass="ma.TeethCare.Main"
   
   # Ou depuis IntelliJ IDEA
   # Clic droit sur MainApp.java → Run 'MainApp'
   ```

### Configurer IntelliJ IDEA (Recommandé)

1. **Ouvrir le projet**
   - File → Open → Sélectionner le dossier TeethCare
   - Laisser IntelliJ configurer Maven automatiquement

2. **Configurer le JDK**
   - File → Project Structure → Project
   - SDK → New → JDK → Installer Java 23

3. **Marquer les répertoires source**
   - Clic droit sur `src/main/java` → Mark Directory as → Sources Root
   - Clic droit sur `src/test/java` → Mark Directory as → Test Sources Root
   - Clic droit sur `src/main/resources` → Mark Directory as → Resources Root

4. **Exécuter les tests**
   ```bash
   mvn test
   ```

### Dépannage courant

**Problème** : `Cannot find module 'com.mysql.cj.jdbc'`
```bash
# Solution : Vérifier pom.xml et réinstaller
mvn clean install -U
```

**Problème** : Erreur de connexion à MySQL
```bash
# Vérifier que MySQL est démarré
# Windows: Services → MySQL80 → Démarrer
# Mac/Linux: brew services start mysql-server
```

**Problème** : Port 3306 déjà utilisé
```bash
# Modifier le port dans db.properties et MySQL config
db.url=jdbc:mysql://localhost:3307/teethcare
```

---

## 🔑 Identifiants par défaut (une fois seed.sql exécuté)

```
Administrateur:
├─ Email: admin@teethcare.ma
├─ Mot de passe: admin123
└─ Permissions: Toutes

Médecin:
├─ Email: medecin@teethcare.ma
├─ Mot de passe: medecin123
└─ Permissions: Consultations, Ordonnances, Certificats

Secrétaire:
├─ Email: secretaire@teethcare.ma
├─ Mot de passe: secretaire123
└─ Permissions: Patients, RDV, Agenda

Staff (Réceptionniste):
├─ Email: staff@teethcare.ma
├─ Mot de passe: staff123
└─ Permissions: Patients (lecture)
```

---

## 🧪 Scénario de démonstration

### Scénario 1 : Workflow Secrétaire
1. **Connexion** avec le compte secrétaire
2. **Ajout d'un nouveau patient** avec ses informations et antécédents
3. **Consultation de l'agenda** du médecin
4. **Création d'un rendez-vous** pour le patient
5. **Visualisation du dashboard** avec statistiques du jour

### Scénario 2 : Workflow Médecin
1. **Connexion** avec le compte médecin
2. **Consultation du dossier médical** du patient
3. **Création d'une consultation** avec observations
4. **Ajout d'interventions médicales** (actes réalisés)
5. **Création d'une ordonnance** avec prescriptions
6. **Émission d'un certificat médical**
7. **Génération de la facture** automatique

### Scénario 3 : Workflow Administrateur
1. **Connexion** avec le compte admin
2. **Ajout d'un nouvel utilisateur** (médecin ou secrétaire)
3. **Attribution des rôles** et permissions
4. **Gestion du catalogue des actes**
5. **Consultation des logs** du système
6. **Création d'une sauvegarde** de la base

### Scénario 4 : Gestion Financière
1. **Consultation des factures** d'un patient
2. **Enregistrement d'un paiement**
3. **Visualisation de la situation financière** globale
4. **Application d'une promotion**
5. **Génération du rapport** de recette du jour
6. **Consultation des statistiques** financières

---

## 📊 Conclusion et perspectives

### Réalisations ✅
✅ **Architecture UML 100% conforme** avec 27 entités correctement modélisées  
✅ **Framework complet** : DTOs, Mappers, Repository, Service, Controller interfaces  
✅ **Répartition équitable** : 5 développeurs avec 20% de charge chacun  
✅ **Structure de code** : Skeleton prêt pour implémentation immédiate  
✅ **Documentation** : Code auto-documenté avec javadoc et commentaires  
✅ **Gestion du cycle de vie patient** : Complet du patient aux consultations/factures  
✅ **Système de rôles** : Admin, Médecin, Secrétaire, Staff avec permissions  

### Réalisations en cours 🔄
🔄 Implémentation des 78 classes (26 ServiceImpl + 26 RepositoryImpl + 26 ControllerImpl)  
🔄 Création du schéma SQL complet (27 tables)  
🔄 Tests unitaires et intégration  
🔄 Documentation Swagger pour API REST  

### Perspectives d'amélioration 🔮
🔮 **Version Web** : Migration Java Swing → Spring Boot + Angular/React  
🔮 **Application Mobile** : Clinicien sur Android/iOS  
🔮 **Télémédecine** : Consultations vidéo intégrées  
🔮 **IA & Prédictions** : Aide au diagnostic et optimisation  
🔮 **Cloud Deployment** : AWS/Azure pour accès distant sécurisé  
🔮 **Interopérabilité** : Connexion avec laboratoires, assurances  
🔮 **Analytics avancés** : Tableaux de bord intelligents et KPIs  
🔮 **Gestion du stock** : Médicaments, équipements, consommables  
🔮 **Intégration paiement** : Passerelle bancaire pour facturation en ligne  

## 📚 Documentation supplémentaire

Pour plus de détails, consulter :
- **`REPARTITION_TACHES.md`** : Détail complet de la répartition et checklist par dev
- **Diagramme UML** : `docs/UML_Diagram.png` ou fichier StarUML
- **Charte graphique** : Palette de couleurs et composants UI
- **Javadoc** : Documentation du code générée avec `mvn javadoc:javadoc`

### Fichiers clés du projet
```
TeethCare/
├─ pom.xml                          → Configuration Maven complète
├─ readme.md                        → Ce fichier
├─ src/main/resources/
│  ├─ config/db.properties          → Connexion base de données
│  ├─ config/beans.properties       → Configuration injection dépendances
│  └─ dataBase/
│     ├─ schema.sql                 → Structure complète (27 tables)
│     └─ seed.sql                   → Données de test
└─ docs/                            → Documentation du projet
   ├─ UML_Diagram.png
   ├─ Architecture.md
   └─ API_Documentation.md
```

---

## 🔧 Architecture des implémentations (ServiceImpl, RepositoryImpl, ControllerImpl)

### Pattern Singleton + Dependency Injection
Chaque couche implémente le pattern Singleton avec injection de dépendances :

```
+-----------+         +----------+         +--------+
| Controller|------→ | Service  |------→ | Repository |
+-----------+         +----------+         +--------+
     REST              Business Logic         JDBC/SQL
```

### Template de ServiceImpl
```java
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    
    @Override
    public PatientDTO createPatient(PatientDTO dto) {
        // Validation
        // Conversion DTO → Entity
        // Appel repository.save()
        // Retour DTO
    }
}

```

---

## 👨‍💻 Équipe projet

| Étudiant | Email |
|----------|-------|
| CHOUKHAIRI Noureddine | noureddine.choukhairi@emsi-edu.ma |
| ELOUALI Haitam | haitam.elouali@emsi-edu.ma |
| MHAMDI ALAOUI Hamza | hamza.mhamdialaoui@emsi-edu.ma |
| MOKADAMI Zouhair | zouhair.mokadami@emsi-edu.ma |
| BAKAROUM Salma | salma.bakaroum@emsi-edu.ma |

**Encadrant** : M. EL MIDAOUI Omar

---

## 📄 Documentation

Pour plus de détails, consulter :
- **Dossier de spécification** : Documentation complète du projet
- **Diagrammes UML** : Classes, cas d'utilisation, séquence
- **Maquettes UI** : Charte graphique et design des écrans
- **Javadoc** : Documentation du code générée automatiquement

---

© 2025 – **TeethCare | EMSI Rabat**  
*« SOURIS MIEUX, VIS MIEUX »*
