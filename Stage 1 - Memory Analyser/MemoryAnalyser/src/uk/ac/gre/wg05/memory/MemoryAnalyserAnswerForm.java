/*
 * MemoryAnalyserAnswerForm.java
 *
 * This screen is displayed after the user has
 * finished attempting to memorise the items.
 * It displays of list of items that includes those that
 * the user had to memorise (i.e. a superset of it).  
 * When the user has finished selecting from the list
 * it checks their answer and displays the result.
 */
package uk.ac.gre.wg05.memory;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MemoryAnalyserAnswerForm extends javax.swing.JPanel {

    /* MyCellRenderer is an inner class is used to display the items in the list in
     * different colours when the user has finished making
     * their selection.  Red items are those that they selected but
     * weren't correct, green items are those that they didn't
     * select but should have.
     */
    static class MyCellRenderer extends JLabel implements ListCellRenderer {

        private boolean[] isCorrect; // array indicating which items
        // in the list the user got correct.

        public void setCorrectArray(boolean[] isCorrect) {
            this.isCorrect = isCorrect;
        }

        // getListCellRendererComponent is the only method defined by ListCellRenderer.
        // It just reconfigures the JLabel each time it is called.
        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value, // value to display
                int index, // cell index
                boolean isSelected, // is the cell selected
                boolean cellHasFocus) {   // the list and the cell have the focus
            String s = value.toString();
            setText(s);
            Font font = list.getFont();
            setFont(font);
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            if (index >= isCorrect.length) {// item that user didn't select but should have
                this.setForeground(new Color(0, 150, 0)); // green
                // make italic
                this.setFont(new Font(font.getFamily(), Font.ITALIC, font.getSize()));
            } else if (!isCorrect[index]) { // item that user did select but shouldn't have
                this.setForeground(new Color(200, 0, 0)); // red
                // make bold
                this.setFont(new Font(font.getFamily(), Font.BOLD, font.getSize()));
            }
            setEnabled(list.isEnabled());

            setOpaque(true);
            return this;
        }
    } // end of inner class MyCellRenderer
    // ListModels for the two JLists
    private DefaultListModel chooseModel, yourAnswerModel;
    private MemoryDataGenerator mdg;
    private Object[] arrayOfItemsToChooseFrom;

    public MemoryAnalyserAnswerForm() {
        this(null, null);

    }

    public MemoryAnalyserAnswerForm(MemoryAnalyserMainForm mainForm,
            MemoryDataGenerator mdg) {
        initComponents(); // set up GUI
        this.mdg = mdg;

        /* Get the list of items the user is to choose from.
         * This is a superset of the list they originally were
         * given to memorise.
         */
        arrayOfItemsToChooseFrom = mdg.returnListOfItemsToChooseFrom().toArray();
        Arrays.sort(arrayOfItemsToChooseFrom);
        chooseModel = new DefaultListModel();
        yourAnswerModel = new DefaultListModel();
        lstItemsToChooseFrom.setModel(chooseModel);
        lstYourAnswer.setModel(yourAnswerModel);
        for (Object item : arrayOfItemsToChooseFrom) {
            chooseModel.addElement(item);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panSelect = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstItemsToChooseFrom = new javax.swing.JList();
        panAnswer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstYourAnswer = new javax.swing.JList();
        panControls = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jPanel1 = new javax.swing.JPanel();
        btnSelect = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnDeselect = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnFinished = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panSelect.setBorder(javax.swing.BorderFactory.createTitledBorder("Select from list"));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(150, 300));

        lstItemsToChooseFrom.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lstItemsToChooseFrom.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstItemsToChooseFrom.setPreferredSize(null);
        jScrollPane1.setViewportView(lstItemsToChooseFrom);

        panSelect.add(jScrollPane1);

        add(panSelect, java.awt.BorderLayout.WEST);

        panAnswer.setBorder(javax.swing.BorderFactory.createTitledBorder("Your answer"));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(150, 300));

        lstYourAnswer.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lstYourAnswer.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstYourAnswer.setPreferredSize(null);
        jScrollPane2.setViewportView(lstYourAnswer);

        panAnswer.add(jScrollPane2);

        add(panAnswer, java.awt.BorderLayout.EAST);

        panControls.setLayout(new java.awt.GridLayout(5, 0));
        panControls.add(filler1);

        btnSelect.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Select Button.png"))); // NOI18N
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });
        jPanel1.add(btnSelect);

        panControls.add(jPanel1);

        btnDeselect.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnDeselect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Deselect Button.png"))); // NOI18N
        btnDeselect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeselectActionPerformed(evt);
            }
        });
        jPanel2.add(btnDeselect);

        panControls.add(jPanel2);

        btnFinished.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnFinished.setText("Finished");
        btnFinished.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinishedActionPerformed(evt);
            }
        });
        jPanel3.add(btnFinished);

        panControls.add(jPanel3);

        add(panControls, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    /* User has chosen an item */
    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        Object[] selection = lstItemsToChooseFrom.getSelectedValues();
        for (Object selected : selection) {
            yourAnswerModel.addElement(selected);
            chooseModel.removeElement(selected);
        }
    }//GEN-LAST:event_btnSelectActionPerformed
    /* User has "unchosen" a previously chosen item */
    private void btnDeselectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeselectActionPerformed
        Object[] selection = lstYourAnswer.getSelectedValues();
        for (Object selected : selection) {
            chooseModel.addElement(selected);
            yourAnswerModel.removeElement(selected);
        }
    }//GEN-LAST:event_btnDeselectActionPerformed
    /* User has finished making their selection so
     * check their answer and display the results.
     */
    private void btnFinishedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishedActionPerformed
       btnSelect.setEnabled(false);
        btnDeselect.setEnabled(false);
        btnFinished.setEnabled(false);

        // Get the user's selections into an array
        Object[] answers = yourAnswerModel.toArray();

        /* We'll use this to note which answers the user
         * got correct.
         */
        boolean[] isCorrect = new boolean[answers.length];

        // Get the list of correct answers.
        java.util.List correctAnswers = mdg.returnListOfItemsToMemorise();

        // Check each answer the user gave to see if it's correct
        for (int i = 0; i < answers.length; i++) {
            if (correctAnswers.contains(answers[i])) { // answer correct
                isCorrect[i] = true;
            }
        }

        Arrays.sort(answers); // so we can use binarySearch

        /* Check each answer the user SHOULD have got to see if
         * they did.  If not add it to the end of the list on display.
         * It will be itentified by being displayed in green.
         */
        for (Iterator i = correctAnswers.iterator(); i.hasNext();) {
            Object ans = i.next();
            if (Arrays.binarySearch(answers, ans) < 0) {
                yourAnswerModel.addElement(ans);
            }
        }

        MemoryAnalyserAnswerForm.MyCellRenderer myCellRend = new MemoryAnalyserAnswerForm.MyCellRenderer();

        /* Tell the cell renderer which of the answers were
         * correct.  It'll use this to decide the colour to display.
         */
        myCellRend.setCorrectArray(isCorrect);
        lstYourAnswer.setCellRenderer(myCellRend);
    }//GEN-LAST:event_btnFinishedActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeselect;
    private javax.swing.JButton btnFinished;
    private javax.swing.JButton btnSelect;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstItemsToChooseFrom;
    private javax.swing.JList lstYourAnswer;
    private javax.swing.JPanel panAnswer;
    private javax.swing.JPanel panControls;
    private javax.swing.JPanel panSelect;
    // End of variables declaration//GEN-END:variables
    // main method just to demonstrate how the class is used

    public static void main(String args[]) throws Exception {
        JFrame aFrame = new JFrame();
        MemoryDataGenerator dummy = new MemoryDataGenerator();
        dummy.generateData();
        MemoryAnalyserAnswerForm me = new MemoryAnalyserAnswerForm(null, dummy);

        aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        aFrame.add(me);
        aFrame.pack();
        aFrame.setVisible(true);
    }
}
