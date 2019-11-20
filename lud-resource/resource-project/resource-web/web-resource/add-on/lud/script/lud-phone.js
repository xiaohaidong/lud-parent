function fontSize() {
    var deviceWidth = document.documentElement.clientWidth;
    document.documentElement.style.fontSize = (deviceWidth /72) + "px";
}
fontSize();
window.onresize = fontSize;