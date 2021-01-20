package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.DateTimeArithmetic;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.IncomingTasksView;

import java.io.*;
import java.time.LocalDateTime;

public class Main {
	public static void main(String[] args) {
		AbstractTaskList generalTaskList = new ArrayTaskList();
		File tasksFile = new File("SavedTasks.dat");
		try(FileInputStream tasksInput = new FileInputStream(tasksFile)) {
			TaskIO.read(generalTaskList, tasksInput);
		} catch(IOException e) {
			generalTaskList = new ArrayTaskList();
		}

		Calendar calendar = new Calendar(generalTaskList);

		IncomingTasks incomingTasks = new
				IncomingTasks(generalTaskList);
		IncomingTasksView view = new IncomingTasksView(incomingTasks);
		IncomingTasksController controller = new
				IncomingTasksController(incomingTasks);
		generalTaskList.setObservers(incomingTasks.getObservers());
		generalTaskList.getObservers().attach(incomingTasks,
				Event.UPDATE);
		calendar.setObservers(incomingTasks.getObservers());
		ControllerThread thread =
				new ControllerThread(controller);

		GeneralController gc = new GeneralController(Action.MAIN_MENU);

		MainMenuController mc = new MainMenuController(incomingTasks);
		AllTasksController atc = new AllTasksController(generalTaskList);
		CalendarController cc = new CalendarController(calendar);

		gc.attach(mc);
		gc.attach(atc);
		gc.attach(cc);

		thread.start();

		gc.process();

		try(FileOutputStream tasksOutput = new
				FileOutputStream(tasksFile)) {
			TaskIO.write(generalTaskList, tasksOutput);
		} catch (FileNotFoundException e) {
			try {
				if(tasksFile.createNewFile()) {
					try(FileOutputStream tasksOutput = new
							FileOutputStream(tasksFile)) {
						TaskIO.write(generalTaskList, tasksOutput);
					} catch(IOException e2) {
					}
				}
			} catch (IOException e1) {
			}
		} catch (IOException e) {
		}
	}
}
