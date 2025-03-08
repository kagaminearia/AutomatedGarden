import java.util.Arrays;
import java.util.List;

class Plant implements Comparable<Plant> {
    private String name; // plant name
    private int tempLowbound; // if temperature lower than this value, it would hurt plant
    private int tempUpperbound; // if temperature higher than this value, it would hurt plant
    private String[] parasites; // insects that would hurt plant
    private int waterLowbound; // if plant get water that lower than this value, it would hurt plant
    private int waterUpperbound; // if plant get water that higher than this value, it would hurt plant
    private int health; // plant health, will decrease when the environment violate the requirement
    private boolean isAlive; // if plant's health decrease to 0, set isAlive false

    public Plant(String name,int tempLowbound, int tempUpperbound, String[] parasites, int waterLowbound, int waterUpperbound){
        this.name = name;
        this.tempLowbound = tempLowbound;
        this.tempUpperbound = tempUpperbound;
        this.parasites = parasites;
        this.waterLowbound = waterLowbound;
        this.waterUpperbound = waterUpperbound;
        this.health = Config.PLANT_HEALTH;
        this.isAlive = true;
    }

    public boolean ifAlive(){
        return this.isAlive;
    }

    // input current garden's environment and calculate if it affects the plant
    // if affect, decrease plant's health
    // if not affect and if health is not full, add one value to health value
    void impact(int temperature, int waterAmount, String[] insect) {
        if (!isAlive) return;

        if(!meetTemperatureCondition(temperature)) health--;
        if(!meetWaterCondition(waterAmount)) health--;
        int currParasite = parasiteNum(insect);
        if (currParasite>0) health -= currParasite;

        // If health is not full and no negative impact, increase health
        if (health < Config.PLANT_HEALTH &&
                temperature >= tempLowbound && temperature <= tempUpperbound &&
                waterAmount >= waterLowbound && waterAmount <= waterUpperbound &&
                currParasite==0) {
            health++;
        }
        // Check if plant is dead
        if (health <= 0) {
            isAlive = false;
        }
    }

    // checking environment functions
    private boolean meetTemperatureCondition(int temp){
        if (temp < tempLowbound || temp > tempUpperbound) {
            return false;
        }
        return true;
    }

    private boolean meetWaterCondition(int water){
        if (water < waterLowbound || water > waterUpperbound) {
            return false;
        }
        return true;
    }

    private int parasiteNum(String[] insects) {
        int num = 0;
        List<String> list = Arrays.asList(parasites);
        for(String insect: insects){
            if (list.contains(insect)) num++;
        }
        return num;
    }

    public int getHealth() {
        return this.health;
    }

    @Override
    public int compareTo(Plant other) {
        return Integer.compare(this.health, other.health);
    }

    @Override
    public String toString() {
        return "Plant name: " + name + " Remaining life: " + health;
    }

}
