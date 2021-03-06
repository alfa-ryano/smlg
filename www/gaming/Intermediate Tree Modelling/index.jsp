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
    //--Activity Single Root Tree Model
    //----input models
    inputModels = [];
    
    //----output models
    outputModels = [];
 
    //----activity
    activities["//@entities.7"] = {'name': 'Single Root Tree Model', 'metamodel': 'tree', 
        'inputModels': inputModels, 'outputModels': outputModels };
        
    //--Activity Double Root Tree Model
    //----input models
    inputModels = [];
    
    //----output models
    outputModels = [];
 
    //----activity
    activities["//@entities.8"] = {'name': 'Double Root Tree Model', 'metamodel': 'tree', 
        'inputModels': inputModels, 'outputModels': outputModels };
        
    //--Activity 2-Level Tree Model
    //----input models
    inputModels = [];
    
    //----output models
    outputModels = [];
 
    //----activity
    activities["//@entities.9"] = {'name': '2-Level Tree Model', 'metamodel': 'tree', 
        'inputModels': inputModels, 'outputModels': outputModels };
        
    //--Activity Multiple Root and Level Tree Model
    //----input models
    inputModels = [];
    
    //----output models
    outputModels = [];
 
    //----activity
    activities["//@entities.10"] = {'name': 'Multiple Root and Level Tree Model', 'metamodel': 'tree', 
        'inputModels': inputModels, 'outputModels': outputModels };
        

    //TRANSITIONS
    transitions["//@entities.1"] = {"source": "//@entities.7", "target": "//@entities.8"}; 
    transitions["//@entities.2"] = {"source": "//@entities.7", "target": "//@entities.9"}; 
    transitions["//@entities.3"] = {"source": "//@entities.11", "target": "//@entities.7"}; 
    transitions["//@entities.4"] = {"source": "//@entities.8", "target": "//@entities.10"}; 
    transitions["//@entities.5"] = {"source": "//@entities.9", "target": "//@entities.10"}; 
    transitions["//@entities.6"] = {"source": "//@entities.10", "target": "//@entities.0"}; 
    
    drawMap(activities, transitions);
</script>
</html>
