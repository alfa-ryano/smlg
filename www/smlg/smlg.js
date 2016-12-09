function SMLGInitialization(editorUI) {
	
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

}