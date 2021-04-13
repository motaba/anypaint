package org.dikappa.anypaint;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class EraserDrawing extends Drawing {
	protected float height;
	protected float width;
	
	public EraserDrawing(float width, float height) {
		this.height=height;
		this.width=width;
		
		createShapes();
	}
	
	protected void createShapes() {
		float cornerradius=height/8;
		float bandwidth=height*5/8;
		float bandstart=height/6;
		
		GeneralPath rightbody=new GeneralPath();
		rightbody.moveTo(0, -height/2);
		rightbody.lineTo(width/2-cornerradius, -height/2);
		rightbody.quadTo(width/2, -height/2, width/2, -height/2+cornerradius);
		rightbody.lineTo(width/2, height/2-cornerradius);
		rightbody.quadTo(width/2, height/2, width/2-cornerradius, height/2);
		rightbody.lineTo(0, height/2);
		rightbody.lineTo(0,  -height/2);
		
		shapes.add(rightbody);
		colors.add(Color.white);
		shapes.add(rightbody.createTransformedShape(AffineTransform.getScaleInstance(-1, 1)));
		colors.add(darker(Color.white));
		
		GeneralPath rightBand=new GeneralPath();
		rightBand.moveTo(0,  -height/2+bandstart);
		rightBand.lineTo(width/2, -height/2+bandstart);
		rightBand.lineTo(width/2, -height/2+bandstart+bandwidth);
		rightBand.lineTo(0, -height/2+bandstart+bandwidth);
		rightBand.lineTo(0, -height/2+bandstart);
		
		shapes.add(rightBand);
		Color azure=new Color(0,128,255);
		colors.add(azure);
		shapes.add(rightBand.createTransformedShape(AffineTransform.getScaleInstance(-1, 1)));
		colors.add(darker(azure));
		
		hotspot=new Point2D.Float(-width/2, height/2);
		
	}

	@Override
	public double getCursorAngle(boolean left) {
		return left?-Math.PI/6:Math.PI/6;
	}
	

}
