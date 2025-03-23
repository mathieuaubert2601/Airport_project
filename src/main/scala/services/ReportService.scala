package services

import models.{Airport, Runway, Country}

object ReportService {
  def topCountriesByAirportCount(airports: List[Airport], countries: List[Country], topN: Int): List[(Country, Int)] = {
    val countryAirportCount = airports.groupBy(_.isoCountry).map {
      case (code, airports) => (countries.find(_.code == code), airports.size)
    }.toList

    countryAirportCount.sortBy(-_._2).take(topN).collect { case (Some(c), count) => (c, count) }
  }

  def lessCountriesByAirportCount(airports: List[Airport], countries: List[Country], topN: Int): List[(Country, Int)] = {
    val countryAirportCount = airports.groupBy(_.isoCountry).map {
      case (code, airports) => (countries.find(_.code == code), airports.size)
    }.toList

    countryAirportCount.sortBy(_._2).take(topN).collect { case (Some(c), count) => (c, count) }
  }

  def runwaySurfacesPerCountry(runways: List[Runway], airports: List[Airport], countries: List[Country]): List[(String, Set[String])] = {
    val surfacesByCountry = airports.groupBy(_.isoCountry).map {
      case (code, airports) =>
        val surfaces = runways.filter(r => airports.exists(_.id == r.airportRef)).map(_.surface).toSet
        (countries.find(_.code == code).map(_.name).getOrElse("Unknown"), surfaces)
    }

    surfacesByCountry.toList.sortBy(_._1)
  }

  def topRunwayIdentifications(runways: List[Runway], topN: Int): List[String] = {
    runways.groupBy(_.leIdent).map {
      case (ident, runways) => (ident, runways.size)
    }.toList
      .sortBy(-_._2)
      .take(topN)
      .map(_._1)
  }
}