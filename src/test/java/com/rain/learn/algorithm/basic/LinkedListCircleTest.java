package com.rain.learn.algorithm.basic;

import org.junit.Assert;
import org.junit.Test;

public class LinkedListCircleTest {

	@Test
	public void testHasCircle() {
		for (int i = 0; i < 10; i++) {
			System.out.println("i=" + i);
			Assert.assertFalse(createNonCircleList(i).existCircle());
		}

		for (int m = 0; m < 10; m++) {
			for (int n = 1; n < 10; n++) {
				System.out.println("m=" + m + ",n=" + n);
				Assert.assertTrue(createCircleList(m, n).existCircle());
			}
		}

	}

	private LinkedList createNonCircleList(int n) {
		if (n == 0) {
			return new LinkedList(null);
		}
		Node[] nodes = new Node[n];
		nodes[0] = new Node();
		for (int i = 1; i < nodes.length; i++) {
			nodes[i] = new Node();
			nodes[i - 1].next = nodes[i];
		}
		nodes[n - 1].next = null;
		return new LinkedList(nodes[0]);
	}

	private LinkedList createCircleList(int m, int n) {
		Node[] circleNodes = new Node[n];
		circleNodes[0] = new Node();
		for (int i = 1; i < circleNodes.length; i++) {
			circleNodes[i] = new Node();
			circleNodes[i - 1].next = circleNodes[i];
		}
		circleNodes[n - 1].next = circleNodes[0];

		if (m == 0) {
			return new LinkedList(circleNodes[0]);
		}

		Node[] lineNodes = new Node[m];
		lineNodes[0] = new Node();
		for (int i = 1; i < lineNodes.length; i++) {
			lineNodes[i] = new Node();
			lineNodes[i - 1].next = lineNodes[i];
		}
		lineNodes[m - 1].next = circleNodes[0];

		return new LinkedList(lineNodes[0]);
	}

}
