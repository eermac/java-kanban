package main.taskManager;

import main.taskPackage.Task;
import main.taskPackage.Epic;
import main.taskPackage.Subtask;
import main.taskPackage.TaskStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager{
    private String filename;

    public FileBackedTasksManager(Path filename){
        this.filename = filename.toString();
    }

    public void save() {
        List<Task> test = new ArrayList<>();

        for(Task next: super.storageTask.values()){
            test.add(next);
        }

        for(Task next: super.storageEpic.values()){
            test.add(next);
        }

        for(Task next: super.storageSubtask.values()){
            test.add(next);
        }

        try {
            if(test.isEmpty()){
                throw new ManagerSaveException("Данных для записи нет");
            }
        } catch (ManagerSaveException exception){
            System.out.println(exception.getMessage());
        }

        try (Writer fileWriter = new FileWriter(this.filename)) {
                fileWriter.write("id,type,name,startTime, duration, endTime, status,description,epic\n");

                for(Task next: test){
                    fileWriter.write(toString(next));
                }

                if(!super.historyMap.isEmpty()){
                    fileWriter.write("\n" + FileBackedTasksManager.historyToString(super.historyManager));
                }

            } catch (IOException exception) {
                exception.getMessage();
            }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
        tasks.put(task, task.getStartTime().toString());
    }

    @Override
    public void createEpic(Epic object){
        super.createEpic(object);
        save();
    }

    @Override
    public void createSubTask(Subtask object, Epic storageSubtask){
        super.createSubTask(object, storageSubtask);
        save();
        tasks.put(object, object.getStartTime().toString());

        if(super.storageSubtask.size() == 1){
            storageSubtask.setStartTime(object);
            storageSubtask.setEndTime(object);
        } else {
            storageSubtask.setDuration(super.storageSubtask);
            storageSubtask.setEndTime(object);
        }
    }

    public List<Task> getPrioritizedTasks(){
        List<Task> listSortetTask = new ArrayList<>();

        for(Task next: tasks.keySet()){
            listSortetTask.add(next);
        }

        return listSortetTask;
    }


    @Override
    public void removeTask(Integer Id){
        super.removeTask(Id);
        save();
    }

    @Override
    public void removeEpic(Integer Id){
        super.removeEpic(Id);
        save();
    }

    @Override
    public void removeSubtask(Integer Id){
        super.removeSubtask(Id);
        save();
    }

    private String toString(Task task) { //проверка на null
        if(task instanceof Subtask){
            return task.getId() + "," + TaskType.SUBTASK + "," + task.getTaskName() + "," + task.getStartTime() + "," + task.getDuration() + "," + task.getEndTime() + "," + task.getStatus() + "," + task.getDescription() + "," + ((Subtask) task).getEpicId() + "\n";
        } else if(task instanceof Epic){
            return task.getId() + "," + TaskType.EPIC + "," + task.getTaskName() + "," + task.getStartTime() + "," + task.getDuration() + "," + task.getEndTime() + "," + task.getStatus() + "," + task.getDescription() + ",\n";
        } else {
            return task.getId() + "," + TaskType.TASK+ "," + task.getTaskName() + "," + task.getStartTime() + "," + task.getDuration() + "," + task.getEndTime() + "," + task.getStatus() + "," + task.getDescription() + ",\n";
        }
    }

    private Task fromString(String value) {
        String[] split = value.split(",");

        if(split[1].equals("TASK")){
            Task task = new Task(split[2], split[4], TaskStatus.valueOf(split[3]));
            task.setId(Integer.parseInt(split[0]));

            return task;
        } else if (split[1].equals("EPIC")){
            Epic task = new Epic(split[2], split[4]);
            task.setId(Integer.parseInt(split[0]));

            return task;
        } else {
            Subtask task = new Subtask(split[2], split[4], TaskStatus.valueOf(split[3]));
            task.setEpicId(Integer.parseInt(split[5]));
            task.setId(Integer.parseInt(split[0]));

            return task;
        }
    }

    private static String historyToString(HistoryManager manager){
        InMemoryHistoryManager taskManager = (InMemoryHistoryManager) manager;
        StringBuilder stringHistory = new StringBuilder();

        for(Task next: taskManager.getTasks()){
            stringHistory.append(next.getId() + ",");
        }

        return stringHistory.deleteCharAt(stringHistory.length()-1).toString();
    }

    private static List<Integer> historyFromString(String value){
        String[] split = value.split(",");
        List<Integer> taskIdList = new ArrayList<>();

        for(String next: split){
            taskIdList.add(Integer.parseInt(next));
        }

        return taskIdList;
    }

    public static FileBackedTasksManager loadFromFile(File file) throws IOException {
        Path filePath = Paths.get("resources", file.getName());
        String line = Files.readString(Path.of(filePath.toString()));
        FileBackedTasksManager loadManager = new FileBackedTasksManager(filePath);

        String lines[] = line.split("\\r?\\n");


        for(int i = 1; i < lines.length; i ++){
            if(lines[i].isEmpty()){
                for(Integer next: historyFromString(lines[i+1])){
                    if(loadManager.storageTask.containsKey(next)){
                        loadManager.getTaskById(next);
                    } else if (loadManager.storageEpic.containsKey(next)){
                        loadManager.getEpicById(next);
                    } else {
                        loadManager.getSubtaskById(next);
                    }
                }
                break;
            } else {
                if(loadManager.fromString(lines[i]) instanceof Subtask){
                    Subtask task = (Subtask) loadManager.fromString(lines[i]);
                    loadManager.createSubTask(task, loadManager.storageEpic.get(task.getEpicId()));
                } else if(loadManager.fromString(lines[i]) instanceof Epic){
                    Epic task = (Epic) loadManager.fromString(lines[i]);
                    loadManager.createEpic(task);
                } else {
                    Task task = loadManager.fromString(lines[i]);
                    loadManager.createTask(task);
                }
            }
        }

        return loadManager;
    }

    Comparator<Task> userComparator = new Comparator<>() {
        @Override
        public int compare(Task user1, Task user2) {
            if(user1.getStartTime().isAfter(user2.getStartTime())) return 1;
            else return -1;
        }
    };

    Map<Task, String> tasks = new TreeMap<>(userComparator);


    public static void main(String[] args) throws IOException {
     Path testFile = Files.createFile(Paths.get("resources", "testFile.csv"));

//     testSaveFile(testFile);
//     testRecoverFile(testFile);
     testDate(testFile);
    }

    public static void testDate(Path test_file) throws IOException {
        FileBackedTasksManager test = new FileBackedTasksManager(test_file);

        Task product = new Task("Поход в магазин", "Купить продуктов на ужин", TaskStatus.NEW, 1000, LocalDateTime.now());
        Task dogWalk = new Task("Погулять с собакой", "Гулять с собакой на площадке", TaskStatus.NEW, 1500, LocalDateTime.now());

        Epic childGoSchool = new Epic("Собрать ребенка в школу", "Подготовить ребенка к учебному году");
        Subtask schoolBag = new Subtask("Купить рюкзак", "Купить школьный рюкзак", TaskStatus.NEW, 10000, LocalDateTime.now());
        Subtask schoolPlace = new Subtask("Показать школу", "Показать ребенку где школа", TaskStatus.NEW, 100, LocalDateTime.now());

        System.out.println("--------------\nСоздание задач");
        test.createTask(dogWalk);
        test.createTask(product);

        System.out.println("--------------\nСоздание эпиков");
        test.createEpic(childGoSchool);

        System.out.println("--------------\nСоздание подзадач");
        test.createSubTask(schoolBag, childGoSchool);
        test.createSubTask(schoolPlace, childGoSchool);

        System.out.println("task1 endTime= " + test.getTaskById(100001).getEndTime());
        System.out.println("task2 endTime= " + test.getTaskById(100002).getEndTime());
        System.out.println("task3 endTime= " + test.getEpicById(100003).getEndTime());
        System.out.println("task4 endTime= " + test.getSubtaskById(100004).getEndTime());
        System.out.println("task5 endTime= " + test.getSubtaskById(100005).getEndTime());

        test.getPrioritizedTasks();

        int i = 1;
        for(Task next: test.getPrioritizedTasks()){
            System.out.println("\ntask priority = " + i++ + "\n" + next.toString() + "date = " + next.getStartTime() + "\n");
        }
    }

    public static void testSaveFile(Path test_file) throws IOException {
        FileBackedTasksManager test = new FileBackedTasksManager(test_file);

        Task product = new Task("Поход в магазин", "Купить продуктов на ужин");
        Task dogWalk = new Task("Погулять с собакой", "Гулять с собакой на площадке");

        Epic childGoSchool = new Epic("Собрать ребенка в школу", "Подготовить ребенка к учебному году");
        Subtask schoolBag = new Subtask("Купить рюкзак", "Купить школьный рюкзак");
        Subtask schoolPlace = new Subtask("Показать школу", "Показать ребенку где школа");
        Subtask schoolFlowers = new Subtask("Купить цветы", "Купить цветы учителям");

        Epic buildHouse = new Epic("Построить дом", "Построить дом на берегу озера");

        System.out.println("--------------\nСоздание задач");
        test.createTask(dogWalk);

        test.createTask(product);

        System.out.println("--------------\nСоздание эпиков");
        test.createEpic(childGoSchool);
        test.createEpic(buildHouse);

        System.out.println("--------------\nСоздание подзадач");
        test.createSubTask(schoolBag, childGoSchool);
        test.createSubTask(schoolPlace, childGoSchool);
        test.createSubTask(schoolFlowers, childGoSchool);

        System.out.println(test.getSubtaskById(100005));
        System.out.println(test.getTaskById(100001));
        System.out.println(test.getEpicById(100003)); //epic with 3 subtask
        System.out.println(test.getSubtaskById(100005));
        System.out.println(test.getTaskById(100002));

        System.out.println("--------------\nВывод истории");
        System.out.println(test.getDefaultHistory().toString());

        test.removeEpic(100003); //delete epic 100003 and subtask on history (100005, 100006)

        System.out.println("--------------\nВывод истории");
        System.out.println(test.getDefaultHistory().toString());

        System.out.println(Files.readString(Path.of("resources/testFile.csv")));
    }

    public static void testRecoverFile(Path testFile) throws IOException {
        FileBackedTasksManager test2 = loadFromFile(testFile.toFile());

        System.out.println("--------------\nВывод истории из нового менеджера");
        System.out.println(test2.getDefaultHistory().toString());
    }
}


class ManagerSaveException extends IOException {
    public ManagerSaveException(final String message){
        super(message);
    }
}
