public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task product = new Task("Поход в магазин", "Купить продуктов на ужин");
        Task dogWalk = new Task("Погулять с собакой", "Гулять с собакой на площадке");

        Epic childGoSchool = new Epic("Собрать ребенка в школу", "Подготовить ребенка к учебному году");
        Subtask schoolBag = new Subtask("Купить рюкзак", "Купить школьный рюкзак");
        Subtask schoolPlace = new Subtask("Показать школу", "Показать ребенку где школа");

        Epic buildHouse = new Epic("Построить дом", "Построить дом на берегу озера");
        Subtask choise = new Subtask("Выбрать место", "Выбрать место для строительства");

        System.out.println("--------------\nСоздание задач и их вывод");
        manager.createTask(product);
        manager.createTask(dogWalk);
        System.out.println(manager.getTask());

        System.out.println("--------------\nСоздание эпиков и их вывод");
        manager.createEpic(childGoSchool);
        manager.createEpic(buildHouse);
        System.out.println(manager.getEpic());

        System.out.println("--------------\nСоздание подзадач и их вывод");
        manager.createSubTask(schoolBag, childGoSchool);
        manager.createSubTask(schoolPlace, childGoSchool);
        manager.createSubTask(choise, buildHouse);
        System.out.println(manager.getSubtusk());
        System.out.println(manager.getAllTaskForEpic(childGoSchool));

        System.out.println("--------------\nИзменение статусов и их вывод");
        manager.updateTask(product, "IN_PROGRESS");
        System.out.println(manager.getTaskById(100001));

        manager.updateSubtask(choise, "IN_PROGRESS");
        System.out.println(manager.getEpicById(100004));
        System.out.println(manager.getAllTaskForEpic(buildHouse));

        manager.updateSubtask(choise, "DONE");
        System.out.println(manager.getEpicById(100004));
        System.out.println(manager.getAllTaskForEpic(buildHouse));

        System.out.println("--------------\nУдаление");
        manager.removeTask(100002);
        System.out.println(manager.getTask());

        manager.removeEpic(100004);
        System.out.println(manager.getEpic());
   }
}
