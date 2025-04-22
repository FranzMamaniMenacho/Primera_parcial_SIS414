document.addEventListener('DOMContentLoaded', function () {

    function showModal(id) {
        document.getElementById("modal-" + id).classList.remove("hidden");
    }

    function closeModal(id) {
        document.getElementById("modal-" + id).classList.add("hidden");
    }

    function performAction(method, url = '') {
        const idInput = getIdInput(url);  // Obtener el ID ingresado dinámicamente
        const dataInput = getFormData(url); // Recoger los datos del formulario

        // Si es un GET por ID, se asegura de que la URL incluya el ID correcto
        let finalUrl = url;
        if (idInput) {
            finalUrl = url.replace("{id}", idInput);  // Reemplazar {id} con el valor del ID
        }

        const fullUrl = 'http://localhost:8080' + finalUrl; // URL completa de la solicitud

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

        // Habitaciones
        if (url.includes('habitaciones')) {
            idInput = document.getElementById("habitacionId").value || '';
        }

        // Clientes
        if (url.includes('clientes')) {
            idInput = document.getElementById("clienteId").value || '';
        }

        // Pagos
        if (url.includes('pagos')) {
            idInput = document.getElementById("pagoId").value || '';
        }

        // Parqueo
        if (url.includes('parqueo')) {
            idInput = document.getElementById("parqueoId").value || '';
        }

        // Reserva
        if (url.includes('reserva')) {
            idInput = document.getElementById("reservaId").value || '';
        }

        // Personal
        if (url.includes('personal')) {
            idInput = document.getElementById("personalId").value || '';
        }

        return idInput && !isNaN(idInput) ? Number(idInput) : '';  // Solo si el ID es válido
    }

    function getFormData(url) {
        let formData = {};

        // Habitaciones
        if (url.includes('habitaciones')) {
            formData = {
                tipoHabitacion: document.getElementById("tipoHabitacion").value || '',
                disponible: document.getElementById("disponible").checked || false,
                precioNoche: document.getElementById("precioNoche").value || ''
            };
        }

        // Clientes
        if (url.includes('clientes')) {
            formData = {
                nombres: document.getElementById("nombres").value || '',
                apellidos: document.getElementById("apellidos").value || '',
                ci: document.getElementById("ci").value || '',
                telefono: document.getElementById("telefono").value || ''
            };
        }

        // Pagos
        if (url.includes('pagos')) {
            formData = {
                monto: document.getElementById("monto").value || '',
                fecha: document.getElementById("fecha").value || '',
                metodo: document.getElementById("metodo").value || '',
                estado: document.getElementById("estado").value || ''
            };
        }

        // Parqueo
        if (url.includes('parqueo')) {
            formData = {
                marca: document.getElementById("marca").value || '',
                placa: document.getElementById("placa").value || '',
                precioPorNoche: document.getElementById("precioPorNoche").value || '',
                color: document.getElementById("color").value || ''
            };
        }

        // Reserva
        if (url.includes('reserva')) {
            formData = {
                fechaEntrada: document.getElementById("fechaEntrada").value || '',
                fechaSalida: document.getElementById("fechaSalida").value || '',
                habitacion: document.getElementById("habitacion").value || ''
            };
        }

        // Personal
        if (url.includes('personal')) {
            formData = {
                nombrePersonal: document.getElementById("nombrePersonal").value || '',
                rol: document.getElementById("rol").value || '',
                idEmpleado: document.getElementById("idEmpleado").value || ''
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
        listElement.innerHTML = '';  // Limpiar los resultados anteriores

        if (Array.isArray(data)) {
            // Si los datos son una lista (GET todos)
            data.forEach(item => {
                let listItem = document.createElement('div');
                listItem.innerHTML = `<strong>ID:</strong> ${item.id} <br> <strong>Detalles:</strong> ${JSON.stringify(item)}`;
                listElement.appendChild(listItem);
            });
        } else {
            // Si los datos son un solo objeto (GET por ID)
            let listItem = document.createElement('div');
            listItem.innerHTML = `<strong>ID:</strong> ${data.id} <br> <strong>Detalles:</strong> ${JSON.stringify(data)}`;
            listElement.appendChild(listItem);
        }
    }

    setupActionButtons();
});
