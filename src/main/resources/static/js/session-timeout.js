const sessionTimeout = 60 * 1000;
let timeoutId;

function resetTimer() {
    if (timeoutId) clearTimeout(timeoutId);

    timeoutId = setTimeout(() => {
        window.location.href = "/login?expired=true";
    }, sessionTimeout);
}

window.onload = resetTimer;
document.onmousemove = resetTimer;
document.onkeydown = resetTimer;
document.onclick = resetTimer;
document.onscroll = resetTimer;