package SERVICE;

import MODEL.Config;
import MODEL.GUI;
import MODEL.enums.Direction;

public class HTML implements GUI {

		
	@Override
	public String createInput(String type,String id,String value,int length) {
		String input = "<input type=\""+type+"\" id=\""+id+"\" value=\""+value+"\"";	
		if(length > 0){
			input += " size=\""+length+"\"";
		}
		input +="\\>";
		return input;
	}
	
	@Override
	public String createInput(String type,String id,String value) {
		return createInput(type,id,value,0);
	}
	
	public String createSelect(){
		return "";
	}
	
	
	@Override
	/**
	 * @author Shazem. Die Methode ist funktionsbereit
	 */
	public String space(int i,Direction d){
		String toreturn="",space="";
		if(d.equals(Direction.horiz)||d.equals(Direction.up)||d.equals(Direction.down)){
			space = "&nbsp;";
		}else if(d.equals(Direction.verti)||d.equals(Direction.left)||d.equals(Direction.right)){
			space = "<br/>";
		}
		for (int j = 0; j < i; j++) {
			toreturn +=space;
		}
		return toreturn;
	}
	
	
	@Override
	public void triggernotice(Exception e){
		String a = Config.progressId;
		
		//ruft die züstandige javascript-Methode auf
	}

	@Override
	public String createRadiobuttons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createDropdown() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
