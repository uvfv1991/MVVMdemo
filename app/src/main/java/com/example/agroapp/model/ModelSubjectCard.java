package com.example.agroapp.model;

import java.util.List;

/**
 * author : jiangxue
 * date : 2023/5/24 14:34
 * description :
 */
public class ModelSubjectCard {

    private  int adIndex;

    private  ModelSubjectCard.Data data;
    private int id;

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

    private String type;


    public  class Data {
        private  int count;
        
        private  String dataType;

        private Header header;
       
        private List<Item> itemList;

        public int getCount() {
            return count;
        }

        public String getDataType() {
            return dataType;
        }

        public Header getHeader() {
            return header;
        }

        public List<Item> getItemList() {
            return itemList;
        }


        public class Header {

            private Object actionUrl;

            private Object cover;

            private Object description;

            private Object font;

            private Object icon;
            private int id;

            private Object label;

            private Object labelList;

            private String rightText;

            private Object subTitle;

            private Object subTitleFont;

            private String textAlign;

            private String title;

            public String getRightText() {
                return rightText;
            }

            public Object getSubTitle() {
                return subTitle;
            }

            public Object getSubTitleFont() {
                return subTitleFont;
            }

            public String getTextAlign() {
                return textAlign;
            }

            public String getTitle() {
                return title;
            }
        }

        public  class Item {
            private int adIndex;

            private Data1 data;
            private int id;

            private Object tag;

            private String type;

            public Data1 getData() {
                return data;
            }

            public   class Data1 {

                private String actionUrl;

                private String dataType;

                private String description;

                private int id;

                private String image;

                private String title;

                public String getActionUrl() {
                    return actionUrl;
                }

                public String getDataType() {
                    return dataType;
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

                public String getTitle() {
                    return title;
                }
            }
        }
    }


}
