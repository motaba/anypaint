package org.dikappa.anypaint;

import java.awt.Color;
import java.awt.geom.GeneralPath;

public class NewDocumentDrawing extends Drawing {
	protected double height=125;
	protected double width=100;
	
	public NewDocumentDrawing() {
		createShapes();
	}
	
	protected void createShapes() {
		double dogear=Math.min(height, width)/3;
		
		GeneralPath main=new GeneralPath();
		main.moveTo(-width/2, -height/2);
		main.lineTo(width/2-dogear, -height/2);
		main.lineTo(width/2-dogear, -height/2+dogear);
		main.lineTo(width/2, -height/2+dogear);
		main.lineTo(width/2, height/2);
		main.lineTo(-width/2, height/2);
		main.lineTo(-width/2, -height/2);
		
		shapes.add(main);
		colors.add(Color.white);
		
		GeneralPath ear=new GeneralPath();
		ear.moveTo(width/2-dogear, -height/2);
		ear.lineTo(width/2-dogear, -height/2+dogear);
		ear.lineTo(width/2, -height/2+dogear);
		ear.lineTo(width/2-dogear, -height/2);
		
		shapes.add(ear);
		colors.add(Color.white);
	}


}
