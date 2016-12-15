var SMLG_DIAGRAM_PATH = "../smlg/diagram/";

var SMLG_DIAGRAM_TYPES = [
	{
		filename : "filesystem.js",
		enabled : true
	},
	{
		filename : "flowchart.js",
		enabled : true
	},
	{
		filename : "tree.js",
		enabled : true
	}
];

var SMLG = function(editorUI) {

	SMLG.editorUI = editorUI;

	mxGraph.convertValueToString = function(cell) {
		if (mxUtils.isNode(cell.value)) {
			return cell.getAttribute('label', '')
		}
	};

	var cellLabelChanged = mxGraph.cellLabelChanged;
	mxGraph.cellLabelChanged = function(cell, newValue, autoSize) {
		if (mxUtils.isNode(cell.value)) {
			// Clones the value for correct undo/redo
			var elt = cell.value.cloneNode(true);
			elt.setAttribute('label', newValue);
			newValue = elt;
		}

		cellLabelChanged.apply(this, arguments);
	};

	/**
	 * Creates a drop handler for inserting the given cells.
	 */
	editorUI.sidebar.SMLGCreateVertexTemplateEntry = function(style, width, height, value, title, showLabel, showTitle, tags, properties) {
		tags = (tags != null && tags.length > 0) ? tags : title.toLowerCase();

		return this.addEntry(tags, mxUtils.bind(this, function() {
			return this.SMLGCreateVertexTemplate(style, width, height, value, title, showLabel, showTitle, null, properties);
		}));
	}

	/**
	 * Creates a drop handler for inserting the given cells.
	 */
	editorUI.sidebar.SMLGCreateVertexTemplate = function(style, width, height, value, title, showLabel, showTitle, allowCellsInserted, properties) {
		var cells = [ new mxCell((value != null) ? value : '', new mxGeometry(0, 0, width, height), style) ];
		cells[0].vertex = true;

		if (properties != null) {
			var node = mxUtils.createXmlDocument().createElement("SMLG");
			cells[0].value = node;
			cells[0].value.setAttribute("label", (value != null) ? value : '');
			cells[0].value.setAttribute("properties", properties);
		}

		return this.createVertexTemplateFromCells(cells, width, height, title, showLabel, showTitle, allowCellsInserted);
	};

	/**
	 * Creates a drop handler for inserting the given cells.
	 */
	editorUI.sidebar.SMLGCreateEdgeTemplateEntry = function(style, width, height, value, title, showLabel, tags, allowCellsInserted, properties) {
		tags = (tags != null && tags.length > 0) ? tags : title.toLowerCase();

		return this.addEntry(tags, mxUtils.bind(this, function() {
			return this.SMLGCreateEdgeTemplate(style, width, height, value, title, showLabel, allowCellsInserted, properties);
		}));
	};

	/**
	 * Creates a drop handler for inserting the given cells.
	 */
	editorUI.sidebar.SMLGCreateEdgeTemplate = function(style, width, height, value, title, showLabel, allowCellsInserted, properties) {
		var cell = new mxCell((value != null) ? value : '', new mxGeometry(0, 0, width, height), style);
		cell.geometry.setTerminalPoint(new mxPoint(0, height), true);
		cell.geometry.setTerminalPoint(new mxPoint(width, 0), false);
		cell.geometry.relative = true;
		cell.edge = true;

		if (properties != null) {
			var node = mxUtils.createXmlDocument().createElement("SMLG");
			cells[0].value = node;
			cells[0].value.setAttribute("label", (value != null) ? value : '');
			cells[0].value.setAttribute("properties", properties);
		}

		return this.createEdgeTemplateFromCells([ cell ], width, height, title, showLabel, allowCellsInserted);
	};

	/**
	 * Override Format refresh function
	 */
	editorUI.format.refresh = function() {
		// Performance tweak: No refresh needed if not visible
		if (this.container.style.width == '0px') {
			return;
		}

		this.clear();
		var ui = this.editorUi;
		var graph = ui.editor.graph;

		var div = document.createElement('div');
		div.style.whiteSpace = 'nowrap';
		div.style.color = 'rgb(112, 112, 112)';
		div.style.textAlign = 'left';
		div.style.cursor = 'default';

		var label = document.createElement('div');
		label.style.border = '1px solid #c0c0c0';
		label.style.borderWidth = '0px 0px 1px 0px';
		label.style.textAlign = 'center';
		label.style.fontWeight = 'bold';
		label.style.overflow = 'hidden';
		label.style.display = (mxClient.IS_QUIRKS) ? 'inline' : 'inline-block';
		label.style.paddingTop = '8px';
		label.style.height = (mxClient.IS_QUIRKS) ? '34px' : '25px';
		label.style.width = '100%';
		this.container.appendChild(div);

		if (graph.isSelectionEmpty()) {
			mxUtils.write(label, mxResources.get('diagram'));

			// Adds button to hide the format panel since
			// people don't seem to find the toolbar button
			// and the menu item in the format menu
			var img = document.createElement('img');
			img.setAttribute('border', '0');
			img.setAttribute('src', Dialog.prototype.closeImage);
			img.setAttribute('title', mxResources.get('hide'));
			img.style.position = 'absolute';
			img.style.display = 'block';
			img.style.right = '0px';
			img.style.top = '8px';
			img.style.cursor = 'pointer';
			img.style.marginTop = '1px';
			img.style.marginRight = '17px';
			img.style.border = '1px solid transparent';
			img.style.padding = '1px';
			img.style.opacity = 0.5;
			label.appendChild(img)

			mxEvent.addListener(img, 'click', function() {
				ui.actions.get('formatPanel').funct();
			});

			div.appendChild(label);
			this.panels.push(new DiagramFormatPanel(this, ui, div));
		} else if (graph.isEditing()) {
			mxUtils.write(label, mxResources.get('text'));
			div.appendChild(label);
			this.panels.push(new TextFormatPanel(this, ui, div));
		} else {
			var containsLabel = this.getSelectionState().containsLabel;
			var currentLabel = null;
			var currentPanel = null;

			var addClickHandler = mxUtils.bind(this, function(elt, panel, index) {
				var clickHandler = mxUtils.bind(this, function(evt) {
					//console.log(currentLabel.prototype);

					if (currentLabel != elt) {
						if (containsLabel) {
							this.labelIndex = index;
						} else {
							this.currentIndex = index;
						}

						if (currentLabel != null) {
							currentLabel.style.backgroundColor = '#d7d7d7';
							currentLabel.style.borderBottomWidth = '1px';
						}

						currentLabel = elt;
						currentLabel.style.backgroundColor = '';
						currentLabel.style.borderBottomWidth = '0px';

						if (currentPanel != panel) {
							if (currentPanel != null) {
								currentPanel.style.display = 'none';
							}

							currentPanel = panel;
							currentPanel.style.display = '';
						}
					}
				});

				mxEvent.addListener(elt, 'click', clickHandler);

				if (index == ((containsLabel) ? this.labelIndex : this.currentIndex)) {
					// Invokes handler directly as a workaround for no click on DIV in KHTML.
					clickHandler();
				}
			});

			var idx = 0;

			label.style.backgroundColor = '#d7d7d7';
			label.style.borderLeftWidth = '1px';
			label.style.width = (containsLabel) ? '50%' : '33.3%';
			var label2 = label.cloneNode(false);
			var label3 = label2.cloneNode(false);

			// Workaround for ignored background in IE
			label2.style.backgroundColor = '#d7d7d7';
			label3.style.backgroundColor = '#d7d7d7';

			// Style
			if (containsLabel) {
				label2.style.borderLeftWidth = '0px';
			} else {
				label.style.borderLeftWidth = '0px';
				mxUtils.write(label, mxResources.get('style'));
				div.appendChild(label);

				var stylePanel = div.cloneNode(false);
				stylePanel.style.display = 'none';
				this.panels.push(new StyleFormatPanel(this, ui, stylePanel));
				this.container.appendChild(stylePanel);

				addClickHandler(label, stylePanel, idx++);
			}

			// Text
			mxUtils.write(label2, mxResources.get('text'));
			div.appendChild(label2);

			var textPanel = div.cloneNode(false);
			textPanel.style.display = 'none';
			this.panels.push(new TextFormatPanel(this, ui, textPanel));
			this.container.appendChild(textPanel);

			// Arrange
			mxUtils.write(label3, mxResources.get('arrange'));
			div.appendChild(label3);

			var arrangePanel = div.cloneNode(false);
			arrangePanel.style.display = 'none';
			this.panels.push(new ArrangePanel(this, ui, arrangePanel));
			this.container.appendChild(arrangePanel);

			addClickHandler(label2, textPanel, idx++);
			addClickHandler(label3, arrangePanel, idx++);

			// Properties
			// SMLGlabel
			var SMLGlabel = label3.cloneNode(false);
			SMLGlabel.style.backgroundColor = '#d7d7d7';

			label.style.width = '20.5%';
			label2.style.width = '20.5%';
			label3.style.width = '26%';
			SMLGlabel.style.width = '33%';

			mxUtils.write(SMLGlabel, "Properties");
			div.appendChild(SMLGlabel);

			var propertiesPanel = div.cloneNode(false);
			propertiesPanel.style.display = 'none';
			this.panels.push(new SMLGPropertiesPanel(this, ui, propertiesPanel));
			this.container.appendChild(propertiesPanel);
			addClickHandler(SMLGlabel, propertiesPanel, idx++);

		//End SMLG
		}
	};


	//Load modelling language
	SMLG.SMLGLoadModellingLangauges();
}

SMLG.SMLGLoadJavascript = function(filename) {
	// DOM: Create the script element
	var scriptElement = document.createElement("script");
	// set the type attribute
	scriptElement.type = "application/javascript";
	// make the script element load file
	scriptElement.src = filename;
	// finally insert the element to the body element in order to load the script
	document.body.appendChild(scriptElement);
	scriptElement.onload = function() {
		console.log(filename);
	}
};
SMLG.prototype.SMLGLoadJavascript = SMLG.SMLGLoadJavascript;

SMLG.SMLGLoadModellingLangauges = function() {
	for (var i = 0; i < SMLG_DIAGRAM_TYPES.length; i++) {
		var modellingLanguage = SMLG_DIAGRAM_TYPES[i];
		if (modellingLanguage.enabled == true) {
			this.SMLGLoadJavascript(SMLG_DIAGRAM_PATH + modellingLanguage.filename);
		}
	}
};
SMLG.prototype.SMLGLoadModellingLangauges = SMLG.SMLGLoadModellingLangauges;




//SMLG
/**
* Adds the label menu items to the given menu and parent.
*/
SMLGPropertiesPanel = function(format, editorUi, container) {
	BaseFormatPanel.call(this, format, editorUi, container);
	this.init();
};

mxUtils.extend(SMLGPropertiesPanel, BaseFormatPanel);

/**
* Adds the label menu items to the given menu and parent.
*/
SMLGPropertiesPanel.prototype.init = function() {
	this.container.style.borderBottom = 'none';
	this.addProperties(this.container);
};

/**
 * Event when a user changes the value of property input
 */
SMLGPropertiesPanel.prototype.UpdatePropertyHandler = function(input) {
	var ui = this.editorUi;
	var graph = ui.editor.graph;
	var selectedCell = graph.getSelectionCell();

	function update(evt) {
		input.value = input.value == null ? "" : input.value;

		var id = input.id;
		var jsonStringProperties = selectedCell.value.getAttribute("properties");
		console.log("");
		console.log("before: " + jsonStringProperties);
		var properties = JSON.parse(jsonStringProperties);
		for (var i = 0; i < properties.length; i++) {
			var property = properties[i];
			if (property["name"] == id) {
				property["value"] = input.value;
				jsonStringProperties = JSON.stringify(properties);
				console.log("after: " + jsonStringProperties);
				selectedCell.value.setAttribute("properties", jsonStringProperties);
				break;
			}
		}

		mxEvent.consume(evt);
	}
	;

	mxEvent.addListener(input, 'blur', update);
	mxEvent.addListener(input, 'change', update);
}

/**
* Adds the label menu items to the given menu and parent.
*/
SMLGPropertiesPanel.prototype.addProperties = function(container) {
	var ui = this.editorUi;
	var editor = ui.editor;
	var graph = editor.graph;
	var ss = this.format.getSelectionState(); //iterate through the ss.vertices to get all selected vertices and their attributes


	var title = this.createTitle("Properties");
	title.style.paddingLeft = '18px';
	title.style.paddingTop = '10px';
	title.style.paddingBottom = '6px';
	container.appendChild(title);

	if (mxClient.IS_QUIRKS) {
		stylePanel.style.display = 'block';
	}

	//return container if there is no cell is selected or number of cells selected is more than one 
	if (graph.getSelectionCount() == 0 || graph.getSelectionCount() > 1) {
		return container;
	}

	var stylePanel = this.createPanel();
	stylePanel.style.paddingTop = '4px';
	stylePanel.style.paddingBottom = '4px';
	stylePanel.style.marginLeft = '1px';
	stylePanel.style.borderWidth = '0px';
	stylePanel.style.position = 'relative';
	stylePanel.className = 'geToolbarContainer';

	var jsonStringProperties = ss.vertices[0].value.getAttribute("properties");
	var properties = JSON.parse(jsonStringProperties);

	for (var i = 0; i < properties.length; i++) {
		var property = properties[i];
		// Writing Property 01
		var stylePropertyLabel = stylePanel.cloneNode(false);

		mxUtils.write(stylePropertyLabel, property["name"].charAt(0).toUpperCase() + property["name"].slice(1));

		var propertyInput = document.createElement('input');
		propertyInput.setAttribute("id", property["name"]);
		propertyInput.value = property["value"];
		propertyInput.style.position = 'absolute';
		propertyInput.style.right = '20px';
		propertyInput.style.width = '100px';
		propertyInput.style.marginTop = '-3px';

		this.addKeyHandler(propertyInput);
		this.UpdatePropertyHandler(propertyInput);
		stylePropertyLabel.appendChild(propertyInput);
		container.appendChild(stylePropertyLabel);
	}

	return container;
};