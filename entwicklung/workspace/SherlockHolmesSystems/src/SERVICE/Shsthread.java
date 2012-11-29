package SERVICE;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import CONTROLLER.Controller;

public class Shsthread extends Thread {
	
	@Override
	public void run() {
		String 
		curname = Thread.currentThread().getName();
		
		
		if(curname.equals(Controller.shsconfig.threadinternal)){
			Controller.shsconfig.loadinternalview();
		}else if(curname.equals(Controller.shsconfig.threadexternal)){
			Controller.shsconfig.loadexternalview();
		}
				
	}
}
