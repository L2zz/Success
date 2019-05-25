package edu.skku.sw3.success;

public class ListItem {
    private String Title;
    private String Date;
    private String Category;

    public ListItem() {}

    public ListItem(String Title, String Date, String Category){
        this.Title = Title;
        this.Date = Date;
        this.Category = Category;
    }

    public String getTitle() {return Title;}

    public String getDate() {return Date;}

    public String getCategory() {return  Category;}
}
