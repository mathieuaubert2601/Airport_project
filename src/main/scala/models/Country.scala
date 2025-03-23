package models

case class Country(
  id: Int,
  code: String,
  name: String
)

object Country {
  def fromCsv(line: String): Option[Country] = {
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

    if (cols.length < 3 || cols(0).isEmpty || cols(1).isEmpty || cols(2).isEmpty) {
      None
    } else {
      for {
        id <- cols(0).toIntOption
      } yield Country(
        id,
        cols(1),
        cols(2)
      )
    }
  }
}
