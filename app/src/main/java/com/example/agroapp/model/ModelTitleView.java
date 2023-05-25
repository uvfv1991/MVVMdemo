package com.example.agroapp.model;

import java.util.List;

/**
 * author : jiangxue
 * date : 2023/5/24 14:34
 * description :
 */
public class ModelTitleView {

    private  int adIndex;

    private  ModelTitleView.Data data;
    private int id;
    
    private String type;

    public int getAdIndex() {
        return adIndex;
    }

    public Data getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public  class Data {
        private  int count;
        
        private  String dataType;

        private String actionUrl;


        private String description;

        private int id;

        private String image;

        private String text;

        private String rightText;

        public String getRightText() {
            return rightText;
        }

        public int getCount() {
            return count;
        }

        public String getDataType() {
            return dataType;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getText() {
            return text;
        }
    }
}
