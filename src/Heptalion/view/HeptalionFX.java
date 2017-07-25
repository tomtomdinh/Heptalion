package hw5.view;

import java.util.Optional;

import hw5.model.Board;
import hw5.model.Game;
import hw5.model.Symbol;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HeptalionFX extends Application {
	private VBox player1Box;
	private VBox player2Box;

	@Override
	public void start(Stage primaryStage) {

		getPlayerNames(primaryStage);

	}

	// sets up a scene to get the player names, forces the user to enter names
	// before starting the game
	private void getPlayerNames(Stage primaryStage) {

		TextField text1 = new TextField();
		TextField text2 = new TextField();
		Label player1 = new Label("Enter player 1 name:");
		Label player2 = new Label("Enter player 2 name:");
		Button createButton = new Button("Create Game");

		createButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String player1Name = null, player2Name = null;

				if (text1.getText() != null && !text1.getText().trim().isEmpty()) {
					player1Name = text1.getText();
				}

				if (text2.getText() != null && !text2.getText().trim().isEmpty()) {
					player2Name = text2.getText();
				}

				if (player1Name != null && player2Name != null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					Text t = new Text("To play this turn-based game, start by dragging one of the domino symbols to match on the board. "
									+ "A prompt will pop-up letting you decide where the other half of the domino piece will go! Good luck!");
					alert.setTitle("How to play");
					alert.setHeaderText(null);
					alert.setContentText(t.getText());
					alert.showAndWait();
					gameSetUp(primaryStage, player1Name, player2Name);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Please enter names!");
					alert.showAndWait();
				}
			}
		});

		VBox root = new VBox();
		Scene playerSetUpScene = new Scene(root);

		root.getChildren().add(player1);
		root.getChildren().add(text1);
		root.getChildren().add(player2);
		root.getChildren().add(text2);
		root.getChildren().add(createButton);

		primaryStage.setScene(playerSetUpScene);
		primaryStage.show();

	}

	// sets up the game by passing on the player names.
	// also sets up the labels for the player names
	// sets up the domino pieces and makes them draggable
	private void gameSetUp(Stage primaryStage, String p1, String p2) {
		Game game = new Game(p1, p2);
		GridPane grid = new GridPane();

		BorderPane border = new BorderPane();
		player1Box = new VBox(5);
		player2Box = new VBox(5);

		Label player1 = new Label(p1);
		Label player2 = new Label(p2);

		player1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		player2.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		HBox playerTurnBox = new HBox();
		Label currentPlayer = new Label(player1.getText() + " it's your turn!");

		currentPlayer.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		playerTurnBox.setStyle("-fx-background-color: orange");
		playerTurnBox.getChildren().add(currentPlayer);
		playerTurnBox.setAlignment(Pos.CENTER);

		border.setTop(playerTurnBox);
		createBoard(game.getBoard(), grid);
		border.setCenter(grid);

		player1Box.setPadding(new Insets(10, 50, 50, 50));
		player2Box.setPadding(new Insets(10, 50, 50, 50));

		player1Box.getChildren().add(player1);
		player2Box.getChildren().add(player2);
		player1Box.setAlignment(Pos.CENTER);
		player2Box.setAlignment(Pos.CENTER);

		createPlayerHand(game, player1Box, 0);
		createPlayerHand(game, player2Box, 1);
		border.setLeft(player1Box);
		border.setRight(player2Box);

		player2Box.setDisable(true);

		takeTurn(game, player1Box, grid, primaryStage, border, currentPlayer);
		takeTurn(game, player2Box, grid, primaryStage, border, currentPlayer);

		Scene boardScene = new Scene(border);
		primaryStage.setTitle("Heptalion");
		primaryStage.setScene(boardScene);
		primaryStage.show();
	}

	// returns a specific node from the gridpane
	private Node getNodeFromPane(GridPane gridPane, int col, int row) {
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return node;
			}
		}
		return null;
	}

	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;

	// eventhandling for the moving the domino pieces around, calls both
	// listeners for both players
	// will handle how to get the other symbol also
	private void takeTurn(Game game, VBox playerBox, GridPane grid, Stage primaryStage, BorderPane border,
			Label currentPlayer) {

		for (Node playerHand : playerBox.getChildren()) {
			if (playerHand instanceof GridPane) {
				for (Node domImg : ((GridPane) playerHand).getChildren()) {
					if (domImg instanceof ImageView) {
						domImg.setOnDragDetected(new EventHandler<MouseEvent>() {
							public void handle(MouseEvent event) {

								Dragboard db = domImg.startDragAndDrop(TransferMode.ANY);

								Integer colIndex = GridPane.getColumnIndex(event.getPickResult().getIntersectedNode());

								int c = colIndex;
								int index = playerBox.getChildren().indexOf(playerHand) - 1;

								// picks which domino on the current player's
								// domino
								game.setDom(index);
								// picks which symbol dragged
								game.setNumSymbol(c);

								ClipboardContent content = new ClipboardContent();
								content.putImage(((ImageView) domImg).getImage());
								db.setContent(content);
								event.consume();
							}
						});
					}
				}
			}
		}
		for (Node node : grid.getChildren()) {
			node.setOnDragOver(new EventHandler<DragEvent>() {

				public void handle(DragEvent event) {

					if (event.getGestureSource() != node && event.getDragboard().hasImage()) {
						event.acceptTransferModes(TransferMode.MOVE);
					}

					event.consume();
				}
			});

			node.setOnDragEntered(new EventHandler<DragEvent>() {
				public void handle(DragEvent event) {
					if (event.getGestureSource() != node && event.getDragboard().hasImage()) {
						node.setOpacity(.5);
					}
					event.consume();
				}
			});
			node.setOnDragExited(new EventHandler<DragEvent>() {
				public void handle(DragEvent event) {
					node.setOpacity(1);
					event.consume();
				}
			});

			node.setOnDragDropped(new EventHandler<DragEvent>() {
				public void handle(DragEvent event) {

					Dragboard db = event.getDragboard();

					boolean success = true;

					if (node != grid && db.hasImage()) {
						Integer cIndex = GridPane.getColumnIndex(event.getPickResult().getIntersectedNode());
						Integer rIndex = GridPane.getRowIndex(event.getPickResult().getIntersectedNode());

						if (game.getBoard().getFace(rIndex, cIndex) == null) {
							System.out.println("you got here");
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("Oops!");
							alert.setContentText("You need to drag the symbol onto a non empty space!");
							alert.showAndWait();

							success = false;
							event.setDropCompleted(success);
							event.consume();
						}

						if (success) {

							if (game.checkIfSame(rIndex, cIndex, game.getPlayerTurn())) {

								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Orientation");
								alert.setHeaderText("Choose where the next symbol will go!");
								alert.setContentText("Choose your option");

								if (game.getPlayerTurn() == 0) {
									GridPane g = (GridPane) player1Box.getChildren().get(game.getDom() + 1);
									ImageView i = (ImageView) g.getChildren().get((game.getNumSymbol() + 1) % 2);
									ImageView k = new ImageView();
									k.setImage(i.getImage());

									alert.setGraphic(k);
								} else {
									GridPane g = (GridPane) player2Box.getChildren().get(game.getDom() + 1);
									ImageView i = (ImageView) g.getChildren().get((game.getNumSymbol() + 1) % 2);
									ImageView k = new ImageView();
									k.setImage(i.getImage());
									alert.setGraphic(k);
								}

								ButtonType buttonUp = new ButtonType("Up");
								ButtonType buttonDown = new ButtonType("Down");
								ButtonType buttonLeft = new ButtonType("Left");
								ButtonType buttonRight = new ButtonType("Right");
								ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

								alert.getButtonTypes().setAll(buttonUp, buttonDown, buttonLeft, buttonRight,
										buttonCancel);

								Optional<ButtonType> result = alert.showAndWait();

								if (result.get() == buttonUp) {
									if (game.selectChoice(rIndex, cIndex, game.getPlayerTurn(), UP)) {
										grid.getChildren().remove(getNodeFromPane(grid, cIndex, rIndex - 1));
										grid.add(createBlank(), cIndex, rIndex - 1);

										grid.getChildren().remove(node);
										grid.add(createBlank(), cIndex, rIndex);

										successfulEvent(success, event);
									} else {
										tryAgain(success, event);
									}

								} else if (result.get() == buttonDown) {
									if (game.selectChoice(rIndex, cIndex, game.getPlayerTurn(), DOWN)) {
										grid.getChildren().remove(getNodeFromPane(grid, cIndex, rIndex + 1));
										grid.add(createBlank(), cIndex, rIndex + 1);

										grid.getChildren().remove(node);
										grid.add(createBlank(), cIndex, rIndex);

										successfulEvent(success, event);
									} else {
										tryAgain(success, event);
									}

								} else if (result.get() == buttonLeft) {
									if (game.selectChoice(rIndex, cIndex, game.getPlayerTurn(), LEFT)) {
										grid.getChildren().remove(getNodeFromPane(grid, cIndex - 1, rIndex));
										grid.add(createBlank(), cIndex - 1, rIndex);

										grid.getChildren().remove(node);
										grid.add(createBlank(), cIndex, rIndex);

										successfulEvent(success, event);

									} else {
										tryAgain(success, event);
									}

								} else if (result.get() == buttonRight) {
									if (game.selectChoice(rIndex, cIndex, game.getPlayerTurn(), RIGHT)) {
										grid.getChildren().remove(getNodeFromPane(grid, cIndex + 1, rIndex));
										grid.add(createBlank(), cIndex + 1, rIndex);

										grid.getChildren().remove(node);
										grid.add(createBlank(), cIndex, rIndex);

										successfulEvent(success, event);
									} else {
										tryAgain(success, event);
									}

								} else if (result.get() == buttonCancel) {
									failEvent(success, event);
								}
							} else {
								tryAgain(success, event);
							}
						}
					}
				}
			});
		}
		for (Node playerHand : playerBox.getChildren()) {
			playerHand.setOnDragDone(new EventHandler<DragEvent>() {

				public void handle(DragEvent event) {
					if (event.getTransferMode() == TransferMode.MOVE) {
						game.removeFromDomList(game.getPlayerTurn());
						playerBox.getChildren().remove(game.getDom() + 1);

						game.switchThePlayer();

						if (game.checkForValidMoves(game.getPlayerTurn())) {
							if (game.getPlayerTurn() == 1) {
								player2Box.setDisable(false);
								Label l = (Label) player2Box.getChildren().get(0);
								currentPlayer.setText(l.getText() + " it's your turn!");
								player1Box.setDisable(true);
							} else {
								player1Box.setDisable(false);
								Label l = (Label) player1Box.getChildren().get(0);
								currentPlayer.setText(l.getText() + " it's your turn!");
								player2Box.setDisable(true);
							}
						} else {
							game.switchThePlayer();
							if (game.getPlayerTurn() == 0) {
								declareWinner(player1Box, primaryStage);
							} else {
								declareWinner(player2Box, primaryStage);
							}
						}
					}
					event.consume();
				}
			});
		}
	}

	// handles when drag is successful
	private void successfulEvent(boolean success, DragEvent event) {
		success = true;
		event.setDropCompleted(success);
		event.consume();
	}

	
	// declares the appropriate winner and closes the game
	private void declareWinner(VBox playerBox, Stage primaryStage) {
		Image i = new Image("file:src/images/winner.png", 100,100,true,true);
		ImageView img = new ImageView();
		img.setImage(i);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		Label l = (Label) playerBox.getChildren().get(0);
		alert.setTitle("WINNER!");
		alert.setHeaderText(null);
		alert.setContentText(l.getText() + " wins!");
		alert.setGraphic(img);
		
		Optional<ButtonType> result = alert.showAndWait();
		// can leave this out to check the remaining pieces
		if (result.get() == ButtonType.OK) { 
			primaryStage.close();
		}
	}

	// if incorrect piece tells user to try again
	private void tryAgain(boolean success, DragEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Try again");
		alert.setHeaderText(null);
		alert.setContentText("Wrong choice! Please try again!");
		alert.showAndWait();

		failEvent(success, event);
	}

	// if incorrect piece doesn't go through with the drag event
	private void failEvent(boolean success, DragEvent event) {
		success = false;
		event.setDropCompleted(success);
		event.consume();
	}

	// creates a blank image
	private ImageView createBlank() {
		Image i = new Image("file:src/images/blank.png", 70, 70, true, true);
		ImageView img = new ImageView();
		img.setImage(i);

		return img;
	}

	// creates the players hand
	private void createPlayerHand(Game game, VBox playerBox, int player) {
		for (Symbol[] s : game.getPlayer(player).playerHand()) {
			GridPane symbolPane = new GridPane();
			for (int i = 0; i < s.length; i++) {
				if (s[i] == Symbol.CIRCLE) {
					Image image = new Image("file:src/images/circle.png", 50, 50, true, true);
					ImageView imageV = new ImageView();
					imageV.setImage(image);
					symbolPane.add(imageV, i, 0);
				}

				if (s[i] == Symbol.DIAMOND) {
					Image image = new Image("file:src/images/diamond.png", 50, 50, true, true);
					ImageView imageV = new ImageView();
					imageV.setImage(image);
					symbolPane.add(imageV, i, 0);
				}

				if (s[i] == Symbol.PLUS) {
					Image image = new Image("file:src/images/plus.png", 50, 50, true, true);
					ImageView imageV = new ImageView();
					imageV.setImage(image);
					imageV.setPreserveRatio(true);
					symbolPane.add(imageV, i, 0);
				}

				if (s[i] == Symbol.SQUARE) {
					Image image = new Image("file:src/images/square.png", 50, 50, true, true);
					ImageView imageV = new ImageView();
					imageV.setImage(image);
					imageV.setPreserveRatio(true);
					symbolPane.add(imageV, i, 0);
				}

				if (s[i] == Symbol.XMARK) {
					Image image = new Image("file:src/images/cross.png", 50, 50, true, true);
					ImageView imageV = new ImageView();
					imageV.setImage(image);
					imageV.setPreserveRatio(true);
					symbolPane.add(imageV, i, 0);
				}

				if (s[i] == Symbol.CLOVER) {
					Image image = new Image("file:src/images/clover.png", 50, 50, true, true);
					ImageView imageV = new ImageView();
					imageV.setImage(image);
					imageV.setPreserveRatio(true);
					symbolPane.add(imageV, i, 0);
				}

				if (s[i] == Symbol.DOT) {
					Image image = new Image("file:src/images/dot.png", 50, 50, true, true);
					ImageView imageV = new ImageView();
					imageV.setImage(image);
					imageV.setPreserveRatio(true);
					symbolPane.add(imageV, i, 0);
				}

			}
			symbolPane.setStyle("-fx-grid-lines-visible: true;");
			playerBox.getChildren().add(symbolPane);
		}
	}

	// creates the board
	private void createBoard(Board board, GridPane grid) {
		for (int i = 0; i < board.getRow(); i++) {
			for (int j = 0; j < board.getCol(); j++) {
				if (board.getFace(i, j) == null) {
					Image image = new Image("file:src/images/blank.png", 70, 70, true, true);
					ImageView iv = new ImageView();
					iv.setImage(image);
					grid.add(iv, j, i);

				}

				if (board.isValidMove(board.getFace(i, j), Symbol.CIRCLE)) {
					Image image = new Image("file:src/images/circle.png", 70, 70, true, true);
					ImageView iv = new ImageView();
					iv.setImage(image);
					grid.add(iv, j, i);
				}

				if (board.isValidMove(board.getFace(i, j), Symbol.DIAMOND)) {
					Image image = new Image("file:src/images/diamond.png", 70, 70, true, true);
					ImageView iv = new ImageView();
					iv.setImage(image);
					grid.add(iv, j, i);
				}

				if (board.isValidMove(board.getFace(i, j), Symbol.PLUS)) {
					Image image = new Image("file:src/images/plus.png", 70, 70, true, true);
					ImageView iv = new ImageView();
					iv.setImage(image);
					grid.add(iv, j, i);
				}

				if (board.isValidMove(board.getFace(i, j), Symbol.SQUARE)) {
					Image image = new Image("file:src/images/square.png", 70, 70, true, true);
					ImageView iv = new ImageView();
					iv.setImage(image);
					grid.add(iv, j, i);
				}

				if (board.isValidMove(board.getFace(i, j), Symbol.XMARK)) {
					Image image = new Image("file:src/images/cross.png", 70, 70, true, true);
					ImageView iv = new ImageView();
					iv.setImage(image);
					grid.add(iv, j, i);
				}

				if (board.isValidMove(board.getFace(i, j), Symbol.CLOVER)) {
					Image image = new Image("file:src/images/clover.png", 70, 70, true, true);
					ImageView iv = new ImageView();
					iv.setImage(image);
					grid.add(iv, j, i);
				}

				if (board.isValidMove(board.getFace(i, j), Symbol.DOT)) {
					Image image = new Image("file:src/images/dot.png", 70, 70, true, true);
					ImageView iv = new ImageView();
					iv.setImage(image);
					grid.add(iv, j, i);
				}

			}
		}
		grid.setStyle("-fx-grid-lines-visible: true;");
	}

	public static void main(String[] args) {
		launch(args);
	}

}
