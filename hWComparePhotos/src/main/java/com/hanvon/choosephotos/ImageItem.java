package com.hanvon.choosephotos;

import java.io.Serializable;

/**
 * �?��图片对象
 * 
 * @author Administrator
 * 
 */
public class ImageItem implements Serializable {
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	public int score;
	public byte[] pFeature;
	public boolean isSelected = false;
}
