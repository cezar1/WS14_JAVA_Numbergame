package jpp.numbergame.gui;

import java.util.List;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import jpp.numbergame.Move;
import jpp.numbergame.Tile;

public class TileGridPane extends Pane {
	//Members
	private int myTableRows;
    private int myTableColumns;
    private NumberRectangle[][] myTiles;// = new int[TableSize][TableSize];
	public TileGridPane(int width,int height) {
		super();
		myTableRows=height;
		myTableColumns=width;
		this.myTiles= new NumberRectangle[myTableRows][myTableColumns];
		ObservableDoubleValue moo = this.widthProperty().divide(400).add(1);
		DoubleBinding myStrokeWidthBinding=new DoubleBinding() {
		     {super.bind(moo);}
			@Override
			protected double computeValue() {
				// TODO Auto-generated method stub
				return moo.doubleValue();
			}
		};
		//Create horizontal lines
		for (int i=0;i<myTableRows;i++)
		{
			Line myLine=new Line();
			myLine.setStroke(Color.GRAY);
			DoubleProperty myLineStrokeWidth=myLine.strokeWidthProperty();
			myLineStrokeWidth.bind(myStrokeWidthBinding);
			myLine.setStartY(0);
			DoubleProperty myEndYProperty=myLine.endYProperty();
			myEndYProperty.bind(this.heightProperty());
			ObservableDoubleValue myXPositionObservable = tileWidthBinding().multiply(i);
			DoubleBinding myXPositionDoubleBinding=new DoubleBinding() {
			     {super.bind(myXPositionObservable);}
				@Override
				protected double computeValue() {
					return myXPositionObservable.doubleValue();
				}
			};
			DoubleProperty myStartXProperty=myLine.startXProperty();
			DoubleProperty myEndXProperty=myLine.endXProperty();
			myStartXProperty.bind(myXPositionDoubleBinding);
			myEndXProperty.bind(myXPositionDoubleBinding);
			this.getChildren().add(myLine);
		}
		//Create vertical lines
		for (int i=0;i<myTableColumns;i++)
		{
			Line myLine=new Line();
			myLine.setStroke(Color.GRAY);
			DoubleProperty myLineStrokeWidth=myLine.strokeWidthProperty();
			myLineStrokeWidth.bind(myStrokeWidthBinding);
			myLine.setStartX(0);
			DoubleProperty myEndXProperty=myLine.endXProperty();
			myEndXProperty.bind(this.widthProperty());
			ObservableDoubleValue myYPositionObservable = tileHeightBinding().multiply(i);
			DoubleBinding myYPositionDoubleBinding=new DoubleBinding() {
			     {super.bind(myYPositionObservable);}
				@Override
				protected double computeValue() {
					return myYPositionObservable.doubleValue();
				}
			};
			DoubleProperty myStartYProperty=myLine.startYProperty();
			DoubleProperty myEndYProperty=myLine.endYProperty();
			myStartYProperty.bind(myYPositionDoubleBinding);
			myEndYProperty.bind(myYPositionDoubleBinding);
			this.getChildren().add(myLine);
		}
		
	}
	public DoubleBinding tileWidthBinding()
	{
		return this.widthProperty().divide(myTableColumns);
	}
	public DoubleBinding tileHeightBinding()
	{
		return this.heightProperty().divide(myTableRows);
	}
	public void addRectangle(Tile tile)
	{
		addRectangle(tile.getValue(), tile.getCoordinate().getX(), tile.getCoordinate().getY());
	}
	public void addRectangle(int value, int x, int y)
	{
		if (this.myTiles[x][y]!=null) throw new IllegalArgumentException();
		else
		{
			this.myTiles[x][y]=new NumberRectangle(x,y,value);
			ObservableDoubleValue myLayoutXObservable = tileWidthBinding().multiply(x);
			DoubleBinding myLayoutXDoubleBinding=new DoubleBinding() {
			     {super.bind(myLayoutXObservable);}
				@Override
				protected double computeValue() {
					return myLayoutXObservable.doubleValue();
				}
			};
			this.myTiles[x][y].layoutXProperty().bind(myLayoutXDoubleBinding);
			ObservableDoubleValue myLayoutYObservable = tileHeightBinding().multiply(y);
			DoubleBinding myLayoutYDoubleBinding=new DoubleBinding() {
			     {super.bind(myLayoutYObservable);}
				@Override
				protected double computeValue() {
					return myLayoutYObservable.doubleValue();
				}
			};
			this.myTiles[x][y].layoutYProperty().bind(myLayoutYDoubleBinding);
			this.getChildren().add(this.myTiles[x][y]);
		}
	}
	public void moveRectangles(List<Move> moves)
	{
		for (Move temp : moves) {
			moveRectangle(temp);
		}
	}
	public void moveRectangle(Move move)
	{
		this.myTiles[move.getFrom().getX()][move.getFrom().getY()].moveTo(move.getTo().getX(),move.getTo().getY());
	}
	public void reset()
	{
		for (int i=0;i<myTableRows;i++) for (int j=0;j<myTableColumns;j++) myTiles[i][j]=null;
	}
}
