<?php

header('Content-Type: application/json');
require 'db.php'; // Include database connection

// Get the request host dynamically
// $host = $_SERVER['REQUEST_SCHEME'] . '://' . $_SERVER['HTTP_HOST'] . '/tablebooking/uploads/';
$host = 'tablebooking/uploads/';

$sql = "SELECT table_no, table_name, table_description, table_images, amount, table_status, table_capacity FROM table_booking.restaurant";
$result = $conn->query($sql);

$tables = [];

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        // Convert image file name to full URL
        $row['table_images'] = !empty($row['table_images']) ? $host . $row['table_images'] : null;
        $tables[] = $row;
    }
    echo json_encode(['success' => true, 'data' => $tables]);
} else {
    echo json_encode(['success' => false, 'message' => 'No tables found']);
}

$conn->close();
