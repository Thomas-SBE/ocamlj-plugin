package org.tsbe.camlj.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent settingsComponent;


    /**
     * Returns the visible name of the configurable component.
     * Note, that this method must return the display name
     * that is equal to the display name declared in XML
     * to avoid unexpected errors.
     *
     * @return the visible name of the configurable component
     */
    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "OcamlJ Interpretter";
    }

    /**
     * @return component which should be focused when the dialog appears
     * on the screen.
     */
    @Override
    public JComponent getPreferredFocusedComponent() {
        return settingsComponent.getPreferedFocusedComponent();
    }

    /**
     * Creates new Swing form that enables user to configure the settings.
     * Usually this method is called on the EDT, so it should not take a long time.
     * <p>
     * Also this place is designed to allocate resources (subscriptions/listeners etc.)
     *
     * @return new Swing form to show, or {@code null} if it cannot be created
     * @see #disposeUIResources
     */
    @Override
    public @Nullable JComponent createComponent() {
        settingsComponent = new AppSettingsComponent();
        settingsComponent.setOcamlLocationText(AppSettingsState.getInstance().OcamlLocation);
        return settingsComponent.getPanel();
    }

    /**
     * Indicates whether the Swing form was modified or not.
     * This method is called very often, so it should not take a long time.
     *
     * @return {@code true} if the settings were modified, {@code false} otherwise
     */
    @Override
    public boolean isModified() {
        AppSettingsState settings = AppSettingsState.getInstance();
        boolean modified = !settingsComponent.getOcamlLocationText().equals(settings.OcamlLocation);
        return modified;
    }

    /**
     * Stores the settings from the Swing form to the configurable component.
     * This method is called on EDT upon user's request.
     *
     * @throws ConfigurationException if values cannot be applied
     */
    @Override
    public void apply() throws ConfigurationException {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.OcamlLocation = settingsComponent.getOcamlLocationText();
    }

    /**
     * Loads the settings from the configurable component to the Swing form.
     * This method is called on EDT immediately after the form creation or later upon user's request.
     */
    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settingsComponent.setOcamlLocationText(settings.OcamlLocation);
    }

    /**
     * Notifies the configurable component that the Swing form will be closed.
     * This method should dispose all resources associated with the component.
     */
    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
