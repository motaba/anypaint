package org.dikappa.anypaint;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

public class ActionBox extends JComponent {

	private static final long serialVersionUID = -5710239025359272742L;

	protected List<ActionPanel> actionPanels=new ArrayList<ActionPanel>();

	protected AnyPaint app;
	
	public ActionBox(AnyPaint app) {
		this.app=app;
		actionPanels.add(new ActionPanel(app, new NewDocumentDrawing(), 10.0));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for (JComponent c: actionPanels) {
			add(c);
		}
		add(Box.createVerticalGlue());
	}
	
	public void setScale(double scale) {
		for (ActionPanel ap: actionPanels) {
			ap.setScale(scale);
		}
	}
}
