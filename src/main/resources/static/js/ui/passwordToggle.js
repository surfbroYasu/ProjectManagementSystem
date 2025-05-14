/**
 * 
 */

 function togglePassword(id, el) {
	const input = document.getElementById(id);
	const icon = el.querySelector('i');
	if (input.type === "password") {
		input.type = "text";
		icon.classList.replace("bx-show", "bx-hide");
	} else {
		input.type = "password";
		icon.classList.replace("bx-hide", "bx-show");
	}
}
