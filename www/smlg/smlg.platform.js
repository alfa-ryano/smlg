

var displayAvailableModels = function(metamodel) {
	var request = new XMLHttpRequest;
	request.onload = function() {
		handleResponse()
	};
	request.open("GET", "DisplayAvailableModels?path=./modelling/" + metamodel + "/", true);
	request.send();

	function handleResponse() {
		console.log(metamodel + ": " + request.responseText);
		var models = JSON.parse(request.responseText);
		var ul = document.getElementById("ul-" + metamodel);

		for (var model in models) {
			if (models.hasOwnProperty(model)) {

				var li = document.createElement('div');
				li.id = "li-" + model;
				li.setAttribute("class", "list-group-item");
				li.innerHTML = "	<div class='pull-right'>" +
					"		<button type='button' class='btn btn-default' onclick=\"openUrl('./grapheditor?" +
					"			metamodel=" + metamodel + "&mode=modelling&model=" + model + "')\">Edit Model</button>" +
					"		<button type='button' class='btn btn-default' " +
					"			data-toggle='modal' data-target='#delete-model' " +
					"			onclick=\"setWillBeDeletedModel('" + metamodel + "','" + model + "')\"> " +
					"			Delete" +
					"		</button>" +
					"	</div>" +
					"<h4>" + capitalizeFirstLetter(model) + "</h4>" +
					"<h5>" + models[model] + "</h5>"
				ul.appendChild(li);

				$(document).ready(function() {
					$('[data-toggle="popover"]').popover();
				});
			}
		}
	}
}

var displayAvailableMetamodels = function() {
	var request = new XMLHttpRequest;
	request.onload = function() {
		listFiles()
	};
	request.open("GET", "ListFiles?path=./metamodel/", true);
	request.send();

	function listFiles() {
		var metamodels = JSON.parse(request.responseText);
		var divMetamodelList = document.getElementById("divMetamodelList");

		for (var i = 0; i < metamodels.length; i++) {
			var metamodel = metamodels[i];

			var innerHtmlString = "<div class='panel-heading'>" +
				"	<h2>" + capitalizeFirstLetter(metamodel) + "</h2>" +
				"	<button type='button' class='btn btn-default' data-toggle='modal' value='" + metamodel + "' " +
				"	data-target='#create-model' onclick=setLabelMetamodel(this.value)>Create Model</button>" +
				"</div>" +
				"<div class='panel-body'>" +
				"	<div id='ul-" + metamodel + "' class='list-group'>" +
				"	</div>" +
				"</div>";

			var divPanel = document.createElement('div');
			divPanel.id = "div-" + metamodel;
			divPanel.setAttribute("class", "panel panel-default");
			divPanel.innerHTML = innerHtmlString;
			divMetamodelList.appendChild(divPanel);

			//fill ul with models
			displayAvailableModels(metamodel);
		}
	}
}

var createNewModel = function() {
	var metamodel = document.getElementById("labelMetamodel").value;
	var name = document.getElementById("labelName").value;
	var description = document.getElementById("textareaDescription").value;

	if (name == null || name.length <= 0) {
		alert("Name cannot be empty!");
		return;
	}

	var params = "metamodel=" + metamodel + "&name=" + name + "&description=" + description;
	var request = new XMLHttpRequest;
	request.onload = function() {
		handleResponse();
	};
	console.log(params);
	request.open("POST", "CreateModel?", true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(params);

	function handleResponse() {
		var responseText = request.responseText;
		alert(responseText);

		$('#create-model').modal('hide');

		var metamodel = document.getElementById("labelMetamodel").value;
		var model = document.getElementById("labelName").value;
		var description = document.getElementById("textareaDescription").value;

		var ul = document.getElementById("ul-" + metamodel);
		var li = document.createElement('li');
		li.id = "li-" + name;
		li.setAttribute("class", "list-group-item justify-content-between align-middle");
		li.innerHTML = "	<div class='pull-right'>" +
			"		<button type='button' class='btn btn-default' onclick=\"openUrl('./grapheditor?" +
			"			metamodel=" + metamodel + "&mode=modelling&model=" + model + "')\">Edit Model</button>" +
			"		<button type='button' class='btn btn-default' " +
			"			data-toggle='modal' data-target='#delete-model' " +
			"			onclick=\"setWillBeDeletedModel('" + metamodel + "','" + model + "')\"> " +
			"			Delete" +
			"		</button>" +
			"	</div>" +
			"<h4>" + capitalizeFirstLetter(model) + "</h4>" +
			"<h5>" + description + "</h5>"
		ul.insertBefore(li, ul.firstChild);

		$(document).ready(function() {
			$('[data-toggle="popover"]').popover();
		});

		document.getElementById("labelMetamodel").value = "";
		document.getElementById("labelName").value = "";
		document.getElementById("textareaDescription").value = "";
	}

}

var setLabelMetamodel = function(value) {
	document.getElementById("labelMetamodel").value = value;
}

var displayAvailableLearning = function() {
	var request = new XMLHttpRequest;
	request.onload = function() {
		listFiles()
	};
	request.open("GET", "DisplayAvailableLearning?path=./learning/", true);
	request.send();

	function listFiles() {
		var files = JSON.parse(request.responseText);
		var divMetamodelList = document.getElementById("divLearningList");

		for (var file in files) {
			if (files.hasOwnProperty(file)) {
				var innerHtmlString = "<div class='panel-heading'>" +
					"	<h2>" + capitalizeFirstLetter(file) + "</h2>" +
					"	<button type='button' class='btn btn-default' onclick=\"openUrl('./grapheditor?" +
					"metamodel=eoml&mode=learning&model=" + file + "')\">Edit Model</button>" +
					"	<button type='button' class='btn btn-default' onclick=\"openUrl('./grapheditor?metamodel=eoml')\">Edit Information</button>" +
					"	<button type='button' class='btn btn-default'" +
					"		data-toggle='modal' data-target='#delete-learning'" +
					"		value='" + file + "'" +
					"		onclick='setWillBeDeletedLearningDesign(this.value)'>Delete" +
					"	</button>" +
					"</div>" +
					"<div class='panel-body'>" + files[file] + "</div>";

				var divPanel = document.createElement('div');
				divPanel.id = "div-" + file;
				divPanel.setAttribute("class", "panel panel-default");
				divPanel.innerHTML = innerHtmlString;
				divMetamodelList.appendChild(divPanel);
			}
		}
	}
}

var setWillBeDeletedModel = function(metamodelName, modelName) {
	document.getElementById("span-delete-model").innerHTML = "<b>" + capitalizeFirstLetter(modelName) + "</b>";
	document.getElementById("button-delete-model").setAttribute("metamodelName", metamodelName);
	document.getElementById("button-delete-model").setAttribute("modelName", modelName);
}

var deleteModel = function(id) {

	var metamodelName = document.getElementById(id).getAttribute("metamodelName");
	var modelName = document.getElementById(id).getAttribute("modelName");

	var params = "metamodel=" + metamodelName + "&model=" + modelName;
	var request = new XMLHttpRequest;
	request.onload = function() {
		handleResponse();
	};
	request.open("GET", "DeleteModel?" + params, true);
	request.send();

	function handleResponse() {
		var responseText = request.responseText;
		alert(responseText);

		var element = document.getElementById("li-" + modelName);
		element.parentNode.removeChild(element);
		$('#delete-model').modal('hide');
	}
}


var setWillBeDeletedLearningDesign = function(value) {
	document.getElementById("span-delete-learning").innerHTML = "<b>" + capitalizeFirstLetter(value) + "</b>";
	document.getElementById("button-delete-learning").value = value;
}
var deleteLearningDesign = function(learningDesignName) {

	var params = "name=" + learningDesignName;
	var request = new XMLHttpRequest;
	request.onload = function() {
		handleResponse();
	};
	request.open("GET", "DeleteLearningDesign?" + params, true);
	request.send();

	function handleResponse() {
		var responseText = request.responseText;
		alert(responseText);

		var element = document.getElementById("div-" + learningDesignName);
		element.parentNode.removeChild(element);
		$('#delete-learning').modal('hide');
	}
}


var createNewLearningDesign = function() {
	var name = document.getElementById("labelName").value.trim();
	var description = document.getElementById("textareaDescription").value.trim();
	if (name == null || name.length <= 0) {
		alert("Name cannot be empty!");
		return;
	}

	var params = "name=" + name + "&description=" + description;
	var request = new XMLHttpRequest;
	request.onload = function() {
		handleResponse();
	};
	console.log(params);
	request.open("POST", "CreateLearningDesign?", true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(params);

	function handleResponse() {
		var responseText = request.responseText;
		alert(responseText);

		$('#create-learning').modal('hide');

		var name = document.getElementById("labelName").value;
		var description = document.getElementById("textareaDescription").value;
		var divMetamodelList = document.getElementById("divLearningList");

		var innerHtmlString = "<div class='panel-heading'>" +
			"	<h2>" + capitalizeFirstLetter(name) + "</h2>" +
			"	<button type='button' class='btn btn-default' onclick=\"openUrl('./grapheditor?" +
			"metamodel=eoml&mode=learning&model=" + name + "')\">Edit Model</button>" +
			"	<button type='button' class='btn btn-default' onclick=\"openUrl('./grapheditor?metamodel=eoml')\">Edit Information</button>" +
			"	<button type='button' class='btn btn-default'" +
			"		data-toggle='modal' data-target='#delete-learning'" +
			"		value='" + name + "'" +
			"		onclick='setWillBeDeletedLearningDesign(this.value)'>Delete" +
			"	</button>" +
			"</div>" +
			"<div class='panel-body'>" + description + "</div>";

		var divPanel = document.createElement('div');
		divPanel.id = "div-" + name;
		divPanel.setAttribute("class", "panel panel-default");
		divPanel.innerHTML = innerHtmlString;
		divMetamodelList.insertBefore(divPanel, divMetamodelList.firstChild);

		document.getElementById("labelName").value = "";
		document.getElementById("textareaDescription").value = "";


	}
}