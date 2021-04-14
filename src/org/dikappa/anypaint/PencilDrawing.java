package org.dikappa.anypaint;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class PencilDrawing extends Drawing{

	protected float width;
	protected float height;
	
	protected Color pencilColor;
	
	public PencilDrawing(Color c) {
		width=50;
		height=150;
		pencilColor=c;
		
		createShapes();
	}
	
	
	protected void createShapes() {
		float baseline=-height/2;
		float rubbersize=height/6;
		float bandsize=height/18;
		float tipsize=height/6;
		float leadsize=height/12;
		float cornerradius=height/36;
		float bodysize=height-rubbersize-bandsize-tipsize-leadsize;
		
		GeneralPath rightRubber=new GeneralPath();
		rightRubber.moveTo(0, baseline);
		rightRubber.lineTo(width/2-cornerradius, baseline);
		rightRubber.quadTo(width/2, baseline, width/2, baseline+cornerradius);
		rightRubber.lineTo(width/2, baseline+rubbersize);
		rightRubber.lineTo(0, baseline+rubbersize);
		rightRubber.lineTo(0, baseline);
		
		Shape leftRubber=rightRubber.createTransformedShape(AffineTransform.getScaleInstance(-1, 1));
	
		baseline+=rubbersize;
		
		GeneralPath rightBand=new GeneralPath();
		rightBand.moveTo(0, baseline);
		rightBand.lineTo(width/2,  baseline);
		rightBand.lineTo(width/2,  baseline+bandsize);
		rightBand.lineTo(0,  baseline+bandsize);
		rightBand.lineTo(0,  baseline);
		
		Shape leftBand=rightBand.createTransformedShape(AffineTransform.getScaleInstance(-1, 1));

		baseline+=bandsize;

		GeneralPath rightBody=new GeneralPath();
		rightBody.moveTo(0, baseline);

		rightBody.lineTo(width/2,  baseline);
		rightBody.lineTo(width/2,  baseline+bodysize);
		rightBody.lineTo(0,  baseline+bodysize);
		rightBody.lineTo(0,  baseline);

		Shape leftBody=rightBody.createTransformedShape(AffineTransform.getScaleInstance(-1, 1));

		baseline+=bodysize;
		
		GeneralPath rightTip=new GeneralPath();
		rightTip.moveTo(0, baseline);
	
		rightTip.lineTo(width/2, baseline);
		rightTip.lineTo(width/2*(1-tipsize/(tipsize+leadsize)), baseline+tipsize);
		rightTip.lineTo(0, baseline+tipsize);
		rightTip.lineTo(0, baseline);
		
		Shape leftTip=rightTip.createTransformedShape(AffineTransform.getScaleInstance(-1, 1));
		
		baseline+=tipsize;
		
		GeneralPath rightlead=new GeneralPath();
		rightlead.moveTo(0,  baseline);
		
		rightlead.lineTo(width/2*(1-tipsize/(tipsize+leadsize)), baseline);
		rightlead.lineTo(width/2*(1-(tipsize+leadsize/2)/(tipsize+leadsize)), baseline+leadsize/2);
		rightlead.quadTo(width/2*(1-(tipsize+leadsize*3/4)/(tipsize+leadsize)), baseline+leadsize*3/4, 0, baseline+leadsize*3/4);
		rightlead.lineTo(0, baseline);
		
		hotspot=new Point2D.Float(0, baseline+leadsize*3/4);
		
		Shape leftlead=rightlead.createTransformedShape(AffineTransform.getScaleInstance(-1, 1));

		
		Color tipColor=new Color(240,180,110);
		
		shapes.add(rightRubber);
		colors.add(Color.gray);
		shapes.add(leftRubber);
		colors.add(darker(Color.gray));
		shapes.add(rightBand);
		colors.add(Color.white);
		shapes.add(leftBand);
		colors.add(darker(Color.white));
		shapes.add(rightBody);
		colors.add(pencilColor);
		shapes.add(leftBody);
		colors.add(darker(pencilColor));
		shapes.add(rightTip);
		colors.add(tipColor);
		shapes.add(leftTip);
		colors.add(darker(tipColor));
		shapes.add(rightlead);
		colors.add(pencilColor);
		shapes.add(leftlead);
		colors.add(darker(pencilColor));
	}
	
	@Override
	public double getCursorAngle(boolean left) {
		return Math.atan(width*2/height)*(left?-1:1);
	}


}
