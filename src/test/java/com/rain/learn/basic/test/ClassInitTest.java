package com.rain.learn.basic.test;

@SuppressWarnings("static-access")
class SingleTon {
	private static SingleTon singleTon = new SingleTon();
	public static int count1;
	public static int count2 = 0;

	private SingleTon() {
		count1++;
		count2++;
	}

	static {
		System.out.println("count1=" + singleTon.count1);
		System.out.println("count2=" + singleTon.count2);
	}

	public static SingleTon getInstance() {
		return singleTon;
	}
}

public class ClassInitTest {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		SingleTon singleTon = SingleTon.getInstance();
		System.out.println("count1=" + singleTon.count1);
		System.out.println("count2=" + singleTon.count2);
	}
}
