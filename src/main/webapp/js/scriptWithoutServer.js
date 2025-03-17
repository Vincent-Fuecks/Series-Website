const htmlTable = document.createElement("table");
const htmlInput = document.getElementById("dataForm");

htmlTable.setAttribute("id", "series-table");


async function fetchSeriesData() {
    const response = await fetch("data/data.json");
    const json = await response.json();
    return json.Elements;
}
 
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
    console.log(typeof(tableContent));
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
        
        let tableContent = [
            [seriesName, rating, status]
        ];
    
        addRowsToTable("tableContainer", tableContent)
        document.getElementById("dataForm").reset();
    }
}

function displaySeries() {
    fetchSeriesData().then(data => {
        const headers = ["Name", "Rating", "State"];
        createTable(data, headers, "tableContainer");
        document.getElementById("tableContainer").appendChild(htmlTable);
    });
}

// Run display function on page load
window.onload = displaySeries;