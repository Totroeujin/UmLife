package com.example.callbacks;

import com.example.model.Post;

import java.util.List;

public interface QueryCompleteCallback<N> {
    void onQueryComplete(List<N> list);
}
