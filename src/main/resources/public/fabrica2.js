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
    $("#bt1").on('click', function() {
  pedirStock("1");
        $("#popup1").show();
        return false;
    });
    $("#bt2").on('click', function() {
  pedirStock("2");
        $("#popup2").show();
        return false;
    });
    $("#bt3").on('click', function() {
  pedirStock("3");
        $("#popup3").show();
        return false;
    });
    $("#bt4").on('click', function() {
  pedirStock("4");
        $("#popup4").show();
        return false;
    });
});

// Petición de stock al servidor

function pedirStock(actor) {

    var request = $.ajax({
  
  // la URL para la petición del stock
  //TODO: Ponerla bien
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
  $("popup" + actor).text("Petición al servidor fallida. Se utilizarán datos locales");
  
  switch(actor) {
  case "1": 
      rellenaPopup(JSON.parse(json_aux1), actor);
      break;
  case "3": 
      rellenaPopup(JSON.parse(json_aux2), actor);
      break;
  case "2": 
      rellenaPopup(JSON.parse(json_aux3), actor);
      break;
  case "4": 
      rellenaPopup(JSON.parse(json_aux4), actor);
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
    // TODO: cuando pongan las etiquetas en el popup, enlazar con los datos del JSON
    switch(stock.tipoActor) {

    case "Agricultor":
  $("popup" + actor).append("<br><br>Datos del Agricultor<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Agricultor: " + "<br>MALTA:" + "<br>1.Base pálida:" +"<br>2.Cebada tostada:" +"<br>3.Negra:" +"<br>4.Crystal:"+ "<br>5.Chocolate:"+ "<br>6.Caramelo:"+ "<br>7.Pilsner:"+ "<br>8.Munich:" + "<br><br>LÚPULO:" + "<br>1.Perle:" +"<br>2.Tettnager:"+"<br>3.Centennial:"+"<br><br>LEVADURA:"+"<br>1.Ale:","<br>2.Lagger:");
  break;

  case "Cooperativa":
  $("popup" + actor).append("<br><br>Datos de la Cooperativa<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock de la Cooperativa: " + "<br>MALTA:" + "<br>1.Base pálida:" +"<br>2.Cebada tostada:" +"<br>3.Negra:" +"<br>4.Crystal:"+ "<br>5.Chocolate:"+ "<br>6.Caramelo:"+ "<br>7.Pilsner:"+ "<br>8.Munich:" + "<br><br>LÚPULO:" + "<br>1.Perle:" +"<br>2.Tettnager:"+"<br>3.Centennial:"+"<br><br>LEVADURA:"+"<br>1.Ale:","<br>2.Lagger:");
  break;


    case "Fabrica":
  $("popup" + actor).append("<br><br>Datos de la Fábrica<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock de la Fábrica: " + "<br>MALTA:" + "<br>1.Base pálida:" +"<br>2.Cebada tostada:" +"<br>3.Negra:" +"<br>4.Crystal:"+ "<br>5.Chocolate:"+ "<br>6.Caramelo:"+ "<br>7.Pilsner:"+ "<br>8.Munich:" + "<br><br>LÚPULO:" + "<br>1.Perle:" +"<br>2.Tettnager:"+"<br>3.Centennial:"+"<br><br>LEVADURA:"+"<br>1.Ale:","<br>2.Lagger:");
  break;

    case "Retailer":
  $("popup" + actor).append("<br><br>Datos del Retailer<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Retailer: MUCHO STOCK, TIENE MUCHOS ELEMENTOS DE ESTOC (organizar los tipos de materias y lotes en columnas (malta, levadura, lotes, etc, y ya los asociaré a la info del JSON)");
  break;

    default :
  break;

    }   
}

var json_aux1 = '{"nomUsuario":"Agricultor1","tipoActor":"Agricultor","email":"agricultor1@email.com","stock":{ "malta_palida":"2000","malta_palida":"2343","malta_tostada":"234252","malta_negra":"74564","malta_crystal":"09340234","malta_chocolate":"324","malta_caramelo":"90428042","malta_pilsner":"23424","malta_munich":"54353","lupulo_perle":"4242","lupulo_tettnager":"4242342","lupulo_centennial":"34242","levadura_ale": "34243","levadura_lagger": "84092","lotes_pilsner":"","lotes_stout":""}}';

var json_aux2 = '{"nomUsuario":"Cooperativa1","tipoActor":"Cooperativa","email":"cooperativa1@email.com","stock":{ "malta_palida":"2000", "malta_palida": "2343","malta_tostada": "234252","malta_negra": "74564","malta_crystal": "09340234","malta_chocolate": "324","malta_caramelo": "90428042","malta_pilsner": "23424","malta_munich": "54353","lupulo_perle": "4242","lupulo_tettnager": "4242342", "lupulo_centennial": "34242","levadura_ale": "34243","levadura_lagger": "84092","lotes_pilsner":"","lotes_stout":""}}';

var json_aux3 = '{"nomUsuario":"Fábrica1","tipoActor":"Fabrica","email":"fabrica1@email.com","stock":{ "malta_palida":"2000","malta_palida": "2343","malta_tostada": "234252","malta_negra": "74564","malta_crystal": "09340234","malta_chocolate": "324","malta_caramelo": "90428042","malta_pilsner": "23424","malta_munich": "54353","lupulo_perle": "4242","lupulo_tettnager": "4242342","lupulo_centennial": "34242","levadura_ale": "34243","levadura_lagger": "84092","lotes_pilsner":"32424","lotes_stout":"23424"}}';

var json_aux4 = '{"nomUsuario":"Retailer1","tipoActor":"Retailer","email":"retailer1@email.com", "stock":{ "malta_palida":"","malta_palida": "","malta_tostada": "","malta_negra": "","malta_crystal": "","malta_chocolate": "","malta_caramelo": "","malta_pilsner": "","malta_munich": "","lupulo_perle": "","lupulo_tettnager": "","lupulo_centennial": "","levadura_ale": "","levadura_lagger": "","lotes_pilsner":"94208","lotes_stout":"23424"}}';
