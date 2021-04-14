package org.dikappa.anypaint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class TargetDrawing extends Drawing {
	public TargetDrawing() {
		Shape s=new Ellipse2D.Double(-90, -90, 180, 180);
		BasicStroke stroke=new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[] {15.0f, 20}, 0);
		Shape strokedCircle=stroke.createStrokedShape(s);
		
		shapes.add(strokedCircle);
		colors.add(Color.red);
	}
}
