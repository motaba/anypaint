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

	public static final double TOOL_MARGIN=10.0;
	public static final double ACTION_MARGIN=10.0;
	public static final double CANVAS_MARGIN=10.0;
	public static final long MIN_TIME_BETWEEN_CLICK=1000l;
	
	protected float width;
	protected float height;
	
	double scale;
	
	protected JFrame frame;
	protected ToolBox toolBox;
	protected ActionBox actionBox;
	protected Canvas canvas;
	protected OverlayPanel cursorPanel;
	
	public AnyPaint() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getScreenDevices()[0];
		
		cursorPanel=new OverlayPanel();
        frame=new JFrame("Minipainter");
		toolBox=new ToolBox(this);
		actionBox=new ActionBox(this);
		canvas=new Canvas(this);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
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

		frame.setSize(1000,800);
		frame.revalidate();
		frame.setVisible(true);
		//device.setFullScreenWindow(frame);
	}

	public void setSize(int width, int height) {
		this.width=width;
		this.height=height;
		scale=height/1000.0;
		
		toolBox.setScale(scale);
		actionBox.setScale(scale);
		cursorPanel.setScale(scale);
		
		int canvasHeight=(int) (height-CANVAS_MARGIN*2*scale);
		int canvasWidth=(int) (width-CANVAS_MARGIN*2*scale-toolBox.getPreferredSize().width-actionBox.getPreferredSize().width);
		canvas.clear(canvasWidth, canvasHeight);
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

	public ToolBox getToolBox() {
		return toolBox;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public OverlayPanel getCursorPane() {
		return cursorPanel;
	}
	
}
