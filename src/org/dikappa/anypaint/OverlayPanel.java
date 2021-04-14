package org.dikappa.anypaint;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class OverlayPanel extends JPanel {

	private static final long serialVersionUID = -7338602680621161504L;

	protected double scale=1.0;
	
	protected Cursor draggingIcon=null;
	
	protected Map<Component, Cursor> cursorMap=new HashMap<Component, Cursor>();
	protected Component currentComponent=null;
	
	protected MouseInputAdapter mil=new MouseInputAdapter() {
		@Override
		public void mouseDragged(MouseEvent e) {
			repaint();
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			currentComponent=(Component) e.getSource();
			repaint();
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			repaint();
		}
	};
	
	public OverlayPanel() {
//		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
//			@Override
//			public void eventDispatched(AWTEvent e) {
//				if (e instanceof MouseEvent&&cursor!=null) {
//					repaint();
//				}
//			}
//		}, AWTEvent.MOUSE_MOTION_EVENT_MASK);
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB), new Point(0,0), "empty"));
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		super.paint(g);
		Cursor cursor=null;
		Component comp=currentComponent;
		while (cursor==null&&comp!=null) {
			cursor=cursorMap.get(comp);
			comp=comp.getParent();
		}
		if (cursor!=null) {
			Point p=MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, this);
			Point2D hs=cursor.getHotSpot();
			cursor.getDrawing().draw((Graphics2D) g, p.x-hs.getX()*scale, p.y-hs.getY()*scale, scale);
		}

	}
	
	public void setScale(double s) {
		scale=s;
		repaint();
	}
	
	public void onCursorChange(Component comp, Cursor cur) {
		cursorMap.put(comp, cur);
		repaint();
	}

	public void addMouseEventSource(Component comp) {
		comp.addMouseListener(mil);
		comp.addMouseMotionListener(mil);
		comp.addMouseWheelListener(mil);
	}
}
