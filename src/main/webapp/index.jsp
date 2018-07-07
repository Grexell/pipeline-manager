<html>
<body>
<button class="button1">try send</button><button class="button2">try get</button>
<button class="button3">delete</button><button class="button4">update</button><input type="text" class="name">
<select class="method">
    <option value="PUT">PUT</option>
    <option value="DELETE">DELETE</option>
    <option value="POST">POST</option>
</select>
<input type="checkbox" class="body"/>
<button class="custom">Send</button>
<br/>
<textarea class="response" style="width: 100%; height: 500px;"></textarea>
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

    btn = document.querySelector(".button3");

    btn.onclick = function(){
        fetch('/api/pipeline/' + document.querySelector(".name").value, {
            method:  document.querySelector(".method").value,
            body: document.querySelector(".response").value
        }).then(function (value) {
            return value.text();
        }).then(function (value) {
            document.querySelector(".response").value = value;
        })
    }

    btn = document.querySelector(".button4");

    btn.onclick = function(){
        fetch('/api/pipeline/' + document.querySelector(".name").value, {
            body: document.querySelector(".response").value,
            method: 'post' // *GET, POST, PUT, DELETE, etc.
        }).then(function (value) {
            return value.text();
        })
    }

    btn = document.querySelector(".custom");

    btn.onclick = function(){
        fetch('/api/' + document.querySelector(".name").value, {
            body: document.querySelector(".response").value,
            method:  document.querySelector(".method").value // *GET, POST, PUT, DELETE, etc.
        }).then(function (value) {
            return value.text();
        })
    }
</script>
</body>
</html>
