package test;

import java.io.File;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.crypto.Cipher;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import CONTROLLER.Controller;
import MODEL.Database;
import SERVICE.Config;
import SERVICE.Myadmin;


public class Launch {

	/**
	 * @param args
	 * @throws Base64DecodingException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Base64DecodingException, SQLException{
		//Dieser Test ist gultig und erfolgreich
		
		String username = "luis11",password="luis";
		HashMap<String, String> attribute = new HashMap<String, String>();
		int i = 0;
		
		if(i == 0){
		
			attribute.put(Controller.shsconfig.username, username);
			attribute.put(Controller.shsconfig.password, password);
			attribute.put(Controller.shsconfig.language, "eng");
		
		Controller.shsuser = Controller.shsconfig.loginSHS("signup",attribute);
		//Controller.shsconfig.createfolder("test1");
		}else if(i == 1){
		
			attribute.put(Controller.shsconfig.username, username);
			attribute.put(Controller.shsconfig.password, password);
			Controller.shsuser = Controller.shsconfig.loginSHS("signin",attribute);
		
		}else{
			System.out.println('~'-'"');
			
		}
		

		File file = new File("C:/Users/x3ro/Desktop/hallo.txt");
		
		Controller.shsconfig.uploadfile(file);
		
		int fileId = 21;
		String[] userlist = {"patrick5","patrick7"};
		
		byte[] 
		sent_by = ((String) Controller.shsuser.getattr(Controller.shsconfig.username)).getBytes(),
		sent_to, his_publickey, pseudokey=null, filename, temp, _ticketsId,_readers,_fileId;
		ResultSet result = null;
		result = Controller.shsdb.select(Controller.shsconfig.pubkeytb, "username,`key`","username LIKE 'patrick5'");//QUERY username kommt aus public_key_tb
		result.next();
		his_publickey = Base64.decode(result.getString("key"));
		String
		ticketsId="",readers="",_temp[];
		System.out.println("h1");
		sent_to = result.getString("username").getBytes();
		
		try {
		byte[] data = "test".getBytes("UTF8");
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(512);
		KeyPair keyPair = kpg.genKeyPair();
		// No installed provider supports this key: javax.crypto.spec.SecretKeySpec
		X509EncodedKeySpec spec = new X509EncodedKeySpec(his_publickey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory.generatePublic(spec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] encrypted = cipher.doFinal(data);
		}catch(Exception e){}
		
		sent_to = Controller.shscipher.crypt(sent_to,his_publickey,Controller.shsconfig.asymInstance,Controller.shsconfig.encryptmode);
		
		sent_by = Controller.shscipher.crypt(sent_by,his_publickey,Controller.shsconfig.asymInstance,Controller.shsconfig.encryptmode);
		
		result = Controller.shsdb.select(Controller.shsconfig.filestb,"`key`,pathdef","id = "+fileId);//QUERY
		result.next();
		pseudokey = Controller.shscipher.crypt(Base64.decode(result.getString("key")),Controller.shsconfig.symInstance, Controller.shsconfig.decryptmode);
		pseudokey = Controller.shscipher.crypt(pseudokey,his_publickey,Controller.shsconfig.asymInstance,Controller.shsconfig.encryptmode);
		System.out.println("h2");
		temp = Controller.shscipher.crypt(Base64.decode(result.getString("pathdef")),Controller.shsconfig.symInstance,Controller.shsconfig.decryptmode); 
		_temp = new String(temp).split(Controller.shsconfig.sep);
		filename = Controller.shscipher.crypt(_temp[_temp.length-1].getBytes(),his_publickey,Controller.shsconfig.asymInstance,Controller.shsconfig.encryptmode);
		
		_fileId = Controller.shscipher.crypt((""+fileId).getBytes(),his_publickey,Controller.shsconfig.asymInstance,Controller.shsconfig.encryptmode);
		
		
		HashMap<String,byte[]> _toinsert = new HashMap<String, byte[]>();
		_toinsert.put("sent_by",sent_by);
		_toinsert.put("sent_to",sent_to);
		_toinsert.put("fileId",_fileId);
		_toinsert.put("filename",filename);
		_toinsert.put("`key`",pseudokey);
		HashMap<String,String> toinsert = new HashMap<String,String>();
		
		for (String key : _toinsert.keySet()) {
			toinsert.put(key,"'"+Base64.encode(_toinsert.get(key))+"'");
		}
		
		Controller.shsdb.insert(Controller.shsconfig.tickettb, toinsert,Controller.shsdb.text(30));	
		
		Controller.shsconfig.createticket(fileId, userlist);
		
		
	
	}

}
