package com.blackbread.oauth;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TokenPreserver {
	private String token;
	private long expiresIn;

	public String getToken() {
		return this.token;
	}

	public long getDxpiresIn() {
		return this.expiresIn;
	}

	public static TokenPreserver getInstance() {
		return SingletonBuilder.singleton;
	}

	private static class SingletonBuilder {
		public static TokenPreserver singleton = new TokenPreserver();
	}

	private TokenPreserver() {
		this.token="abbccc";
		System.out.println("初始化了几次");
	}

	public static void main(String[] args) {
		final CountDownLatch c1 = new CountDownLatch(4);
		final CountDownLatch c2 = new CountDownLatch(1);
		ExecutorService s = Executors.newCachedThreadPool();
		for (int i = 0; i < 4; i++) {
			final int j=i;
			s.execute(new Runnable() {
				@Override
				public void run() {
					c1.countDown();
					try {
						c2.await();
						System.out.println(TokenPreserver.getInstance().getToken());
						System.out.println(j);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		try {
			c1.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c2.countDown();
		s.shutdown();
	}
}
