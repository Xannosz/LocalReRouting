package hu.xannosz.local.rerouting.core.launcher;

import javax.swing.*;

public abstract class MessageGeneratorSettingsPanel<T> extends JPanel {
    public abstract T getSettings();
}