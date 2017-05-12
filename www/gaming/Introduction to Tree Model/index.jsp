<!DOCTYPE html>
<html>
<head>
<title>Map</title>

<link rel="stylesheet" type="text/css"
    href="js/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/smlg.css">

<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="js/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript">
	mxBasePath = 'grapheditor/mxlib';
</script>
<script type="text/javascript" src="grapheditor/mxlib/js/mxClient.js"></script>
<script type="text/javascript" src="smlg/smlg.utils.js"></script>
<script type="text/javascript" src="smlg/smlg.platform.js"></script>
<script type="text/javascript" src="smlg/smlg.map.js"></script>
</head>
<body>
    <%@include file='../../view/template/header.jsp'%>
    <div class="jumbotron">
        <div class="container">
            <h2 id="title"></h2>
        </div>
        <div id="map"></div>
    </div>
</body>

<script type="text/javascript">
    var gameName = getParameterByName("game");
    document.title = gameName;
    document.getElementById("title").innerHTML = gameName;
    var activities = new Object();
    var transitions = new Object();
    var inputModels = null;
    var outputModels = null;
    
    //ACTIVITIES
    //--Activity Create A Root
    //----input models
    inputModels = [];
    
    //----output models
    outputModels = [];
    outputModels.push("Model A"); 
 
    //----activity
    activities["//@entities.7"] = {'name': 'Create A Root', 'metamodel': 'tree', 
        'inputModels': inputModels, 'outputModels': outputModels };
        
    //--Activity Create A Root and A Leaf
    //----input models
    inputModels = [];
    inputModels.push("Model A"); 
    
    //----output models
    outputModels = [];
    outputModels.push("Model B"); 
 
    //----activity
    activities["//@entities.9"] = {'name': 'Create A Root and A Leaf', 'metamodel': 'tree', 
        'inputModels': inputModels, 'outputModels': outputModels };
        
    //--Activity Create A Tree with Two Leaves
    //----input models
    inputModels = [];
    inputModels.push("Model C"); 
    
    //----output models
    outputModels = [];
 
    //----activity
    activities["//@entities.12"] = {'name': 'Create A Tree with Two Leaves', 'metamodel': 'tree', 
        'inputModels': inputModels, 'outputModels': outputModels };
        

    //TRANSITIONS
    transitions["//@entities.0"] = {"source": "//@entities.9", "target": "//@entities.12"}; 
    transitions["//@entities.11"] = {"source": "//@entities.15", "target": "//@entities.7"}; 
    transitions["//@entities.13"] = {"source": "//@entities.12", "target": "//@entities.6"}; 
    transitions["//@entities.14"] = {"source": "//@entities.7", "target": "//@entities.9"}; 
    
    drawMap(activities, transitions);
</script>
</html>
