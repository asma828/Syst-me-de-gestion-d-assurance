# Assurance Management Console App

## Description du projet
Cette application console en Java permet à une société d’assurance de digitaliser la gestion de ses services.  
L’utilisateur peut gérer les **conseillers**, les **clients**, les **contrats** et les **sinistres** via un menu interactif.  
Le projet utilise des concepts de **programmation fonctionnelle** : Streams, Lambdas, Method References et Optional pour rendre le code plus propre et sécurisé.

---

## Technologies utilisées
- Java 8
- JDBC pour la persistance des données
- Java Streams API pour le traitement des collections
- Java Time API pour la gestion des dates
- Lambdas et Method References pour simplifier le code
- Optional pour la gestion des valeurs nulles

---

## Structure du projet
## Structure du projet
```text
Assurance/
│── src/
│   │── Main.java                     # Point d’entrée du programme
│   │
│   ├── View/
│   │   ├── ClientView.java           # Menus et sous-menus relatifs aux clients
│   │   ├── ContratView.java          # Menus et sous-menus relatifs aux contrats
│   │   └── SinistreView.java         # Menus et sous-menus relatifs aux sinistres
│   │
│   ├── Service/
│   │   ├── ConseillerService.java    # Logique métier pour les conseillers
│   │   ├── ClientService.java        # Logique métier pour les clients
│   │   ├── ContratService.java       # Logique métier pour les contrats
│   │   └── SinistreService.java      # Logique métier pour les sinistres
│   │
│   ├── DAO/
│   │   ├── ConseillerDAO.java        # Accès aux conseillers dans la DB
│   │   ├── ClientDAO.java            # Accès aux clients dans la DB
│   │   ├── ContratDAO.java           # Accès aux contrats dans la DB
│   │   └── SinistreDAO.java          # Accès aux sinistres dans la DB
│   │
│   ├── Model/
│   │   ├── Person.java               # Classe de base : nom, prénom, email
│   │   ├── Conseiller.java           # Hérite de Person
│   │   ├── Client.java               # Hérite de Person, référence un conseiller
│   │   ├── Contrat.java              # Id, typeContrat, dateDebut, dateFin, client
│   │   └── Sinistre.java             # Id, typeSinistre, dateTime, cout, description, contrat
│   │
│   └── Enums/
│       ├── TypeContrat.java          # AUTOMOBILE, MAISON, MALADIE
│       └── TypeSinistre.java         # ACCIDENT_VOITURE, ACCIDENT_MAISON, MALADIE
│
│── out/                              # Classes compilées
│── uml/                              # Diagrammes UML
│── .gitignore                        # Git ignore file
│── Assurance.iml                     # Fichier projet IntelliJ
└── README.md                         # Documentation du projet


## Détails des classes principales

### Modèles
- **Person** : nom, prenom, email
- **Conseiller** : hérite de Person
- **Client** : hérite de Person et référence un Conseiller
- **Contrat** : id, typeContrat (ENUM), dateDebut, dateFin, client
- **Sinistre** : id, typeSinistre (ENUM), dateTime, cout, description, contrat

### Enums
- **TypeContrat** : AUTOMOBILE, MAISON, MALADIE
- **TypeSinistre** : ACCIDENT_VOITURE, ACCIDENT_MAISON, MALADIE

### Services
- **ConseillerService, ClientService, ContratService, SinistreService** : CRUD, filtrage, tri

### DAO
- **ClientDAO, ContratDAO, SinistreDAO** : communication avec la base de données

### Vues
- **ClientView, ContratView, SinistreView** : menus et sous-menus pour l’interaction avec l’utilisateur

---

## Prérequis
- Java 8 installé
- Une base de données relationnelle (ex: MySQL) configurée
- JDBC Driver pour la base de données
- IDE Java ou terminal pour compiler et exécuter le projet

---

## Quitter
Pour quitter l’application console, sélectionner l’option **0. Exit** depuis le menu principal.