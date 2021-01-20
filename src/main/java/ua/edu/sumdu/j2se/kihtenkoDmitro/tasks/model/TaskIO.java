package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import java.io.*;

import com.google.gson.Gson;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (ObjectOutputStream listStream = new ObjectOutputStream(out)) {
            listStream.writeInt(tasks.size());

            for (Task currentTask : tasks) {
                listStream.writeObject(currentTask);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try(ObjectInputStream listStream = new ObjectInputStream(in)) {
            int taskAmount = listStream.readInt();

            for(int counter = 0; counter < taskAmount; counter++) {
                tasks.add((Task)listStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try(ObjectOutputStream listStream = new
                ObjectOutputStream(new FileOutputStream(file))) {
            listStream.writeInt(tasks.size());

            for(Task currentTask : tasks) {
                listStream.writeObject(currentTask);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        try(ObjectInputStream listStream = new
                ObjectInputStream(new FileInputStream(file))) {
            int taskAmount = listStream.readInt();

            for(int counter = 0; counter < taskAmount; counter++) {
                tasks.add((Task)listStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        try(BufferedWriter listStream = new BufferedWriter(out)) {
            Gson json = new Gson();

            listStream.write(String.valueOf(tasks.size()));
            listStream.newLine();
            for(Task currentTask : tasks) {
                json.toJson(currentTask, listStream);
                listStream.newLine();
            }
            listStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        try(LineNumberReader listStream = new LineNumberReader(in)) {
            Gson json = new Gson();
            String lineValue;

            int taskNumber = Integer.parseInt(listStream.readLine());

            for(int counter = 0; counter < taskNumber; counter++) {
                lineValue = listStream.readLine();

                tasks.add(json.fromJson(lineValue, Task.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        try(Writer writeStream = new OutputStreamWriter(
                new FileOutputStream(file))) {
            write(tasks, writeStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText(AbstractTaskList tasks, File file) {
        try(Reader readStream = new InputStreamReader(
                new FileInputStream(file))) {
            read(tasks, readStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
