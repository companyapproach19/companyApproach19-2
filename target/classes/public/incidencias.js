function pedir(){
    var request = $.ajax({
      url : "/pedir",
      method : "POST",
      data : "dato=3",
      dataType : "json"
      });
      request.done(function(data) {
        try {
          alert("recibido");
        } catch (e) {
          alert("catch");
          return;
        }
        if (data.result == "OK") {
          alert("data result ok: "+ data.resultado + "\nDato: " + data.dato);
          alert("resultado BD: " + data.returned);
        } else {
          alert("data result no ok");
        }
        });
      request.fail(function(data) {
       alert("fail");
      });
  
  }

function redirigir(){
  var request = $.ajax({
    url : "/redirigir",
    method : "POST",
    });
  request.done(function(data) {
   alert("RECIBIDO: "+data);
   window.open(data);
    });
  request.fail(function(data) {
   alert("fail");
  });
}