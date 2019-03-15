function login(){
    var request = $.ajax({
      url : "/loginUser",
      method : "POST",
      data : "usuario=PEPE&pwd=123",
      dataType : "json"
      });
      request.done(function(data) {
        alert(data +"\n"+ data.nombreUsuario + "_" + data.passwordPlana);
        });
      request.fail(function(data) {
       alert("faill");
      });
  
  }
