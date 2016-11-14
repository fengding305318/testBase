package com.honaf.entity;

import java.io.Serializable;

public class AdvInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String adid;
	private Integer imagesrc;
	private String imageurl;
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		this.adid = adid;
	}
	public Integer getImagesrc() {
		return imagesrc;
	}
	public void setImagesrc(Integer imagesrc) {
		this.imagesrc = imagesrc;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public AdvInfo() {
		super();
	}
	public AdvInfo(String adid, Integer imagesrc, String imageurl) {
		super();
		this.adid = adid;
		this.imagesrc = imagesrc;
		this.imageurl = imageurl;
	}

	

}
//{
//    "adcode": {
//        "imagealt": "中国风",
//        "imagesrc": "./attachment/image/20130515/1368599731.ZfByIh.jpg",
//        "imageurl": "http://www.baidu.com",
//        "type": "image"
//    },
//    "adid": "7",
//    "autype": "0",
//    "available": "1",
//    "pagetype": "Mobileheader",
//    "system": "1",
//    "title": "地方"
//}