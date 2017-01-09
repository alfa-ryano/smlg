    /**
     * Adds the tree to the sidebar.
     */
    SMLG.editorUI.sidebar.SMLGAddTreePalette = function(expand) {
 
        var fns = [
            this.SMLGCreateVertexTemplateEntry('swimlane;fill=1;html=1;childLayout=stackLayout;fillColor=none;horizontalStack=0;resizeParent=1;resizeLast=1;collapsible=1;marginBottom=0;swimlaneFillColor=#ffffff;', 200, 200,  
                 'Node', 'Node', null, null, 'Node', JSON.stringify([{ name: 'class', type: 'EString', value: 'Node', editable: false, visible: true, compartment: false }, { name: 'children', type: 'Node', value: '', editable: true, visible: true, compartment: true }, { name: 'parent', type: 'Node', value: '', editable: true, visible: true, compartment: false }, { name: 'label', type: 'EString', value: '1234', editable: true, visible: true, compartment: false }, { name: 'label', type: 'EString', value: 'label', editable: false, visible: true, compartment: false }, ])),
        ]
        this.SMLGSetDiagramContext(JSON.stringify([{ name: 'uri', type: 'EString', value: 'tree', editable: false, visible: true, compartment: false }, { name: 'prefix', type: 'EString', value: 'tree', editable: false, visible: true, compartment: false }, { name: 'package', type: 'EString', value: 'tree', editable: false, visible: true, compartment: false }, { name: 'diagram', type: 'EString', value: 'Tree', editable: false, visible: true, compartment: false }, { name: 'class', type: 'EString', value: 'Tree', editable: false, visible: true, compartment: false }, { name: 'children', type: 'Node', value: '', editable: true, visible: true, compartment: true }, { name: 'parent', type: 'Node', value: '', editable: true, visible: true, compartment: false }, { name: 'label', type: 'EString', value: '1234', editable: true, visible: true, compartment: false }, ]));
        this.addPaletteFunctions('tree', 'Tree', (expand != null) ? expand : true, fns);
    }
    SMLG.editorUI.sidebar.SMLGAddTreePalette(true);
    console.log("End of tree.js");


