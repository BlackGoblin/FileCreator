/*
 * All rights reserved.
 * Free for personal use.
 */
package fileCreator;

import fileCreator.filePickerFrame.Messages;
import fileCreator.filePickerFrame.SelectionWindow;
import fileCreator.filePickerFrame.SelectionWindow.OnDirectorySelectedListener;
import java.io.File;
import static java.lang.System.exit;
import javax.swing.JOptionPane;

/**
 *
 * @author Reza
 */
public class FileCreator {

    // layout objects
    private static SelectionWindow selectionWindow;

    public static void main(String[] args) {

        selectionWindow = new SelectionWindow();
        selectionWindow.setInstructionMessage(Messages.SELECTION_WINDOW_MAIN_INSTRUCTION);
        selectionWindow.setMainButtonTitle(Messages.CREATE_FILE);
        selectionWindow.setVisible(true);
        selectionWindow.setOnDirectorySelectedListener(directorySelectedListener);
        
    }

    // the on directory selected listener
    private static final OnDirectorySelectedListener directorySelectedListener = new OnDirectorySelectedListener() {
        @Override
        public void onDirectorySelected(String filePath) {
            File file = new File(filePath);
            try {
                if (!file.exists()) {
                    if (!file.createNewFile()) {
                        // cant create file
                        JOptionPane.showMessageDialog(selectionWindow, Messages.ERROR_MESSAGE_CANT_CREATE_FILE, Messages.OPERATION_FAILD, JOptionPane.ERROR_MESSAGE);
                    } else {
                        // operation successful. want to continue?
                        if (JOptionPane.showConfirmDialog(selectionWindow, Messages.CONFIRM_DIALOG_MESSAGE, Messages.OPERATION_SUCCESSFUL, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.NO_OPTION) {
                            exit(0);
                            return;
                        }
                    }
                } else {
                    // the file already exists
                    JOptionPane.showMessageDialog(selectionWindow, Messages.ERROR_MESSAGE_DIRECTORY_ALREADY_EXISTS, Messages.OPERATION_FAILD, JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(selectionWindow, e.getMessage(), Messages.OPERATION_FAILD, JOptionPane.ERROR_MESSAGE);
            }
        }
    };

}
