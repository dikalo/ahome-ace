/*
 Copyright (c) 2014 Ahomé Innovation Technologies. All rights reserved.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
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
