package org.dikappa.anypaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class ToolBoxPanel extends JComponent {
	private static final long serialVersionUID = 7400719111096685343L;

	protected AnyPaint app;
	
	protected Color[] pencilColors=new Color[] {
		new Color(255,0,0),
		new Color(255,128,0),
		new Color(255,255,0),
		new Color(0,255,0),
		new Color(0,255,255),
		new Color(0,128,255),
		new Color(0,0,255),
		new Color(128,0,255),
		new Color(255,0,255),
		new Color(128,64,0),
		new Color(0,0,0)
	};
	
	protected List<Tool> tools=new ArrayList<Tool>();
	
	protected Drawing handDrawing;
	protected BufferedImage cursorImage;
	protected Point cursorHotspot;
	
	protected Tool currentTool;
	
	public ToolBoxPanel(AnyPaint app) {
		this.app=app;
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point p=e.getPoint();
				int idx=Math.round((p.y-app.getVToolSpacing()-app.getPencilWidth()/2)/(app.getPencilWidth()+app.getVToolSpacing()));
				if (idx<0||idx>=tools.size()) {
					return;
				}
				Tool t=tools.get(idx);
				if (t==currentTool) {
					currentTool=null;
				} else {
					currentTool=t;
				}
				onToolSelected();
				app.getCanvas().onToolSelected();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				app.getCursorPane().setImage(cursorImage, cursorHotspot);
			}
		});
	}

	public void onResize() {
		tools.clear();
		for (Color c: pencilColors) {
			tools.add(new Pencil(app, c));
		}
		tools.add(new Eraser(app));
		handDrawing=new HandDrawing(app.getPencilHeight()/2, app.getPencilWidth()*1.5f);
		onToolSelected();
		revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i=0;i<tools.size();i++) {
			Tool t=tools.get(i);
			if (t==currentTool) continue;
			t.getDrawing().draw(g2, getWidth()/2, app.getVToolSpacing()+(app.getPencilWidth()+app.getVToolSpacing())*i+app.getPencilWidth()/2, Math.PI/2);
		}
	}


	@Override
	public Dimension getPreferredSize() {
		int pw=(int) (app.getPencilHeight()+app.getVToolSpacing()*2);
		int ph=(int) ((app.getPencilWidth()+app.getVToolSpacing())*tools.size()+app.getVToolSpacing());
		return new Dimension(pw, ph);
	}
	
	public Tool getCurrentTool() {
		return currentTool;
	}
	
	public void onToolSelected() {
		Tool t=getCurrentTool();
		Point hotspot=handDrawing.getHotSpot(0);
		Rectangle r=handDrawing.determineBounds(0);
		if (t!=null) {
			r=r.union(t.getDrawing().determineBounds(Math.PI/2));
		}
		cursorImage=new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=cursorImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (t!=null) {
			t.getDrawing().draw(g,-r.x,-r.y,Math.PI/2);
		}
		handDrawing.draw(g,-r.x,-r.y,0);
		cursorHotspot=new Point(hotspot.x-r.x,hotspot.y-r.y);

		app.getCursorPane().setImage(cursorImage, cursorHotspot);
		repaint();
	}

	
}
