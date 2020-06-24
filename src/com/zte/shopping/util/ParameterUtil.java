package com.zte.shopping.util;

/**
 * 参数工具类
 * 
 * @author liyan
 *
 */
public class ParameterUtil
{
	public static boolean isnull(String s)
	{
		return "".equals(s) || null == s;
	}
}
