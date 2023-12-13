document.addEventListener("DOMContentLoaded", function () {
    const accountRibbon = document.getElementById("user-profile-account-ribbon");
    const userProfilePopup = document.getElementById("user-profile-popup");
    const closeButton = document.getElementById("user-profile-close-button");

    accountRibbon.addEventListener("click", function () {
        userProfilePopup.style.display = "block";
    });

    closeButton.addEventListener("click", function () {
        userProfilePopup.style.display = "none";
    });

    window.addEventListener("click", function (event) {
        if (event.target === userProfilePopup) {
            userProfilePopup.style.display = "none";
        }
    });
});
