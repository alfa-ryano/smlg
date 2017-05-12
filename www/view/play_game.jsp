<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Play Game</title>

<link rel="stylesheet" type="text/css"
	href="js/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/smlg.css">

<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="js/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="smlg/smlg.utils.js"></script>
<script type="text/javascript" src="smlg/smlg.platform.js"></script>

</head>
<body>
    
    <%@include file='template/header.jsp'%>
    
	<div class="jumbotron">
		<div class="container">
			<h2>Play Game</h2>
		</div>
		<div id="divGameList" class="container list-group"></div>
	</div>

	<script type="text/javascript">
		displayAvailableGames();
	</script>
</body>
</html>