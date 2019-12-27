package com.ccb.recyclerinput.recycler_item;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class BoyItem implements MultiItemEntity {
    @Override
    public int getItemType() {
        return 1;
    }


    public BoyItem(String title , TYPE type){
        this.title = title;
        this.type = type;
    }

    private String title;
    private String content;
    private String src;
    private TYPE type;

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }


    public String getTitle() {
        return title;
  }

    public void setTitle(String title) {
        this.title = title;
  }

  public enum TYPE{
        TEXT,IMAGE
  }
}