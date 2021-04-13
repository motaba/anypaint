package org.dikappa.anypaint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Drawing {

	protected List<Shape> shapes=new ArrayList<Shape>();
	protected List<Color> colors=new ArrayList<Color>();
	protected Point2D.Float hotspot=null;
	
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

	public void draw(Graphics2D g, double x, double y, double scale) {
		AffineTransform at=AffineTransform.getTranslateInstance(x, y);
		at.concatenate(AffineTransform.getScaleInstance(scale, scale));
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
		if (rect!=null) {
			rect=new Rectangle(rect.x-1, rect.y-1, rect.width+2, rect.height+2);
		} else {
			rect=new Rectangle();
		}
		return rect;
	}

	public Rectangle2D getBounds() {
		Rectangle2D rect=new Rectangle2D.Float();
		for (Shape s: shapes) {
			Rectangle2D.union(rect, s.getBounds2D(), rect);
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
	
	public Drawing getTransformedDrawing(AffineTransform at) {
		Drawing td=new Drawing();
		td.colors.addAll(colors);
		for (Shape s: shapes) {
			td.shapes.add(at.createTransformedShape(s));
		}
		return td;
	}
	
	public static Drawing overlay(Drawing ... drawings) {
		Drawing od=new Drawing();
		for (Drawing sd: drawings) {
			od.colors.addAll(sd.colors);
			od.shapes.addAll(sd.shapes);
		}
		return od;
	}
}
