/**
 * @LUIS 04.11.2012 ---> 
 * Hier sind die Methoden die wir Entwickler von euch brauchen werden
 * Ihr dürft euch natürlich sachen aus dem Netz hollen solange die Funktionalitäten erfüllt werden
 * Ich unterstütze euch auch gerne.
 * 
 * data = file oder ordner
 * 
 *  */


function simpleToggle(id){
	$('#'+id).fadeToggle('1');	
}

function simpleToggle(id,duration){
	//duration ="'"+duration+"'";
	$('#'+id).fadeToggle(duration);	
}

function blockToggle(imgId,blockId){//Aufgepasst diese Methode kann nur für speziell angepasste img-Tags optimal funktionieren. 
	simpleToggle(blockId);
	temp1 = $('#'+imgId).attr('src');
	temp2 = $('#'+imgId).attr('alt');//alert($('#'+imgId).attr('src')+'\n'+imgId);
	//Es wird vorausgesetzt dass das Attribut 'alt' den alternativen Pfad enthält
	$('#'+imgId).attr('alt',temp1);
	$('#'+imgId).attr('src',temp2);
	//alert($('#shsbody').css('width'));
}

function popup(){
	//Hier sollte ihr besonders darauf achten dass das Popup-fenster erzeugt werden sollte wo der Cursor (die Maus) sich gerade befindet :) 
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


/////////////////LISTENERS-START//////////////////////////
function listener(listenerMapper){
	toeval = 'function '+listenerMapper+'listener(';
	
	toeval += ')';
	eval(toeval);
}

function ajaxlistener(){
	
}

function windowlistener(){
	
} 
/////////////////LISTENERS-END//////////////////////////

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
	$(function(){alert($('#'+mainwestId).css('width') +'---'+$('#'+bodyId).css('width'));
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