    /**
     * Adds the flowchart to the sidebar.
     */
    SMLG.editorUI.sidebar.SMLGAddFlowchartPalette = function(expand) {
 
        var fns = [
            this.SMLGCreateEdgeTemplateEntry('', 50, 50, 
                 'Transition', 'Transition', null, null, 'Transition', JSON.stringify([{ name: 'class', type: 'EString', value: 'Transition', editable: false, visible: true, compartment: false }, { name: 'name', type: 'EString', value: '', editable: true, visible: true, compartment: false }, { name: 'source', type: 'Node', value: '', editable: false, visible: true, compartment: false }, { name: 'target', type: 'Node', value: '', editable: false, visible: true, compartment: false }, { name: 'label', type: 'EString', value: 'name', editable: false, visible: true, compartment: false }, ])),
            this.SMLGCreateVertexTemplateEntry('', 200, 200,  
                 'Subflow', 'Subflow', null, null, 'Subflow', JSON.stringify([{ name: 'class', type: 'EString', value: 'Subflow', editable: false, visible: true, compartment: false }, { name: 'nodes', type: 'Node', value: '', editable: true, visible: true, compartment: false }, { name: 'transitions', type: 'Transition', value: '', editable: true, visible: true, compartment: false }, { name: 'name', type: 'EString', value: '', editable: true, visible: true, compartment: false }, { name: 'outgoing', type: 'Transition', value: '', editable: true, visible: true, compartment: false }, { name: 'incoming', type: 'Transition', value: '', editable: true, visible: true, compartment: false }, { name: 'label', type: 'EString', value: 'name', editable: false, visible: true, compartment: false }, ])),
            this.SMLGCreateVertexTemplateEntry('', 200, 200,  
                 'Action', 'Action', null, null, 'Action', JSON.stringify([{ name: 'class', type: 'EString', value: 'Action', editable: false, visible: true, compartment: false }, { name: 'name', type: 'EString', value: '', editable: true, visible: true, compartment: false }, { name: 'outgoing', type: 'Transition', value: '', editable: true, visible: true, compartment: false }, { name: 'incoming', type: 'Transition', value: '', editable: true, visible: true, compartment: false }, { name: 'label', type: 'EString', value: 'name', editable: false, visible: true, compartment: false }, ])),
            this.SMLGCreateVertexTemplateEntry('', 200, 200,  
                 'Decision', 'Decision', null, null, 'Decision', JSON.stringify([{ name: 'class', type: 'EString', value: 'Decision', editable: false, visible: true, compartment: false }, { name: 'name', type: 'EString', value: '', editable: true, visible: true, compartment: false }, { name: 'outgoing', type: 'Transition', value: '', editable: true, visible: true, compartment: false }, { name: 'incoming', type: 'Transition', value: '', editable: true, visible: true, compartment: false }, { name: 'label', type: 'EString', value: 'name', editable: false, visible: true, compartment: false }, ])),
        ]
        this.SMLGSetDiagramContext(JSON.stringify([{ name: 'uri', type: 'EString', value: 'flowchart', editable: false, visible: true, compartment: false }, { name: 'prefix', type: 'EString', value: 'flowchart', editable: false, visible: true, compartment: false }, { name: 'package', type: 'EString', value: 'flowchart', editable: false, visible: true, compartment: false }, { name: 'diagram', type: 'EString', value: 'Flowchart', editable: false, visible: true, compartment: false }, { name: 'class', type: 'EString', value: 'Flowchart', editable: false, visible: true, compartment: false }, { name: 'nodes', type: 'Node', value: '', editable: true, visible: true, compartment: false }, { name: 'transitions', type: 'Transition', value: '', editable: true, visible: true, compartment: false }, ]));
        this.addPaletteFunctions('flowchart', 'Flowchart', (expand != null) ? expand : true, fns);
    }
    SMLG.editorUI.sidebar.SMLGAddFlowchartPalette(true);
    console.log("End of flowchart.js");


