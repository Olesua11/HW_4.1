import java.util.Random;
// Доп задание без ошибок никак не получается, завтра постараюсь его сделать)
public class Main {
    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {250, 500, 150, 250};
    public static int[] heroesDamage = {20, 15, 10, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Healer"};
    public static int roundNumber = 0;
    public static boolean medicHealing = true;

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        healHeroes();
        bossAttack();
        heroesAttack();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
            }
        }
    }


    /**
     *public static void healHeroes() {
     *     if (medicHealing && heroesHealth[3] > 0) {
     *         for (int i = 0; i < heroesHealth.length - 1; i++) {
     *             if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
     *                 heroesHealth[i] += 70;
     *                 System.out.println("Medic healed " + heroesAttackType[i] + " for 70 HP.");
     *             }
     *         }
     *     }
     * } в задании указано что медик должен лечить только одного героя у которого хп меньше 100, (я так и зделала), в комментарии
     * я написала чтобы медик лечил всех у кого хп меньше 100,если тот вариан не правильный)).
     */



    public static void healHeroes() {
        if (medicHealing && heroesHealth[3] > 0) {
            int targetHero = -1;
            for (int i = 0; i < heroesHealth.length - 1; i++) {
                if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    targetHero = i;
                    break;
                }
            }
            if (targetHero != -1) {
                heroesHealth[targetHero] += 70;
                System.out.println("Medic healed " + heroesAttackType[targetHero] + " for 70 HP.");
            }
        }
    }


    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length - 1; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
}
