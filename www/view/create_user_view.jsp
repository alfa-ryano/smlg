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
<script type="text/javascript" src="smlg/smlg.utils.js"></script>
<script type="text/javascript" src="smlg/smlg.platform.js"></script>


</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-sm-4"></div>
			<div class="col-sm-4">
				<h1>Create User</h1>
				<div class="panel panel-default">
					<div class="panel-heading"></div>
					<div class="panel-body">
						<form action="CreateUser" method="post"
							onsubmit="return validateNewUser();">
							<% Object message = request.getAttribute("Message"); %>
							<% if (message != null) { %>
                            <div class="alert alert-danger"><%=message.toString() %></div>
                            <% } %>
							<div class="form-group">
								<label for="email">Email Address:</label> <input type="email"
									class="form-control" id="email" name="email">
							</div>
							<div id="alert-email"></div>
							<div class="form-group">
								<label for="username">Username:</label> <input type="username"
									class="form-control" id="username" name="username">
							</div>
							<div id="alert-username"></div>
							<div class="form-group">
								<label for="password">Password:</label> <input type="password"
									class="form-control" id="password" name="password">
							</div>
							<div id="alert-password"></div>
							<div class="form-group">
								<label for="confirm_password">Confirm Password:</label> <input
									type="password" class="form-control" id="confirm_password"
									name="confirm_password">
							</div>
							<div id="alert-confirm-password"></div>
							<button type="submit" class="btn btn-default">Submit</button>
						</form>
					</div>
				</div>
			</div>
			<div class="col-sm-4"></div>
		</div>
	</div>
</body>
</html>