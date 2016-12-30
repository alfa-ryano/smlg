    /**
     * Adds the flowchart to the sidebar.
     */
    SMLG.editorUI.sidebar.SMLGAddFlowchartPalette = function(expand) {
        var fns = [
                 this.SMLGCreateEdgeTemplateEntry('endArrow=classic;', 50, 50, 
                 '', 'Transition', null, null, null, JSON.stringify([{ name: 'diagram', type: 'String', value: 'Flowchart', editable: 'false' }, { name: 'uri', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'prefix', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'package', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'diagram', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'class', type: 'String', value: 'Transition', editable: 'false' }, {'name' : ' name','type' : 'String','value' : '','editable' : 'true'},])),                                       
  
		         this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 
                 'Action', 'Action', null, null, null, JSON.stringify([{ name: 'diagram', type: 'String', value: 'Flowchart', editable: 'false' }, { name: 'uri', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'prefix', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'package', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'diagram', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'class', type: 'String', value: 'Action', editable: 'false' }, ])),
  
		         this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 
                 'Decision', 'Decision', null, null, null, JSON.stringify([{ name: 'diagram', type: 'String', value: 'Flowchart', editable: 'false' }, { name: 'uri', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'prefix', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'package', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'diagram', type: 'String', value: 'flowchart', editable: 'false' }, { name: 'class', type: 'String', value: 'Decision', editable: 'false' }, ])),
    ]
        this.addPaletteFunctions('flowchart', 'Flowchart', (expand != null) ? expand : true, fns);
    }
    SMLG.editorUI.sidebar.SMLGAddFlowchartPalette(true);
    console.log("End of flowchart.js");

