package com.ait.toolkit.ace.client;

import com.ait.toolkit.core.client.JsObject;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Configuration options for ACE
 * 
 * @author Alain Ekambi
 * 
 */
public class AceConfig extends JsObject {

	AceConfig(JavaScriptObject peer) {
		jsObj = peer;
	}

	public native void set(String property, String value)/*-{
		var peer = this.@com.ait.toolkit.core.client.JsObject::getJsObj()();
		peer.set(property, value);
	}-*/;

}
