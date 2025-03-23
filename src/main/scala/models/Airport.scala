package models

case class Airport(
  id: Int,
  ident: String,
  name: String,
  latitude: Double,
  longitude: Double,
  elevation: Int,
  isoCountry: String
)

object Airport {
  def fromCsv(line: String): Option[Airport] = {
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

    if (cols.length < 9 || cols(0).isEmpty || cols(1).isEmpty || cols(3).isEmpty || cols(4).isEmpty || cols(5).isEmpty || cols(6).isEmpty || cols(8).isEmpty) {
      None
    } else {
      for {
        id <- cols(0).toIntOption
        latitude <- cols(4).toDoubleOption
        longitude <- cols(5).toDoubleOption
        elevation <- cols(6).toIntOption
      } yield Airport(
        id,
        cols(1),
        cols(3),
        latitude,
        longitude,
        elevation,
        cols(8)
      )
    }
  }
}