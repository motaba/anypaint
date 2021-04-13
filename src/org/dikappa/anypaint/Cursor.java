package org.dikappa.anypaint;

import java.awt.geom.Point2D;

public class Cursor {
	protected Drawing drawing;
	protected Point2D hotspot;
	
	public Cursor(Drawing drawing, Point2D hotspot) {
		this.drawing=drawing;
		this.hotspot=hotspot;
	}
	
	public Drawing getDrawing() {
		return drawing;
	}
	
	public Point2D getHotSpot() {
		return hotspot;
	}
	
}
