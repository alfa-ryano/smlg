
var displayAvailableMetamodels = function() {
	var request = new XMLHttpRequest;
	request.onload = function() {
		listFiles()
	};
	request.open("GET", "ListFiles?path=./metamodel/", true);
	request.send();

	function listFiles() {
		var files = JSON.parse(request.responseText);
		var divMetamodelList = document.getElementById("divMetamodelList");
		for (var i = 0; i < files.length; i++) {

			var h2 = document.createElement('h2');
			h2.innerHTML = capitalizeFirstLetter(files[i]);
			divMetamodelList.appendChild(h2);

			var div = document.createElement('div');
			div.id = "div" + capitalizeFirstLetter(files[i]);
			div.innerHTML = "<ul id='ul" + capitalizeFirstLetter(files[i]) + "'>" +
				"<li><a href='grapheditor?metamodel=" + files[i] + "'>New ...<a/></li></ul>";
			divMetamodelList.appendChild(div);
		}
	}
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