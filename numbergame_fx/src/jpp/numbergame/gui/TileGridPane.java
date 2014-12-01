package jpp.numbergame.gui;

import java.util.List;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import jpp.numbergame.Move;
import jpp.numbergame.Tile;

public class TileGridPane extends Pane {
	public static final int myPadding=5;
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

        this.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {           	
                System.out.println("TileGridPane width changed from "+t.doubleValue()+" to "+t1.doubleValue()+".");
            }


        });
        this.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                System.out.println("TileGridPane height changed from "+t.doubleValue()+" to "+t1.doubleValue()+".");
            }


        });
		
		
		System.out.println("TileGridPane width:"+this.getWidth()+".");
		System.out.println("Created TileGridPane with tileWidth:"+this.tileHeightBinding().doubleValue() +".");
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
			ObservableDoubleValue myLayoutXObservable = tileWidthBinding().multiply(x).add(myPadding/2.0);
			DoubleBinding myLayoutXDoubleBinding=new DoubleBinding() {
			     {super.bind(myLayoutXObservable);}
				@Override
				protected double computeValue() {
					return myLayoutXObservable.doubleValue();
				}
			};
			this.myTiles[x][y].layoutXProperty().bind(myLayoutXDoubleBinding);
			ObservableDoubleValue myLayoutYObservable = tileHeightBinding().multiply(y).add(myPadding/2.0);
			DoubleBinding myLayoutYDoubleBinding=new DoubleBinding() {
			     {super.bind(myLayoutYObservable);}
				@Override
				protected double computeValue() {
					return myLayoutYObservable.doubleValue();
				}
			};
			this.myTiles[x][y].layoutYProperty().bind(myLayoutYDoubleBinding);
			this.myTiles[x][y].getRectHeightProperty().bind(tileHeightBinding().subtract(myPadding));
			this.myTiles[x][y].getRectWidthProperty().bind(tileWidthBinding().subtract(myPadding));
			this.getChildren().add(this.myTiles[x][y].getMyEnclosedRect());
			this.getChildren().add(this.myTiles[x][y].getMyText());
			
//			//EXTRA DEBUG
//			Rectangle myTestRectangle=new Rectangle();
//			myTestRectangle.setX(100);
//			myTestRectangle.setY(100);
//			myTestRectangle.setWidth(10);
//			myTestRectangle.setHeight(10);
//			this.getChildren().add(myTestRectangle);
			
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
		int x_Origin,y_Origin,x_Destination,y_Destination;
		x_Origin=move.getFrom().getX();
		y_Origin=move.getFrom().getY();
		x_Destination=move.getTo().getX();
		y_Destination=move.getTo().getY();
		this.myTiles[x_Origin][y_Origin].moveTo(x_Destination,y_Destination);
		Tile newTile=new Tile(move.getTo(), myTiles[x_Origin][y_Origin].getValue());
		this.addRectangle(newTile);
		//this.myTiles[x_Origin][y_Origin]=null;
		//this.getChildren().remove(index)
		//this.myTiles[x_Destination][y_Destination]=this.myTiles[x_Origin][y_Origin].
	}
	public void reset()
	{
		for (int i=0;i<myTableRows;i++) for (int j=0;j<myTableColumns;j++) myTiles[i][j]=null;
		//this.getChildren().clear();
	}
}
