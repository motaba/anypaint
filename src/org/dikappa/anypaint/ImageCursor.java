package org.dikappa.anypaint;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class ImageCursor {
	protected Image img;
	protected Point hotspot;
	
	protected Component comp;
	
	protected Point lastMousePosition=null;
	
	protected class MouseTracker implements MouseListener, MouseMotionListener {

		@Override
		public void mouseMoved(MouseEvent e) {
			repaint(lastMousePosition);
			repaint(e.getPoint());
			lastMousePosition=e.getPoint();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if (!comp.contains(e.getPoint())) {
				repaint(lastMousePosition);
				lastMousePosition=null;
			} else {
				repaint(lastMousePosition);
				repaint(e.getPoint());
				lastMousePosition=e.getPoint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			repaint(lastMousePosition);
			lastMousePosition=null;
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			lastMousePosition=e.getPoint();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		
	};
	
	protected MouseTracker mt=new MouseTracker();
	
	protected void repaint(Point p) {
		if (img==null) {
			return;
		}
		if (p==null) {
			return;
		}
		comp.repaint(p.x-hotspot.x,p.y-hotspot.y,img.getWidth(null),img.getHeight(null));
	}
	
	public void paintCursor(Graphics2D g) {
		if (img==null) {
			return;
		}
		if (lastMousePosition!=null) {
			g.drawImage(img, lastMousePosition.x-hotspot.x, lastMousePosition.y-hotspot.y, null);
		}
	}
	
	public void setComponent(Component c) {
		if (comp!=null) {
			comp.removeMouseListener(mt);
			comp.removeMouseMotionListener(mt);
			comp.setCursor(null);
		}
		if (c==null) {
			return;
		}
		comp=c;
		comp.addMouseMotionListener(mt);
		comp.addMouseListener(mt);
		if (img!=null) {
			comp.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB), new Point(0,0), "empty"));
		}
	}
	
	public void setImage(Image img, Point hotspot) {
		if (img==null&&this.img!=null) {
			comp.setCursor(null);
		}
		if (img!=null&&this.img==null) {
			comp.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB), new Point(0,0), "empty"));
		}
		repaint(lastMousePosition);
		this.img=img;
		this.hotspot=hotspot;
		repaint(lastMousePosition);
	}
}
