package services

import models.{Airport, Runway, Country}

object QueryService {
  /**
   * Trouve les aéroports et les pistes associées à un pays donné (par nom ou code).
   *
   * @param countryNameOrCode Le nom ou le code du pays à rechercher.
   * @param countries La liste des pays.
   * @param airports La liste des aéroports.
   * @param runways La liste des pistes.
   * @return Une liste de tuples (Airport, List[Runway]) représentant les aéroports et leurs pistes.
   */
  def findAirportsAndRunways(
    countryNameOrCode: String,
    countries: List[Country],
    airports: List[Airport],
    runways: List[Runway]
  ): List[(Airport, List[Runway])] = {
    // Normalise l'entrée de l'utilisateur : supprime les espaces et met en minuscule
    val normalizedInput = countryNameOrCode.replaceAll("\\s", "").toLowerCase

    // Recherche le pays correspondant dans la liste des pays
    // Utilise une correspondance partielle (fuzzy matching) sur le code ou le nom du pays
    val country = countries.find(c =>
      c.code.replaceAll("\\s", "").toLowerCase.contains(normalizedInput) ||
      c.name.replaceAll("\\s", "").toLowerCase.contains(normalizedInput)
    )

    // Si un pays correspondant est trouvé
    country match {
      case Some(c) =>
        // Filtre les aéroports qui appartiennent à ce pays (en utilisant le code ISO du pays)
        val countryAirports = airports.filter(_.isoCountry == c.code)

        // Pour chaque aéroport trouvé, associe les pistes correspondantes (en utilisant l'ID de l'aéroport)
        countryAirports.map(a => (a, runways.filter(_.airportRef == a.id)))
      case None =>
        // Si aucun pays ne correspond, retourne une liste vide
        List.empty
    }
  }
}