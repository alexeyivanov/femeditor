package com.fem.main;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}
	
	
	@Override
	protected void makeActions(IWorkbenchWindow window) {
		IWorkbenchAction action;
		action = ActionFactory.HELP_CONTENTS.create(window);
		register(action);
		action = ActionFactory.ABOUT.create(window);
		register(action);
	}
	
	
	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager menu = null;
		menu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
		menu.add(getAction(ActionFactory.HELP_CONTENTS.getId()));
		menu.add(getAction(ActionFactory.ABOUT.getId()));

		menuBar.add(menu);
		
		
	}
}
