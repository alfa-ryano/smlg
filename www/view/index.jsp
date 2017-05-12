<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SMLG: Software Modelling Learning Gamification</title>

<link rel="stylesheet" type="text/css"
	href="js/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/smlg.css">

<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="js/bootstrap/js/bootstrap.js"></script>

</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href=".">SMLG</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="Main">Main Menu</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-user"></span>
							Register</a></li>
					<li><a href="LoggingOut"><span class="glyphicon glyphicon-log-out"></span>
							Logout: 
							<span id="label-username">
							<%=request.getAttribute("username").toString() %>
							</span></a></li>
				</ul>
			</div>
		</div>
	</nav>


	<div class="jumbotron">
		<div id="divMetamodelList" class="container list-group text-center">
			<a href="PlayGame" class="list-group-item list-group-item-action">
				<h2>Play Game</h2>
			</a> <a href="DesignLearning"
				class="list-group-item list-group-item-action">
				<h2>Design Learning</h2>
			</a> <a href="CreateModels"
				class="list-group-item list-group-item-action">
				<h2>Create Models</h2>
			</a> <a href="" class="list-group-item list-group-item-action">
				<h2>Register Metamodels</h2>
			</a> <a href="" class="list-group-item list-group-item-action">
				<h2>Options</h2>
			</a>
		</div>
	</div>
</body>
</html>