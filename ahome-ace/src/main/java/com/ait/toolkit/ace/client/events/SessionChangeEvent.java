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
import com.ait.toolkit.ace.client.AceEditorSession;
import com.ait.toolkit.core.client.JsObject;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.DomEvent.Type;

/**
 * Emitted whenever the document is changed.
 * 
 * @author Alain Ekambi
 * 
 */
public class SessionChangeEvent extends JsObject {

	public static String EVENT_NAME = "changeSession";

	private AceEditor source;
	private AceEditorSession oldSession;
	private AceEditorSession session;

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

	public SessionChangeEvent(JavaScriptObject jsObj) {
		super(jsObj);
	}

	public SessionChangeEvent(AceEditor source, AceEditorSession oldSession, AceEditorSession session) {
		this.source = source;
		this.oldSession = oldSession;
		this.session = session;
	}

	/**
	 * @return the source
	 */
	public AceEditor getSource() {
		return source;
	}

	public AceEditorSession getOldSession() {
		return this.oldSession;
	}

	public AceEditorSession getSession() {
		return this.session;
	}

}
