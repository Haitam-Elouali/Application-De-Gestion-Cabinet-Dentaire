# 🦷 TeethCare

---
### Application de Gestion de Cabinet Dentaire

---

> Projet de fin d'année – EMSI Rabat  
> Réalisé par : **CHOUKHAIRI Noureddine, ELOUALI Haitam, MHAMDI ALAOUI Hamza, MOKADAMI Zouhair**  
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
| Catégorie | Technologies |
|------------|---------------|
| Langage principal | Java |
| Framework graphique | Java Swing |
| Base de données | MySQL |
| ORM / DAO | JDBC |
| IDE | IntelliJ IDEA |
| Conception UML | StarUML / PlantUML |
| Méthodologie | Conception UML complète |

---

## 🏗️ Architecture du projet
L'application respecte une architecture **multi-couche MVC** basée sur le diagramme de classes conçu :

```
TeethCare/
├─ config/          → Configuration et injection de dépendances
├─ entities/        → Entités métiers (héritant de la classe Entité)
│  ├─ Utilisateur   → Staff, Médecin, Secrétaire
│  ├─ Patient       → DossierMédicale, Antécédents
│  ├─ RDV           → Rendez-vous
│  ├─ Consultation  → InterventionMédecin, Ordonnance, Certificat
│  ├─ Facture       → SituationFinancière
│  ├─ Acte          → Catalogue des actes médicaux
│  ├─ Médicament    → Catalogue des médicaments
│  ├─ AgendaMensuel → Gestion de disponibilité
│  ├─ Statistiques  → Données analytiques
│  ├─ Log           → Traçabilité des actions
│  └─ Notification  → Communication interne
├─ repository/      → Accès aux données (DAO / JDBC)
├─ service/         → Logique métier
├─ mvc/
│  ├─ controllers/  → Contrôleurs des modules UI
│  ├─ dto/          → Objets de transfert de données
│  └─ ui/           → Interface utilisateur (Swing)
│     ├─ common/        → Composants réutilisables (palette)
│     ├─ patient/       → Module gestion des patients
│     ├─ rdv/           → Module rendez-vous
│     ├─ consultation/  → Module consultations
│     ├─ ordonnance/    → Module ordonnances
│     ├─ certificat/    → Module certificats
│     ├─ caisse/        → Module facturation
│     ├─ dashboard/     → Tableau de bord
│     ├─ admin/         → Module administration
│     └─ agenda/        → Module agenda mensuel
└─ common/          → Exceptions, utilitaires, validateurs
```

### Hiérarchie des classes principales :
- **Entité** : Classe de base avec traçabilité (dates création/modification, auteurs)
- **Utilisateur** → **Staff** → **Médecin** / **Secrétaire**
- **Patient** (1-1) **DossierMédicale** (1-*) **Consultation**, **RDV**, **Ordonnance**, **Certificat**
- **Consultation** (1-*) **InterventionMédecin**, **Facture**
- **SituationFinancière** (1-*) **Facture**

---

## 👥 Organisation du travail

### Équipe
Le projet est réalisé par **quatre développeurs** travaillant en collaboration.

### Rôles et responsabilités
Les développeurs se concentrent sur :
- La conception du système (diagrammes UML)
- Le développement de l'application
- Les tests et la validation

### Planification
Le projet suit un diagramme de Gantt structuré incluant :
- Phase de conception (diagrammes de classes, cas d'utilisation, séquence)
- Phase de développement (modules par modules)
- Phase de tests et validation
- Phase de documentation

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
- Java JDK 11 ou supérieur
- MySQL 8.0+
- IntelliJ IDEA (recommandé)
- Maven

### Installation

1. **Cloner le projet**
   ```bash
   git clone [URL_DU_REPO]
   cd TeethCare
   ```

2. **Configurer la base de données**
   ```bash
   # Créer la base de données
   mysql -u root -p
   CREATE DATABASE teethcare;
   
   # Importer le schéma
   mysql -u root -p teethcare < database/schema.sql
   
   # Importer les données de test (optionnel)
   mysql -u root -p teethcare < database/seed.sql
   ```

3. **Configurer la connexion**
   Modifier `src/main/resources/db.properties` :
   ```properties
   db.url=jdbc:mysql://localhost:3306/teethcare
   db.user=root
   db.password=votre_mot_de_passe
   ```

4. **Compiler et exécuter**
   ```bash
   mvn clean install
   java -jar target/TeethCare-1.0-SNAPSHOT.jar
   ```
   
   Ou depuis IntelliJ : Exécuter la classe `Main`

### Connexions par défaut
```
Administrateur:
- Email: admin@teethcare.ma
- Mot de passe: admin123

Médecin:
- Email: medecin@teethcare.ma
- Mot de passe: medecin123

Secrétaire:
- Email: secretaire@teethcare.ma
- Mot de passe: secretaire123
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

### Réalisations
✅ Application de gestion complète pour cabinet dentaire  
✅ Architecture MVC robuste avec séparation des couches  
✅ Gestion complète du cycle de vie patient  
✅ Système de facturation et suivi financier  
✅ Interface utilisateur intuitive et moderne  
✅ Système de rôles et permissions  
✅ Traçabilité complète avec logs  
✅ Gestion des statistiques et rapports  

### Perspectives d'amélioration
🔮 **Version Web** : Migration vers une architecture client-serveur  
🔮 **Application Mobile** : Version Android/iOS pour médecins  
🔮 **Télémédecine** : Intégration de consultations à distance  
🔮 **IA** : Aide au diagnostic et suggestions de traitements  
🔮 **Cloud** : Hébergement cloud pour accès distant  
🔮 **Interopérabilité** : Connexion avec systèmes externes (laboratoires, assurances)  
🔮 **Analytics avancés** : Tableaux de bord avec prédictions  
🔮 **Gestion du stock** : Module de gestion des consommables et équipements  

---

## 👨‍💻 Équipe projet

| Étudiant | Email | LinkedIn |
|----------|-------|----------|
| CHOUKHAIRI Noureddine | noureddine.choukhairi@emsi-edu.ma | [profil](#) |
| ELOUALI Haitam | haitam.elouali@emsi-edu.ma | [profil](#) |
| MHAMDI ALAOUI Hamza | hamza.mhamdialaoui@emsi-edu.ma | [profil](#) |
| MOKADAMI Zouhair | zouhair.mokadami@emsi-edu.ma | [profil](#) |

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
