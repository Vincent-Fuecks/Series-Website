const htmlTable = document.createElement("table");
const htmlInput = document.getElementById("dataForm");

htmlTable.setAttribute("id", "series-table");
 
function createTable(tableContent, headers, tableId) {
    const headerRow = document.createElement("tr");

    // Create Tabel header
    headers.forEach(header => {
        const th = document.createElement("th");
        th.textContent = header;
        headerRow.appendChild(th);
    });
    htmlTable.appendChild(headerRow);

    // Create Table rows
    tableContent.forEach(row => {
        const htmlTr = document.createElement("tr");
        Object.values(row).forEach(rowValue => {
            const htmlTd = document.createElement("td");
            htmlTd.textContent = rowValue;
            htmlTr.appendChild(htmlTd);
        });
        htmlTable.appendChild(htmlTr);
    });
    document.getElementById(tableId).appendChild(htmlTable);
}

function addRowsToTable(tableId, tableContent) {
    tableContent.forEach(row => {
        const htmlRow = document.createElement("tr");
        Object.values(row).forEach(rowValue => {
            const htmlTd = document.createElement("td");
            htmlTd.textContent = rowValue;
            htmlRow.appendChild(htmlTd);
        });
        htmlTable.appendChild(htmlRow);
    });
    document.getElementById(tableId).appendChild(htmlTable);
}

// Function to handle form submission
function handleFormSubmit(event) {
    const seriesName = document.getElementById('seriesName').value;
    const rating = document.getElementById('rating').value;
    const status = document.getElementById('status').value;

    if (event.key === "Enter" && seriesName.length !== 0 && rating.length !== 0 && status.length !== 0){
        event.preventDefault();
        
        let tableContentForBackend = [
            ['Name', seriesName],
            ["Rating", rating],
            ["Situation", status]
        ];

        let tableContent = [[seriesName, rating, status]];

        const socket = new WebSocket('ws://localhost:8080/series-website/index');

        // Open Socket and Send form content to Backend
        socket.onopen = () => {
            console.log("Verbindung zum WebSocket-Server hergestellt.");
            socket.send(JSON.stringify(tableContentForBackend));
        };
        
        // Only append to table new row form form, if row is stored in backend
        // Therefore wait for convermation from backend
        socket.onmessage = function(event) {
            let response = JSON.parse(event.data)
           
            if (response.status === "success"){
                console.log("Die Serie wurde erfolgreich hinzugefügt: " + response.message);
                addRowsToTable("tableContainer", tableContent)
                document.getElementById("dataForm").reset();
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

// Fetch data from the servlet
fetch('/series-website/index')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(text => {
        console.log('Response from server:', text);

        try {
            const data = JSON.parse(text);
            const headers = data[0];
            const tableData = data.slice(1);
            createTable(tableData, headers, "tableContainer");
        } catch (e) {
            console.error('Error parsing JSON:', e);
        }
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });