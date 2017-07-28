package com.yunsoft.test;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月26日 下午9:54:48
* 类说明
*/
public enum DefaultEnum {
	user(Main.class);
	
	public Class<?> main;
	
	private DefaultEnum(Class<? extends Main> main) {
        this.main = main;
    }
}
