package com.sampleBC;

public class Block {

	private Integer index;
	private long timestamp;
	private String data;
	private String hash;
	private String prevHash;
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getPrevHash() {
		return prevHash;
	}
	public void setPrevHash(String prevHash) {
		this.prevHash = prevHash;
	}
	
	
}
