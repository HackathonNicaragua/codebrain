$(document).ready(function() {
	// Perspectivas Plugin1
	var contentWrapper = $('.wrapper'), perspectiveWrapper = $('#perspective')[0], support = Modernizr.csstransitions, 
	container = $( '.container' ), transEndEventNames = { 'WebkitTransition': 'webkitTransitionEnd', 'MozTransition': 'transitionend','OTransition': 'oTransitionEnd', 'msTransition': 'MSTransitionEnd', 'transition': 'transitionend' }, 
	transEndEventName = transEndEventNames[ Modernizr.prefixed( 'transition' ) ];

    $("#btn_filter").click(function(event) {
        $("#modal-view").toggleClass('ap');
    });

    $("#BtnSave").click(function(event) {
        if ( 
            $("#TypeBusiness").val().length             * $("#BusinessTextTitle").val().length      *
            $("#BusinessTextDescription").val().length  * $("#BusinessTextRUC").val().length        *
            $("#BusinessTextPhoneLocal").val().length   * $("#BusinessTextPhoneSelf").val().length  *
            $("#BusinessTextCoordLong").val().length    * $("#BusinessTextCoordLat").val().length  != 0 ) {
                send ( {
                    title       : $("#BusinessTextTitle").val(),
                    description : $("#BusinessTextDescription").val(),
                    cod_ruc     : $("#BusinessTextRUC").val(),
                    phone_home  : $("#BusinessTextPhoneLocal").val(),
                    phone_self  : $("#BusinessTextPhoneSelf").val(),
                    longitud    : $("#BusinessTextCoordLong").val(),
                    latitud     : $("#BusinessTextCoordLat").val()
                }, 'controller=WebService&action=AddBusiness' );
        }
        else{
            console.log('Uno de los campos esta vacio!');
        }
    });

    function send ( array , get) {
    $.ajax({
        type: 'POST',
        //url: 'http://hackathon.sidemasters.xyz/HackTest/index.php?'+get,
        url: 'index.php?'+get,
        dataType: "json",
        data: array,
        success:function (data) {
            if (data == 0){
                notificar('Exito');
            } else {
                notificar('Error');
            }
        }   
    });
}


    $(".lbl-filter input[type='checkbox']").change(function(event) {
        var places = $("#filters input[type='checkbox']:checked").map(function(){ return $(this).val();} ).get();
        console.log(places);
        clearMarkers();
        if (places.length > 0) {
            service.nearbySearch({
                location: {'lat': latlon.lat,'lng':latlon.lng},
                radius: 500,
                type: places
            }, callback);
        }
    });

	$('#Outer1, #Outer2').click(function(event) {
		if ($(this).attr('id') == 'Outer1') {
			$('#perspective').removeClass('effect-moveleft').addClass('effect-laydown');
			$('nav.outer-nav').removeClass('right').addClass('hidden');
			$('.login').addClass('right').removeClass('hidden');
		}
		else {
			$('#perspective').removeClass('effect-laydown').addClass('effect-moveleft');
			$('.login').removeClass('right').addClass('hidden');
			$('nav.outer-nav').addClass('right').removeClass('hidden');
		}
		docscroll = scrollY();
		contentWrapper.top = docscroll * -1 + 'px';
		$('body').scrollTop(0);
		classie.add( perspectiveWrapper , 'modalview' );
		setTimeout( function() { classie.add( perspectiveWrapper, 'animate' ); }, 25 );
	});

	$('.container').click(function(event) {
		if( classie.has( perspectiveWrapper, 'animate') ) {
			var onEndTransFn = function( ev ) {
				if( support && ( ev.target.className !== 'container' || ev.propertyName.indexOf( 'transform' ) == -1 ) ) return;
				this.removeEventListener( transEndEventName, onEndTransFn );
				classie.remove( perspectiveWrapper, 'modalview' );
				$('body').scrollTop(docscroll);
			};
			if( support ) {
				perspectiveWrapper.addEventListener( transEndEventName, onEndTransFn );
			}
			else {
				onEndTransFn.call();
			}
			classie.remove( perspectiveWrapper, 'animate' );
			$('.login, nav.outer-nav').removeClass('right').addClass('hidden');
		}
	});

	function scrollY() {
		return window.pageYOffset || window.scrollTop;
	}

});
// Google Maps
var latlon, service, map, markers = [], infoWindow, allowed = 0, iconBase = 'views/WebDesign/img/';

function initMap() {
    latlon = {lat: 12.1442126, lng: -86.2717132};
   	map = new google.maps.Map($('#map').get(0), { center: {lat: 12.1442126, lng: -86.2717132}, zoom: 16,
          disableDefaultUI: true });
   	/*
    var noPoi = [
    {
        featureType: "poi",
        stylers: [
          { visibility: "off" }
        ]   
      }
    ];

    map.setOptions({styles: noPoi});*/

   	infoWindow = new google.maps.InfoWindow( {map: map} );
	
    if (navigator.geolocation) {
    	navigator.geolocation.getCurrentPosition(function(position) {
    	   	var pos = { lat: position.coords.latitude, lng: position.coords.longitude };
            infoWindow.setPosition(pos);
            infoWindow.setContent("Hola, tu estas aqui! "+
            	"<br /> Lat: " + pos.lat + 
            	"<br /> Lon:"  + pos.lng );
            map.setCenter(pos);
            latlon = {lat: parseFloat(pos.lat), lng: parseFloat(pos.lng)};
        }, function() {
        	handleLocationError(true, infoWindow, map.getCenter());
        });
   	} else {
        handleLocationError(false, infoWindow, map.getCenter());
    }

    google.maps.event.addListener(map, 'click', function(event) {
        if (allowed != 0){
            createMarker(event.latLng, 1);
        }
    });

    service = new google.maps.places.PlacesService(map);
    new AutocompleteDirectionsHandler(map);
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
   	infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ? 
    	'Error: Fallo el servicio de Geolocalización' : 'Error: Navegador incompatible.');
}

// Auto complete
function AutocompleteDirectionsHandler(map) {
    this.map = map;
    this.originPlaceId = null;
    this.destinationPlaceId = null;
    this.travelMode = 'WALKING';
    var originInput = document.getElementById('origin-input');
    var destinationInput = document.getElementById('destination-input');
    var modeSelector = document.getElementById('mode-selector');
    this.directionsService = new google.maps.DirectionsService;
    this.directionsDisplay = new google.maps.DirectionsRenderer;
    this.directionsDisplay.setMap(map);

    var originAutocomplete = new google.maps.places.Autocomplete( originInput, {placeIdOnly: true});
    var destinationAutocomplete = new google.maps.places.Autocomplete( destinationInput, {placeIdOnly: true});

    this.setupClickListener('changemode-walking', 'WALKING');
    this.setupClickListener('changemode-driving', 'DRIVING');

    this.setupPlaceChangedListener(originAutocomplete, 'ORIG');
    this.setupPlaceChangedListener(destinationAutocomplete, 'DEST');

    this.map.controls[google.maps.ControlPosition.TOP_LEFT].push(originInput);
    this.map.controls[google.maps.ControlPosition.TOP_LEFT].push(destinationInput);
    this.map.controls[google.maps.ControlPosition.TOP_LEFT].push(modeSelector);
}

AutocompleteDirectionsHandler.prototype.setupClickListener = function(id, mode) {
    var radioButton = document.getElementById(id), me = this;
    radioButton.addEventListener('click', function() { me.travelMode = mode; me.route(); });
};

AutocompleteDirectionsHandler.prototype.setupPlaceChangedListener = function(autocomplete, mode) {
    var me = this;
    autocomplete.bindTo('bounds', this.map);
    autocomplete.addListener('place_changed', function() {
        var place = autocomplete.getPlace();
        if (!place.place_id) {
        	window.alert("Please select an option from the dropdown list.");
            return;
        }
        if (mode === 'ORIG') {
        	me.originPlaceId = place.place_id;
        } else {
        	me.destinationPlaceId = place.place_id;
        }
        me.route();
    });
};

AutocompleteDirectionsHandler.prototype.route = function() {
    if (!this.destinationPlaceId) { return; }
    var me = this;
	this.directionsService.route({
	    origin: {'lat': latlon.lat,'lng': latlon.lng },
	    destination: {'placeId': this.destinationPlaceId},
	    travelMode: this.travelMode
	}, function(response, status) {
	    if (status === 'OK') {
			me.directionsDisplay.setDirections(response);
		} else {
			window.alert('Directions request failed due to ' + status);
		}
	});
};

function ChangeWindowsRegistro(){
    $.ajax({
        url: "views/WebDesign/login/registro.php",
        success: function(data){
            $(".acaelcodigo").hide(250); 
            $(".acaelcodigo").show(500).html(data);          
        }
    });

    // $(".finishedFormCod").html("<div class='field email'><div class='icon'></div><input class='input' id='email' type='email' placeholder='Nombre de usuario' autocomplete='off'/></div><div class='field password'><div class='icon'></div><input class='input' id='password' type='password' placeholder='Contraseña sss'/></div><button class='button' id='submit'>INICIAR SESIÓN<div class='side-top-bottom'></div><div class='side-left-right'></div>");
}

function ChangeWindowsLogin(){
    $.ajax({
        url: "views/WebDesign/login/file.php",
        success: function(data){
            $(".acaelcodigo").hide(250); 
            $(".acaelcodigo").show(500).html(data); 
        }
    });
    // $(".finishedFormCod").html("<div class='field email'><div class='icon'></div><input class='input' id='email' type='email' placeholder='Nombre de usuario' autocomplete='off'/></div><div class='field password'><div class='icon'></div><input class='input' id='password' type='password' placeholder='Contraseña sss'/></div><button class='button' id='submit'>INICIAR SESIÓN<div class='side-top-bottom'></div><div class='side-left-right'></div>");
}


function callback(results, status) {
    if (status === google.maps.places.PlacesServiceStatus.OK) {
        for (var i = 0; i < results.length; i++) {
            createMarker(results[i].geometry.location, 0);
            console.log(results[i]);
        }
    }
}

function createMarker(locations, i = 0) {
    if ( i == 1){
        allowed = 0;
        clearMarkers();
        $("#BusinessTextCoordLong, #lon").val(locations.lng);
        $("#BusinessTextCoordLat,  #lat").val(locations.lat);
        $("#Outer2").click();
        var marker = new google.maps.Marker({
            map: map,
            icon: iconBase+'m_new.png',
            position: locations
        });
    }
    else{
        var marker = new google.maps.Marker({
            map: map,
            position: locations
        });
    }

    markers.push(marker);
    google.maps.event.addListener(marker, 'click', function() {
        infoWindow.setContent(place.name);
        infoWindow.open(map, this);
    });
}

function clearMarkers() {
    while(markers.length){
            markers.pop().setMap(null);
        }
    markers = [];
}

function CloseSession(){
    window.location.href="views/WebDesign/login/php/logout.php";
}

function SendLogin(){

    $("#map").click();

    setTimeout(function(){
        $("#Outer1").click();
    }, 700);

}

function SaveRadioButton(value){
    $("#TypeBusiness").val($(value).val());
}

function NextLevel(){
    if ($("#TypeBusiness").val() != ""){
        $(".panelOne").hide(600);
        $(".panelTwo").show(600);
    }
}

function Back(){
    $(".panelTwo").hide(600);
    $(".panelOne").show(600);
}

function FuncMarker(){
    allowed = 1;
    $("#BusinessTextTitle").val($("#TextTitle").val());
    $("#BusinessTextDescription").val($("#TextAreaDescription").val());
    $("#BusinessTextRUC").val($("#TextRUC").val());
    
    $("#BusinessTextPhoneLocal").val($("#TextPhoneLocal").val());
    $("#BusinessTextPhoneSelf").val($("#TextPhoneSelf").val());

    $("#map").click();
}


//Notificacion
function notificar(text) {
    setTimeout( function() {
        $("#Notification").text(text).addClass('show');
    }, 1200 );
    $("#Notification").removeClass('show');
}


/* --- Register --- */
function get_business() {
    send ( {none:'none'}, 'controller=WebService&action=AddBusiness' );
}