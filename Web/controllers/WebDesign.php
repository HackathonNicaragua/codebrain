<?php
	class WebDesign extends Controller {

		function __construct(){
			parent::__construct();
		}

		public function index(){

			$Loader = new LoadModel("WebDesignModel");

			$Index = new WebDesignModel();
			$ListIndex = $Index->getIndex();

			(new View("WebDesign/index.php", compact("ListWebDesign")));
		}
	}
?>