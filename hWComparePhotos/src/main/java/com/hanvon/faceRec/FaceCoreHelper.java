package com.hanvon.faceRec;

public class FaceCoreHelper {
	static{
		System.loadLibrary("HWFaceRecogLibSDK");
	}
	
	/**
	 * 获取秘钥信息
	 * @param pbKeyCode 秘钥
	 * @param pnKeyCodeSize 秘钥长度
	 * @return
	 */
	public static native int HWGetKeyCode(byte[] pbKeyCode,int[] pnKeyCodeSize);
	

	/**
	 * 核心初始化方法
	 * @param bpKeyCode 秘钥
	 * @return 1：成功；0：失败
	 */
	public static native int HwInitFace(byte[] bpKeyCode);
	
	/**
	 * 释放核心方法
	 * @return 1：成功；0：失败
	 */
	public static native int HwReleaseFace();
	
	/**
	 * 获取模板大小
	 * @param featureLength 模板长度
	 * @return 1：成功；0：失败
	 */
	public static native int HwGetFeatureSize(int[] featureLength);
	
	/**
	 * 人脸定位方法
	 * @param pbImg    传入人脸信息灰度图数据
	 * @param width    传入图片宽度
	 * @param height   传入图片高度
	 * @param pFacePos 输出人脸信息【输入空间至少有(HW_FACEPOS_LEN + HW_FACEINFO_LEN)*pnDetectFaceNum个元素】
	 * @param pnDetectFaceNum    输入最大人脸识别数，输出实际检测到的人脸数
	 * @return 1：成功；0：失败
	 */
	public static native int HwDetectMultiFaceAndEyeEx(byte[] pbImg, int width, int height, int[] pFacePos, int[] pnDetectFaceNum);
	
	/**
	 * 获取人脸模板
	 * @param pbImg    传入人脸信息灰度图数据
	 * @param width    传入图片宽度
	 * @param height   传入图片高度
	 * @param pFacePos    输入人脸信息
	 * @param pFeature    输出人脸模板
	 * @return 1：成功；0：失败
	 */
	public static native int HwGetFaceFeatureEx(byte[] pbImg, int width, int height, int[] pFacePos, byte[] pFeature);
	
	/**
	 * 人脸比对方法1
	 * @param thisFeature    当前模板
	 * @param referenceFeature    需要比对的模板
	 * @param frvalue    比对得分
	 * @return 1：成功；0：失败
	 */
	public static native int HwCompareFeature(byte[] thisFeature, byte[] referenceFeature, float[] frvalue);
}
