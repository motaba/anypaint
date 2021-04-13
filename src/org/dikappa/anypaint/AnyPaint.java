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
	
	double scale;
	
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
	protected ActionBox actionBox;
	protected Canvas canvas;
	protected ImageCursorPanel cursorPanel;
	
	public AnyPaint() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getScreenDevices()[0];
		
        frame=new JFrame("Minipainter");
		toolBox=new ToolBoxPanel(this);
		actionBox=new ActionBox(this);
		canvas=new Canvas(this);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		cursorPanel=new ImageCursorPanel();
		cursorPanel.setLayout(new BorderLayout());
		JPanel canvasframe=new JPanel(new GridBagLayout());
		canvasframe.add(canvas);
		cursorPanel.add(canvasframe, BorderLayout.CENTER);
		cursorPanel.add(toolBox, BorderLayout.EAST);
		cursorPanel.add(actionBox, BorderLayout.WEST);

		cursorPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setSize(cursorPanel.getWidth(), cursorPanel.getHeight());
				cursorPanel.repaint();
			}
		});
		
		
		frame.setContentPane(cursorPanel);
		
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();

		frame.setSize(800,600);
		frame.revalidate();
		frame.setVisible(true);
		//device.setFullScreenWindow(frame);
	}

	public void setSize(int width, int height) {
		this.width=width;
		this.height=height;
		scale=height/1000.0;
		
		calculateSizes();
		toolBox.onResize();
		actionBox.setScale(scale);
		canvas.clear((int) (width-vtoolspacing*2-toolBox.getPreferredSize().width-actionBox.getPreferredSize().width), getCanvasHeight());
	}
	
	protected void calculateSizes() {
		//Pencil size=1/4 of height (3:1 length width)
		pencillength=height/6;
		pencilwidth=pencillength/3;
		
		eraserlength=pencillength;
		eraserwidth=eraserlength/2;
	
		vtoolspacing=height/48;
		
		canvasheight=height-vtoolspacing*2;
		canvaswidth=width-vtoolspacing*6-pencillength-getActionIconWidth();
		
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
	
	public ImageCursorPanel getCursorPane() {
		return cursorPanel;
	}
	
	public float getActionIconHeight() {
		return height/8;
	}

	public float getActionIconWidth() {
		return height/12;
	}
}
