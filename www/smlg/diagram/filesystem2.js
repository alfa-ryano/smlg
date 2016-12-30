    /**
     * Adds the myPackage to the sidebar.
     */
    SMLG.editorUI.sidebar.SMLGAddMyPackagePalette = function(expand) {
        var fns = [
		         this.SMLGCreateVertexTemplateEntry('swimlane;whiteSpace=wrap;html=1;', 200, 200, 
                 'Drive', 'Drive', null, null, 'container swimlane lane pool', JSON.stringify([{ name: 'diagram', type: 'String', value: 'Filesystem', editable: 'false' }, { name: 'uri', type: 'String', value: 'myUri', editable: 'false' }, { name: 'prefix', type: 'String', value: 'myPrefix', editable: 'false' }, { name: 'package', type: 'String', value: 'myPackage', editable: 'false' }, { name: 'class', type: 'String', value: 'Drive', editable: 'false' }, ])),
		         this.SMLGCreateVertexTemplateEntry('swimlane;whiteSpace=wrap;html=1;', 200, 200, 
                 'Folder', 'Folder', null, null, 'container swimlane lane pool', JSON.stringify([{ name: 'diagram', type: 'String', value: 'Filesystem', editable: 'false' }, { name: 'uri', type: 'String', value: 'myUri', editable: 'false' }, { name: 'prefix', type: 'String', value: 'myPrefix', editable: 'false' }, { name: 'package', type: 'String', value: 'myPackage', editable: 'false' }, { name: 'class', type: 'String', value: 'Folder', editable: 'false' }, ])),
  
		         this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 
                 'Shortcut', 'Shortcut', null, null, null, JSON.stringify([{ name: 'diagram', type: 'String', value: 'Filesystem', editable: 'false' }, { name: 'uri', type: 'String', value: 'myUri', editable: 'false' }, { name: 'prefix', type: 'String', value: 'myPrefix', editable: 'false' }, { name: 'package', type: 'String', value: 'myPackage', editable: 'false' }, { name: 'class', type: 'String', value: 'Shortcut', editable: 'false' }, ])),
                 this.SMLGCreateEdgeTemplateEntry('dashed=1;endArrow=classic;', 50, 50, 
                 '', 'Target', null, null, null, JSON.stringify([{ name: 'diagram', type: 'String', value: 'Filesystem', editable: 'false' }, { name: 'uri', type: 'String', value: 'myUri', editable: 'false' }, { name: 'prefix', type: 'String', value: 'myPrefix', editable: 'false' }, { name: 'package', type: 'String', value: 'myPackage', editable: 'false' }, { name: 'class', type: 'String', value: 'Shortcut', editable: 'false' }, ])),                                       
                 this.SMLGCreateEdgeTemplateEntry('dashed=1;dashPattern=1 4;strokeWidth=2;endArrow=none;', 50, 50, 
                 '', 'Sync', null, null, null, JSON.stringify([{ name: 'diagram', type: 'String', value: 'Filesystem', editable: 'false' }, { name: 'uri', type: 'String', value: 'myUri', editable: 'false' }, { name: 'prefix', type: 'String', value: 'myPrefix', editable: 'false' }, { name: 'package', type: 'String', value: 'myPackage', editable: 'false' }, { name: 'class', type: 'String', value: 'Sync', editable: 'false' }, ])),                                       
  
		         this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 
                 'File', 'File', null, null, null, JSON.stringify([{ name: 'diagram', type: 'String', value: 'Filesystem', editable: 'false' }, { name: 'uri', type: 'String', value: 'myUri', editable: 'false' }, { name: 'prefix', type: 'String', value: 'myPrefix', editable: 'false' }, { name: 'package', type: 'String', value: 'myPackage', editable: 'false' }, { name: 'class', type: 'String', value: 'File', editable: 'false' }, {'name' : ' name','type' : 'String','value' : 'foo','editable' : 'true'},])),
    ]
        this.addPaletteFunctions('myPackage', 'MyPackage', (expand != null) ? expand : true, fns);
    }
    SMLG.editorUI.sidebar.SMLGAddMyPackagePalette(true);
    console.log("End of myPackage.js");

