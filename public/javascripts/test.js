  function initialize() {
//    var x = ${testDate};
      var x = "BOOOO";
      window.alert(x);
      map_canvas = "FOOOO";
  };

var geocoder;
var map;
function initMap() {
    window.alert("initMap");
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng(-33.844481,151.144744);
    var myOptions = {
	zoom: 10,
	center: latlng,
	// ROADMAP / SATELLITE / HYBRID / TERRAIN
	mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    
    var marker = new google.maps.Marker({
	map: map, 
	position: latlng,
	title: 'BestCarRental.com.au'
    });
    
}

  function displayMap() {
    var latlng = new google.maps.LatLng(-34.397, 150.644);
    var myOptions = {
      zoom: 8,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
  }

window.onload = initMap();
