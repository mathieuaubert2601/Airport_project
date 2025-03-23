error id: models/Country#name.
file:///C:/Users/maube/Desktop/Airport_project/src/main/scala/services/QueryService.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol models/Country#name.
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
package services

import models.{Airport, Runway, Country}

object QueryService {
  def findAirportsAndRunways(
    countryNameOrCode: String,
    countries: List[Country],
    airports: List[Airport],
    runways: List[Runway]
  ): List[(Airport, List[Runway])] = {
    val normalizedInput = countryNameOrCode.replaceAll("\\s", "").toLowerCase

    val country = countries.find(c =>
      c.code.replaceAll("\\s", "").toLowerCase.contains(normalizedInput) ||
      c.name.replaceAll("\\s", "").toLowerCase.contains(normalizedInput)
    )

    country.foreach { c =>
      println(s"Pays trouvé : Nom = ${c.name}, Code = ${c.code}")
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

```

#### Short summary: 

empty definition using pc, found symbol in pc: 