package com.fwkproject.cryptotracker.infrastructure.configuration;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum AppFeatures implements Feature {

    @EnabledByDefault
    @Label("New Pricing Engine")
    NEW_PRICING_ENGINE;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}
