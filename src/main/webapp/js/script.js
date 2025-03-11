async function fetchSeriesData() {
    const response = await fetch("data/data.json");
    const json = await response.json();
    return json.Elements;
}

function createTable(data, headers) {
    const table = document.createElement("table");
    table.setAttribute("id", "series-table");

    // Create Table Header
    const headerRow = document.createElement("tr");
    headers.forEach(header => {
        const th = document.createElement("th");
        th.textContent = header;
        headerRow.appendChild(th);
    });
    table.appendChild(headerRow);

    // Create Table Rows
    data.forEach(item => {
        const row = document.createElement("tr");
        Object.values(item).forEach(value => {
            const td = document.createElement("td");
            td.textContent = value;
            row.appendChild(td);
        });
        table.appendChild(row);
    });

    return table;
}

function displaySeries() {
    fetchSeriesData().then(data => {
        const headers = ["Name", "Rating", "State"];
        const table = createTable(data, headers);
        document.getElementById("list-puntate").appendChild(table);
    });
}

// Run display function on page load
window.onload = displaySeries;