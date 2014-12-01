package jpp.numbergame.gui;



import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import jpp.numbergame.Direction;
import jpp.numbergame.Move;
import jpp.numbergame.NumberGame;

public class NumberGui extends Application {
	public static void main(String[] args)
	{
		launch(args);
		
	}
	private NumberGame game;
	private GamePane gamePane;
	private Label pointsValue=new Label();
	
	private boolean gameIsLost = false;
	public void initGame()
	{
		this.game=new NumberGame(4, 4);
		//this.gamePane.addTile(this.game.addRandomTile());
		//this.gamePane.addTile(this.game.addRandomTile());
		this.gamePane.addTile(this.game.addTile(1, 1, 2));
		this.pointsValue.setText("0");
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("2048");
		this.gamePane=new GamePane(4, 4);
		BorderPane myRootPane=new BorderPane();
		HBox myTopBar=new HBox();
		myTopBar.setStyle("-fx-background-color: palegoldenrod; -fx-font-size:1.5em");
		Label myLabelPoints=new Label();
		myLabelPoints.setText("Points");
		//Label pointsValue=new Label();
		pointsValue.setText("0");
		pointsValue.setStyle("-fx-font-weight: bold; -fx-textfill:darkred;");
		myTopBar.getChildren().add(myLabelPoints);myTopBar.getChildren().add(pointsValue);
		myRootPane.setTop(myTopBar);
		
		//EXTRA DEBUG
//		Rectangle myTestRectangle=new Rectangle();
//		myTestRectangle.setX(100);
//		myTestRectangle.setY(100);
//		myTestRectangle.setWidth(10);
//		myTestRectangle.setHeight(10);
//		myRootPane.getChildren().add(myTestRectangle);
		
		myRootPane.setCenter(gamePane);
		primaryStage.setScene(new Scene(myRootPane, 300, 300));
		primaryStage.show();
		primaryStage.setMinHeight(300);
		primaryStage.setMinWidth(300);
		initGame();
		
		primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent keyEvent) {
	            if (keyEvent.getCode() == KeyCode.UP) {
	            	handleDirectionKey(Direction.UP);
	            }
	            else if (keyEvent.getCode() == KeyCode.DOWN) {
	            	handleDirectionKey(Direction.DOWN);
	            }
	            else if (keyEvent.getCode() == KeyCode.LEFT) {
	            	handleDirectionKey(Direction.LEFT);
	            }
	            else if (keyEvent.getCode() == KeyCode.RIGHT) {
	            	handleDirectionKey(Direction.RIGHT);
	            }
	            else if (keyEvent.getCode() == KeyCode.F5) {
	            	restartGame();
	            }
	            keyEvent.consume();
	        }
	    });
	}
	private void handleDirectionKey(Direction dir)
	{
		if(this.gameIsLost) return;
		List<Move> result=new LinkedList<Move>();
		result=this.game.move(dir);
		if (result.size()>0)
		{
			this.gamePane.moveTiles(result);
			this.pointsValue.setText(String.valueOf(this.game.getPoints()));
			this.gamePane.addTile(this.game.addRandomTile());
		}
		else this.gamePane.showFadingMessage("Move not possible");
		if (this.game.canMove()==false) {
			this.gameIsLost=true;
			this.gamePane.showMessage("Sorry, no more moves possible\nF5 to restart.");
		}
	}
	private void restartGame()
	{
		initGame();
		this.gamePane.reset();
		this.gameIsLost=false;
		//this.gamePane.showFadingMessage("Resseting");
		
	}

}
