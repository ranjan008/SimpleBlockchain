package com.sampleBC;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;

public class BlockChain {
	
	public Block addNewBlock(String data){
		Block block = new Block();
		try {
			block.setIndex((RPCBlockChain.chain.getBlocks().size())+1);
			block.setData(data);
			block.setTimestamp(new Date().getTime());
			block.setPrevHash(RPCBlockChain.chain.getHead().getHash());
			block.setHash(generateHash(block));
			RPCBlockChain.chain.getBlocks().put(block.getHash(), block);
			RPCBlockChain.chain.setHead(block);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return block;
	}
	
	public String generateHash(Block block){
		String hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String sblock = new Gson().toJson(block);
			digest.update(sblock.getBytes(),0,sblock.length());
			hash = new BigInteger(1,digest.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}
	
	public Block genesisBlock() throws NoSuchAlgorithmException{
		Block block = new Block();
		block.setIndex(1);
		block.setPrevHash(generateHash(new Block()));
		block.setData("Genesis");
		block.setTimestamp(1496728679l);
		block.setHash(generateHash(block));
		return block;
	}
	
	public Chain initializeBlockChain(){
		Chain chain = new Chain();
		HashMap<String, Block> blocks = new HashMap<String, Block>();
		try {
			Block genesis = genesisBlock();
			chain.setHead(genesis);
			blocks.put(genesis.getHash(), genesis);
			chain.setBlocks(blocks);;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chain;
	}
	
	/*@SuppressWarnings({ "unchecked", "static-access" })
	public Chain blockchainFromJSON(byte[] jsonblob){
		Chain chain = new Chain();
		try {
			Block genesis = genesisBlock();
			chain.head = genesis;
			chain.blocks.put(genesis.hash, genesis);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(jsonblob));
			ArrayList<Block> blocks = (ArrayList<Block>) ois.readObject();
			for (Block block : blocks) {
				chain.blocks.put(block.hash, block);
				if(block.index > chain.head.index){
					chain.head = block;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chain;
	}*/
	
	public boolean shouldReplaceWithChain(Chain rchain){
		boolean replace = false;
		try {
			Block genesis = genesisBlock();
			if(rchain.getBlocks().containsKey(genesis.getHash())){
				if(rchain.getBlocks().size() > RPCBlockChain.chain.getBlocks().size()){
					replace = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return replace;
	}
	
	public ArrayList<Block> getBlocks(){
		ArrayList<Block> blocks = new ArrayList<Block>();
		try {
			for (Block block : RPCBlockChain.chain.getBlocks().values()) {
				if(!block.getHash().equals(genesisBlock().getHash())){
					blocks.add(block);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blocks;
	}
	
	public boolean isValidBlock(Block block, Block prevblock){
		boolean isValid = true;
		try {
			if(block.getIndex() != (RPCBlockChain.chain.getBlocks().size()+1)){
				isValid = false;
			}else if(!prevblock.getHash().equals(block.getPrevHash())){
				isValid = false;
			}else if(new Date(block.getTimestamp()).compareTo(new Date(prevblock.getTimestamp()))<=0){
				isValid = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValid;
	}
}
