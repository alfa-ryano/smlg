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
    //--Activity One-Speed Fan
    //----input models
    inputModels = [];
    inputModels.push("Model A"); 
    
    //----output models
    outputModels = [];
    outputModels.push("Model B"); 
 
    //----activity
    activities["//@entities.10"] = {'name': 'One-Speed Fan', 'metamodel': 'statechart', 
        'inputModels': inputModels, 'outputModels': outputModels };
        
    //--Activity Two-Speed Fan
    //----input models
    inputModels = [];
    inputModels.push("Model B"); 
    
    //----output models
    outputModels = [];
 
    //----activity
    activities["//@entities.11"] = {'name': 'Two-Speed Fan', 'metamodel': 'statechart', 
        'inputModels': inputModels, 'outputModels': outputModels };
        
    //--Activity Tree-Speed Fan
    //----input models
    inputModels = [];
    inputModels.push("Model C"); 
    
    //----output models
    outputModels = [];
 
    //----activity
    activities["//@entities.12"] = {'name': 'Tree-Speed Fan', 'metamodel': 'statechart', 
        'inputModels': inputModels, 'outputModels': outputModels };
        

    //TRANSITIONS
    transitions["//@entities.0"] = {"source": "//@entities.10", "target": "//@entities.11"}; 
    transitions["//@entities.1"] = {"source": "//@entities.11", "target": "//@entities.12"}; 
    transitions["//@entities.2"] = {"source": "//@entities.13", "target": "//@entities.10"}; 
    transitions["//@entities.3"] = {"source": "//@entities.12", "target": "//@entities.14"}; 
    
    drawMap(activities, transitions);
</script>
</html>
