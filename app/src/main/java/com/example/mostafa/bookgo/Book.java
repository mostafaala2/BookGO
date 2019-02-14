package com.example.mostafa.bookgo;

import java.io.Serializable;
import java.util.Random;

public class Book implements Serializable {
    private String mpic ;
    private String mauthor;
    private String mtitle;
    private String mprice;
    private String mrating;

    private String mdescreption ;
    private String mcurrencycode;
    private String msubtitle ;

public Book(String pic, String author , String title ,String price
     , String descreption , String currencycode , String subtitle , String rating){
         mpic=pic ;
          mauthor = author ;
          mtitle = title ;
          mprice = price ;

         mdescreption = descreption;
         mcurrencycode = currencycode;
         msubtitle = subtitle ;
         mrating = rating ;
    }



    public String getdescreption() {
        return mdescreption;
    }

    public String getcurrencycode() {
        return mcurrencycode;
    }

    public String getsubtitle() {
        return msubtitle;
    }


    public String getrating() {
        return mrating;
    }

    public String getpic() {
        return mpic;
    }

    public String getauthor() {
        return mauthor;
    }

    public String gettitle() {
        return mtitle;
    }

    public String getprice() {
        return mprice;
    }


}