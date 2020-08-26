package logic;

public enum HouseCleaning {
    VACUUMING("/vacuum"),
    SINK_WASHING("/sink"),
    DUST_OFF("/dust");

    private final String name;
    private boolean isDone;

    HouseCleaning(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public static void reset() {
        for (HouseCleaning cleaning : HouseCleaning.values()) {
            System.out.printf("Setting %s to done\n", cleaning.getName());
            cleaning.setDone(false);
        }
    }
}
