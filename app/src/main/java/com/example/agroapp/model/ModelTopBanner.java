package com.example.agroapp.model;

import java.util.List;

/**
 * author : jiangxue
 * date : 2023/5/24 14:34
 * description :
 */
public class ModelTopBanner {

    private  int adIndex;
    
    private  Data data;
    private int id;
   
    private  Object tag;
    
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

    public Object getTag() {
        return tag;
    }

    public String getType() {
        return type;
    }

    public  class Data {
        private  int count;
        
        private  String dataType;
       
        private List<Item> itemList;

        public int getCount() {
            return count;
        }

        public String getDataType() {
            return dataType;
        }

        public List<Item> getItemList() {
            return itemList;
        }

        public  class Item {
            private int adIndex;

            private Data1 data;
            private int id;

            private Object tag;

            private String type;

            public int getAdIndex() {
                return adIndex;
            }

            public Data1 getData() {
                return data;
            }

            public int getId() {
                return id;
            }

            public Object getTag() {
                return tag;
            }

            public String getType() {
                return type;
            }

            public class Data1 {

                private String actionUrl;

                private List adTrack;
                private boolean autoPlay;

                private String dataType;

                private String description;

                private Header header;
                private int id;

                private String image;

                private Label label;

                private List labelList;
                private boolean shade;

                private String title;

                public String getActionUrl() {
                    return actionUrl;
                }

                public List getAdTrack() {
                    return adTrack;
                }

                public boolean isAutoPlay() {
                    return autoPlay;
                }

                public String getDataType() {
                    return dataType;
                }

                public String getDescription() {
                    return description;
                }

                public Header getHeader() {
                    return header;
                }

                public int getId() {
                    return id;
                }

                public String getImage() {
                    return image;
                }

                public Label getLabel() {
                    return label;
                }

                public List getLabelList() {
                    return labelList;
                }

                public boolean isShade() {
                    return shade;
                }

                public String getTitle() {
                    return title;
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

                    private Object rightText;

                    private Object subTitle;

                    private Object subTitleFont;

                    private String textAlign;

                    private Object title;

                }

                public class Label {

                    private String card;

                    private Object detail;

                    private String text;

                }

            }

        }
    }


}
