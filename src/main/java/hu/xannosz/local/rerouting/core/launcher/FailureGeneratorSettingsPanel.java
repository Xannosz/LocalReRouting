package hu.xannosz.local.rerouting.core.launcher;

import javax.swing.*;

public abstract class FailureGeneratorSettingsPanel<T> extends JPanel {
    public abstract T getSettings();
}