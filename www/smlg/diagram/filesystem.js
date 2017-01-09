    /**
     * Adds the myPackage to the sidebar.
     */
    SMLG.editorUI.sidebar.SMLGAddMyPackagePalette = function(expand) {
 
        var fns = [
            this.SMLGCreateVertexTemplateEntry('swimlane;fill=1;html=1;childLayout=stackLayout;fillColor=none;horizontalStack=0;resizeParent=1;resizeLast=1;collapsible=1;marginBottom=0;swimlaneFillColor=#ffffff;', 200, 200,  
                 'Drive', 'Drive', null, null, 'Drive', JSON.stringify([{ name: 'class', type: 'EString', value: 'Drive', editable: false, visible: true, compartment: false }, { name: 'name', type: 'EString', value: 'foo', editable: true, visible: true, compartment: false }, { name: 'contents', type: 'File', value: '', editable: true, visible: true, compartment: true }, { name: 'label', type: 'EString', value: 'name', editable: false, visible: true, compartment: false }, ])),
            this.SMLGCreateVertexTemplateEntry('swimlane;fill=1;html=1;childLayout=stackLayout;fillColor=none;horizontalStack=0;resizeParent=1;resizeLast=1;collapsible=1;marginBottom=0;swimlaneFillColor=#ffffff;', 200, 200,  
                 'Folder', 'Folder', null, null, 'Folder', JSON.stringify([{ name: 'class', type: 'EString', value: 'Folder', editable: false, visible: true, compartment: false }, { name: 'name', type: 'EString', value: 'foo', editable: true, visible: true, compartment: false }, { name: 'contents', type: 'File', value: '', editable: true, visible: true, compartment: true }, { name: 'label', type: 'EString', value: 'name', editable: false, visible: true, compartment: false }, ])),
                this.SMLGCreateEdgeTemplateEntry('dashed=1;endArrow=classic;', 50, 50, 
                 'Target', 'Target', null, null, 'Shortcut', JSON.stringify([{ name: 'uri', type: 'EString', value: 'myUri', editable: false, visible: true, compartment: false }, { name: 'prefix', type: 'EString', value: 'myPrefix', editable: false, visible: true, compartment: false }, { name: 'package', type: 'EString', value: 'myPackage', editable: false, visible: true, compartment: false }, { name: 'class', type: 'EString', value: 'Shortcut.target', editable: false, visible: true, compartment: false }, ])),
            this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 200, 200,  
                 'Shortcut', 'Shortcut', null, null, 'Shortcut', JSON.stringify([{ name: 'class', type: 'EString', value: 'Shortcut', editable: false, visible: true, compartment: false }, { name: 'name', type: 'EString', value: 'foo', editable: true, visible: true, compartment: false }, { name: 'target', type: 'File', value: '', editable: true, visible: true, compartment: false }, { name: 'label', type: 'EString', value: 'name', editable: false, visible: true, compartment: false }, ])),
            this.SMLGCreateEdgeTemplateEntry('dashed=1;dashPattern=1 4;strokeWidth=2;endArrow=none;', 50, 50, 
                 'Sync', 'Sync', null, null, 'Sync', JSON.stringify([{ name: 'class', type: 'EString', value: 'Sync', editable: false, visible: true, compartment: false }, { name: 'source', type: 'File', value: '', editable: false, visible: true, compartment: false }, { name: 'target', type: 'File', value: '', editable: false, visible: true, compartment: false }, ])),
            this.SMLGCreateVertexTemplateEntry('editable=0;whiteSpace=wrap;html=1;', 200, 200,  
                 'File', 'File', null, null, 'File', JSON.stringify([{ name: 'class', type: 'EString', value: 'File', editable: false, visible: true, compartment: false }, { name: 'name', type: 'EString', value: 'foo', editable: true, visible: true, compartment: false }, { name: 'label', type: 'EString', value: 'name', editable: false, visible: true, compartment: false }, ])),
        ]
        this.SMLGSetDiagramContext(JSON.stringify([{ name: 'uri', type: 'EString', value: 'myUri', editable: false, visible: true, compartment: false }, { name: 'prefix', type: 'EString', value: 'myPrefix', editable: false, visible: true, compartment: false }, { name: 'package', type: 'EString', value: 'myPackage', editable: false, visible: true, compartment: false }, { name: 'diagram', type: 'EString', value: 'Filesystem', editable: false, visible: true, compartment: false }, { name: 'class', type: 'EString', value: 'Filesystem', editable: false, visible: true, compartment: false }, { name: 'drives', type: 'Drive', value: '', editable: true, visible: true, compartment: false }, { name: 'syncs', type: 'Sync', value: '', editable: true, visible: true, compartment: false }, ]));
        this.addPaletteFunctions('myPackage', 'MyPackage', (expand != null) ? expand : true, fns);
    }
    SMLG.editorUI.sidebar.SMLGAddMyPackagePalette(true);
    console.log("End of myPackage.js");


