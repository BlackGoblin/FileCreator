/*
 * All rights reserved for the author.
 * Free for personal use.
 */
package fileCreator.filePickerFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * It's a helper class to deal with preferences file and its functions This
 * class uses singleton method
 *
 * @author Reza
 */
public class PreferencesFileHelper {

    /* the instance of this class */
    private static PreferencesFileHelper instance;

    /* the reference to the preferences file */
    private final File prefrencesFile;

    /**
     * the private constructor, builds a reference to the preferences file. Use
     * the static method getInstance() instead
     */
    private PreferencesFileHelper() {
        prefrencesFile = new File(Messages.PREFERENCES_FILE_NAME);
    }

    /**
     * This methods returns an instance of this class
     *
     * @return a PreferencesFileHelper object
     */
    public static PreferencesFileHelper getInstance() {
        if (instance == null) {
            instance = new PreferencesFileHelper();
        }
        return instance;
    }

    /**
     * checks to see if the preference file exists
     *
     * @return true if the file exists, false if it doesn't
     */
    public boolean exists() {
        return prefrencesFile.exists();
    }

    /**
     * creates a new preferences file if it does not already exits
     *
     * @return true if the operation was successful, false if not
     */
    public boolean createPrefrencesFile(){
        boolean success = false;
        try {
            prefrencesFile.createNewFile();
            success = true;
        } catch (IOException ex) {
            Logger.getLogger(PreferencesFileHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    /**
     * puts a preferences option record to the preferences file
     *
     * @param preferencesOptionRecord
     * @return true if the operation was successful, false if not
     */
    public boolean writePreferencesOptionRecord(PreferencesOptionRecord preferencesOptionRecord) {
        boolean success = false;
        try (Formatter formatter = new Formatter(prefrencesFile)) {
            formatter.format("%s %s", preferencesOptionRecord.getName(), preferencesOptionRecord.getValue());
            formatter.close();
            success = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PreferencesFileHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    /**
     * reads and returns a specific preferences option record from the preferences file
     * 
     * @param preferencesOptionRecordName the name of the preferences option record
     * @return a PreferencesOptionRecord object if the preferences option record exists
     */
    public PreferencesOptionRecord readPrefrencesOptionRecord(String preferencesOptionRecordName) {
        try (Scanner scanner = new Scanner(prefrencesFile)) {
            while (scanner.hasNext()) {
                String name = scanner.next();
                String value = scanner.next();
                if (name.equals(preferencesOptionRecordName)) {
                    scanner.close();
                    return new PreferencesOptionRecord(name, value);
                }
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PreferencesFileHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * updates a specific preferences option record from the preferences file
     * 
     * @param preferencesOptionRecord the record that should be updated, notice that it should contain its new value
     * @return true if the operation was successful, false if not
     */
    public boolean updatePreferencesOptionRecord(PreferencesOptionRecord preferencesOptionRecord) {
        boolean success = false;
        
        return success;
    }
}
