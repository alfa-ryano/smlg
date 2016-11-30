function FlowchartInit(editorUI) {
    /**
     * Adds the flowchart to the sidebar.
     */
    editorUI.sidebar.SMLGAddFlowchartPalette = function(expand) {
        var fns = [
                 this.createEdgeTemplateEntry('endArrow=classic;', 50, 50, 
                 '', 'Transition'),                                       
  
		         this.createVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 
                 'Action', 'Action', null, null, null),
  
		         this.createVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 
                 'Decision', 'Decision', null, null, null),
    ]
        this.addPaletteFunctions('flowchart', 'Flowchart', (expand != null) ? expand : true, fns);
    }
    editorUI.sidebar.SMLGAddFlowchartPalette(true);
    console.log("End of flowchart.js");
}
