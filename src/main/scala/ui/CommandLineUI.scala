package ui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField, TextArea}
import scalafx.scene.layout.{VBox, HBox, BorderPane}
import scalafx.geometry.{Insets, Pos}
import services.{QueryService, ReportService, CsvParser}
import models.{Airport, Runway, Country}

object GUIApp extends JFXApp {
  // Charge les données depuis les fichiers CSV
  val airports = CsvParser.parseAirports("data/airports.csv")
  val runways = CsvParser.parseRunways("data/runways.csv")
  val countries = CsvParser.parseCountries("data/countries.csv")

  // Vérifie si les données ont été chargées correctement
  if (airports.isEmpty || runways.isEmpty || countries.isEmpty) {
    println("Erreur : Impossible de charger les fichiers CSV. Vérifiez les chemins et le format des fichiers.")
    System.exit(1)
  }

  // Déclare les variables pour les zones de texte
  private val resultArea1 = new TextArea {
    editable = false
    wrapText = true
    promptText = "Country Data"
  }

  private val resultArea2 = new TextArea {
    editable = false
    wrapText = true
    promptText = "Airport Data"
  }

  private val resultArea3 = new TextArea {
    editable = false
    wrapText = true
    promptText = "Runway Data"
  }

  private val singleResultArea = new TextArea {
    editable = false
    wrapText = true
    promptText = "Report Results"
    minWidth = 600
    minHeight = 400
  }

  // Crée la fenêtre principale
  stage = new PrimaryStage {
    title = "Airport Data Explorer"
    scene = new Scene(1000, 750) {
      root = new BorderPane {
        padding = Insets(20)

        // Titre
        top = new Label {
          text = "Airport Data Explorer"
          style = "-fx-font-size: 20px; -fx-font-weight: bold;"
          alignment = Pos.Center
        }

        val reportButton = new Button("Report") {
          onAction = _ => {
            queryInput.visible = true
            queryInput.managed = true
            executeReportButton.visible = true
            executeReportButton.managed = true
            reportCenterBox.visible = true
            reportCenterBox.managed = true
            queryCenterBox.visible = false
            queryCenterBox.managed = false
            singleResultArea.text = "" // Efface les résultats précédents
          }
        }

        val queryButton = new Button("Query") {
          onAction = _ => {
            queryInput.visible = false
            queryInput.managed = false
            executeReportButton.visible = false
            executeReportButton.managed = false
            reportCenterBox.visible = false
            reportCenterBox.managed = false
            queryCenterBox.visible = true
            queryCenterBox.managed = true
            displayQueryResults()
          }
        }


        // Champ de saisie pour Report
        val queryInput = new TextField {
          promptText = "Enter country name or code"
          visible = false
        }

        // Bouton pour exécuter Report
        val executeReportButton = new Button("Execute Report") {
          onAction = _ => {
            val input = queryInput.text.value
            val results = QueryService.findAirportsAndRunways(input, countries, airports, runways)
            val output = results.map { case (airport, runways) =>
              s"Airport: ${airport.name}\n" +
              runways.map(r => s"  Runway: ${r.leIdent} (Surface: ${r.surface})").mkString("\n")
            }.mkString("\n\n")
            singleResultArea.text = if (output.nonEmpty) output else "No results found."
          }
          visible = false
        }

        // Layout pour les boutons et la barre de saisie
        val topBox = new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(reportButton, queryButton, queryInput, executeReportButton)
        }

        // Layout pour Report
        val reportCenterBox = new VBox {
          spacing = 10
          children = Seq(new Label("Report Data:"),
          singleResultArea)
          visible = false
          managed = false // Empêche de réserver de l’espace quand caché
        }

        // Layout pour Query
        val queryCenterBox = new VBox {
          spacing = 10
          children = Seq(
            new Label("Country Data:"),
            resultArea1,
            new Label("Airport Data:"),
            resultArea2,
            new Label("Runway Data:"),
            resultArea3
          )
          visible = false
          managed = false // Empêche de réserver de l’espace quand caché
        }


        // Ajoute les éléments à la fenêtre
        top = topBox
        center = new VBox {
          spacing = 10
          children = Seq(reportCenterBox, queryCenterBox)
        }
      }
    }
  }

  /**
   * Affiche les résultats de Query dans les zones de texte.
   */
  private def displayQueryResults(): Unit = {
    // Affiche les 10 pays avec le plus grand nombre d'aéroports
    val topCountries = ReportService.topCountriesByAirportCount(airports, countries, 10)
    val lessCountries = ReportService.lessCountriesByAirportCount(airports,countries,10)
    resultArea1.text =
      "Top 10 countries with most airports:\n\n"+
      topCountries.map { case (country, count) => s"${country.name}: $count" }.mkString("\n") + 
      "\n\nTop 10 countries with less airports:\n\n" +
      lessCountries.map { case (country, count) => s"${country.name}: $count" }.mkString("\n")

    // Affiche les types de surfaces de pistes par pays
    val surfaces = ReportService.runwaySurfacesPerCountry(runways, airports, countries)
    resultArea2.text =
      surfaces.map { case (country, surfaces) => s"$country: ${surfaces.mkString(", ")}" }.mkString("\n")

    // Affiche les 10 identifiants de pistes les plus courants
    val topRunwayIdents = ReportService.topRunwayIdentifications(runways, 10)
    resultArea3.text =
      topRunwayIdents.mkString("\n")
  }
}