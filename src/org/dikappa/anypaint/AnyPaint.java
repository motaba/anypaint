package org.dikappa.anypaint;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AnyPaint {

	protected float width;
	protected float height;
	
	protected float pencillength;
	protected float pencilwidth;
	
	protected float eraserlength;
	protected float eraserwidth;
	
	protected float vtoolspacing;
	
	protected float canvaswidth;
	protected float canvasheight;
	
	protected float pencilstrokesize;
	protected float eraserstrokesize;
	
	protected JFrame frame;
	protected ToolBoxPanel toolBox;
	protected Canvas canvas;
	protected ImageCursorGlassPanel glassPane;
	
	public AnyPaint() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getScreenDevices()[0];
		
        frame=new JFrame("Minipainter");
		toolBox=new ToolBoxPanel(this);
		canvas=new Canvas(this);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JPanel content=new JPanel(new BorderLayout());
		JPanel canvasframe=new JPanel(new GridBagLayout());
		canvasframe.add(canvas);
		content.add(canvasframe, BorderLayout.CENTER);
		content.add(toolBox, BorderLayout.EAST);

		content.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setSize(content.getWidth(), content.getHeight());
				content.repaint();
				System.out.println("Comp resized to "+content.getWidth()+"x"+content.getHeight());
			}
		});
		
		glassPane=new ImageCursorGlassPanel(frame);
		frame.setContentPane(content);
		frame.setGlassPane(glassPane);
		glassPane.setVisible(true);
		
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();
		device.setFullScreenWindow(frame);
	}

	public void setSize(int width, int height) {
		this.width=width;
		this.height=height;
		
		calculateSizes();
		canvas.clear(getCanvasWidth(), getCanvasHeight());
		toolBox.onResize();
	}
	
	protected void calculateSizes() {
		//Pencil size=1/4 of height (3:1 length width)
		pencillength=height/6;
		pencilwidth=pencillength/3;
		
		eraserlength=pencillength;
		eraserwidth=eraserlength/2;
	
		vtoolspacing=height/48;
		
		canvasheight=height-vtoolspacing*2;
		canvaswidth=width-vtoolspacing*4-pencillength;
		
		pencilstrokesize=height/100;
		eraserstrokesize=pencilstrokesize*4;
	}
	
	public float getPencilHeight() {
		return pencillength;
	}
	
	public float getPencilWidth() {
		return pencilwidth;
	}
	
	public float getEraserHeight() {
		return eraserlength;
	}
	
	public float getEraserWidth() {
		return eraserwidth;
	}
	
	public float getVToolSpacing() {
		return vtoolspacing;
	}
	
	public int getCanvasWidth() {
		return (int) canvaswidth;
	}
	
	public int getCanvasHeight() {
		return (int) canvasheight;
	}
	public static void main(String[] args) {
//    	KeyHook.blockWindowsKey();
//    	Runtime.getRuntime().addShutdownHook(new Thread() {
//    		@Override
//    		public void run() {
//    			super.run();
//    			KeyHook.unblockWindowsKey();
//    		}
//    	});
        AnyPaint mp=new AnyPaint();
	}

	public float getPencilStrokeSize() {
		return pencilstrokesize;
	}
	
	public float getEraserStrokeSize() {
		return eraserstrokesize;
	}
	
	public ToolBoxPanel getToolBox() {
		return toolBox;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public ImageCursorGlassPanel getCursorPane() {
		return glassPane;
	}
}
