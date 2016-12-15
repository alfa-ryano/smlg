    /**
     * Adds the flowchart to the sidebar.
     */
    SMLG.editorUI.sidebar.SMLGAddFlowchartPalette = function(expand) {
        var fns = [
                 this.SMLGCreateEdgeTemplateEntry('endArrow=classic;', 50, 50, 
                 '', 'Transition', JSON.stringify([{'name' : ' name','type' : 'String','value' : '','editable' : 'true'},])),                                       
  
		         this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 
                 'Action', 'Action', null, null, null, JSON.stringify([])),
  
		         this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 
                 'Decision', 'Decision', null, null, null, JSON.stringify([])),
    ]
        this.addPaletteFunctions('flowchart', 'Flowchart', (expand != null) ? expand : true, fns);
    }
    SMLG.editorUI.sidebar.SMLGAddFlowchartPalette(true);
    console.log("End of flowchart.js");

