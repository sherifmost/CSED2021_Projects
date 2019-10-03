<?php
 $servername = "localhost";
$username = "root";
$password = "";
$dbname = "job";

// Create connection
$conn = new mysqli($servername,$username,$password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
$sql ="SELECT * FROM companyform";
$query = mysqli_query($conn, $sql);

if (!$query) {
	die ('SQL Error: ' . mysqli_error($conn));
}
?>

<html>
<head>
	<title>Displaying MySQL Data in HTML Table</title>
	<style type="text/css">
		body {
			font-size: 15px;
			color: #343d44;
			font-family: "segoe-ui", "open-sans", tahoma, arial;
			padding: 0;
			margin: 0;
		}
		table {
			margin: auto;
			font-family: "Lucida Sans Unicode", "Lucida Grande", "Segoe Ui";
			font-size: 12px;
		}

		h1 {
			margin: 25px auto 0;
			text-align: center;
			text-transform: uppercase;
			font-size: 17px;
		}

		table td {
			transition: all .5s;
		}
		
		/* Table */
		.data-table {
			border-collapse: collapse;
			font-size: 14px;
			min-width: 537px;
		}

		.data-table th, 
		.data-table td {
			border: 1px solid #e1edff;
			padding: 7px 17px;
		}
		.data-table caption {
			margin: 7px;
		}

		/* Table Header */
		.data-table thead th {
			background-color: #508abb;
			color: #FFFFFF;
			border-color: #6ea1cc !important;
			text-transform: uppercase;
		}

		/* Table Body */
		.data-table tbody td {
			color: #353535;
		}
		.data-table tbody td:first-child,
		.data-table tbody td:nth-child(4),
		.data-table tbody td:last-child {
			text-align: right;
		}

		.data-table tbody tr:nth-child(odd) td {
			background-color: #f4fbff;
		}
		.data-table tbody tr:hover td {
			background-color: #ffffa2;
			border-color: #ffff0f;
		}

		/* Table Footer */
		.data-table tfoot th {
			background-color: #e5f5ff;
			text-align: right;
		}
		.data-table tfoot th:first-child {
			text-align: left;
		}
		.data-table tbody td:empty
		{
			background-color: #ffcccc;
		}
	</style>
</head>
     
<body>
	<h1>results</h1>
	<table class="data-table">
		<caption class="title">ALL JOBS</caption>
		<thead>
			<tr>
				<th>Company Name</th>
				<th>field</th>
				<th>position</th>
				<th>empty positions</th>
				<th>Address</th>
                <th>manager name</th>
                <th>email</th>
                <th>description</th>
                <th>phone</th>
                
			</tr>
		</thead>
		<tbody>
       
		<?php
		if(mysqli_num_rows($query)>0){
		while ($row = mysqli_fetch_array($query))
		{
            
            
               echo '<tr>
					<td>'.$row['Name'].'</td>
					<td>'.$row['Field'].'</td>
					<td>'.$row['Position'].'</td>
					<td>'.$row['EmptyPositions'].'</td>
					<td>'.$row['Address'].'</td>
                    <td>'.$row['ManagerName'].'</td>
                    <td>'.$row['ManagerEmail'].'</td>
                    <td>'.$row['JobDescription'].'</td>
                    <td>'.$row['managerphone'].'</td>
                    <input type=hidden name=id value='.$row['id'].'>
                    </tr>';
          // header("refresh:2; url=AddJob.html");
        }
               
		}
            ?>
                                                         
		</tbody>
		<tfoot>
		</tfoot>
	</table>
</body>
</html>