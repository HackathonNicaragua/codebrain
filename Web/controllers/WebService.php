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

		
	}
?>