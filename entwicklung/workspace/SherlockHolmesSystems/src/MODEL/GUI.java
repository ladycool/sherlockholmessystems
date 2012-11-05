package MODEL;

import MODEL.enums.Direction;

public interface GUI {
	/**
	 * @doc <input/>-Feld in HTML. Diese Methode sollte bei Input-Tags benutzt werden die keine Länge benötigen
	 * @param type: <input type=""
	 * @param id: <input id=""
	 * @param value: <input value=""
	 * @return <input type="type" id="id" value="value"/>
	 */
	public String createInput(String type,String id,String value);
	
	/**
	 * @doc <input/>-Feld in HTML. Diese Methode sollte bei Input-Tags benutzt werden die keine Länge benötigen
	 * @param type: <input type=""
	 * @param id: <input id=""
	 * @param value: <input value=""
	 * @param onclick
	 * @return <input type="type" id="id" value="value"/>
	 */
	public String createInput(String type,String id,String value,String onclick);
	
	/**
	 * @doc <input/>-Feld in HTML. Diese Methode sollte bei Input-Tags benutzt werden die eine bestimmte Länge benötigen
	 * @param type: <input type=""
	 * @param id: <input id=""
	 * @param value: <input value=""
	 * @param length: <input length=""
	 * @param onclick
	 * @return <input type="type" id="id" value="value"/>
	 */
	public String createInput(String type,String id,String value,int length,String onclick);
	

	/**
	 * @doc <input/>-Feld in HTML. Diese Methode sollte bei Input-Tags benutzt werden die eine bestimmte Länge benötigen
	 * @param type: <input type=""
	 * @param id: <input id=""
	 * @param length: <input length=""
	 * @return <input type="type" id="id" length="length"/>
	 */
	public String createInput(String type,String id,int length);
	/**
	 * @doc Analog zu der Methode: createInput. Zusatzlich wird hier eine Datenbank verbindung mit der Tabelle "element_names" aufgebaut.
	 * @return
	 */
	public String createSelect();
	
	/**
	 * @doc Analog zu der Methode: createInput. Zusatzlich wird hier eine Datenbank verbindung mit der Tabelle "element_names" aufgebaut.
	 * @return
	 */
	public String createRadiobuttons();
	
	/**
	 * @doc Analog zu der Methode: createInput. Zusatzlich wird hier eine Datenbank verbindung mit der Tabelle "element_names" aufgebaut.
	 * @return
	 */
	public String createDropdown();
	
	/**
	 * @doc Erzeugt je nach ausgewähte Richtung Leerraum.
	 * @param i: Anzahl der "Freiräume" die erzeugt werden sollte.
	 * @param d: Richtung in der, der Freiraum erzeugt werden sollte -verti für senkrecht, -horiz für waagerecht
	 * @return: Leerraum
	 */
	public String space(int i,Direction d);
	
	/**
	 * @doc Selbsterklärend
	 * @param id
	 * @param href
	 * @param mouseover
	 * @param mouseout
	 * @param otherEvents Bsp: "ondbleclick=\"do something\""
	 * @param textId
	 * @return String: <a></a>
	 */
	public String createA(String id,String click,String mouseover,String mouseout,String otherEvents,int textId);
	
	/**
	 * @doc Vereinfachte Form der Methode: createA(String id,String href,String mouseover,String mouseout,String event,int textId)
	 * @param href
	 * @param mouseover
	 * @param mouseout
	 * @param textId
	 * @return String: <a></a>
	 */
	public String createA(String click,String mouseover,String mouseout,int textId);
	
	/**
	 * doc Vereinfachte Form der Methode: createA(String id,String href,String mouseover,String mouseout,String event,int textId)
	 * @param click
	 * @param textId
	 * @return String: <a></a>
	 */
	public String createA(String click,int textId);
	
	/**
	 * @deprecated Für den Moment
	 * @param e
	 */
	public void triggernotice(Exception e);
}
