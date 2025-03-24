const htmlTable = document.createElement("table");
const htmlInput = document.getElementById("dataForm");

htmlTable.setAttribute("id", "series-table");


// --- Function to handle form submission + Addes dynamic new rows---
function handleFormSubmit(event) {
    const seriesName = document.getElementById('name').value;
    const rating = document.getElementById('rating').value;
    const status = document.getElementById('status').value;

    if (event.key === "Enter" && seriesName.length !== 0 && rating.length !== 0 && status.length !== 0){
        event.preventDefault();
        
        let tableContent = [seriesName, rating, status];
        const socket = new WebSocket('ws://localhost:8080/series-website/index-web-socket');

        // Open Socket and Send form content to Backend
        socket.onopen = () => {
            console.log("Verbindung zum WebSocket-Server hergestellt.");
            socket.send(JSON.stringify(tableContent));
        };
        
        // Only append to table new row form form, if row is stored in backend
        // Therefore wait for convermation from backend
        socket.onmessage = function(event) {
            let response = JSON.parse(event.data)
           
            if (response.status === "success"){
                console.log(response.data)
                addRowsToTable(response.data, "tableContainer")
                document.getElementById("dataForm").reset();
                console.log("Die Serie wurde erfolgreich hinzugefügt!");
            }
        };
        
        socket.onerror = (error) => {
            console.error('WebSocket Fehler:', error);
        };
        
        socket.onclose = () => {
            console.log("Verbindung zum WebSocket-Server geschlossen.");
        };
    }
}

function addRowsToTable(tableContent, tableId) {
    const htmlRow = document.createElement("tr");

    for (let key in tableContent) {
        if (key != "id"){
            const htmlTd = document.createElement("td");
            htmlTd.textContent = tableContent[key];
            htmlRow.appendChild(htmlTd);
        }
    }
    htmlTable.appendChild(htmlRow);
}

// --- Load Init Data for Table ---
fetch('/series-website/api/series')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();  // Direktes Parsen als JSON
    })
    .then(data => {
        console.log('Response from server:', data);
        createTableFromJson(data, "tableContainer");
    })
    .catch(error => {
        console.error('Error fetching series:', error);
    });

function createTableFromJson(tableContent, tableId){
    const htmlTrHeader = document.createElement("tr");

    for (let key in tableContent[0]) {
        if (key != "id"){
            const htmlTh = document.createElement("th");
            htmlTh.textContent = key[0].toUpperCase() + key.slice(1);
            htmlTrHeader.appendChild(htmlTh);
        }
    }
    htmlTable.appendChild(htmlTrHeader);

    tableContent.forEach(series => {
        const htmlTr = document.createElement("tr");
        for (let key in series) {
            if (key != "id"){
                const htmlTd = document.createElement("td");
                htmlTd.textContent = series[key];
                htmlTr.appendChild(htmlTd);
            }
        }
        htmlTable.appendChild(htmlTr);
    });
    document.getElementById(tableId).appendChild(htmlTable);   
}