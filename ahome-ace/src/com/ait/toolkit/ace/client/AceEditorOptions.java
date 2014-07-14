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
package com.ait.toolkit.ace.client;

import com.ait.toolkit.core.client.JsObject;
import com.ait.toolkit.core.client.JsoHelper;

/**
 * Options for the ACE Editor
 * 
 * @author Alain Ekambi
 * 
 */
public class AceEditorOptions extends JsObject {

	public AceEditorOptions() {
		jsObj = JsoHelper.createObject();
	}

	public void setEnableBasicAutocompletion(boolean value) {
		JsoHelper.setAttribute(jsObj, "enableBasicAutocompletion", value);
	}

	public void setEnableSnippets(boolean value) {
		JsoHelper.setAttribute(jsObj, "enableSnippets", value);
	}

	public void setEnableLiveAutocompletion(boolean value) {
		JsoHelper.setAttribute(jsObj, "enableLiveAutocompletion", value);
	}
}
