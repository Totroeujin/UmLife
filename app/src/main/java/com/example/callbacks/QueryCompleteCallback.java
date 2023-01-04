package com.example.callbacks;

import com.example.model.Post;

import java.util.List;

public interface QueryCompleteCallback {
    void onQueryComplete(List<Post> postList);
}
