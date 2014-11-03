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

import com.ait.toolkit.ace.client.events.BlurHanler;
import com.ait.toolkit.ace.client.events.ChangeHandler;
import com.ait.toolkit.ace.client.events.CopyHandler;
import com.ait.toolkit.ace.client.events.FocusHandler;
import com.ait.toolkit.ace.client.events.HandlerRegistration;
import com.ait.toolkit.ace.client.events.PasteHandler;
import com.ait.toolkit.ace.client.events.SelectionStyleChangeHandler;
import com.ait.toolkit.ace.client.events.SessionChangeHandler;
import com.ait.toolkit.core.client.JsoHelper;
import com.ait.toolkit.core.client.Util;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RequiresResize;

/**
 * A GWT widget for the ACE Web Editor.
 * 
 * @see <a href="http://ace.c9.io/#nav=about">ACE Web Code Editor</a>
 */
public class AceEditor extends Composite implements RequiresResize, HasText, TakesValue<String> {
	// Used to generate unique element ids for Ace widgets.
	private static int nextId = 0;

	private final String elementId;

	private JavaScriptObject editor;
	private JavaScriptObject modeScriptElement;
	private JavaScriptObject themeScriptElement;

	private JsArray<AceAnnotation> annotations = JavaScriptObject.createArray().cast();

	private Element divElement;

	static {
		Ace.load();
	}

	/**
	 * Constructor.
	 */
	public AceEditor() {
		elementId = "_ahomeAce" + nextId;
		nextId++;
		FlowPanel div = new FlowPanel();
		div.getElement().setId(elementId);
		initWidget(div);
		divElement = div.getElement();
		startEditor();
	}

	private native void startEditor() /*-{
		var editor = $wnd.ace
				.edit(this.@com.ait.toolkit.ace.client.AceEditor::divElement);
		//editor.setOptions({
		//enableBasicAutocompletion : true,
		//enableSnippets : true,
		//enableLiveAutocompletion : true
		//});

		$wnd.ace.require("ace/ext/keybinding_menu").init(editor);
		$wnd.ace.require('ace/ext/settings_menu').init(editor);
		$wnd.ace.require("ace/ext/language_tools");

		this.@com.ait.toolkit.ace.client.AceEditor::editor = editor;
		editor.resize();
		this.@com.ait.toolkit.ace.client.AceEditor::redisplay();
	}-*/;

	public native void setUserWorker(boolean value)/*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.getSession().setUseWorker(value);
	}-*/;

	/**
	 * Call this to force the editor contents to be redisplayed. There seems to be a problem when an AceEditor is embedded in a LayoutPanel: the editor contents don't appear, and
	 * it refuses to accept focus and mouse events, until the browser window is resized. Calling this method works around the problem by forcing the underlying editor to redisplay
	 * itself fully. (?)
	 */
	public native void redisplay() /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.renderer.onResize(true);
		editor.renderer.updateFull();
		editor.resize();
		editor.focus();
	}-*/;

	/**
	 * Cleans up the entire editor.
	 */
	public native void destroy() /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.destroy();
	}-*/;

	/**
	 * Sets the options for this editor.
	 * 
	 * @param options
	 *            , the options of this editor.
	 */
	public native void setOptions(AceEditorOptions options) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor
				.setOptions(options.@com.ait.toolkit.core.client.JsObject::getJsObj()());
	}-*/;

	/**
	 * Returns the current session being used.
	 */
	public native AceEditorSession getSession()/*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var session = editor.session;
		if (!session) {
			return null;
		}
		return @com.ait.toolkit.ace.client.AceEditorSession::new(Lcom/google/gwt/core/client/JavaScriptObject;)(session);
	}-*/;

	/**
	 * Set the theme.
	 * 
	 * @param theme
	 *            the theme (one of the values in the {@link AceEditorTheme} enumeration)
	 */
	private void _setTheme(final AceEditorTheme theme) {
		setThemeByName(theme.getName());
	}

	/**
	 * Set the theme by name.
	 * 
	 * @param themeName
	 *            the theme name (e.g., "twilight")
	 */
	private native void setThemeByName(String themeName) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.setTheme("ace/theme/" + themeName);
	}-*/;

	public native void setShowInvisibles(boolean value) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.setShowInvisibles(value);
	}-*/;

	public native void setDisplayIndentGuides(boolean value) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.setDisplayIndentGuides(value);
	}-*/;

	public native void setHighlightActiveLine(boolean value) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.setHighlightActiveLine(value);
	}-*/;

	public native void setSelectionStyle(String value) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.setSelectionStyle(value);
	}-*/;

	/**
	 * Set the mode.
	 * 
	 * @param mode
	 *            the mode (one of the values in the {@link AceEditorMode} enumeration)
	 */
	private void _setMode(final AceEditorMode mode) {
		setModeByName(mode.getName());
	}

	/**
	 * Set the mode by name.
	 * 
	 * @param shortModeName
	 *            name of mode (e.g., "eclipse")
	 */
	private native void setModeByName(String shortModeName) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var modeName = "ace/mode/" + shortModeName;
		var TheMode = $wnd.require(modeName).Mode;
		editor.getSession().setMode(new TheMode());
	}-*/;

	/**
	 * Register a handler for change events generated by the editor.
	 * 
	 * @param callback
	 *            the change event handler
	 */
	public native void addOnChangeHandler(AceEditorCallback callback) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor
				.getSession()
				.on(
						"change",
						function(e) {
							callback.@com.ait.toolkit.ace.client.AceEditorCallback::invokeAceCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
						});
	}-*/;

	/**
	 * Register a handler for cursor position change events generated by the editor.
	 * 
	 * @param callback
	 *            the cursor position change event handler
	 */
	public native void addOnCursorPositionChangeHandler(AceEditorCallback callback) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.getSession().selection
				.on(
						"changeCursor",
						function(e) {
							callback.@com.ait.toolkit.ace.client.AceEditorCallback::invokeAceCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
						});
	}-*/;

	/**
	 * Set font size.
	 */
	public native void setFontSize(String fontSize) /*-{
		var elementId = this.@com.ait.toolkit.ace.client.AceEditor::elementId;
		var elt = $doc.getElementById(elementId);
		elt.style.fontSize = fontSize;
	}-*/;

	/**
	 * Get the complete text in the editor as a String.
	 * 
	 * @return the text in the editor
	 */
	public native String getText() /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		return editor.getSession().getValue();
	}-*/;

	/**
	 * Set the complete text in the editor from a String.
	 * 
	 * @param text
	 *            the text to set in the editor
	 */
	public native void setText(String text) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.getSession().setValue(text);
	}-*/;

	/**
	 * Insert given text at the cursor.
	 * 
	 * @param text
	 *            text to insert at the cursor
	 */
	public native void insertAtCursor(String text) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.insert(text);
	}-*/;

	/**
	 * Get the current cursor position.
	 * 
	 * @return the current cursor position
	 */
	public native AceEditorCursorPosition getCursorPosition() /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var pos = editor.getCursorPosition();
		return this.@com.ait.toolkit.ace.client.AceEditor::getCursorPositionImpl(DD)(pos.row, pos.column);
	}-*/;

	private AceEditorCursorPosition getCursorPositionImpl(final double row, final double column) {
		return new AceEditorCursorPosition((int) row, (int) column);
	}

	/**
	 * Set whether or not soft tabs should be used.
	 * 
	 * @param useSoftTabs
	 *            true if soft tabs should be used, false otherwise
	 */
	public native void setUseSoftTabs(boolean useSoftTabs) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.getSession().setUseSoftTabs(useSoftTabs);
	}-*/;

	/**
	 * Set tab size. (Default is 4.)
	 * 
	 * @param tabSize
	 *            the tab size to set
	 */
	public native void setTabSize(int tabSize) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.getSession().setTabSize(tabSize);
	}-*/;

	/**
	 * Go to given line.
	 * 
	 * @param line
	 *            the line to go to
	 */
	public native void gotoLine(int line) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.gotoLine(line);
	}-*/;

	/**
	 * Set whether or not the horizontal scrollbar is always visible.
	 * 
	 * @param hScrollBarAlwaysVisible
	 *            true if the horizontal scrollbar is always visible, false if it is hidden when not needed
	 */
	public native void setHScrollBarAlwaysVisible(boolean hScrollBarAlwaysVisible) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.renderer.setHScrollBarAlwaysVisible(hScrollBarAlwaysVisible);
	}-*/;

	/**
	 * Set whether or not the gutter is shown.
	 * 
	 * @param showGutter
	 *            true if the gutter should be shown, false if it should be hidden
	 */
	public native void setShowGutter(boolean showGutter) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.renderer.setShowGutter(showGutter);
	}-*/;

	public native void setAnimatedScroll(boolean value) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.setAnimatedScroll(value);
	}-*/;

	public native void setBehavioursEnabled(boolean value) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.setBehavioursEnabled(value);
	}-*/;

	public native void setFadeFoldWidgets(boolean value) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.setFadeFoldWidgets(value);
	}-*/;

	/**
	 * Set whether or not the gutter is shown.
	 * 
	 * @param showGutter
	 *            true if the gutter should be shown, false if it should be hidden
	 */
	public native void setShowFoldWidgets(boolean value) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		if (editor.setShowFoldWidgets) {
			editor.setShowFoldWidgets(value);
		}
	}-*/;

	/**
	 * Set or unset read-only mode.
	 * 
	 * @param readOnly
	 *            true if editor should be set to readonly, false if the editor should be set to read-write
	 */
	public native void setReadOnly(boolean readOnly) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.setReadOnly(readOnly);
	}-*/;

	/**
	 * Set or unset highlighting of currently selected word.
	 * 
	 * @param highlightSelectedWord
	 *            true to highlight currently selected word, false otherwise
	 */
	public native void setHighlightSelectedWord(boolean highlightSelectedWord) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.setHighlightSelectedWord(highlightSelectedWord);
	}-*/;

	/**
	 * Set or unset the visibility of the print margin.
	 * 
	 * @param showPrintMargin
	 *            true if the print margin should be shown, false otherwise
	 */
	public native void setShowPrintMargin(boolean showPrintMargin) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.renderer.setShowPrintMargin(showPrintMargin);
	}-*/;

	/**
	 * Add an annotation to a the local <code>annotations</code> JsArray<AceAnnotation>, but does not set it on the editor
	 * 
	 * @param row
	 *            to which the annotation should be added
	 * @param column
	 *            to which the annotation applies
	 * @param text
	 *            to display as a tooltip with the annotation
	 * @param type
	 *            to be displayed (one of the values in the {@link AceAnnotationType} enumeration)
	 */
	public void addAnnotation(final int row, final int column, final String text, final AceAnnotationType type) {
		annotations.push(AceAnnotation.create(row, column, text, type.getName()));
	}

	/**
	 * Set any annotations which have been added via <code>addAnnotation</code> on the editor
	 */
	public native void setAnnotations() /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var annotations = this.@com.ait.toolkit.ace.client.AceEditor::annotations;
		editor.getSession().setAnnotations(annotations);
	}-*/;

	public native void setFoldStyle(String value) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var session = editor.getSession();
		if (session && session.setFoldStyle) {
			session.setFoldStyle(value);
		}
	}-*/;

	/**
	 * Clear any annotations from the editor and reset the local <code>annotations</code> JsArray<AceAnnotation>
	 */
	public native void clearAnnotations() /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.getSession().clearAnnotations();
		this.@com.ait.toolkit.ace.client.AceEditor::resetAnnotations()();
	}-*/;

	/**
	 * Reset any annotations in the local <code>annotations</code> JsArray<AceAnnotation>
	 */
	private void resetAnnotations() {
		annotations = JavaScriptObject.createArray().cast();
	}

	/**
	 * Remove a command from the editor.
	 * 
	 * @param command
	 *            the command (one of the values in the {@link AceCommand} enumeration)
	 */
	public void removeCommand(final AceCommand command) {
		removeCommandByName(command.getName());
	}

	/**
	 * Remove commands, that may not me required, from the editor
	 * 
	 * @param command
	 *            to be removed, one of "gotoline", "findnext", "findprevious", "find", "replace", "replaceall"
	 */
	public native void removeCommandByName(String command) /*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		editor.commands.removeCommand(command);
	}-*/;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.ResizeComposite#onResize()
	 */
	@Override
	public void onResize() {
		redisplay();
	}

	@Override
	public void setValue(String value) {
		this.setText(value);
	}

	@Override
	public String getValue() {
		return this.getText();
	}

	public void setMode(final AceEditorMode mode) {
		final String modeId = "mode-" + mode.name().toLowerCase();
		String js = modeId + ".js";
		String path = Util.getModuleBaseUrl() + "ace/" + js;

		Element el = DOM.getElementById(modeId);
		if (el == null) {
			modeScriptElement = ScriptInjector.fromUrl(path).setCallback(new Callback<Void, Exception>() {
				@Override
				public void onSuccess(Void result) {
					JsoHelper.setAttribute(modeScriptElement, "id", modeId);
					AceEditor.this._setMode(mode);
				}

				@Override
				public void onFailure(Exception reason) {
				}
			}).setWindow(ScriptInjector.TOP_WINDOW).inject();

		} else {
			this._setMode(mode);

		}

	}

	public void setTheme(String themeName) {
		setTheme(AceEditorTheme.fromValue(themeName));
	}

	public void setMode(String modeName) {
		setMode(AceEditorMode.fromValue(modeName));
	}

	public void setTheme(final AceEditorTheme theme) {
		final String themeId = "theme-" + theme.name().toLowerCase();
		String js = themeId + ".js";
		String path = Util.getModuleBaseUrl() + "ace/" + js;

		Element el = DOM.getElementById(themeId);
		if (el == null) {
			themeScriptElement = ScriptInjector.fromUrl(path).setCallback(new Callback<Void, Exception>() {
				@Override
				public void onSuccess(Void result) {
					JsoHelper.setAttribute(themeScriptElement, "id", themeId);
					AceEditor.this._setTheme(theme);
				}

				@Override
				public void onFailure(Exception reason) {
				}
			}).setWindow(ScriptInjector.TOP_WINDOW).inject();

		} else {
			_setTheme(theme);
		}
	}

	// Events

	/**
	 * Emitted once the editor has been blurred.
	 * 
	 * @param handler
	 *            , the handler that will handler the event
	 * @return handler registration attached to this event handler
	 */
	public native com.ait.toolkit.ace.client.events.HandlerRegistration addBlurHandler(BlurHanler handler)/*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var event = @com.ait.toolkit.ace.client.events.BlurEvent::new(Lcom/ait/toolkit/ace/client/AceEditor;)(this);
		var fn = function() {
			handler.@com.ait.toolkit.ace.client.events.BlurHanler::onBlur(Lcom/ait/toolkit/ace/client/events/BlurEvent;)(event);
		};
		var eventName = @com.ait.toolkit.ace.client.events.BlurEvent::EVENT_NAME;
		editor.on(eventName, fn);

		var toReturn = @com.ait.toolkit.ace.client.events.HandlerRegistration::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(this,eventName,fn);
		return toReturn;
	}-*/;

	/**
	 * Emitted whenever the document is changed.
	 * 
	 * @param handler
	 *            , the handler that will handler the event
	 * @return handler registration attached to this event handler
	 */
	public native HandlerRegistration addChangeHandler(ChangeHandler handler)/*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var me = this;
		var fn = function(e) {
			var event = @com.ait.toolkit.ace.client.events.ChangeEvent::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;)(me, e.data);
			handler.@com.ait.toolkit.ace.client.events.ChangeHandler::onChange(Lcom/ait/toolkit/ace/client/events/ChangeEvent;)(event);
		};
		var eventName = @com.ait.toolkit.ace.client.events.ChangeEvent::EVENT_NAME;
		editor.on(eventName, fn);

		var toReturn = @com.ait.toolkit.ace.client.events.HandlerRegistration::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(this,eventName,fn);
		return toReturn;
	}-*/;

	/**
	 * Emitted when the selection style changes, via Editor.setSelectionStyle().
	 * 
	 * @param handler
	 *            , the handler that will handler the event
	 * @return handler registration attached to this event handler
	 */
	public native HandlerRegistration addSelectionStyleChangeHandler(SelectionStyleChangeHandler handler)/*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var me = this;
		var fn = function(e) {
			var event = @com.ait.toolkit.ace.client.events.SelectionStyleChangeEvent::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;)(me, e.data);
			handler.@com.ait.toolkit.ace.client.events.SelectionStyleChangeHandler::onSelectionStyleChange(Lcom/ait/toolkit/ace/client/events/SelectionStyleChangeEvent;)(event);
		};
		var eventName = @com.ait.toolkit.ace.client.events.SelectionStyleChangeEvent::EVENT_NAME;
		editor.on(eventName, fn);

		var toReturn = @com.ait.toolkit.ace.client.events.HandlerRegistration::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(this,eventName,fn);
		return toReturn;
	}-*/;

	/**
	 * Emitted when the selection style changes, via <code>Editor.setSelectionStyle()</code>.
	 * 
	 * @param handler
	 *            , the handler that will handler the event
	 * @return handler registration attached to this event handler
	 */
	public native HandlerRegistration addSessionChangeHandler(SessionChangeHandler handler)/*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var me = this;
		var fn = function(e) {
			var os = @com.ait.toolkit.ace.client.AceEditorSession::new(Lcom/google/gwt/core/client/JavaScriptObject;)(e.oldSession);
			var ns = @com.ait.toolkit.ace.client.AceEditorSession::new(Lcom/google/gwt/core/client/JavaScriptObject;)(e.session);
			var event = @com.ait.toolkit.ace.client.events.SessionChangeEvent::new(Lcom/ait/toolkit/ace/client/AceEditor;Lcom/ait/toolkit/ace/client/AceEditorSession;Lcom/ait/toolkit/ace/client/AceEditorSession;)(me,os,ns);
			handler.@com.ait.toolkit.ace.client.events.SessionChangeHandler::onSessionChange(Lcom/ait/toolkit/ace/client/events/SessionChangeEvent;)(event);
		};
		var eventName = @com.ait.toolkit.ace.client.events.SessionChangeEvent::EVENT_NAME;
		editor.on(eventName, fn);

		var toReturn = @com.ait.toolkit.ace.client.events.HandlerRegistration::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(this,eventName,fn);
		return toReturn;
	}-*/;

	/**
	 * Emitted when text is copied.
	 * 
	 * @param handler
	 *            , the handler that will handler the event
	 * @return handler registration attached to this event handler
	 */
	public native HandlerRegistration addCopyHandler(CopyHandler handler)/*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var me = this;
		var fn = function(e) {
			var event = @com.ait.toolkit.ace.client.events.CopyEvent::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;)(me, e.data);
			handler.@com.ait.toolkit.ace.client.events.CopyHandler::onCopy(Lcom/ait/toolkit/ace/client/events/CopyEvent;)(event);
		};
		var eventName = @com.ait.toolkit.ace.client.events.CopyEvent::EVENT_NAME;
		editor.on(eventName, fn);

		var toReturn = @com.ait.toolkit.ace.client.events.HandlerRegistration::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(this,eventName,fn);
		return toReturn;
	}-*/;

	/**
	 * Emitted when text is pasted.
	 * 
	 * @param handler
	 *            , the handler that will handler the event
	 * @return handler registration attached to this event handler
	 */
	public native HandlerRegistration addPasteHandler(PasteHandler handler)/*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var me = this;
		var fn = function(e) {
			var event = @com.ait.toolkit.ace.client.events.PasteEvent::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;)(me, e.data);
			handler.@com.ait.toolkit.ace.client.events.PasteHandler::onPaste(Lcom/ait/toolkit/ace/client/events/PasteEvent;)(event);

		};
		var eventName = @com.ait.toolkit.ace.client.events.PasteEvent::EVENT_NAME;
		editor.on(eventName, fn);

		var toReturn = @com.ait.toolkit.ace.client.events.HandlerRegistration::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(this,eventName,fn);
		return toReturn;
	}-*/;

	/**
	 * Emitted once the editor comes into focus.
	 * 
	 * @param handler
	 *            , the handler that will handler the event
	 * @return handler registration attached to this event handler
	 */
	public native HandlerRegistration addFocusHandler(FocusHandler handler)/*-{
		var editor = this.@com.ait.toolkit.ace.client.AceEditor::editor;
		var event = @com.ait.toolkit.ace.client.events.FocusEvent::new(Lcom/ait/toolkit/ace/client/AceEditor;)(this);
		var fn = function() {
			handler.@com.ait.toolkit.ace.client.events.FocusHandler::onFocus(Lcom/ait/toolkit/ace/client/events/FocusEvent;)(event);
		};
		var eventName = @com.ait.toolkit.ace.client.events.FocusEvent::EVENT_NAME;
		editor.on(eventName, fn);

		var toReturn = @com.ait.toolkit.ace.client.events.HandlerRegistration::new(Lcom/ait/toolkit/ace/client/AceEditor;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(this,eventName,fn);
		return toReturn;
	}-*/;

}
