const API = 'http://localhost:8080/api/vehiculos';

async function cargarVehiculos() {
    const response = await fetch(API);
    const datos = await response.json();

    const tbody = document.getElementById('msg-lista');
    tbody.innerHTML = ""; // limpiar antes de insertar

    datos.forEach(vehiculo => {
        const fila = `
            <tr>
                <td>${vehiculo.id}</td>
                <td>${vehiculo.modelo}</td>
                <td>${vehiculo.categoria}</td>
                <td>${vehiculo.descripcion}</td>
                <td>${vehiculo.precioPorDia}</td>
                <td>${vehiculo.unidadesDisponibles}</td>
            </tr>
        `;
        tbody.innerHTML += fila;
    });
}

async function buscarPorId() {
    const id = document.getElementById('buscar-id').value;

    if (!id) {
        document.getElementById('msg-buscar').innerHTML =
            "<tr><td colspan='6'>Por favor ingresa un ID.</td></tr>";
        return;
    }

    try {
        const response = await fetch(`${API}/${id}`);
        if (!response.ok) {
            throw new Error("No se encontró el vehiculo");
        }
        const vehiculo = await response.json();

        const fila = `
            <tr>
                <td>${vehiculo.id}</td>
                <td>${vehiculo.modelo}</td>
                <td>${vehiculo.categoria}</td>
                <td>${vehiculo.descripcion}</td>
                <td>${vehiculo.precioPorDia}</td>
                <td>${vehiculo.unidadesDisponibles}</td>
            </tr>
        `;
        document.getElementById('msg-buscar').innerHTML = fila;

    } catch (error) {
        document.getElementById('msg-buscar').innerHTML =
            `<tr><td colspan='6'>${error.message}</td></tr>`;
    }
}

async function crear() {
    const vehiculo = {
        modelo:              document.getElementById('modelo').value,
        categoria:           document.getElementById('categoria').value,
        descripcion:         document.getElementById('descripcion').value,
        precioPorDia:        parseFloat(document.getElementById('precioPorDia').value),
        unidadesDisponibles: parseInt(document.getElementById('unidadesDisponibles').value)
    };

    const response = await fetch(API, {
        method:  'POST',
        headers: { 'Content-Type': 'application/json' },
        body:    JSON.stringify(vehiculo)
    });

    const datos = await response.json();
    document.getElementById('msg-crear').textContent = JSON.stringify(datos, null, 2);
}

async function actualizar() {
    const id = document.getElementById('upd-id').value;

    const vehiculo = {
        modelo:              document.getElementById('upd-modelo').value,
        categoria:           document.getElementById('upd-categoria').value,
        descripcion:         document.getElementById('upd-descripcion').value,
        precioPorDia:        parseFloat(document.getElementById('upd-precioPorDia').value),
        unidadesDisponibles: parseInt(document.getElementById('upd-unidadesDisponibles').value)
    };

    const response = await fetch(`${API}/${id}`, {
        method:  'PUT',
        headers: { 'Content-Type': 'application/json' },
        body:    JSON.stringify(vehiculo)
    });

    const datos = await response.json();
    document.getElementById('msg-actualizar').textContent = JSON.stringify(datos, null, 2);
}

async function eliminar() {
    const id = document.getElementById('eliminar-id').value;

    const response = await fetch(`${API}/${id}`, {
        method: 'DELETE'
    });

    const datos = await response.json();
    document.getElementById('msg-eliminar').textContent = JSON.stringify(datos, null, 2);
}
