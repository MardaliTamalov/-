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

            Thread.sleep(3000);
        }


    }

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

    private void moveAnimal() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                if (gameField[i][j] instanceof Predator) {
                    movePredator(i, j);
                }
            }
        }
    }

    private void movePredator(int x, int y) {
        Random random = new Random();
        if (!eatPredator(x, y)) {
            while (true) {
                int a = random.nextInt((x + 1) - (x - 1)) + x - 1;
                int b = random.nextInt((y + 1) - (y - 1)) + y - 1;
                if(a<0 || b<0 || a>gameField.length-1 || b>gameField.length-1){
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

    private boolean eatPredator(int x, int y) {

        for (int i = x - Animal.VISIBILITY; i < x + Animal.VISIBILITY; i++) {
            for (int j = y - Animal.VISIBILITY; j < y + Animal.VISIBILITY; j++) {
                if (i < 0 || j < 0 || i > gameField.length - 1 || j > gameField.length - 1) {
                    continue;
                }
                if (gameField[i][j] instanceof Herbivore || gameField[i][j] instanceof Meat) {
                    gameField[i][j] = gameField[x][y];
                    amountHerbivore--;
                    ((Animal) (gameField[i][j])).eat();
                    gameField[x][y] = null;
                    return true;
                }
            }
        }
        return false;
    }
    private void hungryDie(){
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                if (gameField[i][j] instanceof Animal) {
                    if(((Animal) gameField[i][j]).getLife()==0){
                        if(gameField[i][j] instanceof Predator){
                            amountPredator--;
                        }
                        else if(gameField[i][j] instanceof Herbivore){
                            amountHerbivore--;
                        }
                        gameField[i][j]=null;
                    }
                }
            }
        }
    }

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
