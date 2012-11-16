package SERVICE;

import SERVICE.Config;
import CONTROLLER.Controller;
import MODEL.GUI;
import MODEL.enums.Direction;

public class HTML implements GUI {
	
	/*
	 * PRIVATE
	 */
	private static HTML _html = new HTML();
	private HTML(){
		super();//do nothing
	}
	
	/**
	 * @doc Erzeugt einen Singelton
	 * @return
	 */
	public static HTML getInstance(){
		return HTML._html;
	}
	
	
	@Override
	public String createInput(String type,String id,String value,int length,String onclick) {
		String input = "<input type=\""+type+"\" id=\""+id+"\" name=\""+id+"\" value=\""+value+"\"";	
		if(length > 0){
			input += " size=\""+length+"\"";
		}
		if(!onclick.isEmpty()){
			input += " onclick=\""+onclick+"\"";
		}
		input +="/>";
		return input;
	}
	
	@Override
	public String createInput(String type,String id,String value,String onclick) {
		return createInput(type,id,value,0,onclick);
	}
	
	@Override
	public String createInput(String type,String id,String value) {
		return createInput(type,id,value,0,"");
	}
	
	@Override
	public String createInput(String type,String id,int length) {
		return createInput(type,id,"",length,"");
	}
	
	@Override
	public String defaultTXTInput(String id){
		return createInput("text",id,"",50,"") ;
	}
	
	@Override
	public String createSelect(){
		return "";
	}
	
	@Override
	public String createImg(String imgId,String blockId,String src,String alt,int height,int width,String event,boolean resizeable){
		//Beispiel: <img alt="images/arrowRIGHT.png"" src="images/arrowLEFT.png" onclick="blockToggle('<%=Config.mainwestId%>')">
		String toprint = "<img id=\""+imgId+"\" alt=\""+alt+"\" src=\""+src+"\"";
		if(!event.equals("")){
			toprint += event;
		}
		if(height > 0 && width > 0){
			toprint += "height=\""+height+"\" width=\""+width+"\"";
		}
		if(resizeable){
			toprint += "onmouseover=\"$(this).attr('height',5*$(this).attr('height')/4);$(this).attr('width',5*$(this).attr('width')/4)\"";
			toprint += "onmouseout=\"$(this).attr('height',4*$(this).attr('height')/5);$(this).attr('width',4*$(this).attr('width')/5)\"";
		}
		toprint += ">";		
		return toprint;
	}
	
	@Override
	public String createImg(String imgId,String blockId,String src,String alt,String direction){//Speziel f�r die Pfeile
		int height = 20, width = 20;
		
		//Beispiel: <img alt="images/arrowRIGHT.png"" src="images/arrowLEFT.png" onclick="blockToggle('<%=Config.mainwestId%>')">
		String toprint = "<img id=\""+imgId+"\" alt=\""+alt+"\" src=\""+src+"\" onclick=\"blockToggle('"+imgId+"','"+blockId+"','"+direction+"')\"";
		toprint += "height=\""+height+"\" width=\""+width+"\"";
		toprint += "onmouseover=\"$(this).attr('height',5*$(this).attr('height')/4);$(this).attr('width',5*$(this).attr('width')/4)\"";
		toprint += "onmouseout=\"$(this).attr('height',4*$(this).attr('height')/5);$(this).attr('width',4*$(this).attr('width')/5)\"";
		toprint += ">";		
		return toprint;
	}
	
	@Override
	public String createImg(String imgId,String blockId,String src){
		return createImg(imgId,blockId,src,"",0,0,"",false);
	}
	
	@Override
	/**
	 * @author Shazem. Die Methode ist funktionsbereit
	 */
	public String space(int i,Direction d){
		String toreturn="",space="";
		if(d.equals(Controller.shsconfig.horiz)||d.equals(Controller.shsconfig.up)||d.equals(Controller.shsconfig.down)){
			space = "&nbsp;";
		}else if(d.equals(Controller.shsconfig.verti)||d.equals(Controller.shsconfig.left)||d.equals(Controller.shsconfig.right)){
			space = "<br/>";
		}
		for (int j = 0; j < i; j++) {
			toreturn +=space;
		}
		return toreturn;
	}
	
	
	@Override
	public void triggernotice(Exception e){
		//Spezielfall der Methode triggernotice(String message);
		
		String tosend = e.getMessage();
		triggernotice(tosend);
	}

	@Override
	public void triggernotice(String message){
		String a = Controller.shsconfig.consoleId;
		//ruft die z�standige javascript-Methode auf
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
	
	@Override
	public String createA(String id,String click,String mouseover,String mouseout,String otherEvents,int textId){
		String toreturn = "<a ";
		if(!id.isEmpty()){
			//id += "_a";
			toreturn ="id=\""+id+"_a\" ";
		}
		toreturn += "onmouseover=\"";
		if(!mouseover.isEmpty()){
			toreturn += mouseover+";";
		}
		toreturn += "$(this).css({'cursor':'pointer','color':'blue'});\" ";
		
		toreturn += "onmouseout=\"";
		if(!mouseout.isEmpty()){
			toreturn += mouseout+";";
		}
		toreturn += "$(this).css({'cursor':'auto','color':'black'});\" ";
		
		if(!click.isEmpty()){
			toreturn += "onclick=\""+click+"\" ";
		}
		
		toreturn +=otherEvents+">"+Controller.shsdb.text(textId)+"</a>";
		
		return toreturn;
	}
		
	@Override
	public String createA(String click,String mouseover,String mouseout,int textId){
		return createA("",click, mouseover, mouseout,"", textId);
	}
	
	@Override
	public String createA(String click, int textId) {
		return createA("",click,"","","",textId);
	}

	@Override
	public String createTextarea(String id,String rows,String cols,String initval) {
		String toprint="<textarea rows=\""+rows+"\" cols=\""+cols+"\">"+initval+"</textarea>";		
		return toprint;
	}
}
