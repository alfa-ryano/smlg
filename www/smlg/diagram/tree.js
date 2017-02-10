    /**
     * Adds the tree to the sidebar.
     */
    SMLG.editorUI.sidebar.SMLGAddTreePalette = function(expand) {
 
        var fns = [
            this.SMLGCreateVertexTemplateEntry('childLayout=stackLayout;whiteSpace=wrap;shape=swimlane;resizeParent=0;resizeLast=1;collapsible=1;marginLeft=0;fillColor=none;marginRight=0;rounded=1;html=1;marginBottom=0;horizontalStack=0;marginTop=0;', 200, 120,  
                 'Node', 'Node', null, null, 'Node', JSON.stringify([{ name: 'class', type: 'EAttribute', eType: 'EString', value: 'Node', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { properties: [ { name: 'children', type: 'EReference', eType: 'Node', value: '', editable: true, visible: true, compartment: true, lowerBound: 0, upperBound: -1, eOpposite: '', style: 'shape=swimlane;collapsible=0;noLabel=1;fillColor=none;strokeColor=none;'   }, { name: 'parent', type: 'EReference', eType: 'Node', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: 1, eOpposite: '', style: ''   }, { name: 'name', type: 'EAttribute', eType: 'EString', value: '1234', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: 1, eOpposite: '', style: ''   }, { name: 'gsmLabel', type: 'EAttribute', eType: 'EString', value: 'name', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, ]}, ])),
        ]
        this.SMLGSetDiagramContext(JSON.stringify([{ name: 'uri', type: 'EAttribute', eType: 'EString', value: 'tree', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'prefix', type: 'EAttribute', eType: 'EString', value: 'tree', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'package', type: 'EAttribute', eType: 'EString', value: 'tree', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'diagram', type: 'EAttribute', eType: 'EString', value: 'Tree', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { name: 'class', type: 'EAttribute', eType: 'EString', value: 'Tree', editable: false, visible: true, compartment: false, lowerBound: 0, upperBound:1, eOpposite: '' }, { properties: [ { name: 'children', type: 'EReference', eType: 'Node', value: '', editable: true, visible: true, compartment: true, lowerBound: 0, upperBound: -1, eOpposite: ''   }, { name: 'parent', type: 'EReference', eType: 'Node', value: '', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: 1, eOpposite: ''   }, { name: 'name', type: 'EAttribute', eType: 'EString', value: '1234', editable: true, visible: true, compartment: false, lowerBound: 0, upperBound: 1, eOpposite: ''   }, ]}, ]));
        this.addPaletteFunctions('tree', 'Tree', (expand != null) ? expand : true, fns);
    }
    SMLG.editorUI.sidebar.SMLGAddTreePalette(true);
    console.log("End of tree.js");


