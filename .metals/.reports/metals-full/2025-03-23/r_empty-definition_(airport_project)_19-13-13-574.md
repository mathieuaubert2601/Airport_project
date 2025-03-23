error id: local0
file:///C:/Users/maube/Desktop/Airport_project/src/main/scala/services/QueryService.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol local0
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

    // 1. Recherche exacte (code ou nom)
    val exactMatch = countries.find { c =>
      val normalizedCode = c.code.replaceAll("\\s", "").toLowerCase
      val normalizedName = c.name.replaceAll("\\s", "").toLowerCase
      normalizedCode == normalizedInput || normalizedName == normalizedInput
    }

    // 2. Si aucune correspondance exacte, recherche par début du nom
    val country = exactMatch.orElse {
      countries.find { c =>
        val normalizedName = c.name.replaceAll("\\s", "").toLowerCase
        normalizedName.startsWith(normalizedInput)
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
```

#### Short summary: 

empty definition using pc, found symbol in pc: 