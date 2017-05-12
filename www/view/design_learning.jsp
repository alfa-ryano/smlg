<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Design Learning</title>

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
			<h2>Design Learning</h2>
			<button type="button" class="btn btn-default btn-md"
				data-toggle="modal" data-target="#create-learning">New ...</button>
		</div>
		<div id="divLearningList" class="container"></div>
	</div>


	<!-- Create Modal -->
	<div id="create-learning" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Design Learning</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="usr">Name:</label> <input type="text"
							class="form-control" id="labelName">
					</div>
					<div class="form-group">
						<label for="comment">Description:</label>
						<textarea class="form-control" rows="5" id="textareaDescription"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="createNewLearningDesign()" id="inputButton">Create</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Delete Modal -->
	<div id="delete-learning" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Delete Learning Design</h4>
				</div>
				<div class="modal-body">
					<p>
						Are you sure want to delete <span id="span-delete-learning">this</span>?
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="deleteLearningDesign(this.value)"
						id="button-delete-learning">Yes</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		displayAvailableLearning();
	</script>
</body>
</html>