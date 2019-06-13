package edu.skku.sw3.success;

public class ListItem {
    private String Title;
    private String Date;
    private Integer siteKey;
    private Integer categoryKey;
    private String ItemURL;
    private String itemKey;

    public ListItem() {}

    public ListItem(String Title, String Date, Integer MainCategory, Integer SubCategory, String ItemURL, String itemKey){
        this.Title = Title;
        this.Date = Date;
        this.siteKey = MainCategory;
        this.categoryKey = SubCategory;
        this.ItemURL = ItemURL;
        this.itemKey = itemKey;
    }

    public ListItem(String title, String date, String itemURL, String itemKey) {
        Title = title;
        Date = date;
        ItemURL = itemURL;
        this.itemKey = itemKey;
    }

    public String getTitle() {return Title;}

    public String getDate() {return Date;}


    public String getItemURL() {return  ItemURL;}

    public void setTitle(String title) {
        Title = title;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setItemURL(String itemURL) {
        ItemURL = itemURL;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public Integer getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(Integer siteKey) {
        this.siteKey = siteKey;
    }

    public Integer getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(Integer categoryKey) {
        this.categoryKey = categoryKey;
    }
}
