# OWNLY - Plateforme d'Enchères de Figurines 


**OWNLY** est une application web d'enchères spécialisée dans l'achat et la vente de cartes de collection de figurines.

Ce projet a été réalisé dans le cadre de ma formation **Développeur Web et Web Mobile (DWWM)** à l'**ENI École Informatique**. L'objectif était de concevoir une solution complète, du backend robuste au frontend responsive, permettant aux utilisateurs d'acquérir des objets de collection uniques via un système de crédits.

---

## 🚀 Installation et Lancement

### 1. Prérequis
* **Java 17** ou supérieur.
* Un serveur de base de données (SQL Server par défaut).

### 2. Configuration
Pour des raisons de sécurité, le fichier `src/main/resources/application.properties` contient des valeurs génériques sur ce dépôt.
1. Localisez le fichier `application.properties`.
2. Remplacez les balises `[VOTRE_...]` par vos propres identifiants (Base de données, Google OAuth2, SMTP Gmail).

### 3. Build et Dépendances
Avant de lancer, téléchargez les bibliothèques et compilez le projet :
```bash
./gradlew build
```

### 4. Lancer l'application
* **Via IntelliJ (Recommandé) :** Ouvrez le projet, laissez Gradle importer les dépendances et lancez la classe `OwnlyApplication`.
* **Via le Terminal :** Exécutez `./gradlew bootRun` (nécessite Java 17 configuré dans votre terminal).

L'application sera disponible sur `http://localhost:8080`.

---

## Fonctionnalités Clés

### Pour les Utilisateurs
* **Système d'Enchères en Temps Réel** : Mise en place d'offres sur des produits avec une date de début et de fin précise.
* **Gestion de Crédits** : Chaque utilisateur dispose d'un portefeuille de points/crédits pour enchérir.
* **Catalogue Catégorisé** : Recherche de figurines par catégories (ex: Fantasy, Sci-Fi, Super-Héros).
* **Fiches Produits Détaillées** : Affichage des cartes "FIGZ" avec visuels, descriptions et état de l'enchère.
* **Responsive Design** : Interface optimisée pour mobile, tablette et desktop grâce à Tailwind CSS.

### Espace Membre
* Inscription et connexion sécurisées.
* Tableau de bord : Suivi des enchères en cours et des ventes réalisées.
* Mise en vente : Création d'une nouvelle enchère avec upload d'images.

---

## Stack Technique

Ce projet repose sur une architecture MVC robuste et moderne.

| Domaine | Technologies |
| :--- | :--- |
| **Backend** | ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white) **Spring Boot** (Spring Data JPA, Spring Security, Spring MVC) |
| **Frontend** | ![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white) ![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=flat-square&logo=tailwind-css&logoColor=white) **Thymeleaf** (Moteur de template) |
| **Base de Données** | ![MySQL](https://img.shields.io/badge/MySQL-005C84?style=flat-square&logo=mysql&logoColor=white)|
| **Outils** | [![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=flat-square&logo=gradle&logoColor=white)](https://gradle.org) ![Git](https://img.shields.io/badge/GIT-E44C30?style=flat-square&logo=git&logoColor=white) |

---

## Structure du Projet

L'application suit l'architecture standard de Spring Boot :

```text
src/
├── main/
│   ├── java/fr/eni/ownly/
│   │   ├── bll/          # Couche Logique Métier (Services)
│   │   ├── bo/           # Business Objects (Entités JPA)
│   │   ├── dal/          # Data Access Layer (Repositories)
│   │   ├── ihm/          # Contrôleurs (Controllers) et Formulaires
│   │   └── security/     # Configuration Spring Security
│   └── resources/
│       ├── static/       # Assets (CSS Tailwind compilé, Images, JS)
│       ├── templates/    # Vues HTML (Thymeleaf)
│       └── application.properties
