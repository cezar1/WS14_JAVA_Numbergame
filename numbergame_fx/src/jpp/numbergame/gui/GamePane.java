package jpp.numbergame.gui;

import java.util.List;

import javafx.animation.FadeTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import jpp.numbergame.Move;
import jpp.numbergame.Tile;

public class GamePane extends StackPane {
	//constants
	private static final double myMessageFontSize=20d;
	private static final double myMessageShadowEffectOffset=4.0f;
	//members
	private TileGridPane canvas;
	private Text message;
	private FadeTransition ft;
	public GamePane(int width, int height)
	{
		canvas=new TileGridPane(width,height);
		message=new Text();
		//message.setOpacity(0d);
		message.setFill(Color.DARKRED);
		message.setFont(new Font(myMessageFontSize));
		DropShadow ds = new DropShadow();
		ds.setOffsetY(myMessageShadowEffectOffset);
		ds.setColor(Color.WHITE);
		this.message.setEffect(ds);
		ChangeListener myBoundsListener=new ChangeListener<Bounds>()
		{

			@Override
			public void changed(ObservableValue<? extends Bounds> arg0,
					Bounds oldValBounds, Bounds newValBounds) {
				// TODO Auto-generated method stub
				double scaleHeight,scaleWidth;
				scaleHeight=newValBounds.getHeight()/message.boundsInLocalProperty().getValue().getHeight();
				scaleWidth=newValBounds.getWidth()/message.boundsInLocalProperty().getValue().getWidth();
				message.scaleXProperty().set(scaleWidth);
				message.scaleYProperty().set(scaleHeight);
				scaleHeight=newValBounds.getHeight()/canvas.boundsInLocalProperty().getValue().getHeight();
				scaleWidth=newValBounds.getWidth()/canvas.boundsInLocalProperty().getValue().getWidth();
				canvas.scaleXProperty().set(scaleWidth);
				canvas.scaleYProperty().set(scaleHeight);
			}
		};
		//this.boundsInLocalProperty().addListener(myBoundsListener);
		//canvas.boundsInLocalProperty().addListener(myBoundsListener);
		//message.boundsInLocalProperty().addListener(myBoundsListener);
		ft=new FadeTransition(Duration.millis(1000), message);
		ft.setFromValue(1d);
		ft.setToValue(0d);
		this.getChildren().add(canvas);
		this.getChildren().add(message);
	}
	public void addTile(Tile t)
	{
		this.canvas.addRectangle(t);
	}
	public void moveTiles(List<Move> moves)
	{
		this.canvas.moveRectangles(moves);
	}
	public void reset()
	{
		this.canvas.reset();
		//fadeOutMessage();
	}
	public void showFadingMessage(String text)
	{
		showMessage(text);
		fadeOutMessage();
	}
	public void showMessage(String text)
	{
		ft.stop();
		this.message.setText(text);
		this.message.setOpacity(1d);
	}
	public void fadeOutMessage()
	{
		ft.play();
	}

}
