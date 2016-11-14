package com.hanvon.choosephotos;

import java.io.Serializable;

/**
 * ä¸?¸ªå›¾ç‰‡å¯¹è±¡
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
