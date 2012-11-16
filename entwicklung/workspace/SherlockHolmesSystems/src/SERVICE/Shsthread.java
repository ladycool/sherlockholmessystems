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
				
		boolean allthreadsaredone = true;
		for (Thread t : Shsthread.getAllStackTraces().keySet()) {
			if(!t.equals(Thread.currentThread()) && t.getState() != State.WAITING){
				allthreadsaredone = false;
				break;
			}
		}
		
		if(allthreadsaredone){
			Thread.currentThread().notifyAll();
			Controller.shsconfig.setloadingisdone();
		}else{
			try {
				Controller.shsconfig.setloadingisdone();
				Thread.currentThread().wait();
			} catch (InterruptedException e) {
				Controller.shsgui.triggernotice(e);
			}
		}
		//Controller.shsconfig.setinfo(key, value);
	}
}
