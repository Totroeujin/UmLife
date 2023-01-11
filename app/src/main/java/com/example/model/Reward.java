package com.example.model;

public class Reward {

    private String rewardName;
    private String rewardDescription;
    private String rewardImageUrl;
    private String quote;
    private String requiredPoints;


    public Reward() {

    }

    public Reward(String rewardName, String rewardDescription, String rewardImageUrl, String quote, String requiredPoints) {
        this.rewardName = rewardName;
        this.rewardDescription = rewardDescription;
        this.rewardImageUrl = rewardImageUrl;
        this.quote = quote;
        this.requiredPoints = requiredPoints;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardDescription() {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
    }

    public String getRewardImageUrl() {
        return rewardImageUrl;
    }

    public void setRewardImageUrl(String rewardImageUrl) {
        this.rewardImageUrl = rewardImageUrl;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getRequiredPoints() {
        return requiredPoints;
    }

    public void setRequiredPoints(String requiredPoints) {
        this.requiredPoints = requiredPoints;
    }
}
