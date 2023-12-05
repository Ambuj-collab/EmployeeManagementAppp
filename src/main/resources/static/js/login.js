const baseURL = window.location.origin;

const form = document.getElementById("login-form");
form.addEventListener("submit", (event) => {
	event.preventDefault();

	// 1: get form data
	const formData = new FormData(form);
	// 2: store form data in object
	const jsonObject = formDataToObject(formData);
	// 3: convert form data object to a JSON string
	const jsonString = JSON.stringify(jsonObject);

	validateUserApiCall(jsonString).then(responseBody => {
		if (responseBody.message == 'Valid user') {
			window.location.href = baseURL + "/home";
		}
		else if (responseBody.message == 'Invalid user') {
			showModal("Login failed", "The email and password you entered didn't match our records. Please double-check and try again!!!");
		}
		else if (responseBody.message == "This account doesn't exist") {
			showModal("Login failed", responseBody.message + ". Enter a different account or <a style='color: #008000; background-color: #CCD1D1' href='/signup'>get a new one</a>.");
		}
	});
});

function formDataToObject(formData) {
	const jsonObject = {};

	for (const [key, value] of formData.entries()) {
		jsonObject[key] = value;
	}

	return jsonObject;
};

async function validateUserApiCall(jsonString) {
	const response = await fetch(baseURL + '/validate-user', {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		body: jsonString
	});
	let jsonData = response.json();
	return jsonData;

}

function showModal(modalHeading, modalMessage) {
	let loginModal = document.querySelector("div.modal-wrapper > div.login-modal");
	let messageInLoginModal = document.querySelector("div.modal-wrapper > div.login-modal > div.server-message > h5.add-message");
	loginModal.querySelector("h1.login-status").innerHTML = modalHeading;
	messageInLoginModal.innerHTML = modalMessage;
	document.querySelector("div.modal-wrapper").classList.remove("hide");
	document.querySelector("div.login-form").classList.add("hide");
	let loginModalWidth = loginModal.clientWidth;
	if (loginModalWidth === messageInLoginModal.clientWidth) {
		document.querySelector("div.modal-wrapper > div.login-modal > div.server-message").classList.add("space-from-left-and-right");
	}
	if (loginModalWidth === loginModal.querySelector("h1.login-status").clientWidth) {
		loginModal.style.width = (loginModalWidth + 200).toString() + 'px';
	}
}

const modalClose = document.querySelector("div.modal-wrapper > div.login-modal > button.close");
modalClose.addEventListener("click", function(event) {
	document.querySelector("div.modal-wrapper").classList.add("hide");
	document.querySelector("div.login-form").classList.remove("hide");
});