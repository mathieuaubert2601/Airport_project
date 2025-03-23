package models

case class Runway(
  id: Int,
  airportRef: Int,
  surface: String,
  leIdent: String
)

object Runway {
  def fromCsv(line: String): Option[Runway] = {
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

    if (cols.length < 9 || cols(0).isEmpty || cols(1).isEmpty || cols(5).isEmpty || cols(8).isEmpty) {
      None
    } else {
      for {
        id <- cols(0).toIntOption
        airportRef <- cols(1).toIntOption
      } yield Runway(
        id,
        airportRef,
        cols(5),
        cols(8)
      )
    }
  }
}