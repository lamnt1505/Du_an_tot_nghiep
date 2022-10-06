function clear() {
	document.getElementById("ten").innerHTML = "";
}
function checkempty(formproduct) {
	clear();
	if (formproduct.name.value.trim() == "") {
		document.getElementById("ten").innerHTML = "Không để trống tên!";
		return false;
	} 
	else {
		return true;
	}

}