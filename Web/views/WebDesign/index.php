<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<title>Tragua</title>
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<link rel="stylesheet" href="views/WebDesign/css/normalizer.css">
	<link rel="stylesheet" href="views/WebDesign/css/plantilla.css">
	<link rel="stylesheet" href="views/WebDesign/css/css.css">

	<!-- <link rel="stylesheet" href="views/WebDesign/login/css/style.css"> -->

	<script src="views/WebDesign/js/jquery.js"></script>
	<script src="views/WebDesign/js/jquery.js"></script>
	<script src="views/WebDesign/js/modernizr.js"></script>
	<script src="views/WebDesign/js/plantilla.js"></script>
	<script src="views/WebDesign/js/js.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBf9Fuvi4yBnxwQytLLlAbgAnWd2W_peOU&callback=initMap&libraries=places" async defer></script>
</head>
<body id="perspective" class="perspective effect-moveleft body_maps">
	<section class="container">
		<header class="menu">
			<section id="Logo">Logo</section>
			<section id="Opciones">
				<section id="Outer1">Log/Reg</section>
				<section id="Outer2">Menu</section>
			</section>
		</header>
		<main class="maps wrapper">
			<section id="directions">
			    <input id="origin-input" class="controls" type="text" placeholder="Enter a destination location" >
			    <input id="destination-input" class="controls" type="text" placeholder="Enter a destination location">
			    <div id="mode-selector" class="controls">
			      	<input type="radio" name="type" id="changemode-walking" checked="checked" class="click">
			      	<label for="changemode-walking" class="click">Caminar</label>
			      	<input type="radio" name="type" id="changemode-driving" class="click">
			      	<label for="changemode-driving" class="click">Conducir</label>
			    </div>
			</section>
			<section id="map"></section>
		</main>
	</section>
	<nav class="outer-nav top horizontal hidden">
		<ul>
			<li>Opción 1</li>
			<li>Opción 2</li>
			<li>Opción 3</li>
			<li>Opción 4</li>
		</ul>
	</nav>

	<section class="login hidden acaelcodigo">
		<?php
			include ("views/WebDesign/login/file.php");
		?>
	</section>
    <!-- <script  src="views/WebDesign/login/js/index.js"></script> -->
</body>
</html>