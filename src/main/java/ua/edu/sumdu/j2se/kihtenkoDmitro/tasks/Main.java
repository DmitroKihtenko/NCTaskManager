package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.AllTasksView;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.CalendarView;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.IncomingTasksView;

import java.io.*;

public class Main {
	private static final Logger logger
			= Logger.getLogger(Main.class);

	public static void main(String[] args) {
		try {
			AbstractTaskList generalTaskList = TaskListFactory.
					createTaskList(ListTypes.types.ARRAY);

			File tasksFile = new File("SavedTasks.dat");
			try {
				TaskIO.readBinary(generalTaskList, tasksFile);
			} catch (FileNotFoundException e) {
				logger.info(
						"Task list saving has not been read"
				);
			}

			new AllTasksView(generalTaskList);

			IncomingTasks incomingTasks = new
					IncomingTasks(generalTaskList);
			IncomingTasksView incomingTasksView =
					new IncomingTasksView(incomingTasks);
			IncomingTasksController itc = new
					IncomingTasksController(incomingTasks);
			Calendar calendar = new Calendar(generalTaskList);
			new CalendarView(calendar);
			ControllerThread thread =
					new ControllerThread(itc);

			GeneralController gc = new GeneralController(Action.MAIN_MENU);

			MainMenuController mc = new MainMenuController(incomingTasks);
			AllTasksController atc = new AllTasksController(generalTaskList);
			CalendarController cc = new CalendarController(calendar);

			gc.attach(mc);
			gc.attach(atc);
			gc.attach(cc);

			incomingTasksView.update();

			thread.start();

			gc.process();

			try {
				TaskIO.writeBinary(generalTaskList, tasksFile);
			} catch (IOException e) {
				logger.info(
						"Saving tasks error"
				);
			}
		} catch (IllegalArgumentException e) {
			logger.fatal("Wrong parameter value", e);

			e.printStackTrace();
		} catch (Exception e) {
			logger.fatal("Unknown error", e);

			e.printStackTrace();
		}
	}
}
