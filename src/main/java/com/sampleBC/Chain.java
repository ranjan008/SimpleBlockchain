package com.sampleBC;

import java.util.HashMap;

public class Chain {

	private Block head;
	private HashMap<String, Block> blocks;
	public Block getHead() {
		return head;
	}
	public void setHead(Block head) {
		this.head = head;
	}
	public HashMap<String, Block> getBlocks() {
		return blocks;
	}
	public void setBlocks(HashMap<String, Block> blocks) {
		this.blocks = blocks;
	}
}
