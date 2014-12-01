package jpp.numbergame.gui;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

public class NumberRectangle extends StackPane {
	//Constants
	public static final int myArcWidth=10;
	public static final float myDropShadowOffset=4.0f;
	public static final double myFillTransitionDuration=150;
	public static final int myBaseRectSize=25;
	public static final double myMoveToDuration=150;
	//Members
	private Rectangle myEnclosedRect=new Rectangle();
	private IntegerProperty valueProperty=new SimpleIntegerProperty();
	private Text myText=new Text();
	private DoubleProperty xProperty=new SimpleDoubleProperty();
	private DoubleProperty yProperty=new SimpleDoubleProperty();
	private DoubleProperty rectHeightProperty=new SimpleDoubleProperty();
	private DoubleProperty rectWidthProperty=new SimpleDoubleProperty();
	private Timeline myTimeLine=new Timeline();
	private int Value;
	private double X;
	private double Y;
	//Methods
	public NumberRectangle(int x, int y, int initialValue) {
		super();
		System.out.println("Creating new numberRectangle @ ("+x+","+y+")");
		//this.xProperty.set(x);
		//this.yProperty.set(y);
		//this.xProperty.setValue(this.rectHeightProperty.multiply(x).add(2).getValue());
		//this.yProperty.setValue(this.rectHeightProperty.multiply(y).add(2).getValue());
		this.myEnclosedRect.xProperty().bind(this.layoutXProperty());
		this.myEnclosedRect.yProperty().bind(this.layoutYProperty());
		this.myText.xProperty().bind(this.layoutXProperty().add(this.rectWidthProperty).divide(2));
		this.myText.yProperty().bind(this.layoutYProperty());
		//this.myEnclosedRect.setY(100);
		//this.
		//this.setPadding(new Insets(myPadding,myPadding,myPadding,myPadding));
		this.myEnclosedRect.setArcWidth(myArcWidth);
		//this.myEnclosedRect.setHeight(this.rectHeightProperty.doubleValue()+10);//EXTRA DEBUG
		//this.myEnclosedRect.setWidth(this.rectWidthProperty.doubleValue()+10);//EXTRA DEBUG
		this.myEnclosedRect.heightProperty().bind(this.rectHeightProperty);
		this.myEnclosedRect.widthProperty().bind(this.rectWidthProperty);
		this.myText.setBoundsType(TextBoundsType.VISUAL);
		this.myText.setFill(Color.WHITE);
		this.myText.setFont(Font.font(null, FontWeight.BOLD, 20));
		DropShadow ds = new DropShadow();
		ds.setOffsetY(myDropShadowOffset);
		ds.setColor(Color.BLACK);
		this.myText.setEffect(ds);
		Value=initialValue;
		myText.setText("2");//EXTRA DEBUG
        this.layoutXProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {           	
                System.out.println("NumberRectangle layoutXProperty changed from "+t.doubleValue()+" to "+t1.doubleValue()+".");
            }


        });
        this.layoutYProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {           	
                System.out.println("NumberRectangle layoutYProperty changed from "+t.doubleValue()+" to "+t1.doubleValue()+".");
            }


        });
//		valueProperty.addListener(new ChangeListener<Number>()
//				{
//					@Override
//					public void changed(ObservableValue<? extends Number> arg0,
//							Number oldVal, Number newVal) {
//						myText.setText(newVal.toString());
//						System.out.println("Listener 1!");
//					}
//				}
//		);
//		valueProperty.addListener(new ChangeListener<Number>()
//				{
//					@Override
//					public void changed(ObservableValue<? extends Number> arg0,
//							Number oldVal, Number newVal) {
//						FillTransition myFillTransition=new FillTransition();
//						myFillTransition.setDuration(Duration.millis(myFillTransitionDuration));
//						double myDivision=Math.log(newVal.doubleValue())/Math.log(2048);
//						if (myDivision>1) myDivision=1;
//						Color myInterpolationResult=new Color(0,0,1,1.0).interpolate(Color.PALEGOLDENROD, myDivision);
//						if (myDivision>1) myFillTransition.setToValue(myInterpolationResult);
//						System.out.println("Listener on Number for IntegerProperty!");
//					}
//				}
//		);
		myEnclosedRect.boundsInLocalProperty().addListener(new ChangeListener<Bounds>()
				{
					@Override
					public void changed(ObservableValue<? extends Bounds> arg0,
							Bounds oldVal, Bounds newVal) {
						Double myNewScale=newVal.getWidth()/myText.boundsInLocalProperty().getValue().getWidth();
						myText.setScaleX(myNewScale);
						myText.setScaleY(myNewScale);
						System.out.println("Listener on Bounds for BoundsInLocalProperty!");
					}
				}
		);
		myText.textProperty().addListener(new ChangeListener<String>()
				{
					@Override
					public void changed(ObservableValue<? extends String> arg0,
							String oldVal, String newVal) {
						double myNewScale=myEnclosedRect.boundsInLocalProperty().getValue().getWidth()/myText.boundsInLocalProperty().getValue().getWidth();
						myText.setScaleX(myNewScale);
						myText.setScaleY(myNewScale);
						//myEnclosedRect.setHeight(myBaseRectSize-2*myPadding);
						//myEnclosedRect.setWidth(myBaseRectSize-2*myPadding);
						System.out.println("Listener on Text for TextProperty!");
					}
				}
		);
	}
	public void moveTo(int x, int y)
	{
		KeyValue myKeyValue_X,myKeyValue_Y;
		myKeyValue_X=new KeyValue(myEnclosedRect.xProperty(), x);
		myKeyValue_Y=new KeyValue(myEnclosedRect.yProperty(), y);
		KeyFrame kf = new KeyFrame(Duration.millis(myMoveToDuration), myKeyValue_X,myKeyValue_Y);
		ObservableList myKeyFrames=myTimeLine.getKeyFrames();
		myKeyFrames.clear();
		myKeyFrames.add(kf);
		myTimeLine.playFromStart();
	}
	public IntegerProperty getValueProperty() {
		return valueProperty;
	}
	public void setValueProperty(IntegerProperty valueProperty) {
		this.valueProperty = valueProperty;
	}
	public DoubleProperty getxProperty() {
		return xProperty;
	}
	public void setxProperty(DoubleProperty xProperty) {
		this.xProperty = xProperty;
	}
	public DoubleProperty getyProperty() {
		return yProperty;
	}
	public void setyProperty(DoubleProperty yProperty) {
		this.yProperty = yProperty;
	}
	public DoubleProperty getRectHeightProperty() {
		return rectHeightProperty;
	}
	public void setRectHeightProperty(DoubleProperty rectHeightProperty) {
		this.rectHeightProperty = rectHeightProperty;
	}
	public DoubleProperty getRectWidthProperty() {
		return rectWidthProperty;
	}
	public void setRectWidthProperty(DoubleProperty rectWidthProperty) {
		this.rectWidthProperty = rectWidthProperty;
	}
	public int getValue() {
		return Value;
	}
	public void setValue(int value) {
		Value = value;
	}
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}
	public Rectangle getMyEnclosedRect() {
		return myEnclosedRect;
	}
	public Text getMyText() {
		return myText;
	}

}
