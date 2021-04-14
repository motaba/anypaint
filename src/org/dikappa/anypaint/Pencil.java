package org.dikappa.anypaint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

public class Pencil extends Tool {

	protected Color color;
	protected Stroke stroke;
	protected Drawing drawing;
	protected Cursor cursor;
	protected float size;
	
	public Pencil(Color c, float sz) {
		color=c;
		size=sz;
		stroke=new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		Drawing d=new PencilDrawing(color);
		Point hotspot=d.getHotSpot(-Math.PI/6);
		cursor=new Cursor(d.getTransformedDrawing(AffineTransform.getRotateInstance(-Math.PI/6)),hotspot);
		drawing=d.getTransformedDrawing(AffineTransform.getRotateInstance(Math.PI/2));
	}

	@Override
	public Rectangle strokeTool(Graphics2D g, Point from, Point to) {
		g.setColor(color);
		g.setStroke(stroke);
		
		int minx=Math.min(from.x, to.x);
		int miny=Math.min(from.y, to.y);
		int width=from.x+to.x-minx-minx;
		int height=from.y+to.y-miny-miny;
		int rad=Math.round(size/2);
		
		g.drawLine(from.x, from.y, to.x, to.y);

		return new Rectangle(minx-rad,  miny-rad, width+rad*2, height+rad*2);
	}

	@Override
	public Drawing getDrawing() {
		return drawing;
	}

	@Override
	public Cursor getCursor() {
		return cursor;
	}
}
