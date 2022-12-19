package game;

import animal.Animal;
import animal.Herbivore;
import animal.Predator;
import food.Food;
import food.Grass;
import food.Meat;

import java.util.Random;
import java.util.Scanner;

public class GameProcess {
    private Object[][] gameField = new Object[25][25];

    private Integer amountPredator;
    private Integer amountHerbivore;
    private Integer amountGrass;
    private Integer amountMeat;

    public void start() throws InterruptedException {
        inputStartData();
        createGameObject();
        showGameField();

        while (endGame()) {
            moveAnimal();
            hungryDie();
            showGameField();
            generateFood();
            recuperation();
            Thread.sleep(3000);
        }


    }

    /**
     * метод реализует логику ввода данных стартового количества игровых объектов
     */
    private void inputStartData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите стартовое количество хищников");
        while (true) {
            amountPredator = scanner.nextInt();
            if (amountPredator > 5 || amountPredator < 1) {
                System.out.println("количество хищников не может быть больше 5ти");
                continue;
            }
            break;
        }
        System.out.println("Введите стартовое количество травоядных");
        while (true) {
            amountHerbivore = scanner.nextInt();
            if (amountHerbivore > 5 || amountHerbivore < 1) {
                System.out.println("количество травоядных не может быть больше 5ти");
                continue;
            }
            break;
        }
        System.out.println("Введите стартовое количество травы");
        while (true) {
            amountGrass = scanner.nextInt();
            if (amountGrass > 5 || amountGrass < 1) {
                System.out.println("количество травы не может быть больше 5ти");
                continue;
            }
            break;
        }
        System.out.println("Введите стартовое количество мяса");
        while (true) {
            amountMeat = scanner.nextInt();
            if (amountMeat > 5 || amountMeat < 1) {
                System.out.println("количество мяса не может быть больше 5ти");
                continue;
            }
            break;
        }
    }

    /**
     * метод реализует логику стартовой генерации объектов классов в случайном месте игрового поля gameField
     */
    private void createGameObject() {
        createHerbivore();
        createPredator();
        createGrass();
        createMeat();
    }

    /**
     *метод создает введенное количество объектов класса Хищник в рандомном месте
     */
    private void createPredator() {
        Random random = new Random();
        int countPredators = amountPredator;
        int x;
        int y;
        while (countPredators > 0) {
            x = random.nextInt(25);
            y = random.nextInt(25);
            if (gameField[x][y] == null) {
                Animal predator = new Predator();
                gameField[x][y] = predator;
                countPredators--;
            }
        }

    }

    /**
     *метод создает введенное количество объектов класса Травоядные в рандомном месте
     */
    private void createHerbivore() {
        Random random = new Random();
        int countHerbivore = amountHerbivore;
        int x;
        int y;
        while (countHerbivore > 0) {
            x = random.nextInt(25);
            y = random.nextInt(25);
            if (gameField[x][y] == null) {
                Animal herbivore = new Herbivore();
                gameField[x][y] = herbivore;
                countHerbivore--;
            }
        }
    }

    /**
     *метод реализует логику стартовой генерации объектов класса Grass в случайном месте игрового поля
     */
     private void createGrass() {
        Random random = new Random();
        int countGrass = amountGrass;
        int x;
        int y;
        while (countGrass > 0) {
            x = random.nextInt(25);
            y = random.nextInt(25);
            if (gameField[x][y] == null) {
                Food grass = new Grass();
                gameField[x][y] = grass;
                countGrass--;
            }
        }
    }

    /**
     *метод создает введенное количество объектов класса Мяса в случайном месте игрового поля
     */
    private void createMeat() {
        Random random = new Random();
        int countMeat = amountMeat;
        int x;
        int y;
        while (countMeat > 0) {
            x = random.nextInt(25);
            y = random.nextInt(25);
            if (gameField[x][y] == null) {
                Food meat = new Meat();
                gameField[x][y] = meat;
                countMeat--;
            }
        }
    }

    /**
     *  метод реализует логику по перемещению в игровом поле объектов класса Animal в радиусе одной клетки
     */
    private void moveAnimal() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                if (gameField[i][j] instanceof Predator && !((Predator) gameField[i][j]).isTired()) {
                    ((Predator) gameField[i][j]).setTired(true);
                    movePredator(i, j);
                }
                if (gameField[i][j] instanceof Herbivore && !((Herbivore) gameField[i][j]).isTired()) {
                    ((Herbivore) gameField[i][j]).setTired(true);
                    moveHerbivore(i, j);
                }
            }
        }
    }

    /**
     * метод реализует логику по перемещению в игровом поле объектов класса Хищник в радиусе одной клетки
     */
    private void movePredator(int x, int y) {
        Random random = new Random();
        if (!eatPredator(x, y)) {
            while (true) {
                int a = random.nextInt((x + 2) - (x - 1)) + x - 1;
                int b = random.nextInt((y + 2) - (y - 1)) + y - 1;
                if (a < 0 || b < 0 || a > gameField.length - 1 || b > gameField.length - 1) {
                    continue;
                }
                if (gameField[a][b] == null) {
                    gameField[a][b] = gameField[x][y];
                    gameField[x][y] = null;
                    ((Animal) gameField[a][b]).starve();
                    break;
                }

            }
        }
    }

    /**
     * метод передвигает объекты класса Травоядные в радиусе одной клетки
     * @param x
     * @param y
     */
    private void moveHerbivore(int x, int y) {
        Random random = new Random();
        if (!eatHerbivore(x, y)) {
            while (true) {
                int a = random.nextInt((x + 2) - (x - 1)) + x - 1;
                int b = random.nextInt((y + 2) - (y - 1)) + y - 1;
                if (a < 0 || b < 0 || a > gameField.length - 1 || b > gameField.length - 1) {
                    continue;
                }
                if (gameField[a][b] == null) {
                    gameField[a][b] = gameField[x][y];
                    gameField[x][y] = null;
                    ((Animal) gameField[a][b]).starve();
                    break;
                }
            }
        }
    }

    /**
     * метод имитурует питание Травоядных в радиусе 2 клеток
     * @param x
     * @param y
     * @return
     */
    private boolean eatHerbivore(int x, int y) {

        for (int i = x - Animal.VISIBILITY; i <= x + Animal.VISIBILITY; i++) {
            for (int j = y - Animal.VISIBILITY; j <= y + Animal.VISIBILITY; j++) {
                if (i < 0 || j < 0 || i > gameField.length - 1 || j > gameField.length - 1) {
                    continue;
                }
                if (gameField[i][j] instanceof Grass) {
                    gameField[i][j] = gameField[x][y];
                    ((Animal) (gameField[i][j])).eat();
                    gameField[x][y] = null;
                    multiplyAnimal(i, j);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * метод имитурует питание Хищников в радиусе 2 клеток
     * @param x
     * @param y
     * @return
     */
    private boolean eatPredator(int x, int y) {

        for (int i = x - Animal.VISIBILITY; i < x + Animal.VISIBILITY; i++) {
            for (int j = y - Animal.VISIBILITY; j < y + Animal.VISIBILITY; j++) {
                if (i < 0 || j < 0 || i > gameField.length - 1 || j > gameField.length - 1) {
                    continue;
                }
                if (gameField[i][j] instanceof Herbivore || gameField[i][j] instanceof Meat) {
                    if (gameField[i][j] instanceof Herbivore) {
                        amountHerbivore--;
                    }
                    gameField[i][j] = gameField[x][y];
                    ((Animal) (gameField[i][j])).eat();
                    gameField[x][y] = null;
                    multiplyAnimal(i, j);
                    return true;
                }
            }
        }
        return false;
    }


    private void hungryDie() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                if (gameField[i][j] instanceof Animal) {
                    if (((Animal) gameField[i][j]).getLife() == 0) {
                        if (gameField[i][j] instanceof Predator) {
                            amountPredator--;
                        } else if (gameField[i][j] instanceof Herbivore) {
                            amountHerbivore--;
                        }
                        gameField[i][j] = null;
                    }
                }
            }
        }
    }

    /**
     * завершает игру если животных не осталось
     * @return
     */
    private boolean endGame() {
        if (amountHerbivore <= 0) {
            System.out.println("Игра закончена. Хищники победили!");
            return false;
        }
        if (amountPredator <= 0) {
            System.out.println("Игра закончена. Травоядные победили!");
            return false;
        }
        return true;
    }

    /**
     *
     * @param x
     * @param y
     */
    private void multiplyAnimal(int x, int y) {
        Random random = new Random();
        boolean checkMultiply = random.nextBoolean();
        if (checkMultiply) {
            Animal animal;
            if (gameField[x][y] instanceof Predator) {
                animal = new Predator();
                animal.setTired(true);
                amountPredator++;
            } else {
                animal = new Herbivore();
                animal.setTired(true);
                amountHerbivore++;
            }
            while (true) {
                int a = random.nextInt((x + 2) - (x - 1)) + x - 1;
                int b = random.nextInt((y + 2) - (y - 1)) + y - 1;
                if (a < 0 || b < 0 || a > gameField.length - 1 || b > gameField.length - 1) {
                    continue;
                }
                if (gameField[a][b] == null) {
                    gameField[a][b] = animal;
                    break;
                }
            }
        }

    }


    /**
     * каждый ход генеруется некое количество каждого вида еды
     */
    private void generateFood() {
        generateMeat();
        generateGrass();
    }

    /**
     * каждый ход генеруется некое количество травы
     */
    private void generateGrass() {

        Random random = new Random();
        int countGrass = 5;
        int x;
        int y;
        while (countGrass != 0) {
            x = random.nextInt(25);
            y = random.nextInt(25);
            if (gameField[x][y] == null) {
                Food grass = new Grass();
                gameField[x][y] = grass;
                countGrass--;
            }
        }
    }

    /**
     * каждый ход генеруется некое количество мяса
     */
    private void generateMeat() {
        Random random = new Random();
        int countMeat = 5;
        int x;
        int y;
        while (countMeat != 0) {
            x = random.nextInt(25);
            y = random.nextInt(25);
            if (gameField[x][y] == null) {
                Food meat = new Meat();
                gameField[x][y] = meat;
                countMeat--;
            }
        }
    }

    private void recuperation() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                if (gameField[i][j] instanceof Animal) {
                    ((Animal) gameField[i][j]).setTired(false);
                }
            }
        }
    }

    /**
     * метод выводит на экран
     */
    private void showGameField() {

        for (Object[] objects : gameField) {
            for (Object object : objects) {
                if (object == null) {
                    System.out.print("[ ]");
                } else {
                    System.out.print("[" + object + "]");
                }
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("Хищников " + amountPredator + " Травоядных " + amountHerbivore);
    }


}
