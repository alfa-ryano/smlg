    /**
     * Adds the flowchart to the sidebar.
     */
    SMLG.editorUI.sidebar.SMLGAddFlowchartPalette = function(expand) {
 
        var fns = [
            this.SMLGCreateEdgeTemplateEntry('', 50, 50, 
                 'Transition', 'Transition', null, null, 'Transition', JSON.stringify([{ name: 'class', type: 'EAttribute', eType: 'EString', value: 'Transition', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'name', type: 'EAttribute', eType: 'EString', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: 1, eOpposite: ''   }, { name: 'source', type: 'EReference', eType: 'Node', value: '', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound: 1, eOpposite: 'outgoing'   }, { name: 'target', type: 'EReference', eType: 'Node', value: '', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound: 1, eOpposite: 'incoming'   }, { name: 'label', type: 'EAttribute', eType: 'EString', value: 'name', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, ])),
            this.SMLGCreateVertexTemplateEntry('', 200, 200,  
                 'Subflow', 'Subflow', null, null, 'Subflow', JSON.stringify([{ name: 'class', type: 'EAttribute', eType: 'EString', value: 'Subflow', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'nodes2', type: 'EReference', eType: 'Node', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: ''   }, { name: 'transitions', type: 'EReference', eType: 'Transition', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: ''   }, { name: 'nodes1', type: 'EReference', eType: 'Node', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: ''   }, { name: 'name', type: 'EAttribute', eType: 'EString', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: 1, eOpposite: ''   }, { name: 'outgoing', type: 'EReference', eType: 'Transition', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: 'source'   }, { name: 'incoming', type: 'EReference', eType: 'Transition', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: 'target'   }, { name: 'label', type: 'EAttribute', eType: 'EString', value: 'name', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, ])),
            this.SMLGCreateVertexTemplateEntry('', 200, 200,  
                 'Action', 'Action', null, null, 'Action', JSON.stringify([{ name: 'class', type: 'EAttribute', eType: 'EString', value: 'Action', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'name', type: 'EAttribute', eType: 'EString', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: 1, eOpposite: ''   }, { name: 'outgoing', type: 'EReference', eType: 'Transition', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: 'source'   }, { name: 'incoming', type: 'EReference', eType: 'Transition', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: 'target'   }, { name: 'label', type: 'EAttribute', eType: 'EString', value: 'name', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, ])),
            this.SMLGCreateVertexTemplateEntry('', 200, 200,  
                 'Decision', 'Decision', null, null, 'Decision', JSON.stringify([{ name: 'class', type: 'EAttribute', eType: 'EString', value: 'Decision', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'name', type: 'EAttribute', eType: 'EString', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: 1, eOpposite: ''   }, { name: 'outgoing', type: 'EReference', eType: 'Transition', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: 'source'   }, { name: 'incoming', type: 'EReference', eType: 'Transition', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: 'target'   }, { name: 'label', type: 'EAttribute', eType: 'EString', value: 'name', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, ])),
        ]
        this.SMLGSetDiagramContext(JSON.stringify([{ name: 'uri', type: 'EAttribute', eType: 'EString', value: 'flowchart', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'prefix', type: 'EAttribute', eType: 'EString', value: 'flowchart', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'package', type: 'EAttribute', eType: 'EString', value: 'flowchart', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'diagram', type: 'EAttribute', eType: 'EString', value: 'Flowchart', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'class', type: 'EAttribute', eType: 'EString', value: 'Flowchart', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'nodes2', type: 'EReference', eType: 'Node', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: ''   }, { name: 'transitions', type: 'EReference', eType: 'Transition', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: ''   }, { name: 'nodes1', type: 'EReference', eType: 'Node', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: -1, eOpposite: ''   }, ]));
        this.addPaletteFunctions('flowchart', 'Flowchart', (expand != null) ? expand : true, fns);
    }
    SMLG.editorUI.sidebar.SMLGAddFlowchartPalette(true);
    console.log("End of flowchart.js");


