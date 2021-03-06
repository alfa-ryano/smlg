var SMLG_DIAGRAM_PATH = "../smlg/diagram/";

var SMLG_DIAGRAM_TYPES = [ {
	filename : "eoml.js",
	enabled : true
}, {
	filename : "filesystem.js",
	enabled : false
}, {
	filename : "flowchart.js",
	enabled : false
}, {
	filename : "tree.js",
	enabled : false
} ];

var SMLG = function(editorUI, currentMetamodel, currentMode, currentModel, currentGame) {

	SMLG.currentMetamodel = currentMetamodel;
	SMLG.currentMode = currentMode;
	SMLG.currentModel = currentModel;
	SMLG.currentGame = currentGame;
	SMLG.inModels = null;
	SMLG.outModels = null;
	SMLG.prefix = null;
	SMLG.uri = null;
	SMLG.uri = null;

	SMLG.editorUI = editorUI;
	var graph = SMLG.editorUI.editor.graph;

	var convertValueToString = graph.convertValueToString;
	graph.convertValueToString = function(cell) {
		if (mxUtils.isNode(cell.value)) {
			var currentValue = convertValueToString.apply(this, arguments);
			var node = cell.value;
			var keyForLabelName = "";
			var gsmLabel = node.getAttribute("gsmLabel");
			var currentValue = (gsmLabel == null) ? currentValue : node
				.getAttribute(gsmLabel);
			return currentValue;
		}
	};

	var cellLabelChanged = graph.cellLabelChanged;
	graph.cellLabelChanged = function(cell, newValue, autoSize) {
		if (mxUtils.isNode(cell.value)) {
			var node = cell.value.cloneNode(true);
			var gsmLabel = node.getAttribute("gsmLabel");
			if (gsmLabel != null)
				node.setAttribute(gsmLabel, newValue);
			node.setAttribute("label", (newValue != null) ? newValue : '');
			cell.value = node;
		}
		cellLabelChanged.apply(this, arguments);
	};

	editorUI.sidebar.SMLGSetDiagramContext = function(jsonStringProperties) {
		var graph = this.editorUi.editor.graph;
		var cell = graph.getModel().getCell("1");

		var className = null;
		var prefix = null;
		var myPackage = null;
		var uri = null;
		var properties = null;

		var jsonObjects = JSON.parse(jsonStringProperties);
		for (var i = 0; i < jsonObjects.length; i++) {
			var item = jsonObjects[i];
			if (item["name"] == "class") {
				className = item["value"];
				continue;
			} else if (item["name"] == "prefix") {
				prefix = item["value"];
				SMLG.prefix = prefix;
				continue;
			} else if (item["name"] == "uri") {
				uri = item["value"];
				SMLG.uri = uri;
				continue;
			} else if (item["name"] == "package") {
				myPackage = item["value"];
				SMLG.package = myPackage;
				continue;
			} else if (item["properties"] != null
				&& item["properties"].length > 0) {
				properties = item["properties"];
				continue;
			}
		}

		// myPrefix:Filesystem xmi:version="2.0"
		// xmlns:xmi="http://www.omg.org/XMI"
		// xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		// xmlns:myPrefix="myUri"

		if (className != null && prefix != null && uri != null) {
			var node = mxUtils.createXmlDocument().createElement(className);
			node.setAttribute("prefix", prefix);
			node.setAttribute("uri", uri);
			node.setAttribute("package", myPackage);

			if (properties != null && properties.length > 0) {

				for (var i = 0; i < properties.length; i++) {
					var property = properties[i];
					if (property["type"] == "EAttribute") {
						node.setAttribute(property["name"], property["value"]);
					} else if (property["type"] == "EReference"
						&& property["upperBound"] != -1) {
						node.setAttribute(property["name"], property["value"]);
					} else {
						var element = mxUtils.createXmlDocument()
							.createElement("GSMRootContainer");
						element.setAttribute("name", property["name"]);
						element.setAttribute("type", property["eType"]);
						node.appendChild(element);
					}
				}
			}
			cell.value = node;
		} else {
			throw "class, prefix, or uri for diagram is not defined yet";
		}
	}

	/**
	 * Creates a drop handler for inserting the given cells.
	 */
	editorUI.sidebar.SMLGCreateVertexTemplateEntry = function(style, width,
		height, value, title, showLabel, showTitle, tags, properties) {
		tags = (tags != null && tags.length > 0) ? tags : title.toLowerCase();

		return this.addEntry(tags, mxUtils.bind(this, function() {
			return this.SMLGCreateVertexTemplate(style, width, height, value,
				title, showLabel, showTitle, null, properties);
		}));
	}

	/**
	 * Creates a drop handler for inserting the given cells.
	 */
	editorUI.sidebar.SMLGCreateVertexTemplate = function(style, width, height,
		value, title, showLabel, showTitle, allowCellsInserted,
		jsonStringPoperties) {
		var cells = [ new mxCell((value != null) ? value : '', new mxGeometry(
			0, 0, width, height), style) ];
		cells[0].vertex = true;

		if (jsonStringPoperties != null) {

			var gsmLabel = null;
			var className = null;
			var properties = null;

			var jsonProperties = JSON.parse(jsonStringPoperties);
			for (var i = 0; i < jsonProperties.length; i++) {
				var item = jsonProperties[i];
				if (item["name"] == "class") {
					className = item["value"];
					continue;
				} else if (item["name"] == "gsmLabel") {
					gsmLabel = item["value"];
					continue;
				} else if (item["properties"] != null
					&& item["properties"].length > 0) {
					properties = item["properties"];
					continue;
				}
			}

			if (className != null) {
				var node = mxUtils.createXmlDocument().createElement(className);
				node.setAttribute("label", (value != null) ? value : '');

				if (properties != null && properties.length > 0) {
					for (var i = 0; i < properties.length; i++) {
						var property = properties[i];

						if (property["type"] == "EAttribute") {
							node.setAttribute(property["name"],
								property["value"]);
						} else if (property["type"] == "EReference"
							&& property["upperBound"] != -1) {
							node.setAttribute(property["name"],
								property["value"]);
						} else {
							if (property["compartment"] != null
								&& property["compartment"] == true) {
								var style = property["style"];
								var innerCell = new mxCell(property.name, new mxGeometry(0, 0, height / 2, width), style);
								innerCell.vertex = true;
								innerCell.value = mxUtils.createXmlDocument()
									.createElement("GSMContainer");
								innerCell.value.setAttribute("name",
									property["name"]);
								innerCell.value.setAttribute("label",
									(property.name != null) ? property.name
										: '');
								cells[0].insert(innerCell);
							}
						}
					}
				}
				cells[0].value = node;
			}
		}

		return this.createVertexTemplateFromCells(cells, width, height, title,
			showLabel, showTitle, allowCellsInserted);
	};

	/**
	 * Creates a drop handler for inserting the given cells.
	 */
	editorUI.sidebar.SMLGCreateEdgeTemplateEntry = function(style, width,
		height, value, title, showLabel, tags, allowCellsInserted,
		properties) {
		tags = (tags != null && tags.length > 0) ? tags : title.toLowerCase();

		return this.addEntry(tags, mxUtils.bind(this, function() {
			return this.SMLGCreateEdgeTemplate(style, width, height, value,
				title, showLabel, allowCellsInserted, properties);
		}));
	};

	/**
	 * Creates a drop handler for inserting the given cells.
	 */
	editorUI.sidebar.SMLGCreateEdgeTemplate = function(style, width, height,
		value, title, showLabel, allowCellsInserted, jsonStringProperties) {
		var cell = new mxCell((value != null) ? value : '', new mxGeometry(0,
			0, width, height), style);
		cell.geometry.setTerminalPoint(new mxPoint(0, height), true);
		cell.geometry.setTerminalPoint(new mxPoint(width, 0), false);
		cell.geometry.relative = true;
		cell.edge = true;

		if (jsonStringProperties != null) {

			var className = null;
			var properties = null;
			var gsmLabel = null;

			var jsonProperties = JSON.parse(jsonStringProperties);
			for (var i = 0; i < jsonProperties.length; i++) {
				var item = jsonProperties[i];
				if (item["name"] == "class") {
					className = item["value"];
					continue;
				} else if (item["name"] == "gsmLabel") {
					gsmLabel = item["value"];
					continue;
				} else if (item["properties"] != null
					&& item["properties"].length > 0) {
					properties = item["properties"];
					continue;
				}
			}

			if (className != null) {
				var node = mxUtils.createXmlDocument().createElement(className);
				node.setAttribute("label", (value != null) ? value : '');

				if (properties != null && properties.length > 0) {
					for (var i = 0; i < properties.length; i++) {
						var property = properties[i];

						if (property["type"] == "EAttribute") {
							node.setAttribute(property["name"],
								property["value"]);
						} else if (property["type"] == "EReference"
							&& property["upperBound"] != -1) {
							node.setAttribute(property["name"],
								property["value"]);
						}
					}
				}
				cell.value = node;
			}
		// var node = mxUtils.createXmlDocument().createElement("SMLGCell");
		// cell.value = node;
		// cell.value.setAttribute("label", (value != null) ? value : '');
		// cell.value.setAttribute("properties", properties);
		}

		return this.createEdgeTemplateFromCells([ cell ], width, height, title,
			showLabel, allowCellsInserted);
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

			var addClickHandler = mxUtils
				.bind(
					this,
					function(elt, panel, index) {
						var clickHandler = mxUtils
							.bind(
								this,
								function(evt) {

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

						if (index == ((containsLabel) ? this.labelIndex
								: this.currentIndex)) {
							// Invokes handler directly as a workaround
							// for no click on DIV in KHTML.
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
			this.panels
				.push(new SMLGPropertiesPanel(this, ui, propertiesPanel));
			this.container.appendChild(propertiesPanel);
			addClickHandler(SMLGlabel, propertiesPanel, idx++);

		// End SMLG
		}
	};



	/***
	 * Override grapheditor saveFile function
	 */
	editorUI.saveFile = function(forceDialog) {
		if (SMLG.currentMode == "gaming"){
			return;
		}
		var metamodelName = SMLG.currentMetamodel;
		var modeName = SMLG.currentMode;
		var modelName = SMLG.currentModel;
		var gameName = SMLG.currentGame;

		var encoder = new mxCodec();
		var model = graph.getModel();
		var encodedModel = encoder.encode(model);
		var xml = mxUtils.getPrettyXml(encodedModel);
		xml = encodeURIComponent(xml);

		var params = "metamodel=" + metamodelName + "&mode=" + modeName + "&model=" + modelName + "&game=" + gameName + "&xml=" + xml;
		var request = new XMLHttpRequest;
		request.onload = function() {
			handleResponse();
		};

		request.open("POST", "/smlg/SaveFile?", true);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		request.send(params);

		function handleResponse() {
			var responseText = request.responseText;
			alert(responseText);
		}
	}

	// clear sidebar
	SMLG.ClearSidebar();

	//	// Load modelling language
	//	SMLG.SMLGLoadModellingLangauges();
	SMLG.SMLGLoadJavascript(SMLG_DIAGRAM_PATH + currentMetamodel + ".js");

	//Modify Menu
	SMLG.AddValidateMenu();
	if (SMLG.currentMode == "learning") {
		SMLG.AddGenerateMenu();
	}

	//Load Model
	SMLG.LoadModel(currentMetamodel, currentMode, currentModel, currentGame);
}

SMLG.SaveOutputModel = function() {
	var metamodelName = SMLG.currentMetamodel;
	var modeName = SMLG.currentMode;
	var modelName = SMLG.currentModel;
	var gameName = SMLG.currentGame;

	var encoder = new mxCodec();
	var model = graph.getModel();
	var encodedModel = encoder.encode(model);
	var xml = mxUtils.getPrettyXml(encodedModel);

	var params = "metamodel=" + metamodelName + "&mode=" + modeName + "&model=" + modelName + "&game=" + gameName + "&xml=" + xml;
	var request = new XMLHttpRequest;
	request.onload = function() {
		handleResponse();
	};

	request.open("POST", "/smlg/SaveFile?", true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(params);

	function handleResponse() {
	}
}
SMLG.prototype.SaveOutputModel = SMLG.SaveOutputModel;


/***
 * Generate Game
 */
SMLG.prototype.GenerateGame = function() {
	var graph = SMLG.editorUI.editor.graph;
	var metamodelName = SMLG.currentMetamodel;
	var modeName = "gaming";
	var modelName = SMLG.currentModel;

	var encoder = new mxCodec();
	var model = graph.getModel();
	var encodedModel = encoder.encode(model);
	var xml = mxUtils.getPrettyXml(encodedModel);

	var params = "metamodel=" + metamodelName + "&mode=" + modeName + "&model=" + modelName + "&xml=" + xml;
	var request = new XMLHttpRequest;
	request.onload = function() {
		handleResponse();
	};

	request.open("POST", "/smlg/GenerateGame?", true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(params);

	function handleResponse() {
		var responseText = request.responseText;
		var results = JSON.parse(responseText);

		var messageList = document.getElementById("messageList");
		messageList.style.color = "rgb(112, 112, 112)";
		while (messageList.firstChild) {
			messageList.removeChild(messageList.firstChild);
		}

		if (results.unsatisfiedConstraints.length == 0) {
			var entry = document.createElement('li');
			entry.innerHTML = "Game has been generated successfully!";
			messageList.appendChild(entry);

			return;
		}

		messageList.style.color = "red";
		for (var i = 0; i < results.unsatisfiedConstraints.length; i++) {
			var item = results.unsatisfiedConstraints[i];
			var stringOutput = item.message;
			var entry = document.createElement('li');
			entry.innerHTML = stringOutput;
			messageList.appendChild(entry);
		}
	}
}
SMLG.GenerateGame = SMLG.prototype.GenerateGame;

/***
*	Add generate menu
*/
SMLG.prototype.AddGenerateMenu = function() {
	var editorUi = SMLG.editorUI;

	editorUi.actions.put('generate', new Action("Generate Game", function() {
		SMLG.GenerateGame();
	}
	));

	var validateMenu = editorUi.menus.put('generation', new Menu(mxUtils.bind(editorUi.menus, function(menu, parent) {
		editorUi.menus.addMenuItems(menu, [ "generate" ]);
	})));

	var menu = validateMenu;
	var elt = editorUi.menubar.addMenu("Generation", menu.funct);
	if (elt != null) {
		menu.addListener('stateChanged', function() {
			elt.enabled = menu.enabled;
			if (!menu.enabled) {
				elt.className = 'geItem mxDisabled';

				if (document.documentMode == 8) {
					elt.style.color = '#c3c3c3';
				}
			} else {
				elt.className = 'geItem';
				if (document.documentMode == 8) {
					elt.style.color = '';
				}
			}
		});
	}
}
SMLG.AddGenerateMenu = SMLG.prototype.AddGenerateMenu;

/***
*	Add validate menu
*/
SMLG.prototype.AddValidateMenu = function() {
	var editorUi = SMLG.editorUI;

	editorUi.actions.put('validate', new Action("Validate...", function() {
		SMLG.ValidateModel();
	}
	));

	var validateMenu = editorUi.menus.put('validation', new Menu(mxUtils.bind(editorUi.menus, function(menu, parent) {
		editorUi.menus.addMenuItems(menu, [ "validate" ]);
	})));

	var menu = validateMenu;
	var elt = editorUi.menubar.addMenu("Validation", menu.funct);
	if (elt != null) {
		menu.addListener('stateChanged', function() {
			elt.enabled = menu.enabled;
			if (!menu.enabled) {
				elt.className = 'geItem mxDisabled';

				if (document.documentMode == 8) {
					elt.style.color = '#c3c3c3';
				}
			} else {
				elt.className = 'geItem';
				if (document.documentMode == 8) {
					elt.style.color = '';
				}
			}
		});
	}
}
SMLG.AddValidateMenu = SMLG.prototype.AddValidateMenu;

/***
 * Function to validate model
 */
SMLG.ValidateModel = function() {

	var editorUi = SMLG.editorUI;
	var graph = SMLG.editorUI.editor.graph;
	var metamodelName = SMLG.currentMetamodel;
	var modeName = SMLG.currentMode;
	var modelName = SMLG.currentModel;
	var gameName = SMLG.currentGame;
	var outputModel = null;

	var encoder = new mxCodec();
	var model = graph.getModel();
	var encodedModel = encoder.encode(model);
	var xml = mxUtils.getPrettyXml(encodedModel);
	xml = encodeURIComponent(xml);
	
	if (modeName == "gaming" && SMLG.outModels.length > 0){
		outputModel = SMLG.outModels[0].trim();
	}

	var params = "metamodel=" + metamodelName + "&mode=" + modeName + "&model=" + modelName +
		"&game=" + gameName + "&outputModel=" + outputModel + "&xml=" + xml;
	var request = new XMLHttpRequest;
	request.onload = function() {
		handleResponse();
	};

	request.open("POST", "/smlg/ValidateModel?", true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(params);

	function handleResponse() {
		var responseText = request.responseText;
		var results = JSON.parse(responseText);

		var messageList = document.getElementById("messageList");
		messageList.style.color = "rgb(112, 112, 112)";
		while (messageList.firstChild) {
			messageList.removeChild(messageList.firstChild);
		}

		if (results.unsatisfiedConstraints.length == 0) {
			var entry = document.createElement('li');
			entry.innerHTML = "Everything is OK!";
			messageList.appendChild(entry);

			if (SMLG.currentMode == "gaming") {
				var dlg = new LevelCompletedDialog(editorUi);
				editorUi.showDialog(dlg.container, 320, 80, true, true);
				dlg.init();
			}
		} else {
			messageList.style.color = "red";
			for (var i = 0; i < results.unsatisfiedConstraints.length; i++) {
				var item = results.unsatisfiedConstraints[i];
				var stringOutput = item.message;
				var entry = document.createElement('li');
				entry.innerHTML = stringOutput;
				messageList.appendChild(entry);
			}
		}
	}

}
SMLG.prototype.ValidateModel = SMLG.ValidateModel;

SMLG.prototype.LoadModel = function(metamodel, mode, model, game) {
	var editorUi = SMLG.editorUI;
	var editor = editorUi.editor;

	if (metamodel != null && mode != null && model != null) {

		var params = "metamodel=" + metamodel + "&mode=" + mode + "&model=" + model + "&game=" + game;
		var request = new XMLHttpRequest;
		request.onload = function() {
			handleResponse();
		};
		request.open("GET", "/smlg/LoadModel?" + params, true);
		request.send();

		function handleResponse() {
			var responseText = request.responseText;
			editor.setGraphXml(mxUtils.parseXml(responseText).documentElement);
		}
	}

	if (SMLG.currentMode == "gaming") {
		//for in models
		var incomingModelPath = "../" + SMLG.currentMode + "/" + SMLG.currentGame + "/" + SMLG.currentModel + "/models.in.js";
		$.getScript(incomingModelPath,
			function(data, textStatus, jqxhr) {
				SMLG.inModels = inModels;

				for (var i = 0; i < SMLG.inModels.length; i++) {
					var inModel = "../" + SMLG.inModels[i] + ".xml";
					var params = "metamodel=" + metamodel + "&mode=" + mode + "&model=" + model
						+ "&game=" + game + "&inModel=" + inModel;

					var incomingModelRequest = new XMLHttpRequest;
					incomingModelRequest.onload = function() {
						incomingModelandleResponse();
					};
					incomingModelRequest.open("GET", "/smlg/LoadModel?" + params, true);
					incomingModelRequest.send();

					function incomingModelandleResponse() {
						var responseText = incomingModelRequest.responseText;

						var doc = mxUtils.parseXml(responseText);
						var model = new mxGraphModel();
						var codec = new mxCodec(doc);
						codec.decode(doc.documentElement, model);

						var children = model.getChildren(model.getChildAt(model.getRoot(), 0));
						var importedCells = editor.graph.importCells(children);
						editor.graph.moveCells(importedCells, 0, 0);
						//editor.graph.setSelectionCells(importedCells);
					}
				}
			});

		//for out models
		var outgoingModelPath = "../" + SMLG.currentMode + "/" + SMLG.currentGame + "/" + SMLG.currentModel + "/models.out.js";
		$.getScript(outgoingModelPath,
			function(data, textStatus, jqxhr) {
				SMLG.outModels = outModels;
			});
	}

}
SMLG.LoadModel = SMLG.prototype.LoadModel;


SMLG.prototype.ClearSidebar = function() {
	var editorUI = SMLG.editorUI;
	var sidebar = editorUI.sidebar;
	var container = sidebar.container;
	var children = container.children;

	// reset/remote all default item categories on palette
	for (var i = children.length - 1; i >= 0; i--) {
		var child = children[i];

		if (child == sidebar.palettes['search'][0]
			|| child == sidebar.palettes['search'][1]) {
			continue;
		} else {
			container.removeChild(child);
		}
	}

	// reset/remote all default items on palette
	var palettes = sidebar.palettes;
	for (element in palettes) {
		if (palettes.hasOwnProperty(element)) {
			if (element != "search") {
				delete palettes[element];
			}
		}
	}

	// reset/remove all items on search taglist
	for (element in sidebar.taglist) {
		if (sidebar.taglist.hasOwnProperty(element)) {
			delete sidebar.taglist[element]
		}
	}
}
SMLG.ClearSidebar = SMLG.prototype.ClearSidebar;

SMLG.SMLGLoadJavascript = function(filename) {
	// DOM: Create the script element
	var scriptElement = document.createElement("script");
	// set the type attribute
	scriptElement.type = "application/javascript";
	// make the script element load file
	scriptElement.src = filename;
	// finally insert the element to the body element in order to load the
	// script
	document.body.appendChild(scriptElement);
	scriptElement.onload = function() {
		console.log(filename);

		// Add Message Panel after loading all model elements on the left panel
		SMLG.SMLGAddMessagePanel();
	}
};
SMLG.prototype.SMLGLoadJavascript = SMLG.SMLGLoadJavascript;

SMLG.SMLGLoadModellingLangauges = function() {
	for (var i = 0; i < SMLG_DIAGRAM_TYPES.length; i++) {
		var modellingLanguage = SMLG_DIAGRAM_TYPES[i];
		if (modellingLanguage.enabled == true) {
			this.SMLGLoadJavascript(SMLG_DIAGRAM_PATH
				+ modellingLanguage.filename);
		}
	}
};
SMLG.prototype.SMLGLoadModellingLangauges = SMLG.SMLGLoadModellingLangauges;

SMLG.SMLGPostModel = function(method, address, data, responseFunction) {
	var xmlhttp;
	if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera,
		// Safari
		xmlhttp = new XMLHttpRequest();
	} else { // code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.open(method, address, true);
	xmlhttp.send(data);

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			try {
				if (responseFunction != null) {
					jsonString = xmlhttp.responseText;
					responseFunction(jsonString);
				}
			} catch (err) {
				alert(err.message);
			}
		}
	}
};
SMLG.prototype.SMLGPostModel = SMLG.PostModel;


SMLG.SMLGAddMessagePanel = function() {
	var editorUi = SMLG.editorUI;
	var sidebarContainer = editorUi.sidebarContainer;

	var headerDiv = document.createElement('div');

	var div = document.createElement('div');
	var panelDiv = document.createElement('div');
	var headerDiv = document.createElement('div');
	var bodyDiv = document.createElement('div');
	var ul = document.createElement('ul');

	panelDiv.className = "geSidebar message-panel";
	headerDiv.className = "message-panel-header";
	headerDiv.innerHTML = "Messages";
	bodyDiv.className = "message-panel-body"

	ul.id = "messageList";

	bodyDiv.appendChild(ul);
	panelDiv.appendChild(headerDiv);
	panelDiv.appendChild(bodyDiv);
	div.appendChild(panelDiv);
	sidebarContainer.appendChild(div);
}
SMLG.prototype.SMLGAddMessagePanel = SMLG.SMLGAddMessagePanel;

// SMLG
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

		var node = selectedCell.value;
		for (var i = 0; i < node.attributes.length; i++) {
			var attribute = node.attributes[i];
			if (attribute.name == id) {
				attribute.value = input.value;

				graph.cellLabelChanged(selectedCell, input.value, false);
				ui.format.refresh();

				var encoder = new mxCodec();
				var model = graph.getModel();
				var encodedModel = encoder.encode(model);
				// var xml = mxUtils.getXml(encodedModel);
				var xml = mxUtils.getPrettyXml(encodedModel);

				//SMLG.SMLGPostModel("POST", "../ModelPost", xml, GSMResponse);
				break;
			}
		}
		mxEvent.consume(evt);
	}
	;
	mxEvent.addListener(input, 'change', update);

	function SMLGResponse(response) {
		var results = JSON.parse(response);
		if (results.unsatisfiedConstraints.length == 0)
			return;
		var dlg = new UnsatisfiedConstraintDialog(ui, response);
		ui.showDialog(dlg.container, 320, 220, true, true);
		dlg.init();
	}
}

/**
 * Adds the label menu items to the given menu and parent.
 */
SMLGPropertiesPanel.prototype.addProperties = function(container) {
	var ui = this.editorUi;
	var editor = ui.editor;
	var graph = editor.graph;
	var ss = this.format.getSelectionState(); // iterate through the
	// ss.vertices to get all
	// selected vertices and their
	// attributes

	var title = this.createTitle("Properties");
	title.style.paddingLeft = '18px';
	title.style.paddingTop = '10px';
	title.style.paddingBottom = '6px';
	container.appendChild(title);

	if (mxClient.IS_QUIRKS) {
		stylePanel.style.display = 'block';
	}

	// return container if there is no cell is selected or number of cells
	// selected is more than one
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

	var selectedCell = graph.getSelectionCell();

	if (mxUtils.isNode(selectedCell.value)) {

		var node = selectedCell.value;
		for (var i = 0; i < node.attributes.length; i++) {
			var attribute = node.attributes[i];

			var stylePropertyLabel = stylePanel.cloneNode(false);

			mxUtils.write(stylePropertyLabel, attribute.name.trim().charAt(0)
					.toUpperCase()
				+ attribute.name.trim().slice(1));

			var propertyInput = document.createElement('input');
			propertyInput.setAttribute("id", attribute.name);
			propertyInput.value = attribute.value;
			propertyInput.style.position = 'absolute';
			propertyInput.style.right = '20px';
			propertyInput.style.width = '100px';
			propertyInput.style.marginTop = '-3px';

			// if (properties["editable"] == false) {
			// propertyInput.style.backgroundColor = '#d7d7d7';
			// propertyInput.readOnly = true;
			// }

			this.addKeyHandler(propertyInput);
			this.UpdatePropertyHandler(propertyInput);
			stylePropertyLabel.appendChild(propertyInput);
			container.appendChild(stylePropertyLabel);

		}

		// var jsonStringProperties =
		// selectedCell.value.getAttribute("properties");
		// var jsonProperties = JSON.parse(jsonStringProperties);
		// var properties = null;
		//
		// for (var i = 0; i < jsonProperties.length; i++) {
		// var jsonProperty = jsonProperties[i];
		// if (jsonProperty["properties"] != null &&
		// jsonProperty["properties"].length > 0) {
		// properties = jsonProperty["properties"];
		// jsonProperties.splice(jsonProperties.indexOf(jsonProperty), 1);
		// break;
		// }
		// }
		//
		// //metaproperties
		// for (var i = 0; i < jsonProperties.length; i++) {
		// var jsonProperty = jsonProperties[i];
		//
		// if (jsonProperty["visible"] == false) {
		// continue;
		// }
		//
		// var stylePropertyLabel = stylePanel.cloneNode(false);
		//
		// mxUtils.write(stylePropertyLabel,
		// jsonProperty["name"].trim().charAt(0).toUpperCase() +
		// jsonProperty["name"].trim().slice(1));
		//
		// var propertyInput = document.createElement('input');
		// propertyInput.setAttribute("id", jsonProperty["name"]);
		// propertyInput.value = jsonProperty["value"];
		// propertyInput.style.position = 'absolute';
		// propertyInput.style.right = '20px';
		// propertyInput.style.width = '100px';
		// propertyInput.style.marginTop = '-3px';
		//
		// if (jsonProperty["editable"] == false) {
		// propertyInput.style.backgroundColor = '#d7d7d7';
		// propertyInput.readOnly = true;
		// }
		//
		// this.addKeyHandler(propertyInput);
		// this.UpdatePropertyHandler(propertyInput);
		// stylePropertyLabel.appendChild(propertyInput);
		// container.appendChild(stylePropertyLabel);
		// }
		//
		// //properties
		// for (var i = 0; i < properties.length; i++) {
		// var properties = properties[i];
		//
		// if (properties["visible"] == false) {
		// continue;
		// }
		//
		// var stylePropertyLabel = stylePanel.cloneNode(false);
		//
		// mxUtils.write(stylePropertyLabel,
		// properties["name"].trim().charAt(0).toUpperCase() +
		// properties["name"].trim().slice(1));
		//
		// var propertyInput = document.createElement('input');
		// propertyInput.setAttribute("id", properties["name"]);
		// propertyInput.value = properties["value"];
		// propertyInput.style.position = 'absolute';
		// propertyInput.style.right = '20px';
		// propertyInput.style.width = '100px';
		// propertyInput.style.marginTop = '-3px';
		//
		// if (properties["editable"] == false) {
		// propertyInput.style.backgroundColor = '#d7d7d7';
		// propertyInput.readOnly = true;
		// }
		//
		// this.addKeyHandler(propertyInput);
		// this.UpdatePropertyHandler(propertyInput);
		// stylePropertyLabel.appendChild(propertyInput);
		// container.appendChild(stylePropertyLabel);
		// }

	}

	return container;
};

var LevelCompletedDialog = function(editorUi) {
	var div = document.createElement('div');
	div.style.textAlign = 'right';

	var messagePanel = document.createElement('div');
	messagePanel.style.marginBottom = "16px";
	messagePanel.style.textAlign = "center";
	messagePanel.style.color = "rgb(112, 112, 112)";
	messagePanel.innerHTML = "Level Completed.<br/><br/>Press 'OK' button to go to previous menu."
	div.appendChild(messagePanel);

	var okBtn = mxUtils.button(mxResources.get('ok'), function() {
		editorUi.hideDialog();
		openUrl("../gaming/" + SMLG.currentGame + "?game=" + SMLG.currentGame);

	});
	okBtn.className = 'geBtn';

	this.init = function() {
		okBtn.focus();
	};

	if (editorUi.editor.cancelFirst) {
		div.appendChild(okBtn);
	}

	this.container = div;
}


var UnsatisfiedConstraintDialog = function(editorUi, response) {

	var div = document.createElement('div');
	div.style.textAlign = 'right';

	var messageList = document.createElement('ul');
	messageList.style.marginBottom = "16px";
	messageList.style.textAlign = "left";
	messageList.style.color = "red";

	var results = JSON.parse(response);
	for (var i = 0; i < results.unsatisfiedConstraints.length; i++) {
		var item = results.unsatisfiedConstraints[i];
		var stringOutput = item.message;
		var entry = document.createElement('li');
		entry.innerHTML = stringOutput;
		messageList.appendChild(entry);
	}

	div.appendChild(messageList);

	var okBtn = mxUtils.button(mxResources.get('ok'), function() {
		editorUi.hideDialog();
	});
	okBtn.className = 'geBtn';

	this.init = function() {
		okBtn.focus();
	};

	if (editorUi.editor.cancelFirst) {
		div.appendChild(okBtn);
	}

	this.container = div;

}