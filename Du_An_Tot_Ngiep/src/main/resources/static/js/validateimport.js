function clear() {
	document.getElementById("soluong").innerHTML = "";
}
function checkempty(formproduct) {
	clear();
	if (formproduct.quantity.value.trim() == "") {
		document.getElementById("soluong").innerHTML = "Không để trống số lượng!";
		return false;
	} 
	else if (formproduct.quantity.value.trim() <=0 ) {
		document.getElementById("soluong").innerHTML = "Số lượng phải lớn ơn 0!";
		return false;
	} 
	else {
		return true;
	}

}