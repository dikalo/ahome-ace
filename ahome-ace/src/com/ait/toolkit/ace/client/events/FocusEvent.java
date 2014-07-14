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
package com.ait.toolkit.ace.client.events;

import com.ait.toolkit.ace.client.AceEditor;
import com.ait.toolkit.core.client.JsObject;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.DomEvent.Type;

/**
 * Emitted once the editor has been blurred.
 * 
 * @author Alain Ekambi
 * 
 */
public class FocusEvent extends JsObject {

	public static String EVENT_NAME = "focus";

	private AceEditor source;

	/**
	 * UiBinder implementations
	 */
	private static Type<BlurHanler> TYPE = new Type<BlurHanler>(EVENT_NAME, null);

	public static Type<BlurHanler> getType() {
		return TYPE;
	}

	public static Type<BlurHanler> getAssociatedType() {
		return TYPE;
	}

	public FocusEvent(JavaScriptObject jsObj) {
		super(jsObj);
	}

	public FocusEvent(AceEditor source) {
		this.source = source;
	}

	/**
	 * @return the source
	 */
	public AceEditor getSource() {
		return source;
	}

}
