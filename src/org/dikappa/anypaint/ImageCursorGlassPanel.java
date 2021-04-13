package org.dikappa.anypaint;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class ImageCursorGlassPanel extends JComponent {
	
	private static final long serialVersionUID = 8183362142479951869L;

	//Mouse event listener and redispatch to other components
	class MIListener extends MouseInputAdapter {
	    @Override
	    public void mouseMoved(MouseEvent e) {
			repaintCursorArea(lastMousePosition);
			repaintCursorArea(e.getPoint());
			lastMousePosition=e.getPoint();
	        redispatchMouseEvent(e);
	    }
	 
	    @Override
	    public void mouseDragged(MouseEvent e) {
			if (!contains(e.getPoint())) {
				repaintCursorArea(lastMousePosition);
				lastMousePosition=null;
			} else {
				repaintCursorArea(lastMousePosition);
				repaintCursorArea(e.getPoint());
				lastMousePosition=e.getPoint();
			}
	        redispatchMouseEvent(e);
	    }
	 
	    @Override
	    public void mouseClicked(MouseEvent e) {
	        redispatchMouseEvent(e);
	    }
	 
	    @Override
	    public void mouseEntered(MouseEvent e) {
			lastMousePosition=e.getPoint();
	        redispatchMouseEvent(e);
	    }
	 
	    @Override
	    public void mouseExited(MouseEvent e) {
			repaintCursorArea(lastMousePosition);
			lastMousePosition=null;
	        redispatchMouseEvent(e);
	    }
	 
	    @Override
	    public void mousePressed(MouseEvent e) {
	        redispatchMouseEvent(e);
	    }
	 
	    @Override
	    public void mouseReleased(MouseEvent e) {
	        redispatchMouseEvent(e);
	    }

	    @Override
	    public void mouseWheelMoved(MouseWheelEvent e) {
	        redispatchMouseEvent(e);
	    }

	    protected Component lastDispatchComponent=null;
	    
	    private void redispatchMouseEvent(MouseEvent e) {
	        Point glassPanePoint = e.getPoint();
	        Component layeredPane = frame.getLayeredPane();
	        Point containerPoint = SwingUtilities.convertPoint(
	                                        ImageCursorGlassPanel.this,
	                                        glassPanePoint,
	                                        layeredPane);
	        
	        if (containerPoint.y >= 0 && containerPoint.x >=0) {
	            Component component = 
	                SwingUtilities.getDeepestComponentAt(
	                                        layeredPane,
	                                        containerPoint.x,
	                                        containerPoint.y);

	            if ((e.getID()==MouseEvent.MOUSE_MOVED||e.getID()==MouseEvent.MOUSE_DRAGGED)&&component!=lastDispatchComponent&&lastDispatchComponent!=null) {
	    	        Point exitPoint = SwingUtilities.convertPoint(
                            ImageCursorGlassPanel.this,
                            glassPanePoint,
                            lastDispatchComponent);
	            	
	            	lastDispatchComponent.dispatchEvent(new MouseEvent(component, MouseEvent.MOUSE_EXITED, e.getWhen(), e.getModifiersEx(), exitPoint.x, exitPoint.y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
	            }

	            
	            if (component != null) {
	                Point componentPoint = SwingUtilities.convertPoint(
	                                            ImageCursorGlassPanel.this,
	                                            glassPanePoint,
	                                            component);
	    
		            if ((e.getID()==MouseEvent.MOUSE_MOVED||e.getID()==MouseEvent.MOUSE_DRAGGED)&&component!=lastDispatchComponent) {
		            	component.dispatchEvent(new MouseEvent(component, MouseEvent.MOUSE_ENTERED, e.getWhen(), e.getModifiersEx(), componentPoint.x, componentPoint.y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
		            }
	                MouseEvent ne;
	                if (e instanceof MouseWheelEvent) {
	                	ne=new MouseWheelEvent(component, e.getID(), e.getWhen(), e.getModifiersEx(), componentPoint.x, componentPoint.y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), ((MouseWheelEvent) e).getScrollType(), ((MouseWheelEvent) e).getScrollAmount(), ((MouseWheelEvent) e).getWheelRotation(), ((MouseWheelEvent) e).getPreciseWheelRotation());
	                } else {
	                	ne=new MouseEvent(component, e.getID(), e.getWhen(), e.getModifiersEx(), componentPoint.x, componentPoint.y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
	                }	                	
	            	component.dispatchEvent(ne);
	            }
	            lastDispatchComponent=component;
	        }
	    }
	    
	    
	}

	protected RootPaneContainer frame;
	protected MIListener miListener=new MIListener();
	
	protected Point lastMousePosition=null;

	protected Image img;
	protected Point hotspot;

	
	public ImageCursorGlassPanel(RootPaneContainer frame) {
		this.frame=frame;
		addMouseListener(miListener);
		addMouseMotionListener(miListener);
		addMouseWheelListener(miListener);
		
		setOpaque(false);
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (img==null) {
			return;
		}
		if (lastMousePosition!=null) {
			g.drawImage(img, lastMousePosition.x-hotspot.x, lastMousePosition.y-hotspot.y, null);
		}
	}
	
	protected void repaintCursorArea(Point p) {
		if (img==null) {
			return;
		}
		if (p==null) {
			return;
		}
		repaint(p.x-hotspot.x,p.y-hotspot.y,img.getWidth(null),img.getHeight(null));
	}
	
	public void setImage(Image img, Point hotspot) {
		if (img==null&&this.img!=null) {
			setCursor(null);
		}
		if (img!=null&&this.img==null) {
			setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB), new Point(0,0), "empty"));
		}
		repaintCursorArea(lastMousePosition);
		this.img=img;
		this.hotspot=hotspot;
		repaintCursorArea(lastMousePosition);
	}


}

