package org.dikappa.anypaint;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Drawing {

	protected List<Shape> shapes=new ArrayList<Shape>();
	protected List<Color> colors=new ArrayList<Color>();
	protected Point2D.Float hotspot=null;
	
	protected Cursor leftCursor=null;
	protected Cursor rightCursor=null;

	public void draw(Graphics2D g, float x, float y, double rotate) {
		
		AffineTransform at=AffineTransform.getTranslateInstance(x, y);
		at.concatenate(AffineTransform.getRotateInstance(rotate));
		
		AffineTransform atOld=g.getTransform();
		g.transform(at);
		
		for (int i=0;i<shapes.size();i++) {
			Color c=colors.get(i);
			Shape s=shapes.get(i);
			g.setColor(c);
			g.fill(s);
			g.setColor(Color.black);
			g.draw(s);
		}
		
		g.setTransform(atOld);
	}

	protected Color darker(Color c) {
		return new Color(c.getRed()*75/100,c.getGreen()*75/100,c.getBlue()*75/100);
	}
	
	public Rectangle determineBounds(double rotate) {
		AffineTransform rot=AffineTransform.getRotateInstance(rotate);
		
		Rectangle rect=null;
		for (Shape s: shapes) {
			Shape ts=rot.createTransformedShape(s);
			rect=rect==null?ts.getBounds():rect.union(ts.getBounds());
		}
		return rect;
	}
	
	public Point getHotSpot(double rotate) {
		if (hotspot==null) {
			return new Point(0,0);
		}
		Point2D.Float tp=new Point2D.Float();
		AffineTransform.getRotateInstance(rotate).transform(hotspot, tp);
		return new Point(Math.round(tp.x),Math.round(tp.y));
	}
	
	public double getCursorAngle(boolean left) {
		return 0;
	}
	
	
}
