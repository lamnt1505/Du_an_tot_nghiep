function clear() {
	document.getElementById("sdt").innerHTML = "";
	document.getElementById("matkhau").innerHTML = "";
}

function checkempty1(form) {
	clear();
	if (form.phone.value.trim() == "") {
		document.getElementById("sdt").innerHTML = "Không để trống số phone!";
		return false;
	} 
	else if (form.password.value.trim() == "") {
		document.getElementById("matkhau").innerHTML = "Không để trống mật khẩu!";
		return false;
	} 

	else {
		return true;
	}

}