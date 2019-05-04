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
	  
		url : url,            // la URL para la petición
		data :"id="+actor ,
		type : 'GET',
		dataType : 'json',     // el tipo de información que se espera de respuesta
  
    });
 
     request.done(function(data){
	 
        //reescribo el array local idsOrdenes
		idsOrdenes = JSON.parse(data).listaIDs;
		
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
      
			url : '/crearOrden',    // la URL para la petición
			data : paraJson(actor),
			type : 'POST',
			dataType : 'json',  // el tipo de información que se espera de respuesta
 
		});
 
		request.done(function(data){
      
			//TODO Este metodo redirige a la URL 
			// switch(actor){}
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





function mandarids(urlpar){

  //Distintos casos
	//0aceptar pedido ->Para los recibidos pero no aceptados
	//1ordenes listas para entregar ->Completar
var urlaux;
if(urlpar=='0') urlaux='/aceptarOrden';
if(urlpar=='1') urlaux='/listaOrden';
console.log(urlaux);
  //creare un array de longitud tantos como Ordenes haya (pedidos)
  //donde 1 en i pos significa que he marcado la orden i, -1 si no marcado
  var str = 'producto';
  var array = Array.from(idsOrdenes); // copio idsOrdenes en nueva variable
  var str2 = '';
  var aux;
  for (var i = 1; i< idsOrdenes.length+1; i++) {
  
		var x = i.toString(10);
		str2 = str.concat(x);
		aux = document.getElementById(str2);
		if (aux.checked){
		
		if(urlpar=='0')console.log("intento mandar  a /aceptarOrden?id="+idsOrdenes[i-1]);
		 if(urlpar=='1')console.log("intento mandar  a /listaOrden?id="+idsOrdenes[i-1]);

		  array[i-1] = 1;
		  
		  
			var request = $.ajax({
			
				url : urlaux,
				data :"id="+idsOrdenes[i-1] ,
				type : 'POST',
				dataType : 'json',  // el tipo de información que se espera de respuesta
				
			});
		 
			request.done(function(data){
			  
				 
				 
			});
		 
			request.fail(function(data) {
			 
			 
			if (urlpar == '0') alert("ERROR al aceptar "+idsOrdenes[i-1]+" pedido");

		   	else {
			alert("ERROR al completar "+idsOrdenes[i-1]+" pedido");
				}
			});
	  
	    } else {
			array[i-1] = -1;
		}
    
    }
	
	
	console.log("ARRAY ES  "+array);
	//console.log("EL ORIYINAL ES  "+idsOrdenes);  //(no lo machaca)
	
	
}

//--------------------------------LOCALES------------------------------------------

var idsOrdenes = [6,7,5,3,21,43,76,4,6534];




////////////////////////////PARA MOSTRAR JSONSSSSSSSSSSSSSSS//////////////////////////


function pedirStock(actor,i) {
	
	$("#popup"+i).show();
	
	console.log("pido stock a /dameStockActor?id="+actor);
	
    var request = $.ajax({
		
	  url : '/dameStockActor', // la URL para la petición del stock
	  data : "id=" + actor,
	  type : 'GET',
	  dataType : 'json',  // el tipo de información que se espera de respuesta
	  
		});
    
    // la respuesta es pasada como argumento a la función
    request.done(function(data){
  
		//se han obtenido json del STOCK
		pedido = JSON.parse(data);
			
		//paso por parametro a imprimir
		rellenaPopup(pedido, actor,i);
  
    });
    request.fail(function(data) {
		
		alert("fallo el ajax Stock");
		
		muestraFallo(actor, i);
		  
	});
}




function pedirPedido(pos, actor,i) {
	
	$("#popup"+i).show();
	var idOrden = idsOrdenes[pos];
	console.log("pido pedido a /obtenerOrden?id="+idOrden);
		 
	var request = $.ajax({
		  
		url : '/obtenerOrden',  // la URL para la petición
		data :"id="+idOrden ,
		type : 'GET',
		dataType : 'json',  // el tipo de información que se espera de respuesta
			 
	});
	 
	request.done(function(data){
		 
		//se han obtenido json del pedido
		pedido = JSON.parse(data);
			
		//paso por parametro a imprimir
		rellenaPopup(pedido, actor,i);
		
	});
		 
	request.fail(function(data){
			
		alert("fallo el ajax Pedido "+idsOrdenes[pos]);
			
		muestraFallo(actor, i);
		   
	});
}


function muestraFallo(actor, i){
	
	switch(actor) {
			  case 0:
			  alert("HOLA Agricultor");
					  $("popup"+i).text("Petición al servidor fallida. Se utilizarán datos locales");
				  rellenaPopup(JSON.parse(json_aux1), actor,i);  
				  break;
				  
			  case 1:
			  alert("HOLA Cooperativa");
					  $("popup"+i).text("Petición al servidor fallida. Se utilizarán datos locales");
				  rellenaPopup(JSON.parse(json_aux2), actor,i);  
				  break;
				  
			  //case 2: Transportista
			  
			  case 3:
			  alert("HOLA Fabrica");
					  $("popup"+i).text("Petición al servidor fallida. Se utilizarán datos locales");
				  rellenaPopup(JSON.parse(json_aux3), actor,i);
				  break;
				  
			  case 4:
			  alert("HOLA Tienda");
					  $("popup"+i).text("Petición al servidor fallida. Se utilizarán datos locales");
				  rellenaPopup(JSON.parse(json_aux4), actor,i);
				  break;
				  
			  default:
				  break;
	}
}




function rellenaPopup(stock, actor, i) {
	
   
    if (stock == null) {
		
	  // En este caso ni el servidor ha devuelto nada ni tenemos JSON local
	  $("popup" + i).append("<br><br>Respuesta del servidor o datos locales erróneos. No hay stock para mostrar");
	  return;
    }
    
    switch(stock.tipoActor) {

		// ESTOS METODOS ESTAN ECHOS PARA EL JSON VIEJO, HAY QUE ACTUALIZARLOS
		
		//           por ejemplo lo que antes era stock.nomUsuario, ahora sera stock.actorOrigen
		//                                          ^del json viejo                 ^del json nuevo
	  case "Agricultor":
	  $("popup"+i).append("<br><br>Datos del Agricultor<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Agricultor: " + "<br>MALTA:" + "<br>1.Base pálida: "+ stock.stock.malta_palida +" kg<br>2.Cebada tostada:" +" "+ stock.stock.malta_tostada +" kg<br>3.Negra: "+ stock.stock.malta_negra +" kg<br>4.Crystal: "+ stock.stock.malta_crystal +" kg<br>5.Chocolate: "+ stock.stock.malta_chocolate +" kg<br>6.Caramelo: "+ stock.stock.malta_caramelo +" kg<br>7.Pilsner: "+ stock.stock.malta_pilsner +" kg<br>8.Munich: "+ stock.stock.malta_munich +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ stock.stock.lupulo_perle +" kg<br>2.Tettnager: "+ stock.stock.lupulo_tettnager +" kg<br>3.Centennial: "+ stock.stock.lupulo_centennial +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ stock.stock.levadura_ale +" kg<br>2.Lagger: "+ stock.stock.levadura_lagger +" kg");
	  break;
	  
	  case "Cooperativa":
	  $("popup"+i).append("<br><br>Datos de la Cooperativa<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Agricultor: " + "<br>MALTA:" + "<br>1.Base pálida: "+ stock.stock.malta_palida +" kg<br>2.Cebada tostada:" +" "+ stock.stock.malta_tostada +" kg<br>3.Negra: "+ stock.stock.malta_negra +" kg<br>4.Crystal: "+ stock.stock.malta_crystal +" kg<br>5.Chocolate: "+ stock.stock.malta_chocolate +" kg<br>6.Caramelo: "+ stock.stock.malta_caramelo +" kg<br>7.Pilsner: "+ stock.stock.malta_pilsner +" kg<br>8.Munich: "+ stock.stock.malta_munich +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ stock.stock.lupulo_perle +" kg<br>2.Tettnager: "+ stock.stock.lupulo_tettnager +" kg<br>3.Centennial: "+ stock.stock.lupulo_centennial +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ stock.stock.levadura_ale +" kg<br>2.Lagger: "+ stock.stock.levadura_lagger +" kg");
	  break;

		case "Fabrica":
	  $("popup"+i).append("<br><br>Datos de la Fábrica<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Agricultor: " + "<br>MALTA:" + "<br>1.Base pálida: "+ stock.stock.malta_palida +" kg<br>2.Cebada tostada:" +" "+ stock.stock.malta_tostada +" kg<br>3.Negra: "+ stock.stock.malta_negra +" kg<br>4.Crystal: "+ stock.stock.malta_crystal +" kg<br>5.Chocolate: "+ stock.stock.malta_chocolate +" kg<br>6.Caramelo: "+ stock.stock.malta_caramelo +" kg<br>7.Pilsner: "+ stock.stock.malta_pilsner +" kg<br>8.Munich: "+ stock.stock.malta_munich +" kg<br><br>LÚPULO:" + "<br>1.Perle: "+ stock.stock.lupulo_perle +" kg<br>2.Tettnager: "+ stock.stock.lupulo_tettnager +" kg<br>3.Centennial: "+ stock.stock.lupulo_centennial +" kg<br><br>LEVADURA:"+"<br>1.Ale: "+ stock.stock.levadura_ale +" kg<br>2.Lagger: "+ stock.stock.levadura_lagger +" kg<br><br>LOTES:"+"<br>1. Cerveza Pilsner: "+ stock.stock.lotes_pilsner +" lotes<br>2. Cerveza Stout: " + stock.stock.lotes_stout + " lotes");
	  break;

		case "Retailer":
	  $("popup"+i).append("<br><br>Datos de la Tienda<br>Nombre: " + stock.nomUsuario + "<br>Email: " + stock.email + "<br><br>Stock del Retailer: "+"<br>LOTES:"+"<br>1. Cerveza Pilsner: "+ stock.stock.lotes_pilsner +" lotes<br>2. Cerveza Stout: " + stock.stock.lotes_stout + " lotes");
	  break;

		default :
	  $("popup"+i).append("<br><br>Respuesta del servidor o datos locales erróneos. No hay stock para mostrar");
	  break;

    }   
}



/* JSON local por si el servidor falla o no hay datos */

////// JSONS VIEJOSSSS /////

var json_aux1 = '{"nomUsuario":"Agricultor1","tipoActor":"Agricultor","email":"agricultor1@email.com","stock":{ "malta_palida":"2343","malta_tostada":"234252","malta_negra":"74564","malta_crystal":"09340234","malta_chocolate":"324","malta_caramelo":"90428042","malta_pilsner":"23424","malta_munich":"54353","lupulo_perle":"4242","lupulo_tettnager":"4242342","lupulo_centennial":"34242","levadura_ale": "34243","levadura_lagger": "84092","lotes_pilsner":"","lotes_stout":""}}';

var json_aux2 = '{"nomUsuario":"Cooperativa1","tipoActor":"Cooperativa","email":"cooperativa1@email.com","stock":{ "malta_palida": "2343","malta_tostada": "234252","malta_negra": "74564","malta_crystal": "09340234","malta_chocolate": "324","malta_caramelo": "90428042","malta_pilsner": "23424","malta_munich": "54353","lupulo_perle": "4242","lupulo_tettnager": "4242342", "lupulo_centennial": "34242","levadura_ale": "34243","levadura_lagger": "84092","lotes_pilsner":"","lotes_stout":""}}';

var json_aux3 = '{"nomUsuario":"Fábrica1","tipoActor":"Fabrica","email":"fabrica1@email.com","stock":{ "malta_palida": "2343","malta_tostada": "234252","malta_negra": "74564","malta_crystal": "09340234","malta_chocolate": "324","malta_caramelo": "90428042","malta_pilsner": "23424","malta_munich": "54353","lupulo_perle": "4242","lupulo_tettnager": "4242342","lupulo_centennial": "34242","levadura_ale": "34243","levadura_lagger": "84092","lotes_pilsner":"32424","lotes_stout":"23424"}}';

var json_aux4 = '{"nomUsuario":"Retailer1","tipoActor":"Retailer","email":"retailer1@email.com", "stock":{ "malta_palida": "","malta_tostada": "","malta_negra": "","malta_crystal": "","malta_chocolate": "","malta_caramelo": "","malta_pilsner": "","malta_munich": "","lupulo_perle": "","lupulo_tettnager": "","lupulo_centennial": "","levadura_ale": "","levadura_lagger": "","lotes_pilsner":"596","lotes_stout":"756"}}';



////////////////////JSON NUEVO//////////////////////

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
    "cant_malta_palida": 0,       // para acceder a esto x ejemplo seria, pedido.productosPedidos.cant_malta_palida (al ponerlo en el metodo de arriba envez de pedido es stock(ke es el nombre del parametro))
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
