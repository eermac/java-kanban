import taskManager.FileBackedTasksManager;
import taskManager.InMemoryTaskManager;
import taskPackage.Epic;
import taskPackage.Subtask;
import taskPackage.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        test2();


    }

    public static void test2() throws IOException {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Path test_file = Files.createFile(Paths.get("resources", "testFile.csv"));

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
        System.out.println(test.getSubtaskById(100006));
        System.out.println(test.getSubtaskById(100005));
        System.out.println(test.getSubtaskById(100005));
        System.out.println(test.getSubtaskById(100006));
        System.out.println(test.getTaskById(100002));

        System.out.println("--------------\nВывод истории");
        System.out.println(test.getDefaultHistory().toString());

        test.removeEpic(100003); //delete epic 100003 and subtask on history (100005, 100006)

        System.out.println("--------------\nВывод истории");
        System.out.println(test.getDefaultHistory().toString());

        System.out.println(Files.readString(Path.of("resources/testFile.csv")));
    }
}