// ACTORES: 10 = agricultor, 6 =cooperativa, 2 = transportista, 8 = fábrica, 9 = retailer
// ESTADOS:  -1 = Rechazado, 0 = PendienteAceptar, 1 = Preparando, 2 = ListoEntregar, 3 = Transportandose, 4 = Entregado(Aceptado?)
function pedirIds(actor, estado){
	var actor2;
	switch (actor){
		case 0:
		actor2=10;
		break;
		case 1:
		actor2=6;
		break;
		case 3:
		actor2=8;
		break;
		case 4:
		actor2=9;
		break;
		}

	var url;
	switch (estado) {
		case 0 :
		url = "/ordenesPendientesPorAceptar";
		alert("obteniendo pedidos recibidos (pendientes por aceptar)");
		console.log("pido /ordenesPendientesPorAceptar?idActor="+actor2);
		break;
		
		case 1 :
		url = "/ordenesEnProceso";
		alert("obteniendo pedidos por resolver");
		console.log("pido /ordenesEnProceso?idActor="+actor2);
		break;
		
		case 2 :
		url = "/ordenesQueHeEnviado";
		alert("obteniendo pedidos aceptados");
		console.log("pido /ordenesQueHeEnviado?idActor="+actor2);
		break;
	}
	  
    var request = $.ajax({
	  
		url : url,            // la URL para la petición
		data :"idActor="+actor2 ,
		type : 'GET',
		dataType : 'json',     // el tipo de información que se espera de respuesta
  
    });
 
     request.done(function(data){
		 
		 
	 	
        //reescribo el array local idsOrdenes
		idsOrdenes = data.listaIDs;
		cargar_popups();
		
		console.log(idsOrdenes);
	     	
		//paso por parametro a imprimir
		
		imprimirjson(idsOrdenes);
    
     });
 
     request.fail(function(data) {
     
		alert("Error en el servidor obteniendo ids");
		
		//paso por parametro array idsOrdenes local 
		//imprimirjson(idsOrdenes);
		
		
     });
 
 }



function cargar_popups()
{
	
	 var stri = "'none'";
     var j = 0;
     var contenedor_modales;
     
     contenedor_modales = document.getElementById("contenedor_modales");
	if (idsOrdenes != null){
        for ( var i = 1; i <=idsOrdenes.length; i++) {
        	var modalN = 
                '<div class="form-group">'+
                '<input type="checkbox" name="producto'+i+'" id="producto'+i+'" value="Pedido id'+i+'">'+
                '<label for="producto'+i+'" id="label'+i+'">Pedido '+idsOrdenes[i-1]+'</label>'+
                '<a href="" data-toggle="modal" onclick="pedirPedido('+(i-1)+',4,'+i+')" data-target="#exampleModalScrollable'+i+'"> Ver más información del pedido </a>'+
                '</div>'+                                                          
                '<div class="modal fade" id="exampleModalScrollable'+i+'" tabindex="-1" role="dialog" aria-labelledby="exampleModalScrollableTitle" aria-hidden="true">'+
                '<div class="modal-dialog modal-dialog-scrollable" role="document">'+
                '<div class="modal-content">'+
                '<div class="modal-header">'+
                '<button type="button" class="close" data-dismiss="modal" aria-label="Close">'+
                '<span aria-hidden="true">&times;</span>'+
                '</button>'+
                '<div id="popup'+i+'" style="display: none;">'+  
                '<div class="inner">'+                      
                '<h1>PEDIDO</h1>'+                     
                '<popup'+i+'></popup'+i+'>'+
                '</div>'+
                '</div>'+
                '</div>'+
                '</div>'+
                '</div>'+ 
                '</div>';
                
                contenedor_modales.innerHTML += modalN;
                
          <!-- MODAL -->
          j++;
        }
      }
	
}
 
 
 
 
 
 
 
 
 
 //Funcion que imprime cada id ORDEN
 function imprimirjson(ids){
		console.log("Estoy en el ids ordenes CON");
		console.log(ids);
		if (idsOrdenes == null || idsOrdenes.length==0){
				alert("No hay pedidos");
				return;
		}
		
		var label = 'label';
            	for (var i = 1; i< ids.length + 1; i++) {
                	var x = i.toString(10);
               		document.getElementById(label.concat(x)).innerHTML = ids[i-1];
		}
		  
}






function creaOrden(actor){
	var actor2;
	switch (actor){
		case 0:
		actor2=10;
		break;
		case 1:
		actor2=6;
		break;
		case 3:
		actor2=8;
		break;
		case 4:
		actor2=9;
		break;
		}
	
	  console.log("mando orden a /crearOrden");

	  var soyAuxiliar=paraJson(actor);
      var request = $.ajax({
      
			url : '/crearOrden',    // la URL para la petición
			data : 'json='.concat(soyAuxiliar),
			type : 'POST',
			dataType : 'json',  // el tipo de información que se espera de respuesta
 
		});
 
		request.done(function(data){
      
			//TODO Este metodo redirige a la URL 
			// switch(actor){}
			//window.append('/cooperativaInicio.html'); mejor en el boton.
    
		});
 
		request.fail(function(data) {
     
			alert("Error en el servidor creando orden");
	 
		});
 
 }
 
 
 function paraJson(actor) {

	
	if (actor == 1 || actor == 3){ // coope, fabrica
		if (actor == 1) nuevaOrden.actorOrigen.id= 6;
		if (actor == 3) nuevaOrden.actorOrigen.id= 8;
		nuevaOrden.actorDestino.id = document.getElementById("idDestino").value -0;
		nuevaOrden.idPedido = document.getElementById("idPedido").value -0;
		nuevaOrden.productosPedidos.cant_malta_palida = document.getElementById("malta_palida").value -0;
		nuevaOrden.productosPedidos.cant_malta_munich= document.getElementById("malta_munich").value -0;
		nuevaOrden.productosPedidos.cant_malta_negra= document.getElementById("malta_negra").value -0;
		nuevaOrden.productosPedidos.cant_malta_crystal= document.getElementById("malta_crystal").value -0;
		nuevaOrden.productosPedidos.cant_malta_chocolate= document.getElementById("malta_chocolate").value -0;
		nuevaOrden.productosPedidos.cant_malta_caramelo= document.getElementById("malta_caramelo").value -0;
		nuevaOrden.productosPedidos.cant_cebada= document.getElementById("cebada").value -0;
		nuevaOrden.productosPedidos.cant_cebada_tostada= document.getElementById("cebada_tostada").value -0;
		nuevaOrden.productosPedidos.cant_lupulo_centenial=document.getElementById("lupulo_centinental").value -0;
	}else if (actor == 4){  // tienda
		nuevaOrden.idPedido = -1;
		nuevaOrden.actorOrigen.id= 9;
		nuevaOrden.actorDestino.id =  8;
		nuevaOrden.productosPedidos.cant_lotes_stout =  document.getElementById("cajas_stout").value -0;
		nuevaOrden.productosPedidos.cant_lotes_bisner = document.getElementById("cajas_bisner").value -0;
		
	}
	else { alert("Error llmando a paraJson(MAL:"+actor+")");}
	
	    
		

		console.log(nuevaOrden);
		var orden = JSON.stringify(nuevaOrden);
		console.log(orden);
		return orden;
	

  }








// 0 = /aceptarOrden, 1 = /listaOrden, 2 = /comienzoProcesoFabricacion
//Distintos casos
	//0 aceptar pedido ->Para los recibidos pero no aceptados
	//1 ordenes listas para entregar ->Completar
function mandarids(urlpar){

	var url;

	switch(urlpar){
		
		case 0 :
		url = "/aceptarOrden";
		break;
		
		case 1 :
		url = "/listaOrden";
		break;
		
		case 2:
		break;
		
		default:
		alert("parametro incorrecto mandarids(MAL:"+urlpar+")");
		return;
			
	}
	
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
		
		
		  array[i-1] = 1;
		  
		  // proceso fabricacion
		  if (urlpar==2){ // necesito obtener el id del pedido
				
				var idPedidoAux;
				console.log("pido idPedido en /obtenerOrden?id="+idsOrdenes[i-1]);
				var requestIdPedido = $.ajax({
				  
					url : '/obtenerOrden',  // la URL para la petición
					data :"id="+idsOrdenes[i-1] ,
					type : 'GET',
					dataType : 'json',  // el tipo de información que se espera de respuesta
						 
				});
				 
				requestIdPedido.done(function(data){
					 
					 url = "/comienzoProcesoFabricacion";
					//se han obtenido json del pedido
					idPedidoAux = data.idPedido;
					// ahora ya puedo empezar fabricacion
					
					console.log("empiezo fabricacion en /comienzoProcesoFabricacion?peticion="+idPedidoAux+"&orden="+idsOrdenes[i-1]);
					var request = $.ajax({
			
						url : url,
						data :"peticion="+idPedidoAux+"&orden="+idsOrdenes[i-1] ,
						type : 'GET',
						dataType : 'json',  // el tipo de información que se espera de respuesta
						
						});
				 
						request.done(function(data){
					  	for (var key of Object.keys(data)) {
					  		alert(data.key);
					  	}
						 
						 
						});
				 
						request.fail(function(data) {
					 
							alert("fallo empezando fabricacion de"+idsOrdenes[i-1]);
						
						});
					
				});
					 
				requestIdPedido.fail(function(data){
						
					alert("fallo el ajax obtenerIdPedido "+idsOrdenes[i-1]);
						
					  
				});
			} else { //urlpar es 0 o 1
			
				console.log("acepto/completo en "+url+"id="+idsOrdenes[i-1]);
				var request = $.ajax({
			
				url : url,
				data :"id="+idsOrdenes[i-1] ,
				type : 'POST',
				dataType : 'json',  // el tipo de información que se espera de respuesta
				
				});
		 
				request.done(function(data){
			  
				 
				 
				});
		 
				request.fail(function(data) {
			 
			  		alert("error aceptando pedido: "+idsOrdenes[i-1]);
				
				});
				
			}
		  
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

	var actor2;
	switch (actor){
		case 0:
		actor2=10;
		break;
		case 1:
		actor2=6;
		break;
		case 3:
		actor2=8;
		break;
		case 4:
		actor2=9;
		break;
		}
	$("#popup"+i).show();
	
	console.log("pido stock a /dameStockActor?id="+actor2);
	
    var request = $.ajax({
		
	  url : '/dameStockActor', // la URL para la petición del stock
	  data : "id=" + actor2,
	  type : 'GET',
	  dataType : 'json',  // el tipo de información que se espera de respuesta
	  
		});
    
    // la respuesta es pasada como argumento a la función
    request.done(function(data){
	    
	    
  
		//se han obtenido json del STOCK
		//pedido = data;
			
		//paso por parametro a imprimir
		
		//rellenaPopup(pedido, actor,i);
		imprimeStock(data, i); //maybe parse?
  
    });
    request.fail(function(data) {
		
		alert("fallo el ajax Stock");
		
		//imprimeStock(i);
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
		pedido = data;
			
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
			  alert("HOLA Agricultoraa");
					  $("popup"+i).text("Petición al servidor fallida. Se utilizarán datos locales");
				  rellenaPopup(pedido, actor,i);  
				  break;
				  
			  case 1:
			  alert("HOLA Cooperativa");
					  $("popup"+i).text("Petición al servidor fallida. Se utilizarán datos locales");
				  rellenaPopup(pedido, actor,i);  
				  break;
				  
			  //case 2: Transportista
			  
			  case 3:
			  alert("HOLA Fabrica");
					  $("popup"+i).text("Petición al servidor fallida. Se utilizarán datos locales");
				  rellenaPopup(pedido, actor,i);
				  break;
				  
			  case 4:
			  alert("HOLA Tienda");
					  $("popup"+i).text("Petición al servidor fallida. Se utilizarán datos locales");
				  rellenaPopup(pedido, actor,i);
				  break;
				  
			  default:
				  break;
	}
}




function rellenaPopup(json, actor, i) {
	
   
    if (json == null) {
		
	  // En este caso ni el servidor ha devuelto nada ni tenemos JSON local
	  $("popup" + i).append("<br><br>Respuesta del servidor o datos locales erróneos. No hay stock para mostrar");
	  return;
    }

    var estado = "";
		switch(json.estado){
		case "-1":
		estado="rechazado";
		break;
		case "0":
		estado="Pendiente por aceptar";
		break;
		case "1":
		estado="Preparando";
		break;
		case "2":
		estado="listo para entregar";
		break;
		case "3":
		estado="transportandose";
		break;
		case "4":
		estado="entregado";
		break;
		}
    
    switch(actor) {

		// ESTOS METODOS ESTAN ECHOS PARA EL JSON VIEJO, HAY QUE ACTUALIZARLOS
		
		//           por ejemplo lo que antes era stock.nomUsuario, ahora sera stock.actorOrigen
		//                                          ^del json viejo                 ^del json nuevo
	  
		





	  case 0:
	    alert("still agri");
	  $("popup"+i).append("<br><br>Datos Generales<br>ID orden: " + json.id + "<br>ID pedido:"+ json.idPedido+ "<br>Estado:"+ estado +"<br><br>ID de origen<br> " + json.actorOrigen.id + "<br><br>Cantidades: <br>1.Cebada tostada:" +json.productosPedidos.cant_cebada_tostada +"kg<br>2.Cebada:" +json.productosPedidos.cant_cebada+ "kg<br>3.Malta Palida" +  json.productosPedidos.cant_malta_palida +"+kg<br>4.Malta Munich:" +  json.productosPedidos.cant_malta_munich +" kg<br>5.Malta Negra: "+ json.productosPedidos.cant_malta_negra +" kg<br>6.Malta Crystal: "+ json.productosPedidos.cant_malta_crystal +" kg<br>7.Malta Chocolate: "+ json.productosPedidos.cant_malta_chocolate +" kg<br>8.Malta Caramelo: "+ json.productosPedidos.cant_malta_caramelo +" kg<br>9.Lupulo centenial:" +json.productosPedidos.cant_lupulo_centenial+" kg<br>");
	  break;
	
	  case 1:
	    alert("still cope");
	  $("popup"+i).append("<br><br>Datos Generales<br>ID orden: " + json.id + "<br>ID pedido:"+ json.idPedido+ "<br>Estado:"+ estado  +"<br><br>ID de origen<br> " + json.actorOrigen.id + "<br><br>Cantidades: <br>1.Cebada tostada:" +json.productosPedidos.cant_cebada_tostada +"kg<br>2.Cebada:" +json.productosPedidos.cant_cebada+ "kg<br>3.Malta Palida" +  json.productosPedidos.cant_malta_palida +"+kg<br>4.Malta Munich:" +  json.productosPedidos.cant_malta_munich +" kg<br>5.Malta Negra: "+ json.productosPedidos.cant_malta_negra +" kg<br>6.Malta Crystal: "+ json.productosPedidos.cant_malta_crystal +" kg<br>7.Malta Chocolate: "+ json.productosPedidos.cant_malta_chocolate +" kg<br>8.Malta Caramelo: "+ json.productosPedidos.cant_malta_caramelo +" kg<br>9.Lupulo centenial:" +json.productosPedidos.cant_lupulo_centenial+" kg<br>");
	  break;

		case 3:
		alert("still fab");
	  $("popup"+i).append("<br><br>Datos Generales<br>ID orden: " + json.id + "<br>ID pedido:"+ json.idPedido +"<br>Estado:"+ estado  +"<br><br>ID de origen<br> " + json.actorOrigen.id + "<br><br>Cantidades: <br>1.Cebada tostada:" +json.productosPedidos.cant_cebada_tostada +"kg<br>2.Cebada:" +json.productosPedidos.cant_cebada+ "kg<br>3.Malta Palida" +  json.productosPedidos.cant_malta_palida +"+kg<br>4.Malta Munich:" +  json.productosPedidos.cant_malta_munich +" kg<br>5.Malta Negra: "+ json.productosPedidos.cant_malta_negra +" kg<br>6.Malta Crystal: "+ json.productosPedidos.cant_malta_crystal +" kg<br>7.Malta Chocolate: "+ json.productosPedidos.cant_malta_chocolate +" kg<br>8.Malta Caramelo: "+ json.productosPedidos.cant_malta_caramelo +" kg<br>9.Lupulo centenial:" +json.productosPedidos.cant_lupulo_centenial +" kg<br>10.Lotes Bisner: "+ json.productosPedidos.cant_lotes_bisner + " 11. Lotes Stout: "+ json.productosPedidos.cant_lotes_stout);
	  break;

		case 4:
		alert("still tienda");
	  $("popup"+i).append("<br><br>Datos Generales<br>ID orden: " + json.id + "<br>ID pedido:"+ json.idPedido  +"<br>Estado:"+ estado +"<br><br>Cantidades: <br>1.Lotes Bisner: "+ json.productosPedidos.cant_lotes_bisner + " kg<br>2. Lotes Stout: "+ json.productosPedidos.cant_lotes_stout);
	  break;

		default :
	  $("popup"+i).append("<br><br>Respuesta del servidor o datos locales erróneos. No hay stock para mostrar");
	  break;

    }   
}

//Para el stock
function imprimeStock(json, i){
	
	console.log("STOCK ES: "+json.stock);
	console.log("LO QUE HAY ES: "+JSON.stringify(json));
	
	var stock = '<br><br>Stock';
	//json es el json
	for(var key of Object.keys(json.stock)){
		stock = stock.concat("<br>".concat(key).concat(":"));
		//donde key es el nombre de la materia prima
		var valor_mat = json.stock[key];
		stock = stock.concat(valor_mat)
	}
	$("popup"+i).append(stock);
}

/* JSON local por si el servidor falla o no hay datos */

////// JSONS VIEJOSSSS /////



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


var nuevaOrden = {
  "id": 0,
  "actorOrigen": {
    "id": 0,
    "nombreUsuario": "",
    "passwordPlana": "",
    "email": "",
    "tipoActor": 0
  },
  "actorDestino": {
    "id": 0,
    "nombreUsuario": "",
    "passwordPlana": "",
    "email": "",
    "tipoActor": 0
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
    "cant_lotes_stout": 0,
    "cant_lotes_bisner": 0
  },
  "productosAEntregar": [
    
  ],
  "estado": 0,
  "firmaRecogida": "",
  "firmaEntrega": "",
  "transportista": {
    "id": "",
    "nombreUsuario": "",
    "passwordPlana": "",
    "email": "",
    "tipoActor": 0
  },
  "idRegistro": 0,
  "idPedido": 0,
  "fecha": ""
}



