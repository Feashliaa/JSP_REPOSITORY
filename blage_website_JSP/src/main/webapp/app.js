function checkCredentials() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    // AJAX call to the java servlet, passing the username and password
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {

            // If the user is valid, redirect to the welcome page
            if (this.responseText === "<result>true</result>") {
                console.log("Valid user");
                showAlert();
            } else {
                console.log("Invalid user");
                wrongCredentials();
            }

        }
    }
    xhttp.open("POST", "login", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password));
}

function signUp() {
    // link to the sign-up page
    window.location.href = "signUp.html";
}

function createAccount() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    // AJAX call to the java servlet, passing the username, password
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {

            console.log(this.responseText)

            // If the user is valid, redirect to the welcome page
            if (this.responseText === "<result>Created</result>") {
                console.log("Welcome!");
                let x = document.getElementById("alert");
                // Show the alert box for a few seconds
                x.style.display = "block";
                setTimeout(function () {
                    x.style.display = "none";
                    window.location.href = "welcome.html";
                }, 2000);
            } else {
                console.log("Username taken");
                usernameTaken();
            }

        }
    }
    xhttp.open("POST", "CreateAccount", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password));
}

function changePage() {
    window.location.href = "welcome.html";
}

function wrongCredentials() {
    let x = document.getElementById("label_alert");
    // update the text to make visible
    x.style.visibility = "visible";
}

function usernameTaken() {
    let x = document.getElementById("label_alert");
    // make visible
    x.style.visibility = "visible";
}

function showAlert() {
    let x = document.getElementById("alert");
    // Show the alert box for a few seconds
    x.style.display = "block";
    setTimeout(function () {
        x.style.display = "none";
        changePage();
    }, 2000);
}

function cycleThroughGifs() {
    let gifUrls = ["blage_taking_off.gif"];

    let currentGifIndex = 0;

    setInterval(function () {
        document.getElementById("background-container").innerHTML = "<img src='" + gifUrls[currentGifIndex] + "' style='width: 100%; height: 100%;' alt='broke'>";
        currentGifIndex++;
        if (currentGifIndex >= gifUrls.length) {
            currentGifIndex = 0;
        }
    }, 5000);
}

document.onload = cycleThroughGifs();