package jpp.numbergame.gui;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
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

	private int X;
	private int Y;
	//Methods
	public NumberRectangle(int x, int y, int initialValue) {
		super();
		System.out.println("Creating new numberRectangle @ ("+x+","+y+")");

		setX(x);
		setY(y);

		this.myEnclosedRect.setArcWidth(myArcWidth);

		this.myEnclosedRect.heightProperty().bind(this.rectHeightProperty);
		this.myEnclosedRect.widthProperty().bind(this.rectWidthProperty);
		this.myText.setBoundsType(TextBoundsType.VISUAL);
		this.myText.setFill(Color.WHITE);
		this.myText.setFont(Font.font(null, FontWeight.BOLD, 20));
		DropShadow ds = new DropShadow();
		ds.setOffsetY(myDropShadowOffset);
		ds.setColor(Color.BLACK);
		this.myText.setEffect(ds);
		

        this.getxProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {     
        		myEnclosedRect.xProperty().set(t1.doubleValue());
         		myText.xProperty().set(t1.doubleValue()+(rectWidthProperty.doubleValue()/2));
                System.out.println("NumberRectangle layoutXProperty changed from "+t.doubleValue()+" to "+t1.doubleValue()+".");
            }
        });
        this.getyProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {     
        		myEnclosedRect.yProperty().set(t1.doubleValue());
         		myText.yProperty().set(t1.doubleValue()+(rectHeightProperty.doubleValue()/2));;
                System.out.println("NumberRectangle layoutYProperty changed from "+t.doubleValue()+" to "+t1.doubleValue()+".");
            }
        });
		


        this.rectWidthProperty.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
            	myText.xProperty().set(getxProperty().doubleValue()+(rectWidthProperty.doubleValue()/2));
                System.out.println("SimpleDoubleProperty rectWidthProperty changed from "+t.doubleValue()+" to "+t1.doubleValue()+".");
            }
        });
        this.rectHeightProperty.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {           	
            	myText.yProperty().set(getyProperty().doubleValue()+(rectHeightProperty.doubleValue()/2));;
            	System.out.println("SimpleDoubleProperty rectHeightProperty changed from "+t.doubleValue()+" to "+t1.doubleValue()+".");
            }
        });

		valueProperty.addListener(new ChangeListener<Number>()
				{
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number oldVal, Number newVal) {
						//Color change
						FillTransition myFillTransition=new FillTransition();
						myFillTransition.setShape(myEnclosedRect);
						myFillTransition.setDuration(Duration.millis(myFillTransitionDuration));
						double myDivision=Math.log(newVal.doubleValue())/Math.log(2048);
						if (myDivision>1) myDivision=1;
						Color myInterpolationResult=new Color(1,0,0,1.0).interpolate(Color.PALEGOLDENROD, myDivision);
						myFillTransition.setToValue(myInterpolationResult);
						myFillTransition.play();
						System.out.println("Color on Number for IntegerProperty!");
						//Value change
						UpdateText(X,Y,newVal.intValue());
					}
				}
		);
		myEnclosedRect.boundsInLocalProperty().addListener(new ChangeListener<Bounds>()
				{
					@Override
					public void changed(ObservableValue<? extends Bounds> arg0,
							Bounds oldVal, Bounds newVal) {
						Double myNewScale=newVal.getWidth()/myText.boundsInLocalProperty().getValue().getWidth();
						myText.setScaleX(myNewScale);
						myText.setScaleY(myNewScale);
						//System.out.println("Listener on Bounds for BoundsInLocalProperty!");
					}
				}
		);
		myText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> target, String oldValue, String newValue) {
				Bounds rb = myEnclosedRect.getBoundsInLocal();
				Bounds tb = myEnclosedRect.getBoundsInLocal();
				double scalex = rb.getWidth() / tb.getWidth();
				myText.setScaleX(scalex);
				myText.setScaleY(scalex);
				System.out.println("Listener on Text for TextProperty!");
			}
		});
		this.valueProperty.setValue(initialValue);
	}
	public void moveTo(int x, int y)
	{
		KeyValue myKeyValue_X,myKeyValue_Y;
		setX(x);
		setY(y);
		UpdateText(X,Y,valueProperty.intValue());
		myKeyValue_X=new KeyValue(this.getxProperty(), x*rectWidthProperty.doubleValue());
		myKeyValue_Y=new KeyValue(this.getyProperty(), y*rectHeightProperty.doubleValue());
		KeyFrame kf = new KeyFrame(Duration.millis(myMoveToDuration), myKeyValue_X,myKeyValue_Y);
		ObservableList<KeyFrame> myKeyFrames=myTimeLine.getKeyFrames();
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
		return ReadOnlyIntegerProperty.readOnlyIntegerProperty(valueProperty).get();
	}
	public void setValue(int value) {
		this.valueProperty.setValue(value);
	}
	public double getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public double getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public Rectangle getMyEnclosedRect() {
		return myEnclosedRect;
	}
	public Text getMyText() {
		return myText;
	}
	public void UpdateText(int x,int y,int value)
	{
		//myText.setText(String.valueOf(value)+"("+String.valueOf(x)+";"+String.valueOf(y)+")");
		myText.setText(String.valueOf(value));//+"("+String.valueOf(x)+";"+String.valueOf(y)+")");
	}
	public boolean equals(NumberRectangle obj) {
		return obj.X==this.X && obj.Y==this.Y;
	}
}
