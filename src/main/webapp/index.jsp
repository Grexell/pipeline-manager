<html>
<body>
<button class="button1">try send</button><button class="button2">try get</button>
<br/>
<textarea class="response"></textarea>
<script>
    var btn = document.querySelector(".button1");

    btn.onclick = function(){
        fetch('/api/pipeline', {
            body: document.querySelector(".response").value,
            method: 'PUT', // *GET, POST, PUT, DELETE, etc.
        }).then(function (value) {
            return value.text();
        }).then(function (value) {
            document.querySelector(".response").value = value;
        })
    }

    btn = document.querySelector(".button2");

    btn.onclick = function(){
        fetch('/api/', {
            method: 'Get' // *GET, POST, PUT, DELETE, etc.
        }).then(function (value) {
            return value.text();
        }).then(function (value) {
            document.querySelector(".response").value = value;
        })
    }
</script>
</body>
</html>
