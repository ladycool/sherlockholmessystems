/**
 * @LUIS 04.11.2012 ---> 
 * Hier sind die Methoden die wir Entwickler von euch brauchen werden
 * Ihr dürft euch natürlich sachen aus dem Netz hollen solange die Funktionalitäten erfüllt werden
 * Ich unterstütze euch auch gerne.
 * 
 * data = file oder ordner
 * 
 *  */

//Alternative
function simpleToggle(id,duration){
	//duration ="'"+duration+"'";
	$('#'+id).fadeToggle(duration);
}

function animateToggle(id,direction){
	animateToggle(id,direction,50);	
}

function animateToggle(id,direction,speed){
	if(direction == 'horiz'){
		$('#'+id).animate({width:'toggle'},speed);
	}else if(direction == 'verti'){
		$('#'+id).animate({height:'toggle'},speed);
	}	
}

function blockToggle(imgId,blockId,direction){//Aufgepasst diese Methode kann nur für speziell angepasste img-Tags optimal funktionieren. 
	animateToggle(blockId, direction)
	temp1 = $('#'+imgId).attr('src');
	temp2 = $('#'+imgId).attr('alt');//alert($('#'+imgId).attr('src')+'\n'+imgId);
	//Es wird vorausgesetzt dass das Attribut 'alt' den alternativen Pfad enthält
	$('#'+imgId).attr('alt',temp1);
	$('#'+imgId).attr('src',temp2);
	//alert($('#shsbody').css('width'));
}


function fupload(){
	/**
	 * Das habe ich online gefunden...hoffentlich hilft es.
	 * 
	 * <form id="file_upload_form" method="post" enctype="multipart/form-data" action="upload.aspx">
		    <input name="file" id="file" size="27" type="file" /><br />
		    <input type="submit" name="action" value="Upload" /><br />
		    <iframe id="upload_target" name="upload_target" src="" style="width:0;height:0;border:0px solid #fff;"></iframe>
		</form>
	 */
}



///////////////DATA-MANAGEMENT-START////////////////////
function datadelete(){
	
}

function datarename(){
	
}

function datashare(){
	
}

function dataunshare(){
	
}
///////////////DATA-MANAGEMENT-END////////////////////


function triggernotice(){
	
}

function documentready(bodyId,mainwestId){
	newwidth = $('#'+bodyId).css('width')*10/100;
	//$(document).ready
	$(function(){
		$('#'+mainwestId).css('width',newwidth);
	}); 
	//shsresize(bodyId,mainwesttdId,percentage);
}

function shsresize(bodyId,mainwesttdId,percentage){
	//$(selector).resize(function) 
	toeval = "$(window).resize(function() {";
	toeval += "$('#"+ mainwesttdId + "').css('width',$('#" + bodyId + "').css('width')*"+percentage+");";
	toeval += "});";
	
	/*
	$(window).resize(function() {
		$('#'+mainwestId).css('width',$('#'+bodyId).css('width'));
	});
	*/
}

/**
 * @author Shazem
 * @param event: Das onclick-Even
 * @param path: Dynamischer Pfad zum "ajaxhandler,jsp"-Skript
 * @param popupdim: Json-Objekt das aus der Länge und der Breite des Popups besteht
 * 					Bsp: {width:100,heigth:300}
 * @param ajaxdata: Json-Objekt das aus den zu übermittelnden Parametern besteht
 * 					Bsp: {name:"john",location:"mannheim"}
 */
function popup(event,popupdim,ajaxdata){
	additional = 10;
	body = "#shsbody";
	popupid = "#popup";
	popupwidth = popupdim.width;
	popupheight = popupdim.height;
	var popupleft = 0;
	var popuptop = 0;
	px = "px";
	
	maxwidth = $(body).css('width');
	maxheight = $(body).css('height');
	if(event.pageX || event.pageY){//FIREFOX, CHROME
		popupleft = event.pageX;
		popuptop = event.pageY;
	}else if(window.event){//IE
		popupleft = window.event.clientX;
		popuptop = window.event.clientY;
	}
	
	var marginX = parseInt(maxwidth) - popupleft - additional;
	var marginY = parseInt(maxheight) - popuptop - additional;
		
	if(popupwidth > marginX){
		popupleft -= popupwidth;
	}
	if(popupheight > marginY){
		popuptop -= popupheight;
	}
	
	updatecontainer(popupid,ajaxdata);
	
	popupleft +=px;
	popuptop +=px;
	popupwidth +=px;
	popupheight +=px;
	$(popupid).css({
			left: popupleft,
			top: popuptop,
			width: popupwidth,
			height: popupheight
	      }).show('50');
	//hide() -> to shut it down	
	//http://www.joelpeterson.com/blog/2010/12/quick-and-easy-windowless-popup-overlay-in-jquery/
	//show(), hide(), popup(), fadein(), fadeout()
}


function updatecontainer(containerId,ajaxdata){
	if(containerId.charAt(0) != "#"){containerId = "#"+containerId;}
	var toreplace = "CLOSETAG";
	var closetag = "";
	
	$.ajax({
		type: "POST",
		cache: true,
		dataType: "html",
		data: ajaxdata,
		url: "VIEW/VIEWCONTROLLER/ajaxhandler.jsp",
		success:function(text){
			text = text.replace(toreplace,closetag);
			$(containerId).html(text);
		}
	});
}

