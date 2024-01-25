import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Font;



public class BaccaratGame extends Application {
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;
	double currentBet = 0; // amount bet
	double totalWinnings = 0;
	int temp;
	private TextField playerAmount;
	private TextField bankAmount;
	private TextField betAmount;
	private TextField winAmount;
	private Label displayState;
	private HBox pGrid;
	private HBox bGrid;


	public double evaluateWinnings() {
		theDealer.generateDeck();
		theDealer.shuffleDeck();

		playerHand = theDealer.dealHand();
		bankerHand = theDealer.dealHand();

		GiveCard(playerHand,pGrid);
		GiveCard(bankerHand,bGrid);
		int player = gameLogic.handTotal(playerHand);
		playerAmount.setText(String.valueOf(player));
		int banker = gameLogic.handTotal(bankerHand);
		bankAmount.setText(String.valueOf(banker));

		Duration pauseDuration = Duration.seconds(3);
		Duration outcomeDuration = Duration.seconds(5);
		PauseTransition betweenExtraCards = new PauseTransition(pauseDuration);
		betweenExtraCards.play();
		ArrayList<Card> tempHand = new ArrayList<>();

		boolean naturalWin = player == 8 || player == 9 || banker == 8 || banker == 9;
		if (!naturalWin) {
			boolean playerDraw = gameLogic.evaluatePlayerDraw(playerHand);
			boolean playerDidDraw = false;
			Card playerCard3 = null;
			Card bankerCard3;
			if (playerDraw) { // deal third to player
				playerCard3 = theDealer.drawOne();
				playerHand.add(playerCard3); //add card to the hand
				tempHand.add(playerCard3);
				GiveCard(tempHand, pGrid);
				tempHand.clear();
				playerAmount.setText(String.valueOf(gameLogic.handTotal(playerHand)));
				playerDidDraw = true;
			}
			if (playerDidDraw) {
				if (gameLogic.evaluateBankerDraw(bankerHand, playerCard3)) { // deal third to banker
					bankerCard3 = theDealer.drawOne();
					bankerHand.add(bankerCard3); // add card to the hand
					tempHand.add(bankerCard3);
					GiveCard(tempHand, bGrid);
					tempHand.clear();
					bankAmount.setText(String.valueOf(gameLogic.handTotal(bankerHand)));
				}
			}
			else {
				if (banker <= 5) {
					bankerCard3 = theDealer.drawOne();
					bankerHand.add(bankerCard3); // add card to the hand
					tempHand.add(bankerCard3);
					GiveCard(tempHand, bGrid);
					tempHand.clear();
					bankAmount.setText(String.valueOf(gameLogic.handTotal(bankerHand)));
				}
			}
		}

		String winner = gameLogic.whoWon(playerHand,bankerHand);
		totalWinnings -= currentBet;
		if("Player".equals(winner)){
			if(temp == 1){
				totalWinnings += currentBet;
				totalWinnings += (currentBet *0.95);
				PauseTransition pauseForPlayer = new PauseTransition(outcomeDuration);
				pauseForPlayer.setOnFinished(event -> {
					winAmount.setText(String.valueOf(totalWinnings));
					displayState.setText("Player Won, you bet on player");
				});
				pauseForPlayer.play();
			}
			else if(temp == 2){
				PauseTransition pauseForPlayer = new PauseTransition(outcomeDuration);
				pauseForPlayer.setOnFinished(event -> {
					winAmount.setText(String.valueOf(totalWinnings));
					displayState.setText("Player Won, you bet on bank");
				});
				pauseForPlayer.play();
			}
			else if(temp == 3){
				PauseTransition pauseForPlayer = new PauseTransition(outcomeDuration);
				pauseForPlayer.setOnFinished(event -> {
					winAmount.setText(String.valueOf(totalWinnings));
					displayState.setText("Player won, you bet a tie");
				});
				pauseForPlayer.play();
			}
		}
		else if("Banker".equals(winner)){
			if(temp == 2){
				totalWinnings += currentBet;
				totalWinnings += (currentBet *0.95);
				PauseTransition pauseForPlayer = new PauseTransition(outcomeDuration);
				pauseForPlayer.setOnFinished(event -> {
					winAmount.setText(String.valueOf(totalWinnings));
					displayState.setText("Banker Won, you bet on bank");
				});
				pauseForPlayer.play();
			}
			else if(temp == 1){
				PauseTransition pauseForPlayer = new PauseTransition(outcomeDuration);
				pauseForPlayer.setOnFinished(event -> {
					winAmount.setText(String.valueOf(totalWinnings));
					displayState.setText("Banker Won, you bet on player");
				});
				pauseForPlayer.play();
			}
			else if(temp == 3){
				PauseTransition pauseForPlayer = new PauseTransition(outcomeDuration);
				pauseForPlayer.setOnFinished(event -> {
					winAmount.setText(String.valueOf(totalWinnings));
					displayState.setText("Banker won, you bet on a tie");
				});
				pauseForPlayer.play();
			}
		}
		else if("Draw".equals(winner)){
			if(temp == 3){
				totalWinnings += currentBet;
				totalWinnings += ((currentBet * 8) * 0.95);
				PauseTransition pauseForPlayer = new PauseTransition(outcomeDuration);
				pauseForPlayer.setOnFinished(event -> {
					winAmount.setText(String.valueOf(totalWinnings));
					displayState.setText("It was a tie, you bet on a tie");
				});
				pauseForPlayer.play();
			}
			else if(temp == 2){
				PauseTransition pauseForPlayer = new PauseTransition(outcomeDuration);
				pauseForPlayer.setOnFinished(event -> {
					winAmount.setText(String.valueOf(totalWinnings));
					displayState.setText("It was a tie, you bet on bank");
				});
				pauseForPlayer.play();
			}
			else if(temp == 1){
				PauseTransition pauseForPlayer = new PauseTransition(outcomeDuration);
				pauseForPlayer.setOnFinished(event -> {
					winAmount.setText(String.valueOf(totalWinnings));
					displayState.setText("It was a tie, you bet on player");
				});
				pauseForPlayer.play();
			}
		}

		return totalWinnings;
	}





	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}




	@Override
	public void start(Stage primaryStage) throws Exception {
		theDealer = new BaccaratDealer();
		gameLogic = new BaccaratGameLogic();


		primaryStage.setTitle("Baccarat Game");
		BorderPane root = new BorderPane();
		setBackground(root);
		AnchorPane anchorpane = new AnchorPane();


		//Welcome Label
		Label welcome = new Label("Welcome to Baccarat");
		welcome.setPrefSize(400,60);
		welcome.setFont(new Font(20));
		anchorpane.getChildren().add(welcome);
		AnchorPane.setTopAnchor(welcome,0.0);
		AnchorPane.setLeftAnchor(welcome,175.0);
		AnchorPane.setRightAnchor(welcome,175.0);
		welcome.setAlignment(Pos.CENTER);


		//Player Total
		Label playerTotal = new Label("Player Total");
		playerAmount = new TextField("0");
		playerAmount.setPrefSize(40,30);
		playerAmount.setEditable(false);
		VBox vboxP = new VBox(5);
		vboxP.getChildren().addAll(playerTotal,playerAmount);
		vboxP.setPrefSize(100,80);
		anchorpane.getChildren().add(vboxP);
		AnchorPane.setTopAnchor(vboxP,350.0);
		AnchorPane.setLeftAnchor(vboxP,75.0);


		//Bank Total
		Label bankTotal = new Label("Bank Total");
		bankAmount = new TextField("0");
		bankAmount.setPrefSize(40,30);
		bankAmount.setEditable(false);
		VBox vboxB = new VBox(5);
		vboxB.getChildren().addAll(bankTotal,bankAmount);
		vboxB.setPrefSize(100,80);
		anchorpane.getChildren().add(vboxB);
		AnchorPane.setTopAnchor(vboxB,350.0);
		AnchorPane.setRightAnchor(vboxB,75.0);


		//BetGrid
		Button Tie = new Button("Bet on Tie");
		Button betBank = new Button("Bet on Banker");
		Button betPlayer = new Button("Bet on Player");
		Tie.setDisable(true);
		betBank.setDisable(true);
		betPlayer.setDisable(true);
		Tie.setPrefSize(185,80);
		betBank.setPrefSize(185,80);
		betPlayer.setPrefSize(185,80);
		VBox buttonGrid = new VBox(10);
		buttonGrid.getChildren().addAll(Tie,betBank,betPlayer);
		buttonGrid.setAlignment(Pos. CENTER);
		anchorpane.getChildren().add(buttonGrid);
		AnchorPane.setTopAnchor(buttonGrid,60.0);
		AnchorPane.setLeftAnchor(buttonGrid,175.0);
		AnchorPane.setRightAnchor(buttonGrid,175.0);
		Tie.setMinSize(185, 80);
		Tie.setMaxSize(185, 80);
		betBank.setMinSize(185, 80);
		betBank.setMaxSize(185, 80);
		betPlayer.setMinSize(185, 80);
		betPlayer.setMaxSize(185, 80);


		//State of Game
		displayState = new Label("Press play then enter bet Amount");
		displayState.setPrefSize(240,90);
		displayState.setAlignment(Pos. CENTER);
		displayState.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		VBox display = new VBox(10);
		display.getChildren().add(displayState);
		display.setAlignment(Pos. CENTER);
		anchorpane.getChildren().add(display);
		AnchorPane.setTopAnchor(display,340.0);
		AnchorPane.setLeftAnchor(display,175.0);
		AnchorPane.setRightAnchor(display,175.0);


		//Deck Image
		Image CardDeckI = new Image("file:CardDeck.jpg");
		ImageView CardDeckV = new ImageView(CardDeckI);
		CardDeckV.setFitWidth(100); // Set the width to 180 pixels
		CardDeckV.setFitHeight(300); // Set the height to 380 pixels
		CardDeckV.setPreserveRatio(true);
		VBox deck = new VBox(10);
		deck.getChildren().add(CardDeckV);
		deck.setAlignment(Pos. CENTER);
		deck.setPrefSize(200,400);
		anchorpane.getChildren().add(deck);
		AnchorPane.setTopAnchor(deck,320.0);
		AnchorPane.setLeftAnchor(deck,175.0);
		AnchorPane.setRightAnchor(deck,175.0);


		//Bet Amount
		Label BetPlace = new Label("Bet Amount");
		betAmount = new TextField("0");
		betAmount.setPrefSize(100,40);
		betAmount.setEditable(false);
		VBox toBet = new VBox(5);
		toBet.getChildren().addAll(BetPlace,betAmount);
		anchorpane.getChildren().add(toBet);
		AnchorPane.setBottomAnchor(toBet,70.0);
		AnchorPane.setLeftAnchor(toBet,90.0);


		//Winnings Box
		Label winPlace = new Label("Winnings");
		winAmount = new TextField("0");
		winAmount.setEditable(false);
		winAmount.setPrefSize(100,40);
		VBox winnings = new VBox(5);
		winnings.getChildren().addAll(winPlace,winAmount);
		anchorpane.getChildren().add(winnings);
		AnchorPane.setBottomAnchor(winnings,70.0);
		AnchorPane.setRightAnchor(winnings,90.0);


		//Deal Button
		Button DEAL = new Button("PLAY");
		DEAL.setPrefSize(100,40);
		DEAL.setAlignment(Pos. CENTER);
		anchorpane.getChildren().add(DEAL);
		AnchorPane.setBottomAnchor(DEAL,70.0);
		AnchorPane.setLeftAnchor(DEAL,225.0);
		AnchorPane.setRightAnchor(DEAL,225.0);


		//Player HBox
		pGrid = new HBox(15);
		anchorpane.getChildren().add(pGrid);
		AnchorPane.setLeftAnchor(pGrid,45.0);
		AnchorPane.setTopAnchor(pGrid,455.0);


		//Bank HBox
		bGrid = new HBox(15);
		bGrid.setAlignment(Pos. CENTER);
		anchorpane.getChildren().add(bGrid);
		AnchorPane.setRightAnchor(bGrid,45.0);
		AnchorPane.setTopAnchor(bGrid,455.0);


		//Simple Player Hand
		Label plHand = new Label("Player Hand");
		plHand.setAlignment(Pos. CENTER);
		anchorpane.getChildren().add(plHand);
		AnchorPane.setTopAnchor(plHand,422.0);
		AnchorPane.setLeftAnchor(plHand,70.0);


		//Options menu
		final MenuItem menu1 = new MenuItem("Exit");
		final MenuItem menu2 = new MenuItem("Fresh Start");
		Menu options = new Menu("Options");
		options.getItems().addAll(menu1,menu2);
		MenuBar Menu = new MenuBar();
		Menu.getMenus().add(options);
		anchorpane.getChildren().add(Menu);
		AnchorPane.setLeftAnchor(Menu,30.0);
		AnchorPane.setTopAnchor(Menu,30.0);
		menu1.setOnAction(e ->{
			Platform.exit();
		});
		menu2.setOnAction(e->{
			totalWinnings = 0;
			displayState.setText("Press play then enter bet Amount");
			DEAL.setDisable(false);
			winAmount.setText("0");
			betAmount.setText("0");
			Tie.setDisable(true);
			betBank.setDisable(true);
			betPlayer.setDisable(true);
			bankAmount.setText("0");
			playerAmount.setText("0");
			bGrid.getChildren().clear();
			pGrid.getChildren().clear();
		});


		DEAL.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
			@Override
			public void handle(javafx.event.ActionEvent event) {
				pGrid.getChildren().clear();
				bGrid.getChildren().clear();
				betAmount.setEditable(true);
				displayState.setText("Enter amount to bet");
				DEAL.setDisable(true);
			}
		});


		Tie.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>(){
			@Override
			public void handle(javafx.event.ActionEvent event) {
				Tie.setDisable(true);
				betAmount.setEditable(false);
				betBank.setDisable(true);
				betPlayer.setDisable(true);
				temp = 3;
				displayState.setText("Bet on Tie");
				evaluateWinnings();
				Duration playDisableDuration = Duration.seconds(6);
				PauseTransition pauseForPlayer = new PauseTransition(playDisableDuration);
				pauseForPlayer.setOnFinished(e -> {
					DEAL.setDisable(false);
				});
				pauseForPlayer.play();
			}
		});

		betBank.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>(){
			@Override
			public void handle(javafx.event.ActionEvent event) {
				Tie.setDisable(true);
				betAmount.setEditable(false);
				betBank.setDisable(true);
				betPlayer.setDisable(true);
				temp = 2;
				displayState.setText("Bet on Banker");
				evaluateWinnings();

				Duration playDisableDuration = Duration.seconds(6);
				PauseTransition pauseForPlayer = new PauseTransition(playDisableDuration);
				pauseForPlayer.setOnFinished(e -> {
					DEAL.setDisable(false);
				});
				pauseForPlayer.play();
			}
		});

		betPlayer.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>(){
			@Override
			public void handle(javafx.event.ActionEvent event){
				Tie.setDisable(true);
				betAmount.setEditable(false);
				betBank.setDisable(true);
				betPlayer.setDisable(true);
				temp = 1;
				displayState.setText("Bet on Player");
				evaluateWinnings();
				Duration playDisableDuration = Duration.seconds(6);
				PauseTransition pauseForPlayer = new PauseTransition(playDisableDuration);
				pauseForPlayer.setOnFinished(e -> {
					DEAL.setDisable(false);
				});
				pauseForPlayer.play();
			}
		});


		betAmount.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				boolean isNotEmpty = !newValue.trim().isEmpty();
				boolean isNumeric = newValue.matches("\\d+");
				boolean isPositive = false;
				if (isNumeric) {
					if (Integer.parseInt(newValue) > 0) {
						Tie.setDisable(false);
						betBank.setDisable(false);
						betPlayer.setDisable(false);
						currentBet = Integer.parseInt(newValue);
						displayState.setText("Please select button to bet on");
					} else {
						Tie.setDisable(true);
						betBank.setDisable(true);
						betPlayer.setDisable(true);
						displayState.setText("Please enter positive number");
					}
				} else {
					Tie.setDisable(true);
					betBank.setDisable(true);
					betPlayer.setDisable(true);
					displayState.setText("Please enter numeric value");
				}
			}
		});

		root.setCenter(anchorpane);
		Scene scene = new Scene(root, 700, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}





	private void setBackground(BorderPane pane) {
		pane.setStyle("-fx-background-color: green;");
	}




	private VBox createCardBox(String suite, String number) {
		String suiteSymbol = "";
		VBox cardBox = new VBox(5);
		Label suiteLabel = new Label();
		if("Clubs".equals(suite)){
			cardBox.setStyle("-fx-border-color: black; -fx-border-radius: 3.0;-fx-background-color:lightgray");
			suiteSymbol = "\u2663";
			suiteLabel.setTextFill(Color.BLACK);
		}
		else if("Spades".equals(suite)){
			cardBox.setStyle("-fx-border-color: black; -fx-border-radius: 3.0;-fx-background-color:lightgray");
			suiteSymbol = "\u2660";
			suiteLabel.setTextFill(Color.BLACK);
		}
		else if("Diamonds".equals(suite)){
			cardBox.setStyle("-fx-border-color: red; -fx-border-radius: 3.0;-fx-background-color:lightgray");
			suiteSymbol = "\u2666";
			suiteLabel.setTextFill(Color.RED);
		}
		else if("Hearts".equals(suite)){
			cardBox.setStyle("-fx-border-color: red; -fx-border-radius: 3.0;-fx-background-color:lightgray");
			suiteSymbol = "\u2665";
			suiteLabel.setTextFill(Color.RED);
		}
		Label numberLabel = new Label(number);
		suiteLabel.setText(suiteSymbol);

		cardBox.setPrefSize(70,133);
		cardBox.getChildren().addAll(suiteLabel, numberLabel);
		cardBox.setAlignment(Pos. CENTER);
		return cardBox;
	}




	private void GiveCard(ArrayList<Card> Hand,HBox Grid){
		Duration pauseDuration = Duration.seconds(2);
		for (Card card : Hand) {
			PauseTransition pauseForPlayer = new PauseTransition(pauseDuration);
			pauseForPlayer.setOnFinished(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
				@Override
				public void handle(javafx.event.ActionEvent event) {
					Grid.getChildren().add(createCardBox(card.suite, card.name));
				}
			});
			pauseForPlayer.play();
			pauseDuration = pauseDuration.add(Duration.seconds(2));
		}
	}





	private void pause(int seconds) {
		Duration pauseDuration = Duration.seconds(seconds);
		PauseTransition pauseTransition = new PauseTransition(pauseDuration);
		pauseTransition.play();
	}

}
