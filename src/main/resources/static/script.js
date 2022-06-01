


document.getElementById("btn__iniciar-sesion").addEventListener("click", iniciarSesion);
document.getElementById("btn__registrarse").addEventListener("click", registro);




/* Declaración de variables */
var contenedor_login_registro = document.querySelector(".contenedor__login-registro");

var formulario_login = document.querySelector(".formulario__login");
var formulario_registro = document.querySelector(".formulario__registro");

var caja_trasera_login = document.querySelector(".caja__trasera-login");
var caja_trasera_registro = document.querySelector(".caja__trasera-registro");



 function iniciarSesion() { 

    /* Cuando le demos click al boton de "registrarse", se cambiará el display a block */

    formulario_registro.style.display = "none"
    contenedor_login_registro.style.left = "10px";
    formulario_login.style.display = "block";
    caja_trasera_registro.style.opacity = "1";
    caja_trasera_login.style.opacity = "0"; 

 } 


function registro() {
    /* Cuando le demos click al boton de "registrarse", se cambiará el display a block */
    formulario_registro.style.display = "block";
    contenedor_login_registro.style.left = "410px";
    formulario_login.style.display = "none";
    caja_trasera_registro.style.opacity = "0";
    caja_trasera_login.style.opacity = "1";

}