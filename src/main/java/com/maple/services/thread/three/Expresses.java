package com.maple.services.thread.three;

/*
 * 2、现有一百个快递待派发，定义快递类Expresses 作为公共资源类，
 * 定义快递员线程类Mailman ,请开启三个线程派发此100个快递，
 * 并打印哪个快递员派发了哪一个快递。【加分（很难，不做要求）：打印快递xx一共派发了xx个快递】
 */
public class Expresses {
	private static int totalExpresses = 10000;

	public static int getTotalExpresses() {
		return totalExpresses;
	}

	private Expresses() {

	}

}
