package models

case class Country(
  id: Int,
  code: String,
  name: String
)

object Country {
  /**
   * Convertit une ligne CSV en un objet Country.
   * Retourne None si la ligne est invalide ou si les colonnes obligatoires sont manquantes.
   *
   * @param line Une ligne du fichier CSV.
   * @return Option[Country] représentant le pays parsé, ou None si la ligne est invalide.
   */
  def fromCsv(line: String): Option[Country] = {
    // Split la ligne en colonnes en gérant les virgules à l'intérieur des guillemets
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

    // Vérifie que la ligne a suffisamment de colonnes et qu'aucune colonne obligatoire n'est vide
    if (cols.length < 3 || cols(0).isEmpty || cols(1).isEmpty || cols(2).isEmpty) {
      None
    } else {
      // Convertit les colonnes en types appropriés
      for {
        id <- cols(0).toIntOption
      } yield Country(
        id,
        cols(1), // code
        cols(2)  // name
      )
    }
  }
}