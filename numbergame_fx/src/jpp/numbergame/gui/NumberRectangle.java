package jpp.numbergame.gui;

import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class NumberRectangle extends StackPane {
	public static final int myPadding=5;
	public static final int myArcWidth=10;
	public static final float myDropShadowOffset=4.0f;
	public NumberRectangle(int x, int y, int initialValue) {
		super();
		this.myX.set(x);this.myY.set(y);
		this.setPadding(new Insets(myPadding,myPadding,myPadding,myPadding));
		this.myEnclosedRect.setArcWidth(myArcWidth);
		this.myText.setBoundsType(TextBoundsType.VISUAL);
		this.myText.setFill(Color.WHITE);
		this.myText.setFont(Font.font(null, FontWeight.BOLD, 70));
		DropShadow ds = new DropShadow();
		ds.setOffsetY(myDropShadowOffset);
		ds.setColor(Color.BLACK);
		this.myText.setEffect(ds);
		
//		myTilesValue.addListener(new ChangeListener<Object>()
//		{
//           @Override 
//           public void changed(ObservableValue o,Object oldVal,Object newVal){
//               this. 
//        	   System.out.println("Electric bill has changed!");
//           }
//		});
		myTilesValue.addListener(new ChangeListener<Number>()
				{
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number arg1, Number arg2) {
						// TODO Auto-generated method stub
						myText.setText(arg0.toString());
					}
				}
		);
		
//		myEnclosedRect=new Rectangle();
//		myTilesValue=new SimpleIntegerProperty();
//		myText=new Text();
//		myX=new SimpleDoubleProperty();
//		myY=new SimpleDoubleProperty();
//		myHeight=new SimpleDoubleProperty();
//		myWidth=new SimpleDoubleProperty();
//		myTimeLine=new Timeline();
	}
	private Rectangle myEnclosedRect=new Rectangle();
	private IntegerProperty myTilesValue=new SimpleIntegerProperty();
	private Text myText=new Text();
	private DoubleProperty myX=new SimpleDoubleProperty();
	private DoubleProperty myY=new SimpleDoubleProperty();
	private DoubleProperty myHeight=new SimpleDoubleProperty();
	private DoubleProperty myWidth=new SimpleDoubleProperty();
	private Timeline myTimeLine=new Timeline();
}
