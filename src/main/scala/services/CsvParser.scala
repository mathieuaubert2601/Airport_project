package services

import models.{Airport, Runway, Country}
import scala.io.Source
import scala.util.Try

object CsvParser {
  def parseAirports(filePath: String): List[Airport] = {
    Source.fromFile(filePath).getLines().drop(1).flatMap(Airport.fromCsv).toList
  }

  def parseRunways(filePath: String): List[Runway] = {
    Source.fromFile(filePath).getLines().drop(1).flatMap(Runway.fromCsv).toList
  }

  def parseCountries(filePath: String): List[Country] = {
    Source.fromFile(filePath).getLines().drop(1).flatMap(Country.fromCsv).toList
  }
}