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
  /**
   * Convertit une ligne CSV en un objet Airport.
   * Retourne None si la ligne est invalide ou si les colonnes obligatoires sont manquantes.
   *
   * @param line Une ligne du fichier CSV.
   * @return Option[Airport] représentant l'aéroport parsé, ou None si la ligne est invalide.
   */
  def fromCsv(line: String): Option[Airport] = {
    // Split la ligne en colonnes en gérant les virgules à l'intérieur des guillemets
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

    // Vérifie que la ligne a suffisamment de colonnes et qu'aucune colonne obligatoire n'est vide
    if (cols.length < 9 || cols(0).isEmpty || cols(1).isEmpty || cols(3).isEmpty || cols(4).isEmpty || cols(5).isEmpty || cols(6).isEmpty || cols(8).isEmpty) {
      None
    } else {
      // Convertit les colonnes en types appropriés
      for {
        id <- cols(0).toIntOption
        latitude <- cols(4).toDoubleOption
        longitude <- cols(5).toDoubleOption
        elevation <- cols(6).toIntOption
      } yield Airport(
        id,
        cols(1), // ident
        cols(3), // name
        latitude,
        longitude,
        elevation,
        cols(8) // isoCountry
      )
    }
  }
}