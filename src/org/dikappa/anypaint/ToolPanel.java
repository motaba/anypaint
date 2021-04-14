package org.dikappa.anypaint;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class ToolPanel extends JComponent {
	private static final long serialVersionUID = 1610013928338742993L;
	
	protected Tool tool;
	protected Rectangle2D bounds;
	
	protected double scale=1.0;
	
	protected ToolBox toolBox;
	
	protected long lastClickTime;
	
	public ToolPanel(ToolBox tb, Tool t) {
		toolBox=tb;
		tool=t;
		bounds=t.getDrawing().getBounds();
		lastClickTime=System.currentTimeMillis()-AnyPaint.MIN_TIME_BETWEEN_CLICK;
		
		tb.getApp().getCursorPane().addMouseEventSource(this);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				long now=System.currentTimeMillis();
				if (now-lastClickTime<AnyPaint.MIN_TIME_BETWEEN_CLICK) {
					return;
				}
				lastClickTime=now;
				tb.toolClicked(tool);
			}
		});
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((int) ((bounds.getWidth()+AnyPaint.TOOL_MARGIN*2)*scale), (int) ((bounds.getHeight()+AnyPaint.TOOL_MARGIN*2)*scale));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		tool.getDrawing().draw((Graphics2D) g,(-bounds.getX()+AnyPaint.TOOL_MARGIN)*scale, (-bounds.getY()+AnyPaint.TOOL_MARGIN)*scale, scale);
	}
	
	public void setScale(double scale) {
		this.scale=scale;
	}
}
