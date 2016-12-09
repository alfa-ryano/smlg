function FilesystemInit(editorUI) {

	/**
	 * Adds the general filesystem to the sidebar.
	 */
	editorUI.sidebar.SMLGAddFilesystemPalette = function(expand) {

		var fns = [
			//parameter: style, width, height, value, title, showLabel, showTitle, tags
			this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 'Shortcut', 'Shortcut', null, null, null,
				JSON.stringify([
					{
						"name" : "qqqqq",
						"type" : "String",
						"value" : "ccccc",
						"editable" : "false"
					},
					{
						"name" : "sssss",
						"type" : "Integer",
						"value" : "33333",
						"editable" : "true"
					}
				])
			),
			this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 'File', 'File', null, null, 'rect rectangle box',

				JSON.stringify([
					{
						"name" : "wwwwww",
						"type" : "String",
						"value" : "hhhhh",
						"editable" : "false"
					},
					{
						"name" : "bbbbb",
						"type" : "Integer",
						"value" : "888888",
						"editable" : "true"
					}
				])
			),
			this.SMLGCreateVertexTemplateEntry('swimlane;whiteSpace=wrap;html=1;', 200, 200, 'Drive', 'Drive',
				null, null, 'container swimlane lane pool',

				JSON.stringify([
					{
						"name" : "name",
						"type" : "String",
						"value" : "Foo",
						"editable" : "false"
					},
					{
						"name" : "value",
						"type" : "Integer",
						"value" : "123",
						"editable" : "true"
					}
				])
			),
			this.SMLGCreateVertexTemplateEntry('swimlane;whiteSpace=wrap;html=1;', 200, 200, 'Folder', 'Folder',
				null, null, 'container swimlane lane pool',

				JSON.stringify([
					{
						"name" : "name",
						"type" : "String",
						"value" : "Foo",
						"editable" : "false"
					},
					{
						"name" : "value",
						"type" : "Integer",
						"value" : "123",
						"editable" : "true"
					}
				])
			),
			this.SMLGCreateVertexTemplateEntry('endArrow=classic;html=1;', 50, 50, '', 'Target',

				JSON.stringify([
					{
						"name" : "name",
						"type" : "String",
						"value" : "Foo",
						"editable" : "false"
					},
					{
						"name" : "value",
						"type" : "Integer",
						"value" : "123",
						"editable" : "true"
					}
				])
			),
			this.SMLGCreateVertexTemplateEntry('endArrow=none;html=1;dashed=1;dashPattern=1 4;', 50, 50, '', 'Sync',

				JSON.stringify([
					{
						"name" : "name",
						"type" : "String",
						"value" : "Foo",
						"editable" : "false"
					},
					{
						"name" : "value",
						"type" : "Integer",
						"value" : "123",
						"editable" : "true"
					}
				])
			),
		]
		this.addPaletteFunctions('filesystem', 'Filesystem', (expand != null) ? expand : true, fns);
	}

	editorUI.sidebar.SMLGAddFilesystemPalette(true);
	console.log("End of Filesystem.js");
}