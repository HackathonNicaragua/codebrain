<link rel="stylesheet" href="views/WebDesign/login/css/style.css">

<!-- Agrega el formulario aqui -->
<div class="form" id="form">
    <div class="finishedFormCod">
    	<div class="field email">
            <div class="icon"></div>
            <input class="input" id="username" type="text" placeholder="* Nombre de usuario" autocomplete="off"/>
        </div>

        <div class="field email">
            <div class="icon"></div>
            <input class="input" id="firstname" type="text" placeholder="Nombre" autocomplete="off"/>
        </div>

        <div class="field email">
            <div class="icon"></div>
            <input class="input" id="lastname" type="text" placeholder="Apellido"/>
        </div>

        <div class="field email">
            <div class="icon"></div>
            <input class="input" id="email" type="email" placeholder="Correo eletrónico"/>
        </div>

        <div class="field email">
            <div class="icon"></div>
            <input class="input" id="password" type="password" placeholder="* Contraseña"/>
        </div>

        <button class="button" id="submit">REGISTRARSE
        <div class="side-top-bottom"></div>
        <div class="side-left-right"></div>
    </div>
    </button><small onclick="javascript: ChangeWindowsLogin();">Tengo una cuenta</small>
</div>

<script src="views/WebDesign/login/js/index.js"></script>