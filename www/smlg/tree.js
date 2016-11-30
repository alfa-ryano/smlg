function TreeInit(editorUI) {
    /**
     * Adds the tree to the sidebar.
     */
    editorUI.sidebar.SMLGAddTreePalette = function(expand) {
        var fns = [
		         this.createVertexTemplateEntry('swimlane;whiteSpace=wrap;html=1;', 200, 200, 
                 'Tree', 'Tree', null, null, 'container swimlane lane pool'),
    ]
        this.addPaletteFunctions('tree', 'Tree', (expand != null) ? expand : true, fns);
    }
    editorUI.sidebar.SMLGAddTreePalette(true);
    console.log("End of tree.js");
}
