package org.dikappa.anypaint;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Tool {
	public abstract Rectangle strokeTool(Graphics2D g, Point from, Point to);
	public abstract Drawing getDrawing();
	public abstract Cursor getCursor();
}
