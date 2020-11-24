package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public interface Algorithm {

    String getName();

    MatrixCreator getCreator(AlgorithmSettingsPanel.Settings settings);

    default AlgorithmSettingsPanel getPanel() {
        return new AlgorithmSettingsPanel();
    }

    default Collection<AlgorithmSettingsPanel.Settings> getSettings() {
        Set<AlgorithmSettingsPanel.Settings> settingses = new HashSet<>();

        AlgorithmSettingsPanel.Settings settingsFF = new AlgorithmSettingsPanel.Settings();
        settingsFF.setUseCongestionBorder(false);
        settingsFF.setUseMultiTree(false);
        settingses.add(settingsFF);

        AlgorithmSettingsPanel.Settings settingsFT = new AlgorithmSettingsPanel.Settings();
        settingsFT.setUseCongestionBorder(false);
        settingsFT.setUseMultiTree(true);
        settingses.add(settingsFT);

        AlgorithmSettingsPanel.Settings settingsTF = new AlgorithmSettingsPanel.Settings();
        settingsTF.setUseCongestionBorder(true);
        settingsTF.setUseMultiTree(false);
        settingses.add(settingsTF);

        AlgorithmSettingsPanel.Settings settingsTT = new AlgorithmSettingsPanel.Settings();
        settingsTT.setUseCongestionBorder(true);
        settingsTT.setUseMultiTree(true);
        settingses.add(settingsTT);

        return settingses;
    }
}
