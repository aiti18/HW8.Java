import java.util.Random;

abstract class Hero {
    protected String name;
    protected int attack;
    protected int health;

    public Hero() {
        // Конструктор по умолчанию
    }

    public abstract void useAbility(Boss boss, Hero[] team);

    public void increaseAttack(int value) {
        this.attack += value;
        System.out.println(name + " увеличил атаку на " + value + ". Новая атака: " + attack);
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        System.out.println(name + " получил " + damage + " урона. Осталось здоровья: " + health);
    }
}

class Magic {
    private int attackIncrease;

    public Magic(int attackIncrease) {
        this.attackIncrease = attackIncrease;
    }

    public void increaseTeamAttack(Hero[] team) {
        for (Hero hero : team) {
            hero.increaseAttack(attackIncrease);
        }
    }
}

class Boss {
    private int health;
    private int attack;

    public Boss(int health, int attack) {
        this.health = health;
        this.attack = attack;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        System.out.println("Босс получил " + damage + " урона. Осталось здоровья: " + health);
    }

    public void increaseAggression() {
        this.attack *= 1.5;
        System.out.println("Агрессия босса увеличилась! Новая атака: " + attack);
    }

    public int getHealth() {
        return health;
    }
}

class Avrora extends Hero {
    private boolean invisibleUsed = false;
    private boolean invisible = false;

    public Avrora() {
        this.name = "Avrora";
        this.attack = 10;
        this.health = 100;
    }

    @Override
    public void useAbility(Boss boss, Hero[] team) {
        if (!invisibleUsed) {
            invisible = true;
            invisibleUsed = true;
            System.out.println(name + " вошла в режим невидимости на 2 раунда!");
        }
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void exitInvisibility() {
        invisible = false;
        System.out.println(name + " вышла из режима невидимости.");
    }
}

class Druid extends Hero {
    private boolean helperSummoned = false;

    public Druid() {
        initializeHero();
    }

    private void initializeHero() {
        this.name = "Druid";
        this.attack = 8;
        this.health = 90;
    }

    @Override
    public void useAbility(Boss boss, Hero[] team) {
        if (!helperSummoned) {
            helperSummoned = true;
            if (Math.random() > 0.5) {
                System.out.println(name + " призвал ангела, увеличивая способность медика!");
            } else {
                System.out.println(name + " призвал ворона! Босс стал агрессивнее.");
                boss.increaseAggression();
            }
        }
    }
}

class TrickyBastard extends Hero {
    private boolean playingDead = false;

    public TrickyBastard() {
        setName("TrickyBastard");
        setAttack(12);
        setHealth(95);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void useAbility(Boss boss, Hero[] team) {
        if (!playingDead) {
            playingDead = true;
            System.out.println(name + " притворяется мертвым на этот раунд.");
        }
    }

    public boolean isPlayingDead() {
        return playingDead;
    }

    public void resetPlayingDead() {
        playingDead = false;
    }
}

class Antman extends Hero {
    private boolean sizeChanged = false;

    public Antman() {
        this.name = "Antman";
        this.attack = 15;
        this.health = 120;
    }

    @Override
    public void useAbility(Boss boss, Hero[] team) {
        if (!sizeChanged) {
            sizeChanged = true;
            if (Math.random() > 0.5) {
                System.out.println(name + " увеличился в размере! Атака и здоровье увеличены.");
                attack *= 2;
                health *= 2;
            } else {
                System.out.println(name + " уменьшился в размере! Атака и здоровье уменьшены.");
                attack /= 2;
                health /= 2;
            }
        }
    }

    public void resetSize() {
        sizeChanged = false;
    }
}

class Deku extends Hero {
    public Deku() {
        this.name = "Deku";
        this.attack = 20;
        this.health = 110;
    }

    @Override
    public void useAbility(Boss boss, Hero[] team) {
        Random rand = new Random();
        int[] powerOptions = {20, 50, 100};
        int chosenPower = powerOptions[rand.nextInt(powerOptions.length)];
        attack += attack * (chosenPower / 100.0);
        health -= chosenPower / 2;
        System.out.println(name + " увеличил свою силу на " + chosenPower + "%! Новая атака: " + attack + ". Потеряно здоровья: " + (chosenPower / 2));
    }
}

public class Main {
    public static void main(String[] args) {
        Boss boss = new Boss(500, 50);

        Hero avrora = new Avrora();
        Hero druid = new Druid();
        Hero trickyBastard = new TrickyBastard();
        Hero antman = new Antman();
        Hero deku = new Deku();

        Hero[] team = {avrora, druid, trickyBastard, antman, deku};

        Magic magic = new Magic(5);

        for (int round = 1; round <= 5; round++) {
            System.out.println("\n--- Раунд " + round + " ---");

            for (Hero hero : team) {
                hero.useAbility(boss, team);
            }

            magic.increaseTeamAttack(team);
        }
    }
}
