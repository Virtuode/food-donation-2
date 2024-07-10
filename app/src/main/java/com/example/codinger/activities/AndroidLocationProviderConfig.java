package com.example.codinger.activities;

import java.time.Duration;

public class AndroidLocationProviderConfig {

    private static final Duration DEFAULT_TIME_INTERVAL_FASTEST = Duration.ofSeconds(10); // Example default value
    private static final double DEFAULT_MIN_DISTANCE = 10.0; // Example default value, in meters

    private Duration minTimeInterval;
    private double minDistance;

    public AndroidLocationProviderConfig() {
        this.minTimeInterval = DEFAULT_TIME_INTERVAL_FASTEST;
        this.minDistance = DEFAULT_MIN_DISTANCE;
    }

    public AndroidLocationProviderConfig(Duration minTimeInterval, double minDistance) {
        this.minTimeInterval = minTimeInterval;
        this.minDistance = minDistance;
    }

    public Duration getMinTimeInterval() {
        return minTimeInterval;
    }

    public void setMinTimeInterval(Duration minTimeInterval) {
        this.minTimeInterval = minTimeInterval;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }
}
