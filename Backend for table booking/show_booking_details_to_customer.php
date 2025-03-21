<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// Enable error reporting for debugging
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);

// Database connection
require 'db.php';

// Check if required parameters are set
if (!isset($_GET['booking_date']) || !isset($_GET['table_no'])) {
    http_response_code(400);
    echo json_encode(["error" => "Missing required parameters"]);
    exit;
}

$booking_date = $_GET['booking_date'];
$table_no = $_GET['table_no'];


// Prepare SQL query
$sql = "SELECT b.id AS booking_id, b.table_no, b.starting_time, b.ending_time, b.booking_date, b.amount, b.transaction_id, 
               u.id AS user_id, u.name, u.email, u.phone, u.usertype 
        FROM tbl_bookings b 
        JOIN users u ON b.user_id = u.id 
        WHERE b.booking_date >= ? AND b.table_no = ? 
        ORDER BY b.booking_date ASC";

$stmt = $conn->prepare($sql);
$stmt->bind_param("ss", $booking_date, $table_no);
$stmt->execute();
$result = $stmt->get_result();

$bookings = [];
while ($row = $result->fetch_assoc()) {
    $bookings[] = $row;
}

// Check if results are empty
if (empty($bookings)) {
    http_response_code(404);
    echo json_encode(["message" => "No bookings found for the given date and table number"]);
} else {
    http_response_code(200);
    echo json_encode(["bookings" => $bookings]);
}

$stmt->close();
$conn->close();
?>
