/*
 * All rights reserved for the author.
 * Free for personal use.
 */
package fileCreator.filePickerFrame;

/**
 * This class represents a option record to write into or read from the
 * preferences file.
 *
 * @author Reza
 */
public class PreferencesOptionRecord {

    private String name;
    private String value;

    /**
     * creates a new preferences option record
     * @param name the name of the record
     * @param value the value of the record
     */
    public PreferencesOptionRecord(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}
