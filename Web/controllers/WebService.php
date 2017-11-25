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

	}
?>