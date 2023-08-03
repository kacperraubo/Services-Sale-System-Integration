window.addEventListener('load', function() {
    fetch('/check-user-authentication', {
        method: 'GET',
    })
    .then((response) => response.json())
    .then(data => console.log(data))
    .catch((err) => console.log(err))               
});