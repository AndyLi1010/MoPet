<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author"
	content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Hugo 0.88.1">
<title>loginSystem</title>

<link rel="canonical"
	href="https://getbootstrap.com/docs/5.1/examples/sign-in/">



<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#info').click(function() {
			$('#floatingPassword').val('123');
			$('#floatingInput').val('cindy070814@gmail.com');
			document.getElementById("123").innerText = ""
			document.getElementById("456").innerText = ""
		})
		$('#floatingInput').click(function() {

			document.getElementById("123").innerText = ""
		})
		$('#floatingPassword').click(function() {

			document.getElementById("456").innerText = ""
		})
	})
</script>
<style>
html, body {
	height: 100%;
}

body {
	display: flex;
	align-items: center;
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin {
	width: 100%;
	max-width: 330px;
	padding: 15px;
	margin: auto;
}

.form-signin .checkbox {
	font-weight: 400;
}

.form-signin .form-floating:focus-within {
	z-index: 2;
}

.form-signin input[type="email"] {
	margin-bottom: -1px;
	border-bottom-right-radius: 0;
	border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
	margin-bottom: 10px;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}

.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>

</head>

<body class="text-center">

	<main class="form-signin">
		<form action="checklogin.controller" method="post">
			<img class="mb-4" src="icons.png" alt="" width="100" height="100">
			<h1 class="h3 mb-3 fw-normal">Please sign in</h1>

			<div class="form-floating">
				<input type="text" class="form-control" id="floatingInput"
					name="userEmail"> <label for="floatingInput">Email
					address</label>
				<p style="color: #E60000" id="123">${errors.userEmail}</p>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" id="floatingPassword"
					name="userPwd"> <label for="floatingPassword">Password</label>
				<p style="color: #E60000" id="456">${errors.userPwd}</p>
			</div>
			<p style="color: #E60000">${errors.msg}</p>
			<p style="color: #E60000">${errors.msg2}</p>
			<button class="w-100 btn btn-lg btn-primary" type="submit">Sign
				In</button>
			<p class="mt-5 mb-3 text-muted">© 2017–2021</p>
		</form>
		<button id="info" type="submit">一鍵帶入</button>
	</main>
</body>
</html>