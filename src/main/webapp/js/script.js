function createTable(tableData, headers) {
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

    // Create Table tableData
    tableData.forEach(rowElements => {
        const row = document.createElement("tr");
        Object.values(rowElements).forEach(rowValue => {
            const td = document.createElement("td");
            td.textContent = rowValue;
            row.appendChild(td);
        });
        table.appendChild(row);
    });
    document.getElementById("tableContainer").appendChild(table);
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
               createTable(tableData, headers);
           } catch (e) {
               console.error('Error parsing JSON:', e);
           }
       })
       .catch(error => {
           console.error('Error fetching data:', error);
       });
