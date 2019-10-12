function getCookie(cname) {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for(var i = 0; i <ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

var userCookie = getCookie("userId")
if(userCookie){
	document.getElementById("home-logo").href = "/guardarropas"
	document.getElementById("nav-body-elements").style.display = "block"
}
else{
	document.getElementById("home-logo").href = "/"
	document.getElementById("nav-body-elements").style.display = "none"
}
	