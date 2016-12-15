    /**
     * Adds the filesystem to the sidebar.
     */
    SMLG.editorUI.sidebar.SMLGAddFilesystemPalette = function(expand) {
        var fns = [
		         this.SMLGCreateVertexTemplateEntry('swimlane;whiteSpace=wrap;html=1;', 200, 200, 
                 'Drive', 'Drive', null, null, 'container swimlane lane pool', JSON.stringify([])),
		         this.SMLGCreateVertexTemplateEntry('swimlane;whiteSpace=wrap;html=1;', 200, 200, 
                 'Folder', 'Folder', null, null, 'container swimlane lane pool', JSON.stringify([])),
  
		         this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 
                 'Shortcut', 'Shortcut', null, null, null, JSON.stringify([])),
                 this.SMLGCreateEdgeTemplateEntry('dashed=1;endArrow=classic;', 50, 50, 
                 '', 'Target', JSON.stringify([])),                                       
                 this.SMLGCreateEdgeTemplateEntry('dashed=1;dashPattern=1 4;strokeWidth=2;endArrow=none;', 50, 50, 
                 '', 'Sync', JSON.stringify([])),                                       
  
		         this.SMLGCreateVertexTemplateEntry('whiteSpace=wrap;html=1;', 60, 60, 
                 'File', 'File', null, null, null, JSON.stringify([{'name' : ' name','type' : 'String','value' : 'foo','editable' : 'true'},])),
    ]
        this.addPaletteFunctions('filesystem', 'Filesystem', (expand != null) ? expand : true, fns);
    }
    SMLG.editorUI.sidebar.SMLGAddFilesystemPalette(true);
    console.log("End of filesystem.js");

