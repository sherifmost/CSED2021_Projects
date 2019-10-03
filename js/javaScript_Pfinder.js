function getProfile(e) {
    e.preventDefault();
    var username = document.getElementById('username').value;
    if (!username || username == '') {
        username = 'elshabtota';
    }
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            var user = JSON.parse(xhttp.responseText);
            // the added js is preceeded by $
            document.getElementById('profile').innerHTML = `<div class="card">
            <div class="card-header">
            ${user.name}
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-3">
                        <img src="${user.avatar_url}" alt="" class="img-fluid mx-auto">
                    </div>
                    <div class="col-md-9">
                        <span class="lbl-primary">Public Repos ${user.public_repos}</span>
                        <span class="lbl-danger">Public Gists ${user.public_gists}</span>
                        <br><br>
                        <ul class="list-group">
                            <li class="list-group-item">Website: ${user.blog}</li>
                            <li class="list-group-item">Email: ${user.email}</li>
                            <li class="list-group-item">Bio: ${user.bio}</li>
                            <li class="list-group-item">Website: ${user.blog}</li>
                        </ul>
                        <br>
                        <a class="btn btn-primary" target="_blank" href="${user.html_url}">Visit Github</a>
                    </div>
                </div>   
            </div>
          </div>`;
        }
    }
    xhttp.open('GET', 'https://api.github.com/users/' + username, true);
    xhttp.send();
}
document.getElementById('userForm').addEventListener('submit', getProfile, false);