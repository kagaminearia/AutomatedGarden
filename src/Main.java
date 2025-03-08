import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GardenAPI gardenAPI = new GardenAPI();
        gardenAPI.initialize();

        for (int day = 1; day <= Config.GARDEN_DAYS; day++) { // Simulate 30 days
            System.out.println("Day " + day);
            gardenAPI.newDay();
        }
    }
}