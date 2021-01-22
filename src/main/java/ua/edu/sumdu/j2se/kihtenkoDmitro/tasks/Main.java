package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.AllTasksView;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.CalendarView;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.IncomingTasksView;

import java.io.*;

public class Main {
	public static void main(String[] args) {
		AbstractTaskList generalTaskList = new ArrayTaskList();

		File tasksFile = new File("SavedTasks.dat");
		try(FileInputStream tasksInput = new FileInputStream(tasksFile)) {
			TaskIO.read(generalTaskList, tasksInput);
		} catch(IOException e) {
			generalTaskList = new ArrayTaskList();
		}

		AllTasksView allTasksView = new AllTasksView(generalTaskList);

		IncomingTasks incomingTasks = new
				IncomingTasks(generalTaskList);
		IncomingTasksView incomingTasksView =
				new IncomingTasksView(incomingTasks);
		IncomingTasksController itc = new
				IncomingTasksController(incomingTasks);
		Calendar calendar = new Calendar(generalTaskList);
		CalendarView calendarView = new CalendarView(calendar);
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
