
var displayAvailableMetamodels = function() {
	var request = new XMLHttpRequest;
	request.onload = function() {
		listFiles()
	};
	request.open("GET", "ListFiles?path=./metamodel/", false);
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
	request.open("GET", "ListFiles?path=./learning/", false);
	request.send();

	function listFiles() {
		var files = JSON.parse(request.responseText);
		var divMetamodelList = document.getElementById("divLearningList");
		
		var h2New = document.createElement('h2');
		h2New.innerHTML = "<a href='grapheditor?metamodel=eoml'>New ...</a>";
		divMetamodelList.appendChild(h2New);
		
		for (var i = 0; i < files.length; i++) {
			
			var h2 = document.createElement('h2');
			h2 = document.createElement('h2');
			h2.innerHTML = capitalizeFirstLetter(files[i]) + " | " 
				+ "<a href=''>Edit</a> | " 
				+ "<a href=''>Delete</a> | ";
			divMetamodelList.appendChild(h2);

		}
	}
}