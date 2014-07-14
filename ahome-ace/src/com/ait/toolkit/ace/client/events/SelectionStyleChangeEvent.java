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
 * Emitted whenever the document is changed.
 * 
 * @author Alain Ekambi
 * 
 */
public class SelectionStyleChangeEvent extends JsObject {

	public static String EVENT_NAME = "changeSelectionStyle";

	private AceEditor source;
	private String data;

	/**
	 * UiBinder implementations
	 */
	private static Type<ChangeHandler> TYPE = new Type<ChangeHandler>(EVENT_NAME, null);

	public static Type<ChangeHandler> getType() {
		return TYPE;
	}

	public static Type<ChangeHandler> getAssociatedType() {
		return TYPE;
	}

	public SelectionStyleChangeEvent(JavaScriptObject jsObj) {
		super(jsObj);
	}

	public SelectionStyleChangeEvent(AceEditor source, String data) {
		this.source = source;
		this.data = data;
	}

	/**
	 * @return the source
	 */
	public AceEditor getSource() {
		return source;
	}

	/**
	 * @return the data associated to the change
	 */
	public String getData() {
		return this.data;
	}

}
