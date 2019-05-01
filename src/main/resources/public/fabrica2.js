// Script de control de botones y peticiones http (con Ajax) al servidor (stock)
// de la vista fabrica2.html

// Control de botones de los popups.
// Al pulsarse, se pedirStock hace la petición al servidor y rellena el popup
// Cuando está todo, se muestra el popup

$(document).ready(function(){
    
    $("#hide").on('click', function() {
        $("#popup1").hide();
        return false;
    });
    $("#hide2").on('click', function() {
        $("#popup2").hide();
        return false;
    });
    $("#hide3").on('click', function() {
        $("#popup3").hide();
        return false;
    });
    $("#hide4").on('click', function() {
        $("#popup4").hide();
        return false;
    });
    $("#bt1").on('click', function() {
  pedirStock(0);
        $("#popup1").show();
        return false;
    });
    $("#bt2").on('click', function() {
  pedirStock(3);
        $("#popup2").show();
        return false;
    });
    $("#bt3").on('click', function() {
  pedirStock(1);
        $("#popup3").show();
        return false;
    });
    $("#bt4").on('click', function() {
  pedirStock(4);
        $("#popup4").show();
        return false;
    });
});

// Petición de stock al servidor

function pedirStock(actor) {

    var request = $.ajax({
  
  // la URL para la petición del stock
  url : '/dameStockActor',
  
  // la información a enviar (parámetros que se le pasan a la url)
// (también es posible utilizar una cadena de datos)
	data : "id=" + actor,
  
  // especifica si será una petición POST o GET
  type : 'POST',
  
  // el tipo de información que se espera de respuesta
  dataType : 'json',
  
    });
    // código a ejecutar si la petición es satisfactoria;
    // la respuesta es pasada como argumento a la función
    request.done(function(data){
  
  rellenaPopup(JSON.parse(data), actor);
  
    });
    request.fail(function(data) {

  // Escribe en el popup "Petición al servidor fallida"
  // De momento, si la petición falla lee el JSON auxiliar que tenemos
  
  switch(actor) {
  case 0:
          $("popup1").text("Petición al servidor fallida. Se utilizarán datos locales");
      rellenaPopup(JSON.parse(json_aux01), actor);
      break;
  case 1:
          $("popup3").text("Petición al servidor fallida. Se utilizarán datos locales");
      rellenaPopup(JSON.parse(json_aux11), actor);
      break;
  case 3:
          $("popup2").text("Petición al servidor fallida. Se utilizarán datos locales");
      rellenaPopup(JSON.parse(json_aux31), actor);
      break;
  case 4:
          $("popup4").text("Petición al servidor fallida. Se utilizarán datos locales");
      rellenaPopup(JSON.parse(json_aux41), actor);
      break;
  default:
      break;
  }

    });
}
function rellenaPopup(stock,actor) {
    
    if (stock == null) {
  // En este caso ni el servidor ha devuelto nada ni tenemos JSON local
  // Escribir en el popup "Respuesta del servidor errónea" y terminar
	$("popup" + actor).append("<br><br>Respuesta del servidor o datos locales erróneos. No hay stock para mostrar");
	return;
    }
    
    // Leer info del JSON y ponerla en el popup correspondiente
    switch(stock.tipoActor) {

	// Agricultor
    case 0:
  $("popup1").append("<br><br>Datos del Agricultor<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Agricultor: " + "<br>MALTA:" + "<br>1.Base pálida: "+ stock.stock.malta_palida.toString() +" kg<br>2.Cebada tostada:" +" "+ stock.stock.malta_tostada.toString() +" kg<br>3.Negra: "+ stock.stock.malta_negra.toString() +" kg<br>4.Crystal: "+ stock.stock.malta_crystal.toString() +" kg<br>5.Chocolate: "+ stock.stock.malta_chocolate.toString() +" kg<br>6.Caramelo: "+ stock.stock.malta_caramelo.toString() +" kg<br>7.Pilsner: "+ stock.stock.malta_pilsner.toString() +" kg<br>8.Munich: "+ stock.stock.malta_munich.toString() +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ stock.stock.lupulo_perle.toString() +" kg<br>2.Tettnager: "+ stock.stock.lupulo_tettnager.toString() +" kg<br>3.Centennial: "+ stock.stock.lupulo_centennial.toString() +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ stock.stock.levadura_ale.toString() +" kg<br>2.Lagger: "+ stock.stock.levadura_lagger.toString() +" kg");
  break;
	// Cooperativa
    case 1:
  $("popup3").append("<br><br>Datos de la Cooperativa<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Agricultor: " + "<br>MALTA:" + "<br>1.Base pálida: "+ stock.stock.malta_palida.toString() +" kg<br>2.Cebada tostada:" +" "+ stock.stock.malta_tostada.toString() +" kg<br>3.Negra: "+ stock.stock.malta_negra.toString() +" kg<br>4.Crystal: "+ stock.stock.malta_crystal.toString() +" kg<br>5.Chocolate: "+ stock.stock.malta_chocolate.toString() +" kg<br>6.Caramelo: "+ stock.stock.malta_caramelo.toString() +" kg<br>7.Pilsner: "+ stock.stock.malta_pilsner.toString() +" kg<br>8.Munich: "+ stock.stock.malta_munich.toString() +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ stock.stock.lupulo_perle.toString() +" kg<br>2.Tettnager: "+ stock.stock.lupulo_tettnager.toString() +" kg<br>3.Centennial: "+ stock.stock.lupulo_centennial.toString() +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ stock.stock.levadura_ale.toString() +" kg<br>2.Lagger: "+ stock.stock.levadura_lagger.toString() +" kg");
  break;
	// Fábrica
    case 3:
	$("popup2").append("<br><br>Datos de la Fábrica<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Agricultor: " + "<br>MALTA:" + "<br>1.Base pálida: "+ stock.stock.malta_palida.toString() +" kg<br>2.Cebada tostada:" +" "+ stock.stock.malta_tostada.toString() +" kg<br>3.Negra: "+ stock.stock.malta_negra.toString() +" kg<br>4.Crystal: "+ stock.stock.malta_crystal.toString() +" kg<br>5.Chocolate: "+ stock.stock.malta_chocolate.toString() +" kg<br>6.Caramelo: "+ stock.stock.malta_caramelo.toString() +" kg<br>7.Pilsner: "+ stock.stock.malta_pilsner.toString() +" kg<br>8.Munich: "+ stock.stock.malta_munich.toString() +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ stock.stock.lupulo_perle.toString() +" kg<br>2.Tettnager: "+ stock.stock.lupulo_tettnager.toString() +" kg<br>3.Centennial: "+ stock.stock.lupulo_centennial.toString() +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ stock.stock.levadura_ale.toString() +" kg<br>2.Lagger: "+ stock.stock.levadura_lagger.toString() +" kg<br><br>LOTES:"+"<br>1. Cerveza Pilsner: "+ stock.stock.lotes_pilsner.toString() +" lotes<br>2. Cerveza Stout: " + stock.stock.lotes_stout.toString() + " lotes");
  break;
	// Retailer
    case 4:
  $("popup4").append("<br><br>Datos del Retailer<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Retailer: "+"<br>LOTES:"+"<br>1. Cerveza Pilsner: "+ stock.stock.lotes_pilsner.toString() +" lotes<br>2. Cerveza Stout: " + stock.stock.lotes_stout.toString() + " lotes");
  break;

    default :
  $("popup" + actor).append("<br><br>Respuesta del servidor o datos locales erróneos. No hay stock para mostrar");
  break;

    }   
}


/*******************ANIMACIONNNNNNNNNNNNNNNNNN*/
document.addEventListener("DOMContentLoaded", function(event) { 
    initialize();
});

function initialize(){

    var select = function(s) { return document.querySelector(s); };
    var selectAll = function(s) { return document.querySelectorAll(s); }
    TweenMax.set('#ad', {opacity:1});

    var banner = new TimelineMax({repeat:0, delay:1, yoyo:false, paused:false});
    /*frame1*/
    banner
     
  .from('#bt1', 1, {alpha:0, ease:Back.easeOut})
  .from('#linea1', 1, {scale:0})
  .from('#bt3', 1, {alpha:0, ease:Back.easeOut})
      .to(linea2bis, 1, {scale:0})
  .from('#bt2', 1, {alpha:0, ease:Back.easeOut})
  .from('#linea3', 1, {scale:0})
  .from('#bt4', 1, {alpha:0, ease:Back.easeOut})
    
}


/* JSON local por si el servidor falla o no hay datos */

var json_aux01 = '{"nomUsuario":"Agricultor1","tipoActor":0,"email":"agricultor1@email.com","stock":{ "malta_palida":2343,"malta_tostada":234252,"malta_negra":74564,"malta_crystal":9340234,"malta_chocolate":324,"malta_caramelo":90428042,"malta_pilsner":23424,"malta_munich":54353,"lupulo_perle":4242,"lupulo_tettnager":4242342,"lupulo_centennial":34242,"levadura_ale":99999999999999999999999999,"levadura_lagger": 84092,"lotes_pilsner":"","lotes_stout":""}}';
var json_aux02 = '{"nomUsuario":"Agricultor2","tipoActor":"Agricultor","email":"agricultor1@email.com","stock":{ "malta_palida":2343,"malta_tostada":234252,"malta_negra":74564,"malta_crystal":9340234,"malta_chocolate":324,"malta_caramelo":90428042,"malta_pilsner":23424,"malta_munich":54353,"lupulo_perle":4242,"lupulo_tettnager":4242342,"lupulo_centennial":34242,"levadura_ale":99999999999999999999999999,"levadura_lagger": 84092,"lotes_pilsner":"","lotes_stout":""}}';

var json_aux11 = '{"nomUsuario":"Cooperativa1","tipoActor":1,"email":"cooperativa1@email.com","stock":{ "malta_palida": 2343,"malta_tostada": 234252,"malta_negra": 74564,"malta_crystal": 9340234,"malta_chocolate": 324,"malta_caramelo": 428042,"malta_pilsner": 23424,"malta_munich": 54353,"lupulo_perle": 4242,"lupulo_tettnager": 242342, "lupulo_centennial": 34242,"levadura_ale": 243,"levadura_lagger": 84092,"lotes_pilsner":"","lotes_stout":""}}';
var json_aux12 = '{"nomUsuario":"Cooperativa1","tipoActor":"Cooperativa","email":"cooperativa1@email.com","stock":{ "malta_palida": 2343,"malta_tostada": 234252,"malta_negra": 74564,"malta_crystal": 9340234,"malta_chocolate": 324,"malta_caramelo": 428042,"malta_pilsner": 23424,"malta_munich": 54353,"lupulo_perle": 4242,"lupulo_tettnager": 242342, "lupulo_centennial": 34242,"levadura_ale": 243,"levadura_lagger": 84092,"lotes_pilsner":"","lotes_stout":""}}';

var json_aux31 = '{"nomUsuario":"Fábrica1","tipoActor":3,"email":"fabrica1@email.com","stock":{ "malta_palida": 2343,"malta_tostada": 34252,"malta_negra": 74564,"malta_crystal": 9340234,"malta_chocolate": 324,"malta_caramelo": 428042,"malta_pilsner": 23424,"malta_munich": 54353,"lupulo_perle": 242,"lupulo_tettnager": 4242342,"lupulo_centennial": 34242,"levadura_ale": 34243,"levadura_lagger": 84092,"lotes_pilsner":32424,"lotes_stout":23424}}';
var json_aux32 = '{"nomUsuario":"Fábrica1","tipoActor":"Fabrica","email":"fabrica1@email.com","stock":{ "malta_palida": 2343,"malta_tostada": 34252,"malta_negra": 74564,"malta_crystal": 9340234,"malta_chocolate": 324,"malta_caramelo": 428042,"malta_pilsner": 23424,"malta_munich": 54353,"lupulo_perle": 242,"lupulo_tettnager": 4242342,"lupulo_centennial": 34242,"levadura_ale": 34243,"levadura_lagger": 84092,"lotes_pilsner":32424,"lotes_stout":23424}}';

var json_aux41 = '{"nomUsuario":"Retailer1","tipoActor":4,"email":"retailer1@email.com", "stock":{ "malta_palida": "","malta_tostada": "","malta_negra": "","malta_crystal": "","malta_chocolate": "","malta_caramelo": "","malta_pilsner": "","malta_munich": "","lupulo_perle": "","lupulo_tettnager": "","lupulo_centennial": "","levadura_ale": "","levadura_lagger": "","lotes_pilsner":596,"lotes_stout":756}}';
var json_aux42 = '{"nomUsuario":"Retailer1","tipoActor":"Retailer","email":"retailer1@email.com", "stock":{ "malta_palida": "","malta_tostada": "","malta_negra": "","malta_crystal": "","malta_chocolate": "","malta_caramelo": "","malta_pilsner": "","malta_munich": "","lupulo_perle": "","lupulo_tettnager": "","lupulo_centennial": "","levadura_ale": "","levadura_lagger": "","lotes_pilsner":596,"lotes_stout":756}}';
