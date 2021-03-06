package sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Sudoku extends Application {
	private int counter = 0;

	private static Label lbl = new Label("");
	private static GridPane raster = new GridPane();
	private static GridPane rasterUI = new GridPane();

	private static int z = 0;
	private static int s = 0;
	private static String[] loesung = new String[81];

	@Override
	public void start(Stage primaryStage) {
		lbl.setId("eigenes-label");

		StackPane stack = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setStyle("-fx-background-color: #001825");

		rasterUI.getStyleClass().add("border-verlauf");
		rasterUI.setPrefWidth(500);
		rasterUI.setPrefHeight(500);
		rasterUI.setMaxSize(550, 550);

		rasterUI.setPadding(new Insets(10));
		rasterUI.setHgap(10);
		rasterUI.setVgap(10);

		// PseudoClass right = PseudoClass.getPseudoClass("right");
		// PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

		TextField[] tf = new TextField[81];
		TextField[] tfUI = new TextField[81];

		// "raster" mit Textfeldern füllen !!!!!! //Add(node, Spaltenindex, Zeilenindex)
		for (int zeile = 0; zeile <= 8; zeile++) {
			for (int spalte = 0; spalte <= 8; spalte++) {
				raster.add(tf[counter] = new TextField(), spalte, zeile); // erst spalte dann Zeile!!!
				rasterUI.add(tfUI[counter] = new TextField(), spalte, zeile); // erst spalte dann Zeile!!!
				counter++;
			}
		}

		// RowConstraints in GriDPane eintragen
		RowConstraints rc1 = new RowConstraints();
		rc1.setPercentHeight(12);
		RowConstraints rc2 = new RowConstraints();
		rc2.setPercentHeight(12);
		RowConstraints rc3 = new RowConstraints();
		rc3.setPercentHeight(12);
		RowConstraints rc4 = new RowConstraints();
		rc4.setPercentHeight(12);
		RowConstraints rc5 = new RowConstraints();
		rc5.setPercentHeight(12);
		RowConstraints rc6 = new RowConstraints();
		rc6.setPercentHeight(12);
		RowConstraints rc7 = new RowConstraints();
		rc7.setPercentHeight(12);
		RowConstraints rc8 = new RowConstraints();
		rc8.setPercentHeight(12);
		RowConstraints rc9 = new RowConstraints();
		rc9.setPercentHeight(12);
		raster.getRowConstraints().addAll(rc1, rc2, rc3, rc4, rc5, rc6, rc7, rc8, rc9);
		rasterUI.getRowConstraints().addAll(rc1, rc2, rc3, rc4, rc5, rc6, rc7, rc8, rc9);

		// ColumnConstraints in GriDPane eintragen
		ColumnConstraints cc1 = new ColumnConstraints();
		cc1.setPercentWidth(12);
		ColumnConstraints cc2 = new ColumnConstraints();
		cc2.setPercentWidth(12);
		ColumnConstraints cc3 = new ColumnConstraints();
		cc3.setPercentWidth(12);
		ColumnConstraints cc4 = new ColumnConstraints();
		cc4.setPercentWidth(12);
		ColumnConstraints cc5 = new ColumnConstraints();
		cc5.setPercentWidth(12);
		ColumnConstraints cc6 = new ColumnConstraints();
		cc6.setPercentWidth(12);
		ColumnConstraints cc7 = new ColumnConstraints();
		cc7.setPercentWidth(12);
		ColumnConstraints cc8 = new ColumnConstraints();
		cc8.setPercentWidth(12);
		ColumnConstraints cc9 = new ColumnConstraints();
		cc9.setPercentWidth(12);
		raster.getColumnConstraints().addAll(cc1, cc2, cc3, cc4, cc5, cc6, cc7, cc8, cc9);
		rasterUI.getColumnConstraints().addAll(cc1, cc2, cc3, cc4, cc5, cc6, cc7, cc8, cc9);

		// Buttons
		Button buttonLaden = new Button("DATEI LADEN");
		buttonLaden.setPrefWidth(120);
		buttonLaden.setId("eigener-button");

		Button buttonStart = new Button("START");
		buttonStart.setPrefWidth(120);
		buttonStart.setId("eigener-button");

		Button buttonLoesung = new Button("LÖSUNG");
		buttonLoesung.setPrefWidth(120);
		buttonLoesung.setId("eigener-button");

		Button buttonNeu = new Button("NEUES SPIEL");
		buttonNeu.setPrefWidth(120);
		buttonNeu.setId("eigener-button");

		VBox vb = new VBox(10, buttonLaden, buttonStart, buttonLoesung, buttonNeu, lbl);
		vb.setPadding(new Insets(10));

		Rectangle rv1 = new Rectangle(178, 0, 2, 550);
		rv1.setManaged(false);
		rv1.getStyleClass().add("verlauf");

		Rectangle rv2 = new Rectangle(350, 0, 2, 550);
		rv2.setManaged(false);
		rv2.getStyleClass().add("verlauf");

		Rectangle rh3 = new Rectangle(0, 184, 528, 2);
		rh3.setManaged(false);
		rh3.getStyleClass().add("verlauf");

		Rectangle rh4 = new Rectangle(0, 363, 528, 2);
		rh4.setManaged(false);
		rh4.getStyleClass().add("verlauf");

		stack.getChildren().addAll(rasterUI, rv1, rv2, rh3, rh4); //Stack legt Linienelemente über GridPane
		

		bp.setRight(vb);
		bp.setCenter(stack);
		BorderPane.setAlignment(rasterUI, Pos.CENTER_LEFT);

		Scene scene = new Scene(bp, 670, 550);
		scene.getStylesheets().add("style.css");
				

		//// BUTTON LADEN		
		buttonLaden.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int counter = 0;
				try {
					// BufferedReader anlegen & Zahlen aus generator.txt auslesen und in Sudoku Textfelder einfügen.
					Path taFile = Paths.get("generator.txt");
					System.out.println(taFile.toAbsolutePath());
					BufferedReader br = Files.newBufferedReader(taFile);
					String[] leser = new String[81];

					for (int i = 0; i < leser.length; i++) { 		//Datei zeilenweise einlesen
						leser[i] = br.readLine();
					}

					for (int zeile = 0; zeile <= 8; zeile++) {
						for (int spalte = 0; spalte <= 8; spalte++) {

							if (leser[counter].equals(" ")) {	//wenn leerzeichen, dann leeren String in Feld eintragen
								tf[counter].setText("");
								tfUI[counter].setText("");

							} else {
								tf[counter].setText(leser[counter]);
								tfUI[counter].setText(leser[counter]);

							}

							if (tf[counter].getText().equals("")) { //Wenn Feld mit leerem String eingetragen, dann editierbar lassen
								tf[counter].setEditable(true);
								tfUI[counter].setEditable(true);

							} else {
								tf[counter].setEditable(false);		//Wenn Feld mit String aus Datei gefüllt,dann nicht mehr editierbar machen
								tfUI[counter].setEditable(false);

							}
							counter++;
						}
					}
					lbl.setText("DATEI GELESEN!");
					br.close();

					solveSudoku(); 		// Wenn alle Zeilen ausgelesen und alle Felder Befüllt, dann Lösung berechnen 

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		//// BUTTON LÖSUNG
		buttonLoesung.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int cl = 0;
				for (int zeile = 0; zeile <= 8; zeile++) {
					for (int spalte = 0; spalte <= 8; spalte++) {
						TextField tf = (TextField) getNode(zeile, spalte, rasterUI); // Lösung aus dem Lösungs Array holen und in Felder der UI GridPane schreiben
						tf.setText(loesung[cl]);
						cl++;
						lbl.setText("DAS IST DIE\nLÖSUNG!");
					}
				}
			}
		});

		//// BUTTON START
		buttonStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				startMethode();
				lbl.setText("LOS GEHTS'S");
			}
		});

		//// BUTTON NEU
		buttonNeu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int cl = 0;
				for (int zeile = 0; zeile <= 8; zeile++) {
					for (int spalte = 0; spalte <= 8; spalte++) {
						TextField tf = (TextField) getNode(zeile, spalte, rasterUI);
						tf.setText("");
						tf.setStyle("-fx-control-inner-background: #ffffff");
						cl++;
						lbl.setText("");

					}
				}
			}
		});

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("FX Sudoku");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	// Hole Node(TextField) aus Zelle der GridPane damit Zugriff auf Inhalt erfolgen kann
	public static Node getNode(final int zeile, final int spalte, GridPane r) {
		Node result = null;
		ObservableList<Node> childrens = r.getChildren();
		for (Node node : childrens) {
			if (r.getRowIndex(node) == zeile && r.getColumnIndex(node) == spalte) {
				result = node;
				break;
			}
		}
		return result;
	}

	// Nummer in aktueller Zeile suchen
	public static boolean istNrInZeile(int nummer, int zeile) {
		String n = Integer.toString(nummer);
		for (int spalte = 0; spalte <= 8; spalte++) {
			TextField tf = (TextField) getNode(zeile, spalte, raster);

			if (tf.getText().equals(n)) {
				return true;
			}
		}
		return false;
	}

	// Nummer in aktueller Spalte suchen
	public static boolean istNrInSpalte(int nummer, int spalte) {
		String n = Integer.toString(nummer);
		for (int zeile = 0; zeile <= 8; zeile++) {
			TextField tf = (TextField) getNode(zeile, spalte, raster);

			if (tf.getText().equals(n)) {
				return true;
			}
		}
		return false;
	}

	// Nummer in 3x3 Bereich suchen suchen
	public static boolean istNrInBereich(int nummer, int spalte, int zeile) {
		// Obere linke ecke des Bereichs finden z.b nummer ist in spalte 4 zeile 7
		int bereichZeile = zeile - zeile % 3; // zeile index 7 - (zeile index 7 mod 3 = 1) == Zeile index 6
		int bereichSpalte = spalte - spalte % 3; // spalte index 4 - (spalte index 4 mod 3 = 1) == Spalte index 3
		String n = Integer.toString(nummer);

		for (int bz = bereichZeile; bz < bereichZeile + 3; bz++) {
			// System.out.println("bereich loop 1");

			for (int bs = bereichSpalte; bs < bereichSpalte + 3; bs++) {
				TextField tf = (TextField) getNode(bz, bs, raster);
				// System.out.println("bereich loop 2");
				// System.out.println(tf.getText());

				if (tf.getText().equals(n)) {
					// System.out.println("bereich loop 3a");
					return true;
				}
			}
		}
		return false;
	}

	// erlaubte Platzierung? Zusammenfassung aller drei Methoden: spalte, zeile, bereich
	public static boolean istNummerErlaubt(int nummer, int spalte, int zeile) {

		return (!istNrInZeile(nummer, zeile) && !istNrInSpalte(nummer, spalte) //Wenn nummer nirgendwo gefunden, dann istNummerErlaubt == true
				&& !istNrInBereich(nummer, spalte, zeile));
	}
	
	
	public static boolean solveSudoku() {
		int counter = 0;
		for (int zeile = 0; zeile <= 8; zeile++) {
			for (int spalte = 0; spalte <= 8; spalte++) {

				TextField tf = (TextField) getNode(zeile, spalte, raster);

				if (tf.getText().equals("")) { // Finde leeres Feld
					// System.out.println("leeres Feld gefunden");

					for (int testNummer = 1; testNummer <= 9; testNummer++) { //Testnummern generieren
						// System.out.println("Testnummer Loop");

						if (istNummerErlaubt(testNummer, spalte, zeile)) { // Setze testnummer in leeres Feld und prüfe ob nummer erlaubt ist
							String tn = Integer.toString(testNummer);
							tf.setText(tn);								//Wenn ja, dann trage nummer ein
							// System.out.println("Testnummer " + tn);

							if (solveSudoku()) { //lässt sich das Sudoku mit der zuletzt eingetragenen testNummer bis zum Ende lösen? 
								return true; 	//Wenn ja, dann bleibt die nummer Drin und das Sudoku wird mit der neu eingetragenen nummer erneut mit solveSudoku() durchlaufen
							} else {
								tf.setText(""); //Wenn nein dann wird die eingetragene Testnummer auf null gesetzt und der loop geht mit nächster Testnummer weiter
							}
						}
					}
					// System.out.println("Solve false");
					return false; //ist das sudoku nicht lösbar, dann return false
				}
			}
		}
		
		//Wenn code bis hier her kommt, konnte Sudoku gelöst werden
		System.out.println("SUDOKU GELÖST");
		lbl.setText("START DRÜCKEN\nUM ZU SPIELEN");

		for (int zeile = 0; zeile <= 8; zeile++) {		//schreibe Lösung in Array
			for (int spalte = 0; spalte <= 8; spalte++) {
				TextField tf = (TextField) getNode(zeile, spalte, raster);
				loesung[counter] = tf.getText();
				counter++;
			}
		}
		return true;
	}

	public static void startMethode() {
		int c1 = 0;

		for (int zeile = 0; zeile <= 8; zeile++) {
			z = zeile;

			for (int spalte = 0; spalte <= 8; spalte++) {
				String vergleich = loesung[c1];
				s = spalte;
				c1++;

				TextField tf = (TextField) getNode(zeile, spalte, rasterUI);

				// erlaube nur einen Char als eingabe
				tf.setTextFormatter(new TextFormatter<String>((Change change) -> {
					String newText = change.getControlNewText();
					if (newText.length() > 1) {
						return null;
					} else {
						return change;
					}
				}));
				
				//Event wenn beim Tippen die Taste losgelassen wird
				tf.setOnKeyReleased(new EventHandler<KeyEvent>() {

					public void handle(KeyEvent ke) {

						if (tf.getText().matches("/(^$)|^[1-9]{1}+$")) { // regex erlaube nur zahlen von 1 bis 9 und leer und nur
																			// eine Zahl als Eingabe
							//System.out.println(tf.getText());

							tf.setStyle("-fx-control-inner-background: #ffffff"); //Standard Zustand Textfeld Farbe weiss
							

							if (!tf.getText().isEmpty() && tf.getText().equals(vergleich)) { //wenn eingegebene Zahl  == Lösung, dann hintergrund grün und Meldung
								System.out.println("Gut");
								tf.setStyle("-fx-control-inner-background: green");
								System.out.println("Korrekte Nummer ist " + vergleich);
								lbl.setText("Gut!");
							}

						} else if (tf.getText().equals("")) {						//wenn Textfeld leer, dann Hintergrund wieder auf Weiss
							tf.setStyle("-fx-control-inner-background: #ffffff");
							System.out.println("is empty!");
							lbl.setText("");
						} else {
							tf.setStyle("-fx-control-inner-background: red"); //Wenn eingetragener String nicht 1 bis 9 dann Rot und Meldung
							System.out.println("INVALID INPUT!");
							lbl.setText("Ungültige Eingabe!");
						}
					}
				});

			}
		}
	}
}
