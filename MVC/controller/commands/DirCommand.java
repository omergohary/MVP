/**
 * @file DirCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              show the folders and files in specific dir the user typed.
 * 				
 * @date    24/09/2015
 */


package controller.commands;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

import model.IModel;
import view.IDisplayable;
import view.IView;

/**
 * The command that responsible on "dir" request
 */
public class DirCommand extends CommonCommand {

	/**
	 * Instantiates a new dir command.
	 *
	 * @param view
	 *            the view
	 * @param model
	 *            the model
	 */
	public DirCommand(IView view, IModel model) 
	{
		super(view, model);
	}

	@Override
	/**
	 * This function is responsible to show the current dir (folders and files)
	 * @param args - the required user's dir
	 */
	public void doCommand(final String... args) {
		// IO Command, so execute in a thread.

		new Thread(new Runnable() {

			@Override
			public void run() {
				File f = new File(args[0]);
				final File[] files = f.listFiles();
				view.display(new IDisplayable() {
					String message;

					@Override
					public void display(OutputStream out) {
						PrintWriter writer = new PrintWriter(out);
						if (files != null) {
							for (File file : files) {
								message+=file.getAbsolutePath() + "\n";
							}
						} else {
							message+="No files found.";
						}
						
						writer.println(getMessage());
						writer.flush();
					}

					@Override
					public void setMessage(String aMessage) {
						this.message = aMessage;
					}

					@Override
					public String getMessage() {
						return message;
					}
				});
			}
		}).start();
	}

}
