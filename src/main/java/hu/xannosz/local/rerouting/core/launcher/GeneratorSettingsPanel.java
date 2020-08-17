package hu.xannosz.local.rerouting.core.launcher;

import javax.swing.*;

public abstract class GeneratorSettingsPanel<T> extends JPanel {
    public abstract T getSettings();
}