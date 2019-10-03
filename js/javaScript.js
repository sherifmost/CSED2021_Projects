// Script to enable the tooltips and Popovers
$(function() {
    $('[data-toggle="tooltip"]').tooltip()
})
$(function() {
    $('[data-toggle="popover"]').popover()
})

function register(e) {
    e.preventDefault();
    var name = document.getElementById('name').value;
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;
    var password2 = document.getElementById('password2').value;
    var msg = document.getElementById('msg');
    if (name == '' || email == '' || password == '' || password2 == '') {
        msg.innerHTML = 'Please fill out all fields';
        // Changing the class or adding new classes.
        msg.className = 'alert alert-danger';
    } else {
        if (name.length < 3) {
            msg.innerHTML = 'Name must be at least 3 characters';
            // Changing the class or adding new classes.
            msg.className = 'alert alert-danger';
        } else {
            var atpos = email.indexOf("@");
            var dotpos = email.lastIndexOf(".");
            if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= email.length) {
                msg.innerHTML = 'Please use valid email';
                // Changing the class or adding new classes.
                msg.className = 'alert alert-danger';
            } else {
                if (password != password2) {
                    msg.innerHTML = "Passwords don't match";
                    // Changing the class or adding new classes.
                    msg.className = 'alert alert-danger';

                } else {
                    // Success 
                    msg.innerHTML = name + ' is registered with email ' + email;
                    msg.className = 'alert alert-success';
                }
            }
        }
    }
}
document.getElementById('regForm').addEventListener('submit', register, false);