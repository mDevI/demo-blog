package com.example.demoblog.util;

import com.example.demoblog.model.Post;

import java.util.List;

public class PostGrid {

    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<Post> postsData;

    public PostGrid() {
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<Post> getPostsData() {
        return postsData;
    }

    public void setPostsData(List<Post> postsData) {
        this.postsData = postsData;
    }
}
