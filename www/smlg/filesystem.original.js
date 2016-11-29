function SMLGInit(editorUI) {

	/**
	 * Adds the general filesystem to the sidebar.
	 */
	editorUI.sidebar.SMLGAddFilesystemPalette = function(expand) {
		var fns = [
		    //parameter: style, width, height, value, title, showLabel, showTitle, tags
			this.createVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 'Shortcut', 'Shortcut', null, null, null),
			this.createVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 'File', 'File', null, null, 'rect rectangle box'),
			this.createVertexTemplateEntry('swimlane;whiteSpace=wrap;html=1;', 200, 200, 'Drive', 'Drive',
				null, null, 'container swimlane lane pool'),
			this.createVertexTemplateEntry('swimlane;whiteSpace=wrap;html=1;', 200, 200, 'Folder', 'Folder',
				null, null, 'container swimlane lane pool'),
			this.createEdgeTemplateEntry('endArrow=classic;html=1;', 50, 50, '', 'Target'),
			this.createEdgeTemplateEntry('endArrow=none;html=1;dashed=1;dashPattern=1 4;', 50, 50, '', 'Sync')

		]
		this.addPaletteFunctions('filesystem', 'Filesystem', (expand != null) ? expand : true, fns);
	}

	editorUI.sidebar.SMLGAddFilesystemPalette(true);
	console.log("End of Filesystem.js");
}