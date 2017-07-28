package com.yunsoft.test;

import java.util.Random;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月12日 下午5:43:00
* 类说明
*/
public class Main {

	public static void main(String[] args) {
		int a=81;
		int b=219;
		double result= (double)a/(a+b);
		for(int i=0;i<1000;i++) {
			Random random=new Random();
			int r=random.nextInt(10);
			System.out.println(r);
			if(r>4) {
				a++;
			}
			b++;
			result=(double)a/(a+b);
			System.out.println("a="+a+",b="+b+",rate="+result);
		}
	}	
}
