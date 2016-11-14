package com.example.lenovo.testhangwang.faceRec;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class HWFaceClient {
	/**
	 * ִ执行成功
	 */
	public final static int HW_OK = 0;
	/**
	 * 失败
	 */
	public final static int HW_FAIL = -1;
	/**
	 * 人脸信息数据长度
	 */
	public final static int HW_FACEINFO_LEN = 224;
	/**
	 * 人脸坐标数据长度
	 */
	public final static int HW_FACEPOS_LEN = 4;

	private final static String strDataFile2 = "/data/data/com.hanvon.comparephotos/files";
	private final static String strDataFile = "HanvonFeature";

	private static Socket client;
	private static String strServerAddressIP;
	private static Boolean ServerState = true;
	public static byte[] bpKeyCode = null;
	public static int iKeyCodeSize = 0;
	private static int nPort;
	//private static Handler handler = new Handler();//1
	/**
	 * 将int类型的数据转换为byte数组
	 * 原理：将int数据中的四个byte取出，分别存储
	 * @param n int数据
	 * @return 生成的byte数组
	 */
	public static byte[] intToBytes2(int n){
		byte[] b = new byte[4];
		for(int i = 0;i < 4;i++){
			b[i] = (byte)(n >> (24 - i * 8));
		}
		return b;
	}

	/**
	 * 将byte数组转换为int数据
	 * @param b 字节数组
	 * @return 生成的int数据
	 */
	public static int byteToInt2(byte[] b){
		return (((int)b[0]) << 24) + (((int)b[1]) << 16) + (((int)b[2]) << 8) + b[3];
	}

	/**
	 * 获取客户端人脸核心秘钥
	 * @param ServerAddressIP [input] 服务器IP
	 * @param Port [input] 服务器端口
	 * @return 见接口返回值定义说明
	 */
//	static Runnable clientThread = new Runnable() {
//		@Override
//		public void run() {
//			try {
//				Log.v("hehe", "来没？");
//				client = new Socket(strServerAddressIP, nPort);
//			} catch (IOException e) {
//				Log.v("eeee", e.toString() + "");
//				//e.printStackTrace();
//			}
//		}
//	};
	final static Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x1233) {
				try {
					Log.v("hehe", "来没？");
					client = new Socket(strServerAddressIP, nPort);
				} catch (IOException e) {
					Log.v("eeee", e.toString() + "");
					//e.printStackTrace();
				}
			}
		}
	};
	public static int InitFaceClient(String ServerAddressIP, int Port)
	{
		int Result = -1;
		try {
			strServerAddressIP = ServerAddressIP;
			nPort = Port;
			handler.sendEmptyMessage(0x1233);
			byte[] bpSend = new byte[256];
			int[] nSendLen = new int[1];
			nSendLen[0] = bpSend.length;

			try {
				FaceCoreHelper.HWGetKeyCode(bpSend, nSendLen);
			} catch (Exception ex) {
				System.out.println("<<<<<<<>>>>>>>>>");
				Log.v("ex", ex.toString() + "");
			}


			if(nSendLen[0] <= 0)
				return -1;

			byte[] bpHeadData = intToBytes2(nSendLen[0]);
			byte[] bpKey = new byte[nSendLen[0]];
			System.arraycopy(bpSend, 0, bpKey, 0, bpKey.length);
			DataOutputStream out;
			out = new DataOutputStream(client.getOutputStream());

			out.write(bpHeadData);
			out.write(bpKey);

			Thread ServerConnect = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub

					while (ServerState) {
						if(!client.isClosed())
						{

							//接收来自 server的响应数据
							try {
								DataInputStream in;

								in = new DataInputStream(client.getInputStream());
								byte[] bpHead = new byte[4];

								int result = in.read(bpHead, 0, bpHead.length);

								if(result <= 0)
									continue;

								int KeyLen = byteToInt2(bpHead);

								if(KeyLen > 0)
								{
									byte[] bpData = new byte[KeyLen];

									int iReadLen = in.read(bpData, 0, bpData.length);

									if(iReadLen <= 0)
									{
										ServerState = false;
										return;
									}
									else
									{
										CreatInitKeyCodeFile(bpData, bpData.length);
									}

									ServerState = false;
								}

								in.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				}
	        });

			ServerConnect.start();
			out.flush();
			Result = 0;

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Result;
	}

	/**
	 * 客户端Socket关闭
	 * @return 见接口返回值定义说明
	 */
	public static int ReleaseFaceClient()
	{
		int Result = HW_FAIL;
		try {
			if(client != null)
			{
				if(client.isConnected())
				{
					client.close();
					client = null;
				}
			}
			Result = HW_OK;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Result;
	}

	/**
	 * 获取秘钥信息
	 * @return 见接口返回值定义说明
	 */
	public static int GetKeyCode()
	{
		int nResult = HW_OK;

		try
		{
			FileInputStream in = null;
			String strKeyCode = strDataFile2 + "/" +strDataFile + "/hwKeyCode.dat";
			File dstFile = new File(strKeyCode);
			if(dstFile.exists())
			{
				in = new FileInputStream(strKeyCode);
				iKeyCodeSize = in.available();
				if( iKeyCodeSize > 0)
				{
					bpKeyCode = new byte[iKeyCodeSize];
					in.read(bpKeyCode);
				}
			}
			else
			{
				nResult = HW_FAIL;
			}
			in.close();
		}
		catch(Exception ex)
		{
			nResult = HW_FAIL;
			ex.printStackTrace();
		}


		return nResult;
	}


	/**
	 * 创建秘钥
	 * @param pbKeyCode 秘钥
	 * @param iKeyCodeLen 秘钥长度
	 * @return 见接口返回值定义说明
	 */
	public static int CreatInitKeyCodeFile(byte[] pbKeyCode, int iKeyCodeLen)
	{
		int nResult = 0;

		FileOutputStream out = null;

		try {
			File file = new File(strDataFile2);
			if (!file.exists()) {
				file.mkdir();
			}

			file = new File(strDataFile2 + "/" +strDataFile);
			if (!file.exists()) {
				file.mkdir();
			}

			String strFilePath = strDataFile2 + "/" +strDataFile + "/hwKeyCode.dat";

			File srcFile = new File(strFilePath);

			if(!srcFile.exists())
			{
				srcFile.createNewFile();
			}

			if (iKeyCodeLen > 0) {
				try {
					out = new FileOutputStream(strFilePath);
					out.write(pbKeyCode);
					out.flush();
					out.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (Exception ex) {
			nResult = HW_FAIL;
			ex.printStackTrace();
		}

		return nResult;
	}
}
