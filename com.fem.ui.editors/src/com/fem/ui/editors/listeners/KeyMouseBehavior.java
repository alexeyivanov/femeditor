package com.fem.ui.editors.listeners;

import java.awt.AWTEvent;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;

public abstract class KeyMouseBehavior extends Behavior{



	protected volatile boolean mouseMotion = false;

	protected  Canvas3D canvas;

	protected boolean motion = false;

	protected boolean keyPressed;

	private WakeupCriterion[] conditions;

	private WakeupCondition wakeupCondition;

	public KeyMouseBehavior(Canvas3D canvas3d) {

		if (canvas3d == null) {
			throw new NullPointerException();
		}
		canvas = canvas3d;
		
	}


	public void initialize() {
		conditions = new WakeupCriterion[4];
		conditions[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
		conditions[1] = new WakeupOnAWTEvent(Event.MOUSE_DOWN);
		conditions[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_WHEEL);
		conditions[3] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		wakeupCondition = new WakeupOr(conditions);

		wakeupOn(wakeupCondition);

	}
	
	
	 public void processStimulus(Enumeration criteria ) {
		 WakeupCriterion wakeup;
			AWTEvent[] evt = null;

			while(criteria.hasMoreElements()) {
			    wakeup = (WakeupCriterion)criteria.nextElement();
			    if (wakeup instanceof WakeupOnAWTEvent)
				evt = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
			    processAWTEvents(evt);
			}
		    
			wakeupOn (wakeupCondition);
	}

	 
	
	

	protected abstract void processAWTEvents(AWTEvent aawtevent[]);

	protected abstract void integrateTransforms();

	
}