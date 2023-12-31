/*
 * DisplayStringsDialog.java
 *
 * An instance of this class is used to display the strings to 
 * be memorised.  It receives the parameters (number of strings etc)
 * from the main screen and with the assistance of some helper classes
 * generate the list of strings, adds font and colour information to them,
 * and works out their sizes and the position at which each should be displayed.
 */
package uk.ac.gre.wg05.memory;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class DisplayStringsDialog extends javax.swing.JPanel {
    // class that generates the items to memorise

    private MemoryDataGenerator mdg;
    // Lists for the items to be memorised
    private List<String> stringsToMemorize;
    private List<DisplayString> displayStringsToMemorize;
    // variables for mananging the timing of the display
    private long startTime,
            currentTime,
            totalTime;
    // main form - we return to it when this screen is closed
    private MemoryAnalyserMainForm mainFrame;
    private Timer tim; // timer used to do the count down

    public DisplayStringsDialog() {
        // default values - just to test
        this(null, true, false, false, false, 40, 3, 15, 12, Color.RED);

    }

    /* Constructor
     * mainFrame - main screen to return to when this screen is
     *             finished with.
     * the 4 booleans - determine the characters to use in
     *                  generating the random strings
     * numbOfStrings - number of strings to be memorised
     * lengthOfStrings - length of each string to be memorised
     * numberOfSecs - number of seconds allowed to do the
     *                memorising
     * fontSize - point size of the font to be used to display the strings
     * fontColour - colour fo the font to be used to display the strings
     */
    public DisplayStringsDialog(MemoryAnalyserMainForm mainFrame,
            boolean useLowerCase,
            boolean useUpperCase,
            boolean useDigits,
            boolean useSpecials,
            int numbOfStrings,
            int lengthOfStrings,
            int numberOfSecs,
            int fontSize,
            Color fontColour) {
        initComponents(); // set up the GUI

        this.mainFrame = mainFrame; // keep reference so we can inform when finished

        /* Generate the strings to be memorised based on the parameters
         * specifying length, characters to use etc
         */
        try {
            mdg = new MemoryDataGenerator(
                    useUpperCase, 
                    useLowerCase,
                    useDigits,
                    useSpecials,
                    numbOfStrings,
                    lengthOfStrings);

            mdg.generateData(); // actually generate the data
            stringsToMemorize = mdg.returnListOfItemsToMemorise();

            /* Having got the strings convert them to instances of
             * DisplayString by adding information about the font, colour
             * and size.
             */
            displayStringsToMemorize = new ArrayList<DisplayString>();
            Font theFont = new Font("SERIF", Font.PLAIN, fontSize);
            FontMetrics fm = this.getFontMetrics(theFont);
            int stringHeight = fm.getHeight();
            for (String s : stringsToMemorize) {
                displayStringsToMemorize.add(new DisplayString(s, fontColour, theFont, stringHeight, fm.stringWidth(s), 0, 0));
            }

            /* Use StringPlacer to decide the locations of the strings within
             * the JPanel and also calculate the height of the JPanel needed.
             */
            StringPlacer sp = new StringPlacer(displayStringsToMemorize, 400, -1, 5, 10);
            sp.placeOnContainer();
            int containerHeight = sp.getContainerHeight();

            /* Now we have everything we need to display the strings themselves so
             * go ahead and do this using StringPanel.
             */
            panWords = new StringPanel(displayStringsToMemorize, 400, containerHeight);
            add(panWords, BorderLayout.CENTER);

            // start the clock ticking
            startTiming(numberOfSecs);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     *
     * Almost anything you need to change can be done via the "Code" tab on the
     * properties panel.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panWords = new javax.swing.JPanel();
        panControls = new javax.swing.JPanel();
        prgTimeLeft = new javax.swing.JProgressBar();
        lblSecs = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnFinished = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panWordsLayout = new javax.swing.GroupLayout(panWords);
        panWords.setLayout(panWordsLayout);
        panWordsLayout.setHorizontalGroup(
            panWordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panWordsLayout.setVerticalGroup(
            panWordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
        );

        add(panWords, java.awt.BorderLayout.CENTER);

        prgTimeLeft.setMaximum(120);
        prgTimeLeft.setValue(120);
        panControls.add(prgTimeLeft);

        lblSecs.setText("120");
        panControls.add(lblSecs);

        jLabel1.setText(" secs left");
        panControls.add(jLabel1);

        btnFinished.setText("Finished");
        btnFinished.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinishedActionPerformed(evt);
            }
        });
        panControls.add(btnFinished);

        add(panControls, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void startTiming(int numberOfSecs) {
        startTime = System.currentTimeMillis();
        totalTime = numberOfSecs * 1000;
        lblSecs.setText(numberOfSecs + "");

        /* this timer will go off every 20th of a second
         * and the timerHasPinged() method will be called
         */
        tim = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timerHasPinged();

            }
        });
        tim.start();
    }
    /* Time is up or user has clicked the finish button
     */

    private void finishedMemorising() {
        tim.stop();
        if (mainFrame != null) {
            mainFrame.finishedMemorising(mdg); // back to the main screen
        }
    }
    /* This will be called each time the timer goes off i.e. 20
     * times a second.
     */

    private void timerHasPinged() {
        currentTime = System.currentTimeMillis();

        // How long have they got left?
        long timeLeft = (totalTime - (currentTime - startTime)) + 1000;
        if (timeLeft < 0) { // out of time
            finishedMemorising();
        } else { // still time left so update the progress bar
            lblSecs.setText((timeLeft / 1000) + "");
            float fractionTimeLeft = (float) timeLeft / totalTime;
            prgTimeLeft.setValue((int) (prgTimeLeft.getMaximum() * fractionTimeLeft));
        }
    }

    private void btnFinishedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishedActionPerformed
        finishedMemorising();
    }//GEN-LAST:event_btnFinishedActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFinished;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblSecs;
    private javax.swing.JPanel panControls;
    private javax.swing.JPanel panWords;
    private javax.swing.JProgressBar prgTimeLeft;
    // End of variables declaration//GEN-END:variables

    // Main method just to demonstrate how this class is used.
    public static void main(String[] args) {
        JFrame aFrame = new JFrame();
        DisplayStringsDialog me = new DisplayStringsDialog();

        aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aFrame.getContentPane().add(me);
        aFrame.pack();
        aFrame.setVisible(true);
    }
}
