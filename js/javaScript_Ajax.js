document.getElementById('change').addEventListener('click', changeText, false);

function changeText() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
            // Checking if everything is ready and ok
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                document.getElementById('mainText').innerHtml = xhttp.responseText;
            }
        }
        // The request which is either get or post
    xhttp.open('GET', 'file.txt', true);
    xhttp.send();
}