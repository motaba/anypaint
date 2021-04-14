package org.dikappa.anypaint;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

public class ActionBox extends JComponent {

	private static final long serialVersionUID = -5710239025359272742L;

	protected List<ActionPanel> actionPanels=new ArrayList<ActionPanel>();

	protected AnyPaint app;
	
	protected Cursor handCursor;
	
	public ActionBox(AnyPaint app) {
		this.app=app;
		app.getCursorPane().addMouseEventSource(this);

		handCursor=new Cursor(new HandDrawing(), new Point2D.Double());
		actionPanels.add(new ActionPanel(app, new NewDocumentDrawing()));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for (JComponent c: actionPanels) {
			add(c);
		}
		add(Box.createVerticalGlue());
		app.getCursorPane().onCursorChange(this, handCursor);
	}
	
	public void setScale(double scale) {
		for (ActionPanel ap: actionPanels) {
			ap.setScale(scale);
		}
	}
}
