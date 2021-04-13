package org.dikappa.anypaint;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class ActionPanel extends JComponent {
	private static final long serialVersionUID = -4847734777135800315L;
	
	protected Drawing drawing;
	protected double margin;
	protected Rectangle2D bounds;
	
	protected double scale=1.0;
	
	protected AnyPaint app;
	
	public ActionPanel(AnyPaint a, Drawing d, double m) {
		app=a;
		drawing=d;
		margin=m;
		bounds=drawing.getBounds();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((int) ((bounds.getWidth()+margin)*scale), (int) ((bounds.getHeight()+margin)*scale));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		drawing.draw((Graphics2D) g,-bounds.getX(), -bounds.getY(), scale);
	}
	
	public void setScale(double scale) {
		this.scale=scale;
	}
	
	
	
}
