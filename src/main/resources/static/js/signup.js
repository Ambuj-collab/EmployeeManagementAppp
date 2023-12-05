const baseURL = window.location.origin;

const form = document.getElementById('signup_form');
form.addEventListener('submit', function(event) {
	event.preventDefault();

	// 1: get form data
	const formData = new FormData(form);
	// 2: store form data in object
	const jsonObject = Object.fromEntries(formData);
	// 3: convert form data object to a JSON string
	const jsonString = JSON.stringify(jsonObject);

	registerApiCall(jsonString).then(responseBody => {
		if (responseBody.message.localeCompare("Registration is successful") === 0) {
			showModal("Successfully registered", "It's great to have you aboard, " + responseBody.firstname);
			document.getElementById("modal-login-button").classList.remove("hide");
		}
		else if (responseBody.status === 500 && responseBody.message == 'Registration is not successful') {
			showModal("Registration failed", "Oops! Something went wrong. Please try again");
		}
		else if (responseBody.status === 409 && responseBody.message == 'Already we an account with this email address') {
			showModal("Registration failed", "There’s already an account with this email address. Use a different email or <a style='color: #008000; background-color: #CCD1D1' href='/login'>Login</a> with this email address");
		}
	});
});

async function registerApiCall(jsonString) {
	const response = await fetch(baseURL + "/register", {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		body: jsonString
	});
	let jsonData = response.json();
	if (response.status === 201) {
		return jsonData;
	}
	else if (response.status === 500 && jsonData.message.includes("Registration is successful")) {
		let jsonObj = JSON.parse('{}');
		const responseArray = jsonData.message.split(",");
		jsonObj.message = responseArray[0];
		jsonObj.firstname = responseArray[1];
		jsonObj.lastname = responseArray[2];
		return jsonObj;
	}
	else {
		return jsonData;
	}
}

function showModal(modalHeading, modalMessage) {
	let signupModal = document.querySelector("div.modal-wrapper > div.signup-modal");
	let messageInSignupModal = document.querySelector("div.modal-wrapper > div.signup-modal > div.server-message > h5.add-message");
	signupModal.querySelector("h1.registration-status").innerHTML = modalHeading;
	messageInSignupModal.innerHTML = modalMessage;
	document.querySelector("div.modal-wrapper").classList.remove("hide");
	document.querySelector("div.signup-form").classList.add("hide");
	let signupModalWidth = signupModal.clientWidth;
	if (signupModalWidth === messageInSignupModal.clientWidth) {
		document.querySelector("div.modal-wrapper > div.signup-modal > div.server-message").classList.add("space-from-left-and-right");
	}
	if (signupModalWidth === signupModal.querySelector("h1.registration-status").clientWidth) {
		signupModal.style.width = (signupModalWidth + 200).toString() + 'px';
	}
}

const modalClose = document.querySelector("div.modal-wrapper > div.signup-modal > button.close");
modalClose.addEventListener("click", function(event) {
	let registrationStatus = document.querySelector("div.modal-wrapper > div.signup-modal > h1.registration-status").innerText;
	if (registrationStatus == 'Successfully registered') {
		window.location = baseURL + "/login";
	}
	else {
		document.querySelector("div.modal-wrapper").classList.add("hide");
		document.querySelector("div.signup-form").classList.remove("hide");
	}
});