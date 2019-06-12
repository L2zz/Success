package edu.skku.sw3.success;

import java.util.ArrayList;

public class Site {
    private Integer id;
    private String title;
    private ArrayList<Category> categories;

    public Site(Integer id, String title, ArrayList<Category> categories) {
        this.id = id;
        this.title = title;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
