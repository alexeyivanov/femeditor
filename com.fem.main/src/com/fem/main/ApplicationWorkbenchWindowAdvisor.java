package com.fem.main;


import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.handlers.IHandlerService;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
	
	public static final String CONFIRM_ACTION = "Confirm Action";

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}
	@Override
    public void postWindowOpen() {
//        super.postWindowOpen();
//
//        IWorkbenchWindow window = PlatformUI.getWorkbench()
//                .getActiveWorkbenchWindow();
//        IHandlerService handlerService = (IHandlerService) window
//				.getService(IHandlerService.class);
//        try {
//			handlerService.executeCommand("com.fem.main.openEditor", null);
//		} catch (ExecutionException | NotDefinedException | NotEnabledException
//				| NotHandledException e1) {
//			e1.printStackTrace();
//		}

    }
	
	@Override
	public boolean preWindowShellClose() {
//		return preClose();
		return true;
	}
	
	public static boolean preClose() {

		boolean result =
				MessageDialog.openQuestion(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						"Confirm Action", "Are you sure you want to exit?");

		if (!result) {
			return false;
		}
		result =
				MessageDialog.openQuestion(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						CONFIRM_ACTION,
						"Would you like to save the current scene?");

		return true;
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1500, 1024));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setTitle("FEM models");
	}
}
