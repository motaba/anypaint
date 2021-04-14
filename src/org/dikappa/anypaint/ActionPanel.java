package org.dikappa.anypaint;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class ActionPanel extends JComponent {
	private static final long serialVersionUID = -4847734777135800315L;
	
	protected Action action;
	protected Rectangle2D bounds;
	
	protected double scale=1.0;
	
	protected AnyPaint app;
	
	protected Cursor dragCursor;
	
	public ActionPanel(AnyPaint a, Action act) {
		app=a;
		action=act;
		bounds=action.getIcon().getBounds();
		dragCursor=new Cursor(action.getIcon(), new Point2D.Double());
		app.getCursorPane().addMouseEventSource(this);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((int) ((bounds.getWidth()+AnyPaint.ACTION_MARGIN*2)*scale), (int) ((bounds.getHeight()+AnyPaint.ACTION_MARGIN*2)*scale));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2=(Graphics2D) g;
		action.getIcon().draw(g2,(-bounds.getX()+AnyPaint.ACTION_MARGIN)*scale,(-bounds.getY()+AnyPaint.ACTION_MARGIN)*scale, scale);
	}
	
	public void setScale(double scale) {
		this.scale=scale;
	}
	
	public Action getAction() {
		return action;
	}
	
}
