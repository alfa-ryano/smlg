    /**
     * Adds the tree to the sidebar.
     */
    SMLG.editorUI.sidebar.SMLGAddTreePalette = function(expand) {
        var fns = [
		         this.SMLGCreateVertexTemplateEntry('swimlane;whiteSpace=wrap;html=1;', 200, 200, 
                 'Tree', 'Tree', null, null, 'container swimlane lane pool', JSON.stringify([{ name: 'diagram', type: 'String', value: 'Tree', editable: 'false' }, { name: 'uri', type: 'String', value: 'tree', editable: 'false' }, { name: 'prefix', type: 'String', value: 'tree', editable: 'false' }, { name: 'package', type: 'String', value: 'tree', editable: 'false' }, { name: 'diagram', type: 'String', value: 'tree', editable: 'false' }, { name: 'class', type: 'String', value: 'Tree', editable: 'false' }, {'name' : ' label','type' : 'String','value' : '1234','editable' : 'true'},])),
    ]
        this.addPaletteFunctions('tree', 'Tree', (expand != null) ? expand : true, fns);
    }
    SMLG.editorUI.sidebar.SMLGAddTreePalette(true);
    console.log("End of tree.js");

