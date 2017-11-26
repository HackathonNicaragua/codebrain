<?php
	@session_start();
?>

<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<title>Tragua</title>
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<link rel="stylesheet" href="views/WebDesign/css/normalizer.css">
	<link rel="stylesheet" href="views/WebDesign/css/plantilla.css">
	<link rel="stylesheet" href="views/WebDesign/css/css.css">
	
	<?php
		if (@$_SESSION['login'] != 1){
			?>
				<link rel="stylesheet" href="views/WebDesign/css/extra.css">
			<?php
		} else {
			?>
				<link rel="stylesheet" href="views/WebDesign/css/session_extra.css">
			<?php
		}
	?>

	<link rel="stylesheet" href="views/WebDesign/login/css/font-awesome.css">

	<script src="views/WebDesign/js/jquery.js"></script>
	<script src="views/WebDesign/js/modernizr.js"></script>
	<script src="views/WebDesign/js/plantilla.js"></script>
	<script src="views/WebDesign/js/js.js"></script>
    <script id="googleapi" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBf9Fuvi4yBnxwQytLLlAbgAnWd2W_peOU&callback=initMap&libraries=places" async defer></script>
</head>
<body id="perspective" style="background-color: rgba(48,63,129,0.7);" class="perspective effect-moveleft body_maps">
	<section class="container">
		
		<?php
			if (@$_SESSION['login'] != 1){
				?>
					<header class="menu">
						<section id="Logo"><img src="views/WebDesign/src/logo.png" style="width: 45px; margin: -8px 20px;" alt="Logo"><label class="LogoLabel">Tragua</label></section>
						<section id="Opciones">
							<section id="Outer1"><i class="fa fa-user-circle" aria-hidden="true"></i> Iniciar sesión</section>
							<section id="Outer2"><i class="fa fa-bars" aria-hidden="true"></i> Idioma</section>
						</section>
					</header>
				<?php
			} else {
				?>
					<header class="menu">
						<section id="Logo"><img src="views/WebDesign/src/logo.png" style="width: 45px; margin: -8px 20px;" alt="Logo"><label class="LogoLabel">Tragua</label></section>
						<section id="Opciones">
							<section id="Outer2" class="Outer1"><i class="fa fa-bars" aria-hidden="true"></i> Idioma</section>
							<section id="Outer3" class="Outer2"><i class="fa fa-user-circle" aria-hidden="true"></i> <?php echo @$_SESSION['firstname']; ?></section>
							<section id="Outer4" class="Outer3" onclick="javascript: CloseSession();"><i class="fa fa-bars" aria-hidden="true"></i> Cerrar sesión</section>
						</section>
					</header>
				<?php
			}
		?>

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
			<section id="filters">
				<section id="modal-view">
					<label for="f1" class="lbl-filter">
						<input type="checkbox" id="f1" value="museum">
						<img src="views/WebDesign/img/billete.png" alt="f1">
					</label>
					<label for="f2" class="lbl-filter">
						<input type="checkbox" id="f2" value="lodging">
						<img src="views/WebDesign/img/dormir.png" alt="f2">
					</label>
					<label for="f3" class="lbl-filter">
						<input type="checkbox" id="f3" value="hospital">
						<img src="views/WebDesign/img/hospital.png" alt="f3">
					</label>
					<label for="f4" class="lbl-filter">
						<input type="checkbox" id="f4" value="restaurant">
						<img src="views/WebDesign/img/restaurante.png" alt="f4">
					</label>
					<label for="f5" class="lbl-filter">
						<input type="checkbox" id="f5" value="church">
						<img src="views/WebDesign/img/iglesia.png" alt="f5">
					</label>
					<label for="f6" class="lbl-filter">
						<input type="checkbox" id="f6" value="store">
						<img src="views/WebDesign/img/Tienda.png" alt="f6">
					</label>
				</section>
				<input type="button" value="+" id="btn_filter">
			</section>
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