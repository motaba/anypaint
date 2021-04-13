package org.dikappa.anypaint;

import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

public class HandDrawing extends Drawing {
	
	protected float width;
	protected float height;
	
	public HandDrawing(float width, float height) {
		this.width=width;
		this.height=height;
		
		createShapes();
	}
	
	protected void createShapes() {
		float baseline=height/2;
		float topfinger=-height/2;
		float littlefinger=topfinger+height/6;
		float medfinger=topfinger+height/12;
		float basefinger=topfinger+height/3;
		float basethumb=0;
		
		float lowerradius=width/3;
		float upperradius=width/10;
		
		GeneralPath hand=new GeneralPath();
		
		hand.moveTo(-width/2+lowerradius, baseline);
		hand.lineTo(width/2-lowerradius, baseline);
		hand.append(new Arc2D.Float(width/2-lowerradius*2, baseline-lowerradius*2, lowerradius*2, lowerradius*2, 270, 90, Arc2D.OPEN), true);
		//hand.quadTo(width/2, baseline, width/2, baseline-lowerradius);
		hand.lineTo(width/2, littlefinger+upperradius);
//		hand.quadTo(width/2, littlefinger, width/2-upperradius, littlefinger);
//		hand.quadTo(width/2-upperradius*2, littlefinger, width/2-upperradius*2, littlefinger+upperradius);
		hand.append(new Arc2D.Float(width/2-upperradius*2, littlefinger, upperradius*2, upperradius*2, 0, 180, Arc2D.OPEN), true);
		hand.lineTo(width/2-upperradius*2, basefinger);
		hand.lineTo(width/2-upperradius*2, medfinger+upperradius);
		hand.append(new Arc2D.Float(width/2-upperradius*4, medfinger, upperradius*2, upperradius*2, 0, 180, Arc2D.OPEN), true);
		hand.lineTo(width/2-upperradius*4, basefinger);
		hand.lineTo(width/2-upperradius*4, topfinger+upperradius);
		hand.append(new Arc2D.Float(width/2-upperradius*6, topfinger, upperradius*2, upperradius*2, 0, 180, Arc2D.OPEN), true);
		hand.lineTo(width/2-upperradius*6, basefinger);
		hand.lineTo(width/2-upperradius*6, medfinger+upperradius);
		hand.append(new Arc2D.Float(width/2-upperradius*8, medfinger, upperradius*2, upperradius*2, 0, 180, Arc2D.OPEN), true);
		hand.lineTo(width/2-upperradius*8, basethumb);
		hand.lineTo(width/2-upperradius*8, basethumb-upperradius*(1+Math.sin(Math.PI/4)));
		hand.append(new Arc2D.Float(-width/2,basethumb-upperradius,2*upperradius,2*upperradius,135,45,Arc2D.OPEN), true);
		
		hand.append(new Arc2D.Float(-width/2,baseline-2*lowerradius, 2*lowerradius, 2*lowerradius, 180, 90, Arc2D.OPEN), true);
//		
//		hand.lineTo(-width/2, basethumb);
		shapes.add(hand);
		colors.add(Color.white);
	}

}
