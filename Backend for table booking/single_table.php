<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
require 'db.php';

// Function to fetch data for a single table
function getSingleTableData($conn, $table_no) {
    // Sanitize the input to prevent SQL injection
    $table_no = $conn->real_escape_string($table_no);

    $sql = "
        SELECT 
            r.id AS restaurant_id,
            r.table_no,
            r.table_name,
            r.table_description,
            r.table_images,
            r.table_status,
            r.table_capacity,
            b.id AS booking_id,
            b.user_id,
            b.starting_time,
            b.ending_time,
            b.booking_date,
            b.amount,
            b.transaction_id
        FROM 
            restaurant r
        LEFT JOIN 
            tbl_bookings b
        ON 
            r.table_no = b.table_no
        WHERE 
            r.table_no = '$table_no'
    ";

    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        return $result->fetch_assoc(); // Return a single row
    } else {
        return null; // Return null if no data is found
    }
}

// Handle GET request
if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    // Check if table_no is provided in the query parameters
    if (isset($_GET['table_no'])) {
        $table_no = $_GET['table_no'];
        $response = getSingleTableData($conn, $table_no);

        if ($response) {
            echo json_encode($response);
        } else {
            echo json_encode(["error" => "Table not found"]);
        }
    } else {
        echo json_encode(["error" => "Table number (table_no) is required"]);
    }
}

// Close connection
$conn->close();
?>