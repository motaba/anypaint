package org.dikappa.anypaint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;

public class Eraser extends Tool {
	
	protected float size;
	protected int rad;
	protected Stroke stroke;
	protected Drawing drawing;
	
	public Eraser(AnyPaint app) {
		this.size=app.getEraserStrokeSize();
		rad=Math.round(size/2);
		stroke=new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		drawing=new EraserDrawing(app.getEraserWidth(),app.getEraserHeight());
	}

	@Override
	public Rectangle strokeTool(Graphics2D g, Point from, Point to) {
		g.setColor(Color.white);
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
	
	
}
