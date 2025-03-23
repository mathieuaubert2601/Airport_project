error id: scala/collection/immutable/List#filter().
file:///C:/Users/maube/Desktop/Airport_project/src/main/scala/services/QueryService.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol scala/collection/immutable/List#filter().
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
    println(normalizedInput)


    val country = countries.find(c =>
      c.code.replaceAll("\\s", "").toLowerCase == normalizedInput ||
      c.name.replaceAll("\\s", "").toLowerCase == normalizedInput ||
      c.name.replaceAll("\\s", "").toLowerCase.contains(normalizedInput) ||
      c.code.replaceAll("\\s", "").toLowerCase.contains(normalizedInput) 
      
    )
    

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