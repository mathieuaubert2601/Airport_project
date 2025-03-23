package services

import models.{Airport, Runway, Country}

object QueryService {
  def findAirportsAndRunways(
    countryNameOrCode: String,
    countries: List[Country],
    airports: List[Airport],
    runways: List[Runway]
  ): List[(Airport, List[Runway])] = {
    val normalizedInput = countryNameOrCode.trim.replaceAll("\\s", "").replace("\"", "").toLowerCase

    val exactMatch = countries.find { c =>
      val normalizedCode = c.code.trim.replaceAll("\\s", "").replace("\"", "").toLowerCase
      val normalizedName = c.name.trim.replaceAll("\\s", "").replace("\"", "").toLowerCase
      normalizedCode == normalizedInput || normalizedName == normalizedInput
    }

    val country = exactMatch.orElse {
      countries.find { c =>
        val normalizedName = c.name.trim.replaceAll("\\s", "").replace("\"", "").toLowerCase
        normalizedName.contains(normalizedInput)
      }
    }

    country match {
      case Some(c) =>
        val countryAirports = airports.filter(_.isoCountry == c.code).sortBy(_.name)
        countryAirports.map(a => (a, runways.filter(_.airportRef == a.id)))
      case None =>
        List.empty
    }
  }
}
