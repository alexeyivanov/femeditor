package com.fem.ui.editors.listeners;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.media.j3d.Behavior;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupOnBehaviorPost;
import javax.media.j3d.WakeupOnElapsedFrames;

public abstract class KeyMouseBehavior extends Behavior implements
MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {

	protected static final int POST_ID_EVENT_QUEUED = 9998;

	protected WakeupOnElapsedFrames frameWakeup;

	protected WakeupOnBehaviorPost postWakeup;

	protected volatile boolean mouseMotion = false;

	protected  Canvas3D canvas;

	private List<AWTEvent> eventQueue =  new ArrayList<AWTEvent>();

	private AWTEvent aawtevent[] = null;
	
	protected boolean motion = false;

	protected boolean keyPressed;

//	private boolean listening = false;

	private int listenerFlags;

	private boolean listening = false;
	
	  /**
     * Flag indicating Behavior should listen for Mouse Events
     */
    public final static int MOUSE_LISTENER = 0x01;

    /**
     * Flag indicating Behavior should listen for Mouse Motion Events
     */
    public final static int MOUSE_MOTION_LISTENER = 0x02;

    /**
     * Flag indicating Behavior should listen for Key Events
     */
    public final static int KEY_LISTENER = 0x04;

    /**
     * Flag indicating Behavior should listen for MouseWheel Events
     */
    public final static int MOUSE_WHEEL_LISTENER = 0x08;

	public KeyMouseBehavior(Canvas3D canvas3d, int listenerFlags) {

		if (canvas3d == null) {
			throw new NullPointerException();
		}
		canvas = canvas3d;
		
		setListenerFlags(listenerFlags);
	}

	private void setListenerFlags(int listenerFlags) {
		this.listenerFlags = listenerFlags;
	}

	public void initialize() {
		frameWakeup = new WakeupOnElapsedFrames(0);
		postWakeup = new WakeupOnBehaviorPost(this, POST_ID_EVENT_QUEUED);

		wakeupOn(postWakeup);
		mouseMotion = false;
//
		if (getEnable() && canvas != null) {
			enableListeners(true);
		}
	}
	
	
	 public void processStimulus(Enumeration behEnum ) {
		boolean hadPost = false;

		while (behEnum.hasMoreElements()) {
			WakeupCondition wakeup = (WakeupCondition) behEnum.nextElement();
			if (wakeup instanceof WakeupOnBehaviorPost) {
				hadPost = true;
			} else if (wakeup instanceof WakeupOnElapsedFrames) {
				AWTEvent[] events = null;
				// access to event queue must be synchronized
				synchronized (eventQueue) {
					events = (AWTEvent[]) eventQueue
							.toArray(new AWTEvent[eventQueue.size()]);
					eventQueue.clear();
				}
				processAWTEvents(events);

				if (motion)
					integrateTransforms();
			}
		}

		if (motion || hadPost) {
			// wake up on behavior posts and elapsed frames if in motion
			wakeupOn(frameWakeup);
		} else {
			// only wake up on behavior posts if not in motion
			wakeupOn(postWakeup);
		}
	}
	
	public void setEnable(boolean flag) {
		if (flag == getEnable())
			return;
		super.setEnable(flag);
	}

	
	public void enableListeners(boolean enable) {
	        if (enable) {
	        	if (listening){
	        		return;
	        	}
	            if ( (listenerFlags & MOUSE_LISTENER)!=0)
	            	    canvas.addMouseListener(this);

	            if ( (listenerFlags & MOUSE_MOTION_LISTENER)!=0)
	            	    canvas.addMouseMotionListener(this);

	            if ( (listenerFlags & MOUSE_WHEEL_LISTENER)!=0)
	            	    canvas.addMouseWheelListener(this);

	            if ( (listenerFlags & KEY_LISTENER)!=0)
	            	   canvas.addKeyListener(this);
	            listening = true;
	        } else {
	            if ( (listenerFlags & MOUSE_LISTENER)!=0)
	            	    canvas.removeMouseListener(this);

	            if ( (listenerFlags & MOUSE_MOTION_LISTENER)!=0)
	            	    canvas.removeMouseMotionListener(this);

	            if ( (listenerFlags & MOUSE_WHEEL_LISTENER)!=0)
	            	    canvas.removeMouseWheelListener(this);

	            if ( (listenerFlags & KEY_LISTENER)!=0)
	            	    canvas.removeKeyListener(this);
	            
	            listening = false;
	        }
	    }
	
	

	protected abstract void processAWTEvents(AWTEvent aawtevent[]);

	protected abstract void integrateTransforms();

	protected void queueAWTEvent(AWTEvent awtevent) {

		synchronized (eventQueue) {
			eventQueue.add(awtevent);
		}
		
	   if (eventQueue.size() == 1) {
			postId(POST_ID_EVENT_QUEUED);
		}
		
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
		queueAWTEvent(mouseevent);
	}

	public void mouseReleased(MouseEvent mouseevent) {
		queueAWTEvent(mouseevent);
	}

	public void mouseDragged(MouseEvent mouseevent) {
		queueAWTEvent(mouseevent);
	}

	public void mouseMoved(MouseEvent mouseevent) {
	}

	/*
	 * No longer needed in new implementation (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent keyevent) {
		queueAWTEvent(keyevent);
	}

	public void keyPressed(KeyEvent keyevent) {
		queueAWTEvent(keyevent);
	}


    
    public void keyTyped(final java.awt.event.KeyEvent e) {
        queueAWTEvent( e );
    }

    public void mouseWheelMoved( final java.awt.event.MouseWheelEvent e) {
	    queueAWTEvent( e );
    }    
	
	
}