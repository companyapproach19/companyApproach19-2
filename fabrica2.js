
/*
ESTO ESTÁ BIEN PARA SUBIR 

*/

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
	pedirStock("0");
        $("#popup1").show();
        return false;
    });
    $("#bt2").on('click', function() {
	pedirStock("8");
        $("#popup2").show();
        return false;
    });
    $("#bt3").on('click', function() {
	pedirStock("1");
        $("#popup3").show();
        return false;
    });
    $("#bt4").on('click', function() {
	pedirStock("4");
        $("#popup4").show();
        return false;
    });
});

// /dameStockActor recibe el id (como un nif) del actor, no su tipo. Una fábrica de la bdd
// es la que tiene id=8, por lo que de momento y al haber solo una fábrica, pedimos siempre
// el stock de esa

// Petición de stock de un actor al servidor

function pedirStock(actorString) {

    // Si se trata del stock de la fábrica (id 8), pide su stock con dameStockActor
    if (actorString == "8") {
	var request = $.ajax({
	    
	    // la URL para la petición del stock
	    url : '/dameStockActor',
	    
	    // la información a enviar (parámetros que se le pasan a la url)
	    // (también es posible utilizar una cadena de datos)
	    data : "id=" + actorString,
	    
	    // especifica si será una petición POST o GET
	    type : 'POST',
	    
	    // el tipo de información que se espera de respuesta
	    dataType : 'json',
	    
	});
    }
    // Si no, pide el stock de todos los actores del tipo especificado
    else {
	var request = $.ajax({
	    
	    url : '/dameStockActores',	    
	    data : "id=" + actorString,
	    type : 'POST',	    
	    dataType : 'json',
	    
	});	
    }

    // PETICIÓN CORRECTA: UTILIZA DATOS RECIBIDOS
    // la respuesta es pasada como argumento a la función
    request.done(function(data){
	var jsonObj = data;
	var i = 0;
	var arrayJson = [];
	switch(actorString) {
	case "0" :
	    var op = "Agricultor";
	    break;
	case "1" :
	    var op = "Cooperativa";
	    break;
	case "8" :
	    var op = "Fabrica";
	    break;
	case "4" :
	    var op = "Retailer";
	    break;
	default :
	    var op = "Unknow";
	}
	if (actorString == "8") {
	    arrayJson.push(jsonObj);
	}
	else {
	    while (jsonObj[op + i] != null) {
		//print(jsonObj[op+i]);
		arrayJson.push(jsonObj[op + i]);
		i++;
	    }
	}
	rellenaPopup(arrayJson, actorString);
	
    });
    // PETICIÓN FALLA: UTILIZA DATOS LOCALES
    request.fail(function(data) {

	// Escribe en el popup "Petición al servidor fallida"
	
	var arrayJson = [];
	
	switch(actorString) {
	case "0":
	    arrayJson = [JSON.parse(json_aux0.json_aux01),JSON.parse(json_aux0.json_aux02)];
            $("popup1").text("Petición al servidor fallida. Se utilizarán datos locales");
	    break;
	case "1":
	    arrayJson = [JSON.parse(json_aux1.json_aux11),JSON.parse(json_aux1.json_aux12)];
            $("popup3").text("Petición al servidor fallida. Se utilizarán datos locales");
	    break;
	case "8":
	    arrayJson = [JSON.parse(json_aux3.json_aux31),JSON.parse(json_aux3.json_aux32)];
            $("popup2").text("Petición al servidor fallida. Se utilizarán datos locales");
	    break;
	case "4":
	    arrayJson = [JSON.parse(json_aux4.json_aux41),JSON.parse(json_aux4.json_aux42)];
            $("popup4").text("Petición al servidor fallida. Se utilizarán datos locales");
	    break;
	default:
	    break;
	}
	console.log(arrayJson);
	
	// Llamamos al método para rellenar el popup con los datos del (los) JSON.
	rellenaPopup(arrayJson, actorString);
    });
}
// actor es un string
function rellenaPopup(arrayJson,actorString) {
    
    if (arrayJson == []) {
	// En este caso ni el servidor ha devuelto nada ni tenemos JSON local
	// Escribir en el popup "Respuesta del servidor errónea" y terminar
	$("popup" + actorString).append("<br><br>Respuesta del servidor o datos locales erróneos. No hay stock para mostrar");
	return;
    }
    if (actorString == '0' || actorString == '1' || actorString == '8' || actorString == '4') {
	var stock;
	for (i = 0; i < arrayJson.length; i++) {
	    stock = arrayJson[i];
	    parseJSON(stock,actorString);
	}
    }
    else {
	for (i = 1; i < 5 ; i++){
	    $("popup" + i).append("<br><br>Respuesta del servidor o datos locales erróneos. No hay stock para mostrar");
	}
    }
}

function parseJSON (stock,actorString) {

    // Recorremos los campos del JSON. Si alguna de las materias primas o lotes no es un campo del json, se le da el valor 0 a la variable.
    // Si alguna de las materias prima coincide con algún campo del JSON, se le da el valor que tenga en el JSON a la variable.
    var malta_palida,malta_tostada,malta_negra,malta_crystal,malta_chocolate,malta_caramelo,malta_pilsner,malta_munich,lupulo_perle,lupulo_tettnanger,lupulo_centennial,levadura_ale,levadura_lagger,lotes_pilsner,lotes_stout;
    var array = ['malta_palida','malta_tostada','malta_negra','malta_crystal','malta_chocolate','malta_caramelo','malta_pilsner','malta_munich','lupulo_perle','lupulo_tettnanger','lupulo_centennial','levadura_ale','levadura_lagger','lotes_pilsner','lotes_stout'];
    for (j = 0; j < array.length; j++) {
	var varName = array[j];
	var content = eval('stock.stock.'+varName);
	if (typeof content != 'undefined' && content != "") { eval(varName+"="+content.toString()+";"); } else { eval(varName+"=0;"); }
    }
    // Después de dar valor a todas las variables (materias primas+lotes), los imprimimos en el popup.
    switch (actorString) {

    case "0" :
	$("popup1").append("<br><br>DATOS DEL AGRICULTOR " + stock.nomUsuario + "<br>Email: " + stock.email + "<br>Stock: " + "<br>MALTA:" + "<br>1.Base pálida: "+ malta_palida.toString() +" kg<br>2.Cebada tostada:" +" "+ malta_tostada.toString() +" kg<br>3.Negra: "+ malta_negra.toString() +" kg<br>4.Crystal: "+ malta_crystal.toString() +" kg<br>5.Chocolate: "+ malta_chocolate.toString() +" kg<br>6.Caramelo: "+ malta_caramelo.toString() +" kg<br>7.Pilsner: "+ malta_pilsner.toString() +" kg<br>8.Munich: "+ malta_munich.toString() +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ lupulo_perle.toString() +" kg<br>2.Tettnanger: "+ lupulo_tettnanger.toString() +" kg<br>3.Centennial: "+ lupulo_centennial.toString() +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ levadura_ale.toString() +" kg<br>2.Lagger: "+ levadura_lagger.toString()+" kg");
	break;
	
    case "1" :
	$("popup3").append("<br><br>DATOS DE LA COOPERATIVA" + stock.nomUsuario + "<br>Email: " + stock.email + "<br>Stock: " + "<br>MALTA:" + "<br>1.Base pálida: "+ malta_palida.toString() +" kg<br>2.Cebada tostada:" +" "+ malta_tostada.toString() +" kg<br>3.Negra: "+ malta_negra.toString() +" kg<br>4.Crystal: "+ malta_crystal.toString() +" kg<br>5.Chocolate: "+ malta_chocolate.toString() +" kg<br>6.Caramelo: "+ malta_caramelo.toString() +" kg<br>7.Pilsner: "+ malta_pilsner.toString() +" kg<br>8.Munich: "+ malta_munich.toString() +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ lupulo_perle.toString() +" kg<br>2.Tettnanger: "+ lupulo_tettnanger.toString() +" kg<br>3.Centennial: "+ lupulo_centennial.toString() +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ levadura_ale.toString() +" kg<br>2.Lagger: "+ levadura_lagger.toString() +" kg");
	break;
	
    case "8" :
	$("popup2").append("<br><br>DATOS DE LA FÁBRICA " + stock.nomUsuario + "<br>Email: " + stock.email + "<br>Stock: " + "<br>MALTA:" + "<br>1.Base pálida: "+ malta_palida.toString() +" kg<br>2.Cebada tostada:" +" "+ malta_tostada.toString() +" kg<br>3.Negra: "+ malta_negra.toString() +" kg<br>4.Crystal: "+ malta_crystal.toString() +" kg<br>5.Chocolate: "+ malta_chocolate.toString() +" kg<br>6.Caramelo: "+ malta_caramelo.toString() +" kg<br>7.Pilsner: "+ malta_pilsner.toString() +" kg<br>8.Munich: "+ malta_munich.toString() +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ lupulo_perle.toString() +" kg<br>2.Tettnanger: "+ lupulo_tettnanger.toString() +" kg<br>3.Centennial: "+ lupulo_centennial.toString() +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ levadura_ale.toString() +" kg<br>2.Lagger: "+ levadura_lagger.toString() +" kg<br><br>LOTES:"+"<br>1. Cerveza Pilsner: "+ lotes_pilsner.toString() +" lotes<br>2. Cerveza Stout: " + lotes_stout.toString() + " lotes");
	break;
	
    case "4" :
	$("popup4").append("<br><br>DATOS DEL RETAILER " + stock.nomUsuario + "<br>Email: " + stock.email + "<br>Stock: " + "<br><br>LOTES:"+"<br>1. Cerveza Pilsner: "+ lotes_pilsner.toString() +" lotes<br>2. Cerveza Stout: " + lotes_stout.toString() + " lotes");
	break;
    default :  
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

var json_aux0,json_aux1,json_aux3,json_aux4,json_aux01,json_aux02,json_aux11,json_aux12,json_aux31,json_aux32,json_aux41,json_aux42; 
json_aux0={};json_aux1={};json_aux3={};json_aux4={};

json_aux01 = '{"nomUsuario":"Agricultor1","tipoActor":"Agricultor","email":"agricultor1@email.com","stock":{ "malta_palida":2343,"malta_tostada":234252,"malta_negra":74564,"malta_crystal":9340234,"malta_chocolate":324,"malta_caramelo":90428042}}';
json_aux02 = '{"nomUsuario":"Agricultor2","tipoActor":"Agricultor","email":"agricultor2@email.com","stock":{ "malta_palida":2343,"malta_tostada":234252,"malta_negra":74564,"malta_crystal":9340234,"malta_chocolate":324,"malta_caramelo":90428042,"malta_pilsner":23424,"malta_munich":54353,"lupulo_perle":4242,"lupulo_tettnanger":4242342,"lupulo_centennial":34242,"levadura_ale":99999999999999999999999999,"levadura_lagger": 84092,"lotes_pilsner":"","lotes_stout":""}}';

json_aux11 = '{"nomUsuario":"Cooperativa1","tipoActor":"Cooperativa","email":"cooperativa1@email.com","stock":{ "malta_palida": 2343}}';
json_aux12 = '{"nomUsuario":"Cooperativa2","tipoActor":"Cooperativa","email":"cooperativa2@email.com","stock":{ "malta_palida": 2343,"malta_tostada": 234252,"malta_negra": 74564,"malta_crystal": 9340234,"malta_chocolate": 324,"malta_caramelo": 428042,"malta_pilsner": 23424,"malta_munich": 54353,"lupulo_perle": 4242,"lupulo_tettnanger": 242342, "lupulo_centennial": 34242,"levadura_ale": 243,"levadura_lagger": 84092,"lotes_pilsner":"","lotes_stout":""}}';

json_aux31 = '{"nomUsuario":"Fábrica1","tipoActor":"Fabrica","email":"fabrica1@email.com","stock":{ "malta_palida": 2343,"malta_tostada": 34252,"malta_negra": 74564,"malta_crystal": 9340234,"malta_chocolate": 324,"malta_caramelo": 428042,"malta_pilsner": 23424,"malta_munich": 54353,"lupulo_perle": 242,"lupulo_tettnanger": 4242342,"lupulo_centennial": 34242,"levadura_ale": 34243,"levadura_lagger": 84092,"lotes_pilsner":32424,"lotes_stout":23424}}';
json_aux32 = '{"nomUsuario":"Fábrica2","tipoActor":"Fabrica","email":"fabrica2@email.com","stock":{}}';

json_aux41 = '{"nomUsuario":"Retailer1","tipoActor":"Retailer","email":"retailer1@email.com", "stock":{ "malta_palida": "","malta_tostada": "","malta_negra": "","malta_crystal": "","malta_chocolate": "","malta_caramelo": "","malta_pilsner": "","malta_munich": "","lupulo_perle": "","lupulo_tettnanger": "","lupulo_centennial": "","levadura_ale": "","levadura_lagger": "","lotes_pilsner":596,"lotes_stout":756}}';
json_aux42 = '{"nomUsuario":"Retailer2","tipoActor":"Retailer","email":"retailer2@email.com", "stock":{ "malta_palida": "","malta_tostada": "","malta_negra": "","malta_crystal": "","malta_chocolate": "","malta_caramelo": "","malta_pilsner": "","malta_munich": ""}}';

json_aux0.json_aux01 = json_aux01;
json_aux0.json_aux02 = json_aux02;
json_aux1.json_aux11 = json_aux11;
json_aux1.json_aux12 = json_aux12;
json_aux3.json_aux31 = json_aux31;
json_aux3.json_aux32 = json_aux32;
json_aux4.json_aux41 = json_aux41;
json_aux4.json_aux42 = json_aux42;