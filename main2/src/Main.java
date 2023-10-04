import java.util.Scanner;
import java.util.Random;

class StarCraftGame {
    private int minerals = 100;
    private int armySize = 0;
    private int enemyArmySize = 0;
    private String race = "";
    private int turn = 0;
    private int barracksCount = 0;
    private int healthZergling = 30;
    private int attackZergling = 3;
    private int healthMarine = 50;
    private int attackMarine = 5;
    private int healthZealot = 80;
    private int attackZealot = 8;
    private int healthEnemy = 40;
    private int enemyAttack = 4;
    public static void main(String[] args) {
        StarCraftGame game = new StarCraftGame();
        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome to Starcraft.");
        System.out.println("The objective is to prevent the enemy from advancing.");
        game.chooseRace(userInput);
        System.out.println("You start with " + game.minerals + " minerals.");
        while (game.minerals > 0 && game.armySize < 100 && game.turn < 100) {
            game.displayOptions();
            int choice = userInput.nextInt();
            game.processChoice(choice);
            game.enemyTurn();
            game.turn++;
        }
    }

    private void chooseRace(Scanner userInput) {
        System.out.println("Choose your race: [1] Terran, [2] Protoss, [3] Zerg");
        System.out.print("Enter your choice: ");
        int raceChoice = userInput.nextInt();
        if (raceChoice == 1) {
            race = "Terran";
        } else if (raceChoice == 2) {
            race = "Protoss";
        } else if (raceChoice == 3) {
            race = "Zerg";
        } else {
            System.out.println("Invalid race choice.");
            chooseRace(userInput);
        }
    }

    private void displayOptions() {
        System.out.println("Turn " + turn + " - Choose an action:");
        System.out.println("[1] Mine");
        System.out.println("[2] Train");
        System.out.println("[3] Build");
        System.out.println("[4] Attack");
        System.out.println("Your minerals: " + minerals);
        if ("Terran".equalsIgnoreCase(race)) {
            System.out.println("Your army size: " + armySize + " Marines.");
        } else if ("Protoss".equalsIgnoreCase(race)) {
            System.out.println("Your army size: " + armySize + " Zealots.");
        } else if ("Zerg".equalsIgnoreCase(race)) {
            System.out.println("Your army size: " + armySize + " Zerglings.");
        }
        System.out.println("Enemy army size: " + enemyArmySize);
    }

    private void processChoice(int choice) {
        Random random = new Random();

        switch (choice) {
            case 1:
                int minedMinerals = random.nextInt(30) + 20;
                minerals += minedMinerals;
                System.out.println("You mined " + minedMinerals + " minerals.");
                break;
            case 2:
                if (minerals >= 30) {
                    trainUnits();
                } else {
                    System.out.println("Not enough minerals to train units.");
                }
                break;
            case 3:
                if (minerals >= 50) {
                    buildBuilding();
                } else {
                    System.out.println("Not enough minerals to build a building.");
                }
                break;
            case 4:
                if (armySize > 0) {
                    int enemyStrength = random.nextInt(50) + 30;
                    battle(1);
                } else {
                    System.out.println("You need an army to attack.");
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void trainUnits() {
        Random random = new Random();

        if ("Terran".equalsIgnoreCase(race)) {
            int marines = random.nextInt(8) + 3;
            armySize += marines;
            minerals -= 30;
            System.out.println("Trained " + marines + " Marines.");
        } else if ("Protoss".equalsIgnoreCase(race)) {
            int zealots = random.nextInt(5) + 2;
            armySize += zealots;
            minerals -= 30;
            System.out.println("Warped in " + zealots + " Zealots.");
        } else if ("Zerg".equalsIgnoreCase(race)) {
            int zerglings = random.nextInt(10) + 2;
            armySize += zerglings;
            minerals -= 30;
            System.out.println("Hatched " + zerglings + " Zerglings.");
        }
    }

    private void buildBuilding() {
        Random random = new Random();
        int buildingChoice = random.nextInt(3) + 1;
        if (minerals >= 50) {
            System.out.print("Building a ");
            System.out.println("Barracks...");
            minerals -= 50;
        } else {
            System.out.println("Not enough minerals to build a building.");
        }
    }

    private void battle(double enemyAttackMultiplier) {
        Random random = new Random();
        int yourStrength = 0;
        int enemyStrength = (int) ((enemyArmySize + random.nextInt(20) - 10) * enemyAttackMultiplier);
        if ("Terran".equalsIgnoreCase(race)) {
            yourStrength = (armySize * attackMarine) + random.nextInt(15) - 5;
            armySize -= enemyStrength/healthMarine;
        }
        else if ("Protoss".equalsIgnoreCase(race)) {
            yourStrength = (armySize * attackZealot) + random.nextInt(24) - 4;
            armySize -= enemyStrength/healthZealot;
        }
        else if ("Zerg".equalsIgnoreCase(race)) {
            yourStrength = (armySize * attackZergling) + random.nextInt(9) - 3;
            armySize -= enemyStrength / healthZergling;
        }
        enemyArmySize -= yourStrength/healthEnemy;

        if (armySize >= 0) {
            System.out.println("Victory! You live another day.");
        } else {
            System.out.println("Skill issue :skull:.");
            System.exit(0);
        }
    }

    private void enemyTurn() {
        Random random = new Random();
        int enemyAction = random.nextInt(3) + 1;
        int chance = random.nextInt(100);

        if (turn % 5 == 0 && turn > 0) {
            enemyAction = 4;
        }

        if (enemyAction == 1) {
            if (chance < 80) {
                int minedMinerals = random.nextInt(20) + 10;
                minerals += minedMinerals;
                System.out.println("AI mined " + minedMinerals + " minerals.");
            } else {
                System.out.println("AI attempted to mine minerals but failed.");
            }
        } else if (enemyAction == 2) {
            if (true) {
                int enemyArmyGrowth = random.nextInt(10) + 5;
                enemyArmySize += enemyArmyGrowth;
                System.out.println("AI trained " + enemyArmyGrowth + " units.");
            } else {
                System.out.println("AI attempted to train units but failed.");
            }
        } else if (enemyAction == 3) {
            if (chance < 40) {
                int enemyBuildingChoice = random.nextInt(3) + 1;
                buildEnemyBuilding(enemyBuildingChoice);
            } else {
                System.out.println("AI attempted to build a building but failed.");
            }
        } else {
            battle(Math.random() / 10.0 * 4.0 + 0.3);
        }
    }

    private void buildEnemyBuilding(int buildingChoice) {
        Random random = new Random();

        if (minerals >= 50 && Math.random() > 0.5) {
            System.out.println("Enemy is building a barracks.");
                minerals -= 50;
            }
         else {
            System.out.println("AI does not have enough minerals to build a building.");
        }
    }
}