package org.dikappa.anypaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class Canvas extends JComponent {

	private static final long serialVersionUID = 1L;
	
	protected BufferedImage image;
	protected Graphics2D imggraph;
	protected Stroke stroke;
	
	protected Point lastMousePos=null;
	
	protected AnyPaint mp;
	
	protected Tool currentTool;
	
	protected Cursor cursor;
	
	public Canvas(AnyPaint mp) {
		this.mp=mp;
		
		currentTool=null;

		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				lastMousePos=e.getPoint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (!contains(e.getPoint())) {
					lastMousePos=null;
				} else {
					onMouseDrag(e);
					lastMousePos=e.getPoint();
				}
			}
			
		});

		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lastMousePos=null;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				lastMousePos=e.getPoint();
				mp.getCursorPane().setDrawingCursor(cursor);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				onMouseClick(e);
				lastMousePos=e.getPoint();
			}
		});
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
			}
		});
	}

	protected void onMouseClick(MouseEvent e) {
		strokeTool(e.getPoint(), e.getPoint());
	}
	
	protected void onMouseDrag(MouseEvent e) {
		strokeTool(lastMousePos==null?e.getPoint():lastMousePos, e.getPoint());
	}
	
	protected void strokeTool(Point from, Point to) {
		if (currentTool==null) {
			return;
		}
		Rectangle rect=currentTool.strokeTool(imggraph, from, to);
		repaint(rect);
	}
	
	public void clear(int width, int height) {
		image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		imggraph=image.createGraphics();
		imggraph.setColor(Color.white);
		imggraph.fillRect(0, 0, width, height);
		revalidate();
	}
	
	@Override
	public Dimension getPreferredSize() {
		if (image!=null) {
			return new Dimension(image.getWidth(),image.getHeight());
		}
		return new Dimension(0,0);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Rectangle clip=g.getClipBounds();
		if (clip==null) {
			g.drawImage(image, 0, 0,null);
		} else {
			g.drawImage(image, clip.x,  clip.y,  clip.x+clip.width,  clip.y+clip.height, clip.x,  clip.y,  clip.x+clip.width,  clip.y+clip.height, null);
		}
	}
	
	public void onToolSelected() {
		currentTool=mp.getToolBox().getCurrentTool();
		if (currentTool==null) {
			cursor=null;
		}
		Drawing draw=currentTool.getDrawing();
		double angle=-Math.PI/6.0;
		Point hotspot=draw.getHotSpot(angle);
		cursor=new Cursor(draw.getTransformedDrawing(AffineTransform.getRotateInstance(angle)),hotspot);
	}
	
}
