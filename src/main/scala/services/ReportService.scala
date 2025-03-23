package services

import models.{Airport, Runway, Country}

object ReportService {
  /**
   * Retourne les pays avec le plus grand nombre d'aéroports.
   *
   * @param airports La liste des aéroports.
   * @param countries La liste des pays.
   * @param topN Le nombre de pays à retourner.
   * @return Une liste de tuples (Country, Int) représentant les pays et leur nombre d'aéroports.
   */
  def topCountriesByAirportCount(airports: List[Airport], countries: List[Country], topN: Int): List[(Country, Int)] = {
    // Groupe les aéroports par code de pays et compte le nombre d'aéroports par pays
    val countryAirportCount = airports.groupBy(_.isoCountry).map {
      case (code, airports) => (countries.find(_.code == code), airports.size)
    }.toList

    // Trie les pays par nombre d'aéroports (décroissant) et prend les `topN` premiers
    countryAirportCount.sortBy(-_._2).take(topN).collect { case (Some(c), count) => (c, count) }
  }

  /**
   * Retourne les pays avec le moins grand nombre d'aéroports.
   *
   * @param airports La liste des aéroports.
   * @param countries La liste des pays.
   * @param topN Le nombre de pays à retourner.
   * @return Une liste de tuples (Country, Int) représentant les pays et leur nombre d'aéroports.
   */
  def lessCountriesByAirportCount(airports: List[Airport], countries: List[Country], topN: Int): List[(Country, Int)] = {
    // Groupe les aéroports par code de pays et compte le nombre d'aéroports par pays
    val countryAirportCount = airports.groupBy(_.isoCountry).map {
      case (code, airports) => (countries.find(_.code == code), airports.size)
    }.toList

    // Trie les pays par nombre d'aéroports (croissant) et prend les `topN` premiers
    countryAirportCount.sortBy(_._2).take(topN).collect { case (Some(c), count) => (c, count) }
  }

  def runwaySurfacesPerCountry(runways: List[Runway], airports: List[Airport], countries: List[Country]): List[(String, Set[String])] = {
    // Groupe les aéroports par code de pays
    val surfacesByCountry = airports.groupBy(_.isoCountry).map {
      case (code, airports) =>
        // Filtre les pistes associées aux aéroports du pays et extrait les surfaces
        val surfaces = runways.filter(r => airports.exists(_.id == r.airportRef)).map(_.surface).toSet
        // Associe le nom du pays à l'ensemble des surfaces
        (countries.find(_.code == code).map(_.name).getOrElse("Unknown"), surfaces)
    }

    // Trie les résultats par nom de pays (ordre alphabétique)
    surfacesByCountry.toList.sortBy(_._1)
  }

  /**
   * Retourne les identifiants de pistes les plus courants.
   *
   * @param runways La liste des pistes.
   * @param topN Le nombre d'identifiants à retourner.
   * @return Une liste des identifiants de pistes les plus courants.
   */
  def topRunwayIdentifications(runways: List[Runway], topN: Int): List[String] = {
    // Groupe les pistes par identifiant et compte le nombre de pistes par identifiant
    runways.groupBy(_.leIdent).map {
      case (ident, runways) => (ident, runways.size)
    }.toList
      // Trie les identifiants par nombre de pistes (décroissant) et prend les `topN` premiers
      .sortBy(-_._2)
      .take(topN)
      .map(_._1)
  }
}