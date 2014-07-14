package com.ait.toolkit.ace.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface AceResources extends ClientBundle {

	@Source("ace.js")
	public TextResource aceJs();

	@Source("ext-keybinding_menu.js")
	public TextResource keyBindingMenu();

	@Source("ext-language_tools.js")
	public TextResource languageTools();

	@Source("ext-settings_menu.js")
	public TextResource settingsMenu();

}
