// ACTORES:  0 = agricultor, 1 =cooperativa, 2 = transportista, 3 = fábrica, 4 = retailer
// ESTADOS:  -1 = Rechazado, 0 = PendienteAceptar, 1 = Preparando, 2 = ListoEntregar, 3 = Transportandose, 4 = Entregado(Aceptado?)

function pedirIds(actor, estado){
	
	
	
	  var url;
 
	  switch (estado) {
		  case 0 :
		  url = "/ordenesPendientesPorAceptar";
		  alert("obteniendo pedidos recibidos (pendientes por aceptar)");
		  console.log("pido /ordenesPendientesPorAceptar?id="+actor);
		  break;
		  case 1 :
		  url = "/ordenesEnProceso";
		  alert("obteniendo pedidos por resolver");
		  console.log("pido /ordenesEnProceso?id="+actor);
		  break;
		  case 2 :
		  url = "/ordenesQueHeEnviado";
		  alert("obteniendo pedidos aceptados");
		  console.log("pido /ordenesQueHeEnviado?id="+actor);
		  break;
	    }
	  
      var request = $.ajax({
	  
      // la URL para la petición
      url : url,
 
      // la información a enviar (ID agricultor=0)
      data :"id="+actor ,
 
      // especifica si será una petición POST o GET
      type : 'GET',
 
      // el tipo de información que se espera de respuesta
      dataType : 'json',
 
      });
 
     request.done(function(data){
	 
        //HA FUNCIONADO AJAX
		
		//se han obtenido datos de la peticion ajax
		//parseo lo obtenido para obtener array de ids de las ORDENES
		//reescribo el array local idsOrdenes
		
		idsOrdenes = JSON.parse(data);
		//idsOrdenes = idsOrdenes.listaIDs;
		
		//paso por parametro a imprimir
		imprimirjson(idsOrdenes);
    
     });
 
     request.fail(function(data) {
     
		alert("Error en el servidor obteniendo ids");
		
		//paso por parametro array idsOrdenes local 
		imprimirjson(idsOrdenes);
		
		
     });
 
 }
 
 
 
 
 
 //Funcion que imprime cada id ORDEN
 function imprimirjson(ids){

		  if (ids==null){ 
			alert("No hay pedidos");
		  } else {
			var label = 'label';
            for (var i = 1; i< ids.length + 1; i++) {
                var x = i.toString(10);
                document.getElementById(label.concat(x)).innerHTML = ids[i-1];
			}
		  }
	}






function creaOrden(actor){

      var request = $.ajax({
      // la URL para la petición
      url : '/crearOrden',
 
      // la información a enviar
      // (también es posible utilizar una cadena de datos)
      data : paraJson(actor),
 
      // especifica si será una petición POST o GET
      type : 'POST',
 
      // el tipo de información que se espera de respuesta
      dataType : 'json',
 
      });
 
     request.done(function(data){
      
      //TODO Este metodo redirige a la URL
      windows.append('/cooperativaInicio.html');
    
     });
 
     request.fail(function(data) {
     
     alert("Error en el servidor creando orden");
	 
     
     });
 
 }
 
 function paraJson(actor) {

	
	// switch(actor){} dependiendo tipo actor se rellenan unos campos u otros
	
	
    var object = { 
		id: -1,
		mensaje : "",
		actorOrigen: {
		  id: 6,
		  nombreUsuario: "Productor",
		  tipoActor:  2
		},
		"actorDestino": {
			id: document.getElementById("identificador").value,
		  nombreUsuario: "Agricultor",
		  tipoActor: 1 
		},
		productos: {
		cant_malta_palida: document.getElementById("malta_palida").value,
		cant_malta_munich: document.getElementById("malta_munich").value,
		cant_malta_negra: document.getElementById("malta_negra").value,
		cant_malta_crystal: document.getElementById("malta_crystal").value,
		cant_malta_chocolate: document.getElementById("malta_chocolate").value,
		cant_malta_caramelo: document.getElementById("malta_caramelo").value,
		cant_cebada: document.getElementById("cebada").value, 
		cant_cebada_tostada: document.getElementById("cebada_tostada").value,
		cant_lupulo_centenial:document.getElementById("lupulo_centinental").value,
		cant_cajas_stout:0,
		cant_cajas_bisner:0
		},
  
    };
		
	var jsonText = JSON.stringify(object);
    console.log(jsonText);
	return jsonText;

  }





function mandarids(){

  //creare un array de longitud tantos como Ordenes haya (pedidos)
  //donde 1 en i pos significa que he marcado la orden i, -1 si no marcado
  var str = 'producto';
  var array = Array.from(idsOrdenes); // copio idsOrdenes en nueva variable
  var str2 = '';
  var aux;
  //var label = 'label';
  for (var i = 1; i< idsOrdenes.length+1; i++) {
  
		var x = i.toString(10);
		str2 = str.concat(x);
		aux = document.getElementById(str2);
		if (aux.checked){
		
		console.log("intento mandar  a /aceptarOrden?id="+idsOrdenes[i-1]);
		  
		  array[i-1] = 1;
		  
		  
			var request = $.ajax({
			
			
			  // la URL para la petición
			  url : '/aceptarOrden',
		 
			  // la información a enviar
			  data :"id="+idsOrdenes[i-1] ,
		 
			  // especifica si será una petición POST o GET
			  type : 'POST',
		 
			  // el tipo de información que se espera de respuesta
			  dataType : 'json',
		 

			});
		 
			request.done(function(data){
			  
				 
				 
			});
		 
			request.fail(function(data) {
			 
			 
			 alert("ERROR al aceptar "+idsOrdenes[i-1]+" pedido");
		   
			});
	  
	    } else {
			array[i-1] = -1;
		}
    
    }
	
	
	console.log("ARRAY ES  "+array);
	//console.log("EL ORIYINAL ES  "+idsOrdenes);  //(no lo machaca)
	
	
}

//--------------------------------TRAMPAS LOCALES------------------------------------------

var idsOrdenes = [6,7,5,3,21,43,76,4,6534];
//var idsOrdenes = null;

var pedido = {
  "id": 10,
  "actorOrigen": {
    "id": 42,
    "nombreUsuario": "Retailer",
    "passwordPlana": "password",
    "email": "ret@gmail.es",
    "tipoActor": 4
  },
  "actorDestino": {
    "id": 43,
    "nombreUsuario": "Fabrica",
    "passwordPlana": "password",
    "email": "fab@gmail.es",
    "tipoActor": 3
  },
  "necesitaTransportista": true,
  "productosPedidos": {
    "cant_malta_palida": 0,
    "cant_malta_munich": 0,
    "cant_malta_negra": 0,
    "cant_malta_crystal": 0,
    "cant_malta_chocolate": 0,
    "cant_malta_caramelo": 0,
    "cant_cebada": 0,
    "cant_cebada_tostada": 0,
    "cant_lupulo_centenial": 0,
    "cant_lotes_stout": 4,
    "cant_lotes_bisner": 0
  },
  "productosAEntregar": [
    80,81,82,83
  ],
  "estado": 4,
  "firmaRecogida": "SEFIQSBTTklDSEU=",
  "firmaEntrega": "SG9sYSBxdWUgdGFsIHNveSBjb2xvc2Fs",
  "transportista": {
    "id": "7",
    "nombreUsuario": "Transportista",
    "passwordPlana": "password",
    "email": "trans@gmail.com",
    "tipoActor": 2
  },
  "idRegistro": 300,
  "idPedido": 1,
  "fecha": "may 26, 3919"
}







////////////////////////////PARA MOSTRAR STOCK//////////////////////////
//VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV//
///// popupi, bti, json_auxi, sin sentido alguno.


/// VVVVVVV esto solo se llamará en el mirar stock.
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
  pedirStock("1");
        $("#popup1").show();
        return false;
    });
    $("#bt2").on('click', function() {
  pedirStock("3");
        $("#popup2").show();
        return false;
    });
    $("#bt3").on('click', function() {
  pedirStock("2");
        $("#popup3").show();
        return false;
    });
    $("#bt4").on('click', function() {
  pedirStock("4");
        $("#popup4").show();
        return false;
    });
});

function pedirStock(actor) {
	
	alert("HOLA PEDIR STOCK");
	
    var request = $.ajax({
  
  // la URL para la petición del stock
  url : '/dameStockActor',
  
  // la información a enviar (parámetros que se le pasan a la url)
  // (también es posible utilizar una cadena de datos)
	data : "id=" + actor,
  
  // especifica si será una petición POST o GET
  type : 'GET',
  
  // el tipo de información que se espera de respuesta
  dataType : 'json',
  
    });
    // código a ejecutar si la petición es satisfactoria;
    // la respuesta es pasada como argumento a la función
    request.done(function(data){
  
  rellenaPopup(JSON.parse(data), actor);
  
    });
    request.fail(function(data) {
		alert("fallo el ajax Stock");
	  // Escribe en el popup "Petición al servidor fallida"
	  // De momento, si la petición falla lee el JSON auxiliar que tenemos
  
	  switch(actor) {
	  case "1":
	  alert("HOLA agricultor");
			  $("popup1").text("Petición al servidor fallida. Se utilizarán datos locales");
		  rellenaPopup(JSON.parse(json_aux1), actor);
		  break;
	  case "2":
	  alert("HOLA Cooperativa");
			  $("popup3").text("Petición al servidor fallida. Se utilizarán datos locales");
		  rellenaPopup(JSON.parse(json_aux2), actor);
		  break;
	  case "3":
	  alert("HOLA Fabrica");
			  $("popup2").text("Petición al servidor fallida. Se utilizarán datos locales");
		  rellenaPopup(JSON.parse(json_aux3), actor);
		  break;
	  case "4":
	  alert("HOLA Tienda");
			  $("popup4").text("Petición al servidor fallida. Se utilizarán datos locales");
		  rellenaPopup(JSON.parse(json_aux4), actor);
		  break;
	  default:
		  break;
	  }
	});
}




//////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
////////////////////////PARA VER CADA PEDIDO VVVVVVVVVVVVVVVVVVVVVVV

//pos es la posicion del idsOrdenes (para ver cada pedido a mostrar)
// id: 1 = Agricultor, 2 = Fabrica, 3 = Cooperativa, 4  Tienda
function intermedio(pos, id){
	
	alert("intermedio("+pos+","+id+")");
	if (id == "1") {  pedirPedido(pos, id); $("#popup1").show(); return;}
	if (id == "2") {  pedirPedido(pos, id); $("#popup3").show(); return;}
	if (id == "3") {  pedirPedido(pos, id); $("#popup2").show(); return;}
	if (id == "4") {  pedirPedido(pos, id); $("#popup4").show(); return;}
	alert("ERROR"); return;
	
    
}




function pedirPedido(pos, actor) {
	alert("pedirPedido("+pos+","+actor+")");
	alert("voy a mostrar pedido "+idsOrdenes[pos]);
		 
	var idOrden = idsOrdenes[pos];
		
	console.log("pido pedido a /obtenerOrden?id="+idOrden);
		 
		var request = $.ajax({
		  
			// la URL para la petición
			url : '/obtenerOrden',
			 
			// la información a enviar (ID agricultor=0)
			data :"id="+idOrden ,
			 
			// especifica si será una petición POST o GET
			type : 'GET',
			 
			// el tipo de información que se espera de respuesta
			dataType : 'json',
			 
			});
	 
		 request.done(function(data){
		 
			//HA FUNCIONADO AJAX
			
			//se han obtenido json del pedido
			pedido = JSON.parse(data);
			
			//paso por parametro a imprimir
			rellenaPopup(pedido, parseInt(actor));
		
		 });
		 
		request.fail(function(data) {
			alert("fallo el ajax Pedido");
		  // Escribe en el popup "Petición al servidor fallida"
		  // De momento, si la petición falla lee el JSON auxiliar que tenemos
	  
		  switch(actor) {
		  case 1:
		  alert("HOLA Agricultor");
				  $("popup1").text("Petición al servidor fallida. Se utilizarán datos locales");
			  rellenaPopup(JSON.parse(json_aux1), parseInt(actor));  // parse estupido,, pero rellenaPopup mas aun.
			  break;
		  case 2:
		  alert("HOLA Cooperativa");
				  $("popup3").text("Petición al servidor fallida. Se utilizarán datos locales");
			  rellenaPopup(JSON.parse(json_aux2), parseInt(actor));  
			  break;
		  case 3:
		  alert("HOLA Fabrica");
				  $("popup2").text("Petición al servidor fallida. Se utilizarán datos locales");
			  rellenaPopup(JSON.parse(json_aux3), parseInt(actor));
			  break;
		  case 4:
		  alert("HOLA Tienda");
				  $("popup4").text("Petición al servidor fallida. Se utilizarán datos locales");
			  rellenaPopup(JSON.parse(json_aux4), parseInt(actor));
			  break;
		  default:
			  break;
		  }
		});
}










function rellenaPopup(stock, actor) {
	
	alert("rellenaPopup("+stock+","+actor+")");
    
    if (stock == null) {
  // En este caso ni el servidor ha devuelto nada ni tenemos JSON local
  // Escribir en el popup "Respuesta del servidor errónea" y terminar
  $("popup" + actor).append("<br><br>Respuesta del servidor o datos locales erróneos. No hay stock para mostrar");
  return;
    }
    
    // Leer info del JSON y ponerla en el popup correspondiente
    
	switch(stock.tipoActor) {

    case "Agricultor":
	alert("still Agricultor"); // entra aqui y no sabe imprimirlo. problema con popup
  $("popup1").append("<br><br>Datos del Agricultor<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Agricultor: " + "<br>MALTA:" + "<br>1.Base pálida: "+ stock.stock.malta_palida +" kg<br>2.Cebada tostada:" +" "+ stock.stock.malta_tostada +" kg<br>3.Negra: "+ stock.stock.malta_negra +" kg<br>4.Crystal: "+ stock.stock.malta_crystal +" kg<br>5.Chocolate: "+ stock.stock.malta_chocolate +" kg<br>6.Caramelo: "+ stock.stock.malta_caramelo +" kg<br>7.Pilsner: "+ stock.stock.malta_pilsner +" kg<br>8.Munich: "+ stock.stock.malta_munich +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ stock.stock.lupulo_perle +" kg<br>2.Tettnager: "+ stock.stock.lupulo_tettnager +" kg<br>3.Centennial: "+ stock.stock.lupulo_centennial +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ stock.stock.levadura_ale +" kg<br>2.Lagger: "+ stock.stock.levadura_lagger +" kg");
  break;

    case "Cooperativa":
  $("popup3").append("<br><br>Datos de la Cooperativa<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Agricultor: " + "<br>MALTA:" + "<br>1.Base pálida: "+ stock.stock.malta_palida +" kg<br>2.Cebada tostada:" +" "+ stock.stock.malta_tostada +" kg<br>3.Negra: "+ stock.stock.malta_negra +" kg<br>4.Crystal: "+ stock.stock.malta_crystal +" kg<br>5.Chocolate: "+ stock.stock.malta_chocolate +" kg<br>6.Caramelo: "+ stock.stock.malta_caramelo +" kg<br>7.Pilsner: "+ stock.stock.malta_pilsner +" kg<br>8.Munich: "+ stock.stock.malta_munich +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ stock.stock.lupulo_perle +" kg<br>2.Tettnager: "+ stock.stock.lupulo_tettnager +" kg<br>3.Centennial: "+ stock.stock.lupulo_centennial +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ stock.stock.levadura_ale +" kg<br>2.Lagger: "+ stock.stock.levadura_lagger +" kg");
  break;

    case "Fabrica":
  $("popup2").append("<br><br>Datos de la Fábrica<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Agricultor: " + "<br>MALTA:" + "<br>1.Base pálida: "+ stock.stock.malta_palida +" kg<br>2.Cebada tostada:" +" "+ stock.stock.malta_tostada +" kg<br>3.Negra: "+ stock.stock.malta_negra +" kg<br>4.Crystal: "+ stock.stock.malta_crystal +" kg<br>5.Chocolate: "+ stock.stock.malta_chocolate +" kg<br>6.Caramelo: "+ stock.stock.malta_caramelo +" kg<br>7.Pilsner: "+ stock.stock.malta_pilsner +" kg<br>8.Munich: "+ stock.stock.malta_munich +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ stock.stock.lupulo_perle +" kg<br>2.Tettnager: "+ stock.stock.lupulo_tettnager +" kg<br>3.Centennial: "+ stock.stock.lupulo_centennial +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ stock.stock.levadura_ale +" kg<br>2.Lagger: "+ stock.stock.levadura_lagger +" kg<br><br>LOTES:"+"<br>1. Cerveza Pilsner: "+ stock.stock.lotes_pilsner +" lotes<br>2. Cerveza Stout: " + stock.stock.lotes_stout + " lotes");
  break;

    case "Retailer":
  $("popup4").append("<br><br>Datos del Retailer<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Retailer: "+"<br>LOTES:"+"<br>1. Cerveza Pilsner: "+ stock.stock.lotes_pilsner +" lotes<br>2. Cerveza Stout: " + stock.stock.lotes_stout + " lotes");
  break;

    default :
  $("popup" + actor).append("<br><br>Respuesta del servidor o datos locales erróneos. No hay stock para mostrar");
  break;

    }   
}



/* JSON local por si el servidor falla o no hay datos */

var json_aux1 = '{"nomUsuario":"Agricultor1","tipoActor":"Agricultor","email":"agricultor1@email.com","stock":{ "malta_palida":"2343","malta_tostada":"234252","malta_negra":"74564","malta_crystal":"09340234","malta_chocolate":"324","malta_caramelo":"90428042","malta_pilsner":"23424","malta_munich":"54353","lupulo_perle":"4242","lupulo_tettnager":"4242342","lupulo_centennial":"34242","levadura_ale": "34243","levadura_lagger": "84092","lotes_pilsner":"","lotes_stout":""}}';

var json_aux2 = '{"nomUsuario":"Cooperativa1","tipoActor":"Cooperativa","email":"cooperativa1@email.com","stock":{ "malta_palida": "2343","malta_tostada": "234252","malta_negra": "74564","malta_crystal": "09340234","malta_chocolate": "324","malta_caramelo": "90428042","malta_pilsner": "23424","malta_munich": "54353","lupulo_perle": "4242","lupulo_tettnager": "4242342", "lupulo_centennial": "34242","levadura_ale": "34243","levadura_lagger": "84092","lotes_pilsner":"","lotes_stout":""}}';

var json_aux3 = '{"nomUsuario":"Fábrica1","tipoActor":"Fabrica","email":"fabrica1@email.com","stock":{ "malta_palida": "2343","malta_tostada": "234252","malta_negra": "74564","malta_crystal": "09340234","malta_chocolate": "324","malta_caramelo": "90428042","malta_pilsner": "23424","malta_munich": "54353","lupulo_perle": "4242","lupulo_tettnager": "4242342","lupulo_centennial": "34242","levadura_ale": "34243","levadura_lagger": "84092","lotes_pilsner":"32424","lotes_stout":"23424"}}';

var json_aux4 = '{"nomUsuario":"Retailer1","tipoActor":"Retailer","email":"retailer1@email.com", "stock":{ "malta_palida": "","malta_tostada": "","malta_negra": "","malta_crystal": "","malta_chocolate": "","malta_caramelo": "","malta_pilsner": "","malta_munich": "","lupulo_perle": "","lupulo_tettnager": "","lupulo_centennial": "","levadura_ale": "","levadura_lagger": "","lotes_pilsner":"596","lotes_stout":"756"}}';



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

