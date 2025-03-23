package ui

import services.{QueryService, ReportService, CsvParser}
import models.{Airport, Runway, Country}
import scala.util.{Try, Success, Failure}

object CommandLineUI {
  def main(args: Array[String]): Unit = {
    // Chemins des fichiers CSV (pourraient être passés en arguments de ligne de commande)
    val airportsFilePath = "data/airports.csv"
    val runwaysFilePath = "data/runways.csv"
    val countriesFilePath = "data/countries.csv"

    // Charge les données depuis les fichiers CSV
    val airports = CsvParser.parseAirports(airportsFilePath)
    val runways = CsvParser.parseRunways(runwaysFilePath)
    val countries = CsvParser.parseCountries(countriesFilePath)

    // Vérifie si les données ont été chargées correctement
    if (airports.isEmpty || runways.isEmpty || countries.isEmpty) {
      println("Erreur : Impossible de charger les fichiers CSV. Vérifiez les chemins et le format des fichiers.")
      System.exit(1)
    }

    // Affiche le menu principal
    println("Choose an option: 1. Query 2. Reports")
    val choice = scala.io.StdIn.readLine()

    choice match {
      case "1" => // Option Query
        println("Enter country name or code:")
        val input = scala.io.StdIn.readLine()
        println(s"Input received: '$input'")

        // Recherche les aéroports et les pistes pour le pays donné
        val results = QueryService.findAirportsAndRunways(input, countries, airports, runways)

        // Affiche les résultats
        if (results.isEmpty) {
          println("Aucun résultat trouvé pour ce pays.")
        } else {
          results.foreach { case (airport, runways) =>
            println(s"Airport: ${airport.name}")
            runways.foreach(r => println(s"  Runway: ${r.leIdent} (Surface: ${r.surface})"))
          }
        }

      case "2" => // Option Reports
        // Affiche les 10 pays avec le plus grand nombre d'aéroports
        val topCountries = ReportService.topCountriesByAirportCount(airports, countries, 10)
        println("Top 10 countries with most airports:")
        topCountries.foreach { case (country, count) => println(s"${country.name}: $count") }

        // Affiche les 10 pays avec le moins grand nombre d'aéroports
        val lessCountries = ReportService.lessCountriesByAirportCount(airports, countries, 10)
        println("\nTop 10 countries with least airports:")
        lessCountries.foreach { case (country, count) => println(s"${country.name}: $count") }

        // Affiche les types de surfaces de pistes par pays
        val surfaces = ReportService.runwaySurfacesPerCountry(runways, airports, countries)
        println("\nRunway surfaces per country:")
        surfaces.foreach { case (country, surfaces) => println(s"$country: ${surfaces.mkString(", ")}") }

        // Affiche les 10 identifiants de pistes les plus courants
        val topRunwayIdents = ReportService.topRunwayIdentifications(runways, 10)
        println("\nTop 10 most common runway identifications:")
        topRunwayIdents.foreach(println)

      case _ => // Option invalide
        println("Invalid choice. Please choose 1 or 2.")
    }
  }
}