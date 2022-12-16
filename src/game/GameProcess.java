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

    public void start() {
        inputStartData();
        createGameObject();
        showGameField();
    }

    private void inputStartData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите стартовое количество хищников");
        while (true) {
            amountPredator = scanner.nextInt();
            if (amountPredator > 5||  amountPredator<0) {
                System.out.println("количество хищников не может быть больше 5ти");
                continue;
            }
            break;
        }
        System.out.println("Введите стартовое количество травоядных");
        while (true) {
            amountHerbivore = scanner.nextInt();
            if (amountHerbivore > 5 ) {
                System.out.println("количество травоядных не может быть больше 5ти");
                continue;
            }
            break;
        }
        System.out.println("Введите стартовое количество травы");
        while (true) {
            amountGrass = scanner.nextInt();
            if (amountGrass > 5) {
                System.out.println("количество травы не может быть больше 5ти");
                continue;
            }
            break;
        }
        System.out.println("Введите стартовое количество мяса");
        while (true) {
            amountMeat = scanner.nextInt();
            if (amountMeat > 5) {
                System.out.println("количество мяса не может быть больше 5ти");
                continue;
            }
            break;
        }
    }

    private void createGameObject() {
    createHerbivore();
    createPredator();
    createGrass();
    createMeat();
    }

    private void createPredator() {
        Random random = new Random();
        int countPredators = amountPredator;
        int x;
        int y;
        while (countPredators != 0) {
            x = random.nextInt(25);
            y = random.nextInt(25);
            if (gameField[x][y] == null) {
                Animal predator = new Predator();
                gameField[x][y] = predator;
                countPredators--;
            }
        }

    }

    private void createHerbivore() {
        Random random = new Random();
        int countHerbivore = amountHerbivore;
        int x;
        int y;
        while (countHerbivore != 0) {
            x = random.nextInt(25);
            y = random.nextInt(25);
            if (gameField[x][y] == null) {
                Animal herbivore = new Herbivore();
                gameField[x][y] = herbivore;
                countHerbivore--;
            }
        }
    }

    private void createGrass() {
        Random random = new Random();
        int countGrass = amountGrass;
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

    private void createMeat() {
        Random random = new Random();
        int countMeat = amountMeat;
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
    }


}
