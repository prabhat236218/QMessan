package com.example.prabhat.q;

/**
 * Created by prabhat on 20/3/18.
 */
public class model {
    private String img;
    private String name;
    private String price;
    private String dp;
    public model()
    {}
    public model(String dp,String img,String name,String price)
    {
        this.img=img;
        this.name=name;
        this.price=price;
        this.dp=dp;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getImg() {
        return img;
    }
}
