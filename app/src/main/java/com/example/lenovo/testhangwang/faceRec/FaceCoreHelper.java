package com.example.lenovo.testhangwang.faceRec;

import android.util.Log;

public class FaceCoreHelper {
	static {
		try {
			System.loadLibrary("HWFaceRecogLibSDK");
		} catch (Exception ex) {
			Log.v("ex", ex.toString() + "");
		}

	}

	/**
	 * ��ȡ��Կ��Ϣ
	 * @param pbKeyCode ��Կ
	 * @param pnKeyCodeSize ��Կ����
	 * @return
	 */
	public static native int HWGetKeyCode(byte[] pbKeyCode,int[] pnKeyCodeSize);


	/**
	 * ���ĳ�ʼ������
	 * @param bpKeyCode ��Կ
	 * @return 1���ɹ���0��ʧ��
	 */
	public static native int HwInitFace(byte[] bpKeyCode);

	/**
	 * �ͷź��ķ���
	 * @return 1���ɹ���0��ʧ��
	 */
	public static native int HwReleaseFace();

	/**
	 * ��ȡģ���С
	 * @param featureLength ģ�峤��
	 * @return 1���ɹ���0��ʧ��
	 */
	public static native int HwGetFeatureSize(int[] featureLength);

	/**
	 * ������λ����
	 * @param pbImg    ����������Ϣ�Ҷ�ͼ���
	 * @param width    ����ͼƬ���
	 * @param height   ����ͼƬ�߶�
	 * @param pFacePos ���������Ϣ������ռ�������(HW_FACEPOS_LEN + HW_FACEINFO_LEN)*pnDetectFaceNum��Ԫ�ء�
	 * @param pnDetectFaceNum    �����������ʶ�������ʵ�ʼ�⵽��������
	 * @return 1���ɹ���0��ʧ��
	 */
	public static native int HwDetectMultiFaceAndEyeEx(byte[] pbImg, int width, int height, int[] pFacePos, int[] pnDetectFaceNum);

	/**
	 * ��ȡ����ģ��
	 * @param pbImg    ����������Ϣ�Ҷ�ͼ���
	 * @param width    ����ͼƬ���
	 * @param height   ����ͼƬ�߶�
	 * @param pFacePos    ����������Ϣ
	 * @param pFeature    �������ģ��
	 * @return 1���ɹ���0��ʧ��
	 */
	public static native int HwGetFaceFeatureEx(byte[] pbImg, int width, int height, int[] pFacePos, byte[] pFeature);

	/**
	 * �����ȶԷ���1
	 * @param thisFeature    ��ǰģ��
	 * @param referenceFeature    ��Ҫ�ȶԵ�ģ��
	 * @param frvalue    �ȶԵ÷�
	 * @return 1���ɹ���0��ʧ��
	 */
	public static native int HwCompareFeature(byte[] thisFeature, byte[] referenceFeature, float[] frvalue);
}
