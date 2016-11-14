package com.example.lenovo.testhangwang.choosephotos;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/4/27.
 */
public class ImageItem implements Serializable {
    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    public int score;
    public byte[] pFeature;
    public boolean isSelected = false;
}
