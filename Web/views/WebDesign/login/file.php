<link rel="stylesheet" href="views/WebDesign/login/css/style.css">

<!-- Agrega el formulario aqui -->
<div class="form login" id="form">
    <div class="finishedFormCod">
    	<div class="field email">
            <div class="icon"></div>
            <input class="input" id="username" type="username" placeholder="Nombre de usuario" autocomplete="off"/>
        </div>

        <div class="field password">
            <div class="icon"></div>
            <input class="input" id="password" type="password" placeholder="Contraseña"/>
        </div>

        <button class="button" id="btn_login">INICIAR SESIÓN
        <div class="side-top-bottom"></div>
        <div class="side-left-right"></div>
    </div>
    </button><small onclick="javascript: ChangeWindowsRegistro();">Registrarse</small>
</div>

<script src="views/WebDesign/login/js/index.js"></script>