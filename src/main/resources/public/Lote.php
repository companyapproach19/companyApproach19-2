<!------ Include the above in your HEAD tag ---------->
<html lang="es">
  <head>
    <meta charset="utf-8">
    <title>Lote</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<link href="prueba.css" rel="stylesheet" id="bootstrap-css">

</head>
	
	
<script type="text/javascript">
      function get_params() {
          var url_string = window.location.href;
          console.log(url_string);
          var url = new URL(url_string);
          var numLoteIntroducido = url.searchParams.get("numLoteIntroducido");
          console.log(numLoteIntroducido);
      }
 </script>
	
<body onload="get_params()" style="display: block; height: 100vh; !important">
<body>
    

<div class="container-fluid register">
    <div class="row"> 
        <div class="col-md-3 register-left">
             <img src="https://image.flaticon.com/icons/png/512/25/25007.png" alt=""/>
             <h3>Fábrica</h3>
                        <!--<p>Pedidos recibidos</p>-->
                    <!--<input type="submit" name="" value="Log out"/><br/>-->
        </div>
        <div class="col-md-9 register-right">
            <div class="row">
                <div class="col-md-5 offset-md-2">
                 <h3 class="register-heading">Proceso de fabricación</h3>
            </div>

            <ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
              <li class="nav-item">
                <a class="nav-link" id="home" data-toggle="submit" href="/" role="submit" aria-controls="home" aria-selected="true">Log out</a>
              </li>
            </ul>
          </div>
          <br/><br/><br/><br/><br/>
            <div class="row">
                <div class="col-md-10 offset-md-2">
                   <p><h3><strong> Lote:</strong> <?php echo "$numLoteIntroducido:$numLoteIntroducido;?></h3>
                    
                      </br>

                    </p>
                    <p><strong>1. Llegada a la fábrica</strong>
                      <br>
                      <div class="col-md-5 offset-md-2">
                        <a class="nav-link" id="Llegada" onclick="llegadaALaFabrica()" data-toggle="submit" href="#" role="submit" aria-controls="home" aria-selected="true">+ info</a>
                      </div>
                      
                    </p>     
                    <p><strong>2. Molienda</strong>
                      <br>
                      <div class="col-md-5 offset-md-2">
                        <a class="nav-link" id="Molienda" onclick="molienda()" data-toggle="submit" href="#" role="submit" aria-controls="home" aria-selected="true">+ info</a>
                      </div>
                      
                    </p>   
                    <p><strong>3. Cocción</strong>
                      <br>
                      <div class="col-md-5 offset-md-2">
                        <a class="nav-link" id="Coccion" onclick="coccion()" data-toggle="submit" href="#" role="submit" aria-controls="home" aria-selected="true">+ info</a>
                      </div>
                      
                    </p>   
                    <p><strong>4. Fermentación</strong>
                      <br>
                      <div class="col-md-5 offset-md-2">
                        <a class="nav-link" id="Fermentacion" onclick="fermentacion()" data-toggle="submit" href="#" role="submit" aria-controls="home" aria-selected="true">+ info</a>
                      </div>
                      
                    </p>   
                    <p><strong>5. Embotellado</strong>
                      <br>
                      <div class="col-md-5 offset-md-2">
                        <a class="nav-link" id="Embotellado" onclick="embotellado()" data-toggle="submit" href="#" role="submit" aria-controls="home" aria-selected="true">+ info</a>
                      </div>
                      
                    </p>   
                    <p><strong>6. Salida de la fábrica</strong>
                      <br>
                      <div class="col-md-5 offset-md-2">
                        <a class="nav-link" id="Salida" onclick="salidaDeLaFabrica()" data-toggle="submit" href="#" role="submit" aria-controls="home" aria-selected="true">+ info</a>
                      </div>
                      
                    </p>   
                    
                    <br/><br/><br/>
                    <div class="row">
                        <div class="col-md-10 offset-md-2">
                            <ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
                            <li class="nav-item" >
                                <a class="nav-link" id="home" data-toggle="submit" href="NumLote.php" role="submit" aria-controls="home" aria-selected="true">volver</a>
                            </li>
                            </ul>
                        </div>
            
                    </div>

                    <br/><br/><br/>

                </div>
            </div>
        </div>
        <br/><br/><br/><br/><br/><br/>
    </div> 
</div>

<script>
function llegadaALaFabrica(){
	alert("llamadaALaFabrica");
    var request = $.ajax({
    url : '/llegadaALaFabrica',
    data : "numLoteIntroducido=" + $("#numLoteIntroducido).val(),
    type : 'POST',
    dataType : 'json',
    });
	request.done(function(response){
		//var fecha = response.fechaInicio;
		alert("llama funcion ajax correcta");
		var fecha = response["fechaInicio"];
		//$('#Llegada').text(fecha);
		alert("la fecha que he recogido del servicio es:" + fecha);
  
   });
   request.fail(function(response) {
 
		alert("Error en el servidor");
 
   });
}
function molienda(){
	alert("moliendo");
	var request = $.ajax({
		url:'/molienda',
		data : "numLoteIntroducido=" + $("#numLoteIntroducido).val(),
		type : 'POST',
		dataType : 'json',
	});
	request.done(function(response){
		alert("llama funcion ajax correcta");
		var fecha = response["fechaInicio"];
		alert("la fecha que se ha recogido del servicio es: " + fecha);
	});
	request.fail(function(response){
		alert("Error en el servidor");
	});
	
}
function coccion(){
	alert("cociendo");
	var request = $.ajax({
		url:'/coccion',
		data : "numLoteIntroducido=" + $("#numLoteIntroducido).val(),
		type : 'POST',
		dataType : 'json',
	});
	request.done(function(response){
		alert("llama funcion ajax correcta");
		var fecha = response["fechaInicio"];
		alert("la fecha que se ha recogido del servicio es: " + fecha);
	});
	request.fail(function(response){
		alert("Error en el servidor");
	});
	
}
function fermentacion(){
	alert("fermentando");
	var request = $.ajax({
		url:'/fermentacion',
		data : "numLoteIntroducido=" + $("#numLoteIntroducido).val(),
		type : 'POST',
		dataType : 'json',
	});
	request.done(function(response){
		alert("llama funcion ajax correcta");
		var fecha = response["fechaInicio"];
		alert("la fecha que se ha recogido del servicio es: " + fecha);
		alert("la densidad es 1.2 no hace falta segunda fermentación")
	});
	request.fail(function(response){
		alert("Error en el servidor");
	});
	
}
function embotellado(){
	alert("embotellando");
	var request = $.ajax({
		url:'/embotellado',
		data : "numLoteIntroducido=" + $("#numLoteIntroducido).val(),
		type : 'POST',
		dataType : 'json',
	});
	request.done(function(response){
		alert("llama funcion ajax correcta");
		var fecha = response["fechaInicio"];
		alert("la fecha que se ha recogido del servicio es: " + fecha);
	});
	request.fail(function(response){
		alert("Error en el servidor");
	});
	
}
function salidaDeLaFabrica(){
	alert("salida de la fabrica");
	var request = $.ajax({
		url:'/salidaDeLaFabrica',
		data : "numLoteIntroducido=" + $("#numLoteIntroducido).val(),
		type : 'POST',
		dataType : 'json',
	});
	request.done(function(response){
		alert("llama funcion ajax correcta");
		var fecha = response["fechaInicio"];
		alert("la fecha que se ha recogido del servicio es: " + fecha);
	});
	request.fail(function(response){
		alert("Error en el servidor");
	});
	
}
</script>
</body>
