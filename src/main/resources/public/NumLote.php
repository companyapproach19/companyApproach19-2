<html lang="es">
  <head>
    <title>NumLote</title>
    <meta charset="utf-8">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="prueba.css" rel="stylesheet" id="bootstrap-css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    
  </head>
  <body style="display: block; height: 100vh; !important">
  <body>

    <div class="container-fluid register">
      <div class="row">
        <div class="col-md-3 register-left">
          <img src="https://image.flaticon.com/icons/png/512/25/25007.png" alt="" />
          <h1>Fábrica</h1>
                        <!--<p>Pedidos recibidos</p>
                        <input type="submit" name="" value="Log out"/><br/>
                            -->
        </div>

        <div class="col-md-9 register-right">
          <div class="row">
            <div class="col-md-4 offset-md-2">
              <h3 class="register-heading">¿Qué desea realizar?</h3>
            </div>

            <ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
              <li class="nav-item">
                <a class="nav-link" id="home" data-toggle="submit" href="/" role="submit" aria-controls="home" aria-selected="true">Log out</a>
              </li>
            </ul>
          </div>
          <br/><br/><br/><br/>

          <div class="row">
            <div class="col-md-10 offset-md-2">
              <a class="btn btn-primary" data-toggle="collapse" href="#collapseSeguimiento" role="button" aria-expanded="false" aria-controls="collapseSeguimiento">
              Producciones
            </a>
            
            <div class="collapse" id="collapseSeguimiento">
              Introduzca el número de una <strong>producción:</strong>
              <br/></br>
              <div>
                <span class="fa fa-user" id = numLoteIntroducido></span> 
                <input type="text"  name="lote" Placeholder="1234..." required>
                
                <div class="input-group-append">
                  <ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
                    <li class="nav-item" >
                       <a class="nav-link" id="home" data-toggle="submit" href="Lote.php?numLoteIntroducido=$numLoteIntroducido" role="submit" aria-controls="home" aria-selected="true" onclick="function recogidaNumLote() {
                            window.location = 'Lote.html?numLoteIntroducido=' + document.getElementById('numLote').value;
                       }
                       recogidaNumLote()">Buscar</a>
                    </li>
                  </ul>
                </div>
              </div> <!-- JS because of IE support; better: placeholder="Username" -->
              <br/>
            </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-10 offset-md-2">
              <a class="btn btn-primary" data-toggle="submit" href="Cadena.html" role="button" aria-expanded="false" aria-controls="collapseCreacion">
                Seguimiento de cadena
              </a>

            <!--  <div class="collapse" id="collapseCreacion">
                <span class="row">Selecciona la producción de cerveza que deseea crear:</span>
                <div class="row">
                  <ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
                    <li class="nav-item" >
                       <a class="nav-link" id="home" data-toggle="submit" href="Pilsner.html" role="submit" aria-controls="home" aria-selected="true">Crear producción Pilsner</a>
                    </li>
                  </ul>

                  <ul class="nav nav-tabs nav-justified ml-2" id="myTab" role="tablist">
                    <li class="nav-item">
                       <a class="nav-link" id="home" data-toggle="submit" href="Stout.html" role="submit" aria-controls="home" aria-selected="true">Crear produción Stout</a>
                    </li>
                  </ul>

                </div>
                <br/>            
              </div>-->
            </div>
          </div>
      
            <div class="row">
              <div class="col-md-10 offset-md-2">
                <a class="btn btn-primary" data-toggle="collapseVisualizar" href="fabrica2.html" role="button" aria-expanded="false" aria-controls="collapseVisualizar">
                  Cuadro de mandos
                </a>
              </div>
           </div>
           <div class="row">
            <div class="col-md-10 offset-md-2">
              <a class="btn btn-primary" data-toggle="collapse" href="#collapse3" role="button" aria-expanded="false" aria-controls="collapseCreacion">
                Pedidos
              </a>

              <div class="collapse" id="collapse3">
                
                <div class="row">
                  <ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
                    <li class="nav-item" >
                       <a class="nav-link" id="home" data-toggle="submit" href="fabricaListaPedidosAceptados.html" role="submit" aria-controls="home" aria-selected="true">Pedidos aceptados</a>
                    </li>
                  </ul>
                
                  <ul class="nav nav-tabs nav-justified ml-2" id="myTab" role="tablist">
                    <li class="nav-item" >
                       <a class="nav-link" id="home" data-toggle="submit" href="fabricaListaPedidosPorResolver.html" role="submit" aria-controls="home" aria-selected="true">Pedidos por resolver</a>
                    </li>
                  </ul>
                  </div>

                  <div class="row">
                  <ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
                    <li class="nav-item">
                       <a class="nav-link" id="home" data-toggle="submit" href="fabricaListaPedidosRecibidos.html" role="submit" aria-controls="home" aria-selected="true">Pedidos recibidos</a>
                    </li>
                  </ul>
                  <br>
                  <ul class="nav nav-tabs nav-justified ml-2" id="myTab" role="tablist">
                    <li class="nav-item">
                       <a class="nav-link" id="home" data-toggle="submit" href="fabricaSolicitarPedido.html" role="submit" aria-controls="home" aria-selected="true">Solicitar pedidos</a>
                    </li>
                  </ul>

                </div>
                <br/>            
              </div>
            </div>
          </div>
           <br/><br/><br/><br/><br/><br/>
           </div>
         </div>
       </div>
  </body>
</html>
<!------ Include the above in your HEAD tag ---------->

<link href="http://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


<!-- <div class="clearfix">
  <a class="nav-link" href="Pilsner.html"  style= 'text-decoration: none;color:white'>
                  <input type="button" class="btnRegister" value="Crear lote Pilsner">
                </a>
                <a class="nav-link" href="Stout.html"  style= 'text-decoration: none;color:white'>
                  <input type="button" class="btnRegister" value="Crear lote Stout">
                </a>
<div class="row register-form">
                        <div class="col-md-9">
                        <div class="container">
                            <div class="row">
                            <a class="nav-link" href="Lote.html"  style="text-decoration: none;color:white">
                  <input type="button" class="btnRegister" value="Buscar">
                </a> 
              <div class="row">
                                
                                <p>
                                  <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample2" role="button" aria-expanded="false" aria-controls="collapseExample2">
                                    Crear un Lote
                                  </a>
                                </p>
                                
                            </div>-->

       <script>
       function hacerSeguimientoLote(){
         var request = $.ajax({
           url: '/obtenerNumLote',
           data : "numLoteIntroducido=" + $("#CuadroNumLote").val(),
           type : 'POST',
           dataType : 'json',
         });
         request.done(function(data){
              
              //Este metodo redirige a la URL
             if(data.existe){
               href = "Lote.html";
           alert(data.num);
           }
           else{
           alert("No existe el lote");
           }
            
             });
         
             request.fail(function(data) {
           
             alert("Error en el servidor");
           
             });
       }
       
       
       
          function obtenerNumLote(){

      var request = $.ajax({
      // la URL para la petición
      url : '/numLote',
 
      // la información a enviar
      // (también es posible utilizar una cadena de datos)
      data : "numLoteIntroducido=" + $("#numeroIntroducido").val(),
 
      // especifica si será una petición POST o GET
      type : 'POST',
 
      // el tipo de información que se espera de respuesta
      dataType : 'json',
 
      });
 
     request.done(function(data){
      
      //Este metodo redirige a la URL
     if(data.existe){
   alert(data.num);
   }
   else{
   alert("No existe el lote");
   }
    
     });
 
     request.fail(function(data) {
   
     alert("Error en el servidor");
   
     });
 
 }
        </script>
        function comprobarValor(valor){
          if(!NaN( $("#numeroIntroducido").val())){
            alert("Numero introducido no valido.");
              location.reload();
          }
          }
          function pasarVariables(pagina, nombres) {
            pagina +="?";
            nomVec=$("#numeroIntroducido").val();
              pagina += nomVec + "=" + escape(eval(nomVec));
              pagina = pagina.substring(0,pagina.length-1);
              location.href=pagina;
          }
