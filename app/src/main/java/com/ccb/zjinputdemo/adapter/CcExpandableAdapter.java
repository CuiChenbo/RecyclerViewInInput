package com.ccb.zjinputdemo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ccb.zjinputdemo.MainActivity;
import com.ccb.zjinputdemo.R;
import com.ccb.zjinputdemo.dialog.InputAlertDialog;
import com.ccb.zjinputdemo.recycler_item.BoyItem;
import com.ccb.zjinputdemo.recycler_item.ExpandItem;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class CcExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    private MainActivity mainActivity;
    public CcExpandableAdapter(MainActivity activity ,List<MultiItemEntity> data) {
        super(data);
        mainActivity = activity;
        addItemType(TYPE_LEVEL_0, R.layout.view_expand_0);
        addItemType(TYPE_LEVEL_1, R.layout.view_expand_1);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        final BaseViewHolder fhelper = helper;
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final ExpandItem item0 = (ExpandItem) item;
                helper.setText(R.id.expand_list_item0_tv, item0.getTitle());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = fhelper.getAdapterPosition();
                        if (item0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final BoyItem boyData = (BoyItem) item;
                helper.setText(R.id.expand_list_item1_tv, boyData.getTitle());
                helper.setGone(R.id.tvInput, boyData.getType() == BoyItem.TYPE.TEXT);
                helper.setGone(R.id.ivInput, boyData.getType() == BoyItem.TYPE.IMAGE);
                helper.setText(R.id.tvInput,boyData.getContent());
                Glide.with(mContext).load(boyData.getContent()).into((ImageView) helper.getView(R.id.ivInput));
                helper.getView(R.id.btSubmin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, boyData.getContent(), Toast.LENGTH_SHORT).show();
                    }
                });
                helper.getView(R.id.tvInput).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //这个position并不分等级（不区分父Item和子Item），完全按照展开顺序排列的（从上向下排列的顺序）；
                        showDialog(fhelper.getAdapterPosition());
                    }
                });
                helper.getView(R.id.ivInput).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //这个position并不分等级（不区分父Item和子Item），完全按照展开顺序排列的（从上向下排列的顺序）；
                        mainActivity.openCamera(fhelper.getAdapterPosition());
                    }
                });
                break;
        }
    }

    private InputAlertDialog inputAlertDialog;

    private void showDialog(final int position) {
        if (inputAlertDialog == null) inputAlertDialog = new InputAlertDialog(mContext).bunlid();
        final BoyItem boyItem = (BoyItem) getData().get(position);

        inputAlertDialog.setTitleText(boyItem.getTitle());
        inputAlertDialog.setInputText(boyItem.getContent());
        inputAlertDialog.setSubmitClickList(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boyItem.setContent(inputAlertDialog.getInputText());
                setData(position , boyItem);
                inputAlertDialog.dissmiss();
            }
        });
        inputAlertDialog.show();
    }
}
