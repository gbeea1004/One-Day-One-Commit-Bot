$(document).ready(function() {
    setInterval(timer, 1000); // 1초마다 반복
});

function timer() {
    let timerBody = document.getElementById('timer');

    let nowTime = new Date();
    let commitTime = new Date(nowTime.getFullYear(), nowTime.getMonth(), nowTime.getDate() + 1, 0, 0, 0, 0);
    var timeGap = new Date(0, 0, 0, 0, 0, 0, commitTime - nowTime);

    timerBody.innerHTML = timeGap.getHours() + "시 " + timeGap.getMinutes() + "분 " + timeGap.getSeconds() + "초";
}