package com.example.utils;

import com.example.model.Comment;
import com.example.model.Post;
import com.example.model.Review;

import java.util.List;

public class SpamCheck {

    private String[] salesTriggers = {"$$$" ,"100% free", "Earn $", "earn extra cash", "free offer", "online marketing",
            "life insurance", "limited time offer", "1-800", "1-888", "buy now"};

    private String[] vulgarTriggers = {"fuck", "fucking", "dick", "dickhead", "dyke", "motherfucker", "cocksucker", "bitch", "bastard", "asshole",
    "pussy", "wanker", "cibai", "nigger", "slut", "cunt", "crap", "son of a bitch", "whore", "prick"};

    private int minLength = 8;
    private double threshold = 0.2;

    public SpamCheck() {

    }

    public SpamCheck(int minLength, double threshold) {
        this.minLength = minLength;
        this.threshold = threshold;
    }

    // Check for spam by inspecting the newContent itself
    public boolean contentIsSpam(String newContent) {

        // Empty content
        if (newContent.isEmpty()) {
            return true;
        }

        // check for no. of symbols against no. of words
        int numOfInvalid = 0;
        int numOfChecked = newContent.length();
        for (int i = 0; i < newContent.length(); i++) {
            char c = newContent.charAt(i);
            if (Character.isWhitespace(c)) {
                numOfChecked--; // We don't count whitespaces.
            } else if (Character.UnicodeBlock.of(c) != Character.UnicodeBlock.BASIC_LATIN) {
                numOfInvalid++;
            } else {
                numOfInvalid += Character.isLetterOrDigit(c) ? 0 : 1;
            }
        }

        return numOfChecked > 0 && numOfChecked >= minLength &&
                (double)numOfInvalid / (double)numOfChecked >= threshold ||
                duplicate(newContent) ||
                keySmash(newContent) || salesSpamTrigger(newContent);
    }

    // Check for spam by comparing newContent to existing posts
    public boolean comparativeCommentSpamCheck(String commentDetail, List<Comment> existingComments) {
        // Check if
        int flags = 0;

        for (Comment curComment: existingComments) {
            if (findSimilarity(curComment.getCommentDetail(), commentDetail) >=0.7)
                flags++;
        }
        if (flags > 3) {
            return true;
        }
        return false;
    }

    public boolean comparativeReviewSpamCheck(String reviewDetail, List<Review> existingReviews) {
        // Check if
        int flags = 0;

        for (Review review: existingReviews) {
            if (findSimilarity(review.getComment(), reviewDetail) >=0.7)
                flags++;
        }
        if (flags > 3) {
            return true;
        }
        return false;
    }


    //prevent repeating words
    public boolean duplicate(String message){

        String words[] = message.split(" ");

        for(int i = 0; i < words.length; i++) {
            int count = 1;
            for(int j = i+1; j < words.length; j++) {
                if(words[i].equals(words[j])) {
                    count++;
                    words[j] = "0";
                }
            }
            if(count > 4)
                return true;
        }
        return false;
    }

    public boolean keySmash(String message){
        String words[] = message.split(" ");

        //keySmash with space
        for (int i=0; i<words.length; i++) {
            if (words[i].length() > 10) {
                return true;
            }
        }

        //keySmash without space
        if (!message.contains(" ") && message.length()>9)
            return true;

        return false;
    }

    public boolean salesSpamTrigger(String message){

        for(int i=0; i < salesTriggers.length; i++){
            if(message.toLowerCase().contains(salesTriggers[i].toLowerCase()))
                return true;
        }

        return false;
    }

    public boolean vulgarTrigger(String message) {
        for(int i=0; i < vulgarTriggers.length; i++){
            if(message.toLowerCase().contains(vulgarTriggers[i].toLowerCase()))
                return true;
        }
        return false;
    }

    //find similarity percent between message and preMessage
    public int getLevenshteinDistance(String X, String Y){
        int m = X.length();
        int n = Y.length();

        int[][] T = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            T[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            T[0][j] = j;
        }

        int cost;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cost = X.charAt(i - 1) == Y.charAt(j - 1) ? 0: 1;
                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                        T[i - 1][j - 1] + cost);
            }
        }

        return T[m][n];
    }

    public double findSimilarity(String newContent, String postedContent) {

        double maxLength = Double.max(newContent.length(), postedContent.length());
        if (maxLength > 0) {
            // optionally ignore case if needed
            return (maxLength - getLevenshteinDistance(newContent, postedContent)) / maxLength;
        }
        return 1.0;
    }
}