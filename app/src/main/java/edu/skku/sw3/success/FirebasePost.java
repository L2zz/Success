package edu.skku.sw3.success;

public class FirebasePost {
    public String Title;
    public String Date;
    public String SubCategory;
    public String MainCategory;
    public String Key;
    public String ItemURL;

    public FirebasePost(){
// Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost(String Title, String Date, String SubCategory, String MainCategory, String Key, String ItemURL) {
        this.Title = Title;
        this.Date = Date;
        this.SubCategory = SubCategory;
        this.MainCategory = MainCategory;
        this.Key = Key;
        this.ItemURL = ItemURL;
    }
}