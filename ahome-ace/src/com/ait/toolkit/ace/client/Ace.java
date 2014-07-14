package com.ait.toolkit.ace.client;

import com.ait.toolkit.ace.client.resources.AceResources;
import com.ait.toolkit.core.client.Util;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.shared.GWT;

/**
 * The main class required to set up an Ace instance in the browser.
 * 
 * @author Alain Ekambi
 * 
 */
public class Ace {

	private static final AceResources resources = GWT.create(AceResources.class);

	public static void load() {
		if (!isLoaded()) {
			ScriptInjector.fromString(resources.aceJs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
			ScriptInjector.fromString(resources.keyBindingMenu().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
			ScriptInjector.fromString(resources.languageTools().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
			ScriptInjector.fromString(resources.settingsMenu().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
			setBasePath(Util.getModuleBaseUrl() + "ace/");
		}
	}

	public static AceConfig getConfig() {
		if (isLoaded()) {
			return new AceConfig(_getConfig());
		}
		return null;
	}

	private static native JavaScriptObject _getConfig()/*-{
		return $wnd.ace.config;
	}-*/;

	public static native boolean isLoaded()/*-{
		if (typeof $wnd.ace === "undefined" || $wnd.ace === null) {
			return false;
		}
		return true;
	}-*/;

	private static native void setBasePath(String path)/*-{
		$wnd.ace.config.set("basePath", path);
	}-*/;

}
