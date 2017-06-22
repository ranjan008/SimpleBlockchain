package com.sampleBC;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class RPCBlockChain {

	private RPCBlockChain(){
    	init();
    }
	
	private static BlockChain bc;
	public static Chain chain;
	HashMap<String, String> peers = new HashMap<String,String>();
	
	public void init(){
		bc = new BlockChain();
		chain = bc.initializeBlockChain();
	}
	
	@RequestMapping(value = "/createBlock", method = {RequestMethod.POST,RequestMethod.GET})
	public Block createBlock(@RequestBody String data,HttpServletRequest request,HttpServletResponse response){
		Block block = null;
		try {
			block = bc.addNewBlock(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return block;
	}
	
	@RequestMapping(value = "/getBlocks", method = {RequestMethod.POST,RequestMethod.GET})
	public ArrayList<Block> getBlocks(HttpServletRequest request,HttpServletResponse response){
		ArrayList<Block> blocks = null;
		try {
			blocks = bc.getBlocks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blocks;
	}
	
	@RequestMapping(value = "/chain", method = {RequestMethod.POST,RequestMethod.GET})
	public String blocks(@RequestBody Chain rchain,HttpServletRequest request,HttpServletResponse response){
		String res = null;
		try {
			if (bc.shouldReplaceWithChain(rchain)){
				chain = rchain;
			}
			res="success";
		} catch (Exception e) {
			res="failure";
		}
		return res;
	}
	
	@RequestMapping(value = "/getChain", method = {RequestMethod.POST,RequestMethod.GET})
	public Chain getChain(HttpServletRequest request,HttpServletResponse response){
		return chain;
	}
	
	@RequestMapping(value = "/addPeer", method = {RequestMethod.POST,RequestMethod.GET})
	public boolean addPeer(@RequestBody String peerIP,HttpServletRequest request,HttpServletResponse response){
		boolean status = false;
		try {
			synchronizePeers(peerIP);
			peers.put(peerIP, peerIP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public void synchronizePeers(String peer){
		String uri = "http://"+peer+":7070/SBC/chain";
		String url = "http://"+peer+":7070/SBC/getChain";
		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.postForObject(uri, chain, String.class);
			Chain rchain = restTemplate.getForObject(url, Chain.class);
			if (bc.shouldReplaceWithChain(rchain)){
				chain = rchain;
			}
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
	}
	
}
