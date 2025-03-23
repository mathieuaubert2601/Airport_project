package models

case class Runway(
  id: Int,
  airportRef: Int,
  surface: String,
  leIdent: String
)

object Runway {
  /**
   * Convertit une ligne CSV en un objet Runway.
   * Retourne None si la ligne est invalide ou si les colonnes obligatoires sont manquantes.
   *
   * @param line Une ligne du fichier CSV.
   * @return Option[Runway] représentant la piste parsée, ou None si la ligne est invalide.
   */
  def fromCsv(line: String): Option[Runway] = {
    // Split la ligne en colonnes en gérant les virgules à l'intérieur des guillemets
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1).map(_.trim)

    // Vérifie que la ligne a suffisamment de colonnes et qu'aucune colonne obligatoire n'est vide
    if (cols.length < 9 || cols(0).isEmpty || cols(1).isEmpty || cols(5).isEmpty || cols(8).isEmpty) {
      None
    } else {
      // Convertit les colonnes en types appropriés
      for {
        id <- cols(0).toIntOption
        airportRef <- cols(1).toIntOption
      } yield Runway(
        id,
        airportRef,
        cols(5), // surface
        cols(8)  // leIdent
      )
    }
  }
}