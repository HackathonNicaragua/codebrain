<?php

class Model {
	public $db = null;

	function __construct(){
		try {
			$this->getConnection();
		} catch (PDOException $e) {
		    die("Falló la conexión: ".$e->getMessage());
		}
	}

	public function getConnection(){
		$host = "127.0.0.1";
        $user = "SideMaster";
        $pass = "Inform@tico";
        $dbase = "hacktest";

		$cn = sprintf("host=%s;dbname=%s", $host, $dbase);

		if ($this->db = new PDO("mysql:".$cn, $user, $pass)){
			$this->db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); 

			if (@$this->db->query("SET NAMES 'utf8'"))
				return true;
		}

		return false;
	}
}

?>
