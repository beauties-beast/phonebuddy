package com.github.beauties_beast.phonebuddy;

/**
 * Created by boggs on 10/9/15.
 */
public class ServiceManager {
    private static ServiceManager instance = new ServiceManager();

    public static ServiceManager getInstance() {
        return instance;
    }

    private ServiceManager() {
        active = false;
    }

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
