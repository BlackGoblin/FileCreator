/*
 * All rights reserved for the author.
 * Free for personal use.
 */
package fileCreator.filePickerFrame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class creates a complete window to choose a directory and a file name.
 * This values can be accessed for another class while all the complexity is
 * handled here.
 *
 * @author Reza
 */
public class SelectionWindow extends JFrame {

    // layout objects
    private final FlowLayout windowLayout;
    private final JLabel instructionMessageLabel;
    private final JFileChooser fileChooser;
    private final JTextField directoryTextField;
    private final JButton chooseDirectoryButton;
    private final JTextField fileNameTextField;
    private final JButton mainButton;

    private final JMenuBar menuBar;
    private final JMenu optionsMenu;
    private final JCheckBoxMenuItem preferencesCheckBoxMenuItem;
    private final JMenuItem defaultPreferencesMenuItem;

    private final JPanel preferencesPanel;
    private final JTextField defaultDirectoryTextField;
    private final JTextField defaultFileNameTextField;

    // data objects
    private File file;
    private PreferencesFileHelper preferencesFileHelper;
    private Dimension instructionMessageLabelDimension;
    private Dimension textFieldDimension;
    private Dimension buttonDimension;

    // listener objects
    private OnDirectorySelectedListener directorySelectedListener;

    public SelectionWindow() {
        super(Messages.SELECTION_WINDOW_TITLE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));

        // initialize the frame options
        windowLayout = new FlowLayout();
        setLayout(windowLayout);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Dimensions.SELECTION_WINDOW_WIDTH, Dimensions.SELECTION_WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            Image i = ImageIO.read(getClass().getResource("icon.png"));
            setIconImage(i);
        } catch (IOException ex) {
            Logger.getLogger(SelectionWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        // initailze the objects and components
        instructionMessageLabelDimension = new Dimension(Dimensions.INSTRUCTION_MESSAGE_LABEL_STANDARD_WIDTH, Dimensions.INSTRUCTION_MESSAGE_LABEL_STANDARD_HEIGHT);
        textFieldDimension = new Dimension(Dimensions.TEXT_FIELD_STANDARD_WIDTH, Dimensions.TEXT_FIELD_STANDARD_HEIGHT);
        buttonDimension = new Dimension(Dimensions.BUTTON_STANDARD_WIDTH, Dimensions.BUTTON_STANDARD_HEIGHT);

        instructionMessageLabel = new JLabel();
        instructionMessageLabel.setText(Messages.INSTRUCTION_MESSAGE_DEFAULT);
        instructionMessageLabel.setPreferredSize(instructionMessageLabelDimension);

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        directoryTextField = new JTextField(Dimensions.DIRECTORY_TEXT_FIELD_COLUMN_SIZE);
        directoryTextField.setToolTipText(Messages.DIRECTORY_TEXT_FIELD_TOOLTIP);
        directoryTextField.setPreferredSize(textFieldDimension);

        chooseDirectoryButton = new JButton(Messages.CHOOSE_DIRECTORY_BUTTON);
        chooseDirectoryButton.setPreferredSize(buttonDimension);

        fileNameTextField = new JTextField(Messages.UNTITLED_FILE_NAME, Dimensions.FILE_NAME_TEXT_FIELD_COLUMN_SIZE);
        fileNameTextField.setToolTipText(Messages.FILE_NAME_TEXT_FIELD_TOOLTIP);
        fileNameTextField.setPreferredSize(textFieldDimension);

        mainButton = new JButton(Messages.OK);
        mainButton.setPreferredSize(buttonDimension);

        // initialize menu objects
        menuBar = new JMenuBar();
        optionsMenu = new JMenu(Messages.MENU_EDIT);
        preferencesCheckBoxMenuItem = new JCheckBoxMenuItem(Messages.MENU_ITEM_PREFRENCES_CHECK_BOX);
        preferencesCheckBoxMenuItem.setState(false);
        defaultPreferencesMenuItem = new JMenuItem(Messages.MENU_ITEM_PREFERENCES_DEFAULTS, KeyEvent.VK_P);
        defaultPreferencesMenuItem.setEnabled(false);

        defaultDirectoryTextField = new JTextField(20);
        defaultFileNameTextField = new JTextField(20);

        preferencesPanel = new JPanel(new FlowLayout());
        preferencesPanel.setPreferredSize(new Dimension(Dimensions.PREFRENCES_DIALOG_WIDTH, Dimensions.PREFRENCES_DIALOG_HEIGHT));
        preferencesPanel.add(new JLabel("Default directory: "));
        preferencesPanel.add(defaultDirectoryTextField);
        preferencesPanel.add(Box.createVerticalStrut(15));
        preferencesPanel.add(new JLabel("Default file name: "));
        preferencesPanel.add(defaultFileNameTextField);

        // get the directory
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            directoryTextField.setText(file.getAbsolutePath());
        }

        // add menu objects
        optionsMenu.add(preferencesCheckBoxMenuItem);
        optionsMenu.add(defaultPreferencesMenuItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);

        // add the components and set the layout visible
        add(instructionMessageLabel);
        add(directoryTextField);
        add(chooseDirectoryButton);
        add(fileNameTextField);
        add(mainButton);

        preferencesFileHelper = PreferencesFileHelper.getInstance();
        if (preferencesFileHelper.exists()) {
            // preferences file exists
            
            // get the cheched status
            PreferencesOptionRecord preferencesOptionRecord = preferencesFileHelper.readPrefrencesOptionRecord(Messages.PREFRENCES_RECORD);
            if (preferencesOptionRecord != null) {
                if (preferencesOptionRecord.getValue().equals(Messages.PREFRENCES_CHECKED)) {
                    preferencesCheckBoxMenuItem.setState(true);
                    defaultPreferencesMenuItem.setEnabled(true);
                } else {
                    preferencesCheckBoxMenuItem.setState(false);
                    defaultPreferencesMenuItem.setEnabled(false);
                }
            }
            
            // get the dfualt diretory
            preferencesOptionRecord = preferencesFileHelper.readPrefrencesOptionRecord(Messages.PREFRENCES_DIRECTORY);
            if (preferencesOptionRecord != null) {
                directoryTextField.setText(preferencesOptionRecord.getValue());
            } else {
                directoryTextField.setText("");
            }
            
            // get the default file name
            preferencesOptionRecord = preferencesFileHelper.readPrefrencesOptionRecord(Messages.PREFRENCES_FILE);
            if (preferencesOptionRecord != null) {
                fileNameTextField.setText(preferencesOptionRecord.getValue());
            } else {
                fileNameTextField.setText(Messages.UNTITLED_FILE_NAME);
            }
            
        } else {
            // preferences file does not exists
            preferencesCheckBoxMenuItem.setState(false);
            defaultPreferencesMenuItem.setEnabled(false);

        }

        // set the listeners
        chooseDirectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the directory
                if (fileChooser.showOpenDialog(SelectionWindow.this) == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    directoryTextField.setText(file.getAbsolutePath());
                }
            }
        });

        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the directory and file name
                if (directorySelectedListener != null) {
                    String filePath = new String();

                    // check for input errors
                    if (!(directoryTextField.getText().length() > 0) && !(fileNameTextField.getText().length() > 0)) {
                        JOptionPane.showMessageDialog(SelectionWindow.this, Messages.ERROR_MESSAGE_DIRECTORY_AND_FILE_NAME_NOT_SELECTED, Messages.INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
                        directoryTextField.requestFocus();
                        return;
                    } else if (!(directoryTextField.getText().length() > 0)) {
                        JOptionPane.showMessageDialog(SelectionWindow.this, Messages.ERROR_MESSAGE_DIRECTORY_NOT_SELECTED, Messages.INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
                        directoryTextField.requestFocus();
                        return;
                    } else if (!(fileNameTextField.getText().length() > 0)) {
                        JOptionPane.showMessageDialog(SelectionWindow.this, Messages.ERROR_MESSAGE_FILE_NAME_NOT_SELECTED, Messages.INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
                        fileNameTextField.requestFocus();
                        return;
                    }

                    filePath = directoryTextField.getText() + "/" + fileNameTextField.getText();
                    directorySelectedListener.onDirectorySelected(filePath);
                }
            }
        });

        preferencesCheckBoxMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (preferencesCheckBoxMenuItem.getState()) {
                    defaultPreferencesMenuItem.setEnabled(true);
                } else {
                    defaultPreferencesMenuItem.setEnabled(false);
                }
            }
        });

        defaultPreferencesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(SelectionWindow.this, preferencesPanel, "Default preferences", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {

                }
            }
        });

    }

    /**
     * sets the text for instruction message of the frame
     *
     * @param instructionMessage the instruction message of the frame
     */
    public void setInstructionMessage(String instructionMessage) {
        instructionMessageLabel.setText(instructionMessage);
    }

    public void setMainButtonTitle(String title) {
        mainButton.setText(title);
    }

    /**
     * sets a listener for when the directory is selected
     *
     * @param listener a listener that gets called when the directory is
     * selected
     */
    public void setOnDirectorySelectedListener(OnDirectorySelectedListener listener) {
        directorySelectedListener = listener;
    }

    /**
     * listener for file path input
     */
    public interface OnDirectorySelectedListener {

        /**
         * gets called when the user hits create button
         *
         * @param filePath the path to the file user wants to create
         */
        public void onDirectorySelected(String filePath);
    }

}
