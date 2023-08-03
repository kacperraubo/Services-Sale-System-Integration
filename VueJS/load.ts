window.addEventListener('load', function() {
    new Vue({
        el: '#app',
        mounted() {
            this.checkUserAuthentication();
        },
        methods: {
            checkUserAuthentication() {
                axios.get('/check-user-authentication')
                    .then((response: { data: any; }) => console.log(response.data))
                    .catch((error: any) => console.log(error));
            }
        }
    });
});
