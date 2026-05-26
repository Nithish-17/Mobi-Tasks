package model;

public class RegistryReport {

    private int pending;
    private int active;
    private int revoked;
    private int expired;

    private double totalAuthorisedVolume;

    public RegistryReport(
            int pending,
            int active,
            int revoked,
            int expired,
            double totalAuthorisedVolume
    ) {

        this.pending = pending;
        this.active = active;
        this.revoked = revoked;
        this.expired = expired;
        this.totalAuthorisedVolume =
                totalAuthorisedVolume;
    }

    public int getPending() {
        return pending;
    }

    public int getActive() {
        return active;
    }

    public int getRevoked() {
        return revoked;
    }

    public int getExpired() {
        return expired;
    }

    public double getTotalAuthorisedVolume() {
        return totalAuthorisedVolume;
    }
}