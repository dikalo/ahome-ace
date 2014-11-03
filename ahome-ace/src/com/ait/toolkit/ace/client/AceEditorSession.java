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
 * Stores all the data about {@link AceEditor} state providing easy way to change editors state.
 * 
 * @author Alain Ekambi
 * 
 */
public class AceEditorSession extends JsObject {

	protected AceEditorSession(JavaScriptObject obj) {
		jsObj = obj;
	}

	public native void setFoldStyle(String value)/*-{
		var peer = this.@com.ait.toolkit.core.client.JsObject::getJsObj()();
		if (peer.setFoldStyle) {
			peer.setFoldStyle(value);
		}
	}-*/;

	public native AceEditorDocument getDocument()/*-{
		var peer = this.@com.ait.toolkit.core.client.JsObject::getJsObj()();
		var obj = peer.getDocument();
		if (!obj) {
			return null;
		}
		return @com.ait.toolkit.ace.client.AceEditorDocument::new(Lcom/google/gwt/core/client/JavaScriptObject;)(obj);
	}-*/;

	// TODO
	// Finish me

}
