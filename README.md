ahome-ace
=========

High performance  web code editor for GWT.


ahome-ace is a java API  for the [Ace Editor](http://ace.c9.io/).
If you want to embed a powerful web based  editor in a GWT application, ahome-ace might be  what you're looking for!


The editor comes with a lot of nice feratures:

* Fully integrate with GWT Widgets.
* UI Binder support
* Syntax highlighting for over 110 languages (TextMate/Sublime Text.tmlanguage files can be imported)
* Over 20 themes (TextMate/Sublime Text .tmtheme files can be imported)
* Automatic indent and outdent
* An optional command line
* Handles huge documents (four million lines seems to be the limit!)
* Fully customizable key bindings including vim and Emacs modes
* Search and replace with regular expressions
* Highlight matching parentheses
* Toggle between soft tabs and real tabs
* Displays hidden characters
* Drag and drop text using the mouse
* Line wrapping
* Code folding
* Multiple cursors and selections
* Live syntax checker (currently JavaScript/CoffeeScript/CSS/XQuery)
* Cut, copy, and paste functionality
*  And more to come ...



The following components are required to work with Client-IO:

* GWT 2.5 and higher
* ahome-core project
 

##Getting started with ahome-ace
1) The project does'nt not have a binary. So one will have to build a jar file from source and add it to the classpath


2) Inherit the ahome-ace module

```xml
  <inherits name="com.ait.toolkit.ace.AhomeAce"/>
```

3) Below is an example how to use the widget.In this example we use our [Ext4j library](https://github.com/ahome-it/ahome-client-io/issues) but ahome-ace is compatible with any GWT library.


```java
package com.ait.toolkit.clientio.demo.client;
package com.ait.toolkit.clientio.demo.client;

import com.ait.toolkit.ace.client.AceEditor;
import com.ait.toolkit.ace.client.AceEditorMode;
import com.ait.toolkit.ace.client.AceEditorOptions;
import com.ait.toolkit.ace.client.AceEditorTheme;
import com.ait.toolkit.sencha.ext.client.core.ExtEntryPoint;
import com.ait.toolkit.sencha.ext.client.layout.Layout;
import com.ait.toolkit.sencha.ext.client.ui.Window;

public class AceTest extends ExtEntryPoint {

	private AceEditor edior;

	@Override
	public void onLoad() {

		Window w = new Window("ACE Editor");
		w.setLayout(Layout.FIT);
		w.setSize(700, 400);

		edior = new AceEditor();
		edior.setTheme(AceEditorTheme.TWILIGHT);
		edior.setMode(AceEditorMode.JAVASCRIPT);

		AceEditorOptions options = new AceEditorOptions();
		// options.setEnableBasicAutocompletion(true);
		options.setEnableLiveAutocompletion(true);
		edior.setOptions(options);

		w.add(edior);
		w.show();
	}

}
```

4) ahome-ace also works nicely with GWT's UI Binder
```xml
<!DOCTYPE ui:UiBinder SYSTEM "http://dl.google.com/gwt/DTD/xhtml.ent">
<ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	xmlns:x="urn:import:com.ait.toolkit.sencha.ext.client.ui"
	xmlns:ace="urn:import:com.ait.toolkit.ace.client" >
	
	<x:Window ui:field="window" title="ACE Editor demo" layout="fit">
	   <ace:AceEditor  ui:field="editor"  theme="twillight" mode="javascript" />
	</x:Window>
</ui:UiBinder> 
```

##Real world Demo
* [Ahomé-Ace Demo](http://ahome-it.github.io/ahome-client-io/)

##Issues tracking
* [Ahomé-Ace  Issues tracker](https://github.com/ahome-it/ahome-ace/issues)

##Community
* [Ahomé Google+ Community](https://plus.google.com/u/0/communities/106380618381566688303) - See whats happening in the community.


##Enterprise Support
Get high quality support through Ahomé
* <a href="http://opensource.ahome-it.com/pricing/">Buy Enterprise Support</a>


  





