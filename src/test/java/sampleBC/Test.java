package sampleBC;

import java.security.NoSuchAlgorithmException;

import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.sampleBC.Block;

public class Test {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		/*String url = "http://192.168.1.66:7070/SBC/createBlock";
		RestTemplate restTemplate = new RestTemplate();
		Block response = restTemplate.postForObject(url, "More Test Block", Block.class);
		System.out.println(new Gson().toJson(response));*/
		
		String url = "http://192.168.1.66:7070/SBC/addPeer";
		RestTemplate restTemplate = new RestTemplate();
		boolean response = restTemplate.postForObject(url, "192.168.1.67", boolean.class);
		System.out.println(new Gson().toJson(response));
	}

}
