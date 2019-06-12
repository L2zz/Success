package edu.skku.sw3.success;

public class ListItem {
    private String Title;
    private String Date;
    private String MainCategory;
    private String SubCategory;
    private String ItemURL;

    public ListItem() {}

    public ListItem(String Title, String Date, String MainCategory, String SubCategory, String ItemURL){
        this.Title = Title;
        this.Date = Date;
        this.MainCategory = MainCategory;
        this.SubCategory = SubCategory;
        this.ItemURL = ItemURL;
    }

    public String getTitle() {return Title;}

    public String getDate() {return Date;}

    public String getMainCategory() {return MainCategory;}

    public String getSubCategory() {return SubCategory;}

    public String getItemURL() {return  ItemURL;}

    public void setTitle(String title) {
        Title = title;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setMainCategory(String mainCategory) {
        MainCategory = mainCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }

    public void setItemURL(String itemURL) {
        ItemURL = itemURL;
    }
}
