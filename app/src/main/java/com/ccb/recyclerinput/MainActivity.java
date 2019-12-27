package com.ccb.recyclerinput;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ccb.recyclerinput.adapter.CcExpandableAdapter;
import com.ccb.recyclerinput.recycler_item.BoyItem;
import com.ccb.recyclerinput.recycler_item.ExpandItem;
import com.ccb.recyclerinput.utils.GlideEngine;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CcExpandableAdapter expandableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expandableAdapter = new CcExpandableAdapter(this,getExpandListData(10));
        expandableAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recyclerView.setAdapter(expandableAdapter);
        expandableAdapter.expandAll(0, true);
    }


    private List<MultiItemEntity> getExpandListData(int count) {
        int lvCount = count;
        int lv1Count = 6;

        List<MultiItemEntity> data = new ArrayList<>();
        for (int i = 0; i < lvCount; i++) {
            ExpandItem item0 = new ExpandItem("标题" + i);
            for (int j = 0; j < lv1Count; j++) {
                BoyItem item1;
                if (j % 3 == 0) {
                    item1 = new BoyItem("选择图片" + i + "." + j, BoyItem.TYPE.IMAGE);
                } else {
                    item1 = new BoyItem("输入内容" + i + "." + j, BoyItem.TYPE.TEXT);
                }
                item0.addSubItem(item1);
            }
            data.add(item0);
        }

        return data;
    }

    public void click(View view) {
        List<MultiItemEntity> datas = expandableAdapter.getData();
        List<MultiItemEntity> newDatas = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i) instanceof ExpandItem) {
                newDatas.add(datas.get(i));
            }
        }
        Log.i("CCB", new Gson().toJson(newDatas));
//        openCamera(0);
    }

    private int adapterPosition;
    public void openCamera(int position) {
        adapterPosition =  position;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    10);
        } else {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    }

    private String TAG="CCB";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回五种path
                    // 1.media.getPath(); 原图path
                    // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
                    // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
                    // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                    // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路
                    //径；注意：.isAndroidQTransform(false);此字段将返回空
                    // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                    MultiItemEntity multiItemEntity = expandableAdapter.getData().get(adapterPosition);
                    if (multiItemEntity instanceof BoyItem){
                        BoyItem boyItem = (BoyItem) multiItemEntity;
                        for (LocalMedia media : selectList) {
                            boyItem.setContent(media.getPath());
                            expandableAdapter.setData(adapterPosition , boyItem);
                            Log.i(TAG, "压缩::" + media.getCompressPath());
                            Log.i(TAG, "原图::" + media.getPath());
                            Log.i(TAG, "裁剪::" + media.getCutPath());
                            Log.i(TAG, "是否开启原图::" + media.isOriginal());
                            Log.i(TAG, "原图路径::" + media.getOriginalPath());
                            Log.i(TAG, "Android Q 特有Path::" + media.getAndroidQToPath());
                        }
                    }

                    break;
            }
        }
    }
}
