<html>
<head>

</head>
<body>
	<div class="container">
		<h2>Please Confirm</h2>

		<p>
			clientId =  "${authorizationRequest.clientId}" ,
			approved =  "${authorizationRequest.approved?string('true', 'false')}" ,
			redirectUri = "${authorizationRequest.redirectUri}"
			scope = ${authorizationRequest.scope?join(", ")}.
		</p>
		<form id="confirmationForm" name="confirmationForm"
			action="../oauth/authorize" method="post">
			<input name="user_oauth_approval" value="true" type="hidden" />
			<button class="btn btn-primary" type="submit">同意</button>
		</form>
		<form id="denyForm" name="confirmationForm"
			action="../oauth/authorize" method="post">
			<input name="user_oauth_approval" value="false" type="hidden" />
			<button class="btn btn-primary" type="submit">取消</button>
		</form>
	</div>

</body>
</html>