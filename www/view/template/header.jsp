<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="Main">SMLG</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<li class="active"><a href="Main">Main Menu</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="LoggingOut"><span
						class="glyphicon glyphicon-log-out"></span> Logout<span
						id="label-username"><% 
						String username = "";
						if (request.getAttribute("username")!= null){
							username = request.getAttribute("username").toString();
						}%><%= ": " + username %>
					</span></a></li>
			</ul>
		</div>
	</div>
</nav>