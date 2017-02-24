function drawMap(activities, transitions) {
	mxConstants.ACTIVE_REGION = 1;

	// Checks if browser is supported
	if (!mxClient.isBrowserSupported()) {
		// Displays an error message if the browser is
		// not supported.
		mxUtils.error('Browser is not supported!', 200, false);
	} else {
		var container = document.createElement('div');
		container.style.position = 'absolute';
		container.style.overflow = 'hidden';
		container.style.left = '10px';
		container.style.top = '10px';
		container.style.right = '10px';
		container.style.bottom = '10px';
		container.style.background = 'url("../../images/grid.gif")';

		// Workaround for Internet Explorer ignoring certain styles
		if (mxClient.IS_QUIRKS) {
			document.body.style.overflow = 'hidden';
			new mxDivResizer(container);
		}

		document.body.appendChild(container);

		// Creates the graph inside the given container
		var graph = new mxGraph(container);
		graph.setCellsMovable(false);
		graph.setCellsSelectable(false);
		graph.setHtmlLabels(true);
		graph.view.setTranslate(20, 20);

		function updateStyle(state, hover) {
			if (hover) {
				state.style[mxConstants.STYLE_FILLCOLOR] = '#EEEEEE';
			}
		}
		;

		// Changes fill color to Grey on mouseover
		graph.addMouseListener(
			{
				currentState : null,
				previousStyle : null,
				mouseDown : function(sender, me) {
					if (this.currentState != null) {
						this.dragLeave(me.getEvent(), this.currentState);
						this.currentState = null;
					}
				},
				mouseMove : function(sender, me) {
					if (this.currentState != null && me.getState() == this.currentState) {
						return;
					}

					var tmp = graph.view.getState(me.getCell());

					// Ignores everything but vertices
					if (graph.isMouseDown || (tmp != null && !
						graph.getModel().isVertex(tmp.cell))) {
						tmp = null;
					}

					if (tmp != this.currentState) {
						if (this.currentState != null) {
							this.dragLeave(me.getEvent(), this.currentState);
						}

						this.currentState = tmp;

						if (this.currentState != null) {
							this.dragEnter(me.getEvent(), this.currentState);
						}
					}
				},
				mouseUp : function(sender, me) {},
				dragEnter : function(evt, state) {
					if (state != null) {
						this.previousStyle = state.style;
						state.style = mxUtils.clone(state.style);
						updateStyle(state, true);
						state.shape.apply(state);
						state.shape.redraw();

						if (state.text != null) {
							state.text.apply(state);
							state.text.redraw();
						}
					}
				},
				dragLeave : function(evt, state) {
					if (state != null) {
						state.style = this.previousStyle;
						updateStyle(state, false);
						state.shape.apply(state);
						state.shape.redraw();

						if (state.text != null) {
							state.text.apply(state);
							state.text.redraw();
						}
					}
				}
			});

		// Enables rubberband selection
			//new mxRubberband(graph);

		// Gets the default parent for inserting new cells. This
		// is normally the first child of the root (ie. layer 0).
		var parent = graph.getDefaultParent();

		// Adds cells to the model in a single step
		graph.getModel().beginUpdate();
		try {
			var activityStyle = "fontSize=14;arcSize=10;rounded=1;fontColor=#000000;fontStyle=1;whiteSpace=wrap;html=1;fillColor=#FFFFFF;strokeColor=#000000;";
			var transitionStyle = "endArrow=block;html=1;endFill=1;strokeColor=#000000;";
			var vertices = new Object();

			for (var entityId in activities) {
				if (activities.hasOwnProperty(entityId)) {
					var label = activities[entityId];
					var vertex = graph.insertVertex(parent, null, label, 0, 0, 120, 80, activityStyle);
					vertices[entityId] = vertex;
				}
			}

			for (var entityId in transitions) {
				if (transitions.hasOwnProperty(entityId)) {
					var sourceEntityId = transitions[entityId]["source"];
					var targetEntityId = transitions[entityId]["target"];

					var sourceVertex = vertices.hasOwnProperty(sourceEntityId) ? vertices[sourceEntityId] : null;
					var targetVertex = vertices.hasOwnProperty(targetEntityId) ? vertices[targetEntityId] : null;

					if (sourceVertex != null && targetVertex != null) {
						graph.insertEdge(parent, null, '', sourceVertex, targetVertex, transitionStyle);
					}
				}
			}

			//			for (i = 0; i < activityList.length; i++){
			//				var activity = activityList[i];
			//				graph.insertVertex(parent, null, activity, 0, 0, 120, 80, activityStyle);
			//			}

			//			var v1 = graph.insertVertex(parent, null, '1', 120, 150, 120, 80, style);
			//			var v2 = graph.insertVertex(parent, null, '2', 200, 150, 120, 80, style);
			//			var v3 = graph.insertVertex(parent, null, '3', 280, 100, 120, 80, style);
			//			var v4 = graph.insertVertex(parent, null, '4', 280, 200, 120, 80, style);
			//			var v5 = graph.insertVertex(parent, null, '5', 360, 150, 120, 80, style);
			//			var e1 = graph.insertEdge(parent, null, '', v1, v2, style2);
			//			var e2 = graph.insertEdge(parent, null, '', v2, v3, style2);
			//			var e3 = graph.insertEdge(parent, null, '', v2, v4, style2);
			//			var e4 = graph.insertEdge(parent, null, '', v3, v5, style2);
			//			var e5 = graph.insertEdge(parent, null, '', v4, v5, style2);

			var layout = null;
			layout = new mxHierarchicalLayout(graph, mxConstants.DIRECTION_WEST);

			layout.maintainParentLocation = true;
			layout.parentBorder = 100;
			layout.intraCellSpacing = 100;
			layout.execute(parent);

		} finally {
			graph.getModel().endUpdate();
		}
	}
}