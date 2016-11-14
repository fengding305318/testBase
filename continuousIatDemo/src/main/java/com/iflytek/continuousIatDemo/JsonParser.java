package com.iflytek.continuousIatDemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

/**
 * Json结果解析类
 */
public class JsonParser {

	public static String parseIatResult(String json) {
		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			JSONArray words = joResult.getJSONArray("ws");
			for (int i = 0; i < words.length(); i++) {
				// 转写结果词，默认使用第一个结果
				JSONArray items = words.getJSONObject(i).getJSONArray("cw");
				JSONObject obj = items.getJSONObject(0);
				ret.append(obj.getString("w"));
//				如果需要多候选结果，解析数组其他字段
//				for(int j = 0; j < items.length(); j++)
//				{
//					JSONObject obj = items.getJSONObject(j);
//					ret.append(obj.getString("w"));
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString();
	}

	public static String parseGrammarResult(String json) {
		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			JSONArray words = joResult.getJSONArray("ws");
			for (int i = 0; i < words.length(); i++) {
				JSONArray items = words.getJSONObject(i).getJSONArray("cw");
				for(int j = 0; j < items.length(); j++)
				{
					JSONObject obj = items.getJSONObject(j);
					if(obj.getString("w").contains("nomatch"))
					{
						ret.append("没有匹配结果.");
						return ret.toString();
					}
					ret.append("【结果】" + obj.getString("w"));
					ret.append("【置信度】" + obj.getInt("sc"));
					ret.append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.append("没有匹配结果.");
		}
		return ret.toString();
	}

	//解析计算的json数据
	public static String pareIatCalculateResult(String json) {
		String text =null;
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);
			if (joResult.length() == 7) {
				//天气预报json解析
				JSONObject dataResult = joResult.getJSONObject("data");
				JSONArray jsonArray = dataResult.getJSONArray("result");
				JSONObject jsonObject = jsonArray.getJSONObject(1);
				text = jsonObject.getString("city") + jsonObject.getString("weather") + jsonObject.getString("tempRange") + jsonObject.getString("wind");
			}else if (joResult.length() == 5) {
				//计算解析
				JSONObject answerJson = joResult.getJSONObject("answer");
				text = answerJson.getString("text");
			}else if (joResult.length() == 6) {
			   //百科与其它的库中都有时json解析百科
				JSONArray jsonArray = joResult.getJSONArray("moreResults");
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				JSONObject jsonObject1 = jsonObject.getJSONObject("answer");
				text = jsonObject1.getString("text");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return text;
	}
}
