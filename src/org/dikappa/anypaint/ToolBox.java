package org.dikappa.anypaint;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ToolBox extends JPanel {
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
	
	protected Drawing handDrawing;
	
	protected Cursor cursor;
	
	protected Tool currentTool;
	
	protected List<ToolPanel> toolPanels=new ArrayList<ToolPanel>();
	
	public ToolBox(AnyPaint app) {
		this.app=app;
		app.getCursorPane().addMouseEventSource(this);

		for (Color c: pencilColors) {
			toolPanels.add(new ToolPanel(this, new Pencil(c, 5)));
		}
		toolPanels.add(new ToolPanel(this, new Eraser(30)));
		handDrawing=new HandDrawing();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for (JComponent c: toolPanels) {
			add(c);
		}
		add(Box.createVerticalGlue());
		onToolSelected();
	}

	public Tool getCurrentTool() {
		return currentTool;
	}
	
	public void onToolSelected() {
		Tool t=getCurrentTool();
		Point hotspot=handDrawing.getHotSpot(0);
		Drawing cd=handDrawing;
		if (t!=null) {
			cd=Drawing.overlay(
				t.getDrawing(),
				handDrawing);

		}
		cursor=new Cursor(cd, hotspot);
		app.getCursorPane().onCursorChange(this, cursor);
		repaint();
	}

	public AnyPaint getApp() {
		return app;
	}
	
	public void toolClicked(Tool t) {
		if (t==currentTool) {
			currentTool=null;
		} else {
			currentTool=t;
		}
		onToolSelected();
		app.getCanvas().onToolSelected();
	}
	
	public void setScale(double scale) {
		for (ToolPanel tp: toolPanels) {
			tp.setScale(scale);
		}
	}

}
