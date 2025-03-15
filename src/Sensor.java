public class Sensor {
    /*
    public int getTemp(Garden garden){
        return garden.getTemperature();
    }

    public int getWater(Garden garden){
        return garden.getRainAmount();
    }

     */

    public int getTemp(Area area){
        return area.getCurrentTemp();
    }

    public int getWater(Area area){
        return area.getCurrentWater();
    }

    public String[] getPest(Area area){
        return area.getInsects();
    }


}
