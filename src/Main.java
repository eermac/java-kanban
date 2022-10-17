import taskManager.InMemoryTaskManager;
import taskPackage.Epic;
import taskPackage.Subtask;
import taskPackage.Task;

public class Main {
    public static void main(String[] args) {
        test2();
    }

    public static void test2(){
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task product = new Task("Поход в магазин", "Купить продуктов на ужин");
        Task dogWalk = new Task("Погулять с собакой", "Гулять с собакой на площадке");

        Epic childGoSchool = new Epic("Собрать ребенка в школу", "Подготовить ребенка к учебному году");
        Subtask schoolBag = new Subtask("Купить рюкзак", "Купить школьный рюкзак");
        Subtask schoolPlace = new Subtask("Показать школу", "Показать ребенку где школа");
        Subtask schoolFlowers = new Subtask("Купить цветы", "Купить цветы учителям");

        Epic buildHouse = new Epic("Построить дом", "Построить дом на берегу озера");

        System.out.println("--------------\nСоздание задач");
        inMemoryTaskManager.createTask(product);
        inMemoryTaskManager.createTask(dogWalk);

        System.out.println("--------------\nСоздание эпиков");
        inMemoryTaskManager.createEpic(childGoSchool);
        inMemoryTaskManager.createEpic(buildHouse);

        System.out.println("--------------\nСоздание подзадач");
        inMemoryTaskManager.createSubTask(schoolBag, childGoSchool);
        inMemoryTaskManager.createSubTask(schoolPlace, childGoSchool);
        inMemoryTaskManager.createSubTask(schoolFlowers, childGoSchool);

        System.out.println(inMemoryTaskManager.getSubtaskById(100005));
        System.out.println(inMemoryTaskManager.getTaskById(100001));
        System.out.println(inMemoryTaskManager.getEpicById(100003)); //epic with 3 subtask
        System.out.println(inMemoryTaskManager.getSubtaskById(100005));
        System.out.println(inMemoryTaskManager.getSubtaskById(100006));
        System.out.println(inMemoryTaskManager.getSubtaskById(100005));
        System.out.println(inMemoryTaskManager.getSubtaskById(100005));
        System.out.println(inMemoryTaskManager.getSubtaskById(100006));


        System.out.println("--------------\nВывод истории");
        System.out.println(inMemoryTaskManager.getDefaultHistory().toString());

        inMemoryTaskManager.removeEpic(100003); //delete epic 100003 and subtask on history (100005, 100006)

        System.out.println("--------------\nВывод истории");
        System.out.println(inMemoryTaskManager.getDefaultHistory().toString());
    }
}