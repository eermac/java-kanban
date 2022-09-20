import taskManager.InMemoryTaskManager;
import taskPackage.Epic;
import taskPackage.Subtask;
import taskPackage.Task;
import taskPackage.TaskStatus;

public class Main {
    public static void main(String[] args) {
        test();
   }

   public static void test(){
       InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

       Task product = new Task("Поход в магазин", "Купить продуктов на ужин");
       Task dogWalk = new Task("Погулять с собакой", "Гулять с собакой на площадке");

       Epic childGoSchool = new Epic("Собрать ребенка в школу", "Подготовить ребенка к учебному году");
       Subtask schoolBag = new Subtask("Купить рюкзак", "Купить школьный рюкзак");
       Subtask schoolPlace = new Subtask("Показать школу", "Показать ребенку где школа");

       Epic buildHouse = new Epic("Построить дом", "Построить дом на берегу озера");
       Subtask choise = new Subtask("Выбрать место", "Выбрать место для строительства");

       System.out.println("--------------\nСоздание задач и их вывод");
       inMemoryTaskManager.createTask(product);
       inMemoryTaskManager.createTask(dogWalk);
       System.out.println(inMemoryTaskManager.getAllTask());

       System.out.println("--------------\nСоздание эпиков и их вывод");
       inMemoryTaskManager.createEpic(childGoSchool);
       inMemoryTaskManager.createEpic(buildHouse);
       System.out.println(inMemoryTaskManager.getAllEpic());

       System.out.println("--------------\nСоздание подзадач и их вывод");
       inMemoryTaskManager.createSubTask(schoolBag, childGoSchool);
       inMemoryTaskManager.createSubTask(schoolPlace, childGoSchool);
       inMemoryTaskManager.createSubTask(choise, buildHouse);
       System.out.println(inMemoryTaskManager.getAllSubtusk());
       System.out.println(inMemoryTaskManager.getAllTaskForEpic(childGoSchool));

       System.out.println("--------------\nИзменение статусов и их вывод");
       inMemoryTaskManager.updateTask(product, TaskStatus.IN_PROGRESS);
       System.out.println(inMemoryTaskManager.getTaskById(100001));

       inMemoryTaskManager.updateSubtask(choise, TaskStatus.IN_PROGRESS);
       System.out.println(inMemoryTaskManager.getEpicById(100004));
       System.out.println(inMemoryTaskManager.getAllTaskForEpic(buildHouse));

       inMemoryTaskManager.updateSubtask(choise, TaskStatus.DONE);
       System.out.println(inMemoryTaskManager.getEpicById(100004));
       System.out.println(inMemoryTaskManager.getAllTaskForEpic(buildHouse));

       System.out.println("--------------\nУдаление");
       inMemoryTaskManager.removeTask(100002);
       System.out.println(inMemoryTaskManager.getAllTask());

       inMemoryTaskManager.removeEpic(100004);
       System.out.println(inMemoryTaskManager.getAllEpic());

       System.out.println("--------------\nВывод истории");
       System.out.println(inMemoryTaskManager.getDefaultHistory().toString());
   }
}
