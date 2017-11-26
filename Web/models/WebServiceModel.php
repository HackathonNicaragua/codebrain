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

	    public function CheckUserEmail($email){
	    	@$R = $this->db->query("SELECT * FROM user WHERE email='".$email."';");

	    	if (@$R->rowCount() > 0)
	    		return false;

	    	return true;
	    }

	    public function AddUserInfo($username, $firstname, $lastname, $email){
	    	$q = "INSERT INTO user_info(username, firstname, lastname, email, date_at, date_unix) VALUES (:username,:firstname,:lastname,:email,:date_at,:date_unix);";
	       	$stmt = $this->db->prepare($q);

	    	$stmt->bindValue(":username", 	$username);
	    	$stmt->bindValue(":firstname", 	$firstname);
	    	$stmt->bindValue(":lastname", 	$lastname);
	    	$stmt->bindValue(":email", 		$email);
	    	$stmt->bindValue(":date_at", 	date('Y-n-j'));
	    	$stmt->bindValue(":date_unix", 	(string)time());

	    	if ($stmt->execute())
	    		return true;

	    	return false;
	    }

	    public function AddBusiness($Array){
	    	$username 	= empty($Array['username']) 	? "-" : $Array['username'];
	    	$username 	= $this->CleanString($username);

	    	$title 			= empty($Array['title']) 		? "-" : $Array['title'];
	    	$description 	= empty($Array['description']) 	? "-" : $Array['description'];
	    	$cod_ruc 		= empty($Array['cod_ruc']) 		? "-" : $Array['cod_ruc'];
	    		    	
	    	$q 		= "INSERT INTO business(username, title, description, cod_ruc, date_at, date_unix) VALUES (:username,:title,:description,:cod_ruc,:date_at,:date_unix);";
	    	$stmt 	= $this->db->prepare($q);

	    	$stmt->bindValue(":username", 		$username);
	    	$stmt->bindValue(":title", 			$title);
	    	$stmt->bindValue(":description", 	$description);
	    	$stmt->bindValue(":cod_ruc", 		$cod_ruc);
	    	$stmt->bindValue(":date_at", 		date('Y-n-j'));
	    	$stmt->bindValue(":date_unix", 		(string)time());	
	    
	    	if ($stmt->execute()){
	    		$id_business = $this->getBusinessId($username, $title);

	    		if ($this->AddBusinessMap($id_business, $Array))
	    			return true;
	    	}

	    	return false;
	    }

	    public function getBusiness(){
	    	$stmt = $this->db->query("SELECT * FROM user;");

			if ($stmt->rowCount() > 0)
	    		while ($r = $stmt->fetch(\PDO::FETCH_ASSOC))
					$RawData[] = $r;

			return $RawData;
	    }

	    public function getBusinessUser($Array){
	    	$username = empty($Array['username']) ? "-" : $Array['username'];
	    	$username = $this->CleanString($username);

	    	$stmt = $this->db->query("SELECT * FROM user WHERE username='".$username."';");

			if ($stmt->rowCount() > 0)
	    		while ($r = $stmt->fetch(\PDO::FETCH_ASSOC))
					$RawData[] = $r;

			return $RawData;
	    }

	    public function updateBusiness($id_business, $Data){
	    	$title 			= empty($Data['title']) 		? "-" : $Data['title'];
	    	$description 	= empty($Data['description']) 	? "-" : $Data['description'];
	    	$cod_ruc 		= empty($Data['cod_ruc']) 		? "-" : $Data['cod_ruc'];

	    	$Update = "UPDATE business SET title='".$title."', description='".$description."', cod_ruc='".$cod_ruc."' WHERE id_business='".$id_business."'";
	    	$Execute = $this->db->query($Update);
	    	
	    	if ($Execute)
	    		return true;
	    	
	    	return false;
	    }

	    public function addBusinessContact($id_business, $Contacts){
	    	$location 	= empty($Contacts['location']) 		? "-" : $Contacts['location'];
	    	$phone_home = empty($Contacts['phone_home']) 	? "-" : $Contacts['phone_home'];
	    	$phone_self = empty($Contacts['phone_self']) 	? "-" : $Contacts['phone_self'];
	    	
	    	if ($this->CheckBusinessContact($id_business))
	    		if ($this->updateBusinessContact($id_business, $Contacts))
	    			return true;

	    	$q 		= "INSERT INTO business_contact(id_business, location, phone_home, phone_self) VALUES (:id_business,:location,:phone_home,:phone_self);";
	    	$stmt 	= $this->db->prepare($q);

	    	$stmt->bindValue(":id_business", 	$id_business);
	    	$stmt->bindValue(":location", 		$location);
	    	$stmt->bindValue(":phone_home", 	$phone_home);
	    	$stmt->bindValue(":phone_self", 	$phone_self);
	    
	    	if ($stmt->execute())
	    		return true;

	    	return false;
	    }

	    public function updateBusinessContact($id_business, $Contacts){
	    	$location 	= empty($Contacts['location']) 		? "-" : $Contacts['location'];
	    	$phone_home = empty($Contacts['phone_home']) 	? "-" : $Contacts['phone_home'];
	    	$phone_self = empty($Contacts['phone_self']) 	? "-" : $Contacts['phone_self'];

	    	$Update = "UPDATE business_contact SET location='".$location."', phone_home='".$phone_home."', phone_self='".$phone_self."' WHERE id_business='".$id_business."'";
	    	$Execute = $this->db->query($Update);
	    	
	    	if ($Execute)
	    		return true;
	    	
	    	return false;
	    }

	    public function getBusinessCount(){
	    	$q = $this->db->query("SELECT * FROM business;");
			return $q->rowCount();
	    }

	    public function getBusinessContact($id_business){
	    	$q = $this->db->query("SELECT * FROM business_contact WHERE id_business='".$id_business."'");

	    	if ($q->rowCount() > 0)
	    		while ($r = $q->fetch(\PDO::FETCH_ASSOC))
					$RawData[] = $r;

			return $RawData;
	    }

	    public function CheckBusiness($id_business){
	    	$R = $this->db->query("SELECT * FROM business WHERE id_business='".$id_business."';");

	    	if ($R->rowCount() > 0)
	    		return false;

	    	return true;
	    }

	    public function addBusinessMap($id_business, $Coords){
	    	$longitud 	= empty($Coords['longitud']) 	? "-" : $Coords['longitud'];
	    	$latitud 	= empty($Coords['latitud']) 	? "-" : $Coords['latitud'];

	    	$q 		= "INSERT INTO business_map(id_business, longitud, latitud) VALUES (:id_business,:longitud,:latitud);";
	    	$stmt 	= $this->db->prepare($q);

	    	$stmt->bindValue(":id_business", $id_business);
	    	$stmt->bindValue(":longitud", 	$longitud);
	    	$stmt->bindValue(":latitud", 	$latitud);
	    
	    	if ($stmt->execute())
	    		return true;

	    	return false;
	    }

	    public function getBusinessId($username, $title){
	    	$QBusiness = $this->db->query("SELECT id_business FROM business WHERE username='".$username."' AND title='".$title."'");
	    
	    	if ($stmt->rowCount() > 0){
	    		$UsersData = [];

	    		while ($row = $stmt->fetch(\PDO::FETCH_ASSOC)){
	    			$UsersData[] = [
	    				'id_business' => $row['id_business']
	    			];
	    		}

	    		foreach ($UsersData as $value) {
	    			return $value['id_business'];
	    		}
	    	}
	    }

	    #Método que obtiene el id_business validando también sus coordenadas. 
	    public function getBusinessIdCheckMap($username, $longitud, $latitud){

	    	if ($QBusiness->rowCount() > 0){

	    		$QBusinessData = [];
	    		while ($row = $QBusiness->fetch(\PDO::FETCH_ASSOC)){
	    			$QBusinessData[] = [
	    				'id_business' 	=> $row['id_business']
	    			];
	    		}

	    		$QBusinessMap = $this->db->query("SELECT * FROM business_map WHERE longitud='".$longitud."' AND latitud='".$latitud."'");
	    		
	    		if ($QBusinessMap->rowCount() > 0){
	    			$QBusinessMapData = [];
		    		while ($row = $QBusinessMap->fetch(\PDO::FETCH_ASSOC)){
		    			$QBusinessMapData[] = [
		    				'id_business' 	=> $row['id_business']
		    			];
		    		}

		    		$QBusinessData_id_business = "-";
		    		foreach ($QBusinessMapData as $value) {
		    			$QBusinessData_id_business = $value['id_business'];
		    		}

		    		#Se recorre la información de $QBusiness
		    		foreach ($QBusinessData as $value) {
		    			if ($QBusinessData_id_business == $value['id_business'])
		    				return $QBusinessData_id_business;
		    		}
	    		}
	    	} else {
	    		return false;
	    	}
	    }

	}

?>