package com.example.model;

public class Reward {

    private String rewardName;
    private String rewardDescription;
    private String rewardImageUrl;

    public Reward() {

    }

    public Reward(String rewardName, String rewardDescription, String rewardImageUrl) {
        this.rewardName = rewardName;
        this.rewardDescription = rewardDescription;
        this.rewardImageUrl = rewardImageUrl;
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
}
