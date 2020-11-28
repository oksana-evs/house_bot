package logic;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public enum HouseCleaning {
    VACUUMING("/vacuum"),
    SINK_WASHING("/sink"),
    HOB("/hob"),
    WC("/wc"),
    DUST_OFF("/dust");

    private final String name;

    public static final String FILE_NAME = "state.properties";
    private static final Logger LOGGER = Logger.getLogger(HouseCleaning.class.getName());

    HouseCleaning(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        String done = "false";
        Properties props = new Properties();
        try (FileReader reader = new FileReader(FILE_NAME)) {
            props.load(reader);
            done = props.getProperty(name, String.valueOf(false));
        } catch (IOException exception) {
            LOGGER.severe(exception.getMessage());
        }
        return Boolean.parseBoolean(done);
    }

    public void setDone(boolean done) {
        Properties props = new Properties();
        try (FileReader reader = new FileReader(FILE_NAME)) {
            props.load(reader);
            props.setProperty(name, String.valueOf(done));
        } catch (IOException exception) {
            LOGGER.severe(exception.getMessage());
        }
        try (FileOutputStream out = new FileOutputStream(FILE_NAME)) {
            props.setProperty(name, String.valueOf(done));
            props.store(out, null);
        } catch (IOException exception) {
            LOGGER.severe(exception.getMessage());
        }
    }

    public static void reset() {
        for (HouseCleaning cleaning : HouseCleaning.values()) {
            LOGGER.info(String.format("Setting %s to not done\n", cleaning.getName()));
            cleaning.setDone(false);
        }
    }
}
