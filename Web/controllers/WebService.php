<?php
	class WebService extends Controller {

		function __construct(){
			parent::__construct();
		}

		public function index(){
			$Loader 	= new LoadModel("WebServiceModel");
			$WebService = new WebServiceModel();

			$ListWebService = $WebService->getIndex();

			echo $ListWebService;
		}

		public function Login(){
			$Model 		= new LoadModel("WebServiceModel");
			$WebService = new WebServiceModel();

			if (isset($_REQUEST['username']) && isset($_REQUEST['password']))
				if ($WebService->LoginUser($_REQUEST['username'], $_REQUEST['password']))
					echo 0;
				else
					echo 2;		#Error de logueo, credenciales incorrectas
			else
				echo 1; 		#Error al enviar los datos
		}

		public function AddUser(){
			$Model 		= new LoadModel("WebServiceModel");
			$WebService = new WebServiceModel();

			if (isset($_REQUEST))
				if ($WebService->CheckUser($_REQUEST['username']) && $WebService->CheckUserEmail($_REQUEST['email']))
					if ($WebService->AddUser($_REQUEST))
						echo 0;			#Agregado
					else
						echo 4; 		#El usuario no se ha podido registrar
				else
					echo 3;				#El usuario ya existe
			else
				echo 2;					#No se han recibido los parametros
		}


	}
?>