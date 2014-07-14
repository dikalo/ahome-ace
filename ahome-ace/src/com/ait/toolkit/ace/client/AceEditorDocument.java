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
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Contains the text of the document. Document can be attached to several EditSessions. At its core, Documents are just an array of strings, with each row in the document matching
 * up to the array index.
 * 
 * @author Alain Ekambi
 * 
 */
public class AceEditorDocument extends JsObject {

	protected AceEditorDocument(JavaScriptObject obj) {
		jsObj = obj;
	}

	// TODO
	// Finish me

}
