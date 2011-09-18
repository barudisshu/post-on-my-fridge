<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
	<head>
		<title>Posts on the fridge</title>
		
		<link rel="stylesheet" type="text/css" href="css/main.css"/>
		<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.16.custom.css"/>
		<link rel="stylesheet" type="text/css" href="css/captcha.css"/>
		
		<script language="javascript" type="text/javascript" src = "/scripts/jquery-1.6.3.min.js"></script>
		<script language="javascript" type="text/javascript" src = "/scripts/jquery-ui-1.8.16.custom.min.js"></script>
		<script language="javascript" type="text/javascript" src = "/scripts/jquery-captcha.js"></script>
		<script language="javascript" type="text/javascript" src = "/scripts/post-on-my-fridge.js"></script>
		
		<script language="javascript" type="text/javascript" src = "/scripts/browser-update.js"></script>
		<script language="javascript" type="text/javascript" src = "/scripts/google-analytics.js"></script>
			
		<meta charset="utf-8">
	</head>
	<body>
	
	<div class="header"></div>
	
	<div class="global">				
		<div class ="leftPanel">
			<div class="headline">
				<ol>
	  				<li>Fill in the post. </li>
					<li>Solve the captcha.</li>
					<li>Drop the post on the fridge.</li>
				</ol>
			</div>
			
			<form id="postForm" accept-charset="utf-8">
				<div class="newPost">
					<div class="content">
						<textarea name=content id="content" rows="8" cols="20" maxlength="100" title="Content goes there">
						</textarea>
					</div>
					<div class="author">
						<input type="text" name=author id="author" maxlength="15" size="15" title="Who are u?"/>
					</div>		
				</div>
				<div class="ajax-fc-container"></div>
			</form>
			
			<div class ="trash_bin"></div>
			
			<div class="info">
				<img src="http://code.google.com/appengine/images/appengine-silver-120x30.gif" alt="Powered by Google App Engine" />
				&nbsp;&&nbsp;
				<a href="https://github.com/shagaan/PostOnMyFridge" target="blank">
					<img src="https://a248.e.akamai.net/assets.github.com/images/modules/header/logov6.svg" alt="Powered by Google App Engine" />
				</a>
			</div>
			
		</div>	
				
		<div class="fridge"></div>
											
	</div>
	</body>
</html>