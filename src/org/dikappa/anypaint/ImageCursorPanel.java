package org.dikappa.anypaint;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImageCursorPanel extends JPanel {

	private static final long serialVersionUID = -7338602680621161504L;

	protected Cursor cursor=new Cursor(new NewDocumentDrawing(), new Point2D.Double(0,0));
	protected double scale=1.0;
	
	public ImageCursorPanel() {
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent e) {
				if (e instanceof MouseEvent) {
					repaint();
				}
			}
		}, AWTEvent.MOUSE_EVENT_MASK|AWTEvent.MOUSE_MOTION_EVENT_MASK);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (cursor!=null) {
			Point p=MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, this);
			Point2D hs=cursor.getHotSpot();
			cursor.getDrawing().draw((Graphics2D) g, p.x-hs.getX()*scale, p.y-hs.getY()*scale, scale);
		}

	}
	
	public void setDrawingCursor(Cursor c) {
		if (c==null&&cursor!=null) {
			setCursor(null);
		}
		if (c!=null&&cursor==null) {
			setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB), new Point(0,0), "empty"));
		}
//		repaintCursorArea(lastMousePosition);
		cursor=c;
//		repaintCursorArea(lastMousePosition);
		repaint();

	}
	
	public void setScale(double s) {
		scale=s;
		repaint();
	}
}
