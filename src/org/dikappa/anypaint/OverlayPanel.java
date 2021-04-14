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
	protected Action draggingAction=null;
	protected Drawing targetDrawing=null;
	
	protected Map<Component, Cursor> cursorMap=new HashMap<Component, Cursor>();
	protected Component currentComponent=null;
	
	protected MouseInputAdapter mil=new MouseInputAdapter() {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (e.getSource() instanceof ActionPanel) {
				Action action=((ActionPanel) e.getSource()).getAction();
				if (draggingIcon==null) {
					draggingAction=action;
					draggingIcon=new Cursor(action.getIcon(), new Point2D.Double());
					targetDrawing=new TargetDrawing();
					targetDrawing=Drawing.overlay(targetDrawing, draggingAction.getIcon());
				}
			}
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
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (draggingIcon!=null) {
				Point p=SwingUtilities.convertPoint((Component) e.getSource(), e.getX(), e.getY(), OverlayPanel.this);
				p.x-=OverlayPanel.this.getWidth()/2;
				p.y-=OverlayPanel.this.getHeight()/2;
				double rad=Math.sqrt(p.x*p.x+p.y*p.y);
				if (rad<=90*scale) {
					app.getCanvas().clear();
				}
				draggingIcon=null;
				repaint();
			}
		}
	};
	
	protected AnyPaint app;
	
	public OverlayPanel(AnyPaint a) {
		app=a;
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB), new Point(0,0), "empty"));
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		super.paint(g);
		Cursor cursor=draggingIcon;
		Component comp=currentComponent;
		while (cursor==null&&comp!=null) {
			cursor=cursorMap.get(comp);
			comp=comp.getParent();
		}
		if (cursor!=null) {
			Point p=MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, this);
			Point2D hs=cursor.getHotSpot();
			if (draggingIcon!=null) {
				targetDrawing.draw(g2, getWidth()/2, getHeight()/2, scale, 0.3f);
			}
			cursor.getDrawing().draw((Graphics2D) g, p.x-hs.getX()*scale, p.y-hs.getY()*scale, scale);
		}

	}
	
	public void setScale(double s) {
		scale=s;
		invalidate();
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
