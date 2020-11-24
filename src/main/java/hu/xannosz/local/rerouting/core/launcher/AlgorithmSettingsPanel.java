package hu.xannosz.local.rerouting.core.launcher;

import lombok.Data;

import javax.swing.*;

public class AlgorithmSettingsPanel extends JPanel {

    private final JCheckBox useMultiTree = new JCheckBox();
    private final JCheckBox useCongestionBorder = new JCheckBox();

    public AlgorithmSettingsPanel() {
        add(new JLabel("MTS model: "));
        add(useMultiTree);
        useMultiTree.setSelected(false);
        add(new JLabel("Congestion Border: "));
        add(useCongestionBorder);
        useCongestionBorder.setSelected(false);
    }

    public Settings getSettings() {
        Settings settings = new Settings();
        settings.setUseCongestionBorder(useCongestionBorder.isSelected());
        settings.setUseMultiTree(useMultiTree.isSelected());
        return settings;
    }

    @Data
    public static class Settings {
        private boolean useMultiTree;
        private boolean useCongestionBorder;
    }
}