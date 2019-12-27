package com.ccb.recyclerinput.recycler_item;

import com.ccb.recyclerinput.adapter.CcExpandableAdapter;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ExpandItem extends AbstractExpandableItem<BoyItem> implements MultiItemEntity {

    private String title;

  public ExpandItem(String title){
        this.title = title;
  }

    @Override
  public int getLevel() {
        return CcExpandableAdapter.TYPE_LEVEL_0;
  }

    @Override
  public int getItemType() {
        return 0;
  }

    public String getTitle() {
        return title;
  }

    public void setTitle(String title) {
        this.title = title;
  }
}