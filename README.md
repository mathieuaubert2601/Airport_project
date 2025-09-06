# Airport Project ✈️

Bienvenue sur le projet **Airport Project**, une application conçue pour illustrer la gestion et l’analyse de données aéroportuaires à travers des outils modernes de développement logiciel et de data engineering.

## Objectifs du projet

- Centraliser, traiter et analyser des données liées aux aéroports.
- Illustrer les bonnes pratiques de structuration d’un projet Scala/Spark.
- Offrir une base pour des analyses avancées ou des extensions (prévision, visualisation…).

## Table des matières

- [Structure du projet](#structure-du-projet)
- [Installation & Prérequis](#installation--prérequis)
- [Utilisation](#utilisation)
- [Auteurs](#auteurs)

---

## Structure du projet

```
├── data/            # Jeux de données aéroportuaires
├── src/             # Code source principal (Scala, Spark)
│   └── main/        # Classes et modules principaux
│   └── test/        # Tests unitaires et d'intégration
├── project/         # Fichiers de configuration SBT
├── build.sbt        # Dépendances et configuration du projet Scala
├── java.jvmopts     # Configuration JVM
├── .vscode/         # Paramètres pour Visual Studio Code
├── .metals/         # Paramètres Metals (Scala)
├── .bsp/ .bloop/    # Build & compilation Scala
```

## Installation & Prérequis

Ce projet nécessite :

- **Scala** (2.12+ recommandé)
- **SBT** (outil de build Scala)
- **Spark** (pour le traitement distribué des données)
- **Java 8+**

Pour installer les dépendances :

```bash
sbt update
```

Pour compiler le projet :

```bash
sbt compile
```

## Utilisation

1. Placez vos fichiers de données dans le dossier `data/`.
2. Lancez le projet avec SBT :

```bash
sbt run
```

3. Pour les tests :

```bash
sbt test
```
