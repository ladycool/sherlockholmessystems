package SERVICE;


import CONTROLLER.Controller;

public class Shsthread extends Thread {
	
	@Override
	public void run() {
		String curname = Thread.currentThread().getName();	
		
		if(curname.equals(Controller.shsconfig.threadinternal)){
			Controller.shsconfig.loadinternalview();
		}else if(curname.equals(Controller.shsconfig.threadexternal)){
			Controller.shsconfig.loadexternalview();
		}
				
	}
}
