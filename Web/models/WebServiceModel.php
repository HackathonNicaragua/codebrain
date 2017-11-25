<?php
	class WebServiceModel extends Model {
		public function getIndex(){
			return "Welcome...";
		}

		public function CleanString($str) {
		    $str = trim($str);
		 	
		    $str = str_replace(
		        array('á', 'à', 'ä', 'â', 'ª', 'Á', 'À', 'Â', 'Ä'),
		        array('a', 'a', 'a', 'a', 'a', 'A', 'A', 'A', 'A'),
		        $str
		    );

		    $str = str_replace(
		        array('é', 'è', 'ë', 'ê', 'É', 'È', 'Ê', 'Ë'),
		        array('e', 'e', 'e', 'e', 'E', 'E', 'E', 'E'),
		        $str
		    );

		    $str = str_replace(
		        array('í', 'ì', 'ï', 'î', 'Í', 'Ì', 'Ï', 'Î'),
		        array('i', 'i', 'i', 'i', 'I', 'I', 'I', 'I'),
		        $str
		    );

		    $str = str_replace(
		        array('ó', 'ò', 'ö', 'ô', 'Ó', 'Ò', 'Ö', 'Ô'),
		        array('o', 'o', 'o', 'o', 'O', 'O', 'O', 'O'),
		        $str
		    );

		    $str = str_replace(
		        array('ú', 'ù', 'ü', 'û', 'Ú', 'Ù', 'Û', 'Ü'),
		        array('u', 'u', 'u', 'u', 'U', 'U', 'U', 'U'),
		        $str
		    );
		 
		    $str = str_replace(
		        array('ñ', 'Ñ', 'ç', 'Ç'),
		        array('n', 'N', 'c', 'C',),
		        $str
		    );

		    #Se retorna el nuevo string.
		    return $str;
		}

		public function getUsers(){
			$stmt = $this->db->query("SELECT * FROM user;");

			if ($stmt->rowCount() > 0)
	    		while ($r = $stmt->fetch(\PDO::FETCH_ASSOC))
					$RawData[] = $r;

			return $RawData;
		}

		public function LoginUser($usr, $pwd){
	    	$usr = $this->CleanString($usr);

	    	$pwd = trim($pwd);
			if (!get_magic_quotes_gpc())
				$usr = addslashes($usr);

			$usr 	= mysql_escape_string($usr);
	    	$stmt 	= $this->db->query("SELECT password FROM user WHERE username='".$usr."';");

	    	if ($stmt->rowCount() > 0)
	    		while ($r = $stmt->fetch(\PDO::FETCH_ASSOC))
	    			if (password_verify($pwd, $r['password']))
	    				return true;

	    	return false;
	    }

	    public function AddUser($Array){
	    	$username 	= empty($Array['username']) 	? "-" : $Array['username'];
	    	$username 	= $this->CleanString($username);

	    	$password 	= empty($Array['password']) 	? "-" : $Array['password'];
	    	$firstname 	= empty($Array['firstname']) 	? "-" : $Array['firstname'];
	    	$lastname 	= empty($Array['lastname']) 	? "-" : $Array['lastname'];
	    	$email 		= empty($Array['email']) 		? "-" : $Array['email'];
	    	
	    	$q 			= "INSERT INTO user(username, password) VALUES (:username,:password);";
	    	$stmt 		= $this->db->prepare($q);

	    	$stmt->bindValue(":username", $username);
	    	$stmt->bindValue(":password", password_hash($password, PASSWORD_DEFAULT));
	    
	    	if ($stmt->execute())
	    		if ($this->AddUserInfo($username, $firstname, $lastname, $email))
	    			return true;

	    	return false;
	    }

	    public function CheckUser($usr){
	    	$R = $this->db->query("SELECT * FROM user WHERE username='".$usr."';");

	    	if ($R->rowCount() > 0)
	    		return false;

	    	return true;
	    }
	}
}