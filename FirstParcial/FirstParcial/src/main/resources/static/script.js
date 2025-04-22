document.addEventListener('DOMContentLoaded', function () {

    function showModal(id) {
        document.getElementById("modal-" + id).classList.remove("hidden");
    }

    function closeModal(id) {
        document.getElementById("modal-" + id).classList.add("hidden");
    }

    function performAction(method, url = '') {
        const idInput = getIdInput(url);
        const dataInput = getFormData(url);

        const finalUrl = url.replace("{id}", idInput);
        const fullUrl = 'http://localhost:8080' + finalUrl;

        let options = {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
        };

        if (method === 'POST' || method === 'PUT' || method === 'PATCH') {
            options.body = JSON.stringify(dataInput);
        }

        fetch(fullUrl, options)
            .then(response => response.json())
            .then(data => {
                console.log('Respuesta de la API:', data);
                alert('Operación completada: ' + method + ' en ' + fullUrl);
                closeModal(getModalFromUrl(url));
                if (method === 'GET') {
                    displayList(data, url);
                }
            })
            .catch(error => {
                console.error('Error en la solicitud:', error);
                alert('Error en la operación');
            });
    }


    function getIdInput(url) {
        let idInput = '';
        if (url.includes('habitaciones')) {
            idInput = document.getElementById("habitacionID").value || '';
        }
        if (url.includes('clientes')) {
            idInput = document.getElementById("clienteID").value || '';
        }

        return idInput && !isNaN(idInput) ? Number(idInput) : '';
    }

    function getFormData(url) {
        let formData = {};
        if (url.includes('habitaciones') || url.includes('clientes')) {
            formData = {
                nombres: document.getElementById("nombres").value || '',
                apellidos: document.getElementById("apellidos").value || '',
                ci: document.getElementById("ci").value || '',
                telefono: document.getElementById("telefono").value || ''
            };
        }

        return formData;
    }

    function getModalFromUrl(url) {
        if (url.includes('habitaciones')) return 'habitaciones';
        if (url.includes('clientes')) return 'clientes';
        if (url.includes('pagos')) return 'pagos';
        if (url.includes('parqueo')) return 'parqueo';
        if (url.includes('reserva')) return 'reserva';
        if (url.includes('personal')) return 'personal';
        return '';
    }


    function displayList(data, url) {
        let listContainer = '';

        if (url.includes('habitaciones')) {
            listContainer = 'habitaciones-list';
        } else if (url.includes('clientes')) {
            listContainer = 'clientes-list';
        } else if (url.includes('pagos')) {
            listContainer = 'pagos-list';
        } else if (url.includes('parqueo')) {
            listContainer = 'parqueo-list';
        } else if (url.includes('reserva')) {
            listContainer = 'reserva-list';
        } else if (url.includes('personal')) {
            listContainer = 'personal-list';
        }

        const listElement = document.getElementById(listContainer);
        listElement.innerHTML = '';
        data.forEach(item => {
            let listItem = document.createElement('div');
            listItem.textContent = JSON.stringify(item);
            listElement.appendChild(listItem);
        });
    }

    function setupActionButtons() {

        document.querySelectorAll('.button-habitaciones').forEach(button => {
            button.addEventListener('click', function () {
                const method = this.dataset.method;
                const url = this.dataset.url;
                performAction(method, url);
            });
        });

        document.querySelectorAll('.button-clientes').forEach(button => {
            button.addEventListener('click', function () {
                const method = this.dataset.method;
                const url = this.dataset.url;
                performAction(method, url);
            });
        });

        document.querySelectorAll('.button-pagos').forEach(button => {
            button.addEventListener('click', function () {
                const method = this.dataset.method;
                const url = this.dataset.url;
                performAction(method, url);
            });
        });

        document.querySelectorAll('.button-parqueo').forEach(button => {
            button.addEventListener('click', function () {
                const method = this.dataset.method;
                const url = this.dataset.url;
                performAction(method, url);
            });
        });

        document.querySelectorAll('.button-reserva').forEach(button => {
            button.addEventListener('click', function () {
                const method = this.dataset.method;
                const url = this.dataset.url;
                performAction(method, url);
            });
        });

        document.querySelectorAll('.button-personal').forEach(button => {
            button.addEventListener('click', function () {
                const method = this.dataset.method;
                const url = this.dataset.url;
                performAction(method, url);
            });
        });
    }

    setupActionButtons();
});
